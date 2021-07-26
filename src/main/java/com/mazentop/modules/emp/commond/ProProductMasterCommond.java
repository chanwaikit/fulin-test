package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProProductMaster;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

/**
 *
 * @author dengy
 * @title: ProProductMasterCommond
 * @description: 商品主表
 * @date 2020/03/13
 */
@Data
public class ProProductMasterCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = ProProductMaster.F_PRODUCT_NAME, alias = ProProductMaster.TABLE_NAME)
    private String productName;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_FK_PRODUCT_TYPE_ID, alias = ProProductMaster.TABLE_NAME)
    private String fkProductTypeId;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_IS_SHELVE, alias = ProProductMaster.TABLE_NAME)
    private Integer isShelve;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_IS_ENABLE, alias = ProProductMaster.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.LIKE,property = ProProductMaster.F_PRODUCT_SKU, alias = ProProductMaster.TABLE_NAME)
    private String productSku;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_FK_TAG_ID, alias = ProProductMaster.TABLE_NAME)
    private String fkTagId;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_ID,alias = ProProductMaster.TABLE_NAME)
    private String id;

    private String o;

}
