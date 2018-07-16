package com.khy.auth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {



    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //resourceId 用于分配给可授予的clientId
        //stateless  标记以指示在这些资源上仅允许基于令牌的身份验证
        //tokenStore token的存储方式（上一章节提到）
        resources.resourceId(ResourceId.DEMO_RESOURCE_ID).stateless(true);//resources.resourceId(ResourcesIDs.USER_RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
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

        http.authorizeRequests().antMatchers("/**").authenticated()
                .anyRequest().authenticated();
    }

}

