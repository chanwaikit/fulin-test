package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.SysPartner;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.SysPartnerCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.mztframework.dao.page.Page;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/option/{api_version}/sysPartner")
@Api(tags = "合作伙伴接接口")
public class SysPartnerController {

    @Log("合作伙伴列表")
    @ApiOperation("合作伙伴列表")
    @ResponseBody
    @GetMapping("/list")
    public Result SysPartnerList(SysPartnerCommond commond) {
        return Result.build(() -> {
            List<SysPartner> sysPartnerList = SysPartner.me()
                    .setIsEnable(Status.YES)
                    .setOrderByFields(Order.desc(ProProductMaster.F_ADD_TIME))
                    .find(commond);
            return new Page(sysPartnerList,commond);
        });

    }

    @Log("合作伙伴新增")
    @ApiOperation("合作伙伴新增")
    @ResponseBody
    @PostMapping(value = "/addSysPartner")
    public Result addSysPartner(@RequestBody SysPartner sysPartner) {
        if (Objects.isNull(sysPartner)) {
            return Result.toast("合作信息获取失败!");
        }
        Long count = sysPartner.me().setPartnerTitle(sysPartner.getPartnerTitle()).findCount();
        if (count > 0) {
            return Result.toast("该合作已存在!");
        }
        sysPartner.setAddTime(Utils.currentTimeSecond());
        sysPartner.setIsEnable(1);
        sysPartner.insert();
        return Result.success();
    }

    @Log("合作伙伴编辑")
    @ApiOperation("合作伙伴编辑")
    @ResponseBody
    @PostMapping(value = "/editSysPartner")
    public Result editSysPartner(@RequestBody SysPartner sysPartner) {
        if (Objects.isNull(sysPartner) || Objects.isNull(sysPartner.getId())) {
            return Result.success("参数不能为空!");
        }
        SysPartner partner = SysPartner.me().setId(sysPartner.getId()).get();
        if (Objects.isNull(partner)) {
            return Result.toast("合作信息获取失败!");
        }
        SysPartner partnerTitle = SysPartner.me().setPartnerTitle(sysPartner.getPartnerTitle()).get();
        Long count = SysPartner.me().setPartnerTitle(sysPartner.getPartnerTitle()).findCount();
        if (count > 0 && !partner.getId().equals(partnerTitle.getId())) {
            return Result.toast("合作信息已存在!");
        }
        sysPartner.setOperationTime(Utils.currentTimeSecond());
        sysPartner.update();
        return Result.success();
    }

    @Log("编辑合作伙伴状态")
    @ApiOperation("编辑合作伙伴状态")
    @ResponseBody
    @GetMapping(value = "/editSysPartnerEnable/{id}")
    public Result editSysPartnerEnable( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysPartner sysPartner = SysPartner.me().setId(id).get();
        if (Objects.isNull(sysPartner)) {
            return Result.toast("友情信息获取失败!");
        }
        if (sysPartner.getIsEnable()==1){
            sysPartner.setIsEnable(0);
        }else {
            sysPartner.setIsEnable(1);
        }
        sysPartner.setOperationTime(Utils.currentTimeSecond());
        sysPartner.update();
        return Result.success();
    }

    @Log("获取伙伴详情")
    @ApiOperation("获取伙伴详情")
    @ResponseBody
    @GetMapping(value = "/getSysPartner/{id}")
    public Result getSysPartner( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysPartner sysPartner = SysPartner.me().setId(id).get();
        if (Objects.isNull(sysPartner)) {
            return Result.toast("合作伙伴获取失败!");
        }
        return Result.build(()->sysPartner);
    }
    @Log("删除伙伴详情")
    @ApiOperation("删除伙伴详情")
    @ResponseBody
    @GetMapping(value = "/delSysPartner/{id}")
    public Result delSysPartner( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysPartner sysPartner = SysPartner.me().setId(id).get();
        if (Objects.isNull(sysPartner)) {
            return Result.toast("合作伙伴获取失败!");
        }
        sysPartner.deleteById();
        return Result.success();
    }
}
