package com.khy.jwt.service.impl;

import com.khy.jwt.entity.JwtUser;
import com.khy.jwt.entity.Role;
import com.khy.jwt.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*模拟数据库操作*/
        JwtUser user = new JwtUser();
        user.setUsername("10086");
        user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        roles.add(role);

        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ADMIN1");
        roles.add(role1);

        user.setRoles(roles);
        return user;

        /*SysUser user = sysUserRepository.findByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;*/
    }

}
