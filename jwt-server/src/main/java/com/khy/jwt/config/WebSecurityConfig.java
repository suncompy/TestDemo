package com.khy.jwt.config;

import com.khy.jwt.filter.JWTAuthenticationFilter;
import com.khy.jwt.filter.JWTLoginFilter;
import com.khy.jwt.filter.MyFirstFilter;
import com.khy.jwt.service.UserService;
import com.khy.jwt.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JWTLoginFilter，JWTAuthenticationFilter组合在一起
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 需要放行的URL
     */
    public static final String[] AUTH_WHITELIST = {
            // -- register url
            "/signup",
            "/common/**",
            "/websocket*.html",
            "/main.css",
            "/js/**",
            "/gs-guide-websocket/**",
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/webjars/**",
            "favicon.ico"
            // other public endpoints of your API may be appended to this array
    };

    @Bean
    UserService jwtUserService() { //注册UserServiceImpl的bean
        return new UserServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 设置 HTTP 验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 由于使用的是JWT，我们这里不需要csrf(跨站请求伪造)
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 允许对于网站静态资源的无授权访问
                .antMatchers(AUTH_WHITELIST).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                //将我们定义的JWT方法加入SpringSecurity的处理流程中。
                .addFilterBefore(new MyFirstFilter(), JWTLoginFilter.class)
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();// 设置注销成功后跳转页面，默认是跳转到登录页面;

        // 禁用缓存
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(jwtUserService()).passwordEncoder(new BCryptPasswordEncoder()); //添加我们自定义的user detail service认证
        auth.authenticationProvider(new CustomAuthenticationProvider(jwtUserService(), new BCryptPasswordEncoder()));
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/static/**");//忽略静态资源的拦截
    }*/
}
