package com.mazentop.modules.user.commond;

import com.mazentop.entity.OrdPaymentRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

@Data
public class OrdPaymentRecordCommond extends PageCommond {

    @Criteria(expression = Expression.EQ
            , property = OrdPaymentRecord.F_FK_CLIENTELE_ID
            , alias = OrdPaymentRecord.TABLE_NAME)
    private String clientId;


    @Criteria(expression = Expression.BETWEEN,property = OrdPaymentRecord.F_ADD_TIME,alias = OrdPaymentRecord.TABLE_NAME)
    private SearchTime addTime;


    @Criteria(expression = Expression.EQ, property = OrdPaymentRecord.F_IS_PAY_SUCCESS, alias = OrdPaymentRecord.TABLE_NAME)
    private Integer isPaySuccess;



}
