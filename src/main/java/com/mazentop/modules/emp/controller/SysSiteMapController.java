package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysNoticeSetting;
import com.mazentop.entity.SysSiteMap;
import com.mazentop.entity.SysSiteMapType;
import com.mazentop.modules.emp.commond.SysSiteMapCommod;
import com.mazentop.modules.emp.service.SysSiteMapService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/siteMap")
@Api(value = "/option/v1", tags = "网站地图：网站地图管理", description = "SysSiteMapController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysSiteMapController {
    @Autowired
    SysSiteMapService sysSiteMapService;

    @Log("网站地图类型获取")
    @ApiOperation("网站地图类型获取")
    @GetMapping(value = "/getSiteMapType")
    public Result getSiteMapTypeList() {
        return Result.build(() -> SysSiteMapType.me().find());
    }

    @Log("网站地图列表获取")
    @ApiOperation("网站地图列表获取")
    @GetMapping(value = "/findSiteMapList")
    public Result findSiteMapList( SysSiteMapCommod commod) {
         return Result.build(() ->{
             List<SysSiteMap> sysSiteMaps = SysSiteMap.me().find(commod);
            return new Page(sysSiteMaps,commod);
        });
    }

    @Log("网站地图新增/修改")
    @ApiOperation("网站地图新增/修改")
    @PostMapping(value = "/editSiteMap")
    public Result editSiteMap( @RequestBody SysSiteMap sysSiteMap) {
        sysSiteMapService.editSiteMap(sysSiteMap);
        return Result.success();
    }

    @Log("网站地图获取")
    @ApiOperation("网站地图获取")
    @GetMapping(value = "/getSiteMap/{id}")
    public Result getSiteMap(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(() -> SysSiteMap.me().setId(id).get());
    }

    @Log("网站地图删除")
    @ApiOperation("网站地图删除")
    @GetMapping(value = "/delSiteMap/{id}")
    public Result delSiteMap(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(() -> SysSiteMap.me().setId(id).deleteById());
    }

}
