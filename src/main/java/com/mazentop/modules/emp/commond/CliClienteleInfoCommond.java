package com.mazentop.modules.emp.commond;

import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.modules.emp.model.ScreenConditionDto;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

import java.util.List;

/**
 * @author dengy
 * @title: CliClienteleInfoCommond
 * @description: 顾客管理
 * @date 2020/03/11 14:31
 */
@Data
public class CliClienteleInfoCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_LOGIN_NAME, alias = CliClienteleInfo.TABLE_NAME)
    private String loginName;

    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_ID, alias = CliClienteleInfo.TABLE_NAME)
    private String id;


    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_PHONE, alias = CliClienteleInfo.TABLE_NAME)
    private String phone;

    /**
     * 是否启用
     */
    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_ENABLE, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_EMAIL, alias = CliClienteleInfo.TABLE_NAME)
    private String email;

    @Criteria(expression = Expression.BETWEEN,property = CliClienteleInfo.F_ADD_TIME,alias = CliClienteleInfo.TABLE_NAME)
    private SearchTime addTime;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_BUYER, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isBuyer;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_MORE_BUYER, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isMoreBuyer;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_SUBSCRIBER, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isSubscriber;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_ACCOUNT_CERTIFICATION, alias = CliClienteleInfo.TABLE_NAME)
    private String certificationStatus;

    /**
     * 注册开始时间
     */
    private Long startTime;

    /**
     * 注册结束时间
     */
    private Long endTime;

    private String query;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_COMPANY_ID, alias = CliClienteleInfo.TABLE_NAME)
    private String companyId;

    /**
     * 国家
     */
    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_COUNTRY, alias = CliClienteleInfo.TABLE_NAME)
    private String country;


    @Criteria(expression = Expression.IN, property = CliClienteleInfo.F_ID, alias = CliClienteleInfo.TABLE_NAME)
    private List<String> ids;

//    @Criteria(expression = Expression.SQL, sql = "and id in (select clientele_id from cli_clientele_info_group where group_id = :groupId)")
    private String groupId;

    /**
     * 最小成功订单数
     */
    private Long minCount;

    /**
     * 最大成功订单数
     */
    private Long maxCount;

    /**
     * 购买次数
     */
    @Criteria(expression = Expression.BETWEEN,property = CliClienteleInfo.F_BUY_TIME,alias = CliClienteleInfo.TABLE_NAME)
    private SearchTime buyTime;


    /**
     * 筛选条件
     */
    private List<ScreenConditionDto> screenCondition;


}
