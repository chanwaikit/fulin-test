package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductSpec;
import com.mazentop.entity.ProProductStock;
import com.mazentop.modules.emp.commond.ProProductStockCommond;
import com.mazentop.modules.emp.dto.ProProductStockDto;
import com.mazentop.modules.emp.service.ProProductStockService;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/product/stock")
@Api(value = "/option/v1", tags = "商品库存：库存管理", description = "ProProductStockController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProProductStockController {
    @Autowired
    ProProductStockService stockService;

    @Log("商品库存列表")
    @GetMapping(value = "/list")
    @ApiOperation(value = "商品库存列表")
    public Result getProProductStockList(ProProductStockCommond commond) {
        return Result.build(() ->  stockService.getProProductStockPage(commond) );
    }



    @Log("商品库存详情")
    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "商品库存详情")
    public Result getProProductStock(@PathVariable String id) {
        return Result.build(() -> stockService.getProProductStock(id));
    }


    @Log("编辑商品库存")
    @PostMapping(value = "/editProductStock")
    @ApiOperation(value = "编辑商品库存")
    public Result editProductStock(@RequestBody ProProductStock productStock) {
        return Result.build(() -> stockService.doEditProductStock(productStock));
    }


    @Log("商品规格类列表")
    @GetMapping(value = "/spec/list")
    @ApiOperation(value = "商品规格类列表")
    public Result getProProductSpecList() {
        return Result.build(() ->
                ProProductSpec.me().setParentId("0").find()
        );
    }


    @Log("编辑商品规格类")
    @PostMapping(value = "/edit/spec")
    @ApiOperation(value = "编辑商品规格类")
    public Result editProProductSpec(@RequestBody ProProductSpec proProductSpec) {
        return stockService.doEditProProductSpec(proProductSpec);
    }

    @Log("删除商品规格类")
    @GetMapping(value = "/del/spec/{id}")
    @ApiOperation(value = "删除商品规格类")
    public Result editProProductSpec(@PathVariable String id) {
        return stockService.delProProductSpec(id);
    }

    @Log("商品规格列表")
    @GetMapping(value = "/get/spec/item/list{parentId}")
    @ApiOperation(value = "商品规格列表")
    public Result getProProductSpecItemList(@PathVariable String parentId) {
        return Result.build(() ->
                ProProductSpec.me().setParentId(parentId).find()
        );
    }

    @Log("删除商品规格")
    @GetMapping(value = "/del/spec/item{id}")
    @ApiOperation(value = "删除商品规格")
    public Result editProProductSpecItem(@PathVariable String id) {
        return stockService.delProProductSpecItem(id);
    }
}
