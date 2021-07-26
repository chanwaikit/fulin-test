package com.mazentop.modules.web.controller;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.ProSeo;
import com.mazentop.entity.SkinPage;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.entity.SysTemplate;
import com.mazentop.model.OptionsEnum;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.service.OptionService;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mztframework.cache.Options;
import com.mztframework.dao.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BaseController {

    @Autowired
    OptionService optionService;

    public static String HOME_PAGE = "home";

    protected String templatePath(String templateName){
       return ThemeUtil.templatePath(templateName);
    }

    protected ProSeo retrievalSeo(String type, String slug){
        return ProSeo.me().setType(type).setSeoAddress(slug).get();
    }

    protected String error404() {
        return templatePath("error/404");
    }

    protected String errorNull() {
        return templatePath("error/null");
    }


    protected void setGlobeSeoAttrs(ModelMap modelMap, ProSeo seo) {
        if(!Objects.isNull(seo)) {
            this.setGlobleAttrs(modelMap, seo.getSeoTitle(), seo.getSeoKeywords(), seo.getSeoDescription());
        }
    }

    protected void setGlobeSeoAttrs(ModelMap modelMap) {
        this.setGlobleAttrs(modelMap,
                Options.get(OptionsEnum.SITE_SEO_TITLE.key()),
                Options.get(OptionsEnum.SITE_SEO_KEYWORD.key()),
                Options.get(OptionsEnum.SITE_SEO_DESCRIPTION.key()));
    }

    /**
     *   主页面添加 标题、关键词 描述
     *
     * @param modelMap
     * @param title
     * @param keywords
     * @param description
     */
    protected void setGlobleAttrs(ModelMap modelMap, String title, String keywords, String description) {
        if (StrUtil.isNotBlank(title)) {
            modelMap.put(Status.ATTR_GLOBAL_WEB_TITLE, title);
        }

        if (StrUtil.isNotBlank(keywords)) {
            modelMap.put(Status.ATTR_GLOBAL_META_KEYWORDS, keywords);
        }

        if (StrUtil.isNotBlank(description)) {
            modelMap.put(Status.ATTR_GLOBAL_META_DESCRIPTION, description);
        }

    }

    protected String toPageBlock(String slug, ModelMap modelMap) {
        SysTemplate sysTemplate = ThemeUtil.getTemplate();
        SkinPage skinPage = SkinPage.me().setTemplateId(sysTemplate.getId()).setUrl(String.format("/%s", slug)).get();

        if(Objects.isNull(skinPage)) {
            return error404();
        }
        setGlobeSeoAttrs(modelMap, retrievalSeo(ProSeoTypeEnum.TYPE_PAGE.type(), slug));

        modelMap.put("page", skinPage);

        List<SkinPageBlock> skinPageBlocks =
                SkinPageBlock.me().setView(skinPage.getId()).setHandle(skinPage.getId()).setIsEnable(Status.YES).setOrderByFields(Order.asc(SkinPageBlock.F_SORT)).find();

        List<BlockData> blocks;

        blocks = skinPageBlocks
                .stream().map(skinPageBlock -> {
            BlockData blockData = new BlockData();
            blockData.setBlock(skinPageBlock);
            return blockData;
        }).collect(Collectors.toList());

        modelMap.put("blocks", blocks);

        if(HOME_PAGE.equals(slug)) {
            return templatePath("index");
        }
        return "/web/page.html";
    }

}
