package com.mazentop.modules.emp.controller;


import com.mazentop.entity.ProProductCommon;
import com.mazentop.modules.emp.commond.productCommonCommond;
import com.mazentop.modules.emp.service.ProProductCommonService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/productCommon")
@Api(value = "/option/v1", tags = "商品描述", description = "ProProductCommondController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProProductCommondController {

    @Autowired
    ProProductCommonService describeService;

    @ApiOperation("分頁")
    @GetMapping("/list")
    public Result getPage(productCommonCommond commond) {
        return Result.build(() -> describeService.getPage(commond));
    }


    @ApiOperation("新增编辑")
    @PostMapping(value = "/addOrUpdate")
    public Result addOrUpdate(@RequestBody ProProductCommon productCommon) {
        return Result.build(() -> describeService.addOrUpdate(productCommon));
    }


    @ApiOperation("获取单个")
    @GetMapping(value = "/getProductCommon/{id}")
    public Result getProductCommon( @PathVariable String id) {
        return Result.build(() -> describeService.getProductCommon(id));
    }


    @ApiOperation("删除")
    @ResponseBody
    @PostMapping(value = "/delProductCommon")
    public Result delProductCommon(@RequestBody List<String> ids) {
        return Result.build(() -> describeService.delProductCommon(ids));
    }

}
