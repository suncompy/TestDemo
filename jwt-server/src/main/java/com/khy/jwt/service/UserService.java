package com.khy.jwt.service;

import com.khy.jwt.entity.JwtUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    JwtUser get(String id,String name);
    JwtUser get();
    String delete(String id);
    String save(String id, String value);
    String update(String id, String value);
}
