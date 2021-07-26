package com.mazentop.modules.evaluation.commond;

import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.entity.OrdSalesOrder;
import com.mztframework.commond.PageCommond;
import com.mztframework.commons.Lists;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zhoumei
 * @title: EvaOrderCommond
 * @description: 测评-订单
 * @date 2021/01/8
 */
@Data
public class EvaOrderCommond extends PageCommond {

//    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_STATUS, alias = EvaOrdOrder.TABLE_NAME)
    private String status;

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_STATUS, alias = EvaOrdOrder.TABLE_NAME)
    private String statusType;

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_ORDER_NO, alias = EvaOrdOrder.TABLE_NAME)
    private String orderNo;

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_TAG, alias = EvaOrdOrder.TABLE_NAME)
    private String tag;

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_ID, alias = EvaOrdOrder.TABLE_NAME)
    private String id;

    @Criteria(expression = Expression.LIKE, property = EvaOrdOrder.F_AMAZON_ORDER_NO, alias = EvaOrdOrder.TABLE_NAME)
    private String amazonOrderNo;

    @Criteria(expression = Expression.SQL, sql = "  and add_user_id in (select id from cli_clientele_info where email like :email)")
    private String email;

    @Criteria(expression = Expression.SQL, sql = "  and product_sku in (select product_sku from eva_pro_product where country_id  =:countryId)")
    private String countryId;

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_IS_ENABLE, alias = EvaOrdOrder.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.BETWEEN, property = EvaOrdOrder.F_ADD_TIME, alias = EvaOrdOrder.TABLE_NAME)
    private SearchTime addTime;

    private Long startTime;

    private Long endTime;

    @Criteria(expression = Expression.IN, property = EvaOrdOrder.F_STATUS, alias = EvaOrdOrder.TABLE_NAME)
    private List<Integer> orderStatusList;


    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_FK_CLIENTELE_ID, alias = EvaOrdOrder.TABLE_NAME)
    private String clientId;


    public void setStatus(String status) {
        this.status = status;
        switch (status) {
            // 待处理
            case "pending": {
                this.setOrderStatusList(Arrays.asList(1));
                break;
            }
            // 待审核
            case "wait": {
                this.setOrderStatusList(Lists.newArrayList(2,3,4));
                break;
            }
            // 审核中
            case "review": {
                this.setOrderStatusList(Lists.newArrayList(5,6,7));
                break;
            }
            // 已完成
            case "completed": {
                this.setOrderStatusList(Lists.newArrayList(6));
                break;
            }
            // 已关闭
            case "closed": {
                this.setOrderStatusList(Arrays.asList(8));
                break;
            }
            default : {
                this.setOrderStatusList(null);
                break;
            }
        }
    }

}
