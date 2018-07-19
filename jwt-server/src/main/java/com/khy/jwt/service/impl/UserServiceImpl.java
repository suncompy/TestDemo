package com.khy.jwt.service.impl;

import com.khy.jwt.entity.JwtUser;
import com.khy.jwt.entity.Role;
import com.khy.jwt.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*模拟数据库操作*/
        JwtUser user = new JwtUser();
        user.setUsername("10086");
        user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
        List<Role> roles = Collections.emptyList();
        Role sysRole = new Role();
        sysRole.setId(1L);
        sysRole.setName("ADMIN");
        user.setRoles(roles);
        return user;

        /*SysUser user = sysUserRepository.findByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;*/
    }

}
