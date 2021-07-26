package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProProductColor;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

import java.util.List;

@Data
public class ProProductColorCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = ProProductColor.F_COLOR_NAME, alias = ProProductColor.TABLE_NAME)
    private String colorName;

    @Criteria(expression = Expression.IN, property = ProProductColor.F_ID, alias = ProProductColor.TABLE_NAME)
    private List<String> ids;
}
