package com.khy.jwt.exception;

import com.alibaba.fastjson.JSONException;
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

    /*@ExceptionHandler(value = {TokenException.class})
    @ResponseBody
    public String tokenHandle(HttpServletRequest request, HttpServletResponse response) {
        return "this is token error exception";

    }

    @ExceptionHandler(value = {JSONException.class})
    @ResponseBody
    public String jsonHandle(HttpServletRequest request, HttpServletResponse response) {
        return "this is json error exception";
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public String runtimeHandle(HttpServletRequest request, HttpServletResponse response) {
        return "this is runtime error exception";

    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public String globalHandle(HttpServletRequest request, HttpServletResponse response) {
        return "this is global error exception";

    }*/
}
