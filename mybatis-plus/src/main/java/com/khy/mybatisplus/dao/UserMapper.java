package com.khy.mybatisplus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.khy.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取会话用户列表
     * @param page
     * @param wrapper
     * @return
     */
    List<User> selectUserPage(Page page, @Param("ew") Wrapper wrapper);
}