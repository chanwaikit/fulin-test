package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.CmsArticle;
import com.mztframework.render.Tag;
import org.springframework.stereotype.Component;

/**
 * @author dengy
 * 文章详情
 */
@Component
public class CmsArticleTag extends Tag {
    @Override
    public void execute() {
        String id = getParam("id");
        String module = getParam("module");

        CmsArticle cmsArticle =
                CmsArticle.me()
                        .setId(id)
                        .setModule(module)
                        .setStatus("normal")
                        .get();
        setVariable("article", cmsArticle);
        renderBody();


    }

    @Override
    public String name() {
        return "cms_article";
    }
}
