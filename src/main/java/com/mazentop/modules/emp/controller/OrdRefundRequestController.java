package com.mazentop.modules.emp.controller;

import com.mazentop.entity.OrdRefundRequest;
import com.mazentop.model.OrdRefundRequestStatusEnum;
import com.mazentop.modules.emp.commond.OrdRefundRequestCommond;
import com.mazentop.modules.emp.service.OrdRefundRequestService;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengy
 * @title: OrdSalesOrderController
 * @description: 退款订单
 * @date 2020/3/14 15:24
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/ordRefundRequestInfo")
@Api(value = "/option/v1", tags = "订单:退款订单", description = "OrdRefundRequestController", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdRefundRequestController {

    @Autowired
    OrdRefundRequestService ordRefundRequestService;

    @Log("退款订单")
    @GetMapping(value = "/OrdRefundRequestInfoList")
    @ApiOperation(value = "退款订单", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ordRefundRequestCommond", value = "查询条件", required = true, dataType = "OrdRefundRequestCommond")
    })
    public Result ordSalesOrderList(OrdRefundRequestCommond ordRefundRequestCommond) {
        return Result.build(() ->  ordRefundRequestService.ordSalesOrderList(ordRefundRequestCommond));
    }
    @Log("查询单个退款订单信息")
    @GetMapping(value = "/getOrdRefundRequest/{id}")
    @ApiOperation(value = "查询单个退款订单信息", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })

    public Result getOrdRefundRequest(@PathVariable String id) {
        return Result.build(() ->  {
            OrdRefundRequest ordRefundRequest = OrdRefundRequest.me().setId(id).get();
            if(ordRefundRequest.getRefundAmount()!=null){
                ordRefundRequest.addExten("refundAmount", Helper.transformF2Y(ordRefundRequest.getRefundAmount()));
            }else{
                ordRefundRequest.addExten("refundAmount", 0);
            }
            ordRefundRequest.addExten("refundStatus", OrdRefundRequestStatusEnum.getDesc(ordRefundRequest.getRefundRequestStatus()));
            return ordRefundRequest;
        });
    }
}
