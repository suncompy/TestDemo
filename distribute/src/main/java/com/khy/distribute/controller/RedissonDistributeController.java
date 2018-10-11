package com.khy.distribute.controller;

import com.khy.distribute.utils.DistributedRedissonUtil;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedissonDistributeController {
    private static int i = 50;

    @Autowired
    private RedissonClient redisson;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
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

    public void sub() throws InterruptedException {
        String key = "one";
        DistributedRedissonUtil.acquire(redisson, key);
        if(i>0){
            //Thread.sleep(1000L);
            i--;
        }
        System.out.println(i);
        DistributedRedissonUtil.release(redisson, key);
    }

}
