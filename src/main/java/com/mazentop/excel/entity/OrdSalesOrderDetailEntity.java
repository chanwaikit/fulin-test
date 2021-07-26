package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wangzy
 * @date: 2019/9/29
 * @description: 导出Excel实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdSalesOrderDetailEntity implements Serializable {

    private String id;

    @Excel(name = "销售单编号", width = 30, needMerge = true)
    private String salesOrderNo;

    @ExcelCollection(name = "商品")
    List<ProductEntity> product;

    @Excel(name = "收货人名称", width = 15 , needMerge = true)
    private String receiverName;

    @Excel(name = "收货人电话", width = 15 , needMerge = true)
    private String receiverPhone;

    @Excel(name = "收货人电子邮件", width = 15 , needMerge = true)
    private String receiverEmail;

    @Excel(name = "收货人国家", width = 15 , needMerge = true)
    private String receiverCountry;

    @Excel(name = "收货人详细地址", width = 15 , needMerge = true)
    private String receiverAddress;

    @Excel(name = "客户名称", width = 15 )
    private String clientName;

    @Excel(name = "下单客户电子邮件", width = 15 )
    private String clientEmail;

    @Excel(name = "客户联系电话", width = 15 , needMerge = true)
    private String clientPhone;

    @Excel(name = "总价格", width = 15 , needMerge = true)
    private String totalPrice;

    @Excel(name = "总商品数", width = 15 , needMerge = true)
    private Integer totalProductNumber;

    @Excel(name = "物流渠道名称", width = 15 , needMerge = true)
    private String transportsChannelName;

    @Excel(name = "运单号", width = 15 , needMerge = true)
    private String transportsNo;

    @Excel(name = "总运费", width = 15 , needMerge = true)
    private String totalTransportsFree;

    @Excel(name = "总支付费用", width = 15 , needMerge = true)
    private String totalPaymentFree;

    @Excel(name = "支付时间", width = 15 , needMerge = true)
    private String paymentTime;

    @Excel(name = "支付人", width = 15 , needMerge = true)
    private String paymentName;

    @Excel(name = "支付流水号", width = 15 , needMerge = true)
    private String paymentStreamNo;

    @Excel(name = "支付平台名称", width = 15 , needMerge = true)
    private String paymentPlatformName;

    @Excel(name = "隐藏字段", isColumnHidden=true,width = 15 , needMerge = true)
    private String hideText1;

    @Excel(name = "隐藏字段2", isColumnHidden=true,width = 15 , needMerge = true)
    private String hideText2;

}
