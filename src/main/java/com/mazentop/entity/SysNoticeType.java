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
* Description: SysNoticeType实体
*/
@SuppressWarnings("all")
public class SysNoticeType extends BaseBean<SysNoticeType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_notice_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
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
        put(F_NOTICE_TYPE_NAME, null);
        put(F_REMARK, null);
    }

    public SysNoticeType() {
        super();
    }

    public SysNoticeType(Map<String, Object> map) {
        super(map);
    }

    public SysNoticeType(String id) {
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
    public SysNoticeType setId(String id) {
        set(F_ID, id);
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
    public SysNoticeType setNoticeTypeName(String noticeTypeName) {
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
    public SysNoticeType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysNoticeType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysNoticeType me(){
        return new SysNoticeType();
    }

    private static class Mapper implements RowMapper<SysNoticeType> {

        private Supplier<SysNoticeType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysNoticeType mapRow(ResultSet rs, int rownum) throws SQLException {
            SysNoticeType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setNoticeTypeName(Utils.toCast(rs.getObject(F_NOTICE_TYPE_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysNoticeType> newMapper(){
        return newMapper(SysNoticeType::new);
    }

    public RowMapper<SysNoticeType> newMapper(Supplier<SysNoticeType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysNoticeType {
        @Override
        public abstract RowMapper<SysNoticeType> newMapper();
    }
}
