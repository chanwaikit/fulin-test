package com.mazentop.modules.emp.dto.query;

import com.mazentop.entity.ProProductMaster;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class ProductMasterQueryDto extends BaseCommond {

    @Criteria(expression = Expression.LIKE, property = ProProductMaster.F_PRODUCT_NAME, alias = ProProductMaster.TABLE_NAME)
    private String productName;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_FK_PRODUCT_TYPE_ID, alias = ProProductMaster.TABLE_NAME)
    private String fkProductTypeId;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_IS_SHELVE, alias = ProProductMaster.TABLE_NAME)
    private Integer isShelve;

    @Criteria(expression = Expression.EQ, property = ProProductMaster.F_IS_ENABLE, alias = ProProductMaster.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.IN, property = ProProductMaster.F_ID, alias = ProProductMaster.TABLE_NAME)
    private List<String> ids;

}
