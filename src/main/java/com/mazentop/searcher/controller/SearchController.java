package com.mazentop.searcher.controller;

import com.mazentop.entity.ProSearchKey;
import com.mazentop.modules.freemarker.tag.SearchProductBreadcrumbTag;
import com.mazentop.modules.freemarker.tag.SearchProductRelevantTag;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.ISuggester;
import com.mazentop.searcher.SearchParam;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 列表页面搜索
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/25 20:38
 */
@Controller
@RequestMapping
public class SearchController extends BaseController{

    @Autowired
    ISearcher searcher;

    @Autowired
    ISuggester iSuggester;

//    @GetMapping("/productIndex")
//    public String searchToIndex(ProductKeyword keyword, ModelMap modelMap) {
//        this.setGlobeSeoAttrs(modelMap);
//        if (Helper.isNotEmpty(keyword.getValue())){
//            ProSearchKey key=new ProSearchKey();
//            key.setKeyword(keyword.getValue());
//            key.setAddTime(Utils.currentTimeSecond());
//            key.insert();
//        }
//        modelMap.put("query", keyword);
//        modelMap.put("page", searcher.search(keyword, IndexProvider.M_PRODUCT));
//        // 搜索相关数据
//        SearchProductRelevantTag searchProductRelevantTag = new SearchProductRelevantTag(keyword);
//        modelMap.put(searchProductRelevantTag.name(), searchProductRelevantTag);
//        // 搜索面包屑
//        SearchProductBreadcrumbTag searchProductBreadcrumbTag = new SearchProductBreadcrumbTag(keyword);
//        modelMap.put(searchProductBreadcrumbTag.name(), searchProductBreadcrumbTag);
//        return templatePath("product/list");
//    }


    @GetMapping("/productList")
    public String productList( ModelMap modelMap) {
        this.setGlobeSeoAttrs(modelMap);
        return templatePath("product/list");
    }



    @GetMapping("/productIndex")
    @ResponseBody
    public Page<Snapshot> productIndex(ProductKeyword keyword) {
        return searcher.search(keyword, IndexProvider.M_PRODUCT);
    }


    @GetMapping("/productParamIndex")
    @ResponseBody
    public Page<Snapshot> productParamIndex(ProductKeyword keyword) {
        return searcher.search(keyword, IndexProvider.M_PRODUCT_PARAM);
    }

    @GetMapping("/suggest")
    public String suggest(String keyword, ModelMap modelMap) throws IOException {
        modelMap.put("keyword", keyword);
        if (Utils.isBlank(keyword)) {
            return templatePath("_inc/search");
        }
        Page<SearchParam> lookup = iSuggester.lookup(keyword, "");
        lookup.getList().forEach(searchParam -> {
            Page<Snapshot> page = searcher.search(new ProductKeyword(searchParam.getKeywords()), IndexProvider.M_PRODUCT);
            if (!Objects.isNull(page)) {
                searchParam.setTotalRow(page.getTotalRow());
            }

        });
        modelMap.put("lookup", lookup);
        return templatePath("_inc/search");
    }


    @GetMapping("/suggest/get/sku")
    @ResponseBody
    public List<SearchParam> suggestSku(String keyword) throws IOException {
        if (Utils.isBlank(keyword)) {
            return null;
        }
        Page<SearchParam> lookup = iSuggester.lookup(keyword, "");
        lookup.getList().forEach(searchParam -> {
            Page<Snapshot> page = searcher.search(new ProductKeyword(searchParam.getKeywords()), IndexProvider.M_PRODUCT_PARAM);
            if (!Objects.isNull(page)) {
                searchParam.setTotalRow(page.getTotalRow());
            }

        });
        return lookup.getList();
    }
}
