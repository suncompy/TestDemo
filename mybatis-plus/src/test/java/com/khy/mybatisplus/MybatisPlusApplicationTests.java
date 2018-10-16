package com.khy.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.khy.mybatisplus.entity.User;
import com.khy.mybatisplus.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    public void selectUserOne() {
        User user = userService.selectById(1);
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void selectUser() {
        QueryWrapper<User> qwS = new QueryWrapper<>();
        qwS.eq("phone_number", "13800138009");
        User user = (User) userService.selectOne(qwS);
        System.out.println(JSON.toJSONString(user));
    }


    @Test
    public void selectPage() {
        /*User user = userService.selectById(1);
        System.out.println(JSON.toJSONString(user));*/
        Page page = userService.selectUserPage();
        System.out.println(JSON.toJSONString(page));

    }
}
