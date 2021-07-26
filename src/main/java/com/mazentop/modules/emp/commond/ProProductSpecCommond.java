package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProComment;
import com.mazentop.entity.ProProductSpec;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class ProProductSpecCommond extends BaseCommond {
    @Criteria(expression = Expression.IN, property = ProProductSpec.F_ID, alias = ProProductSpec.TABLE_NAME)
    private List<String> ids;
}
