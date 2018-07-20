package com.khy.jwt.utils;

import com.khy.jwt.exception.TokenException;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final long EXPIRATION_TIME = 3600000; // 1 hour
    private static final String SECRET = "jdowsf&Secret^#";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    public static final String ROLE = "USER_ROLE";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private static final String TIMESTAMP = "timestamp";

    public static String generateToken(Map<String, Object> user) {
        user.put(TIMESTAMP, new Date().getTime());
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
        if (StringUtils.isBlank(token) || !token.startsWith(TOKEN_PREFIX))
            throw new TokenException("Token为空或者格式不对");
        // 当token无效时，解析token异常
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new TokenException("Token已过期");
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            throw new TokenException("Token格式错误");
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new TokenException("Token没有被正确构造");
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            throw new TokenException("签名失败");
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new TokenException("非法参数异常");
        }
    }
}
