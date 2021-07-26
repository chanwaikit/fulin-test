package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinBlock;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author zhaoqt
 */
@Data
public class SkinBlockCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = SkinBlock.F_TITLE, alias = SkinBlock.TABLE_NAME)
    private String title;
}
