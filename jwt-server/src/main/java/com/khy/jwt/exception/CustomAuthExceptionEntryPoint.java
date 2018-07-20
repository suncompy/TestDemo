package com.khy.jwt.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义CustomAuthExceptionEntryPoint用于tokan校验失败返回信息
 */
public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {


    /**
     * 未登录或无权限时触发的操作 返回 {"code":401,"message":"小弟弟，你没有携带 token 或者 token 无效！","data":""}
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        httpServletResponse.getWriter().println("{\"code\":401,\"message\":\"小弟弟，你没有携带 token 或者 token 无效！\",\"data\":\"\"}");
        httpServletResponse.getWriter().flush();
    }


    /*@Override
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
    }*/


}
