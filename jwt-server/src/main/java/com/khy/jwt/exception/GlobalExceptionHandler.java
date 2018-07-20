package com.khy.jwt.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理全局异常，但是过滤器中的异常处理不了
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, TokenException.class})
    @ResponseBody
    public String jsonHandle(HttpServletRequest request, HttpServletResponse response) {
        return "this is error exception";

    }
}
