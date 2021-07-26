package com.mazentop.modules.emp.controller;

import com.mazentop.entity.OrdOrderLogisticsStatus;
import com.mazentop.entity.OrdRefundRequest;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mztframework.commons.Utils;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 退款
 *
 * @author chen quan
 * @date 2020/4/14 18:23
 **/
@RestController
@RequestMapping(value = "/refundReview")
public class RefundReviewController {


    /**
     * 退款审核
     * @param id
     * @param flag
     * @return
     */
    @GetMapping("/audit")
    public Result refundOrderAudit(@RequestParam(value = "id") String id
            , @RequestParam(value = "flag") String flag) {

        Integer edit = OrdRefundRequest.me()
                .setId(id)
                .setRefundRequestStatus(flag)
                .setAuditTime( Utils.currentTimeSecond())
                .setAuditer(Subject.username())
                .setOperationTime( Utils.currentTimeSecond())
                .setOperationUserId(Subject.id())
                .setOperationUserName(Subject.username()).update();

        if(edit > 0){
            return Result.build(() -> "操作成功");
        }
        return Result.build(() -> "操作失败");
    }


    /**
     * 退款操作
     * @param ordRefundRequest
     * @return
     */
    @PostMapping("/refused")
    public Result refundOperation(@RequestBody OrdRefundRequest ordRefundRequest){
        if(ordRefundRequest.getRefundRequestStatus().equals("04")){
            OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setSalesOrderNo(ordRefundRequest.getSalesOrderNo()).get();
            if(Objects.nonNull(ordSalesOrder)){
                ordSalesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.CANCEL_COMPLETE.status()).update();
            }
            OrdOrderLogisticsStatus.me()
                    .setFkSalesOrderId(ordSalesOrder.getId())
                    .setSalesOrderStatus("已退款")
                    .setTransportsChannelName(ordSalesOrder.getTransportsChannelName())
                    .setTransportsChannelId(ordSalesOrder.getTransportsChannelId())
                    .setTransportsNo(ordSalesOrder.getTransportsNo())
                    .setAddTime(Utils.currentTimeSecond())
                    .setAddUserId(Subject.id())
                    .setAddUserName(Subject.username()).insert();
            
        }
        return Result.build(() -> ordRefundRequest.update());
    }

}
