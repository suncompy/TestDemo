package com.khy.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.khy.mybatisplus.dao.UserMapper;
import com.khy.mybatisplus.entity.User;
import com.khy.mybatisplus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 获取用户列表
     *
     * @return
     */
    @Override
    public Page selectUserPage() {
        long pageNum = 1;
        long page_size = 10;
        QueryWrapper<User> qwS = new QueryWrapper<>();
        qwS.eq("u.deleted", 0);
        qwS.and(wrapper -> wrapper.like("u.nickname", "小丑").or().like("u.phone_number", "13800138009"));
        qwS.orderByDesc("u.last_login_time");
        Page<User> page = new Page<>(pageNum, page_size);
        page.setRecords(baseMapper.selectUserPage(page, qwS));
        return page;
    }

}
