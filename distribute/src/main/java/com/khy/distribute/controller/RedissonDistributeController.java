package com.khy.distribute.controller;

import com.khy.distribute.utils.RedissonDistributedUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class RedissonDistributeController {
    private static int i = 50;

    @Autowired
    private RedissonClient redisson;

    @RequestMapping(value = "/testRedisson", method = RequestMethod.POST)
    public String test() {
        testLock();
        return "success";
    }

    @Async
    public void testLock() {
        for(int i =0, j = 100; i < j; i++){
            new Thread(()->{
                try {
                    sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 可重入锁（同步方法）
     * @throws InterruptedException
     */
    public void sub() throws InterruptedException {
        String key = "one";
        RedissonDistributedUtil.acquire(redisson, key, 5);
        if(i>0){
            //Thread.sleep(1000L);
            i--;
        }
        System.out.println(i);
        RedissonDistributedUtil.release(redisson, key);
    }

    /**
     * 测试可重入锁（异步方法）
     * @return
     */
    @RequestMapping(value = "/testRedissonAsync", method = RequestMethod.POST)
    public String testRedissonAsync() {
        System.out.println("1============" + LocalDateTime.now());
        RLock lock = redisson.getLock("anyLock");
        System.out.println("2============" + LocalDateTime.now());
        lock.lockAsync(10, TimeUnit.SECONDS);
        System.out.println("3============" + LocalDateTime.now());
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("4============" + LocalDateTime.now());
        lock.unlockAsync();
        System.out.println("5============" + LocalDateTime.now());
        return "success";
    }
}
