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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        09:48 2020/03/12
* Company:     美赞拓
* Version:     1.0
* Description: BloBlog实体
*/
@SuppressWarnings("all")
public class BloBlog extends BaseBean<BloBlog> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "blo_blog";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 标题
     */
    public static final String F_TITLE = "title";
    /**
     * 博客类型
     */
    public static final String F_FK_TYPE_ID = "fk_type_id";
    /**
     * 博客类型名称
     */
    public static final String F_TYPE_NAME = "type_name";
    /**
     * 概述
     */
    public static final String F_DESCRIPTION = "description";
    /**
     * 作者
     */
    public static final String F_AUTHOR = "author";
    /**
     * 预览次数
     */
    public static final String F_CLICK_NUM = "click_num";
    /**
     * 预览图
     */
    public static final String F_PRO_IMAGE_URL = "pro_image_url";
    /**
     * 内容
     */
    public static final String F_CONTENT = "content";
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
    /**
     * 添加人名称
     */
    public static final String F_ADD_USER_NAME = "add_user_name";
    /**
     * 操作时间
     */
    public static final String F_OPERATION_TIME = "operation_time";
    /**
     * 操作人编号
     */
    public static final String F_OPERATION_USER_ID = "operation_user_id";
    /**
     * 操作人名称
     */
    public static final String F_OPERATION_USER_NAME = "operation_user_name";
    /**
     * 公司编号
     */
    public static final String F_COMPANY_ID = "company_id";
    /**
     * 公司名称
     */
    public static final String F_COMPANY_NAME = "company_name";
    /**
     * 0.否1.是（是否发布）
     */
    public static final String F_IS_PUBLISH = "is_publish";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TITLE, null);
        put(F_FK_TYPE_ID, null);
        put(F_TYPE_NAME, null);
        put(F_DESCRIPTION, null);
        put(F_AUTHOR, null);
        put(F_CLICK_NUM, null);
        put(F_PRO_IMAGE_URL, null);
        put(F_CONTENT, null);
        put(F_SEO_TITLE, null);
        put(F_SEO_KEYWORDS, null);
        put(F_SEO_DESCRIPTION, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_IS_PUBLISH, null);
    }

    public BloBlog() {
        super();
    }

    public BloBlog(Map<String, Object> map) {
        super(map);
    }

    public BloBlog(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 主键 set
     */
    public BloBlog setId(String id) {
        set(F_ID, id);
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
    public BloBlog setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
     * @return fk_type_id to fkTypeId 博客类型<BR/>
     */
    public String getFkTypeId() {
        return getTypedValue(F_FK_TYPE_ID, String.class);
    }
    /**
     * @param fkTypeId to fk_type_id 博客类型 set
     */
    public BloBlog setFkTypeId(String fkTypeId) {
        set(F_FK_TYPE_ID, fkTypeId);
        return this;
    }
    /**
     * @return type_name to typeName 博客类型名称<BR/>
     */
    public String getTypeName() {
        return getTypedValue(F_TYPE_NAME, String.class);
    }
    /**
     * @param typeName to type_name 博客类型名称 set
     */
    public BloBlog setTypeName(String typeName) {
        set(F_TYPE_NAME, typeName);
        return this;
    }
    /**
     * @return description to description 概述<BR/>
     */
    public String getDescription() {
        return getTypedValue(F_DESCRIPTION, String.class);
    }
    /**
     * @param description to description 概述 set
     */
    public BloBlog setDescription(String description) {
        set(F_DESCRIPTION, description);
        return this;
    }
    /**
     * @return author to author 作者<BR/>
     */
    public String getAuthor() {
        return getTypedValue(F_AUTHOR, String.class);
    }
    /**
     * @param author to author 作者 set
     */
    public BloBlog setAuthor(String author) {
        set(F_AUTHOR, author);
        return this;
    }
    /**
     * @return click_num to clickNum 预览次数<BR/>
     */
    public Integer getClickNum() {
        return getTypedValue(F_CLICK_NUM, Integer.class);
    }
    /**
     * @param clickNum to click_num 预览次数 set
     */
    public BloBlog setClickNum(Integer clickNum) {
        set(F_CLICK_NUM, clickNum);
        return this;
    }
    /**
     * @return pro_image_url to proImageUrl 预览图<BR/>
     */
    public String getProImageUrl() {
        return getTypedValue(F_PRO_IMAGE_URL, String.class);
    }
    /**
     * @param proImageUrl to pro_image_url 预览图 set
     */
    public BloBlog setProImageUrl(String proImageUrl) {
        set(F_PRO_IMAGE_URL, proImageUrl);
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
    public BloBlog setContent(String content) {
        set(F_CONTENT, content);
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
    public BloBlog setSeoTitle(String seoTitle) {
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
    public BloBlog setSeoKeywords(String seoKeywords) {
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
    public BloBlog setSeoDescription(String seoDescription) {
        set(F_SEO_DESCRIPTION, seoDescription);
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
    public BloBlog setRemark(String remark) {
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
    public BloBlog setAddTime(Long addTime) {
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
    public BloBlog setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
     * @return add_user_name to addUserName 添加人名称<BR/>
     */
    public String getAddUserName() {
        return getTypedValue(F_ADD_USER_NAME, String.class);
    }
    /**
     * @param addUserName to add_user_name 添加人名称 set
     */
    public BloBlog setAddUserName(String addUserName) {
        set(F_ADD_USER_NAME, addUserName);
        return this;
    }
    /**
     * @return operation_time to operationTime 操作时间<BR/>
     */
    public Long getOperationTime() {
        return getTypedValue(F_OPERATION_TIME, Long.class);
    }
    /**
     * @param operationTime to operation_time 操作时间 set
     */
    public BloBlog setOperationTime(Long operationTime) {
        set(F_OPERATION_TIME, operationTime);
        return this;
    }
    /**
     * @return operation_user_id to operationUserId 操作人编号<BR/>
     */
    public String getOperationUserId() {
        return getTypedValue(F_OPERATION_USER_ID, String.class);
    }
    /**
     * @param operationUserId to operation_user_id 操作人编号 set
     */
    public BloBlog setOperationUserId(String operationUserId) {
        set(F_OPERATION_USER_ID, operationUserId);
        return this;
    }
    /**
     * @return operation_user_name to operationUserName 操作人名称<BR/>
     */
    public String getOperationUserName() {
        return getTypedValue(F_OPERATION_USER_NAME, String.class);
    }
    /**
     * @param operationUserName to operation_user_name 操作人名称 set
     */
    public BloBlog setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
     * @return company_id to companyId 公司编号<BR/>
     */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
     * @param companyId to company_id 公司编号 set
     */
    public BloBlog setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
     * @return company_name to companyName 公司名称<BR/>
     */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
     * @param companyName to company_name 公司名称 set
     */
    public BloBlog setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
     * @return is_publish to isPublish 0.否1.是（是否发布）<BR/>
     */
    public Integer getIsPublish() {
        return getTypedValue(F_IS_PUBLISH, Integer.class);
    }
    /**
     * @param isPublish to is_publish 0.否1.是（是否发布） set
     */
    public BloBlog setIsPublish(Integer isPublish) {
        set(F_IS_PUBLISH, isPublish);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public BloBlog setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static BloBlog me(){
        return new BloBlog();
    }

    private static class Mapper implements RowMapper<BloBlog> {

        private Supplier<BloBlog> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public BloBlog mapRow(ResultSet rs, int rownum) throws SQLException {
            BloBlog bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setFkTypeId(Utils.toCast(rs.getObject(F_FK_TYPE_ID), String.class));
            bean.setTypeName(Utils.toCast(rs.getObject(F_TYPE_NAME), String.class));
            bean.setDescription(Utils.toCast(rs.getObject(F_DESCRIPTION), String.class));
            bean.setAuthor(Utils.toCast(rs.getObject(F_AUTHOR), String.class));
            bean.setClickNum(Utils.toCast(rs.getObject(F_CLICK_NUM), Integer.class));
            bean.setProImageUrl(Utils.toCast(rs.getObject(F_PRO_IMAGE_URL), String.class));
            bean.setContent(Utils.toCast(rs.getObject(F_CONTENT), String.class));
            bean.setSeoTitle(Utils.toCast(rs.getObject(F_SEO_TITLE), String.class));
            bean.setSeoKeywords(Utils.toCast(rs.getObject(F_SEO_KEYWORDS), String.class));
            bean.setSeoDescription(Utils.toCast(rs.getObject(F_SEO_DESCRIPTION), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setIsPublish(Utils.toCast(rs.getObject(F_IS_PUBLISH), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<BloBlog> newMapper(){
        return newMapper(BloBlog::new);
    }

    public RowMapper<BloBlog> newMapper(Supplier<BloBlog> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends BloBlog {
        @Override
        public abstract RowMapper<BloBlog> newMapper();
    }
}
