package com.khy.auth2server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAuthorizationServer
@MapperScan("com.khy.auth2server.dao")
@EnableTransactionManagement
public class Auth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Auth2ServerApplication.class, args);
    }
}
