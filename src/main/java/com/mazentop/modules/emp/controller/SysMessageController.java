package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysHelpCenterContent;
import com.mazentop.entity.SysMessage;
import com.mazentop.modules.emp.commond.SysHelpCenterContentCommond;
import com.mazentop.modules.emp.commond.SysMessageCommond;
import com.mazentop.modules.emp.service.SysMessageService;
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

/**
 * @author: dengy
 * @date: 2020/3/20
 * @description:
 */

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/sysMessageInfo")
@Api(value = "/option/v1", tags = "系统消息", description = "SysMessageController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysMessageController {

    @Autowired
    SysMessageService sysMessageService;

    @Log("消息列表")
    @GetMapping(value = "/sysMessageList")
    @ApiOperation(value = "消息列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMessageCommond", value = "查询条件", required = true, dataType = "SysMessageCommond")
    })
    public Result sysMessageList(SysMessageCommond sysMessageCommond) {
        return Result.build(() ->  sysMessageService.sysMessageList(sysMessageCommond));
    }


    @Log("新增/修改消息")
    @PostMapping(value = "/doSysMessageAddOrUpdate")
    @ApiOperation(value = "新增/修改消息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMessage", value = "保存参数", required = true, dataType = "SysMessage")
    })
    public Result doSysMessageAddOrUpdate(@RequestBody SysMessage sysMessage) {
        return Result.build(() -> sysMessageService.doSysMessageAddOrUpdate(sysMessage));
    }

    @Log("删除消息")
    @PostMapping(value = "/deleteSysMessageInfo")
    @ApiOperation(value="删除消息", notes="入参:主键 删除系统消息id 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 删除系统消息id 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteSysMessageInfo(@RequestBody List<String> ids){
        return Result.build(() -> sysMessageService.deleteSysMessageInfo(ids));
    }

    @Log("查询消息详情")
    @GetMapping(value = "/getMessageInfo/{id}")
    @ApiOperation(value = "查询消息详情", notes = "入参:查询条件")
    public Result getMessageInfo(@PathVariable String id) {
        return Result.build(() ->  {
            SysMessage sysMessage = SysMessage.me().setId(id).get();
            return sysMessage;
        });
    }

}
