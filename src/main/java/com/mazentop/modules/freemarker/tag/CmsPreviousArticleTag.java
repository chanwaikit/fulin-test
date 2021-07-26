package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.CmsArticle;
import com.mazentop.plugins.seo.Seo;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * 文章上一篇
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 16:55
 */
public class CmsPreviousArticleTag extends Tag {

    private CmsArticle cmsArticle;

    public CmsPreviousArticleTag(CmsArticle cmsArticle) {
        this.cmsArticle = cmsArticle;
    }

    @Override
    public void execute() {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("id", cmsArticle.getId());
        paramMap.put("module", cmsArticle.getModule());
        String sql = "select id, title from cms_article where id < :id and module =:module and status ='normal' ";
        sql += "order by add_time desc ";
        sql += "limit 1 ";
        Map<String, Object> map = CmsArticle.dao.queryForMap(sql, paramMap);
        map.put("url", Seo.getSeoUrlForBlog(Utils.toCast(map.get("id"), String.class)));
        setVariable("previous", map);
        renderBody();
    }

    @Override
    public String name() {
        return "cms_previous";
    }
}
