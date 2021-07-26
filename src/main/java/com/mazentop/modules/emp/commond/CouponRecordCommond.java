package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ActGetCouponRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @date: 2020/11/13
 * @description:
 */
@Data
public class CouponRecordCommond extends PageCommond {

    /**
     * 活动id
     */
    @Criteria(expression = Expression.EQ, property = ActGetCouponRecord.F_FK_ACTIVITY_ID, alias =  ActGetCouponRecord.TABLE_NAME)
    private String activityId;


}
