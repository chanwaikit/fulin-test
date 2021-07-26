package com.mazentop.searcher;


import com.mztframework.dao.page.Page;

import java.io.IOException;

/**
 * @Auther: Wangzy
 * @Date: 2019/7/2 10:03
 * @Description:
 */
public interface ISuggester {

    /**
     * 构建 suggester索引
     */
    void build(String id);

    /**
     * 关键词联想查询
     * @return
     */
    Page<SearchParam> lookup(String name, String region) throws IOException;

}

