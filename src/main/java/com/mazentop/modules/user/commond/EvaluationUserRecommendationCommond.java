package com.mazentop.modules.user.commond;

import com.mazentop.entity.EvaUserRecommendation;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class EvaluationUserRecommendationCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = EvaUserRecommendation.F_REFERRER_ID, alias = EvaUserRecommendation.TABLE_NAME)
    private String referrerId;

}
