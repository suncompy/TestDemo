package com.khy.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.khy.mybatisplus.entity.User;

public interface UserService extends IService<User> {
    /**
     * 获取会话列表
     *
     * @return
     */
    Page selectUserPage();
}
