package com.khy.jwt.controller;

import com.khy.jwt.entity.JwtUser;
import com.khy.jwt.service.UserService;
import com.khy.jwt.utils.NetworkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@Api(value = "IndexController", description = "indexController测试接口")
public class IndexController {

    @RequestMapping("/hello")
    @ApiOperation(value = "获取hello", notes = "测试当前用户仅仅登录但是没有授权")
    @ApiImplicitParam(paramType = "path", name = "cusId", required = true, value = "客户ID", dataType = "string")
    //@PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public String hello() throws Exception {
        log.info("come in");
        //throw new Exception("index controller of exception");
        return "hello";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "获取ADMIN信息", notes = "根据客户ID获取客户信息")
    public @ResponseBody
    Object helloToAdmin(String userId) {
        return "Hello World! You are ADMIN ";
    }

    @GetMapping("/normal")
    @PreAuthorize("hasAuthority('NORMAL')")
    public @ResponseBody
    Object helloToNormal(String userId) {
        return "Hello World! You are NORMAL ";
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
     *
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


    //测试缓存


    @Autowired
    private UserService userService;

    /**
     * 根据ID获取信息
     *
     * @param id
     * @return
     */
    @GetMapping("/testCache/{id}")
    public JwtUser test(@PathVariable("id") String id) {
        return userService.get(id, "khy");
    }

    @GetMapping("/test/Time")
    public JwtUser testCacheTime() {
        return userService.get();
    }

    /**
     * 删除某个ID的信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") String id) {
        return userService.delete(id);
    }

    /**
     * 保存某个ID的信息
     *
     * @param id
     * @return
     */
    @PostMapping
    public String save(@RequestParam("id") String id, @RequestParam("value") String value) {
        return userService.save(id, value);
    }

    /**
     * 跟新某个ID的信息
     *
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public String update(@PathVariable("id") String id, @RequestParam("value") String value) {
        return userService.update(id, value);
    }

    @GetMapping("/common/testip")
    public String testip(HttpServletRequest request) throws IOException {
        return NetworkUtils.getIpAddress(request);
    }
}
