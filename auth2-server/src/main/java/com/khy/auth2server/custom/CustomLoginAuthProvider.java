/*
package com.khy.auth2server.custom;

import com.khy.auth2server.entity.SysUser;
import com.khy.auth2server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;

@Component
public class CustomLoginAuthProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomLoginAuthProvider.class);
    @Resource
    private UserService userDetailsService;

    @Resource
    private MessageSource messageSource;

    private Locale locale = LocaleContextHolder.getLocale();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("authenticate");

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        SysUser sysUser = (SysUser) userDetailsService.loadUserByUsername(username);
        if (null == sysUser){
            throw new UsernameNotFoundException(messageSource.getMessage("usernameNotFound", null, locale));
        }
        if (!sysUser.isEnabled()){
            throw new DisabledException(messageSource.getMessage("accountDisabled", null, locale));
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);

        if(!encoder.matches(password, sysUser.getPassword())){
            throw new InvalidGrantException(messageSource.getMessage("passwordError", null, locale));
        }

        Collection<? extends GrantedAuthority> authorities = sysUser.getAuthorities();
        return new UsernamePasswordAuthenticationToken(sysUser, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
*/
