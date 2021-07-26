package com.mazentop.modules.freemarker.tag;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mazentop.entity.ProBrand;
import com.mazentop.entity.ProProductType;
import com.mazentop.modules.freemarker.dto.BreadcrumbDto;
import com.mazentop.modules.skin.dto.LinkDto;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品搜索相关条件
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/5 21:58
 */
public class SearchProductBreadcrumbTag extends Tag {

    private ProductKeyword keyword;

    public SearchProductBreadcrumbTag(ProductKeyword keyword) {
        this.keyword = keyword;
    }

    String hideInputTemplate = "<input name=\"%s\" value=\"%s\" type=\"hidden\" />";

    @Override
    public void execute() {
        List<BreadcrumbDto> breadcrumbs = new ArrayList<>();

        // 品牌查询
//        if(!Utils.isBlank(keyword.getBrand())) {
//            ProBrand proBrand = new ProBrand().setId(keyword.getBrand()).get();
//            breadcrumbs.add(
//                    new BreadcrumbDto(proBrand.getId(), proBrand.getBrandName(), String.format(hideInputTemplate, "brand", proBrand.getId()), ""));
//        }
        // 分类查询
        if(!Utils.isBlank(keyword.getC())) {
            ProProductType proProductType = new ProProductType().setId(keyword.getC()).get();
            List<LinkDto> linkDto = ThemeUtil.getTypePath(proProductType);
            linkDto.forEach(item -> {
                breadcrumbs.add(new BreadcrumbDto(item.getId(), item.getTitle(), "c", item.getUrl()));
            });
        }
        // 规格查询
        breadcrumbs.addAll(StrUtil.split(keyword.getEv(), ',')
                .stream()
                .map(s -> new BreadcrumbDto(s, s, String.format(hideInputTemplate, "ev", s), ""))
                .collect(Collectors.toList()));

        setVariable("breadcrumbs", breadcrumbs);
        renderBody();
    }


    @Override
    public String name() {
        return "search_product_breadcrumb";
    }
}
