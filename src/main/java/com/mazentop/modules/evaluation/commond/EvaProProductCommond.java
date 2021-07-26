package com.mazentop.modules.evaluation.commond;

import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProProductMaster;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 *
 * @author zhoumei
 * @title: EvaProProductCommond
 * @description: 测评-商品
 * @date 2021/01/8
 */
@Data
public class EvaProProductCommond extends PageCommond {

    @Criteria(expression = Expression.SQL, sql = " and eva_pro_product.id in (select fk_product_id from pro_product_shortcut cut where cut.fk_product_type_id = :fkProductTypeId)")
    private String fkProductTypeId;

    @Criteria(expression = Expression.LIKE, property = EvaProProduct.F_PRODUCT_NAME, alias = EvaProProduct.TABLE_NAME)
    private String productName;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_IS_SHELVE, alias = EvaProProduct.TABLE_NAME)
    private Integer isShelve;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_IS_ENABLE, alias = EvaProProduct.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.LIKE,property = EvaProProduct.F_PRODUCT_SKU, alias = EvaProProduct.TABLE_NAME)
    private String productSku;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_FK_TAG_ID, alias = EvaProProduct.TABLE_NAME)
    private String fkTagId;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_COUNTRY_ID, alias = EvaProProduct.TABLE_NAME)
    private String countryId;

    @Criteria(expression = Expression.EQ, property = EvaProProduct.F_BUYING_PATTERNS, alias = EvaProProduct.TABLE_NAME)
    private String buyingPatterns;

    private String o;

}
