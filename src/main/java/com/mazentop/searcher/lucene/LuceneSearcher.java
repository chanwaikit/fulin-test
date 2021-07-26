package com.mazentop.searcher.lucene;

import com.mazentop.CmsConfig;
import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.Keyword;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.index.AbstractIndexProvider;
import com.mazentop.searcher.index.SuggesterIndexProvideer;
import com.mztframework.commons.Utils;
import com.mztframework.dao.page.Page;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoqt
 * @title: LuceneSearcher
 * @description: 系统内置 Lucene 检索
 * @date 2019/5/918:36
 */
@Component
@Slf4j
public class LuceneSearcher implements ISearcher {

    /**索引文件 内存存储*/
    private static Directory directory;

    @Autowired
    SuggesterIndexProvideer suggesterIndexProvideer;
    @Autowired
    CmsConfig cmsConfig;

    @Autowired
    LuceneSuggester luceneSuggester;

    @Autowired
    Map<String, AbstractIndexProvider> indexProviderMap;

    @PostConstruct
    @Override
    public void init()  {
        // 开发环境不使用文件存储索引
        if(cmsConfig.getLucene().isDebug()) {
            directory = new RAMDirectory();
        } else {
            try {
                File indexDir = new File(cmsConfig.getLucene().getIndexDir());
                if (!indexDir.exists()) {
                    indexDir.mkdirs();
                }
                directory = NIOFSDirectory.open(indexDir.toPath());
            } catch (IOException e) {
               log.error("init lucene path error", e);
            }
        }
    }
    IndexWriter getWriter(){
        try {
            //得到索引所在的目录
            //创建标准分词器
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
            return new IndexWriter(directory,iwConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public void save(Snapshot snapshot, String module) {

        try {
            @Cleanup IndexWriter writer = getWriter();
            writer.addDocument(indexProviderMap.get(module).createDocument(snapshot));
            luceneSuggester.build(snapshot.getId());
            writer.commit();
        } catch (IOException e) {
            log.error("add bean to lucene error", e);
        }
    }

    @Override
    public void delete(Snapshot snapshot, String module) {
        try {
            @Cleanup IndexWriter writer = new IndexWriter(directory, getIndexWriterConfig());
            writer.deleteDocuments(new Term(Snapshot.F_ID, snapshot.getId()));
            writer.commit();
        } catch (IOException e) {
            log.error("delete bean to lucene error, beanId:" + snapshot.getId(), e);
        }
    }

    @Override
    public void update(Snapshot snapshot, String module) {
        delete(snapshot, module);
        save(snapshot, module);
    }

    @Override
    public boolean rebuild(String module) {
        // 参数为null 并且无法找到给定参数 执行全部索引重建
        if(Utils.isBlank(module)) {
            for(String moduleKey : indexProviderMap.keySet()) {
                AbstractIndexProvider indexProvider = indexProviderMap.get(moduleKey);
                for (Snapshot snapshot : indexProvider.rebuild()) {
                    update(snapshot, moduleKey);
                }
            }
            return true;
        } else if(indexProviderMap.containsKey(module)){
            AbstractIndexProvider indexProvider = indexProviderMap.get(module);
            indexProvider.rebuild().forEach( snapshot -> update(snapshot, module));
            return true;
        }
        return false;
    }

    @Override
    public Page<Snapshot> search(Keyword keyword, String module) {
        try {
            AbstractIndexProvider indexProvider = indexProviderMap.get(module);
            @Cleanup IndexReader aIndexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(aIndexReader);
            Query query = indexProvider.getQuery(keyword, module);
            TopDocs topDocs;
            // 分页
            if(keyword.isPagination()) {
                // 如果是倒序，再获取上一页数据的时候，排序改变；需要将元数据排序保存起来
//                String o = keyword.getO();
                Sort sort = keyword.getSort();
                ScoreDoc lastScoreDoc = getLastScoreDoc(keyword, query, searcher);
                topDocs = searcher.searchAfter(lastScoreDoc, query, keyword.getPageSize(), sort);
            // 不分页
            } else {
                topDocs = searcher.search(query, keyword.getPageSize(), keyword.getSort());
            }
            List<Snapshot> searchers = indexProvider.getSnapshots(searcher, topDocs, simpleHTMLFormatter(query), keyword);
            //统计总页数
            int totalRow = searchTotalRecord(searcher, query);

            return new Page<>(searchers, keyword.getPageIndex(), keyword.getPageSize(), totalRow);

        } catch (Exception e) {
            if(e instanceof  IndexNotFoundException) {
                return new Page<>(Lists.newArrayList(), 0);
            }
            log.error("lucene search", e);
        }
        return null;
    }


    @Override
    public int totalRow(Keyword keyword, String module) {
        try {
            AbstractIndexProvider indexProvider = indexProviderMap.get(module);
            @Cleanup IndexReader aIndexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(aIndexReader);
            Query query = indexProvider.getQuery(keyword, module);
            return searchTotalRecord(searcher, query);
        } catch (IOException e) {
            if(e instanceof  IndexNotFoundException) {
                return 0;
            }
            log.error("lucene search", e);
        }
        return 0;
    }
    private Highlighter simpleHTMLFormatter(Query query) {
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<fonts color='red'>", "</fonts>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        highlighter.setTextFragmenter(new SimpleFragmenter(1024));
        return highlighter;
    }

    private int searchTotalRecord(IndexSearcher searcher, Query query) throws IOException {
        TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
        if (topDocs == null || topDocs.scoreDocs == null
                || topDocs.scoreDocs.length == 0) {
            return 0;
        }
        ScoreDoc[] docs = topDocs.scoreDocs;
        return docs.length;
    }

    private ScoreDoc getLastScoreDoc(Keyword keyword, Query query, IndexSearcher indexSearcher) throws IOException {
        if (keyword.getPageIndex() == 1) {
            // 如果是第一页返回空
            return null;
        }
        // 获取上一页的数量
        int num = keyword.getPageSize() * (keyword.getPageIndex() - 1);
        TopDocs tds = indexSearcher.search(query, num, keyword.getSort());
        return tds.scoreDocs[num - 1];

        // 反序
//        if(!Utils.isBlank(o) && o.startsWith("-")){
//            int num = keyword.getPageSize() * (keyword.getPageIndex() - 1);
//            TopDocs tds = indexSearcher.search(query, num + 1, keyword.getSort());
//            return tds.scoreDocs[num];
//        // 正序
//        }else{

//        }

    }

    private IndexWriterConfig getIndexWriterConfig() {
        return new IndexWriterConfig( new IKAnalyzer());
    }
}
