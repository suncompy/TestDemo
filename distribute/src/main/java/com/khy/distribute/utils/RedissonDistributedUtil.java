package com.khy.distribute.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 加锁和释放锁的方法
 */
@Component
public class RedissonDistributedUtil {

    private static final String LOCK_TITLE = "redisson_Lock_";

    /**
     * 加锁
     * @param redisson
     * @param lockName
     * @param seconds
     * @return
     */
    public static boolean acquire(RedissonClient redisson, String lockName, long seconds){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.lock(seconds, TimeUnit.SECONDS); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        System.err.println("======lock======"+Thread.currentThread().getName());
        return  true;
    }

    /**
     * 释放锁
     * @param redisson
     * @param lockName
     */
    public static void release(RedissonClient redisson, String lockName){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        if (mylock != null) {
            /*if(!mylock.isHeldByCurrentThread()){
                System.out.println("超时自动放锁.......");
            }*/
            mylock.unlock();
        }
        System.err.println("======unlock======"+Thread.currentThread().getName());
    }


}
