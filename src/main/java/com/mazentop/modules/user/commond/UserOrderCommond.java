package com.mazentop.modules.user.commond;

import com.mazentop.entity.OrdSalesOrder;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

import java.util.List;

@Data
public class UserOrderCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = OrdSalesOrder.F_FK_CLIENTELE_ID, alias = OrdSalesOrder.TABLE_NAME)
    private String userId;

    @Criteria(expression = Expression.LIKE_R, property = OrdSalesOrder.F_SALES_ORDER_NO, alias = OrdSalesOrder.TABLE_NAME)
    private String orderNo;

    @Criteria(expression = Expression.EQ, property = OrdSalesOrder.F_IS_ENABLE, alias = OrdSalesOrder.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.EQ, property = OrdSalesOrder.F_SALES_ORDER_STATUS, alias = OrdSalesOrder.TABLE_NAME)
    private String orderStatus;

    @Criteria(expression = Expression.IN, property = OrdSalesOrder.F_SALES_ORDER_STATUS, alias = OrdSalesOrder.TABLE_NAME)
    private List<String> orderStatusList;
}
