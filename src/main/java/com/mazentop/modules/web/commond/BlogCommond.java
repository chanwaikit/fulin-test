package com.mazentop.modules.web.commond;

import com.mazentop.modules.emp.commond.CmsArticleCommond;
import com.mazentop.modules.emp.warpper.Article;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * 博客查询
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 16:05
 */
@Data
public class BlogCommond extends CmsArticleCommond {

    @Criteria(expression = Expression.SQL, sql = " and id in(select fk_article_id from cms_article_link where cms_article_link.fk_taxonomy_id = :taxonomy)")
    private String taxonomy;

    public BlogCommond() {
        this.setModule("blog");
        this.setStatus(Article.STATUS_NORMAL);
    }
}
