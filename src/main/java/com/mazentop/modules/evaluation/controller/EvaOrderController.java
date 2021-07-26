package com.mazentop.modules.evaluation.controller;


import com.mazentop.entity.EvaCashBackApply;
import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.modules.emp.dto.EvaOrderDto;
import com.mazentop.modules.evaluation.commond.EvaOrderCommond;
import com.mazentop.modules.evaluation.service.EvaOrderService;
import com.mazentop.modules.user.service.EvaluationOrderService;
import com.mazentop.modules.user.service.OrderService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhoumei
 * @title: OrderController
 * @description: 商品管理
 * @date 2021/1/6 10:22
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/evaOrder")
@Api(value = "/option/v1", tags = "订单管理", description = "OrderController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaOrderController {

    @Autowired
    EvaOrderService orderService;

    @GetMapping(value = "/getOrderList")
    @ApiOperation(value = "订单表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commond", value = "查询条件", required = true, dataType = "EvaOrderCommond")
    })
    public Result getOrderList(EvaOrderCommond commond) {
        return Result.build(() -> orderService.getOrderList(commond));
    }


    @GetMapping(value = "/getOrderStatus")
    @ApiOperation(value = "获取订单状态数量", notes = "入参:查询条件")
    public Result getOrderStatus() {
        return Result.build(() -> orderService.getOrderStatus());
    }


    @GetMapping(value = "/getOrderDetail/{orderId}")
    @ApiOperation(value = "获取订单详情", notes = "入参:查询条件")
    public Result getOrderInfo(@PathVariable  String orderId) {
        return Result.build(() -> orderService.getOrderDetail(orderId));
    }


    @PostMapping(value = "/examineApply")
    @ApiOperation(value = "亚马逊信息审核")
    public Result examineApply(@RequestBody EvaOrderDto dto) {
        return Result.build(() -> orderService.doExamineApply(dto));
    }

    /**
     * 关闭订单
     * @param id
     * @return
     */
    @PostMapping("/close/{id}")
    @ResponseBody
    public Result closeOrder(@PathVariable String id) {
        R r = orderService.doCloseOrder(id);
        if (r.getState()== HttpStatus.OK.value()){
            return Result.success();
        }
        return Result.toast(r.getMessage());
    }



}
