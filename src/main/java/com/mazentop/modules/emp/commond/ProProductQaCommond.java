package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProProductQa;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

/**
 * @author: zhoumei
 * @date: 2020/8/20
 * @description: 商品问答
 */
@Data
public class ProProductQaCommond extends PageCommond {

    private static final long serialVersionUID = 4068241153620949658L;

    @Criteria(expression = Expression.EQ, property = ProProductQa.F_SHOP_ID,alias = ProProductQa.TABLE_NAME)
    private String shopId;

    @Criteria(expression = Expression.EQ, property = ProProductQa.F_FK_PRODUCT_ID, alias = ProProductQa.TABLE_NAME)
    private String productId;

    @Criteria(expression = Expression.EQ, property = ProProductQa.F_FK_CLIENTELE_ID, alias = ProProductQa.TABLE_NAME)
    private String fkClienteleId;

    @Criteria(expression = Expression.SQL,sql = " and fk_product_id in(select id from pro_product_master m where m.product_name like :productName)")
    private String productName;

    @Criteria(expression = Expression.EQ, property = ProProductQa.F_IS_DISPLAY, alias = ProProductQa.TABLE_NAME)
    private Integer isDisplay;

    private Long startTime;

    private Long endTime;

    @Criteria(expression = Expression.BETWEEN,property = ProProductQa.F_PROBLEM_TIME,alias = ProProductQa.TABLE_NAME)
    private SearchTime problemTime;

    public ProProductQaCommond() {
        this.setPageSize(10);
    }
}
