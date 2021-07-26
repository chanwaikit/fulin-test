package com.mazentop.modules.emp.controller;

import com.mazentop.entity.OrdPaymentOfflineRecord;
import com.mazentop.model.PaymentOfflineStateEnum;
import com.mazentop.modules.emp.dto.OrdOfflinePaymentRecordDto;
import com.mazentop.modules.emp.service.OrdPaymentOfflineRecordService;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/option/{api_version}/order/offline")
@Api(tags = "线下订单支付")
public class OrdOfflinePaymentRecordController {
    @Autowired
    OrdPaymentOfflineRecordService ordPaymentOfflineRecordService;

    @Log("获取支付详情")
    @ApiOperation("获取支付详情")
    @GetMapping("/get/order/offlie/{orderId}")
    public Result getOrdOfflinePaymentRecord(@PathVariable String orderId) {
        return Result.build(() ->
                OrdPaymentOfflineRecord.me().setFkSalesOrderId(orderId).get()
        );
    }


    @Log("审核线下支付")
    @ApiOperation("审核线下支付")
    @PostMapping("/edit/offline/payment/state")
    public Result editOrdOfflinePaymentRecord(@RequestBody OrdOfflinePaymentRecordDto dto) {
        if (Helper.isEmpty(dto.getOrderId())){
            return   Result.toast("订单id不能为空");
        }
        OrdPaymentOfflineRecord record = OrdPaymentOfflineRecord.me().setFkSalesOrderId(dto.getOrderId()).get();
        if (Helper.isEmpty(record)){
            return Result.toast("暂无信息！");
        }
        if (dto.getState().equals(PaymentOfflineStateEnum.PAYMENT_STATE_REJECT.state())){
            if (Helper.isEmpty(dto.getRemark())){
                return Result.toast("驳回信息不能为空！");
            }else {
                record.setRemark(dto.getRemark());
            }
        }
        record.setPaymentState(dto.getState());
        ordPaymentOfflineRecordService.doEditOfflinePaymentRecord(record);
        return Result.success();
    }



}
