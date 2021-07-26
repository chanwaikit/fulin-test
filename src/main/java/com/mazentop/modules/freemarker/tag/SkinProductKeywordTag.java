package com.mazentop.modules.freemarker.tag;

import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mztframework.commons.Utils;
import com.mztframework.dao.page.Page;
import com.mztframework.render.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品关键词查询相关推荐
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 01:40
 */
@Component
public class SkinProductKeywordTag extends Tag {

    @Autowired
    ISearcher searcher;

    @Override
    public void execute() {
        String keyword = getParam("keyword", "");
        String ignore = getParam("ignore", "");
        ProductKeyword productKeyword = new ProductKeyword();
        productKeyword.setK(keyword);
        Page<Snapshot> snapshotPage = searcher.search(productKeyword, IndexProvider.M_PRODUCT);

        Map<String, Object> map = new HashMap<>(2);

        map.put("isEmpty", snapshotPage.getList().isEmpty());
        if(!Utils.isBlank(ignore)) {
           List<Snapshot> snapshots = snapshotPage.getList().stream().filter(snapshot -> !snapshot.getId().equals(ignore)).collect(Collectors.toList());
            map.put("list", snapshots);
            map.put("isEmpty", snapshots.isEmpty());
        } else {
            map.put("list", snapshotPage.getList());
            map.put("isEmpty", snapshotPage.getList().isEmpty());
        }
        setVariable("relevant", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_product_keyword";
    }
}
