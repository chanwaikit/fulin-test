package com.mazentop.modules.emp.controller;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.modules.emp.dto.UpdateOrdSalesOrderDto;
import com.mazentop.modules.emp.service.OrdSalesOrderDetailService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/14
 * @description:
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/ordSalesOrderDetail")
@Api(value = "/option/v1", tags = "订单：订单详情", description = "OrdSalesOrderDetailController", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdSalesOrderDetailController {

    @Autowired
    OrdSalesOrderDetailService ordSalesOrderDetailService;

    @Log("查询订单详情")
    @GetMapping(value = "/getOrderDetail/{id}")
    @ApiOperation(value = "查询订单详情", notes = "入参:订单ID")
    public Result getOrdSalesOrderDetail(@PathVariable String id) {

        if(StringUtils.isBlank(id)){
            return Result.toast("参数为空");
        }

        if (Objects.isNull(OrdSalesOrder.me().setId(id).get())){
            return Result.toast("订单数据丢失");
        }

        return Result.build(() ->  {
            return ordSalesOrderDetailService.getOrdSalesOrderDetail(id);
        });
    }

    @Log("更新订单状态")
    @PostMapping(value = "/updateOrdSalesOrder")
    @ApiOperation(value = "更新订单状态", notes = "入参:订单ID")
    public Result updateOrdSalesOrder (@RequestBody UpdateOrdSalesOrderDto updateOrdSalesOrderDto) {

        if(StringUtils.isBlank(updateOrdSalesOrderDto.getId()) || StringUtils.isBlank(updateOrdSalesOrderDto.getStatus())){
            return Result.toast("参数为空");
        }

        // 获取当前用户信息
        String curUserId = Subject.id();
        if(StringUtils.isBlank(curUserId)){
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)){
            R.error("当前用户失效");
        }

        // 取消订单
        if(OrdSalesOrderStatusEnum.CANCEL_COMPLETE.status().equals(updateOrdSalesOrderDto.getStatus())){
            ordSalesOrderDetailService.cancellationOfOrder(curEmployee,updateOrdSalesOrderDto);
        }

        // 已发货
        if(OrdSalesOrderStatusEnum.DELIVER_COMPLETE.status().equals(updateOrdSalesOrderDto.getStatus())){
            ordSalesOrderDetailService.shippedOfOrder(curEmployee,updateOrdSalesOrderDto);
        }

        OrdSalesOrder.me().setId(updateOrdSalesOrderDto.getId()).setSalesOrderStatus(updateOrdSalesOrderDto.getStatus()).update();

        return Result.success();
    }

}
