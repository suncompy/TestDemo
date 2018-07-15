/*
package com.khy.auth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration("myAuthenticationManager")
public class MyAuthenticationManager implements AuthenticationManager {
    @Autowired
    @Qualifier("daoAuhthenticationOauthProvider")
    private AuthenticationProvider daoAuhthenticationOauthProvider;

    @Bean(name = "daoAuhthenticationOauthProvider")
    public AuthenticationProvider daoAuhthenticationOauthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsOauthService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return daoAuhthenticationOauthProvider.authenticate(authentication);
    }
}
*/
