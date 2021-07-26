package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductType;
import com.mazentop.modules.emp.commond.ProProductTypeCoommond;
import com.mazentop.modules.emp.dto.ProductTypeDto;
import com.mazentop.modules.emp.service.ProProductTypeService;
import com.mazentop.plugins.theme.ThemeUtil;
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
 * @date: 2020/3/13
 * @description:
 */

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/proProductType")
@Api(value = "/option/v1", tags = "商品：分类管理", description = "ProProductTypeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProProductTypeController {

    @Autowired
    ProProductTypeService proProductTypeService;

    @Log("查询商品分类(树)")
    @GetMapping(value = "/findProProductTypeTreeList/{treeType}")
    @ApiOperation(value = "查询商品分类(树)", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proProductTypeCoommond", value = "查询条件", required = true, dataType = "ProProductTypeCoommond")
    })
    public Result findProProductTypeTreeList(@PathVariable String treeType) {
        return Result.build(() ->  proProductTypeService.findProProductTypeTreeList(treeType));
    }

    @Log("查询商品分类(根节点)")
    @GetMapping(value = "/findProProductTypeRootList")
    @ApiOperation(value = "查询商品分类(根节点)", notes = "入参:查询条件")
    public Result findProProductTypeRootList(ProProductTypeCoommond proProductTypeCoommond) {
        return Result.build(() ->  proProductTypeService.findProProductTypeRootList(proProductTypeCoommond));
    }

    @Log("查询商品分类(分页)")
    @GetMapping(value = "/findProProductTypeList")
    @ApiOperation(value = "查询商品分类(分页)", notes = "入参:查询条件")
    public Result findProProductTypeList(ProProductTypeCoommond proProductTypeCoommond) {
        return Result.build(() ->  proProductTypeService.findProProductTypeList(proProductTypeCoommond));
    }

    @Log("新增/修改商品分类")
    @PostMapping(value = "/doProProductTypeAddOrUpdate")
    @ApiOperation(value = "新增/修改商品分类", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proProductType", value = "保存参数", required = true, dataType = "ProProductType")
    })
    public Result doProProductTypeAddOrUpdate(@RequestBody ProductTypeDto proProductType) {
        if(Objects.isNull(proProductType)){
            return Result.toast("参数为空");
        }
        return Result.build(() -> proProductTypeService.doProProductTypeAddOrUpdate(proProductType));
    }


    @Log("查询单个商品分类")
    @GetMapping(value = "/getProProductType/{id}")
    @ApiOperation(value = "查询单个商品分类", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getProProductType(@PathVariable String id) {
        return Result.build(() -> proProductTypeService.getProProductType(id));
    }



    @Log("删除商品分类")
    @PostMapping("/deleteProProductType")
    @ApiOperation(value="删除商品分类", notes="入参:品牌主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result deleteProProductType(@RequestBody List<String> ids) {
        return proProductTypeService.doDeleteProductType(ids);
    }


    @Log("商品分类树(懒加载)")
    @GetMapping(value = "/lazyProTreeList/{pid}")
    @ApiOperation(value = "商品分类(懒加载)", notes = "入参:查询条件")
    public Result lazyProTreeList(@PathVariable String pid) {
        return Result.build(() ->  proProductTypeService.lazyProTreeList(pid));
    }
}
