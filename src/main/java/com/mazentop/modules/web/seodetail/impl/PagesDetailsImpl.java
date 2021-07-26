package com.mazentop.modules.web.seodetail.impl;

import com.mazentop.entity.ProSeo;
import com.mazentop.entity.SkinPage;
import com.mazentop.modules.web.seodetail.ISeoDetails;
import com.mazentop.plugins.theme.ThemeUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Objects;

@Service("pages-details")
public class PagesDetailsImpl implements ISeoDetails {
    @Override
    public String builder(ModelMap modelMap, ProSeo seo) {
        SkinPage skinPage = SkinPage.me().setId(seo.getSource()).get();
        modelMap.put("page", skinPage);
        return originalTemplate(seo);
    }

    @Override
    public ProSeo retrievalSeo(String slug) {
        SkinPage skinPage = SkinPage.me().setSlug(slug).includeSelectFields(SkinPage.F_ID).findFirst();
        if(!Objects.isNull(skinPage)) {
            return ProSeo.me().setSource(skinPage.getId()).findFirst();
        }
        return null;
    }

    @Override
    public String originalTemplate(ProSeo seo) {
        return ThemeUtil.templatePath(String.format("pages/%s", "default"));
    }
}
