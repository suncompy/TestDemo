package com.khy.jwt.exception;

/**
 * 描述：基础异常信息
 *
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

