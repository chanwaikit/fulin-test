package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ActConditionType;
import com.mazentop.entity.ActPromotionActivity;
import com.mazentop.entity.ActPromotionProduct;
import com.mazentop.entity.ActReduceActivity;
import com.mazentop.modules.emp.commond.ActPromotionActivityCommond;
import com.mazentop.modules.emp.commond.ActReduceActivityCommond;
import com.mazentop.modules.emp.dto.ActPromotionActivityDto;
import com.mazentop.modules.emp.dto.ActReducectivityDto;
import com.mazentop.modules.emp.service.ActPromotionActivityService;
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
 * @description: 促销活动管理
 * @date 2020/3/19 13:32
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/actPromotionActivity")
@Api(value = "/option/v1", tags = "活动：促销活动", description = "ActPromotionActivityController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActPromotionActivityController {

    @Autowired
    ActPromotionActivityService actPromotionActivityService;

    @Log("查询促销活动列表")
    @GetMapping(value = "/actPromotionActivityList")
    @ApiOperation(value = "查询促销活动列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actPromotionActivityCommond", value = "查询条件", required = true, dataType = "ActPromotionActivityCommond")
    })
    public Result actPromotionActivityList(ActPromotionActivityCommond actPromotionActivityCommond) {
        return Result.build(() -> actPromotionActivityService.actPromotionActivityList(actPromotionActivityCommond));
    }

    @Log("获取促销活动状态数量")
    @GetMapping(value = "/getPromotionActivityStatus")
    @ApiOperation(value = "获取促销活动状态数量", notes = "入参:查询条件")
    public Result getPromotionActivityStatus() {
        return Result.build(() -> actPromotionActivityService.getPromotionActivityStatus());
    }

    @Log("新增/修改促销活动信息")
    @PostMapping(value = "/doActPromotionActivityUpdateOrAdd")
    @ApiOperation(value = "新增/修改促销活动信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actPromotionActivityDto", value = "保存参数", required = true, dataType = "ActPromotionActivityDto")
    })
    public Result doActPromotionActivityUpdateOrAdd(@RequestBody ActPromotionActivityDto actPromotionActivityDto) {
        return Result.build(() -> actPromotionActivityService.doActPromotionActivityUpdateOrAdd(actPromotionActivityDto));
    }


    @Log("删除促销活动信息")
    @PostMapping(value = "/deletePromotionActivity")
    @ApiOperation(value="删除促销活动信息", notes="入参:主键 促销活动Id 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 促销活动Id 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deletePromotionActivity(@RequestBody List<String> ids){
        return Result.build(() -> actPromotionActivityService.deleteActReduceActivity(ids));
    }


    @Log("查询促销活动")
    @GetMapping(value = "/getPromotionActivity/{id}")
    @ApiOperation(value = "查询促销活动", notes = "入参:查询条件")
    public Result getPromotionActivity(@PathVariable String id) {
        return Result.build(() ->  actPromotionActivityService.getPromotionActivity(id));
    }
}
