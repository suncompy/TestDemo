package com.khy.auth2server.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义CustomAuthExceptionEntryPoint用于tokan校验失败返回信息
 */
public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        Map map = new HashMap();
        map.put("is_error", "true");
        map.put("code", "401");//tokan校验失败
        map.put("message", authException.getMessage());
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
