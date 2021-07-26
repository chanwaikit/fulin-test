package com.mazentop.modules.emp.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class CliClienteleInfoDto {
    @Excel(name = "邮箱", orderNum = "0")
    private String email;

    @Excel(name = "姓氏", orderNum = "1")
    private String clientSurname;

    @Excel(name = "名字", orderNum = "2")
    private String clientName;

    @Excel(name = "电话", orderNum = "3")
    private String phone;

    @Excel(name = "国家", orderNum = "4")
    private String country;

    @Excel(name = "国家代码", orderNum = "5")
    private String countryCode;

    @Excel(name = "地区", orderNum = "6")
    private String province;

    @Excel(name = "头像地址", orderNum = "7")
    private String iconImageUrl;

    @Excel(name = "错误信息", orderNum = "8")
    private String error;


}
