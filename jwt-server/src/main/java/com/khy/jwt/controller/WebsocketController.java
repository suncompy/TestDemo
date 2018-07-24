package com.khy.jwt.controller;

import com.khy.jwt.dto.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebsocketController {

    /**
     * The @MessageMapping annotation ensures that if a message is sent to destination "/hello", then the greeting() method is called.
     * The return value is broadcast to all subscribers to "/topic/greetings" as specified in the @SendTo annotation.
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello") //浏览器发送请求通过@messageMapping 映射/hello 这个地址。
    @SendTo("/topic/greetings")  //服务器端有消息时,会向订阅@SendTo 中的路径的浏览器发送消息(广播)。
    public Greeting greeting(Greeting message, @Header("atytopic") String topic) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 广播给所有订阅者
     *
     * @return
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //第一个参数是浏览器订阅的地址，第二个参数是消息本身
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback";
    }

    /**
     * 点对点
     * @param principal
     * @param message
     */
    @MessageMapping("/chat")
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
    public void handleChat(Principal principal, Greeting message) {

        /**
         * 此处是一段硬编码。如果发送人是wyf 则发送给 wisely 如果发送人是wisely 就发送给 wyf。
         */
        if (principal.getName().equals("10086")) {
            //通过convertAndSendToUser 向用户发送信息,
            // 第一个参数是接收消息的用户,第二个参数是浏览器订阅的地址,第三个参数是消息本身

            messagingTemplate.convertAndSendToUser("10087",
                    "/topic/notifications", principal.getName() + "-send:"
                            + message.getName());
        } else {
            messagingTemplate.convertAndSendToUser("10086",
                    "/topic/notifications", principal.getName() + "-send:"
                            + message.getName());
        }
    }
}
