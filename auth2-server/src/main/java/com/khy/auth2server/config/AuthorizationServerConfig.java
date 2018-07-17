package com.khy.auth2server.config;

import com.khy.auth2server.exception.CustomWebResponseExceptionTranslator;
import com.khy.auth2server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /*@Autowired
    private RedisConnectionFactory connectionFactory;*/

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyClientDetailsService myClientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator(){
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity translate(Exception e) throws Exception {
                ResponseEntity responseEntity = super.translate(e);
                OAuth2Exception body = (OAuth2Exception) responseEntity.getBody();
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                // do something with header or response
                if(401==responseEntity.getStatusCode().value()){
                    return new ResponseEntity("faile 401", headers, responseEntity.getStatusCode());
                }else{
                    return new ResponseEntity(body, headers, responseEntity.getStatusCode());
                }

            }
        };

        //return new CustomWebResponseExceptionTranslator();
    }

    @Bean // 声明TokenStore实现
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new MyTokenEnhancer();
    }

    /*@Bean // 声明 ClientDetails实现
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }*/

    /**
     * 配置令牌端点(Token Endpoint)的安全约束.
     * <p>
     * 创建客户端身份认证核心过滤器ClientCredentialsTokenEndpointFilter
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        System.out.println("=========================three");
        //super.configure(security);
        //主要是让/oauth/token支持client_id以及client_secret作登录认证
        security.allowFormAuthenticationForClients();
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里
     * 或者是通过数据库来存储调取详情信息。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        System.out.println("ClientDetailsServiceConfigurer");
        clients.withClientDetails(myClientDetailsService);
//        clients.inMemory() // 使用in-memory存储
//                .withClient("my-client-1") //（必须的）用来标识客户的Id。
//                .secret("$2a$10$0jyHr4rGRdQw.X9mrLkVROdQI8.qnWJ1Sl8ly.yzK0bp06aaAkL9W") //（需要值得信任的客户端）客户端安全码，如果有的话。
//                .authorizedGrantTypes("authorization_code", "refresh_token") // 该client允许的授权类型,默认为空。
//                .scopes("read", "write", "execute") //用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
//                .redirectUris("http://localhost:8081/login/oauth2/code/callback");
    }


    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        System.out.println("AuthorizationServerEndpointsConfigurer");
        //认证管理器，当你选择了资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(userService);
        endpoints.tokenStore(tokenStore());
        endpoints.setClientDetailsService(myClientDetailsService);
        endpoints.tokenEnhancer(tokenEnhancer());
        endpoints.exceptionTranslator(webResponseExceptionTranslator());

        //配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        //ClientDetailsService中配置了时间后此处配置无效
        //tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
        endpoints.tokenServices(tokenServices);
    }

    /*@Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }*/

    /*@Bean // 声明 ClientDetails实现
    public ClientDetailsService clientDetails() {
        return new MyClientDetailsService();
    }*/


    public static void main(String[] args) {
        System.out.println(new org.apache.tomcat.util.codec.binary.Base64().encodeAsString("my-client-1:12345678".getBytes()));
        System.out.println(java.util.Base64.getEncoder().encodeToString("my-client-1:12345678".getBytes()));
    }
}
