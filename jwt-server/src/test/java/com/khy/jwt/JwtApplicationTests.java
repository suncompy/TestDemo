package com.khy.jwt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("test","redis");
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
        Assert.assertEquals("redis", stringRedisTemplate.opsForValue().get("test"));
    }
}
