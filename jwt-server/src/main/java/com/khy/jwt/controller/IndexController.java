package com.khy.jwt.controller;

import com.khy.jwt.utils.JwtUtil;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
public class IndexController {

    @RequestMapping("/hello")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public String hello(){
        log.info("come in");
        return "hello";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    Object helloToAdmin(String userId) {
        return "Hello World! You are ADMIN ";
    }

    @GetMapping("/normal")
    @PreAuthorize("hasAuthority('NORMAL')")
    public @ResponseBody
    Object helloToNormal(String userId) {
        return "Hello World! You are ADMIN ";
    }

    @GetMapping("/hello")
    public @ResponseBody
    Object hello(String userId) {
        return "Hello World! You have valid token";
    }

    /*@Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/login")
    public Object login() throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername("");
        String jwt = JwtUtil.generateToken(userDetails.getUsername());
    }*/


    /*@PostMapping("/login")
    public Object login(HttpServletResponse response,
                        @RequestBody Account account) throws IOException {
        if(isValidPassword(account)) {
            String jwt = JwtUtil.generateToken(account.username);
            return new HashMap<String,String>(){{
                put("token", jwt);
            }};
        }else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }*/

    /**
     * 注册用户 默认开启白名单
     * @param user
     */
    @PostMapping("/signup")
    /*public User signup(@RequestBody User user) {
        User bizUser = userRepository.findByUsername(user.getUsername());
        if(null != bizUser){
            throw new UsernameIsExitedException("用户已经存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));
        return userRepository.save(user);
    }*/

    private boolean isValidPassword(Account ac) {
        //we just have 2 hardcoded user
        if ("admin".equals(ac.username) && "admin".equals(ac.password)
                || "user".equals(ac.username) && "user".equals(ac.password)) {
            return true;
        }
        return false;
    }


    public static class Account {
        public String username;
        public String password;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
