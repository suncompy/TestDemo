package com.khy.auth2server.config;

import com.khy.auth2server.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService sysUserService() { //注册UserServiceImpl的bean
        return new UserServiceImpl();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService()).passwordEncoder(new BCryptPasswordEncoder()); //添加我们自定义的user detail service认证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("==============SecurityConfiguration.configure(HttpSecurity http)");
        /*http
                .authorizeRequests()
                .anyRequest().permitAll();//所以资源*/

        http .authorizeRequests().antMatchers("/login").permitAll().and()
                // default protection for all resources (including /oauth/authorize)
                .authorizeRequests() .anyRequest().hasRole("ADMIN");
        // ... more configuration, e.g. for form login
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("secret"));
        System.out.println(bCryptPasswordEncoder.encode("10086"));
    }
}
