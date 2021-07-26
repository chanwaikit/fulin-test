package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysEmailTemplate;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysEmailTemplateCommond extends PageCommond {
    @Criteria(expression = Expression.EQ, property = SysEmailTemplate.F_FK_TEMPLATE_TYPE_ID, alias = SysEmailTemplate.TABLE_NAME)
    private String typeId;
}
