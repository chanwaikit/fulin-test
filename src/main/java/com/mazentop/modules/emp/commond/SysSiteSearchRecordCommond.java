package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysPartner;
import com.mazentop.entity.SysSiteSearchRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysSiteSearchRecordCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysSiteSearchRecord.F_KEYWORDS, alias = SysSiteSearchRecord.TABLE_NAME )
    private String keywords;
}
