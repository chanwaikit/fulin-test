package com.mazentop.searcher.index.impl;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.evaluation.service.EvaProProductService;
import com.mazentop.modules.skin.block.dto.CommentDto;
import com.mazentop.modules.web.dto.ProProductStockDto;
import com.mazentop.modules.web.service.ProductService;
import com.mazentop.modules.web.service.ProductTypeService;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.searcher.Keyword;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.dto.Sku;
import com.mazentop.searcher.index.AbstractIndexProvider;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.index.snapshot.ProductSnapshot;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaoqt
 * @title: ProductIndexProvider
 * @description: 产品索引提供者
 * @date 2019/5/920:14
 */
@Component(IndexProvider.M_PRODUCT)
@Slf4j
public class ProductIndexProvider extends AbstractIndexProvider {

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    ProductService productService;

    @Autowired
    EvaProProductService evaProductService;

    @Override
    public Document createDocument(Snapshot snapshot) {

        Document document = new Document();
        EvaProProduct evaProduct = new EvaProProduct().setId(snapshot.getId()).get();

        // 商品 标题 查询关键词 分类 缩略图
        snapshot.setTitle(evaProduct.getProductName());
        snapshot.setKeyword(evaProduct.getKeywords());
        snapshot.setThumbnail(evaProduct.getProductPicImageUrl());

        addCommonPart(document, snapshot, IndexProvider.M_PRODUCT);

        // 详情页
        addStringField(document, ProductSnapshot.F_URL, Seo.getSeoUrlDetail(evaProduct.getId(), ProSeoTypeEnum.TYPE_PRODUCT.type()));

        // 销售数量
        if (Helper.isEmpty(evaProduct.getSales())) {
            addIntField(document, ProductSnapshot.F_SALE_NUM, 0);
        } else {
            addIntField(document, ProductSnapshot.F_SALE_NUM,evaProduct.getSales());
        }

        // 商品分类
        List<ProProductType> proProductTypes = productService.findProProductType(evaProduct.getId());

        if(null != proProductTypes && proProductTypes.size() > 0){
            List<String> typeIds = proProductTypes.stream().map(ProProductType::getId).collect(Collectors.toList());
            document.add(new TextField(ProductSnapshot.F_TAXONOMY, String.join(",", typeIds), Field.Store.NO));
        }

        // 商品国家
        document.add(new StringField(ProductSnapshot.F_COUNTRY, evaProduct.getCountryId(), Field.Store.NO));

        // 标签
        String tagId = evaProduct.getFkTagId();
        if (!Objects.isNull(tagId)) {
            document.add(new StringField(ProductSnapshot.F_TAG, tagId, Field.Store.NO));
        }

        // 价格
        addLongField(document, ProductSnapshot.F_PRICE, evaProduct.getPrice());
        addLongField(document, ProductSnapshot.F_OLD_PRICE, evaProduct.getOriginalPrice());
        addLongField(document, ProductSnapshot.F_CASH_BACK, evaProduct.getRebates());

        // 审核通过时间
        document.add(new LongPoint(ProductSnapshot.F_DATE_INDEX, evaProduct.getShelveTime()));
        addLongField(document, ProductSnapshot.F_TIMES, evaProduct.getShelveTime());
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
                            ProductSnapshot.F_TITLE,
                    });

            // 分类查询
            if (!Helper.isBlank(productKeyword.getC())) {
                Query wildcardQuery = new WildcardQuery(new Term(ProductSnapshot.F_TAXONOMY,"*" + productKeyword.getC() + "*"));
                booleanClauses.add(wildcardQuery, BooleanClause.Occur.MUST);
            }
            // 国家查询
            termQuery(booleanClauses, productKeyword.getCountry(), ProductSnapshot.F_COUNTRY);
            // 商品标签
            termQuery(booleanClauses, productKeyword.getTag(), ProductSnapshot.F_TAG);

            // 价格区间查询
//            numericRangeQuery(booleanClauses, yuan2fen(productKeyword.getMinPrice()), yuan2fen(productKeyword.getMaxPrice()), ProductSnapshot.F_OLD_PRICE);
            priceRangeQuery(booleanClauses, yuan2fen(productKeyword.getMinPrice()), yuan2fen(productKeyword.getMaxPrice()), ProductSnapshot.F_PRICE);

            // 时间查询
            numericRangeQuery(booleanClauses, productKeyword.getPubdate(), null, ProductSnapshot.F_DATE_INDEX);
            moduleQuery(booleanClauses, module);
            return booleanClauses.build();
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private Long yuan2fen(Double yuan) {
        if (Objects.isNull(yuan)) {
            return null;
        }
        return Utils.transformY2F(yuan);
    }

    @Override
    public List<Snapshot> getSnapshots(IndexSearcher searcher, TopDocs topDocs, Highlighter highlighter, Keyword keyword) throws Exception {
        List<Snapshot> snapshots = new ArrayList<>();
        for (ScoreDoc item : topDocs.scoreDocs) {
            ProductSnapshot productSnapshot = new ProductSnapshot();
            productSnapshot.warpper(searcher.doc(item.doc));
            snapshots.add(productSnapshot);
        }
        return snapshots;
    }

    @Override
    public List<Snapshot> rebuild() {
        List<EvaProProduct> product = new EvaProProduct().setIsShelve(BooleanEnum.TRUE.getValue()).find();
        return product.stream().map(libProduct -> new Snapshot(libProduct.getId())).collect(Collectors.toList());
    }

}
