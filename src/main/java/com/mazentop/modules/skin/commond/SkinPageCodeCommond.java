package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinPageCode;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SkinPageCodeCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = SkinPageCode.F_TITLE, alias = SkinPageCode.TABLE_NAME)
    private String title;
}
