package com.khy.auth2server.dao;

import com.khy.auth2server.entity.OauthCode;

public interface OauthCodeMapper {
    int insert(OauthCode record);

    int insertSelective(OauthCode record);
}