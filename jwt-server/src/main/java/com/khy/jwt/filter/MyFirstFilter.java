package com.khy.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.khy.jwt.vo.RestfulResponse;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter");
        try {
            servletRequest.setCharacterEncoding("UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RuntimeException e) {
            PrintWriter out = servletResponse.getWriter();
            String error = e.getMessage().substring(e.getMessage().indexOf(":") + 1);
            out.write(JSON.toJSONString(new RestfulResponse<>(false, error, 410, null)));
            out.flush();
            out.close();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {

    }
}
