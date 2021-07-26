package com.mazentop.modules.emp.dto.query;

import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProProductMaster;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class EvaProductQueryDto extends BaseCommond {

    @Criteria(expression = Expression.LIKE, property = EvaProProduct.F_PRODUCT_NAME, alias = EvaProProduct.TABLE_NAME)
    private String productName;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_IS_SHELVE, alias = EvaProProduct.TABLE_NAME)
    private Integer isShelve;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_IS_ENABLE, alias = EvaProProduct.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.IN, property = EvaProProduct.F_ID, alias = EvaProProduct.TABLE_NAME)
    private List<String> ids;

    /**
     * 标签编号
     */
    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_FK_TAG_ID, alias = EvaProProduct.TABLE_NAME)
    private String fkTagId;

    /**
     * 国家
     */
    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_COUNTRY_ID, alias = EvaProProduct.TABLE_NAME)
    private String countryId;



}
