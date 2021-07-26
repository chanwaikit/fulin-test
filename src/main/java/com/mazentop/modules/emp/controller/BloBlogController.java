package com.mazentop.modules.emp.controller;

import com.mazentop.entity.*;
import com.mazentop.modules.emp.commond.BloBlogCommond;
import com.mazentop.modules.emp.dto.BloBlogDto;
import com.mazentop.modules.emp.service.BloBlogService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.R;
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
 * @author: wangzy
 * @date: 2020/3/11
 * @description:
 */

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/bloblog")
@Api(value = "/option/v1", tags = "博客:博客管理", description = "BloBlogController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BloBlogController {

    @Autowired
    BloBlogService bloBlogService;

    @Log("查询博客列表")
    @GetMapping(value = "/findBloBlogList")
    @ApiOperation(value = "查询博客列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bloBlogCommond", value = "查询条件", required = true, dataType = "BloBlogCommond")
    })
    public Result findBloBlogList(BloBlogCommond bloBlogCommond) {
        return Result.build(() -> bloBlogService.findBloBlogList(bloBlogCommond));
    }

    @Log("新增/修改博客信息")
    @PostMapping(value = "/doBloBlogAddOrUpdate")
    @ApiOperation(value = "新增/修改博客信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bloBlog", value = "保存参数", required = true, dataType = "BloBlog")
    })
    public Result doBloBlogAddOrUpdate(@RequestBody BloBlogDto bloBlog) {

        if (Objects.isNull(bloBlog)) {
            return Result.toast("传参为空");
        }

        return Result.build(() -> bloBlogService.doBloBlogAddOrUpdate(bloBlog));
    }

    @Log("查询单个博客信息")
    @GetMapping(value = "/getBloBlog/{id}")
    @ApiOperation(value = "查询单个博客信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getBloBlog(@PathVariable String id) {
        return Result.build(() ->  {
            BloBlog bloBlog = BloBlog.me().setId(id).get();
            bloBlog.addExten("type", BloBlogType.me().setId(bloBlog.getFkTypeId()).get());
            return bloBlog;
        });
    }

    @Log("删除博客")
    @PostMapping("/deleteBloBlog")
    @ApiOperation(value="删除博客信息", notes="入参:博客主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result deleteBloBlog(@RequestBody List<String> ids) {
        return Result.build(() -> bloBlogService.doDeleteBloBlog(ids));
    }
}
