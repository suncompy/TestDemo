package com.khy.auth2server.dao;

import com.khy.auth2server.entity.OauthApprovals;

public interface OauthApprovalsMapper {
    int insert(OauthApprovals record);

    int insertSelective(OauthApprovals record);
}