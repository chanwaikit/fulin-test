package com.mazentop.searcher.index.impl;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductSpec;
import com.mazentop.entity.ProProductType;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.web.dto.ColorCardDto;
import com.mazentop.modules.web.dto.ProProductSpecDto;
import com.mazentop.modules.web.service.ProductService;
import com.mazentop.searcher.Keyword;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.index.AbstractIndexProvider;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.index.snapshot.ProductParamGroupSnapshot;
import com.mazentop.searcher.index.snapshot.ProductParamSnapshot;
import com.mazentop.searcher.index.snapshot.ProductSnapshot;
import com.mazentop.searcher.keyword.ProductKeyword;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品参数索引
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/5/12 04:25
 */
@Component(IndexProvider.M_PRODUCT_PARAM)
@Slf4j
public class ProductParamIndexProvider extends AbstractIndexProvider {

    public static final String SID_SUFFIX = "-param";

    @Autowired
    ProductService productService;

    @Override
    public Document createDocument(Snapshot snapshot) {
        Document document = new Document();
        String productId = snapshot.getId().replace(SID_SUFFIX, "");
        EvaProProduct evaProduct = new EvaProProduct().setId(productId).get();

        // 商品 标题 查询关键词
        // 重置 doc sid
        snapshot.setId(snapshot.getId());
        snapshot.setTitle(evaProduct.getProductName());
        snapshot.setKeyword(evaProduct.getKeywords());
        addCommonPart(document, snapshot, IndexProvider.M_PRODUCT_PARAM);

        // 分类
        List<ProProductType> proProductTypes = productService.findProProductType(evaProduct.getId());
        List<String> typeIds = proProductTypes.stream().map(ProProductType::getId).collect(Collectors.toList());
        document.add(new TextField(ProductSnapshot.F_TAXONOMY, String.join(",", typeIds), Field.Store.NO));

        List<ProductParamSnapshot.Type> types = proProductTypes.stream()
                .map(proProductType -> {
                    ProductParamSnapshot.Type type = new ProductParamSnapshot.Type();
                    type.setId(proProductType.getId());
                    type.setName(proProductType.getProductTypeName());
                    return type;
                }).collect(Collectors.toList());
        addStringField(document, ProductSnapshot.F_TAXONOMY_VALUE, JSON.toJSONString(types));

        return document;
    }

    @Override
    public Query getQuery(Keyword keyword, String module) {
        try {
            ProductKeyword productKeyword = (ProductKeyword) keyword;
            BooleanQuery.Builder booleanClauses = new BooleanQuery.Builder();
            // 关键词查询
            queryParser(booleanClauses, productKeyword.getMakeKeyword(),
                    new String[]{
                            ProductSnapshot.F_KEYWORD,
                            ProductSnapshot.F_TITLE
//                            ProductSnapshot.F_SKU_INDEX
                    });
            moduleQuery(booleanClauses, module);
            return booleanClauses.build();
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Snapshot> getSnapshots(IndexSearcher searcher, TopDocs topDocs, Highlighter highlighter, Keyword keyword) throws Exception {
        List<ProductParamSnapshot> productParamSnapshots = new ArrayList<>();
        for (ScoreDoc item : topDocs.scoreDocs) {
            ProductParamSnapshot productParamSnapshot = new ProductParamSnapshot();
            productParamSnapshot.warp(searcher.doc(item.doc));
            productParamSnapshots.add(productParamSnapshot);
        }

        ProductParamGroupSnapshot productParamGroupSnapshot = new ProductParamGroupSnapshot();

        // 合并所有分类/ 品牌/ 色卡
        Set<ProductParamSnapshot.Type> types = new HashSet<>();
        Set<ProductParamSnapshot.Brand> brands = new HashSet<>();
        Set<ProductParamSnapshot.Color> colors = new HashSet<>();
        List<ProductParamSnapshot.Spec> specs = new ArrayList<>();
        productParamSnapshots.forEach(productParamSnapshot -> {
            brands.add(productParamSnapshot.getBrand());
            types.addAll(productParamSnapshot.getTypes());
            colors.addAll(productParamSnapshot.getColors());
            specs.addAll(productParamSnapshot.getSpecs());
        });
        productParamGroupSnapshot.setTypes(new ArrayList<>(types));
        productParamGroupSnapshot.setBrands(new ArrayList<>(brands));
        productParamGroupSnapshot.setColors(new ArrayList<>(colors));

        // 合并规格
        Map<String, List<ProductParamSnapshot.Spec>> specMap =
                specs.stream().collect(Collectors.groupingBy(ProductParamSnapshot.Spec::getName));

        Map<String, List<String>> specStringMap = new HashMap<>(specMap.size());
        specMap.forEach((k, v) -> {
            // 去重规格值
            Set<String> valueSet = new HashSet<>();
            v.forEach(specValue -> valueSet.addAll(specValue.getAttrs()));
            specStringMap.put(k, new ArrayList<>(valueSet));
        });
        productParamGroupSnapshot.setSpecMap(specStringMap);

        return Collections.singletonList(productParamGroupSnapshot);
    }

    @Override
    public List<Snapshot> rebuild() {
        List<EvaProProduct> product = new EvaProProduct().setIsShelve(BooleanEnum.TRUE.getValue()).find();
        return product.stream().map( libProduct ->
                new Snapshot(String.format("%s%s",libProduct.getId(), SID_SUFFIX))).collect(Collectors.toList());
    }
}
