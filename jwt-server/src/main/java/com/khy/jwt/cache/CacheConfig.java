package com.khy.jwt.cache;

import com.khy.jwt.entity.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Return the cache manager bean to use for annotation-driven cache management. A default CacheResolver will be
     * initialized behind the scenes with this cache manager. For more fine-grained management of the cache resolution,
     * consider setting the CacheResolver directly
     * <p>
     * https://blog.csdn.net/sy793314598/article/details/80719224
     *
     * @return
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置

        //设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
//        ClassLoader loader = this.getClass().getClassLoader();
//        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);

        config = config.entryTtl(Duration.ofMinutes(1))     // 设置缓存的默认过期时间，也是使用Duration设置
                .disableCachingNullValues();     // 不缓存空值
        //.serializeValuesWith(pair)
        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", config);
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)     // 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }

    /**
     * 自定义key生成策略
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(o.getClass().getName());
                stringBuilder.append(".");
                stringBuilder.append(method.getName());
                stringBuilder.append("[");
                for (Object obj : objects) {
                    stringBuilder.append(obj.toString());
                }
                stringBuilder.append("]");

                return stringBuilder.toString();
            }
        };
    }

    /**
     * 自定义cache异常信息,当发生异常时会直接执行方法;如果不重写该异常,则发生异常时不会执行方法
     * @return
     */
    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
                logger.error("从缓存中获取值失败, key="+o.toString());
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
                logger.error("往缓存中放值失败, key="+o.toString());
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
                logger.error("从缓存中删值失败, key="+o.toString());
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                logger.error("清除缓存失败");
            }
        };
    }

    public static void main(String[] args) {
        System.out.println(JwtUser.class.getSimpleName());
        System.out.println(JwtUser.class.getName());
    }
}
