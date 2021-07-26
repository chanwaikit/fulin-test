package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProProductStock;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

import java.util.List;

@Data
public class ProProductStockCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = ProProductStock.F_FK_PRODUCT_ID, alias = ProProductStock.TABLE_NAME)
    private String productId;


    @Criteria(expression = Expression.IN,property = ProProductStock.F_ID, alias = ProProductStock.TABLE_NAME)
    private List<String> ids;

    /**
     * 是否启用
     */
    @Criteria(expression = Expression.EQ, property = ProProductStock.F_IS_ENABLE, alias = ProProductStock.TABLE_NAME)
    private Integer isEnable;


    /**
     * sku
     */
    @Criteria(expression = Expression.LIKE, property = ProProductStock.F_PRODUCT_SUB_SKU, alias = ProProductStock.TABLE_NAME)
    private String productSubSku;


    private String o;

}
