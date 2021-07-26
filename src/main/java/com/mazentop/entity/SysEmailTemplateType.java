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
* Date:        14:54 2020/04/14
* Company:     美赞拓
* Version:     1.0
* Description: SysEmailTemplateType实体
*/
@SuppressWarnings("all")
public class SysEmailTemplateType extends BaseBean<SysEmailTemplateType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_email_template_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 邮件模板类型名称
    */
    public static final String F_EMAIL_TEMPLATE_TYPE_NAME = "email_template_type_name";
    /**
    * 邮件发送时刻
    */
    public static final String F_EMAIL_SEND_MOMENT = "email_send_moment";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_EMAIL_TEMPLATE_TYPE_NAME, null);
        put(F_EMAIL_SEND_MOMENT, null);
        put(F_REMARK, null);
    }

    public SysEmailTemplateType() {
        super();
    }

    public SysEmailTemplateType(Map<String, Object> map) {
        super(map);
    }

    public SysEmailTemplateType(String id) {
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
    public SysEmailTemplateType setId(String id) {
        set(F_ID, id);
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
    public SysEmailTemplateType setEmailTemplateTypeName(String emailTemplateTypeName) {
        set(F_EMAIL_TEMPLATE_TYPE_NAME, emailTemplateTypeName);
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
    public SysEmailTemplateType setEmailSendMoment(String emailSendMoment) {
        set(F_EMAIL_SEND_MOMENT, emailSendMoment);
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
    public SysEmailTemplateType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysEmailTemplateType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysEmailTemplateType me(){
        return new SysEmailTemplateType();
    }

    private static class Mapper implements RowMapper<SysEmailTemplateType> {

        private Supplier<SysEmailTemplateType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysEmailTemplateType mapRow(ResultSet rs, int rownum) throws SQLException {
            SysEmailTemplateType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setEmailTemplateTypeName(Utils.toCast(rs.getObject(F_EMAIL_TEMPLATE_TYPE_NAME), String.class));
            bean.setEmailSendMoment(Utils.toCast(rs.getObject(F_EMAIL_SEND_MOMENT), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysEmailTemplateType> newMapper(){
        return newMapper(SysEmailTemplateType::new);
    }

    public RowMapper<SysEmailTemplateType> newMapper(Supplier<SysEmailTemplateType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysEmailTemplateType {
        @Override
        public abstract RowMapper<SysEmailTemplateType> newMapper();
    }
}
