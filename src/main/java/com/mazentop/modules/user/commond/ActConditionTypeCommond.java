package com.mazentop.modules.user.commond;

import com.mazentop.entity.ActConditionType;
import com.mazentop.entity.ActCouponActivity;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class ActConditionTypeCommond extends PageCommond {

    @Criteria(expression = Expression.LE, property = ActConditionType.F_TYPE_CONDITION, alias = ActConditionType.TABLE_NAME)
    private Long typeCondition;

    @Criteria(expression = Expression.EQ, property = ActConditionType.F_FK_ACTIVITY_ID, alias = ActConditionType.TABLE_NAME)
    private String activityId;
}
