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
* Date:        15:11 2020/03/14
* Company:     美赞拓
* Version:     1.0
* Description: OrdSalesOrderOperateRecord实体
*/
@SuppressWarnings("all")
public class OrdSalesOrderOperateRecord extends BaseBean<OrdSalesOrderOperateRecord> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_sales_order_operate_record";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 销售单编号
    */
    public static final String F_FK_SALES_ORDER_ID = "fk_sales_order_id";
    /**
    * 销售单状态
    */
    public static final String F_SALES_ORDER_STATUS = "sales_order_status";
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

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_SALES_ORDER_ID, null);
        put(F_SALES_ORDER_STATUS, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public OrdSalesOrderOperateRecord() {
        super();
    }

    public OrdSalesOrderOperateRecord(Map<String, Object> map) {
        super(map);
    }

    public OrdSalesOrderOperateRecord(String id) {
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
    public OrdSalesOrderOperateRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_sales_order_id to fkSalesOrderId 销售单编号<BR/>
    */
    public String getFkSalesOrderId() {
        return getTypedValue(F_FK_SALES_ORDER_ID, String.class);
    }
    /**
    * @param fkSalesOrderId to fk_sales_order_id 销售单编号 set
    */
    public OrdSalesOrderOperateRecord setFkSalesOrderId(String fkSalesOrderId) {
        set(F_FK_SALES_ORDER_ID, fkSalesOrderId);
        return this;
    }
    /**
    * @return sales_order_status to salesOrderStatus 销售单状态<BR/>
    */
    public String getSalesOrderStatus() {
        return getTypedValue(F_SALES_ORDER_STATUS, String.class);
    }
    /**
    * @param salesOrderStatus to sales_order_status 销售单状态 set
    */
    public OrdSalesOrderOperateRecord setSalesOrderStatus(String salesOrderStatus) {
        set(F_SALES_ORDER_STATUS, salesOrderStatus);
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
    public OrdSalesOrderOperateRecord setRemark(String remark) {
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
    public OrdSalesOrderOperateRecord setAddTime(Long addTime) {
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
    public OrdSalesOrderOperateRecord setAddUserId(String addUserId) {
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
    public OrdSalesOrderOperateRecord setAddUserName(String addUserName) {
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
    public OrdSalesOrderOperateRecord setOperationTime(Long operationTime) {
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
    public OrdSalesOrderOperateRecord setOperationUserId(String operationUserId) {
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
    public OrdSalesOrderOperateRecord setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdSalesOrderOperateRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdSalesOrderOperateRecord me(){
        return new OrdSalesOrderOperateRecord();
    }

    private static class Mapper implements RowMapper<OrdSalesOrderOperateRecord> {

        private Supplier<OrdSalesOrderOperateRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdSalesOrderOperateRecord mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdSalesOrderOperateRecord bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkSalesOrderId(Utils.toCast(rs.getObject(F_FK_SALES_ORDER_ID), String.class));
            bean.setSalesOrderStatus(Utils.toCast(rs.getObject(F_SALES_ORDER_STATUS), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdSalesOrderOperateRecord> newMapper(){
        return newMapper(OrdSalesOrderOperateRecord::new);
    }

    public RowMapper<OrdSalesOrderOperateRecord> newMapper(Supplier<OrdSalesOrderOperateRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdSalesOrderOperateRecord {
        @Override
        public abstract RowMapper<OrdSalesOrderOperateRecord> newMapper();
    }
}
