package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysVisitorComeFrom;
import com.mazentop.entity.SysVisitorRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysVisitorRecordCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysVisitorRecord.F_CLIENT_NAME, alias = SysVisitorRecord.TABLE_NAME)
    private String clientName;
}
