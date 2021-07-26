package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.entity.OrdSalesOrderOperateRecord;
import com.mztframework.commons.Utils;
import org.springframework.stereotype.Service;

/**
 * @author: dengy
 * @date: 2020/3/14
 * @description:
 */
@Service
public class OrdSalesOrderOperateRecordService {

    /**
     * 新增订单操作记录
     * @param ordSalesOrder
     * @param curEmployee
     */
    public void doSaveOperateRecord(OrdSalesOrder ordSalesOrder, EmpEmployeeInfo curEmployee){
        OrdSalesOrderOperateRecord ordSalesOrderOperateRecord =new OrdSalesOrderOperateRecord();
        ordSalesOrderOperateRecord.setAddTime(Utils.currentTimeSecond());
        ordSalesOrderOperateRecord.setOperationTime(Utils.currentTimeSecond());
        ordSalesOrderOperateRecord.setAddUserId(curEmployee.getId());
        ordSalesOrderOperateRecord.setAddUserName(curEmployee.getEmployeeName());
        ordSalesOrderOperateRecord.setOperationUserId(curEmployee.getId());
        ordSalesOrderOperateRecord.setOperationUserName(curEmployee.getEmployeeName());
        ordSalesOrderOperateRecord.setFkSalesOrderId(ordSalesOrder.getSalesOrderNo());
        ordSalesOrderOperateRecord.setSalesOrderStatus(ordSalesOrder.getSalesOrderStatus());
        ordSalesOrderOperateRecord.insert();
    }

}
