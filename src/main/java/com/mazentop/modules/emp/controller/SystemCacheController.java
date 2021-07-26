package com.mazentop.modules.emp.controller;

import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.ISuggester;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoqt
 * @title: SystemController
 * @description: 系统控制器
 * @date 2019/5/1010:34
 */
@RestController
@RequestMapping("/option/{api_version}/systemCache")
public class SystemCacheController {

    @Autowired
    ISearcher iSearcher;

    @Autowired
    ISuggester suggester;

    @GetMapping("/rebuild-lucene")
    @ResponseBody
    public Result rebuildLuceneIndex(String module) {
        iSearcher.rebuild(module);
        return Result.success();
    }

    @GetMapping("/rebuild-lucene-suggester")
    @ResponseBody
    public Result rebuildLuceneSuggesterIndex() {
        suggester.build(null);
        return Result.success();
    }

}
