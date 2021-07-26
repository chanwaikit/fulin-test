package com.mazentop.modules.user.commond;

import com.mazentop.entity.ActGetCouponRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class ActGetCouponRecordCommond extends PageCommond {

    @Criteria(expression = Expression.EQ
            , property = ActGetCouponRecord.F_FK_CLIENTELE_ID
            , alias = ActGetCouponRecord.TABLE_NAME)
    private String clientId;

    @Criteria(expression = Expression.EQ
            , property = ActGetCouponRecord.F_IS_USE
            , alias = ActGetCouponRecord.TABLE_NAME)
    private Integer use;

}
