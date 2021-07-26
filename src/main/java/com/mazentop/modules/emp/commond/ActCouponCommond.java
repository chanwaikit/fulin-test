package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ActCouponActivity;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/18
 * @description:
 */
@Data
public class ActCouponCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = ActCouponActivity.F_ACTIVITY_NAME, alias = ActCouponActivity.TABLE_NAME)
    private String couponName;

    @Criteria(expression = Expression.EQ, property = ActCouponActivity.F_ACTIVITY_STATUS, alias = ActCouponActivity.TABLE_NAME)
    private String activityStatus;

    private Long startTime;

    private Long endTime;

    @Criteria(expression = Expression.BETWEEN,property = ActCouponActivity.F_ADD_TIME,alias = ActCouponActivity.TABLE_NAME)
    private SearchTime addTime;

}
