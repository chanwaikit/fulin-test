package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinPage;
import com.mazentop.entity.SkinPageLayout;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author dengy
 */
@Data
public class SkinPageLayoutCommond extends PageCommond {


    @Criteria(expression = Expression.LIKE, property = SkinPageLayout.F_NAME, alias = SkinPageLayout.TABLE_NAME)
    private String name;

    @Criteria(expression = Expression.EQ, property = SkinPageLayout.F_TEMPLATE_ID, alias = SkinPageLayout.TABLE_NAME)
    private String templateId;
}
