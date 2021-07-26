package com.mazentop.searcher.index;

import com.mazentop.searcher.Keyword;
import com.mazentop.searcher.Snapshot;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.List;
import java.util.Objects;

/**
 * @author zhaoqt
 * @title: AbstractIndexProvider
 * @description: 索引供应者
 * @date 2019/5/919:24
 */
public abstract class AbstractIndexProvider {


    protected void addCommonPart(Document document, Snapshot snapshot, String module) {

        addStringField(document, Snapshot.F_ID, snapshot.getId());
        addTextField(document, Snapshot.F_TITLE, snapshot.getTitle());

        addStoredField(document, Snapshot.F_THUMBNAIL, snapshot.getThumbnail());

        addTextField(document, Snapshot.F_KEYWORD, snapshot.getKeyword());
        addTextField(document, Snapshot.F_TAXONOMY, snapshot.getTaxonomy());

        // 摘要 直接存储不索引、详情内容索引不不存储
        addStoredField(document, Snapshot.F_SUMMARY, snapshot.getSummary());
        addTextFieldIndex(document, Snapshot.F_CONTENT, snapshot.getContent());

        addStringField(document, Snapshot.F_MODULE, module);

        addStoredField(document, Snapshot.F_URL, snapshot.getUrl());

    }

    /**
     * StringField 类 字符串
     *
     *  这个Field用来构建一个字符串Field，但是不会进行分词，会将整个串存储在索引中，比如(订单号,身份证号等)
     *
     * @param document
     * @param name
     * @param value
     */
    protected void addStringField(Document document, String name, String value) {
        if(!Utils.isBlank(value)) {
            document.add(new StringField(name, value, Field.Store.YES));
        }
    }

    /**
     * LongField 类 Long型
     *
     *  这个Field用来构建一个Long数字型Field，进行分词和索引，比如(价格)
     *
     * @param document
     * @param name
     * @param value
     */
    protected void addLongField(Document document, String name, Long value) {
        if(!Objects.isNull(value)) {
            Field field  = new LongPoint(name, value);
            document.add(field);
            field = new NumericDocValuesField(name, value);
            document.add(field);
            field = new StoredField(name, value);
            document.add(field);
        }
    }

    protected void addIntField(Document document, String name, Integer value) {
        if(!Objects.isNull(value)) {
            Field field  = new IntPoint(name, value);
            document.add(field);
            field = new NumericDocValuesField(name, value);
            document.add(field);
            field = new StoredField(name, value);
            document.add(field);
        }
    }

    protected void addDoubleField(Document document, String name, Double value) {
        if(!Objects.isNull(value)) {
            Field field  = new DoublePoint(name, value);
            document.add(field);
            field = new DoubleDocValuesField(name, value);
            document.add(field);
            field = new StoredField(name, value);
            document.add(field);
        }
    }

    /**
     * StoredField 类 重载方法，支持多种类型
     *
     *  这个Field用来构建不同类型Field 不分析，不索引，但要Field存储在文档中
     *
     * @param document
     * @param name
     * @param value
     */
    protected void addStoredField(Document document, String name, String value) {
        if(!Utils.isBlank(value)) {
            document.add(new StoredField(name, value));
        }
    }

    /**
     * TextField 类 字符串
     *
     *  支持分词、存储选择、进行索引
     *
     * @param document
     * @param name
     * @param value
     * @param store
     */
    private void addTextField(Document document, String name, String value, Field.Store store) {
        if(!Utils.isBlank(value)) {
            document.add(new TextField(name, value, store));
        }
    }

    /**
     * TextField 进行索引、存储
     *
     * @param document
     * @param name
     * @param value
     */
    protected void addTextField(Document document, String name, String value) {
        addTextField(document, name, value, Field.Store.YES);
    }

    /**
     * TextField 只索引不存储
     *
     * @param document
     * @param name
     * @param value
     */
    protected void addTextFieldIndex(Document document, String name, String value) {
        addTextField(document, name, value, Field.Store.NO);
    }


    /**
     * 分析器查询
     *
     * @param keyword
     * @param fieldName
     * @return
     * @throws ParseException
     */
    protected Query queryParser(String keyword, String fieldName) throws ParseException {
        QueryParser queryParser = new QueryParser( fieldName, new IKAnalyzer());
        return queryParser.parse(Helper.isEnglish(keyword) ? keyword+"* " : keyword);
    }

