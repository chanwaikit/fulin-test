package com.mazentop.modules.freemarker.tag;


import com.mazentop.entity.CmsArticleLink;
import com.mazentop.entity.CmsTaxonomy;
import com.mazentop.model.Status;
import com.mazentop.util.Helper;
import com.mztframework.dao.annotation.Order;
import com.mztframework.render.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dengy
 * 文章类别
 */
@Component
public class CmsTaxonomysTag extends Tag {
    @Override
    public void execute() {
        String module = getParam("module");
        String pid = getParam("pid", Status.TREE_ROOT_NODE);
        String articleId = getParam("articleId");
        if (!Helper.isEmpty(articleId)) {
            String fkTaxonomyId = CmsArticleLink.me().setFkArticleId(articleId).get().getFkTaxonomyId();
            CmsTaxonomy taxonomy = CmsTaxonomy.me().setId(fkTaxonomyId).get();
            setVariable("taxonomy", taxonomy);
        }
        List<CmsTaxonomy> cmsTaxonomies =
                CmsTaxonomy.me()
                        .setPid(pid)
                        .setModule(module)
                        .setIsEnable(Status.YES)
                        .setOrderByFields(Order.asc(CmsTaxonomy.F_SORT))
                        .find();
        setVariable("taxonomys", cmsTaxonomies);
        renderBody();
    }

    @Override
    public String name() {
        return "cms_taxonomys";
    }
}
