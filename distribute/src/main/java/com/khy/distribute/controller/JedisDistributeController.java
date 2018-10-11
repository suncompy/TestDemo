package com.khy.distribute.controller;

import com.khy.distribute.utils.JedisDistributeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class JedisDistributeController {
    private static int i = 50;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping(value = "/testjedis", method = RequestMethod.POST)
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
        String uuid = UUID.randomUUID().toString();
        JedisDistributeUtil.tryLock(redisTemplate, key, uuid, 10L);
        if(i>0){
            //Thread.sleep(1000L);
            i--;
        }
        System.out.println(i);
        JedisDistributeUtil.releaseLock(redisTemplate, key, uuid);
    }
}
