package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        10:04 2020/05/28
* Company:     美赞拓
* Version:     1.0
* Description: SysEmailTemplate实体
*/
@SuppressWarnings("all")
public class SysEmailTemplate extends BaseBean<SysEmailTemplate> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_email_template";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 邮件模板名称
     */
    public static final String F_EMAIL_TEMPLATE_NAME = "email_template_name";
    /**
     * 邮件模板类型名称
     */
    public static final String F_EMAIL_TEMPLATE_TYPE_NAME = "email_template_type_name";
    /**
     * 邮件模板类型编号
     */
    public static final String F_FK_TEMPLATE_TYPE_ID = "fk_template_type_id";
    /**
     * 邮件发送时刻
     */
    public static final String F_EMAIL_SEND_MOMENT = "email_send_moment";
    /**
     * 模板代码内容
     */
    public static final String F_TEMPLATE_CODE_CONTENT = "template_code_content";
    /**
     * 模板显示内容
     */
    public static final String F_TEMPLATE_SHOW_CONTENT = "template_show_content";
    /**
     * 主题
     */
    public static final String F_THEME = "theme";
    /**
     * 是否启用
     */
    public static final String F_IS_ENABLE = "is_enable";
    /**
     * 排序
     */
    public static final String F_SORT = "sort";
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
        put(F_EMAIL_TEMPLATE_NAME, null);
        put(F_EMAIL_TEMPLATE_TYPE_NAME, null);
        put(F_FK_TEMPLATE_TYPE_ID, null);
        put(F_EMAIL_SEND_MOMENT, null);
        put(F_TEMPLATE_CODE_CONTENT, null);
        put(F_TEMPLATE_SHOW_CONTENT, null);
        put(F_THEME, null);
        put(F_IS_ENABLE, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysEmailTemplate() {
        super();
    }

    public SysEmailTemplate(Map<String, Object> map) {
        super(map);
    }

    public SysEmailTemplate(String id) {
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
    public SysEmailTemplate setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return email_template_name to emailTemplateName 邮件模板名称<BR/>
     */
    public String getEmailTemplateName() {
        return getTypedValue(F_EMAIL_TEMPLATE_NAME, String.class);
    }
    /**
     * @param emailTemplateName to email_template_name 邮件模板名称 set
     */
    public SysEmailTemplate setEmailTemplateName(String emailTemplateName) {
        set(F_EMAIL_TEMPLATE_NAME, emailTemplateName);
        return this;
    }
    /**
     * @return email_template_type_name to emailTemplateTypeName 邮件模板类型名称<BR/>
     */
    public String getEmailTemplateTypeName() {
        return getTypedValue(F_EMAIL_TEMPLATE_TYPE_NAME, String.class);
    }
    /**
     * @param emailTemplateTypeName to email_template_type_name 邮件模板类型名称 set
     */
    public SysEmailTemplate setEmailTemplateTypeName(String emailTemplateTypeName) {
        set(F_EMAIL_TEMPLATE_TYPE_NAME, emailTemplateTypeName);
        return this;
    }
    /**
     * @return fk_template_type_id to fkTemplateTypeId 邮件模板类型编号<BR/>
     */
    public String getFkTemplateTypeId() {
        return getTypedValue(F_FK_TEMPLATE_TYPE_ID, String.class);
    }
    /**
     * @param fkTemplateTypeId to fk_template_type_id 邮件模板类型编号 set
     */
    public SysEmailTemplate setFkTemplateTypeId(String fkTemplateTypeId) {
        set(F_FK_TEMPLATE_TYPE_ID, fkTemplateTypeId);
        return this;
    }
    /**
     * @return email_send_moment to emailSendMoment 邮件发送时刻<BR/>
     */
    public String getEmailSendMoment() {
        return getTypedValue(F_EMAIL_SEND_MOMENT, String.class);
    }
    /**
     * @param emailSendMoment to email_send_moment 邮件发送时刻 set
     */
    public SysEmailTemplate setEmailSendMoment(String emailSendMoment) {
        set(F_EMAIL_SEND_MOMENT, emailSendMoment);
        return this;
    }
    /**
     * @return template_code_content to templateCodeContent 模板代码内容<BR/>
     */
    public String getTemplateCodeContent() {
        return getTypedValue(F_TEMPLATE_CODE_CONTENT, String.class);
    }
    /**
     * @param templateCodeContent to template_code_content 模板代码内容 set
     */
    public SysEmailTemplate setTemplateCodeContent(String templateCodeContent) {
        set(F_TEMPLATE_CODE_CONTENT, templateCodeContent);
        return this;
    }
    /**
     * @return template_show_content to templateShowContent 模板显示内容<BR/>
     */
    public String getTemplateShowContent() {
        return getTypedValue(F_TEMPLATE_SHOW_CONTENT, String.class);
    }
    /**
     * @param templateShowContent to template_show_content 模板显示内容 set
     */
    public SysEmailTemplate setTemplateShowContent(String templateShowContent) {
        set(F_TEMPLATE_SHOW_CONTENT, templateShowContent);
        return this;
    }
    /**
     * @return theme to theme 主题<BR/>
     */
    public String getTheme() {
        return getTypedValue(F_THEME, String.class);
    }
    /**
     * @param theme to theme 主题 set
     */
    public SysEmailTemplate setTheme(String theme) {
        set(F_THEME, theme);
        return this;
    }
    /**
     * @return is_enable to isEnable 是否启用<BR/>
     */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
     * @param isEnable to is_enable 是否启用 set
     */
    public SysEmailTemplate setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
     * @return sort to sort 排序<BR/>
     */
    public String getSort() {
        return getTypedValue(F_SORT, String.class);
    }
    /**
     * @param sort to sort 排序 set
     */
    public SysEmailTemplate setSort(String sort) {
        set(F_SORT, sort);
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
    public SysEmailTemplate setRemark(String remark) {
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
    public SysEmailTemplate setAddTime(Long addTime) {
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
    public SysEmailTemplate setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysEmailTemplate setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysEmailTemplate me(){
        return new SysEmailTemplate();
    }

    private static class Mapper implements RowMapper<SysEmailTemplate> {

        private Supplier<SysEmailTemplate> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysEmailTemplate mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysEmailTemplate bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setEmailTemplateName(Utils.toCast(columnsName.contains(F_EMAIL_TEMPLATE_NAME) ? rs.getObject(F_EMAIL_TEMPLATE_NAME) : null, String.class));
            bean.setEmailTemplateTypeName(Utils.toCast(columnsName.contains(F_EMAIL_TEMPLATE_TYPE_NAME) ? rs.getObject(F_EMAIL_TEMPLATE_TYPE_NAME) : null, String.class));
            bean.setFkTemplateTypeId(Utils.toCast(columnsName.contains(F_FK_TEMPLATE_TYPE_ID) ? rs.getObject(F_FK_TEMPLATE_TYPE_ID) : null, String.class));
            bean.setEmailSendMoment(Utils.toCast(columnsName.contains(F_EMAIL_SEND_MOMENT) ? rs.getObject(F_EMAIL_SEND_MOMENT) : null, String.class));
            bean.setTemplateCodeContent(Utils.toCast(columnsName.contains(F_TEMPLATE_CODE_CONTENT) ? rs.getObject(F_TEMPLATE_CODE_CONTENT) : null, String.class));
            bean.setTemplateShowContent(Utils.toCast(columnsName.contains(F_TEMPLATE_SHOW_CONTENT) ? rs.getObject(F_TEMPLATE_SHOW_CONTENT) : null, String.class));
            bean.setTheme(Utils.toCast(columnsName.contains(F_THEME) ? rs.getObject(F_THEME) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysEmailTemplate> newMapper(){
        return newMapper(SysEmailTemplate::new);
    }

    public RowMapper<SysEmailTemplate> newMapper(Supplier<SysEmailTemplate> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysEmailTemplate {
        @Override
        public abstract RowMapper<SysEmailTemplate> newMapper();
    }
}
