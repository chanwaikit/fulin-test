package com.mazentop.modules.emp.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.model.ClienteleCertificationEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.CliClienteleGoodsTrailCommond;
import com.mazentop.modules.emp.commond.CliClienteleInfoCommond;
import com.mazentop.modules.emp.commond.CliClienteleReceiverAddressCommond;
import com.mazentop.modules.emp.dto.ClienteleCertificationDto;
import com.mazentop.modules.emp.service.CliClienteleInfoService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
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
 * @title: CliClienteleInfoController
 * @description: 顾客管理
 * @date 2020/3/11 14:53
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/cliClienteleInfo")
@Api(value = "/option/v1", tags = "顾客管理", description = "CliClienteleInfoController", produces = MediaType.APPLICATION_JSON_VALUE)
public class CliClienteleInfoController {

    @Autowired
    CliClienteleInfoService cliClienteleInfoService;

    @Log("顾客信息列表")
    @GetMapping(value = "/cliClienteleInfoList")
    @ApiOperation(value = "顾客信息列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cliClienteleInfoCommond", value = "查询条件", required = true, dataType = "CliClienteleInfoCommond")
    })
    public Result cliClienteleInfoList(CliClienteleInfoCommond cliClienteleInfoCommond) {
        return Result.build(() ->  cliClienteleInfoService.cliClienteleInfoList(cliClienteleInfoCommond));
    }


    @Log("获取顾客状态数量")
    @GetMapping(value = "/getClientStatus")
    @ApiOperation(value = "获取顾客状态数量", notes = "入参:查询条件")
    public Result getClientStatus() {
        return Result.build(() -> cliClienteleInfoService.getClientStatus());
    }



    @Log("查客户分组的用户")
    @PostMapping(value = "/getInfoPageByGroup")
    @ApiOperation(value = "查客户分组的用户", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cmmond", value = "查询条件", required = true, dataType = "CliClienteleInfoCommond")
    })
    public Result getInfoPageByGroup(@RequestBody CliClienteleInfoCommond cmmond) {
        return Result.build(() ->  cliClienteleInfoService.getInfoPageByGroup(cmmond));
    }


    @Log("查客户收货地址")
    @GetMapping(value = "/getReceiverAddressPage")
    @ApiOperation(value = "查客户分组的用户", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cmmond", value = "查询条件", required = true, dataType = "CliClienteleReceiverAddressCommond")
    })
    public Result getReceiverAddressPage(CliClienteleReceiverAddressCommond cmmond) {
        return Result.build(() ->  cliClienteleInfoService.getReceiverAddressPage(cmmond));
    }



    @Log("新增/修改顾客信息")
    @PostMapping(value = "/doCliClienteleInfoAddOrUpdate")
    @ApiOperation(value = "新增/修改顾客信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cliClienteleInfo", value = "保存参数", required = true, dataType = "CliClienteleInfo")
    })
    public Result doCliClienteleInfoAddOrUpdate(@RequestBody CliClienteleInfo cliClienteleInfo) {
        return Result.build(() -> cliClienteleInfoService.doCliClienteleInfoAddOrUpdate(cliClienteleInfo));
    }


    @Log("查询单个顾客信息")
    @GetMapping(value = "/getCliClienteleInfo/{id}")
    @ApiOperation(value = "查询单个顾客信息", notes = "入参:查询条件")
    public Result getCliClienteleInfo(@PathVariable String id) {
        return Result.build(() ->  cliClienteleInfoService.getCliClienteleInfo(id));
    }

    @PostMapping(value = "/updateCliClienteleInfo/enable")
    public Result updateCliClienteleEnable(@RequestParam String id,@RequestParam Boolean isv) {
        CliClienteleInfo clienteleInfo=CliClienteleInfo.me().setId(id).get();
         Integer original = isv?Status.NO:Status.YES;
        if (!clienteleInfo.getIsEnable().equals(original)){
            clienteleInfo.setIsEnable(original).update();
        }

        return Result.success();
    }


    @Log("顾客轨迹列表")
    @GetMapping(value = "/cliClienteleGoodsTrailList")
    @ApiOperation(value = "顾客轨迹列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cliClienteleGoodsTrailCommond", value = "查询条件", required = true, dataType = "CliClienteleGoodsTrailCommond")
    })
    public Result cliClienteleGoodsTrailList(CliClienteleGoodsTrailCommond cliClienteleGoodsTrailCommond) {
        return Result.build(() ->  cliClienteleInfoService.cliClienteleGoodsTrailList(cliClienteleGoodsTrailCommond));
    }


    @Log("删除顾客信息")
    @PostMapping(value = "/deleteCliClienteleInfo")
    @ApiOperation(value="删除顾客信息", notes="入参:主键 顾客ID 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 顾客ID 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteCliClienteleInfo(@RequestBody List<String> ids){
        return Result.build(() -> cliClienteleInfoService.doDeleteCliClienteleInfo(ids));
    }


    @Log("删除顾客")
    @GetMapping("/delete")
    @ApiOperation(value="删除顾客", notes="入参:顾客主键id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true,allowMultiple=true, dataType = "String")
    })
    public Result delete(String id) {
        return Result.build(() -> cliClienteleInfoService.delete(id));
    }

    @PostMapping("/certification")
    private Result accountCertification(@RequestBody ClienteleCertificationDto  dto){
        if (StrUtil.isBlank(dto.getUserId())){
            return Result.toast("参数为空!");
        }
        if (StrUtil.isBlank(ClienteleCertificationEnum.getStatus(dto.getCertification()))){
            return Result.toast("参数异常!");
        }
        Db.tx(() -> {
            CliClienteleInfo info = CliClienteleInfo.me().setId(dto.getUserId()).get();
            info.setAccountCertification(ClienteleCertificationEnum.getStatus(dto.getCertification()));
            if (StrUtil.isNotBlank(dto.getRemark())){
                info.setCertificationRemark(dto.getRemark());
            }
            info.update();
            return true;
        });
        return Result.success();
    }
}
