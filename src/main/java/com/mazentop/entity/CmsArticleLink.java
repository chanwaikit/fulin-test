package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      dengy
* Mail:        dengy@mazentop.com
* Date:        20:31 2020/06/04
* Company:     美赞拓
* Version:     1.0
* Description: CmsArticleLink实体
*/
@SuppressWarnings("all")
public class CmsArticleLink extends BaseBean<CmsArticleLink> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "cms_article_link";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 文章外键
    */
    public static final String F_FK_ARTICLE_ID = "fk_article_id";
    /**
    * 评论外键
    */
    public static final String F_FK_TAXONOMY_ID = "fk_taxonomy_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_ARTICLE_ID, null);
        put(F_FK_TAXONOMY_ID, null);
    }

    public CmsArticleLink() {
        super();
    }

    public CmsArticleLink(Map<String, Object> map) {
        super(map);
    }

    public CmsArticleLink(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id <BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id  set
    */
    public CmsArticleLink setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_article_id to fkArticleId 文章外键<BR/>
    */
    public String getFkArticleId() {
        return getTypedValue(F_FK_ARTICLE_ID, String.class);
    }
    /**
    * @param fkArticleId to fk_article_id 文章外键 set
    */
    public CmsArticleLink setFkArticleId(String fkArticleId) {
        set(F_FK_ARTICLE_ID, fkArticleId);
        return this;
    }
    /**
    * @return fk_taxonomy_id to fkTaxonomyId 评论外键<BR/>
    */
    public String getFkTaxonomyId() {
        return getTypedValue(F_FK_TAXONOMY_ID, String.class);
    }
    /**
    * @param fkTaxonomyId to fk_taxonomy_id 评论外键 set
    */
    public CmsArticleLink setFkTaxonomyId(String fkTaxonomyId) {
        set(F_FK_TAXONOMY_ID, fkTaxonomyId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CmsArticleLink setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CmsArticleLink me(){
        return new CmsArticleLink();
    }

    private static class Mapper implements RowMapper<CmsArticleLink> {

        private Supplier<CmsArticleLink> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CmsArticleLink mapRow(ResultSet rs, int rownum) throws SQLException {
            CmsArticleLink bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkArticleId(Utils.toCast(rs.getObject(F_FK_ARTICLE_ID), String.class));
            bean.setFkTaxonomyId(Utils.toCast(rs.getObject(F_FK_TAXONOMY_ID), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CmsArticleLink> newMapper(){
        return newMapper(CmsArticleLink::new);
    }

    public RowMapper<CmsArticleLink> newMapper(Supplier<CmsArticleLink> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CmsArticleLink {
        @Override
        public abstract RowMapper<CmsArticleLink> newMapper();
    }
}
