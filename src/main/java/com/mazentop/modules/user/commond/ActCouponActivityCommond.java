package com.mazentop.modules.user.commond;

import com.mazentop.entity.ActCouponActivity;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

/**
 * @author chen quan
 * @date 2020/4/16 15:42
 **/
@Data
public class ActCouponActivityCommond extends BaseCommond {

    @Criteria(expression = Expression.IN, property = ActCouponActivity.F_ID, alias = ActCouponActivity.TABLE_NAME)
    private List<String> ids;

}
