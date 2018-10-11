package com.khy.distribute.distributed;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;

/**
 * redission工厂配置类
 */

@Configuration
//@PropertySource("classpath:redisson-dev.yaml")
public class RedissonFactory {

    @Autowired
    private Environment env;

    /**
     * 哨兵模式自动装配
     *
     * @return
     */

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.enable.sentinel", havingValue = "true")
    RedissonClient redissonSentinel() {
    /*Config config = new Config();
            SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redssionProperties.getSentinelAddresses())
                    .setMasterName(redssionProperties.getMasterName())
                    .setTimeout(redssionProperties.getTimeout())
                    .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                    .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());

            if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
                serverConfig.setPassword(redssionProperties.getPassword());
            }
            return Redisson.create(config);*/
        return null;
    }

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "redisson.enable.single", havingValue = "true")
    RedissonClient redissonSingle() throws IOException {
        //环境判断
        /*String[] profiles = env.getActiveProfiles();
        String profile = "";
        if (profiles.length > 0) {
            profile = "-" + profiles[0];
        }
        Resource resource = new ClassPathResource("redisson" + profile + ".yaml");
        System.out.println(resource.exists());
        System.out.println(((ClassPathResource) resource).getPath());
        System.out.println(resource.getURL());
        return Redisson.create(Config.fromYAML(resource.getInputStream()));*/

        //创建配置
        Config config = new Config();
        //threads（线程池数量）
        config.setThreads(0);
        //Netty线程池数量
        config.setNettyThreads(0);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //指定使用单节点部署方式
        singleServerConfig.setAddress("redis://192.168.7.68:6379");
        //设置密码
        singleServerConfig.setPassword(null);

        //创建客户端(发现这一非常耗时，基本在2秒-4秒左右)
        return Redisson.create(config);
    }


    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     *
     * @return
     */
    /*@Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        RedissLockUtil.setLocker(locker);
        return locker;
    }*/
}
