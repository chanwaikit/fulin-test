package com.mazentop.modules.emp.controller;

import com.mazentop.entity.EmpAuthorityInfo;
import com.mazentop.modules.emp.commond.EmpDepartmentInfoCommond;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengy
 * @title: EmpAuthorityInfoController
 * @description: 权限信息
 * @date 2020/3/10 14:53
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/empAuthorityInfo")
@Api(value = "/option/v1", tags = "权限信息", description = "EmpAuthorityInfoController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpAuthorityInfoController {

    @Log("查询权限列表")
    @GetMapping(value = "/findAuthorityList")
    public Result findDepartmentList() {
        return Result.build(() -> EmpAuthorityInfo.me().find());
    }
}
