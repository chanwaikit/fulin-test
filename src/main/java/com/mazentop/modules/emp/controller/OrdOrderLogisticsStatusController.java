package com.mazentop.modules.emp.controller;

import com.mazentop.entity.OrdOrderLogisticsStatus;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.modules.emp.dto.TransportsDto;
import com.mazentop.modules.emp.service.OrdOrderLogisticsStatusService;
import com.mazentop.util.Helper;
import com.mztframework.dao.annotation.Order;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/option/{api_version}/order/logistics")
@Api(tags = "物流管理")
public class OrdOrderLogisticsStatusController {

    @Autowired
    OrdOrderLogisticsStatusService ordOrderLogisticsStatusService;



    @Log("物流列表")
    @ApiOperation("物流列表")
    @GetMapping("/list/{orderId}")
    public Result orderLogistics(@PathVariable String orderId) {
        return Result.build(() -> OrdOrderLogisticsStatus.me().setFkSalesOrderId(orderId).setOrderByFields(Order.asc(ProProductMaster.F_ADD_TIME)).find());
    }

    @Log("编辑物流信息")
    @ApiOperation("编辑物流信息")
    @PostMapping("/add/transports")
    public Result addOrderTransports(@RequestBody TransportsDto dto) {
        if (Helper.isNotEmpty(dto)) {
            if (Helper.isEmpty(dto.getOrderId())) {
                return Result.toast("订单ID不能为空！");
            }
            if (Helper.isEmpty(dto.getTransportsChannelName())) {
                return Result.toast("物流公司不能为空！");
            }
            if (Helper.isEmpty(dto.getTransportsChannelId())) {
                dto.setTransportsChannelId("2");
            }
            if (Helper.isEmpty(dto.getTransportsNo())) {
                return Result.toast("物流单号不能为空！");
            }
            OrdOrderLogisticsStatus ordOrderLogisticsStatus=new OrdOrderLogisticsStatus();
            BeanUtils.copyProperties(dto, ordOrderLogisticsStatus);
            ordOrderLogisticsStatus.setFkSalesOrderId(dto.getOrderId());
            ordOrderLogisticsStatusService.doEditOrdOrderLogisticsStatus(ordOrderLogisticsStatus);

            return Result.success();
        }
        return Result.toast("参数异常");
    }


    @Log("物流签收")
    @ApiOperation("物流签收")
    @GetMapping("/order/transports/sing/{orderId}")
    public Result orderSign(@PathVariable String orderId) {
        OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setId(orderId).get();
        if (ordSalesOrder.getSalesOrderStatus().equals(OrdSalesOrderStatusEnum.DELIVER_COMPLETE.status())) {
            ordOrderLogisticsStatusService.doOrderSign(ordSalesOrder);
            return Result.success();
        }
        return Result.toast("订单状态异常！");
    }

}
