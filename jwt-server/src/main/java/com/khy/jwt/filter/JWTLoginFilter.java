package com.khy.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khy.jwt.entity.JwtUser;
import com.khy.jwt.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.khy.jwt.utils.JwtUtil.USER_INFO;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // 注入用户的信息
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        log.debug("根据token注入用户权限信息");
        Map<String, Object> claims = null;
        try {
            claims = JwtUtil.validateTokenAndGetClaims(req);
            String userStr = String.valueOf(claims.get(USER_INFO));
            JwtUser user = new ObjectMapper().readValue(userStr, JwtUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            user.getAuthorities())
            );
        } catch (IOException e) {
            log.error("根据token注入用户权限信息错误，claims=" + JSON.toJSONString(claims));
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        log.debug("用户成功登录后,编辑token");
        // builder the token
        String token = null;
        PrintWriter out = response.getWriter();
        Map<String, String> map = new HashMap<>();
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            // 定义存放角色集合的对象
            List<String> roleList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roleList.add(grantedAuthority.getAuthority());
            }
            HashMap<String, Object> user = new HashMap<>();
            user.put("username", auth.getName());
            user.put("roles", JSON.toJSONString(roleList));
            token = JwtUtil.generateToken(user);
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", token);
            map.put("is_error", "false");
            map.put("message", "success");
            out.write(JSON.toJSONString(map));
        } catch (Exception e) {
            map.put("is_error", "false");
            map.put("message", "success");
            out.write(JSON.toJSONString(map));
            log.error("用户成功登录后,编码token失败");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //我们只对地址 /api 开头的api检查jwt. 不然的话登录/login也需要jwt
    /*private boolean isProtectedUrl(HttpServletRequest request) {
        return pathMatcher.match("/api/**", request.getServletPath());
    }*/
}
