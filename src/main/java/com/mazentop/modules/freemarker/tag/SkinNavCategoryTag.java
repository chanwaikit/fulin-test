package com.mazentop.modules.freemarker.tag;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.ProProductType;
import com.mazentop.entity.SkinPageBlockNav;
import com.mazentop.modules.skin.block.dto.MenuTree;
import com.mazentop.plugins.seo.Seo;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/7/12 00:09
 */
@Component
@Slf4j
public class SkinNavCategoryTag extends Tag {

    @Override
    public void execute() {
        String menuId = getParam("navId");
        if(!Utils.isBlank(menuId)) {
            List<MenuTree> menuTrees = new ArrayList<>();
            SkinPageBlockNav skinPageBlockNav = SkinPageBlockNav.me().setId(menuId).get();
            if(!Objects.isNull(skinPageBlockNav) && MenuTree.TYPE_CLASSIFICATIONS.equals(skinPageBlockNav.getType())) {
                List<ArrayList> arrayLists =  JSON.parseArray(skinPageBlockNav.getInlineData(), ArrayList.class);
                if(!Objects.isNull(arrayLists)) {
                    List<String> lastLevelCategorys = new ArrayList<>();
                    arrayLists.forEach(arrayList -> lastLevelCategorys.add((String) arrayList.get(arrayList.size() - 1)));

                    lastLevelCategorys.forEach(categoryId -> {
                        ProProductType proProductType = ProProductType.me().setId(categoryId).get();
                        MenuTree menuTree = new MenuTree();
                        menuTree.setTitle(proProductType.getProductTypeName());
                        menuTree.setType(MenuTree.TYPE_CLASSIFICATIONS);
                        menuTree.setImgUrl(proProductType.getProductTypeProImage());
                        menuTree.setUrl(Seo.getSeoUrlForProductType(proProductType.getId()));
                        menuTree.setId(proProductType.getId());
                        menuTrees.add(menuTree);
                    });
                }

            }
            setVariable("navCs", menuTrees);
            renderBody();
        } else {
            log.error("[@skin_nav_category /] 缺少必要参数 navId，例如：[@skin_nav_category navId=\"菜单主键\" /]");
        }

    }

    @Override
    public String name() {
        return "skin_nav_category";
    }
}
