package com.khy.auth2server.dao;

import com.qiyego.wsgjp.entity.DataCollect;
import com.qiyego.wsgjp.enums.DataCollectTypeEnum;
import org.apache.ibatis.annotations.Param;

/**
 * 数据收集情况相关操作
 */
public interface DataCollectMapper {
    /**
     * 根据客户编号和类型查询信息
     *
     * @param customerId 客户编号
     * @param type       类型
     * @return
     */
    DataCollect selectByCustomerId(@Param("customerId") Long customerId, @Param("type") DataCollectTypeEnum type);

    /**
     * 根据客户编号和类型查询 有效信息
     *
     * @param customerId 客户编号
     * @param type       类型
     * @return
     */
    DataCollect selectEnableByCustomerId(@Param("customerId") Long customerId, @Param("type") DataCollectTypeEnum type);

    /**
     * 将数据收集情况持久化到数据库。不存在则插入，否则更新
     *
     * @param dataCollectInfo 数据收集情况
     * @return
     */
    int insertOrUpdate(DataCollect dataCollectInfo);

    /**
     * 根据erp公司名称查询信息
     *
     * @param companyName erp公司名称
     * @return
     */
    DataCollect selectByCompanyName(@Param("companyName") String companyName, @Param("type") DataCollectTypeEnum type);
}