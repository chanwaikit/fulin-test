package com.mazentop.modules.emp.warpper;

import com.mazentop.entity.CmsArticle;
import com.mazentop.entity.CmsArticleLink;
import com.mazentop.entity.ProSeo;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.jwt.security.Subject;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章内容
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/6/9 06:02
 */
@Data
public class Article implements Serializable {

    /**
     *  正常发布
     */
    public static final String STATUS_NORMAL = "normal";
    /**
     * 草稿
     */
    public static final String STATUS_DRAFT = "draft";
    /**
     * 垃圾箱
     */
    public static final String STATUS_TRASH = "trash";


    private static final long serialVersionUID = 3463463580744284019L;

    private String id;

    private String title;

    private String slug;

    private String content;

    private List<String> taxonomys;

    private String thumbnail;

    private String module;

    private String status;

    private List<String> tags;

    private ProSeo seo;

    public CmsArticle saveArticle() {
        CmsArticle cmsArticle = new CmsArticle();
        BeanUtils.copyProperties(this, cmsArticle);
        cmsArticle.setFkUserId(Subject.id());
        if(Helper.isBlank(cmsArticle.getId())) {
            cmsArticle.setSort(0);
        }
        // 若描述信息为非空数据进行一个html压缩操作
        if(!Utils.isBlank(cmsArticle.getContent())) {
            cmsArticle.setContent(Helper.compress(cmsArticle.getContent()));
        }
        cmsArticle.insertOrUpdate();
        if(!taxonomys.isEmpty()) {
            CmsArticleLink.me().setFkArticleId(cmsArticle.getId()).delete();
            for(String taxonomyId : taxonomys) {
                CmsArticleLink cmsArticleLink = new CmsArticleLink();
                cmsArticleLink.setFkArticleId(cmsArticle.getId());
                cmsArticleLink.setFkTaxonomyId(taxonomyId);
                cmsArticleLink.insert();
            }
        }
        return cmsArticle;
    }

    public Article warpper(CmsArticle cmsArticle) {
        BeanUtils.copyProperties(cmsArticle, this);
        List<CmsArticleLink> cmsArticleLinks = CmsArticleLink.me().setFkArticleId(cmsArticle.getId()).find();
        setTaxonomys(cmsArticleLinks.stream().map(CmsArticleLink::getFkTaxonomyId).collect(Collectors.toList()));
        return this;
    }
}
