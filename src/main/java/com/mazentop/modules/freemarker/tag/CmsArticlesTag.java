package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.CmsArticle;
import com.mazentop.entity.CmsArticleLink;
import com.mazentop.util.Helper;
import com.mztframework.commons.Maps;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.render.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengy
 * 文章列表
 */
@Component
public class CmsArticlesTag extends Tag {
    @Autowired
    BaseDao baseDao;
    @Override
    public void execute() {
        String taxonomy = getParam("taxonomy");
        String module = getParam("module");
        Integer limit = getParamToInt("limit");

        List<CmsArticle> list = new ArrayList<>();
        if(!Helper.isEmpty(taxonomy)){
            List<CmsArticleLink> cmsArticleLinks = CmsArticleLink.me().setFkTaxonomyId(taxonomy).find();

            if(!cmsArticleLinks.isEmpty()){
            List<String> ids = cmsArticleLinks.stream().map(CmsArticleLink::getFkArticleId).collect(Collectors.toList());
            list = baseDao.queryForBeanList("select * from cms_article where status = 'normal' and id in(:ids) order by sort asc", Maps.of("ids", ids), new CmsArticle());
            }
        }else{
            list = CmsArticle.me().setModule(module).setStatus("normal").setOrderByFields(Order.desc(CmsArticle.F_ADD_TIME)).setLimit(limit).find();
        }
        setVariable("articles", list);
        renderBody();
    }

    @Override
    public String name() {
        return "cms_articles";
    }
}
