package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysNoticeSetting;
import com.mazentop.entity.SysVisitorComeFrom;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysVisitorComeFromCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysVisitorComeFrom.F_CLIENT_NAME, alias = SysVisitorComeFrom.TABLE_NAME)
    private String clientName;
}
