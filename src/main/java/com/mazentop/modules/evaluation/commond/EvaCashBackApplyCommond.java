package com.mazentop.modules.evaluation.commond;

import com.mazentop.entity.EvaCashBackApply;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

/**
 *
 * @author zhoumei
 * @title: EvaCashBackApplyCommond
 * @description: 测评-返现申请
 * @date 2021/01/8
 */
@Data
public class EvaCashBackApplyCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = EvaCashBackApply.F_STATUS, alias = EvaCashBackApply.TABLE_NAME)
    private Integer status;

    @Criteria(expression = Expression.SQL, sql = "and eva_order_id in (select id from eva_ord_order where amazon_order_no like :orderNo)")
    private String orderNo;

    @Criteria(expression = Expression.BETWEEN, property = EvaCashBackApply.F_ADD_TIME, alias = EvaCashBackApply.TABLE_NAME)
    private SearchTime addTime;

    @Criteria(expression = Expression.SQL, sql = "  and eva_order_id in ( select id from eva_ord_order where   product_sku in (select product_sku from eva_pro_product where country_id  =:countryId)")
    private String countryId;

    @Criteria(expression = Expression.SQL, sql = "  and eva_order_id in ( select id from eva_ord_order where   product_sku like :asin)")
    private String asin;

    private Long startTime;

    private Long endTime;

}
