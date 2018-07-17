package com.khy.auth2server.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义登录失败异常信息
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    public CustomOauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOauthException(String msg) {
        super(msg);
    }


}
