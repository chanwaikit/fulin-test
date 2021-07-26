package com.mazentop.modules.emp.dto;

import com.mazentop.entity.EmpAuthorityInfo;
import com.mazentop.entity.EmpEmployeeInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: wangzy
 * @date: 2020/3/11
 * @description:
 */
@Data
public class EmployeeDto {

    private EmpEmployeeInfo employeeInfo;

    private List<String> authorityIds;

    private List<EmpAuthorityInfo> empAuthorityInfoList;

}
