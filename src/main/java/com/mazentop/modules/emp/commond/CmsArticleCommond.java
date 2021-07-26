package com.mazentop.modules.emp.commond;

import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * cms 文章查询
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/6/8 21:15
 */
@Data
public class CmsArticleCommond extends PageCommond {

    private static final long serialVersionUID = -8338634753694463403L;

    @Criteria(expression = Expression.EQ)
    private String module;

    @Criteria(expression = Expression.EQ)
    private String status;

    @Criteria(expression = Expression.LIKE)
    private String title;

}
