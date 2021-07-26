package com.mazentop.modules.emp.commond;

import com.mazentop.entity.EmpDepartmentInfo;
import com.mazentop.entity.SysCompany;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author dengy
 * @title: EmpDepartmentInfoCommond
 * @description: 部门表
 * @date 2020/03/10 14:31
 */
@Data
public class EmpDepartmentInfoCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = EmpDepartmentInfo.F_DEPARTMENT_NAME, alias = EmpDepartmentInfo.TABLE_NAME)
    private String departmentName;

}
