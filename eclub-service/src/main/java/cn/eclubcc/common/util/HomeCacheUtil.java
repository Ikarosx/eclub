package cn.eclubcc.common.util;

import cn.eclubcc.service.HomeService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:markeNick
 */
@Slf4j
public class HomeCacheUtil extends RedisUtil{
    // 首页缓存基础时间 -- 1分钟
    public static final long BASE_EXPIRE = 6000;

    /**
     * 从缓存获取数据，没有数据就去数据库获取
     * @param page  当前页
     * @param limit 限制数
     * @param category  社团类别
     * @param homeService
     * @return
     * @throws InterruptedException
     *
     * author: markeNick
     */
    public static Map<String, Object> getCacheOfClubList(int page, int limit, String category, HomeService homeService) throws InterruptedException{

        String clubList_page = "clubList_" + category + "_page_" + page + "_limit_" + limit;
        String clubList_count = "clubList_" + category + "_count";

        Map<String, Object> result = new HashMap<>();
        long count;
        List list = null;

        // 从缓存中读取数据
        list = RedisUtil.lGet(clubList_page, 0, -1);

        String countStr = (String) RedisUtil.get(clubList_count);
        count = Long.parseLong(countStr == null ? "0" : countStr) ;

        if(list != null && list.size() > 0) {

            result.put("list", list);
            result.put("count", count);
            return result;
        }

        ReentrantLock lock = new ReentrantLock();
        // 成功获取锁，则从数据库获取数据
        if(lock.tryLock()) {

            try {
                // 从数据库获取数据
                list = homeService.queryClubListLimit(page, limit, category);
                count = homeService.queryClubCountByCategory(category);
                result.put("list", list);
                result.put("count", count);

                // 更新缓存
                if(list != null && list.size() > 0) {

                    setCacheByList(clubList_page, list, 10);
                    setCacheByString(clubList_count, count, 10);
                } else {    // 防止缓存击穿
                    setCacheByList(clubList_page, null, 1);
                    setCacheByString(clubList_count, "null", 1);
                }
            } finally {
                lock.unlock();
            }
        } else {    // 获取锁失败
            // 休眠100ms后，重新获取数据
            Thread.sleep(100);
            result = getCacheOfClubList(page, limit, category, homeService);
        }

        return result;
    }

    /**
     * 将List缓存存入Redis
     * @param key
     * @param list
     * @param time  过期时间 单位：分钟
     * @return
     *
     * author: markeNick
     */
    public static boolean setCacheByList(String key, List list, int time) {
        return RedisUtil.lSet(key, list, BASE_EXPIRE * time);
    }

    /**
     * 将Object缓存存入Redis
     * @param key
     * @param object
     * @param time
     * @return
     *
     * author: markeNick
     */
    public static boolean setCacheByString(String key, Object object, int time) {
        return RedisUtil.set(key, object, BASE_EXPIRE * time);
    }
}