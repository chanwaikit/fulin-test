package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysAdvertisement;
import com.mazentop.modules.emp.commond.SysAdvertisementCommond;
import com.mazentop.modules.emp.service.SysAdvertisementService;
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

import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/19
 * @description:
 */

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/adisement")
@Api(value = "/option/v1", tags = "广告：广告管理", description = "SysCountryController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysAdvertisementController {

    @Autowired
    SysAdvertisementService advertisementService;

    @Log("查询广告列表")
    @GetMapping(value = "/findSysAdisementList")
    @ApiOperation(value = "查询广告列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAdvertisementCommond", value = "查询条件", required = true, dataType = "SysAdvertisementCommond")
    })
    public Result findSysAdvertisementList(SysAdvertisementCommond sysAdvertisementCommond) {
        return Result.build(() -> advertisementService.findSysAdvertisementList(sysAdvertisementCommond));
    }

    @Log("新增/修改广告")
    @PostMapping(value = "/doSysAdisementAddOrUpdate")
    @ApiOperation(value = "新增/修改广告", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAdvertisement", value = "保存参数", required = true, dataType = "SysAdvertisement")
    })
    public Result doSysAdvertisementAddOrUpdate(@RequestBody SysAdvertisement sysAdvertisement) {
        if(Objects.isNull(sysAdvertisement)){
            return Result.toast("参数为空");
        }
        return Result.build(() -> advertisementService.doSysAdvertisementAddOrUpdate(sysAdvertisement));
    }

    @Log("查询广告")
    @GetMapping(value = "/getAdisement/{id}")
    @ApiOperation(value = "查询广告", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getAdvertisement(@PathVariable String id) {
        return Result.build(() -> SysAdvertisement.me().setId(id).get());
    }

}
