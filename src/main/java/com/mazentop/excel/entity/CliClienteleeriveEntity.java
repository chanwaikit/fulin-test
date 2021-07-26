package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wangzy
 * @date: 2019/9/29
 * @description: 导出客户信息Excel实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CliClienteleeriveEntity implements Serializable {
    @Excel(name = "标识", height = 20, width = 30,isColumnHidden = true)
    private String id;

    @Excel(name = "姓", height = 20, width = 30)
    private String clientSurname;

    @Excel(name = "名", height = 20, width = 30)
    private String clientName;

    @Excel(name = "电子邮件", height = 20, width = 30)
    private String email;

    @Excel(name = "密码", height = 20, width = 30)
    private String password;

    @Excel(name = "电话", height = 20, width = 30)
    private String phone;

    @Excel(name = "国家", height = 20, width = 30)
    private String country;

    @Excel(name = "省", height = 20, width = 30)
    private String province;

    @Excel(name = "登录名", height = 20, width = 30)
    private String loginName;

    @Excel(name = "等级", height = 20, width = 30)
    private Integer level;

    @Excel(name = "购买次数", height = 20, width = 30)
    private Integer buyTime;

    @Excel(name = "购买金额", height = 20, width = 30)
    private String buySum;

    @Excel(name = "顾客类型名称", height = 20, width = 30)
    private String clientTypeName;

    @Excel(name = "谷歌账号", height = 20, width = 30)
    private String googleAccount;

    @Excel(name = "fb账号", height = 20, width = 30)
    private String facebookAccount;

    @Excel(name = "推特账号", height = 20, width = 30)
    private String twitterAccount;

    @Excel(name = "添加时间", height = 20, width = 30)
    private Integer addTime;

    @Excel(name = "首次购买时间", height = 20, width = 30)
    private Integer firstBuyTime;

    @Excel(name = "购买商品总数", height = 20, width = 30)
    private Integer buyGoodsNumber;

    @Excel(name = "币种", height = 20, width = 30)
    private String currency;

    @Excel(name = "是否订阅", height = 20, width = 30)
    private Integer isSubscriber;

    @Excel(name = "是否购买", height = 20, width = 30)
    private Integer isBuyer;

    @Excel(name = "是否已重复购买", height = 20, width = 30)
    private Integer isMoreBuyer;

    @Excel(name = "公司名称", height = 20, width = 30)
    private String companyName;

    @Excel(name = "错误信息", height = 20, width = 30)
    private String error;

    public static class TipInfo{
        public static final String CLIENT_SURNAME="顾客姓(必填)";
        public static final String UNWANTED_ID="标识（此列导入时不可删除 可为空）";
        public static final String CLIENT_NAME="顾客名 (必填)";
        public static final String PASSWORD="密码 不填默认 123456";
        public static final String EMAIL="邮箱必填(邮箱=登录名)";
        public static final String PHONE="手机号码 非必填";
        public static final String COUNTRY="国家 非必填";
        public static final String PROVINCE="省或洲 非必填";
        public static final String UNWANTED="此列包括后面列导入不需要填写";

    }

}
