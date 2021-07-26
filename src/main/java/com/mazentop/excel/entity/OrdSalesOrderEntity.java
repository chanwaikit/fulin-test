package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wangzy
 * @date: 2019/9/29
 * @description: 导出Excel实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdSalesOrderEntity implements Serializable {

    private Integer id;

    @Excel(name = "销售单编号",  width = 30)
    private String salesOrderNo;

    @Excel(name = "客户名称",  width = 15)
    private String clientName;

    @Excel(name = "下单客户电子邮件",  width = 15)
    private String clientEmail;

    @Excel(name = "客户联系电话",  width = 15)
    private String clientPhone;

    @Excel(name = "收货人名称",  width = 15)
    private String receiverName;

    @Excel(name = "收货人电话",  width = 15)
    private String receiverPhone;

    @Excel(name = "收货人电子邮件",  width = 15)
    private String receiverEmail;

    @Excel(name = "收货人国家",  width = 15)
    private String receiverCountry;

    @Excel(name = "收货人省",  width = 15)
    private String receiverProvince;

    @Excel(name = "收货人城市",  width = 15)
    private String receiverCity;

    @Excel(name = "收货人详细地址",  width = 15)
    private String receiverAddress;

    @Excel(name = "总价格",  width = 15)
    private String totalPrice;

    @Excel(name = "总体积",  width = 15)
    private Long totalVol;

    @Excel(name = "总重量",  width = 15)
    private Long totalWeight;

    @Excel(name = "总商品数",  width = 15)
    private Integer totalProductNumber;

    @Excel(name = "物流渠道名称",  width = 15)
    private String transportsChannelName;

    @Excel(name = "运单号",  width = 15)
    private String transportsNo;

    @Excel(name = "运费方案名称",  width = 15)
    private String transportsFreeName;

    @Excel(name = "总运费",  width = 15)
    private String totalTransportsFree;

    @Excel(name = "总支付费用",  width = 15)
    private String totalPaymentFree;

    @Excel(name = "支付时间",  width = 15)
    private String paymentTime;

    @Excel(name = "支付人",  width = 15)
    private String paymentName;

    @Excel(name = "支付流水号",  width = 15)
    private String paymentStreamNo;

    @Excel(name = "支付平台名称",  width = 15)
    private String paymentPlatformName;

    @Excel(name = "支付卡号",  width = 15)
    private String paymentCardNo;

    @Excel(name = "退款申请单编号",  width = 15)
    private String refundRequestNo;
}
