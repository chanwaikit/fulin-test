package com.mazentop.modules.evaluation.controller;


import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.modules.evaluation.commond.EvaProProductCommond;
import com.mazentop.modules.evaluation.dto.ProProductDto;
import com.mazentop.modules.evaluation.service.EvaProProductService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhoumei
 * @title: EvaProProductController
 * @description: 测评-商品管理
 * @date 2020/3/13 10:22
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/evaProduct")
@Api(value = "/option/v1", tags = "测评-商品管理", description = "ProProductController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaProProductController {

    @Autowired
    EvaProProductService productService;


    @PostMapping(value = "/doProProductAddOrUpdate")
    @ApiOperation(value = "新增/修改商品信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proProductMaster", value = "保存参数", required = true, dataType = "ProProductMasterDto")
    })
    public Result doProProductAddOrUpdate(@RequestBody ProProductDto proProductDto) {
        return productService.doProProductMasterAddOrUpdate(proProductDto);
    }


    @GetMapping(value = "/getProProductPage")
    @ApiOperation(value = "商品管理分页", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commond", value = "查询条件", required = true, dataType = "ProProductCommond")
    })
    public Result getProProductPage(EvaProProductCommond commond) {
        return Result.build(() -> productService.getProProductPage(commond));
    }


    @GetMapping(value = "/getProProductList")
    @ApiOperation(value = "商品管理列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commond", value = "查询条件", required = true, dataType = "ProProductCommond")
    })
    public Result getProProductList(EvaProProductCommond commond) {
        return Result.build(() -> productService.getProProductList(commond));
    }


    @GetMapping(value = "/getProductStatus")
    @ApiOperation(value = "获取商品状态数量", notes = "入参:查询条件")
    public Result getProductStatus() {
        return Result.build(() -> productService.getProductStatus());
    }


    @GetMapping(value = "/copy/{id}")
    @ApiOperation(value = "复制商品")
    public Result copyProduct(@PathVariable("id")String id) {
        return Result.build(() -> productService.doCopyProduct(id));
    }


    @PostMapping(value = "/deleteProduct")
    @ApiOperation(value = "删除商品信息", notes = "入参:主键 商品ID 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 商品ID 数组", required = true, allowMultiple = true, dataType = "String")
    })
    public Result deleteProduct(@RequestBody List<String> ids) {
        return Result.build(productService.deleteProduct(ids));
    }


    @GetMapping(value = "/getProductInfo/{id}")
    @ApiOperation(value = "查询单个商品信息", notes = "入参:查询条件")
    public Result getProductInfo(@PathVariable String id) {
        return Result.build(() -> productService.getProductInfo(id));
    }



    @PostMapping(value = "/updateIsShelve")
    @ApiOperation(value = "批量上下架", notes = "入参:主键 商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dto", value = "商品", required = true, allowMultiple = true, dataType = "ProProductDto")
    })
    public Result updateIsShelve(@RequestBody ProProductDto dto) {
        return Result.build(() -> productService.updateIsShelve(dto));
    }


    @GetMapping(value = "/getSku")
    @ApiOperation(value = "获取系统生成SKU信息", notes = "无入参")
    public Result getSku() {
        return Result.build(() -> productService.getCode());
    }


    @PostMapping(value = "/updateProductTag")
    @ApiOperation(value = "修改商品标签", notes = "入参:主键 商品")
    public Result updateProductTag(@RequestBody EvaProProduct proProduct){
        proProduct.update();
        return Result.success();
    }


}
