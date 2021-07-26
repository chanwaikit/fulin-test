package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.mazentop.CmsConfig;
import com.mazentop.excel.service.impl.EvaCashBackApplyExcelServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 导出Excel实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaCashBackApplyEntity {

    @Excel(name = "亚马逊订单号（此列导入时不可删除 不可为空）",  width = 20)
    private String evaOrderNo;

    @Excel(name = "用户邮箱",  width = 20)
    private String email;

    @Excel(name = "用户手机号",  width = 20)
    private String phone;

    @Excel(name = "国家",  width = 20)
    private String country;

    @Excel(name = "订单时间",  width = 20)
    private String date;

    @Excel(name = "现价",  width = 20)
    private String price;

    @Excel(name = "原价",  width = 20)
    private String originalPrice;

    @Excel(name = "回扣",  width = 20)
    private String rebate;

    @Excel(name = "PayPal账号",  width = 20)
    private String paypalAccount;

    @Excel(name = "PayPal流水号", width = 20)
    private String paypalSerialNo;

    @Excel(name = "转账凭证(图和列注意对应)",type = 2, width = 35, height = 20,savePath = CmsConfig.EXCEL_FILE_PATH)
    private String transferVoucher;

    @Excel(name = "评论链接",  width = 20)
    private String amazonCommentLink;

    @Excel(name = "评论截图",type = 2, width = 35, height = 20,savePath = CmsConfig.EXCEL_FILE_PATH)
    private String commentImg;




}
