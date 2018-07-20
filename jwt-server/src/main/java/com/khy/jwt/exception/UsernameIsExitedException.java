package com.khy.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户存在异常
 */
public class UsernameIsExitedException extends AuthenticationException {

    public UsernameIsExitedException(String msg) {
        super(msg);
    }

    public UsernameIsExitedException(String msg, Throwable t) {
        super(msg, t);
    }
}