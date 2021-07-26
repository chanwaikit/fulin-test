package com.mazentop.modules.emp.controller;

import com.mazentop.entity.BloBlogType;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.modules.emp.commond.BloBlogTypeCommond;
import com.mazentop.modules.emp.commond.CliClienteleInfoCommond;
import com.mazentop.modules.emp.service.BloBlogTypeService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
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
 * @author: wangzy
 * @date: 2020/3/11
 * @description:
 */

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/bloblogtype")
@Api(value = "/option/v1", tags = "博客:博客分类", description = "BloBlogTypeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BloBlogTypeController {

    @Autowired
    BloBlogTypeService bloBlogTypeService;

    @Log("博客分类列表")
    @GetMapping(value = "/bloBlogTypeList")
    @ApiOperation(value = "博客分类列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bloBlogTypeCommond", value = "查询条件", required = true, dataType = "BloBlogTypeCommond")
    })
    public Result bloBlogTypeList(BloBlogTypeCommond bloBlogTypeCommond) {
        return Result.build(() ->  bloBlogTypeService.bloBlogTypeList(bloBlogTypeCommond));
    }

    @Log("查询博客分类")
    @GetMapping(value = "/getBloBlogType/{id}")
    @ApiOperation(value = "查询博客分类", notes = "入参:查询条件")
    public Result getBloBlogType(@PathVariable String id) {
        return Result.build(() ->  {
            BloBlogType bloBlogType = BloBlogType.me().setId(id).get();
            return bloBlogType;
        });
    }

    @Log("新增/修改博客类型信息")
    @PostMapping(value = "/doBloBlogTypeAddOrUpdate")
    @ApiOperation(value = "新增/修改博客类型信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bloBlogType", value = "保存参数", required = true, dataType = "BloBlogType")
    })
    public Result doBloBlogTypeAddOrUpdate(@RequestBody BloBlogType bloBlogType) {
        return Result.build(() -> bloBlogTypeService.doBloBlogTypeAddOrUpdate(bloBlogType));
    }

    @Log("批量删除博客类型")
    @PostMapping(value = "/deleteBloBlogType")
    @ApiOperation(value="批量删除博客类型", notes="入参:主键 博客类型ID 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 博客类型ID 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteBloBlogType(@RequestBody List<String> ids){
        return Result.build(() -> bloBlogTypeService.deleteBloBlogType(ids));
    }
    @Log("删除博客类型")
    @GetMapping("/delete")
    @ApiOperation(value="删除博客类型", notes="入参:博客类型主键id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true,allowMultiple=true, dataType = "String")
    })
    public Result delete(String id) {
        return Result.build(() -> bloBlogTypeService.delete(id));
    }


    @Log("查询博客分类集合")
    @GetMapping(value = "/getBloBlogTypeList")
    @ApiOperation(value = "查询博客分类集合", notes = "入参:查询条件")
    public Result getBloBlogTypeList() {
        return Result.build(() ->  {
            List<BloBlogType> list = BloBlogType.me().setCompanyId(Subject.group()).find();
            return list;
        });
    }
}
