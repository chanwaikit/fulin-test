package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
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
* Date:        09:33 2020/03/19
* Company:     美赞拓
* Version:     1.0
* Description: SysHelpCenterContent实体
*/
@SuppressWarnings("all")
public class SysHelpCenterContent extends BaseBean<SysHelpCenterContent> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_help_center_content";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 帮助中心分类编号
     */
    public static final String F_FK_HELP_CENTER_TYPE_ID = "fk_help_center_type_id";
    /**
     * 帮助中心分类名称
     */
    public static final String F_HELP_CENTER_TYPE_NAME = "help_center_type_name";
    /**
     * 标题
     */
    public static final String F_TITLE = "title";
    /**
     * 内容
     */
    public static final String F_CONTENT = "content";
    /**
     * 同级排序
     */
    public static final String F_SORT = "sort";
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
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_HELP_CENTER_TYPE_ID, null);
        put(F_HELP_CENTER_TYPE_NAME, null);
        put(F_TITLE, null);
        put(F_CONTENT, null);
        put(F_SORT, null);
        put(F_SEO_TITLE, null);
        put(F_SEO_KEYWORDS, null);
        put(F_SEO_DESCRIPTION, null);
        put(F_SEO_ADDRESS, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysHelpCenterContent() {
        super();
    }

    public SysHelpCenterContent(Map<String, Object> map) {
        super(map);
    }

    public SysHelpCenterContent(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 编号<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 编号 set
     */
    public SysHelpCenterContent setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_help_center_type_id to fkHelpCenterTypeId 帮助中心分类编号<BR/>
     */
    public String getFkHelpCenterTypeId() {
        return getTypedValue(F_FK_HELP_CENTER_TYPE_ID, String.class);
    }
    /**
     * @param fkHelpCenterTypeId to fk_help_center_type_id 帮助中心分类编号 set
     */
    public SysHelpCenterContent setFkHelpCenterTypeId(String fkHelpCenterTypeId) {
        set(F_FK_HELP_CENTER_TYPE_ID, fkHelpCenterTypeId);
        return this;
    }
    /**
     * @return help_center_type_name to helpCenterTypeName 帮助中心分类名称<BR/>
     */
    public String getHelpCenterTypeName() {
        return getTypedValue(F_HELP_CENTER_TYPE_NAME, String.class);
    }
    /**
     * @param helpCenterTypeName to help_center_type_name 帮助中心分类名称 set
     */
    public SysHelpCenterContent setHelpCenterTypeName(String helpCenterTypeName) {
        set(F_HELP_CENTER_TYPE_NAME, helpCenterTypeName);
        return this;
    }
    /**
     * @return title to title 标题<BR/>
     */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
     * @param title to title 标题 set
     */
    public SysHelpCenterContent setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
     * @return content to content 内容<BR/>
     */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
     * @param content to content 内容 set
     */
    public SysHelpCenterContent setContent(String content) {
        set(F_CONTENT, content);
        return this;
    }
    /**
     * @return sort to sort 同级排序<BR/>
     */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
     * @param sort to sort 同级排序 set
     */
    public SysHelpCenterContent setSort(Integer sort) {
        set(F_SORT, sort);
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
    public SysHelpCenterContent setSeoTitle(String seoTitle) {
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
    public SysHelpCenterContent setSeoKeywords(String seoKeywords) {
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
    public SysHelpCenterContent setSeoDescription(String seoDescription) {
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
    public SysHelpCenterContent setSeoAddress(String seoAddress) {
        set(F_SEO_ADDRESS, seoAddress);
        return this;
    }
    /**
     * @return remark to remark 备注<BR/>
     */
    public String getRemark() {
        return getTypedValue(F_REMARK, String.class);
    }
    /**
     * @param remark to remark 备注 set
     */
    public SysHelpCenterContent setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }
    /**
     * @return add_time to addTime 添加时间<BR/>
     */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
     * @param addTime to add_time 添加时间 set
     */
    public SysHelpCenterContent setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return add_user_id to addUserId 添加人编号<BR/>
     */
    public String getAddUserId() {
        return getTypedValue(F_ADD_USER_ID, String.class);
    }
    /**
     * @param addUserId to add_user_id 添加人编号 set
     */
    public SysHelpCenterContent setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysHelpCenterContent setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysHelpCenterContent me(){
        return new SysHelpCenterContent();
    }

    private static class Mapper implements RowMapper<SysHelpCenterContent> {

        private Supplier<SysHelpCenterContent> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysHelpCenterContent mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysHelpCenterContent bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkHelpCenterTypeId(Utils.toCast(columnsName.contains(F_FK_HELP_CENTER_TYPE_ID) ? rs.getObject(F_FK_HELP_CENTER_TYPE_ID) : null, String.class));
            bean.setHelpCenterTypeName(Utils.toCast(columnsName.contains(F_HELP_CENTER_TYPE_NAME) ? rs.getObject(F_HELP_CENTER_TYPE_NAME) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setSeoTitle(Utils.toCast(columnsName.contains(F_SEO_TITLE) ? rs.getObject(F_SEO_TITLE) : null, String.class));
            bean.setSeoKeywords(Utils.toCast(columnsName.contains(F_SEO_KEYWORDS) ? rs.getObject(F_SEO_KEYWORDS) : null, String.class));
            bean.setSeoDescription(Utils.toCast(columnsName.contains(F_SEO_DESCRIPTION) ? rs.getObject(F_SEO_DESCRIPTION) : null, String.class));
            bean.setSeoAddress(Utils.toCast(columnsName.contains(F_SEO_ADDRESS) ? rs.getObject(F_SEO_ADDRESS) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysHelpCenterContent> newMapper(){
        return newMapper(SysHelpCenterContent::new);
    }

    public RowMapper<SysHelpCenterContent> newMapper(Supplier<SysHelpCenterContent> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysHelpCenterContent {
        @Override
        public abstract RowMapper<SysHelpCenterContent> newMapper();
    }
}
