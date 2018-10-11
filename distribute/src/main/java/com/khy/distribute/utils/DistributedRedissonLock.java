package com.khy.distribute.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 加锁和释放锁的方法
 */
@Component
public class DistributedRedissonLock {

    private static final String LOCK_TITLE = "redisson_Lock_";

    public static boolean acquire(RedissonClient redisson, String lockName){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.lock(2, TimeUnit.MINUTES); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        System.err.println("======lock======"+Thread.currentThread().getName());
        return  true;
    }

    public static void release(RedissonClient redisson, String lockName){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        if (mylock != null) {
            mylock.unlock();
        }
        System.err.println("======unlock======"+Thread.currentThread().getName());
    }


}
