package com.khy.auth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里
     * 或者是通过数据库来存储调取详情信息。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcTokenStore
        clients.cl // 使用in-memory存储
                .withClient("my-client-1") //（必须的）用来标识客户的Id。
                .secret("$2a$10$0jyHr4rGRdQw.X9mrLkVROdQI8.qnWJ1Sl8ly.yzK0bp06aaAkL9W") //（需要值得信任的客户端）客户端安全码，如果有的话。
                .authorizedGrantTypes("authorization_code", "refresh_token") // 该client允许的授权类型,默认为空。
                .scopes("read", "write", "execute") //用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
                .redirectUris("http://localhost:8081/login/oauth2/code/callback");
//                .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }


    public static void main(String[] args) {
        System.out.println(new org.apache.tomcat.util.codec.binary.Base64().encodeAsString("my-client-1:12345678".getBytes()));
        System.out.println(java.util.Base64.getEncoder().encodeToString("my-client-1:12345678".getBytes()));
    }
}
