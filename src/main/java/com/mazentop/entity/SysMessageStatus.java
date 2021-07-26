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
* Date:        14:26 2020/03/26
* Company:     美赞拓
* Version:     1.0
* Description: SysMessageStatus实体
*/
@SuppressWarnings("all")
public class SysMessageStatus extends BaseBean<SysMessageStatus> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_message_status";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 消息编号
    */
    public static final String F_FK_SYS_MESSAGE_ID = "fk_sys_message_id";
    /**
    * 客户编号
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 是否已读
    */
    public static final String F_IS_READ = "is_read";
    /**
    * 是否删除
    */
    public static final String F_IS_DELETE = "is_delete";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_SYS_MESSAGE_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_IS_READ, null);
        put(F_IS_DELETE, null);
    }

    public SysMessageStatus() {
        super();
    }

    public SysMessageStatus(Map<String, Object> map) {
        super(map);
    }

    public SysMessageStatus(String id) {
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
    public SysMessageStatus setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_sys_message_id to fkSysMessageId 消息编号<BR/>
    */
    public String getFkSysMessageId() {
        return getTypedValue(F_FK_SYS_MESSAGE_ID, String.class);
    }
    /**
    * @param fkSysMessageId to fk_sys_message_id 消息编号 set
    */
    public SysMessageStatus setFkSysMessageId(String fkSysMessageId) {
        set(F_FK_SYS_MESSAGE_ID, fkSysMessageId);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 客户编号<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 客户编号 set
    */
    public SysMessageStatus setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return is_read to isRead 是否已读<BR/>
    */
    public Integer getIsRead() {
        return getTypedValue(F_IS_READ, Integer.class);
    }
    /**
    * @param isRead to is_read 是否已读 set
    */
    public SysMessageStatus setIsRead(Integer isRead) {
        set(F_IS_READ, isRead);
        return this;
    }
    /**
    * @return is_delete to isDelete 是否删除<BR/>
    */
    public Integer getIsDelete() {
        return getTypedValue(F_IS_DELETE, Integer.class);
    }
    /**
    * @param isDelete to is_delete 是否删除 set
    */
    public SysMessageStatus setIsDelete(Integer isDelete) {
        set(F_IS_DELETE, isDelete);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysMessageStatus setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysMessageStatus me(){
        return new SysMessageStatus();
    }

    private static class Mapper implements RowMapper<SysMessageStatus> {

        private Supplier<SysMessageStatus> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysMessageStatus mapRow(ResultSet rs, int rownum) throws SQLException {
            SysMessageStatus bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkSysMessageId(Utils.toCast(rs.getObject(F_FK_SYS_MESSAGE_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setIsRead(Utils.toCast(rs.getObject(F_IS_READ), Integer.class));
            bean.setIsDelete(Utils.toCast(rs.getObject(F_IS_DELETE), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysMessageStatus> newMapper(){
        return newMapper(SysMessageStatus::new);
    }

    public RowMapper<SysMessageStatus> newMapper(Supplier<SysMessageStatus> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysMessageStatus {
        @Override
        public abstract RowMapper<SysMessageStatus> newMapper();
    }
}
