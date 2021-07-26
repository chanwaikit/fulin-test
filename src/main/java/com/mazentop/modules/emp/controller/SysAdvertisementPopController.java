package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysAdvertisementPop;
import com.mazentop.entity.SysFriendsLinkType;
import com.mazentop.entity.SysNoticeSetting;
import com.mazentop.modules.emp.commond.SysAdvertisementPopCommond;
import com.mazentop.modules.emp.commond.SysCountryCommond;
import com.mazentop.modules.emp.service.SysAdvertisementPopService;
import com.mazentop.modules.emp.service.SysCountryService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/advertisementPop")
@Api(value = "/option/v1", tags = "弹浮窗广告：弹浮窗广告管理", description = "SysAdvertisementPopController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysAdvertisementPopController {
    @Autowired
    SysAdvertisementPopService sysAdvertisementPopService;


    @Log("查询弹浮窗广告列表")
    @GetMapping(value = "/findAdvertisementPopList")
    @ApiOperation(value = "查询弹浮窗广告列表")
    public Result findAdvertisementPopList(SysAdvertisementPopCommond commond) {

        return Result.build(() ->
                sysAdvertisementPopService.sysAdvertisementPopList(commond)
        );
    }


    @Log("弹浮窗广告编辑")
    @ApiOperation("弹浮窗广告编辑")
    @PostMapping(value = "/editAdvertisementPop")
    public Result editAdvertisementPop(@RequestBody SysAdvertisementPop sysAdvertisementPop) {
        if (Objects.isNull(sysAdvertisementPop) || Objects.isNull(sysAdvertisementPop.getId())) {
            return Result.toast("弹浮窗广告获取失败!");
        }
        Long count = SysAdvertisementPop.me().setId(sysAdvertisementPop.getId()).findCount();
        if (count == 0 ) {
            return Result.toast("弹浮窗广告不存在!");
        }
        sysAdvertisementPop.update();
        return Result.success();
    }

    @Log("弹浮窗广告获取")
    @ApiOperation("弹浮窗广告获取")
    @GetMapping(value = "/getAdvertisementPop/{id}")
    public Result getAdvertisementPop(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(() -> SysAdvertisementPop.me().setId(id).get());
    }

    @Log("弹浮窗广告获取")
    @ApiOperation("弹浮窗广告获取")
    @GetMapping(value = "/getAdvertisementPop/position/{position}")
    public Result getAdvertisementPopByPosition(@PathVariable String position) {
        if (Objects.isNull(position)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(() -> SysAdvertisementPop.me().setPosition(position).get());
    }
}
