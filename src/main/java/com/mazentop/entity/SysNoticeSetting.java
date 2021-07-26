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
* Date:        18:53 2020/04/09
* Company:     美赞拓
* Version:     1.0
* Description: SysNoticeSetting实体
*/
@SuppressWarnings("all")
public class SysNoticeSetting extends BaseBean<SysNoticeSetting> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_notice_setting";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 通知标题
    */
    public static final String F_NOTICE_TITLE = "notice_title";
    /**
    * 通知内容
    */
    public static final String F_NOTICE_CONTENT = "notice_content";
    /**
    * 是否启动
    */
    public static final String F_IS_SUCCESS = "is_success";
    /**
    * 通知类型编号
    */
    public static final String F_FK_NOTICE_TYPE_ID = "fk_notice_type_id";
    /**
    * 通知类型名称
    */
    public static final String F_NOTICE_TYPE_NAME = "notice_type_name";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_NOTICE_TITLE, null);
        put(F_NOTICE_CONTENT, null);
        put(F_IS_SUCCESS, null);
        put(F_FK_NOTICE_TYPE_ID, null);
        put(F_NOTICE_TYPE_NAME, null);
        put(F_REMARK, null);
    }

    public SysNoticeSetting() {
        super();
    }

    public SysNoticeSetting(Map<String, Object> map) {
        super(map);
    }

    public SysNoticeSetting(String id) {
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
    public SysNoticeSetting setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return notice_title to noticeTitle 通知标题<BR/>
    */
    public String getNoticeTitle() {
        return getTypedValue(F_NOTICE_TITLE, String.class);
    }
    /**
    * @param noticeTitle to notice_title 通知标题 set
    */
    public SysNoticeSetting setNoticeTitle(String noticeTitle) {
        set(F_NOTICE_TITLE, noticeTitle);
        return this;
    }
    /**
    * @return notice_content to noticeContent 通知内容<BR/>
    */
    public String getNoticeContent() {
        return getTypedValue(F_NOTICE_CONTENT, String.class);
    }
    /**
    * @param noticeContent to notice_content 通知内容 set
    */
    public SysNoticeSetting setNoticeContent(String noticeContent) {
        set(F_NOTICE_CONTENT, noticeContent);
        return this;
    }
    /**
    * @return is_success to isSuccess 是否启动<BR/>
    */
    public String getIsSuccess() {
        return getTypedValue(F_IS_SUCCESS, String.class);
    }
    /**
    * @param isSuccess to is_success 是否启动 set
    */
    public SysNoticeSetting setIsSuccess(String isSuccess) {
        set(F_IS_SUCCESS, isSuccess);
        return this;
    }
    /**
    * @return fk_notice_type_id to fkNoticeTypeId 通知类型编号<BR/>
    */
    public String getFkNoticeTypeId() {
        return getTypedValue(F_FK_NOTICE_TYPE_ID, String.class);
    }
    /**
    * @param fkNoticeTypeId to fk_notice_type_id 通知类型编号 set
    */
    public SysNoticeSetting setFkNoticeTypeId(String fkNoticeTypeId) {
        set(F_FK_NOTICE_TYPE_ID, fkNoticeTypeId);
        return this;
    }
    /**
    * @return notice_type_name to noticeTypeName 通知类型名称<BR/>
    */
    public String getNoticeTypeName() {
        return getTypedValue(F_NOTICE_TYPE_NAME, String.class);
    }
    /**
    * @param noticeTypeName to notice_type_name 通知类型名称 set
    */
    public SysNoticeSetting setNoticeTypeName(String noticeTypeName) {
        set(F_NOTICE_TYPE_NAME, noticeTypeName);
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
    public SysNoticeSetting setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysNoticeSetting setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysNoticeSetting me(){
        return new SysNoticeSetting();
    }

    private static class Mapper implements RowMapper<SysNoticeSetting> {

        private Supplier<SysNoticeSetting> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysNoticeSetting mapRow(ResultSet rs, int rownum) throws SQLException {
            SysNoticeSetting bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setNoticeTitle(Utils.toCast(rs.getObject(F_NOTICE_TITLE), String.class));
            bean.setNoticeContent(Utils.toCast(rs.getObject(F_NOTICE_CONTENT), String.class));
            bean.setIsSuccess(Utils.toCast(rs.getObject(F_IS_SUCCESS), String.class));
            bean.setFkNoticeTypeId(Utils.toCast(rs.getObject(F_FK_NOTICE_TYPE_ID), String.class));
            bean.setNoticeTypeName(Utils.toCast(rs.getObject(F_NOTICE_TYPE_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysNoticeSetting> newMapper(){
        return newMapper(SysNoticeSetting::new);
    }

    public RowMapper<SysNoticeSetting> newMapper(Supplier<SysNoticeSetting> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysNoticeSetting {
        @Override
        public abstract RowMapper<SysNoticeSetting> newMapper();
    }
}
