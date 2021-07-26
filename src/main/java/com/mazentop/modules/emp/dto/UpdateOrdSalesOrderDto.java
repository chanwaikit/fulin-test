package com.mazentop.modules.emp.dto;

import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/14
 * @description:
 */
@Data
public class UpdateOrdSalesOrderDto {

    /**
     * 订单主表ID
     */
    private String id;

    /**
     * 订单状态
     */
    private String status;
}
