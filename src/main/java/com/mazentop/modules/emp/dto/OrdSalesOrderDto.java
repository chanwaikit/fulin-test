package com.mazentop.modules.emp.dto;

import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.entity.OrdSalesOrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrdSalesOrderDto extends OrdSalesOrder {

    List<OrdSalesOrderDetail> detailList;
}
