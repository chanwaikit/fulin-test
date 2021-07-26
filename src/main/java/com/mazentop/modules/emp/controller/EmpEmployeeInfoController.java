package com.mazentop.modules.emp.controller;

import com.mazentop.entity.EmpAuthorityInfo;
import com.mazentop.entity.EmpEmployeeAuthorityInfo;
import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.modules.emp.commond.EmpEmployeeInfoCommond;
import com.mazentop.modules.emp.dto.EmployeeDto;
import com.mazentop.modules.emp.service.EmpEmployeeInfoService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dengy
 * @title: EmpEmployeeInfoController
 * @description: 员工管理
 * @date 2020/3/10 14:53
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/employeeInfo")
@Api(value = "/option/v1", tags = "员工：员工管理", description = "EmpEmployeeInfoController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpEmployeeInfoController {

    @Autowired
    EmpEmployeeInfoService empEmployeeInfoService;

    @Log("查询员工信息")
    @GetMapping(value = "/findEmployeeInfoList")
    @ApiOperation(value = "查询员工信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empEmployeeInfoCommond", value = "查询条件", required = true, dataType = "EmpEmployeeInfoCommond")
    })
    public Result findEmployeeInfoList(EmpEmployeeInfoCommond empEmployeeInfoCommond) {
        return Result.build(() ->  empEmployeeInfoService.findEmployeeInfoList(empEmployeeInfoCommond));
    }

    @Log("新增/修改员工信息")
    @PostMapping(value = "/doEmployeeAddOrUpdate")
    @ApiOperation(value = "新增/修改员工信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeDto", value = "保存参数", required = true, dataType = "EmployeeDto")
    })
    public Result doDepartmentAddOrUpdate(@RequestBody EmployeeDto employeeDto) {
        return Result.build(() -> empEmployeeInfoService.doEmployeeAddOrUpdate(employeeDto));
    }



    @PostMapping(value = "/doUpdate")
    @ApiOperation(value = "修改员工信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeDto", value = "保存参数", required = true, dataType = "EmployeeDto")
    })
    public Result doUpdate(@RequestBody EmpEmployeeInfo employeeDto) {
        return Result.build(() -> empEmployeeInfoService.doUpdate(employeeDto));
    }




    @Log("查询单个员工信息")
    @GetMapping(value = "/getEmployee/{id}")
    @ApiOperation(value = "查询单个员工信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getDepartment(@PathVariable String id) {
        return Result.build(() ->  {
            EmployeeDto employeeDto = new EmployeeDto();
            EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(id).get();
            if(!Objects.isNull(empEmployeeInfo)){

                // 获取权限表
                List<EmpEmployeeAuthorityInfo> authorityInfos = EmpEmployeeAuthorityInfo.me().setFkEmployeeId(empEmployeeInfo.getId()).find();
                List<String> ids = new ArrayList<>();
                for (EmpEmployeeAuthorityInfo authorityInfo : authorityInfos) {
                    ids.add(EmpAuthorityInfo.me().setId(authorityInfo.getFkAuthorityId()).get().getId());
                }
                employeeDto.setEmployeeInfo(empEmployeeInfo);
                employeeDto.setAuthorityIds(ids);

            }
            return employeeDto;
        });
    }

    @Log("删除员工")
    @PostMapping("/deleteEmployee")
    @ApiOperation(value="删除员工信息", notes="入参:员工主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result deleteDepartment(@RequestBody List<String> ids) {
        return Result.build(() -> empEmployeeInfoService.doDeleteEmployee(ids));
    }

}
