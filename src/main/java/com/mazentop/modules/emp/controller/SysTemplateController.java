package com.mazentop.modules.emp.controller;


import com.mazentop.entity.SysTemplate;
import com.mazentop.modules.emp.commond.SysTemplateCommond;
import com.mazentop.modules.emp.service.SysTemplateService;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/sysTemplate")
@Api(value = "/option/v1", tags = "模板设置", description = "SysTemplateController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysTemplateController {

    @Autowired
    SysTemplateService sysTemplateService;

    @ApiOperation("模板列表")
    @GetMapping("/list")
    public Result SysTemplateList(SysTemplateCommond commond) {
        return Result.build(() -> {
            List<SysTemplate> sysTemplateList = SysTemplate.me()
                    .find(commond);
            return new Page(sysTemplateList,commond);
        });
    }


    @ApiOperation("查询模板信息")
    @GetMapping(value = "/getSysTemplate/{id}")
    public Result getSysTemplate( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysTemplate sysTemplate = SysTemplate.me().setId(id).get();
        if (Objects.isNull(sysTemplate)) {
            return Result.toast("模板信息获取失败!");
        }
        return Result.build(()->sysTemplate);
    }
    @ApiOperation("模板新增编辑")
    @PostMapping(value = "/addOrUpdateTemplate")
    public Result addOrUpdateTemplate(@RequestBody SysTemplate sysTemplate) {
        if (Objects.isNull(sysTemplate)) {
            return Result.toast("参数不能为空!");
        }
        if(Helper.isNotEmpty(sysTemplate.getId())){
            if(sysTemplate.getIsEnable() == 1){
                Long count = SysTemplate.me().setId(sysTemplate.getId()).setIsEnable(1).findCount();
                if(count < 1){
                    count = SysTemplate.me().setIsEnable(1).findCount();
                    if(count>0){
                        return Result.toast("只能启用一个模板");
                    }
                }
            }
        }else{
            if(sysTemplate.getIsEnable() == 1){
                Long count = SysTemplate.me().setIsEnable(1).findCount();
                if(count>0){
                    return Result.toast("只能启用一个模板");
                }
            }
        }
        sysTemplate.insertOrUpdate();
        return Result.success();
    }
    @Log("删除模板信息")
    @ApiOperation("删除模板信息")
    @ResponseBody
    @GetMapping(value = "/delSysTemplate/{id}")
    public Result delSysTemplate( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysTemplate.me().setId(id).delete();
        return Result.success();
    }

    @ApiOperation("查询模板列表")
    @GetMapping(value = "/getSysTemplateList")
    public Result getSysTemplate() {
        List<SysTemplate> sysTemplate = SysTemplate.me().find();
        return Result.build(()->sysTemplate);
    }
}
