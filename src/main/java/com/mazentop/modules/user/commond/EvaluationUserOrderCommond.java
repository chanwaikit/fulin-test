package com.mazentop.modules.user.commond;

import com.mazentop.entity.EvaOrdOrder;
import com.mztframework.commond.PageCommond;
import com.mztframework.commons.Lists;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class EvaluationUserOrderCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_FK_CLIENTELE_ID, alias = EvaOrdOrder.TABLE_NAME)
    private String fkClienteleId;

    @Criteria(expression = Expression.LIKE, property = EvaOrdOrder.F_AMAZON_ORDER_NO, alias = EvaOrdOrder.TABLE_NAME)
    private String orderNo;

    private String status;

    @Criteria(expression = Expression.EQ, property = EvaOrdOrder.F_IS_ENABLE, alias = EvaOrdOrder.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.IN, property = EvaOrdOrder.F_STATUS, alias = EvaOrdOrder.TABLE_NAME)
    private List<Integer> orderStatusList;


    public void setStatus(String status) {
        this.status = status;
        switch (status) {
            case "submit-order": {
                this.setOrderStatusList(Lists.newArrayList(1,2,3,4));
                break;
            }
            case "pending": {
                this.setOrderStatusList(Arrays.asList(1));
                break;
            }
            case "submit-comments": {
                this.setOrderStatusList(Lists.newArrayList(5,6,7));
                break;
            }
            case "closed": {
                this.setOrderStatusList(Arrays.asList(8));
                break;
            }
            case "payable": {
                this.setOrderStatusList(Arrays.asList(6));
                break;
            }
            default : {
                this.setOrderStatusList(null);
                break;
            }
        }

    }
}
