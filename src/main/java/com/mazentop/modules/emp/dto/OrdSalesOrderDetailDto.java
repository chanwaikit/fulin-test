package com.mazentop.modules.emp.dto;

import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.entity.OrdSalesOrderDetail;
import com.mazentop.entity.OrdSalesOrderOperateRecord;
import lombok.Data;

import java.util.List;

/**
 * @author: wangzy
 * @date: 2020/3/14
 * @description:
 */
@Data
public class OrdSalesOrderDetailDto extends OrdSalesOrder {

    private List<OrdSalesOrderDetail> ordSalesOrderDetailList;

    private List<OrdSalesOrderOperateRecord> ordSalesOrderOperateRecordList;
}
