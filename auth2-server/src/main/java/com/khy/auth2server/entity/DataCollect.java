package com.khy.auth2server.entity;

import com.qiyego.wsgjp.enums.DataCollectEnum;
import com.qiyego.wsgjp.enums.DataCollectTypeEnum;

import java.time.LocalDateTime;

/**
 * 数据收集情况
 *
 * @author yzl
 * @date 2018/3/22
 */
public class DataCollect {
    /**
     * ID
     */
    private Long id;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 类型 XUN_CHUAN：迅传 SPIDER：爬虫
     */
    private DataCollectTypeEnum type;

    /**
     * 状态
     */
    private DataCollectEnum state;

    /**
     * 上次收集时间
     */
    private LocalDateTime lastTime;

    /**
     * 附加字段1
     */
    private String extField1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public DataCollectTypeEnum getType() {
        return type;
    }

    public void setType(DataCollectTypeEnum type) {
        this.type = type;
    }

    public DataCollectEnum getState() {
        return state;
    }

    public void setState(DataCollectEnum state) {
        this.state = state;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1;
    }
}