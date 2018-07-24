package com.khy.jwt.config;

import com.alibaba.fastjson.JSON;
import com.khy.jwt.exception.TokenException;
import com.khy.jwt.service.impl.GrantedAuthorityImpl;
import com.khy.jwt.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;
import java.util.Map;

/**
 * 开启使用STOMP协议来传输基于代理(message broker)的消息,此时支持使用@MessageMapping 就像支持@RequestMapping一样。
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     * @return
     */
    /*@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }*/

    /**
     * 配置消息代理(message broker)
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //广播式配置一个/topic 消息代理
        config.enableSimpleBroker("/topic");
        //定义服务端接受客户端发送信息的地址的前缀
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * endPoint 注册协议节点,并映射指定的URl
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个Stomp 协议的endpoint,并指定 SockJS协议
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    /**
     * 此处拦截首次连接(CONNECT),并进行token验证,同时把用户信息注入StompHeaderAccessor,这样才能够实现点对点通信.
     *
     * UsernamePasswordAuthenticationToken是Principal的实现类.
     *
     * websocket只有在握手的时候才会经过MyFirstFilter,JWTLoginFilter,JWTAuthenticationFilter这三个过滤器,当连接成功
     * (即握手成功)后,后续的信息传输(包括send,disconnect)都不再经过以上过滤器,只会被该拦截器拦截
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                //1. 判断是否首次连接请求
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String jwtToken = accessor.getFirstNativeHeader("Authorization");
                    log.debug("webSocket token is {}", jwtToken);
                    Map<String, Object> parseToken = JwtUtil.validateTokenAndGetClaims(jwtToken);
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
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            MapUtils.getString(parseToken, JwtUtil.USERNAME), null, authorities
                    );
                    //SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    //验证成功,登录
                    //Authentication user = new Authentication(""); // access authentication header(s)}
                    accessor.setUser(usernamePasswordAuthenticationToken);
                    return message;
                }
                return message;
            }
        });
    }
}
