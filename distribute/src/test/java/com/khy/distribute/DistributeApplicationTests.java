package com.khy.distribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributeApplicationTests {

   /* @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(stringRedisTemplate.getConnectionFactory());
    }*/

    @Autowired
    private RedissonClient redisson;

    @Test
    public void redisson() {
		/*String user_id="1";
        String key=user_id+"_key";
        //获取锁
        RLock lock = redisson.getLock(key);
        lock.lock();
        //执行具体逻辑...

        RBucket<Object> bucket = redisson.getBucket("a");
        bucket.set("bb");

        lock.unlock();*/

        RBucket<Object> bucket = redisson.getBucket("khyaaaa");
        bucket.set("khyaaaa");
        //redisson.shutdown();
        RBucket<Object> bucket1 = redisson.getBucket("khybbbb");
        bucket1.set("khybbbb");
        System.out.println(bucket.size());
        System.out.println(bucket.get());


    }


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DistributeApplication.class);
        ConfigurableApplicationContext context = app.run(args);
        System.out.println(context.getEnvironment().getProperty("idleConnectionTimeout"));
        context.close();
    }
}
