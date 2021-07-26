package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProComment;
import com.mazentop.entity.SysHelpCenterContent;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: dengy
 * @date: 2020/3/19
 * @description:
 */
@Data
public class SysHelpCenterContentCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = SysHelpCenterContent.F_SEO_KEYWORDS, alias = SysHelpCenterContent.TABLE_NAME)
    private String seoKeywords;

    @Criteria(expression = Expression.EQ, property = SysHelpCenterContent.F_FK_HELP_CENTER_TYPE_ID, alias = SysHelpCenterContent.TABLE_NAME)
    private String fkHelpCenterTypeId;

}
