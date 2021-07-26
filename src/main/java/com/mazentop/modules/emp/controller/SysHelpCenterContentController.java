package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysHelpCenterContent;
import com.mazentop.entity.SysHelpCenterType;
import com.mazentop.modules.emp.commond.SysHelpCenterContentCommond;
import com.mazentop.modules.emp.commond.SysHelpCenterTypeCommond;
import com.mazentop.modules.emp.service.SysHelpCenterContentService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: dengy
 * @date: 2020/3/19
 * @description:
 */

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/sysHelpCenter")
@Api(value = "/option/v1", tags = "帮助：帮助中心", description = "SysHelpCenterContentController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysHelpCenterContentController {

    @Autowired
    SysHelpCenterContentService sysHelpCenterContentService;

    @Log("帮助中心列表")
    @GetMapping(value = "/sysHelpCenterList")
    @ApiOperation(value = "帮助中心列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysHelpCenterContentCommond", value = "查询条件", required = true, dataType = "SysHelpCenterContentCommond")
    })
    public Result sysHelpCenterList(SysHelpCenterContentCommond sysHelpCenterContentCommond) {
        return Result.build(() ->  sysHelpCenterContentService.sysHelpCenterList(sysHelpCenterContentCommond));
    }


    @Log("查询帮助信息")
    @GetMapping(value = "/getSysHelpCenterInfo/{id}")
    @ApiOperation(value = "查询帮助信息", notes = "入参:查询条件")
    public Result getSysHelpCenterInfo(@PathVariable String id) {
        return Result.build(() ->  {
            SysHelpCenterContent sysHelpCenterContent = SysHelpCenterContent.me().setId(id).get();
            return sysHelpCenterContent;
        });
    }

    @Log("查询帮助分类信息")
    @GetMapping(value = "/getSysHelpCenterType")
    @ApiOperation(value = "查询帮助信息", notes = "入参:查询条件")
    public Result getSysHelpCenterType() {
        return Result.build(() ->  {
            SysHelpCenterTypeCommond sysHelpCenterTypeCommond = new SysHelpCenterTypeCommond();
            sysHelpCenterTypeCommond.setCompanyId(Subject.group());
            sysHelpCenterTypeCommond.setOrderBy("sort");
            List<SysHelpCenterType> sysHelpCenterTypeList = SysHelpCenterType.me().find(sysHelpCenterTypeCommond);
            return sysHelpCenterTypeList;
        });
    }

    @GetMapping(value = "/getSysHelpCenterTypePage")
    @ApiOperation(value = "查询帮助信息分页", notes = "入参:查询条件")
    public Result getSysHelpCenterTypePage(SysHelpCenterTypeCommond centerTypeCommond) {
        return Result.build(() ->  {
            centerTypeCommond.setCompanyId(Subject.group());
            centerTypeCommond.setOrderBy("sort");
            List<SysHelpCenterType> sysHelpCenterTypeList = SysHelpCenterType.me().find(centerTypeCommond);
            return new Page(sysHelpCenterTypeList,centerTypeCommond);
        });
    }
    @Log("删除帮助信息")
    @PostMapping(value = "/deleteSysHelpCenterInfo")
    @ApiOperation(value="删除帮助信息", notes="入参:主键 删除帮助信息id 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 删除帮助信息id 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteSysHelpCenterInfo(@RequestBody List<String> ids){
        return Result.build(() -> sysHelpCenterContentService.doDeleteSysHelpCenterInfo(ids));
    }

    @Log("新增/修改帮助信息")
    @PostMapping(value = "/doSysHelpCenterInfoAddOrUpdate")
    @ApiOperation(value = "新增/修改帮助信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysHelpCenterContent", value = "保存参数", required = true, dataType = "SysHelpCenterContent")
    })
    public Result doSysHelpCenterInfoAddOrUpdate(@RequestBody SysHelpCenterContent sysHelpCenterContent) {
        return Result.build(() -> sysHelpCenterContentService.doSysHelpCenterInfoAddOrUpdate(sysHelpCenterContent));
    }
    @Log("新增/修改帮助分类")
    @PostMapping(value = "/doSysHelpTypeAddOrUpdate")
    @ApiOperation(value = "新增/修改帮助分类", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysHelpCenterType", value = "保存参数", required = true, dataType = "SysHelpCenterType")
    })
    public Result doSysHelpTypeAddOrUpdate(@RequestBody SysHelpCenterType sysHelpCenterType) {
        return Result.build(() -> sysHelpCenterContentService.doSysHelpTypeAddOrUpdate(sysHelpCenterType));
    }

    @GetMapping(value = "/doSysHelpTypeDelete/{id}")
    @ApiOperation(value = "新增/修改帮助分类", notes = "入参:保存参数")
    public Result doSysHelpTypeDelete(@PathVariable String id) {
        Long count = SysHelpCenterContent.me().setFkHelpCenterTypeId(id).findCount();
        if (count>0){
            return Result.toast("该分类已被绑定");
        }
        return Result.build(() -> SysHelpCenterType.me().setId(id).deleteById());
    }

    @Log("查询帮助类型信息")
    @GetMapping(value = "/getSysHelpTypeInfo/{id}")
    @ApiOperation(value = "查询帮助类型信息", notes = "入参:查询条件")
    public Result getSysHelpTypeInfo(@PathVariable String id) {
        return Result.build(() ->  {
            SysHelpCenterType sysHelpCenterType = SysHelpCenterType.me().setId(id).get();
            return sysHelpCenterType;
        });
    }
}
