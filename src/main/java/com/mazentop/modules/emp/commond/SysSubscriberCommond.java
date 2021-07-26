package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysSiteSearchRecord;
import com.mazentop.entity.SysSubscriber;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysSubscriberCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysSubscriber.F_EMAIL, alias = SysSubscriber.TABLE_NAME )
    private String email;
}
