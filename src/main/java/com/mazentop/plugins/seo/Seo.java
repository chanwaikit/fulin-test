package com.mazentop.plugins.seo;

import com.mazentop.entity.ProSeo;
import com.mazentop.model.ProSeoTypeEnum;
import com.mztframework.cache.Options;

import java.util.Objects;

import static java.lang.String.format;

/**
 * TODO
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/25 18:48
 */
public class Seo {

    /**
     * 返回全路径
     * @param source
     * @param type
     * @return
     */
    public static String getSeoUrlDetail(String source,String type){
        String webDomain = Options.get("site_setting_domain_name");
        if (Objects.isNull(webDomain)) {
            return null;
        }
        ProSeo proProductSeo = ProSeo.me().setSource(source).setType(type).get();
        if (Objects.isNull(proProductSeo)) {
            return null;
        }
        String url = ProSeoTypeEnum.getProSeoTypeEnumByType(type).getUri();
        url = format( url+"%s",webDomain, proProductSeo.getSeoAddress());
        return url;
    }

    /**
     * 返回域名路径
     * @param type
     * @return
     */
    public static String getSeoUrl(String type){
        return format(ProSeoTypeEnum.getProSeoTypeEnumByType(type).getUri(), Options.get("site_setting_domain_name"));
    }


    /**
     *  获取产品 访问地址 返回全路径
     *
     * @param porductId
     * @return
     */
    public static String getSeoUrlForPorduct(String porductId) {
        return getSeoUrlDetail(porductId, ProSeoTypeEnum.TYPE_PRODUCT.type());
    }

    /**
     * 获取博客全路径
     *
     * @param blogId
     * @return
     */
    public static String getSeoUrlForBlog(String blogId) {
        return getSeoUrlDetail(blogId, ProSeoTypeEnum.TYPE_BLOG.type());
    }

    /**
     *  获取专辑全路径
     *
     * @param porductId
     * @return
     */
    public static String getSeoUrlForCollection(String porductId) {
        return getSeoUrlDetail(porductId, ProSeoTypeEnum.TYPE_SKIN.type());
    }

    /**
     * 获取产品分类全路径
     *
     * @param productTypeId
     * @return
     */
    public static String getSeoUrlForProductType(String productTypeId) {
        return getSeoUrlDetail(productTypeId, ProSeoTypeEnum.TYPE_CATEGORY.type());
    }

    /**
     * 获取产品页面全路径
     *
     * @param pageId
     * @return
     */
    public static String getSeoUrlForPageType(String pageId) {
        return getSeoUrlDetail(pageId, ProSeoTypeEnum.TYPE_PAGE.type());
    }
}
