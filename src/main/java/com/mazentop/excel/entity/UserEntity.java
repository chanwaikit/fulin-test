package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @date: 2019/9/29
 * @description: 导出客户信息Excel实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    @Excel(name = "姓", height = 20, width = 30)
    private String clientSurname;

    @Excel(name = "名", height = 20, width = 30)
    private String clientName;

    @Excel(name = "电子邮件", height = 20, width = 30)
    private String email;

    @Excel(name = "电话", height = 20, width = 30)
    private String phone;

    @Excel(name = "国家", height = 20, width = 30)
    private String country;

    @Excel(name = "省", height = 20, width = 30)
    private String province;

    @Excel(name = "登录名", height = 20, width = 30)
    private String loginName;

    @Excel(name = "返现次数", height = 20, width = 30)
    private Integer buyTime;

    @Excel(name = "返现金额", height = 20, width = 30)
    private String buySum;

    @Excel(name = "注册时间", height = 20, width = 30)
    private String addTime;





}
