package com.mazentop.modules.web.seodetail.impl;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProSeo;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.freemarker.tag.SearchProductBreadcrumbTag;
import com.mazentop.modules.freemarker.tag.SearchProductRelevantTag;
import com.mazentop.modules.freemarker.tag.SkinServiceDescriptionTag;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.seodetail.ISeoDetails;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
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
@Service("category-details")
public class CategoryDetailsImpl implements ISeoDetails {

    @Autowired
    ProProductMasterService proProductMasterService;


    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    ISearcher searcher;

    /**
     * 服务器详情页数据直出数据组装
     *
     * @param modelMap
     * @param seo
     * @return
     */
    @Override
    public String builder(ModelMap modelMap, ProSeo seo) {
        return builder(new ProductKeyword(),modelMap,seo);
    }

    @Override
    public String builder(ProductKeyword keyword,ModelMap modelMap, ProSeo seo) {
        if (Objects.isNull(keyword)) {
            keyword = new ProductKeyword();
        }
        keyword.setC(seo.getSource());
        modelMap.put("page", searcher.search(keyword, IndexProvider.M_PRODUCT));
        // 搜索相关数据
        SearchProductRelevantTag searchProductRelevantTag = new SearchProductRelevantTag(keyword);
        modelMap.put(searchProductRelevantTag.name(), searchProductRelevantTag);
        // 搜索面包屑
        SearchProductBreadcrumbTag searchProductBreadcrumbTag = new SearchProductBreadcrumbTag(keyword);
        modelMap.put(searchProductBreadcrumbTag.name(), searchProductBreadcrumbTag);

        return ThemeUtil.templatePath("product/list");
    }
}
