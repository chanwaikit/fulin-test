package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.model.PaymentOfflineStateEnum;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class OrdPaymentOfflineRecordService {

    public Result doEditOfflinePaymentRecord(OrdPaymentOfflineRecord paymentRecord) {
        String curUserId = Subject.id();
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (paymentRecord.getPaymentState().equals(PaymentOfflineStateEnum.PAYMENT_STATE_SUCCESS.state())) {
            OrdOrderLogisticsStatus ordOrderLogisticsStatus = new OrdOrderLogisticsStatus();
            ordOrderLogisticsStatus.setIsSign(BooleanEnum.FALSE.getValue());
            ordOrderLogisticsStatus.setAddUserId(curEmployee.getAddUserId());
            ordOrderLogisticsStatus.setAddUserName(curEmployee.getEmployeeName());
            ordOrderLogisticsStatus.setAddTime(Utils.currentTimeSecond());
            ordOrderLogisticsStatus.setFkSalesOrderId(paymentRecord.getFkSalesOrderId());
            ordOrderLogisticsStatus.setSalesOrderStatus("已支付");
            ordOrderLogisticsStatus.insert();
            OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setId(paymentRecord.getFkSalesOrderId());
            ordSalesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_ING_COMPLETE.status());
            ordSalesOrder.update();
        }
        paymentRecord.setOperationTime(Utils.currentTimeSecond());
        paymentRecord.setOperationUserId(curEmployee.getId());
        paymentRecord.setOperationUserName(curEmployee.getEmployeeName());
        paymentRecord.update();
        return Result.success();
    }
}
