package com.khy.distribute.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/qq_36510261/article/details/78962081
 * Redis 从2.6.12版本开始 set 命令支持 NX 、 PX 这些参数来达到 setnx 、 setex 、 psetex 命令的效果
 */
@Slf4j
@Service
public class JedisDistributeUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JedisDistributeUtil.class);

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX"; //表示只有当锁定资源不存在的时候才能 SET 成功
    private static final String SET_WITH_EXPIRE_TIME = "EX"; //expire 表示锁定的资源的自动过期时间，单位是秒
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /*@Autowired
    private StringRedisTemplate redisTemplate;*/

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     *
     * 支持重复，线程安全
     *
     * @param lockKey   加锁键
     * @param clientId  加锁客户端唯一标识(采用UUID)
     * @param seconds   锁过期时间
     * @return
     */
    public static Boolean tryLock(StringRedisTemplate redisTemplate, String lockKey, String clientId, long seconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, seconds);
            if (LOCK_SUCCESS.equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }


    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     *
     * 支持重复，线程安全
     *
     * @param lockKey   加锁键
     * @param clientId  加锁客户端唯一标识(采用UUID)
     * @param seconds   锁过期时间
     * @param timeout   超时时间（单位：秒）
     * @return
     */
    public static Boolean tryLock(StringRedisTemplate redisTemplate, long timeout, String lockKey, String clientId, long seconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            //系统计时器的当前值，以毫微秒为单位。
            long nano = System.nanoTime();
            do{
                LOGGER.debug("try lock key: " + lockKey);
                String result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, seconds);
                if (LOCK_SUCCESS.equals(result)) {
                    return Boolean.TRUE;
                }
                // 存在锁,循环等待锁
                if (timeout <= 0) {  // 没有设置超时时间，直接退出等待
                    break;
                }
                try {
                    Thread.sleep(30L);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }while ((System.nanoTime() - nano) < TimeUnit.SECONDS.toNanos(timeout));
            return Boolean.FALSE;
        });
    }

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @param clientId
     * @return
     */
    public static Boolean releaseLock(StringRedisTemplate redisTemplate, String lockKey, String clientId) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),
                    Collections.singletonList(clientId));
            if (RELEASE_SUCCESS.equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }
}
