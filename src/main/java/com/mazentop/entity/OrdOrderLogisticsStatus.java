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
* Date:        16:27 2020/04/10
* Company:     美赞拓
* Version:     1.0
* Description: OrdOrderLogisticsStatus实体
*/
@SuppressWarnings("all")
public class OrdOrderLogisticsStatus extends BaseBean<OrdOrderLogisticsStatus> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_order_logistics_status";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 订单编号
    */
    public static final String F_FK_SALES_ORDER_ID = "fk_sales_order_id";
    /**
    * 订单状态
    */
    public static final String F_SALES_ORDER_STATUS = "sales_order_status";
    /**
    * 改变时间
    */
    public static final String F_CHANGE_TIME = "change_time";
    /**
    * 物流渠道名称
    */
    public static final String F_TRANSPORTS_CHANNEL_NAME = "transports_channel_name";
    /**
    * 物流渠道编号
    */
    public static final String F_TRANSPORTS_CHANNEL_ID = "transports_channel_id";
    /**
    * 快递单号
    */
    public static final String F_TRANSPORTS_NO = "transports_no";
    /**
    * 是否已签收
    */
    public static final String F_IS_SIGN = "is_sign";
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
        put(F_CHANGE_TIME, null);
        put(F_TRANSPORTS_CHANNEL_NAME, null);
        put(F_TRANSPORTS_CHANNEL_ID, null);
        put(F_TRANSPORTS_NO, null);
        put(F_IS_SIGN, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public OrdOrderLogisticsStatus() {
        super();
    }

    public OrdOrderLogisticsStatus(Map<String, Object> map) {
        super(map);
    }

    public OrdOrderLogisticsStatus(String id) {
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
    public OrdOrderLogisticsStatus setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_sales_order_id to fkSalesOrderId 订单编号<BR/>
    */
    public String getFkSalesOrderId() {
        return getTypedValue(F_FK_SALES_ORDER_ID, String.class);
    }
    /**
    * @param fkSalesOrderId to fk_sales_order_id 订单编号 set
    */
    public OrdOrderLogisticsStatus setFkSalesOrderId(String fkSalesOrderId) {
        set(F_FK_SALES_ORDER_ID, fkSalesOrderId);
        return this;
    }
    /**
    * @return sales_order_status to salesOrderStatus 订单状态<BR/>
    */
    public String getSalesOrderStatus() {
        return getTypedValue(F_SALES_ORDER_STATUS, String.class);
    }
    /**
    * @param salesOrderStatus to sales_order_status 订单状态 set
    */
    public OrdOrderLogisticsStatus setSalesOrderStatus(String salesOrderStatus) {
        set(F_SALES_ORDER_STATUS, salesOrderStatus);
        return this;
    }
    /**
    * @return change_time to changeTime 改变时间<BR/>
    */
    public Long getChangeTime() {
        return getTypedValue(F_CHANGE_TIME, Long.class);
    }
    /**
    * @param changeTime to change_time 改变时间 set
    */
    public OrdOrderLogisticsStatus setChangeTime(Long changeTime) {
        set(F_CHANGE_TIME, changeTime);
        return this;
    }
    /**
    * @return transports_channel_name to transportsChannelName 物流渠道名称<BR/>
    */
    public String getTransportsChannelName() {
        return getTypedValue(F_TRANSPORTS_CHANNEL_NAME, String.class);
    }
    /**
    * @param transportsChannelName to transports_channel_name 物流渠道名称 set
    */
    public OrdOrderLogisticsStatus setTransportsChannelName(String transportsChannelName) {
        set(F_TRANSPORTS_CHANNEL_NAME, transportsChannelName);
        return this;
    }
    /**
    * @return transports_channel_id to transportsChannelId 物流渠道编号<BR/>
    */
    public String getTransportsChannelId() {
        return getTypedValue(F_TRANSPORTS_CHANNEL_ID, String.class);
    }
    /**
    * @param transportsChannelId to transports_channel_id 物流渠道编号 set
    */
    public OrdOrderLogisticsStatus setTransportsChannelId(String transportsChannelId) {
        set(F_TRANSPORTS_CHANNEL_ID, transportsChannelId);
        return this;
    }
    /**
    * @return transports_no to transportsNo 快递单号<BR/>
    */
    public String getTransportsNo() {
        return getTypedValue(F_TRANSPORTS_NO, String.class);
    }
    /**
    * @param transportsNo to transports_no 快递单号 set
    */
    public OrdOrderLogisticsStatus setTransportsNo(String transportsNo) {
        set(F_TRANSPORTS_NO, transportsNo);
        return this;
    }
    /**
    * @return is_sign to isSign 是否已签收<BR/>
    */
    public Integer getIsSign() {
        return getTypedValue(F_IS_SIGN, Integer.class);
    }
    /**
    * @param isSign to is_sign 是否已签收 set
    */
    public OrdOrderLogisticsStatus setIsSign(Integer isSign) {
        set(F_IS_SIGN, isSign);
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
    public OrdOrderLogisticsStatus setRemark(String remark) {
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
    public OrdOrderLogisticsStatus setAddTime(Long addTime) {
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
    public OrdOrderLogisticsStatus setAddUserId(String addUserId) {
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
    public OrdOrderLogisticsStatus setAddUserName(String addUserName) {
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
    public OrdOrderLogisticsStatus setOperationTime(Long operationTime) {
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
    public OrdOrderLogisticsStatus setOperationUserId(String operationUserId) {
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
    public OrdOrderLogisticsStatus setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdOrderLogisticsStatus setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdOrderLogisticsStatus me(){
        return new OrdOrderLogisticsStatus();
    }

    private static class Mapper implements RowMapper<OrdOrderLogisticsStatus> {

        private Supplier<OrdOrderLogisticsStatus> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdOrderLogisticsStatus mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdOrderLogisticsStatus bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkSalesOrderId(Utils.toCast(rs.getObject(F_FK_SALES_ORDER_ID), String.class));
            bean.setSalesOrderStatus(Utils.toCast(rs.getObject(F_SALES_ORDER_STATUS), String.class));
            bean.setChangeTime(Utils.toCast(rs.getObject(F_CHANGE_TIME), Long.class));
            bean.setTransportsChannelName(Utils.toCast(rs.getObject(F_TRANSPORTS_CHANNEL_NAME), String.class));
            bean.setTransportsChannelId(Utils.toCast(rs.getObject(F_TRANSPORTS_CHANNEL_ID), String.class));
            bean.setTransportsNo(Utils.toCast(rs.getObject(F_TRANSPORTS_NO), String.class));
            bean.setIsSign(Utils.toCast(rs.getObject(F_IS_SIGN), Integer.class));
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
    public RowMapper<OrdOrderLogisticsStatus> newMapper(){
        return newMapper(OrdOrderLogisticsStatus::new);
    }

    public RowMapper<OrdOrderLogisticsStatus> newMapper(Supplier<OrdOrderLogisticsStatus> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdOrderLogisticsStatus {
        @Override
        public abstract RowMapper<OrdOrderLogisticsStatus> newMapper();
    }
}
