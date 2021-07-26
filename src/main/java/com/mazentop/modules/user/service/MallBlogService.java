package com.mazentop.modules.user.service;

import com.mazentop.entity.BloBlog;
import com.mazentop.modules.emp.commond.BloBlogCommond;
import com.mazentop.modules.web.constants.*;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.dao.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cq
 */
@Service
public class MallBlogService {

    @Autowired
    private MallHomeService mallHomeService;


    /**
     * 博客分页查询
     * @param page 页
     * @param pageSize 条数
     * @param blogTitle 标题
     * @return Page
     */
    public Page page(Integer page, Integer pageSize, String blogTitle) {

        BloBlogCommond blogCommond = new BloBlogCommond();
        blogCommond.setP(page);
        blogCommond.setPageSize(pageSize);
        blogCommond.setPublish(BlogConstant.CONFIRM_PUBLISH);
        blogCommond.setO("-" + BloBlog.F_ADD_TIME);
        if (StringUtils.isNoneBlank(blogTitle)) {
            blogCommond.setTitle(blogTitle);
        }
        Page paginate = BloBlog.me().paginate(blogCommond);
        paginate.getList().forEach(o ->{
            BloBlog b = (BloBlog) o;
            b.addExten("seoUrl", Seo.getSeoUrlForBlog(b.getId()));
            b.addExten("addTime", Helper.timeStampFormat(b.getAddTime()));
        });

        return paginate;
    }

    /**
     * 根据ID查询博客详情
     * @param blogId 博客ID
     * @return BloBlog
     */
    public BloBlog detail(String blogId) {

        BloBlog bloBlog = BloBlog.me().setId(blogId).get();
        bloBlog.addExten("addTime", Helper.timeStampFormat(bloBlog.getAddTime()));

        return bloBlog;
    }

}
