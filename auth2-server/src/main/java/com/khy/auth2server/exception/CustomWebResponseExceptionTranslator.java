package com.khy.auth2server.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        return this.handleOAuth2Exception(new CustomOauthException(e.getMessage(), e));
        /*Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception)this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return this.handleOAuth2Exception((OAuth2Exception)ase);
        } else {
            ase = (AuthenticationException)this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
            if (ase != null) {
                return this.handleOAuth2Exception(new CustomWebResponseExceptionTranslator.UnauthorizedException(e.getMessage(), e));
            } else {
                ase = (AccessDeniedException)this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
                if (ase instanceof AccessDeniedException) {
                    return this.handleOAuth2Exception(new CustomWebResponseExceptionTranslator.ForbiddenException(ase.getMessage(), ase));
                } else {
                    ase = (HttpRequestMethodNotSupportedException)this.throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
                    return ase instanceof HttpRequestMethodNotSupportedException ? this.handleOAuth2Exception(new CustomWebResponseExceptionTranslator.MethodNotAllowed(ase.getMessage(), ase)) : this.handleOAuth2Exception(new CustomWebResponseExceptionTranslator.ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
                }
            }
        }*/
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || e instanceof InsufficientScopeException) {
            headers.set("WWW-Authenticate", String.format("%s %s", "Bearer", e.getSummary()));
        }

        ResponseEntity<OAuth2Exception> response = new ResponseEntity(e, headers, HttpStatus.valueOf(status));
        return response;
    }


    private static class MethodNotAllowed extends OAuth2Exception {
        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "method_not_allowed";
        }

        public int getHttpErrorCode() {
            return 405;
        }
    }

    private static class UnauthorizedException extends OAuth2Exception {
        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }

        public int getHttpErrorCode() {
            return 401;
        }
    }

    private static class ServerErrorException extends OAuth2Exception {
        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "server_error";
        }

        public int getHttpErrorCode() {
            return 500;
        }
    }

    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "access_denied";
        }

        public int getHttpErrorCode() {
            return 403;
        }
    }
}
