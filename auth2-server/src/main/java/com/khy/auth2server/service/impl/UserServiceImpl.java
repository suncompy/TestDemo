package com.khy.auth2server.service.impl;

import com.khy.auth2server.entity.SysRole;
import com.khy.auth2server.entity.SysUser;
import com.khy.auth2server.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*模拟数据库操作*/
        SysUser user = new SysUser();
        user.setUsername("10086");
        user.setPassword("$2a$10$p3tvTg03hcETVqK4O1W71OC/1mxY8HM.cblWmazRqiyEfRZRuMEzi");
        List<SysRole> roles = Collections.emptyList();
        SysRole sysRole = new SysRole();
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
