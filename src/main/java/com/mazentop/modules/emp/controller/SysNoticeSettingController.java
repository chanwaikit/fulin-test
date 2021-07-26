package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysAdvertisementPop;
import com.mazentop.entity.SysNoticeSetting;
import com.mazentop.modules.emp.commond.SysNoticeSettingCommond;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/noticeSetting")
@Api(value = "/option/v1", tags = "通知设置：通知设置管理", description = "SysNoticeSettingController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysNoticeSettingController {

    @Log("通知设置列表")
    @GetMapping(value = "/findNoticeSettingList")
    @ApiOperation(value = "通知设置列表")
    public Result findNoticeSettingList(SysNoticeSettingCommond commond) {
        return Result.build(() ->{
            List<SysNoticeSetting> sysNoticeSetting =SysNoticeSetting.me().find(commond);
            return  new Page(sysNoticeSetting,commond);
        });
    }


    @Log("通知设置编辑")
    @ApiOperation("通知设置编辑")
    @PostMapping(value = "/editNoticeSetting")
    public Result editNoticeSetting(@RequestBody SysNoticeSetting sysNoticeSetting) {
        if (Objects.isNull(sysNoticeSetting) || Objects.isNull(sysNoticeSetting.getId())) {
            return Result.toast("弹浮窗广告获取失败!");
        }
        SysNoticeSetting sysNoticeSetting1 = SysNoticeSetting.me().setId(sysNoticeSetting.getId()).get();
        if (Helper.isEmpty(sysNoticeSetting1)) {
            return Result.toast("通知设置不存在!");
        }
        sysNoticeSetting1.setNoticeTitle(sysNoticeSetting.getNoticeTitle());
        sysNoticeSetting1.setNoticeContent(sysNoticeSetting.getNoticeContent());
        sysNoticeSetting1.setIsSuccess(sysNoticeSetting.getIsSuccess());
        sysNoticeSetting1.update();
        return Result.success();
    }

    @Log("通知设置获取")
    @ApiOperation("通知设置获取")
    @GetMapping(value = "/getNoticeSetting/{id}")
    public Result getNoticeSetting(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(() -> SysNoticeSetting.me().setId(id).get());
    }


}
