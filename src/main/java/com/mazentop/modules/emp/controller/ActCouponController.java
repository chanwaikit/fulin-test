package com.mazentop.modules.emp.controller;

import com.mazentop.entity.*;
import com.mazentop.modules.emp.commond.ActCouponCommond;
import com.mazentop.modules.emp.commond.CouponRecordCommond;
import com.mazentop.modules.emp.dto.ActConditionTypeDto;
import com.mazentop.modules.emp.dto.ActCouponActivityDto;
import com.mazentop.modules.emp.service.ActCouponService;
import com.mazentop.modules.emp.service.ActPromotionActivityService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/18
 * @description:
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/actCoupon")
@Api(value = "/option/v1", tags = "优惠券", description = "ActCouponController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActCouponController {

    @Autowired
    ActCouponService actCouponService;


    @Log("查询优惠券类型")
    @GetMapping(value = "/findActCouponTypeList")
    @ApiOperation(value = "查询优惠券类型", notes = "无入参")
    public Result findActCouponTypeList() {
        return Result.build(() -> actCouponService.findActCouponTypeList());
    }



    @Log("获取优惠券状态数量")
    @GetMapping(value = "/getCouponActivityStatus")
    @ApiOperation(value = "获取优惠券状态数量", notes = "入参:查询条件")
    public Result getCouponActivityStatus() {
        return Result.build(() -> actCouponService.getCouponActivityStatus());
    }



    @Log("查询优惠券列表")
    @GetMapping(value = "/findActCouponList")
    @ApiOperation(value = "查询优惠券列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponCommond", value = "查询条件", required = true, dataType = "ActCouponCommond")
    })
    public Result findActCouponList(ActCouponCommond couponCommond) {
        return Result.build(() -> actCouponService.findActCouponList(couponCommond));
    }


    @Log("查询优惠券使用记录")
    @GetMapping(value = "/getCouponRecordList")
    @ApiOperation(value = "查询优惠券使用记录", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commond", value = "查询条件", required = true, dataType = "CouponRecordCommond")
    })
    public Result getCouponRecordList(CouponRecordCommond commond) {
        return Result.build(() -> actCouponService.getCouponRecordList(commond));
    }


    @Log("新增/修改优惠券信息")
    @PostMapping(value = "/doActCouponAddOrUpdate")
    @ApiOperation(value = "新增/修改博客信息信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actCouponActivity", value = "保存参数", required = true, dataType = "ActCouponActivity")
    })
    public Result doActCouponAddOrUpdate(@RequestBody ActCouponActivityDto actCouponActivity) {

        if (Objects.isNull(actCouponActivity)) {
            return Result.toast("传参为空");
        }

        return Result.build(() -> actCouponService.doActCouponAddOrUpdate(actCouponActivity));
    }


    @Log("给用户派发优惠券")
    @PostMapping(value = "/doDistributeCoupon/{activityId}")
    @ApiOperation(value = "给用户派发优惠券", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clienteleList", value = "保存参数", required = true, dataType = "List<String>")
    })
    public Result doDistributeCoupon(@PathVariable String activityId, @RequestBody List<String> clienteleList) {
        return Result.build(() -> actCouponService.doDistributeCoupon(clienteleList, activityId));
    }



    @Log("查询单个优惠券信息")
    @GetMapping(value = "/getActCoupon/{id}")
    @ApiOperation(value = "查询单个优惠券信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getActCoupon(@PathVariable String id) {
        return Result.build(() ->  actCouponService.getActCoupon(id));
    }

    @Log("删除优惠券")
    @PostMapping("/deleteActCoupon")
    @ApiOperation(value="删除优惠券信息", notes="入参:博客主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result deleteActCoupon(@RequestBody List<String> ids) {
        return Result.build(() -> actCouponService.deleteActCoupon(ids));
    }

    @Log("获取随机码")
    @GetMapping(value = "/getCode")
    @ApiOperation(value = "获取随机码", notes = "无入参")
    public Result getCode() {
        return Result.build(() -> actCouponService.getCode());
    }

}
