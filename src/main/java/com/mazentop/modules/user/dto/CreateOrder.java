package com.mazentop.modules.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建订单  需要做数字签名校验 暂不加
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/8/1 10:36
 */
@Data
public class CreateOrder implements Serializable{

    private static final long serialVersionUID = 8804677667072992287L;

    private List<String> items;

    /**
     * 订单号，换支付方式时，需要使用到
     */
    private String salesOrderNo;

    /**
     * 支付类型 paypal .Paypal asiabill .Asiabill  offline.线下支付
     */
    private String payType;

    private String userId;

    private String settlement;

    private String fkProductId;

    private String skuCode;

    private Integer number;

    private String addressId;

    private String ordRemark;
}
