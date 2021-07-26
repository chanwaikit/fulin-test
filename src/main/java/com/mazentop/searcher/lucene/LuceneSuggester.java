package com.mazentop.searcher.lucene;


import com.mazentop.CmsConfig;
import com.mazentop.searcher.ISuggester;
import com.mazentop.searcher.SearchParam;
import com.mazentop.searcher.index.SuggesterIndexProvideer;
import com.mazentop.searcher.iterator.SearchParamIterator;
import com.mztframework.dao.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Wangzy
 * @Date: 2019/7/2 10:05
 * @Description:
 */
@Component
@Slf4j
public class LuceneSuggester implements ISuggester {

    @Autowired
    private  SuggesterIndexProvideer suggesterIndexProvideer;

    private static Directory directory;


    @Autowired
    CmsConfig cmsConfig;



    @PostConstruct
    public void init()  {
        // 开发环境不使用文件存储索引
        if(cmsConfig.getLucene().isDebug()) {
            directory = new RAMDirectory();
        } else {
            try {
                File indexDir = new File(cmsConfig.getLucene().getIndexDir() + File.separator + "suggester");
                directory = FSDirectory.open(Paths.get(indexDir.getPath()));
                if (!indexDir.exists()) {
                    indexDir.mkdirs();
                }
            } catch (IOException e) {
                log.error("init lucene path error", e);
            }
        }
    }

    @Override
    public void build(String id) {
        try {
            getSuggester().build(new SearchParamIterator(suggesterIndexProvideer.build().iterator()));
        } catch (IOException e) {
            log.error("Lucene suggester ", e);
        }
    }

    @Override
    public Page<SearchParam> lookup(String name, String region) throws IOException {
        //num决定了返回几条数据，参数四表明是否所有TermQuery是否都需要满足，参数五表明是否需要高亮显示
        List<Lookup.LookupResult> results = getSuggester().lookup(name, 10, false, false);
        List<SearchParam> list = new ArrayList<>();
        for (Lookup.LookupResult result : results) {
            String key = result.key.toString();
            list.add(new SearchParam(key));
        }
        return new Page<>(list, list.size());
    }

    public AnalyzingInfixSuggester getSuggester() throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        return new AnalyzingInfixSuggester(directory, analyzer);
    }
}
