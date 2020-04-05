package cn.eclubcc.common.util.activity;

import cn.eclubcc.common.util.RedisUtil;
import cn.eclubcc.pojo.activity.ActivityForDetail;
import cn.eclubcc.pojo.http.response.QueryResult;
import cn.eclubcc.service.ActivityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Slf4j
@Component
public class ActivityCacheUntil extends RedisUtil {
    // 首页缓存基础时间 -- 秒为单位
    public static final long BASE_EXPIRE = 60;

    public static final int CLUBLIST_LIMIT = 10;


    /**
     * 将缓存存入Redis
     * @param key
     * @param list
     * @param time  过期时间 单位：分钟
     * @return
     * author: markeNick
     */
    public static boolean setCacheByList(String key, List list, int time) {
        System.out.println("=====>activity setCacheByList");
        return RedisUtil.lSet(key, list, BASE_EXPIRE * time);
    }

    /**
     * 从缓存获取数据，没有数据就去数据库获取
     * @param page
     * @return List<Object>
     * @throws InterruptedException
     * author: markeNick
     */
    public static QueryResult getCacheOfActivityList(ActivityForDetail activity,ActivityService activityService,int page, int limit,Integer state) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        List<String> list = getNoNullObjectValue(activity);//获取用来拼接key的非空字符串
        QueryResult queryResult=new QueryResult();
        List result = null;
        if(list==null){//字符串全为空 尝试从缓存中取
            String key="activityList:all:page:" + page + ":limit:" + limit+":state:"+state;
            if (checkedCacheData(queryResult, key)) return queryResult;
            //缓存没有查数据库
          return selectDataFromDB(activity,activityService,page,limit,state,true,queryResult);
        }
        String id = activity.getId();
        String clubId = activity.getClubId();
        String userId = activity.getUserId();
        //参数中至少有其中一个:活动id、clubId、userId
        // 查询缓存
        String key="activityList:" + id+":"
                +clubId+":"+userId + ":page:" + page + ":limit:" + limit+":state:"+state;
        if (checkedCacheData(queryResult, key)) return queryResult;

        // 缓存没有查数据库
        return selectDataFromDB(activity,activityService,page,limit,state,false,queryResult);
    }

    public static boolean checkedCacheData(QueryResult queryResult, String key) {
        List result;
        result = RedisUtil.lGet(key, 0, -1);
        if(result!=null&&result.size()>0){//假如缓存有

            long total=((Integer)RedisUtil.get(key + ":total")).longValue();
            if(result.get(0).equals("nothing")){//缓存是无效数据
                queryResult.setList(null);
                queryResult.setTotal(0);
            }else {//缓存是有效数据
                queryResult.setList(result);
                queryResult.setTotal(total);
            }
            return true;
        }
        return false;
    }

    /**
     * 从数据库取活动详细列表,并放到缓存中
     * @param activity
     * @param activityService
     * @param page
     * @param limit
     * @param state
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static QueryResult selectDataFromDB(ActivityForDetail activity,
                                               ActivityService activityService,
                                               int page, int limit, Integer state,
                                               boolean isNull,QueryResult queryResult) throws IllegalAccessException, NoSuchFieldException, InterruptedException {
        List result = null;
        ReentrantLock lock = new ReentrantLock();
        // 成功获取锁，则从数据库获取数据
        if(lock.tryLock()) {
            try {
                // 从数据库获取数据
                Page<Object> objects = PageHelper.startPage(page, limit, true);
                result = activityService.selectActivityDetailById(activity,page,limit,state);
                long total = objects.getTotal();//记录总数
                if (isNull){//id、clubId、userId一个都没传
                    String key="activityList:all:page:" + page
                            + ":limit:" + limit+":state:"+state;
                    if (result!=null&&result.size()>0){//数据库有数据 更新缓存

                        setCacheByList(key, result, 60);//活动信息列表
                        RedisUtil.set(key+ ":total",total,3600);//记录数

                    }else { //数据库无数据 更新缓存  防缓存穿透

                        result.add("nothing");//数据库无数据
                        log.info("cn.eclubcc.common.util.activity.ActivityCacheUntil.selectDataFromDB==>查询所有活动详细信息,数据库无数据");
                        setCacheByList(key, result, 10);//无效数据
                        RedisUtil.set(key+ ":total",total,600);//记录数
                    }

                }else {//至少有其中一个参数id、clubId、userId
                    String id = activity.getId();
                    String clubId = activity.getClubId();
                    String userId = activity.getUserId();
                    String key="activityList:" + id
                            + ":" + clubId + ":" + userId + ":page:" + page + ":limit:" + limit + ":state:" + state;

                    if (result!=null&&result.size()>0) {//数据库有数据 更新缓存

                        setCacheByList(key, result, 60);//活动信息列表
                        RedisUtil.set(key+ ":total",total,3600);//记录数

                    }else { //数据库无数据

                        // 更新缓存 防止缓存穿透
                        result.add("nothing");//数据库无数据
                        log.info("cn.eclubcc.common.util.activity.ActivityCacheUntil.selectDataFromDB==>查询所有活动详细信息,数据库无数据");
                        setCacheByList(key, result, 10);//无效数据
                        RedisUtil.set(key+ ":total",total,600);//记录数

                    }

                }
                if(result!=null&&result.size()>0){
                    queryResult.setList(result);
                }
                queryResult.setTotal(total);
            } finally {
                lock.unlock();
            }
        } else {    // 获取锁失败
            // 休眠100ms后，重新获取数据
            Thread.sleep(100);
            queryResult = getCacheOfActivityList(activity, activityService, page, limit,state);
        }
        return queryResult;
    }

    /**
     * 反射获取属性值
     * @param o 目标对象
     * @param name 目标属性名
     * @return
     */
    public static String getValueByObjectAndFieldName(Object o,String name) throws NoSuchFieldException, IllegalAccessException {
        Field filed = o.getClass().getDeclaredField(name);
        filed.setAccessible(true);
        return (String)filed.get(o);
    }

    /**
     * 反射获取对象中不为空的值
     *
     * @param object 目标对象
     * @return 返回字符数组
     */
    public static List<String> getNoNullObjectValue(Object object) throws IllegalAccessException {
        if (null == object) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString()) && f.getType()==String.class) {
                    String value = (String) f.get(object);
                    arrayList.add(value);
                }

            }
            if(arrayList.size()==0){
                arrayList=null;
            }
        return arrayList;
    }
}

