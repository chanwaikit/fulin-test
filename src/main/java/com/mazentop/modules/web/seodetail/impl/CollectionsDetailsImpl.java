package com.mazentop.modules.web.seodetail.impl;

import com.mazentop.entity.ProSeo;
import com.mazentop.entity.SkinPage;
import com.mazentop.modules.web.seodetail.ISeoDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * 专题详情页面
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 14:55
 */
@Service("collections-details")
public class CollectionsDetailsImpl implements ISeoDetails {
    @Override
    public String builder(ModelMap modelMap, ProSeo seo) {
        SkinPage skinPage = SkinPage.me().setId(seo.getSource()).get();
        modelMap.put("page", skinPage);
        return originalTemplate(seo);
    }
}
