package com.mazentop.modules.user.commond;

import com.mazentop.entity.EvaWithdrawal;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class EvaluationWithdrawalCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = EvaWithdrawal.F_FK_CLIENTELE_ID, alias = EvaWithdrawal.TABLE_NAME)
    private String fkClienteleId;


    @Criteria(expression = Expression.EQ, property = EvaWithdrawal.F_TYPE, alias = EvaWithdrawal.TABLE_NAME)
    private Integer type;


    @Criteria(expression = Expression.EQ, property = EvaWithdrawal.F_STATUS, alias = EvaWithdrawal.TABLE_NAME)
    private Integer status;



}
