
package com.khy.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.khy.jwt.config.WebSecurityConfig;
import com.khy.jwt.exception.TokenException;
import com.khy.jwt.service.impl.GrantedAuthorityImpl;
import com.khy.jwt.utils.JwtUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 * token的校验和解析
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("进入doFilterInternal过滤器进行token的校验和解析");
        System.out.println(request.getServletPath());
        if(isProtectedUrl(request)){
            System.out.println("============"+request.getHeader("Authorization"));
            chain.doFilter(request, response);
        }else {
            Map<String, Object> parseToken = JwtUtil.validateTokenAndGetClaims(request);
            String[] roles = {};
            try {
                roles = JSON.parseObject(MapUtils.getString(parseToken, JwtUtil.ROLE), String[].class);
            } catch (Exception e) {
                log.error("解析用户header中的token失败，token = " + JSON.toJSONString(parseToken));
                throw new TokenException("Token格式错误");
            }
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            for (int i = 0, j = roles.length; i < j; i++) {
                authorities.add(new GrantedAuthorityImpl(roles[i]));
            }
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            MapUtils.getString(parseToken, JwtUtil.USERNAME), null, authorities
                    ));
            chain.doFilter(request, response);
        }

    }

    private static final PathMatcher pathMatcher = new AntPathMatcher();
    //我们只对地址 /api 开头的api检查jwt. 不然的话登录/login也需要jwt
    private boolean isProtectedUrl(HttpServletRequest request) {
        for(String s : WebSecurityConfig.AUTH_WHITELIST){
            if(pathMatcher.match(s, request.getServletPath())){
                return true;
            }
        }
        return false;
    }
}
