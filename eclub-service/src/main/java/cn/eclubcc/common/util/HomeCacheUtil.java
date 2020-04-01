package cn.eclubcc.common.util;

import cn.eclubcc.service.impl.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:markeNick
 */
public class HomeCacheUtil extends RedisUtil{
    // 首页缓存基础时间 -- 1分钟
    public static final long BASE_EXPIRE = 60000;

    public static final int CLUBLIST_LIMIT = 10;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private static RedisUtil redisUtil;

    @Autowired
    private static HomeServiceImpl homeService;

    /**
     * 将缓存存入Redis
     * @param key
     * @param list
     * @param time  过期时间 单位：分钟
     * @return
     * author: markeNick
     */
    public static boolean setCacheByList(String key, List<Object> list, int time) {

        return redisUtil.lSet(key, list, BASE_EXPIRE * time);
    }

    /**
     * 从缓存获取数据，没有数据就去数据库获取
     * @param page
     * @return List<Object>
     * @throws InterruptedException
     * author: markeNick
     */
    public static List<Object> getCacheOfClubList(int page) throws InterruptedException{
        String clubList_page = "clubList-page" + page;

        // 从缓存中读取数据
        List<Object> result = redisUtil.lGet(clubList_page, 0, -1);

        if(result != null) {
            return result;
        }

        ReentrantLock lock = new ReentrantLock();
        // 成功获取锁，则从数据库获取数据
        if(lock.tryLock()) {
            // 从数据库获取数据
            result = homeService.queryClubListLimit(page, CLUBLIST_LIMIT);

            // 更新缓存
            if(result != null) {
                setCacheByList(clubList_page, result, 10);
            }

            lock.unlock();
        } else {    // 获取锁失败
            // 休眠100ms后，重新获取数据
            Thread.sleep(100);
            result = getCacheOfClubList(page);
        }

        return result;
    }
}