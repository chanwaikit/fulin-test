package com.mazentop.modules.emp.controller;


import com.mazentop.entity.EmpDepartmentInfo;
import com.mazentop.modules.emp.commond.EmpDepartmentInfoCommond;
import com.mazentop.modules.emp.commond.EmpEmployeeInfoCommond;
import com.mazentop.modules.emp.service.EmpDepartmentInfoService;
import com.mazentop.modules.emp.service.EmpEmployeeInfoService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author dengy
 * @title: EmpDepartmentInfoController
 * @description: 部门管理
 * @date 2020/3/10 14:53
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/departmentInfo")
@Api(value = "/option/v1", tags = "员工：部门管理", description = "EmpDepartmentInfoController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpDepartmentInfoController {

    @Autowired
    EmpDepartmentInfoService empDepartmentInfoService;

    @Log("查询部门信息列表")
    @GetMapping(value = "/findDepartmentList")
    @ApiOperation(value = "查询部门信息列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empDepartmentInfoCommond", value = "查询条件", required = true, dataType = "EmpDepartmentInfoCommond")
    })
    public Result findDepartmentList(EmpDepartmentInfoCommond empDepartmentInfoCommond) {
        return Result.build(() ->  empDepartmentInfoService.findDepartmentList(empDepartmentInfoCommond));
    }

    @Log("查询部门列表")
    @GetMapping(value = "/getDepartmentList")
    @ApiOperation(value = "查询部门列表", notes = "入参:查询条件")
    public Result getDepartmentList() {
        return Result.build(() -> EmpDepartmentInfo.me().find());
    }

    @Log("新增/修改部门信息")
    @PostMapping(value = "/doDepartmentAddOrUpdate")
    @ApiOperation(value = "新增/修改部门信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empDepartmentInfo", value = "保存参数", required = true, dataType = "EmpDepartmentInfo")
    })
    public Result doDepartmentAddOrUpdate(@RequestBody EmpDepartmentInfo empDepartmentInfo) {
        return Result.build(() -> empDepartmentInfoService.doDepartmentAddOrUpdate(empDepartmentInfo));
    }

    @Log("查询单个部门信息")
    @GetMapping(value = "/getDepartment/{id}")
    @ApiOperation(value = "查询单个部门信息", notes = "入参:查询条件")
    public Result getDepartment(@PathVariable String id) {
        return Result.build(() -> EmpDepartmentInfo.me().setId(id).get());
    }

    @Log("删除部门")
    @GetMapping("/deleteDepartment")
    @ApiOperation(value="删除部门信息", notes="入参:商品主键id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteDepartment(String id) {
        return Result.build(() -> empDepartmentInfoService.doDeleteDepartment(id));
    }

    @Log("删除部门信息")
    @PostMapping(value = "/deleteDepartments")
    @ApiOperation(value="删除部门信息", notes="入参:主键 部门ID 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result cancelDistribution(@RequestBody List<String> ids){
        return Result.build(() -> empDepartmentInfoService.doDeleteDepartments(ids));
    }
}
