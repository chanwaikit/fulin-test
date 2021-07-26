package com.mazentop.excel.util;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget(value = "courseEntity")
public class CompanyHasImgModel {
    @Excel(name = "公司名称", width = 40)
    private String companyName;

    @Excel(name = "公司LOGO", type = 2, width = 40,height = 50)
    private String companyLogo;

    public CompanyHasImgModel(String companyName, String companyLogo) {
        this.companyName = companyName;
        this.companyLogo = companyLogo;
    }

}
