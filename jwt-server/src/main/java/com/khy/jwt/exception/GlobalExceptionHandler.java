package com.khy.jwt.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误。
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, "请求映射处理错误", headers, status, request);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, TokenException.class})
    @ResponseBody
    public String jsonHandle(HttpServletRequest request, HttpServletResponse response) {
        return "this is error exception";

    }
}
