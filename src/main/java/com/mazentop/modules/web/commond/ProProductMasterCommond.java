package com.mazentop.modules.web.commond;

import com.mazentop.entity.ProProductMaster;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

import java.util.List;

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

    @Criteria(expression = Expression.IN, property = ProProductMaster.F_FK_PRODUCT_TYPE_ID, alias = ProProductMaster.TABLE_NAME)
    private List<String> fkProductTypeId;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_IS_SHELVE, alias = ProProductMaster.TABLE_NAME)
    private Integer isShelve;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_IS_ENABLE, alias = ProProductMaster.TABLE_NAME)
    private Integer isEnable;

//    @Criteria(expression = Expression.BETWEEN,property = ProProductMaster.F_PRODUCT_MALL_PRICE,alias = ProProductMaster.TABLE_NAME)
//    private SearchTime productMallPrice;

    private String orderByNames;

    private String minProductMallPrice;

    private String maxProductMallPrice;

    private String rootTypeId;

}
