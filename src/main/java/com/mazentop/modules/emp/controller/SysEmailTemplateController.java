package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysEmailSendRecord;
import com.mazentop.entity.SysEmailTemplate;
import com.mazentop.entity.SysEmailTemplateType;
import com.mazentop.modules.emp.commond.SysEmailSendRecordCommond;
import com.mazentop.modules.emp.commond.SysEmailTemplateCommond;
import com.mazentop.modules.emp.dto.SysEmailTemplateTypeDto;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/email/template")
@Api(value = "/option/v1", tags = "邮件模板：邮件管理管理", description = "SysEmailTemplateController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysEmailTemplateController {

    @ApiOperation("邮件模板列表")
    @GetMapping("/list")
    public Result sysEmailTemplateList(SysEmailTemplateCommond commond) {
        commond.setOrderBy("id asc");
        List<SysEmailTemplateType> list = SysEmailTemplateType.me().find(commond);
        List<SysEmailTemplateTypeDto> dtoList=new ArrayList<>();
        for (SysEmailTemplateType templateType : list) {
            SysEmailTemplateTypeDto dto=new SysEmailTemplateTypeDto();
            List<SysEmailTemplate> sysEmailTemplates = SysEmailTemplate.me().setFkTemplateTypeId(templateType.getId()).find();
            BeanUtils.copyProperties(templateType,dto);
            dto.setList(sysEmailTemplates);
            dtoList.add(dto);
        }

        return Result.build(() -> dtoList);

    }

    @ApiOperation("邮件发送记录列表")
    @GetMapping("/record/list")
    public Result sysEmailSendRecordList(SysEmailSendRecordCommond commond) {
        return Result.build(() ->{
            List<SysEmailSendRecord> list = SysEmailSendRecord.me().find(commond);
            return  new Page(list,commond);
        });

    }

    @ApiOperation("编辑模板")
    @PostMapping("/edit")
    public Result editSysEmailTemplate(@RequestBody SysEmailTemplate sysEmailTemplate) {
        if (Helper.isEmpty(sysEmailTemplate.getId())){
            return Result.toast("暂无数据！");
        }
        if (Helper.isEmpty(sysEmailTemplate.getTemplateShowContent())){
            return Result.toast("邮件正文不能为空！");
        }
        sysEmailTemplate.update();
        return Result.success();
    }

    @ApiOperation("获取模板详情")
    @GetMapping("/get/{id}")
    public Result getSysEmailTemplate(@PathVariable String id) {
        SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setId(id).get();
        return Result.build(()->sysEmailTemplate);
    }

    @ApiOperation("邮件模板类型列表")
    @GetMapping("/type/list")
    public Result sysEmailTemplateTypeList(SysEmailTemplateCommond commond) {
        return Result.build(() ->{
            List<SysEmailTemplateType> list = SysEmailTemplateType.me().find(commond);
            return  new Page(list,commond);
        });

    }

    @ApiOperation("邮件模板类型获取")
    @GetMapping("/type/{id}")
    public Result getEmailTemplateType(@PathVariable String id) {
        return Result.build(() ->{
            SysEmailTemplateType templateType = SysEmailTemplateType.me().setId(id).get();
            return  templateType;
        });

    }

    @ApiOperation("编辑模板类型")
    @GetMapping("/edit/type")
    public Result editSysEmailTemplate(@RequestBody SysEmailTemplateType sysEmailTemplateType) {
        if (Helper.isEmpty(sysEmailTemplateType.getId())){
            return Result.toast("暂无数据！");
        }
        sysEmailTemplateType.update();
        return Result.toast("暂无数据！");
    }
}
