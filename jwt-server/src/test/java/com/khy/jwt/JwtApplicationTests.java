package com.khy.jwt;

import com.alibaba.fastjson.JSON;
import com.khy.jwt.entity.JwtUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    //测试redis
    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("test","redis");
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
        Assert.assertEquals("redis", stringRedisTemplate.opsForValue().get("test"));
    }

    @Test
    public void testSetRedisObject(){
        JwtUser user = new JwtUser();
        user.setUsername("10086");
        user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
        redisTemplate.opsForValue().set("testObject", user, 10L, TimeUnit.SECONDS);
    }

    @Test
    public void testGetRedisObject(){
        JwtUser user = (JwtUser) redisTemplate.opsForValue().get("testObject");
        System.out.println(user);
        System.out.println(JSON.toJSONString(user));
    }
}
