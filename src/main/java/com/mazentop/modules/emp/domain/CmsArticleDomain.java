package com.mazentop.modules.emp.domain;

import com.mazentop.entity.CmsArticle;
import com.mazentop.entity.CmsArticleLink;
import com.mazentop.entity.CmsTaxonomy;
import com.mazentop.model.Status;
import com.mztframework.commons.Maps;
import com.mztframework.dao.annotation.Order;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Title: CmsArticleDomain
 * @Description: 产品分类
 * @Author liuq
 * @Date 2019/6/10 14:42
 */
public class CmsArticleDomain extends CmsArticle.Sub{


    private static final long serialVersionUID = -7149678293008929830L;

    public String getTaxonomys() {
        List<String> taxonomys = new ArrayList<>();
        List<CmsArticleLink> articleLinks = new CmsArticleLink().setFkArticleId(this.getId()).find();
        for(CmsArticleLink cmsArticleLink : articleLinks){
            String taxonomyId =  cmsArticleLink.getFkTaxonomyId();
            CmsTaxonomy cmsTaxonomy = new CmsTaxonomy().setId(taxonomyId).get();
            taxonomys.add(cmsTaxonomy.getTitle());
        }
        return String.join(",", taxonomys);
    }

    public CmsArticleDomain getFirstTaxonomyArticle(String module) {
        CmsTaxonomy cmsTaxonomy = new CmsTaxonomy().setModule(module).setIsEnable(Status.YES).setOrderByFields(Order.asc(CmsArticle.F_SORT)).findFirst();
        if(!Objects.isNull(cmsTaxonomy)) {
            List<CmsArticleLink> cmsArticleLinks = CmsArticleLink.me().setFkTaxonomyId(cmsTaxonomy.getId()).list();
            if(!cmsArticleLinks.isEmpty()) {
                List<String> ids = cmsArticleLinks.stream().map(CmsArticleLink::getFkArticleId).collect(Collectors.toList());
               return dao.queryForBean("select * from cms_article where status = 'normal' and id in(:ids) order by sort asc limit 1", Maps.of("ids", ids), this);
            }
        }
        return this;
    }

    @Override
    public RowMapper<CmsArticle> newMapper() {
        return super.newMapper(CmsArticleDomain::new);
    }
}