    /**
     * 分析器查询
     *
     * @param booleanClauses
     * @param keyword
     * @param fieldName
     * @throws ParseException
     */
    protected void queryParser(BooleanQuery.Builder booleanClauses, String keyword, String fieldName) throws ParseException {
        if(!Utils.isBlank(keyword)) {
            booleanClauses.add(
                    new BooleanClause(queryParser(keyword, fieldName), BooleanClause.Occur.SHOULD));
        }
    }

    /**
     * 多字段分析器查询
     *
     * @param booleanClauses
     * @param keyword
     * @param fieldNames
     */
    protected void queryParser(BooleanQuery.Builder booleanClauses, String keyword, String[] fieldNames) throws ParseException {
        if(!Utils.isBlank(keyword)) {
            QueryParser parser = new MultiFieldQueryParser( fieldNames, new StandardAnalyzer());
            parser.setDefaultOperator(QueryParser.Operator.AND);
            //开启支持关键词前面加*
            parser.setAllowLeadingWildcard(true);
            booleanClauses.add(parser.parse(keyword), BooleanClause.Occur.MUST);
        }

    }
    /**
     * 精确查询可查询 StringField
     *
     * @param keyword
     * @param fieldName
     * @return
     */
    protected Query termQuery(String keyword, String fieldName) {
        return new TermQuery(new Term(fieldName, keyword));
    }

    /**
     * 精确查询可查询 StringField
     *
     * @param booleanClauses
     * @param keyword
     * @param fieldName
     */
    protected void termQuery(BooleanQuery.Builder booleanClauses, String keyword, String fieldName) {
        if(!Utils.isBlank(keyword)) {
            booleanClauses.add(new BooleanClause(termQuery(keyword, fieldName), BooleanClause.Occur.MUST));
        }
    }


    /**
     * 数组值查询
     *
     * @param booleanClauses
     * @param keywords
     * @param fieldName
     */
    protected void termQuery(BooleanQuery.Builder booleanClauses, String[] keywords, String fieldName) {
        if(!Objects.isNull(keywords) && keywords.length > 0) {
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            for(String keyword : keywords) {
                booleanQuery.add(new BooleanClause(termQuery(keyword, fieldName), BooleanClause.Occur.SHOULD));
            }
            booleanClauses.add(termQuery("*", fieldName), BooleanClause.Occur.MUST);
        }
    }

    /**
     * 区间查询
     *
     * @param booleanClauses
     * @param min
     * @param max
     * @param fieldName
     */
    protected void numericRangeQuery(BooleanQuery.Builder booleanClauses, Long min, Long max, String fieldName) {
        boolean minInclusive  = !Objects.isNull(min);
        boolean maxInclusive  = !Objects.isNull(max);
        if(minInclusive || maxInclusive) {
            booleanClauses.add(new BooleanClause(LongPoint.newRangeQuery(fieldName, min.intValue(), max.intValue()), BooleanClause.Occur.MUST));
        }
    }


    /**
     * 价格区间查询
     *
     * @param booleanClauses
     * @param min
     * @param max
     * @param fieldName
     */
    protected void priceRangeQuery(BooleanQuery.Builder booleanClauses, Long min, Long max, String fieldName) {
        boolean minInclusive  = !Objects.isNull(min);
        boolean maxInclusive  = !Objects.isNull(max);
        if(minInclusive || maxInclusive) {
            if(Objects.isNull(min)){
                min = 0L;
            }
            if(Objects.isNull(max)){
                max = 1000000L;
            }
            booleanClauses.add(new BooleanClause(LongPoint.newRangeQuery(fieldName, min.intValue(), max.intValue()), BooleanClause.Occur.MUST));
        }
    }


    protected void moduleQuery(BooleanQuery.Builder booleanClauses, String module) {
        termQuery(booleanClauses, module, Snapshot.F_MODULE);
    }




    /**
     * 创建 lucene document
     *
     * @param snapshot
     * @return
     */
    public abstract Document createDocument(Snapshot snapshot);


    /**
     * 根据 websearch 返回 lucene Query
     *
     * @param keyword
     * @param module
     * @return
     */
    public abstract Query getQuery(Keyword keyword, String module);


    /**
     * 查询结果返回快照 bean
     *
     * @param searcher
     * @param topDocs
     * @param highlighter
     * @param keyword
     * @return
     * @throws Exception
     */
    public abstract List<Snapshot> getSnapshots(
            IndexSearcher searcher, TopDocs topDocs, Highlighter highlighter, Keyword keyword) throws Exception;

    /**
     * 索引重建数据返回
     *
     * @return
     */
    public abstract List<Snapshot> rebuild();
}
