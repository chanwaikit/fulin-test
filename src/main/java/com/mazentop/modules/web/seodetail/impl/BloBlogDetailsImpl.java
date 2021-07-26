package com.mazentop.modules.web.seodetail.impl;

import com.mazentop.entity.CmsArticle;
import com.mazentop.entity.ProSeo;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.freemarker.tag.CmsNextArticleTag;
import com.mazentop.modules.freemarker.tag.CmsPreviousArticleTag;
import com.mazentop.modules.web.seodetail.ISeoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Objects;

/**
 * 产品详情页面
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/27 11:00
 */
@Service("blog-details")
public class BloBlogDetailsImpl implements ISeoDetails {

    @Autowired
    ProProductMasterService proProductMasterService;

    @Override
    public String builder(ModelMap modelMap, ProSeo seo) {
        CmsArticle cmsArticle =
                CmsArticle.me()
                        .setId(seo.getSource())
                        .setStatus("normal")
                        .get();
        if(Objects.isNull(cmsArticle)) {
            return errorNull();
        }
        modelMap.put("blog", cmsArticle);
        CmsNextArticleTag next = new CmsNextArticleTag(cmsArticle);
        CmsPreviousArticleTag previous = new CmsPreviousArticleTag(cmsArticle);
        modelMap.put(next.name(), next);
        modelMap.put(previous.name(), previous);
        return originalTemplate(seo);
    }
}
