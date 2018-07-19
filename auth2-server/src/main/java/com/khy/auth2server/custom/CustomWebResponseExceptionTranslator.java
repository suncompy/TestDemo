package com.khy.auth2server.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * 用户名或者密码错误
 */
@Service("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    /*@Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new CustomOauthException(oAuth2Exception.getMessage()));
    }*/

    private static Logger logger = LoggerFactory.getLogger(CustomWebResponseExceptionTranslator.class);

    @Resource
    private MessageSource messageSource;

    private Locale locale = LocaleContextHolder.getLocale();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if(e instanceof InvalidTokenException){
            InvalidTokenException invalidTokenException = (InvalidTokenException) e;
            if("Token was not recognised".equals(invalidTokenException.getMessage())){
                return ResponseEntity
                        .status(invalidTokenException.getHttpErrorCode())
                        .body(new CustomOauthException(messageSource.getMessage("tokenWasNotRecognised", null, locale)));
            }
        }
        if(e instanceof OAuth2Exception){
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(new CustomOauthException(oAuth2Exception.getMessage()));
        }else if(e instanceof DisabledException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                    .body(new CustomOauthException(e.getMessage()));
        }else{
            return ResponseEntity
                    .status(500)
                    .body(new CustomOauthException(e.getMessage()));
        }
    }
}
