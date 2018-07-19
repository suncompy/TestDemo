package com.khy.jwt.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static final long EXPIRATION_TIME = 3600000; // 1 hour
    public static final String SECRET = "jdowsf&Secret^#";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_INFO = "userInfo";

    public static String generateToken(Map<String, Object> user) {
        user.put("timestamp", new Date().getTime());
        String jwt = Jwts.builder()
                .setSubject("{\"alg\":\"RS256\", \"typ\":\"JWT\"}")
                .setClaims(user)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + " " + jwt;
    }

    public static Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null)
            throw new TokenValidationException("Missing token");
        // parse the token. exception when token is invalid
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        return body;
    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}
