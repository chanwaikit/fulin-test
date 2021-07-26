package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProProductShortcut;
import com.mazentop.entity.ProProductType;
import com.mazentop.entity.ProSeo;
import com.mazentop.model.Status;
import com.mazentop.plugins.cache.DataCache;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 产品类型
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/19 17:28
 */
@Component
public class SkinProductTypeTag extends Tag {

    @Autowired
    CacheManager cacheManager;

    @Override
    public void execute() {
        String pid = getParam("pid", "root");
        String id = getParam("id", "");
        String productId = getParam("productId", "");

        if(!Utils.isBlank(productId)) {
            Map<String, Object> map = new HashMap<>(2);

            // 查询产品快照表
            ProProductShortcut shortcut = ProProductShortcut.me().setFkProductId(productId).setOrderByFields("id desc").findFirst();

            if(!Objects.isNull(shortcut)) {
                // 获取产品分类
                ProProductType proProductType = ProProductType.me().setId(shortcut.getFkProductTypeId()).get();
                String type = ThemeUtil.getTypeById(proProductType.getId());
                map.put("cur", type);

                // 获取产品分类路径
                List<ProProductType> proProductTypes = new ArrayList<>();
                proProductTypes.add(proProductType);
                proProductTypes = ThemeUtil.getTypeList(proProductTypes, ProProductType.me().setProductTypeName(Status.TREE_ROOT_NODE).get(), proProductType);
                map.put("nodes", proProductTypes);

            }

            setVariable("productType", map);
            renderBody();
            return;
        }

        if(!Utils.isBlank(id)) {
            setVariable("productType", ProProductType.me().setId(id).get());
            renderBody();
            return;
        }

        DataCache dataCache = new DataCache(DataCache.NAME_CATEGORY);
        dataCache.clear();
        if(dataCache.existKey(pid)) {
            setVariable("productTypes", dataCache.get(pid, List.class));
            renderBody();
        } else {
            List<ProProductType> libCategories = Optional.ofNullable(ProProductType.me().setParentProductTypeId(pid).find()).orElse(Collections.emptyList());
            // 填充 seo 地址
            libCategories.forEach(item -> {
                ProSeo proSeo = Optional.ofNullable(ProSeo.me().setSource(item.getId()).get()).orElse(ProSeo.me());
                item.addExten(ProSeo.F_SEO_ADDRESS,proSeo.getSeoAddress());
            });
            dataCache.put(pid, libCategories);
            setVariable("productTypes", libCategories);
            renderBody();
        }
    }

    @Override
    public String name() {
        return "skin_product_type";
    }
}
