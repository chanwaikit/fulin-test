package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysTemplate;
import com.mazentop.entity.SysVisitorComeFrom;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysTemplateCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = SysTemplate.F_TEMPLATE_NAME, alias = SysTemplate.TABLE_NAME)
    private String templateName;
}
