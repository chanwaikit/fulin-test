package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinPage;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SkinPageCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = SkinPage.F_TITLE, alias = SkinPage.TABLE_NAME)
    private String title;

    @Criteria(expression = Expression.EQ, property = SkinPage.F_TEMPLATE_ID, alias = SkinPage.TABLE_NAME)
    private String templateId;


    @Criteria(expression = Expression.EQ, property = SkinPage.F_TYPE, alias = SkinPage.TABLE_NAME)
    private String type;


}
