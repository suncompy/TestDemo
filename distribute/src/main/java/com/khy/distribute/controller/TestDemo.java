package com.khy.distribute.controller;

import com.khy.distribute.utils.DistributedRedissonLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDemo {
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
        for(int i =0, j = 10; i < j; i++){
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
        DistributedRedissonLock.acquire(redisson, key);
        if(i>0){
            //Thread.sleep(5000L);
            i--;
        }
        System.out.println(i);
        DistributedRedissonLock.release(redisson, key);
    }

}
