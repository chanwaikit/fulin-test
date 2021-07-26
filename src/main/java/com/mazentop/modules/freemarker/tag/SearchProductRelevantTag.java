package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProSeo;
import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.index.snapshot.ProductParamGroupSnapshot;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mztframework.dao.page.Page;
import com.mztframework.render.Tag;
import com.mztframework.spring.SpringContextHolder;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

/**
 * 产品搜索相关条件
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/5 21:58
 */
public class SearchProductRelevantTag extends Tag {

    private ProductKeyword keyword;

    private ISearcher searcher = SpringContextHolder.getBean(ISearcher.class);

    public SearchProductRelevantTag(ProductKeyword keyword) {
        this.keyword = new ProductKeyword();
        BeanUtils.copyProperties(keyword, this.keyword);
    }


    @Override
    public void execute() {
        keyword.setPagination(false);
        keyword.setPageSize(100);
        Page<Snapshot> snapshotPage = searcher.search(keyword, IndexProvider.M_PRODUCT_PARAM);
        Optional<Snapshot> snapshotOptional = snapshotPage.getList().stream().findFirst();

        if(snapshotOptional.isPresent()) {
            ProductParamGroupSnapshot productParamGroupSnapshot = (ProductParamGroupSnapshot)snapshotOptional.get();
            productParamGroupSnapshot.getTypes().forEach(item -> {
                item.setId(Optional.ofNullable(ProSeo.me().setSource(item.getId()).findFirst()).orElse(ProSeo.me()).getSeoAddress());;
            });
            setVariable("relevant", productParamGroupSnapshot);
        } else {
            setVariable("relevant", new ProductParamGroupSnapshot());
        }
        renderBody();
    }

    @Override
    public String name() {
        return "search_product_relevant";
    }
}
