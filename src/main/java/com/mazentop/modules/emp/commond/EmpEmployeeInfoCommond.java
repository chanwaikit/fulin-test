package com.mazentop.modules.emp.commond;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author dengy
 * @title: EmpEmployeeInfoCommond
 * @description: 员工表
 * @date 2020/03/10 14:31
 */
@Data
public class EmpEmployeeInfoCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = EmpEmployeeInfo.F_EMPLOYEE_NAME, alias = EmpEmployeeInfo.TABLE_NAME)
    private String employeeName;
}
