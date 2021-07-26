package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ActConditionType;
import com.mazentop.entity.ActDiscountUseType;
import com.mazentop.entity.ActReduceActivity;
import com.mazentop.entity.BloBlogType;
import com.mazentop.modules.emp.commond.ActReduceActivityCommond;
import com.mazentop.modules.emp.commond.BloBlogCommond;
import com.mazentop.modules.emp.dto.ActReducectivityDto;
import com.mazentop.modules.emp.dto.OrdSalesOrderDto;
import com.mazentop.modules.emp.service.ActReduceActivityService;
import com.mztframework.annotation.ApiVersion;
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

/**
 * @author dengy
 * @title: ActReduceActivityController
 * @description: 满减活动管理
 * @date 2020/3/18 09:32
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/actReduceActivityInfo")
@Api(value = "/option/v1", tags = "活动：满减活动", description = "ActReduceActivityController", produces = MediaType.APPLICATION_JSON_VALUE)

public class ActReduceActivityController {

    @Autowired
    ActReduceActivityService actReduceActivityService;

    @Log("查询满减活动列表")
    @GetMapping(value = "/actReduceActivityList")
    @ApiOperation(value = "查询满减活动列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actReduceActivityCommond", value = "查询条件", required = true, dataType = "ActReduceActivityCommond")
    })
    public Result actReduceActivityList(ActReduceActivityCommond actReduceActivityCommond) {
        return Result.build(() -> actReduceActivityService.actReduceActivityList(actReduceActivityCommond));
    }

    @Log("获取满减活动状态数量")
    @GetMapping(value = "/getReduceActivityStatus")
    @ApiOperation(value = "获取满减活动状态数量", notes = "入参:查询条件")
    public Result getReduceActivityStatus() {
        return Result.build(() -> actReduceActivityService.getReduceActivityStatus());
    }

    @Log("新增/修改满减活动信息")
    @PostMapping(value = "/doActReduceActivityUpdateOrAdd")
    @ApiOperation(value = "新增/修改满减活动信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actReducectivityDto", value = "保存参数", required = true, dataType = "ActReducectivityDto")
    })
    public Result doActReduceActivityUpdateOrAdd(@RequestBody ActReducectivityDto actReducectivityDto) {
        return Result.build(() -> actReduceActivityService.doActReduceActivityUpdateOrAdd(actReducectivityDto));
    }
    @Log("删除满减活动信息")
    @PostMapping(value = "/deleteActReduceActivity")
    @ApiOperation(value="删除满减活动信息", notes="入参:主键 满减活动Id 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 满减活动Id 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteActReduceActivity(@RequestBody List<String> ids){
        return Result.build(() -> actReduceActivityService.deleteActReduceActivity(ids));
    }

    @Log("查询优惠类型")
    @GetMapping(value = "/getActDiscountUseType")
    @ApiOperation(value = "查询优惠类型", notes = "入参:查询条件")
    public Result getActDiscountUseType() {
        return Result.build(() ->  {
            List<ActDiscountUseType> list = ActDiscountUseType.me().find();
            return list;
        });
    }
    @Log("查询满减活动")
    @GetMapping(value = "/getActReduceActivity/{id}")
    @ApiOperation(value = "查询满减活动", notes = "入参:查询条件")
    public Result getActReduceActivity(@PathVariable String id) {
        return Result.build(() ->  {
            ActReduceActivity actReduceActivity = ActReduceActivity.me().setId(id).get();
            actReduceActivity.addExten("typeList",null);
            if(!Objects.isNull(actReduceActivity)){
                List<ActConditionType> typeList = ActConditionType.me().setFkActivityId(id).find();
                actReduceActivityService.optActConditionType(typeList);
                actReduceActivity.addExten("typeList",typeList);
            }
            return actReduceActivity;
        });
    }
}
