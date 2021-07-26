package com.mazentop.searcher.index;

import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.engine.analysis.AnalysisEngine;
import com.google.common.collect.Lists;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.model.BooleanEnum;
import com.mazentop.searcher.SearchParam;
import com.mazentop.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: Wangzy
 * @Date: 2019/7/2 10:08
 * @Description:
 */
@Component
@Slf4j
public class SuggesterIndexProvideer {

    public List<SearchParam> build() {

        List<ProProductMaster> productMasters = new ProProductMaster().setIsShelve(BooleanEnum.TRUE.getValue()).find();
        ArrayList<SearchParam> searchParams = new ArrayList<>();
        for (ProProductMaster productMaster : productMasters) {
            List<List<String>> valueGroup = new ArrayList<>();
            Analyzer analyzer = new StandardAnalyzer();
            TokenizerEngine engine = new AnalysisEngine(analyzer);
            Result result = engine.parse(productMaster.getProductName());
            List<String> nameWords = new ArrayList<>();
            result.forEach(word -> {
                if(word.getText().length() > 1) {
                    nameWords.add(word.getText());
                }
            });
            valueGroup.add(nameWords);

            // guan jian ci
//            List<String> keywords = Utils.splitForList(productMaster.getKeywords(), ',');
//            keywords.forEach(s -> searchParams.add(new SearchParam(s)));
//            valueGroup.add(keywords);

//            List<LibProductattrLink> links = LibProductattrLink.me().setFkProductId(libProduct.getId()).list();
//            Map<Integer, List<LibProductattrLink>> attrMap = links.stream().collect(Collectors.groupingBy(LibProductattrLink::getAttrNameCode));
//                // sku  shu xing
//            attrMap.forEach((k, v) -> {
//                Set<String> values = new HashSet<>();
//                v.forEach(libProductattrLink -> values.add(new LibProductattrValue(libProductattrLink.getAttrValueCode()).get().getValue()));
//                valueGroup.add(new ArrayList<>(values));
//            });
            List<List<String>> groupList = Helper.descartes(valueGroup, Lists.newArrayList(), 0, Lists.newArrayList());
            groupList.forEach(strings -> searchParams.add(new SearchParam(String.join(" ", strings))));

        }

        return searchParams.stream().filter(Helper.distinctByKey(SearchParam::getKeywords)).collect(Collectors.toList());
    }

    public List<SearchParam> build(String id) {

        List<ProProductMaster> productMasters = new ProProductMaster().setId(id).find();
        ArrayList<SearchParam> searchParams = new ArrayList<>();
        for (ProProductMaster productMaster : productMasters) {
            List<List<String>> valueGroup = new ArrayList<>();
            Analyzer analyzer = new StandardAnalyzer();
            TokenizerEngine engine = new AnalysisEngine(analyzer);
            Result result = engine.parse(productMaster.getProductName());
            List<String> nameWords = new ArrayList<>();
            result.forEach(word -> {
                if(word.getText().length() > 1) {
                    nameWords.add(word.getText());
                }
            });
            valueGroup.add(nameWords);
            List<List<String>> groupList = Helper.descartes(valueGroup, Lists.newArrayList(), 0, Lists.newArrayList());
            groupList.forEach(strings -> searchParams.add(new SearchParam(String.join(" ", strings))));

        }

        return searchParams.stream().filter(Helper.distinctByKey(SearchParam::getKeywords)).collect(Collectors.toList());
    }


}
