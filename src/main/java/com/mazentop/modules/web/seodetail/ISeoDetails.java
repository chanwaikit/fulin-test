package com.mazentop.modules.web.seodetail;

import com.mazentop.entity.ProSeo;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.searcher.keyword.ProductKeyword;
import org.springframework.ui.ModelMap;

/**
 * seo 详情页数据组装
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/27 10:47
 */
public interface ISeoDetails {

    /**
     * 服务器详情页数据直出数据组装
     * @param modelMap
     * @param seo
     * @return
     */
    String builder(ModelMap modelMap, ProSeo seo);
    default String builder(ProductKeyword keyword, ModelMap modelMap, ProSeo seo){return null;};



    /**
     * 默认模板提供
     * @param seo
     * @return
     */
    default String originalTemplate(ProSeo seo) {
       return ThemeUtil.templatePath(String.format("%s/details", seo.getType()));
    }

    default String errorNull() {
        return ThemeUtil.templatePath(("error/null"));
    }

    default ProSeo retrievalSeo(String slug) {
        return null;
    }
}
