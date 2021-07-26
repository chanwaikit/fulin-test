package com.mazentop.modules.emp.controller;

import com.mazentop.entity.CliClienteleGroup;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.CliClienteleReceiverAddress;
import com.mazentop.modules.emp.commond.CliClienteleGoodsTrailCommond;
import com.mazentop.modules.emp.commond.CliClienteleGroupCommond;
import com.mazentop.modules.emp.commond.CliClienteleInfoCommond;
import com.mazentop.modules.emp.service.CliClienteleGroupService;
import com.mazentop.modules.emp.service.CliClienteleInfoService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @title: CliClienteleGroupController
 * @description: 顾客分组
 * @date 2020/11/09 14:53
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/cliClienteleGroup")
@Api(value = "/option/v1", tags = "顾客分组", description = "CliClienteleGroupController", produces = MediaType.APPLICATION_JSON_VALUE)
public class CliClienteleGroupController {

    @Autowired
    CliClienteleGroupService groupService;

    @GetMapping(value = "/cliClienteleGroupPage")
    public Result cliClienteleGroupPage(CliClienteleGroupCommond commond) {
        return Result.build(() ->  groupService.cliClienteleGroupPage(commond));
    }

    @GetMapping(value = "/cliClienteleGroupList")
    public Result cliClienteleGroupList() {
        return Result.build(() ->  CliClienteleGroup.me().find());
    }

    @PostMapping(value = "/doGroupAddOrUpdate")
    public Result doGroupAddOrUpdate(@RequestBody CliClienteleGroup dto) {
        return Result.build(() -> groupService.doGroupAddOrUpdate(dto));
    }


    @GetMapping("/deleteGroup")
    public Result deleteGroup(String id) {
        return Result.build(() -> groupService.deleteGroup(id));
    }



    @GetMapping(value = "/getGroupInfo/{id}")
    public Result getCliClienteleInfo(@PathVariable String id) {
        return Result.build(() ->  groupService.getGroupInfo(id));
    }


    @PostMapping(value = "/doClientAddGroup/{clientId}")
    public Result doClientAddGroup(@PathVariable String clientId, @RequestBody List<String> groupIds) {
        return Result.build(() -> groupService.doClientAddGroup(clientId, groupIds));
    }

    @GetMapping(value = "/doClientAddGroup/{clientId}/{groupId}")
    public Result doClientAddGroup(@PathVariable String clientId, @PathVariable String groupId) {
        R r = groupService.doClientAddGroup(clientId, groupId);
        if(r.getState()== HttpStatus.OK.value()){
            return Result.success();
        }
        return Result.toast(r.getMessage());
    }
}
