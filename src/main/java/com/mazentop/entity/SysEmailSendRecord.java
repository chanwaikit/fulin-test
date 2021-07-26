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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        14:54 2020/04/14
* Company:     美赞拓
* Version:     1.0
* Description: SysEmailSendRecord实体
*/
@SuppressWarnings("all")
public class SysEmailSendRecord extends BaseBean<SysEmailSendRecord> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_email_send_record";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 邮件模板编号
     */
    public static final String F_FK_EMAIL_TEMPLATE_ID = "fk_email_template_id";
    /**
     * 邮件模板名称
     */
    public static final String F_EMAIL_TEMPLATE_NAME = "email_template_name";
    /**
     * 发送时间
     */
    public static final String F_SEND_TIME = "send_time";
    /**
     * 收件人列表
     */
    public static final String F_SEND_PERSON_LIST = "send_person_list";
    /**
     * 发送内容
     */
    public static final String F_SEND_CONTENT = "send_content";
    /**
     * 是否发送成功
     */
    public static final String F_IS_SUCCESS = "is_success";
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
        put(F_FK_EMAIL_TEMPLATE_ID, null);
        put(F_EMAIL_TEMPLATE_NAME, null);
        put(F_SEND_TIME, null);
        put(F_SEND_PERSON_LIST, null);
        put(F_SEND_CONTENT, null);
        put(F_IS_SUCCESS, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysEmailSendRecord() {
        super();
    }

    public SysEmailSendRecord(Map<String, Object> map) {
        super(map);
    }

    public SysEmailSendRecord(String id) {
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
    public SysEmailSendRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_email_template_id to fkEmailTemplateId 邮件模板编号<BR/>
     */
    public String getFkEmailTemplateId() {
        return getTypedValue(F_FK_EMAIL_TEMPLATE_ID, String.class);
    }
    /**
     * @param fkEmailTemplateId to fk_email_template_id 邮件模板编号 set
     */
    public SysEmailSendRecord setFkEmailTemplateId(String fkEmailTemplateId) {
        set(F_FK_EMAIL_TEMPLATE_ID, fkEmailTemplateId);
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
    public SysEmailSendRecord setEmailTemplateName(String emailTemplateName) {
        set(F_EMAIL_TEMPLATE_NAME, emailTemplateName);
        return this;
    }
    /**
     * @return send_time to sendTime 发送时间<BR/>
     */
    public Long getSendTime() {
        return getTypedValue(F_SEND_TIME, Long.class);
    }
    /**
     * @param sendTime to send_time 发送时间 set
     */
    public SysEmailSendRecord setSendTime(Long sendTime) {
        set(F_SEND_TIME, sendTime);
        return this;
    }
    /**
     * @return send_person_list to sendPersonList 收件人列表<BR/>
     */
    public String getSendPersonList() {
        return getTypedValue(F_SEND_PERSON_LIST, String.class);
    }
    /**
     * @param sendPersonList to send_person_list 收件人列表 set
     */
    public SysEmailSendRecord setSendPersonList(String sendPersonList) {
        set(F_SEND_PERSON_LIST, sendPersonList);
        return this;
    }
    /**
     * @return send_content to sendContent 发送内容<BR/>
     */
    public String getSendContent() {
        return getTypedValue(F_SEND_CONTENT, String.class);
    }
    /**
     * @param sendContent to send_content 发送内容 set
     */
    public SysEmailSendRecord setSendContent(String sendContent) {
        set(F_SEND_CONTENT, sendContent);
        return this;
    }
    /**
     * @return is_success to isSuccess 是否发送成功<BR/>
     */
    public Integer getIsSuccess() {
        return getTypedValue(F_IS_SUCCESS, Integer.class);
    }
    /**
     * @param isSuccess to is_success 是否发送成功 set
     */
    public SysEmailSendRecord setIsSuccess(Integer isSuccess) {
        set(F_IS_SUCCESS, isSuccess);
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
    public SysEmailSendRecord setRemark(String remark) {
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
    public SysEmailSendRecord setAddTime(Long addTime) {
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
    public SysEmailSendRecord setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysEmailSendRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysEmailSendRecord me(){
        return new SysEmailSendRecord();
    }

    private static class Mapper implements RowMapper<SysEmailSendRecord> {

        private Supplier<SysEmailSendRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysEmailSendRecord mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysEmailSendRecord bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkEmailTemplateId(Utils.toCast(columnsName.contains(F_FK_EMAIL_TEMPLATE_ID) ? rs.getObject(F_FK_EMAIL_TEMPLATE_ID) : null, String.class));
            bean.setEmailTemplateName(Utils.toCast(columnsName.contains(F_EMAIL_TEMPLATE_NAME) ? rs.getObject(F_EMAIL_TEMPLATE_NAME) : null, String.class));
            bean.setSendTime(Utils.toCast(columnsName.contains(F_SEND_TIME) ? rs.getObject(F_SEND_TIME) : null, Long.class));
            bean.setSendPersonList(Utils.toCast(columnsName.contains(F_SEND_PERSON_LIST) ? rs.getObject(F_SEND_PERSON_LIST) : null, String.class));
            bean.setSendContent(Utils.toCast(columnsName.contains(F_SEND_CONTENT) ? rs.getObject(F_SEND_CONTENT) : null, String.class));
            bean.setIsSuccess(Utils.toCast(columnsName.contains(F_IS_SUCCESS) ? rs.getObject(F_IS_SUCCESS) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysEmailSendRecord> newMapper(){
        return newMapper(SysEmailSendRecord::new);
    }

    public RowMapper<SysEmailSendRecord> newMapper(Supplier<SysEmailSendRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysEmailSendRecord {
        @Override
        public abstract RowMapper<SysEmailSendRecord> newMapper();
    }
}
