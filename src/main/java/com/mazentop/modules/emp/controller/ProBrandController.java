package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProBrand;
import com.mazentop.modules.emp.commond.ProBrandCommond;
import com.mazentop.modules.emp.service.ProBrandService;
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
 * @author: wangzy
 * @date: 2020/3/12
 * @description:
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/proBrand")
@Api(value = "/option/v1", tags = "商品：品牌管理", description = "ProBrandController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProBrandController {

    @Autowired
    ProBrandService proBrandService;

    @Log("查询品牌信息")
    @GetMapping(value = "/findProBrands")
    @ApiOperation(value = "查询品牌信息", notes = "入参:查询条件")
    public Result findProBrands() {
        return Result.build(() ->  proBrandService.findProBrands());
    }


    @Log("查询品牌信息(分页)")
    @GetMapping(value = "/findProBrandList")
    @ApiOperation(value = "查询品牌信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proBrandCommond", value = "查询条件", required = true, dataType = "ProBrandCommond")
    })
    public Result findProBrandList(ProBrandCommond proBrandCommond) {
        return Result.build(() ->  proBrandService.findProBrandList(proBrandCommond));
    }

    @Log("新增/修改品牌信息")
    @PostMapping(value = "/doProBrandAddOrUpdate")
    @ApiOperation(value = "新增/修改品牌信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proBrand", value = "保存参数", required = true, dataType = "ProBrand")
    })
    public Result doProBrandAddOrUpdate(@RequestBody ProBrand proBrand) {
        if(Objects.isNull(proBrand)){
            return Result.toast("参数为空");
        }
        return Result.build(() -> proBrandService.doProBrandAddOrUpdate(proBrand));
    }



    @Log("查询单个品牌信息")
    @GetMapping(value = "/getProBrand/{id}")
    @ApiOperation(value = "查询单个品牌信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getProBrand(@PathVariable String id) {
        return Result.build(() -> ProBrand.me().setId(id).get());
    }



    @Log("删除品牌")
    @PostMapping("/deleteProBrand")
    @ApiOperation(value="删除品牌信息", notes="入参:品牌主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result deleteProBrand(@RequestBody List<String> ids) {
        return Result.build(() -> proBrandService.doDeleteProBrand(ids));
    }
}
