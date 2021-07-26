package com.mazentop.modules.emp.commond;

import com.mazentop.entity.OrdSalesOrder;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

import java.util.List;

/**
 * @author dengy
 * @title: OrdSaoesOrderCommond
 * @description: 订单管理
 * @date 2020/03/14 16:31
 */
@Data
public class OrdSalesOrderCommond extends PageCommond {

    @Criteria(expression = Expression.BETWEEN, property = OrdSalesOrder.F_PAYMENT_TIME, alias = OrdSalesOrder.TABLE_NAME)
    private SearchTime paymentTime;

    private String companyId;


    @Criteria(expression = Expression.BETWEEN,property = OrdSalesOrder.F_ADD_TIME,alias = OrdSalesOrder.TABLE_NAME)
    private SearchTime addTime;

    private String query;

    @Criteria(expression = Expression.EQ, property = OrdSalesOrder.F_SALES_ORDER_STATUS, alias = OrdSalesOrder.TABLE_NAME)
    private String salesOrderStatus;

    @Criteria(expression = Expression.IN, property = OrdSalesOrder.F_SALES_ORDER_STATUS, alias = OrdSalesOrder.TABLE_NAME)
    private List<String> salesOrderStatusList;

    @Criteria(expression = Expression.LIKE_R, property = OrdSalesOrder.F_SALES_ORDER_NO, alias = OrdSalesOrder.TABLE_NAME)
    private String salesOrderNo;

    @Criteria(expression = Expression.LIKE_R, property = OrdSalesOrder.F_LOCALHOST_SN, alias = OrdSalesOrder.TABLE_NAME)
    private Long localhostSn;


    @Criteria(expression = Expression.EQ, property = OrdSalesOrder.F_FK_CLIENTELE_ID, alias = OrdSalesOrder.TABLE_NAME)
    private String clientId;

    @Criteria(expression = Expression.IN, property = OrdSalesOrder.F_PAYMENT_PLATFORM_TYPE_ID, alias = OrdSalesOrder.TABLE_NAME)
    private String platformType;

    /**
     * 线上订单状态
     */
    @Criteria(expression = Expression.IN, property = OrdSalesOrder.F_PAYMENT_PLATFORM_TYPE_ID, alias = OrdSalesOrder.TABLE_NAME)
    private List<String> platformTypes;


    @Criteria(expression = Expression.GE, property = OrdSalesOrder.F_PAYMENT_TIME, alias = OrdSalesOrder.TABLE_NAME)
    private Long startTime;


    @Criteria(expression = Expression.LE, property = OrdSalesOrder.F_PAYMENT_TIME, alias = OrdSalesOrder.TABLE_NAME)
    private Long endTime;

    @Criteria(expression = Expression.IN, property = OrdSalesOrder.F_ID, alias = OrdSalesOrder.TABLE_NAME)
    private List<String> ids;

    @Criteria(expression = Expression.EQ, property = OrdSalesOrder.F_FK_TAG_ID, alias = OrdSalesOrder.TABLE_NAME)
    private String fkTagId;

    @Criteria(expression = Expression.EQ,property = OrdSalesOrder.F_ID, alias = OrdSalesOrder.TABLE_NAME)
    private String id;

    private Integer size;

    /**
     * 优惠券id
     */
    @Criteria(expression = Expression.EQ,property = OrdSalesOrder.F_COUPON_ACTIVITY_ID, alias = OrdSalesOrder.TABLE_NAME)
    private String activityId;

}
