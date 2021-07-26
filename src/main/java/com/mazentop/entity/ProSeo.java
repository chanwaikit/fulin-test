package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        10:22 2020/04/27
* Company:     美赞拓
* Version:     1.0
* Description: ProSeo实体
*/
@SuppressWarnings("all")
public class ProSeo extends BaseBean<ProSeo> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_seo";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 来源id
    */
    public static final String F_SOURCE = "source";
    /**
    * 来源类型
    */
    public static final String F_TYPE = "type";
    /**
    * 是否独立编辑
    */
    public static final String F_IS_BRANCH = "is_branch";
    /**
    * SEO-标题
    */
    public static final String F_SEO_TITLE = "seo_title";
    /**
    * SEO-关键词
    */
    public static final String F_SEO_KEYWORDS = "seo_keywords";
    /**
    * SEO-概述
    */
    public static final String F_SEO_DESCRIPTION = "seo_description";
    /**
    * SEO-地址
    */
    public static final String F_SEO_ADDRESS = "seo_address";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SOURCE, null);
        put(F_TYPE, null);
        put(F_IS_BRANCH, null);
        put(F_SEO_TITLE, null);
        put(F_SEO_KEYWORDS, null);
        put(F_SEO_DESCRIPTION, null);
        put(F_SEO_ADDRESS, null);
    }

    public ProSeo() {
        super();
    }

    public ProSeo(Map<String, Object> map) {
        super(map);
    }

    public ProSeo(String id) {
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
    public ProSeo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return source to source 来源id<BR/>
    */
    public String getSource() {
        return getTypedValue(F_SOURCE, String.class);
    }
    /**
    * @param source to source 来源id set
    */
    public ProSeo setSource(String source) {
        set(F_SOURCE, source);
        return this;
    }
    /**
    * @return type to type 来源类型<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 来源类型 set
    */
    public ProSeo setType(String type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return is_branch to isBranch 是否独立编辑<BR/>
    */
    public Integer getIsBranch() {
        return getTypedValue(F_IS_BRANCH, Integer.class);
    }
    /**
    * @param isBranch to is_branch 是否独立编辑 set
    */
    public ProSeo setIsBranch(Integer isBranch) {
        set(F_IS_BRANCH, isBranch);
        return this;
    }
    /**
    * @return seo_title to seoTitle SEO-标题<BR/>
    */
    public String getSeoTitle() {
        return getTypedValue(F_SEO_TITLE, String.class);
    }
    /**
    * @param seoTitle to seo_title SEO-标题 set
    */
    public ProSeo setSeoTitle(String seoTitle) {
        set(F_SEO_TITLE, seoTitle);
        return this;
    }
    /**
    * @return seo_keywords to seoKeywords SEO-关键词<BR/>
    */
    public String getSeoKeywords() {
        return getTypedValue(F_SEO_KEYWORDS, String.class);
    }
    /**
    * @param seoKeywords to seo_keywords SEO-关键词 set
    */
    public ProSeo setSeoKeywords(String seoKeywords) {
        set(F_SEO_KEYWORDS, seoKeywords);
        return this;
    }
    /**
    * @return seo_description to seoDescription SEO-概述<BR/>
    */
    public String getSeoDescription() {
        return getTypedValue(F_SEO_DESCRIPTION, String.class);
    }
    /**
    * @param seoDescription to seo_description SEO-概述 set
    */
    public ProSeo setSeoDescription(String seoDescription) {
        set(F_SEO_DESCRIPTION, seoDescription);
        return this;
    }
    /**
    * @return seo_address to seoAddress SEO-地址<BR/>
    */
    public String getSeoAddress() {
        return getTypedValue(F_SEO_ADDRESS, String.class);
    }
    /**
    * @param seoAddress to seo_address SEO-地址 set
    */
    public ProSeo setSeoAddress(String seoAddress) {
        set(F_SEO_ADDRESS, seoAddress);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProSeo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProSeo me(){
        return new ProSeo();
    }

    private static class Mapper implements RowMapper<ProSeo> {

        private Supplier<ProSeo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProSeo mapRow(ResultSet rs, int rownum) throws SQLException {
            ProSeo bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setSource(Utils.toCast(rs.getObject(F_SOURCE), String.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), String.class));
            bean.setIsBranch(Utils.toCast(rs.getObject(F_IS_BRANCH), Integer.class));
            bean.setSeoTitle(Utils.toCast(rs.getObject(F_SEO_TITLE), String.class));
            bean.setSeoKeywords(Utils.toCast(rs.getObject(F_SEO_KEYWORDS), String.class));
            bean.setSeoDescription(Utils.toCast(rs.getObject(F_SEO_DESCRIPTION), String.class));
            bean.setSeoAddress(Utils.toCast(rs.getObject(F_SEO_ADDRESS), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProSeo> newMapper(){
        return newMapper(ProSeo::new);
    }

    public RowMapper<ProSeo> newMapper(Supplier<ProSeo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProSeo {
        @Override
        public abstract RowMapper<ProSeo> newMapper();
    }
}
