package cn.eclubcc.common.util.activity;

import cn.eclubcc.common.util.RedisUtil;
import cn.eclubcc.pojo.activity.ActivityForDetail;
import cn.eclubcc.service.ActivityService;
import cn.eclubcc.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author moyingren
 * @date 2020/4/2
 */
@Slf4j
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
    public static List getCacheOfActivityList(ActivityForDetail activity,ActivityService activityService,int page, int limit,Integer state) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        List<String> list = getNoNullObjectValue(activity);//获取用来拼接key的非空字符串
        List result = null;
        if(list==null){//字符串全为空 尝试从缓存中取
            result = RedisUtil.lGet("activityList_all_page_" + page + "_limit_" + limit, 0, -1);
            if(result!=null){//假如缓存有 长度等于0 也要返回 防止缓存穿透
                RedisUtil.expire("activityList_all_page_" + page + "_limit_" + limit,60);//更新过期时间 一小时
                return result;
            }
            //缓存没有查数据库
          return selectDataFromDB(activity,activityService,page,limit,state,true);
        }

        String activityList_page = null;

        for (String s:list){//假如缓存有
                result = RedisUtil.lGet("activityList_" + s + "_page_" + page + "_limit_" + limit, 0, -1);
                if(result!=null){ //长度等于0 也要返回 防止缓存穿透
                    RedisUtil.expire("activityList_" + s + "_page_" + page + "_limit_" + limit,60);//更新过期时间 一小时
                    return result;
                }
            }

        // 缓存没有查数据库
        return selectDataFromDB(activity,activityService,page,limit,state,false);
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
    public static List selectDataFromDB(ActivityForDetail activity,ActivityService activityService,int page, int limit,Integer state,boolean isNull) throws IllegalAccessException, NoSuchFieldException, InterruptedException {
        List result = null;
        ReentrantLock lock = new ReentrantLock();
        // 成功获取锁，则从数据库获取数据
        if(lock.tryLock()) {
            try {
                // 从数据库获取数据
                result = activityService.selectActivityDetailById(activity,page,limit,state);
                if (isNull){//id、clubId、userId一个都没传
                    setCacheByList("activityList_all_page_" + page + "_limit_" + limit, result, 10);
                }else {
                    Object o = result.get(0);
                    String id = getValueByObjectAndFieldName(o, "id");
                    String clubId = getValueByObjectAndFieldName(o, "clubId");
                    String userId = getValueByObjectAndFieldName(o, "userId");
                    // 更新缓存 长度等于0 也要返回 防缓存穿透
                    setCacheByList("activityList_" + id + "_page_" + page + "_limit_" + limit, result, 10);
                    setCacheByList("activityList_" + clubId + "_page_" + page + "_limit_" + limit, result, 10);
                    setCacheByList("activityList_" + userId + "_page_" + page + "_limit_" + limit, result, 10);
                }
            } finally {
                lock.unlock();
            }
        } else {    // 获取锁失败
            // 休眠100ms后，重新获取数据
            Thread.sleep(100);
            result = getCacheOfActivityList(activity, activityService, page, limit,state);
        }
        return result;
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

