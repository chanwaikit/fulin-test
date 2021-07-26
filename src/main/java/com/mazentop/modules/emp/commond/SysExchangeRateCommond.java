package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysExchangeRate;
import com.mazentop.entity.SysFriendsLink;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysExchangeRateCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysExchangeRate.F_TCUR, alias = SysExchangeRate.TABLE_NAME)
    private String tcur;
}
