package com.khy.auth2server.dao;

import com.qiyego.wsgjp.entity.WsgjpAuthCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WsgjpAuthCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WsgjpAuthCode record);

    int insertSelective(WsgjpAuthCode record);

    WsgjpAuthCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WsgjpAuthCode record);

    int updateByPrimaryKey(WsgjpAuthCode record);

    WsgjpAuthCode selectByCustomerId(@Param("customerId") Long customerId);

    List<WsgjpAuthCode> selectTaskList();
}