package com.mazentop.modules.emp.controller;


import com.mazentop.entity.SysExchangeRate;
import com.mazentop.modules.emp.commond.SysExchangeRateCommond;
import com.mazentop.modules.emp.service.SysExchangeRateService;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/exchange/rate")
@Api( tags = "外汇比率：外币管理")
public class SysExchangeRateController {
    @Autowired
    SysExchangeRateService sysExchangeRateService;

    @Log("外汇比率列表")
    @ApiOperation("外汇比率列表")
    @GetMapping("/list")
    public Result sysExchangeRateList(SysExchangeRateCommond commond) {
        return Result.build(() -> {
            List<SysExchangeRate> sysExchangeRates = SysExchangeRate.me().setOrderByFields(Order.desc(SysExchangeRate.F_EDIT_TIME)).find(commond);
            for (SysExchangeRate sysExchangeRate : sysExchangeRates) {
                if (Helper.isNotEmpty(sysExchangeRate.getTagRatio())){
                    sysExchangeRate.setTagRatio(sysExchangeRate.getTagRatio().setScale(2, BigDecimal.ROUND_DOWN));
                }
                sysExchangeRate.setRatio(sysExchangeRate.getRatio().setScale(2, BigDecimal.ROUND_DOWN));
            }
            return new Page(sysExchangeRates,commond);
            });
    }

    @Log("添加/编辑外汇比率")
    @ApiOperation("添加/编辑外汇比率")
    @PostMapping(value = "/edit")
    public Result editSysExchangeRate(@RequestBody SysExchangeRate sysExchangeRate) {
        if (Helper.isEmpty(sysExchangeRate.getScur())){
            Result.toast("换算币种不能为空！");
        }
        if (Helper.isEmpty(sysExchangeRate.getTcur())){
            Result.toast("被换算币种不能为空！");
        }
        if (Helper.isEmpty(sysExchangeRate.getRatio())){
            Result.toast("换算比率不能为空");
        }
        sysExchangeRateService.doEditSysExchangeRate(sysExchangeRate);
        return Result.success();
    }

    @Log("外汇比率详情")
    @ApiOperation("外汇比率详情")
    @GetMapping("/get/{id}")
    public Result getSysExchangeRate(@PathVariable String id){
        SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(id).get();
        if (Helper.isEmpty(sysExchangeRate)){
            Result.toast("暂无相关数据！");
        }
        if (Helper.isNotEmpty(sysExchangeRate.getTagRatio())){
            sysExchangeRate.setTagRatio(sysExchangeRate.getTagRatio().setScale(2, BigDecimal.ROUND_DOWN));
        }
        sysExchangeRate.setRatio(sysExchangeRate.getRatio().setScale(2, BigDecimal.ROUND_DOWN));
        return Result.build(()->sysExchangeRate);
    }

    @Log("外汇比率删除")
    @ApiOperation("外汇比率删除")
    @GetMapping("/del/{id}")
    public Result delSysExchangeRate(@PathVariable String id){
        SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(id).get();
        if (Helper.isEmpty(sysExchangeRate)){
            Result.toast("暂无相关数据！");
        }
        return Result.build(()->sysExchangeRate.deleteById());
    }
}
