package com.mazentop.modules.emp.service;

import com.mazentop.entity.CmsArticleLink;
import com.mazentop.entity.CmsTaxonomy;
import com.mztframework.commons.Maps;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wangzh
 * @DATE 2019/7/8
 */
@Service
public class CmsArticleService {

    @Autowired
    BaseDao baseDao;

    public Map<String, List<Map<String, Object>>> sortable(String module){
        List<Map<String, Object>> lists =  baseDao.queryForList("select * from cms_article where status = 'normal' and module = ? order by sort, add_time desc", module);
        for (Map<String, Object> stringObjectMap: lists) {
            CmsArticleLink cmsArticleLink = CmsArticleLink.me().setFkArticleId(stringObjectMap.get("id").toString()).get();
            if(!Objects.isNull(cmsArticleLink)){
                CmsTaxonomy cmsTaxonomy = CmsTaxonomy.me().setId(cmsArticleLink.getFkTaxonomyId()).get();
                stringObjectMap.put("name",cmsTaxonomy.getTitle() + "-" + stringObjectMap.get("title"));
            }else{
                stringObjectMap.put("name",stringObjectMap.get("title"));
            }
        }
        return Maps.of("list", lists);
    }
}
