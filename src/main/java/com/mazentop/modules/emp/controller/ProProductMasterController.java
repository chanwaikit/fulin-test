package com.mazentop.modules.emp.controller;


import com.mazentop.entity.ProProductMaster;
import com.mazentop.listener.MessageSource;
import com.mazentop.modules.emp.commond.ProProductMasterCommond;
import com.mazentop.modules.emp.dto.ProProductMasterDto;
import com.mazentop.modules.emp.dto.ProductMasterIsShelveDto;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.plugins.event.EventHolder;
import com.mazentop.plugins.event.Message;
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

/**
 * @author dengy
 * @title: ProProductMasterController
 * @description: 商品管理
 * @date 2020/3/13 10:22
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/proProductMaster")
@Api(value = "/option/v1", tags = "商品管理", description = "ProProductMasterController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProProductMasterController {

    @Autowired
    ProProductMasterService proProductMasterService;


    @Log("商品管理列表")
    @GetMapping(value = "/proProductMasterList")
    @ApiOperation(value = "商品管理列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proProductMasterCommond", value = "查询条件", required = true, dataType = "ProProductMasterCommond")
    })
    public Result proProductMasterList(ProProductMasterCommond commond) {
        return Result.build(() -> proProductMasterService.proProductMasterList(commond));
    }



    @Log("商品管理列表")
    @GetMapping(value = "/getProductPage")
    @ApiOperation(value = "商品管理列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proProductMasterCommond", value = "查询条件", required = true, dataType = "ProProductMasterCommond")
    })
    public Result getProductPage(ProProductMasterCommond commond) {
        return Result.build(() -> proProductMasterService.getProductPage(commond));
    }



    @Log("获取商品状态数量")
    @GetMapping(value = "/getProductStatus")
    @ApiOperation(value = "获取商品状态数量", notes = "入参:查询条件")
    public Result getProductStatus() {
        return Result.build(() -> proProductMasterService.getProductStatus());
    }



    @PostMapping(value = "/doProProductMasterAddOrUpdate")
    @ApiOperation(value = "新增/修改商品信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proProductMaster", value = "保存参数", required = true, dataType = "ProProductMasterDto")
    })
    public Result doProProductMasterAddOrUpdate(@RequestBody ProProductMasterDto proProductMaster) {
        Result result = proProductMasterService.doProProductMasterAddOrUpdate(proProductMaster,"1");
        if (result.isSuccess()){
            if (proProductMaster.getIsShelve() == 1) {
                EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, proProductMaster.getId()));
            }
            if (proProductMaster.getIsShelve() == 0) {
                EventHolder.publishEvent(new Message(MessageSource.PRODUCT_DEL, proProductMaster.getId()));
            }
        }
        return result;
    }


    @Log("查询单个商品信息")
    @GetMapping(value = "/getProductMasterInfo/{id}")
    @ApiOperation(value = "查询单个商品信息", notes = "入参:查询条件")
    public Result getProductMasterInfo(@PathVariable String id) {
        return Result.build(() -> proProductMasterService.getProductMasterInfo(id));
    }



    @Log("删除商品信息")
    @PostMapping(value = "/deleteProductMaster")
    @ApiOperation(value = "删除商品信息", notes = "入参:主键 商品ID 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 商品ID 数组", required = true, allowMultiple = true, dataType = "String")
    })
    public Result deleteProductMaster(@RequestBody List<String> ids) {
        return Result.build(proProductMasterService.doDeleteProductMaster(ids));
    }




    @GetMapping("/deleteProductColorOrSize")
    @ApiOperation(value="删除商品规格信息", notes="入参:顾客主键id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteProductColorOrSize(String id) {
        return Result.build(() -> proProductMasterService.doDeleteProductColorOrSize(id));
    }



    @GetMapping(value = "/getSku")
    @ApiOperation(value = "获取系统生成SKU信息", notes = "无入参")
    public Result getSku() {
        return Result.build(() -> proProductMasterService.getCode());
    }



    @PostMapping(value = "/updateIsShelve")
    @ApiOperation(value = "批量上下架", notes = "入参:主键 商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productMasterIsShelveDto", value = "商品", required = true, allowMultiple = true, dataType = "ProductMasterIsShelveDto")
    })
    public Result updateIsShelve(@RequestBody ProductMasterIsShelveDto productMasterIsShelveDto) {
        return Result.build(() -> proProductMasterService.updateIsShelve(productMasterIsShelveDto));
    }



    @PostMapping(value = "/updateIsSales")
    @ApiOperation(value = "批量修改销量", notes = "入参:主键 商品")
    public Result updateSales(@RequestBody ProductMasterIsShelveDto dto){
        if (dto.getIds()==null||dto.getIds().size()<=0||dto.getSales()==null){
            return Result.success();
        }
        proProductMasterService.updateSales(dto.getIds(),dto.getSales());
        return Result.success();
    }


    @GetMapping(value = "/copy/{id}")
    @ApiOperation(value = "复制商品")
    public Result copyProduct(@PathVariable("id")String id) {
        return Result.build(() -> proProductMasterService.doCopyProduct(id));
    }


    @PostMapping(value = "/updateProductTag")
    @ApiOperation(value = "修改商品标签", notes = "入参:主键 商品")
    public Result updateProductTag(@RequestBody ProProductMaster productMaster){
        productMaster.update();
        return Result.success();
    }
}
