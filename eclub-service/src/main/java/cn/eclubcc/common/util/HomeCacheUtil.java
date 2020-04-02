package cn.eclubcc.common.util;

import cn.eclubcc.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:markeNick
 */
@Slf4j
public class HomeCacheUtil extends RedisUtil{
    // 首页缓存基础时间 -- 1分钟
    public static final long BASE_EXPIRE = 60000;

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
        System.out.println("=====> setCacheByList");
        return RedisUtil.lSet(key, list, BASE_EXPIRE * time);
    }

    /**
     * 从缓存获取数据，没有数据就去数据库获取
     * @param page
     * @return List<Object>
     * @throws InterruptedException
     * author: markeNick
     */
    public static List getCacheOfClubList(int page, int limit, String category, HomeService homeService) throws InterruptedException{

        String clubList_page = "clubList_" + category + "_page_" + page + "_limit_" + limit;

        List result = null;
        result = RedisUtil.lGet(clubList_page, 0, -1);

        // 从缓存中读取数据
        if(result != null && result.size() > 0) {

            return result;
        }

        ReentrantLock lock = new ReentrantLock();
        // 成功获取锁，则从数据库获取数据
        if(lock.tryLock()) {

            try {
                // 从数据库获取数据
                result = homeService.queryClubListLimit(page, limit, category);

                // 更新缓存
                if(result != null && result.size() > 0) {

                    setCacheByList(clubList_page, result, 10);
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
}