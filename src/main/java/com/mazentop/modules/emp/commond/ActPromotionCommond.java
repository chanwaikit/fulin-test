package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ActPromotionActivity;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

@Data
public class ActPromotionCommond extends BaseCommond {

    @Criteria(expression = Expression.EQ,property = ActPromotionActivity.F_ID,alias = ActPromotionActivity.TABLE_NAME)
    private String id;

    @Criteria(expression = Expression.EQ,property = ActPromotionActivity.F_ACTIVITY_STATUS,alias = ActPromotionActivity.TABLE_NAME)
    private String type;


    @Criteria(expression = Expression.GE,property = ActPromotionActivity.F_END_TIME,alias = ActPromotionActivity.TABLE_NAME)
    private Long endTime;



}
