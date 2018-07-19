package com.khy.auth2server.config;

import com.khy.auth2server.custom.CustomAccessDeniedHandler;
import com.khy.auth2server.custom.CustomAuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    @Qualifier("customAccessDeniedHandler")
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    /*@Autowired
    @Qualifier("appLoginFailureHandler")
    private AuthenticationFailureHandler appLoginFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler appLoginInSuccessHandler;*/

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //resourceId 用于分配给可授予的clientId
        //stateless  标记以指示在这些资源上仅允许基于令牌的身份验证
        //tokenStore token的存储方式（上一章节提到）
        resources.resourceId(ResourceId.DEMO_RESOURCE_ID).stateless(true);//resources.resourceId(ResourcesIDs.USER_RESOURCE_ID).stateless(false);
        resources.authenticationEntryPoint(new CustomAuthExceptionEntryPoint());
        resources.accessDeniedHandler(customAccessDeniedHandler);
        /*.authenticationEntryPoint(authenticationEntryPoint())
        .accessDeniedHandler(accessDeniedHandler());*/

    }

    /**
     * Inject your custom exception translator into the OAuth2 {@link AuthenticationEntryPoint}.
     *
     * @return AuthenticationEntryPoint
     */
    /*@Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        final OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
        entryPoint.setExceptionTranslator(customWebResponseExceptionTranslator);
        return entryPoint;
    }*/

    /** Define your custom exception translator bean here *//*
    @Bean
    public WebResponseExceptionTranslator<?> exceptionTranslator() {
        return new ApiErrorWebResponseExceptionTranslator();
    }*/

    /**
     * Classic Spring Security stuff, defining how to handle {@link AccessDeniedException}s.
     * Inject your custom exception translator into the OAuth2AccessDeniedHandler.
     * (if you don't add this access denied exceptions may use a different format)
     *
     * @return AccessDeniedHandler
     */
    /*@Bean
    public AccessDeniedHandler accessDeniedHandler() {
        final OAuth2AccessDeniedHandler handler = new OAuth2AccessDeniedHandler();
        handler.setExceptionTranslator(customWebResponseExceptionTranslator);
        return handler;
    }*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*OAuth2AuthenticationProcessingFilter f = new OAuth2AuthenticationProcessingFilter();
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionTranslator(webResponseExceptionTranslator());
        f.setAuthenticationEntryPoint(oAuth2AuthenticationEntryPoint);
        *//*OAuth2AuthenticationManager o = new OAuth2AuthenticationManager();
        DefaultTokenServices dt = new DefaultTokenServices();
        dt.setTokenStore(tokenStore());
        o.setTokenServices(dt);
        f.setAuthenticationManager(o);*//*
        http.addFilterBefore(f, AbstractPreAuthenticatedProcessingFilter.class);*/

        System.out.println("ResourceServerConfiguration.configure(HttpSecurity http)");
        /*http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers()
                .antMatchers("/user/**")
                .and()
                .authorizeRequests()
                .antMatchers("/user/profile").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))");*/
//http.authorizeRequests().antMatchers("/**").authenticated();//全部都要认证
        http


                .authorizeRequests().antMatchers("/order/**").authenticated()//配置order访问控制，必须认证过后才可以访问
                .antMatchers("/forbidden").hasRole("KHY");

        //.anyRequest().authenticated();
    }

}

