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
* Date:        16:53 2020/03/17
* Company:     美赞拓
* Version:     1.0
* Description: ActActivityResultRecord实体
*/
@SuppressWarnings("all")
public class ActActivityResultRecord extends BaseBean<ActActivityResultRecord> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "act_activity_result_record";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 销售单编号
     */
    public static final String F_FK_SALES_ORDER_ID = "fk_sales_order_id";
    /**
     * 销售单号
     */
    public static final String F_SALES_ORDER_NO = "sales_order_no";
    /**
     * 客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 活动编号
     */
    public static final String F_FK_ACTIVITY_ID = "fk_activity_id";
    /**
     * 活动名称
     */
    public static final String F_ACTIVITY_NAME = "activity_name";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品名称
     */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
     * 优惠码
     */
    public static final String F_COUPON_CODE = "coupon_code";
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
        put(F_FK_SALES_ORDER_ID, null);
        put(F_SALES_ORDER_NO, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_FK_ACTIVITY_ID, null);
        put(F_ACTIVITY_NAME, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_COUPON_CODE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ActActivityResultRecord() {
        super();
    }

    public ActActivityResultRecord(Map<String, Object> map) {
        super(map);
    }

    public ActActivityResultRecord(String id) {
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
    public ActActivityResultRecord setId(String id) {
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
    public ActActivityResultRecord setFkSalesOrderId(String fkSalesOrderId) {
        set(F_FK_SALES_ORDER_ID, fkSalesOrderId);
        return this;
    }
    /**
     * @return sales_order_no to salesOrderNo 销售单号<BR/>
     */
    public String getSalesOrderNo() {
        return getTypedValue(F_SALES_ORDER_NO, String.class);
    }
    /**
     * @param salesOrderNo to sales_order_no 销售单号 set
     */
    public ActActivityResultRecord setSalesOrderNo(String salesOrderNo) {
        set(F_SALES_ORDER_NO, salesOrderNo);
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
    public ActActivityResultRecord setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_name to clientName 客户名称<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 客户名称 set
     */
    public ActActivityResultRecord setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return fk_activity_id to fkActivityId 活动编号<BR/>
     */
    public String getFkActivityId() {
        return getTypedValue(F_FK_ACTIVITY_ID, String.class);
    }
    /**
     * @param fkActivityId to fk_activity_id 活动编号 set
     */
    public ActActivityResultRecord setFkActivityId(String fkActivityId) {
        set(F_FK_ACTIVITY_ID, fkActivityId);
        return this;
    }
    /**
     * @return activity_name to activityName 活动名称<BR/>
     */
    public String getActivityName() {
        return getTypedValue(F_ACTIVITY_NAME, String.class);
    }
    /**
     * @param activityName to activity_name 活动名称 set
     */
    public ActActivityResultRecord setActivityName(String activityName) {
        set(F_ACTIVITY_NAME, activityName);
        return this;
    }
    /**
     * @return fk_product_id to fkProductId 商品编号<BR/>
     */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
     * @param fkProductId to fk_product_id 商品编号 set
     */
    public ActActivityResultRecord setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
     * @return product_name to productName 商品名称<BR/>
     */
    public String getProductName() {
        return getTypedValue(F_PRODUCT_NAME, String.class);
    }
    /**
     * @param productName to product_name 商品名称 set
     */
    public ActActivityResultRecord setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
     * @return coupon_code to couponCode 优惠码<BR/>
     */
    public String getCouponCode() {
        return getTypedValue(F_COUPON_CODE, String.class);
    }
    /**
     * @param couponCode to coupon_code 优惠码 set
     */
    public ActActivityResultRecord setCouponCode(String couponCode) {
        set(F_COUPON_CODE, couponCode);
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
    public ActActivityResultRecord setRemark(String remark) {
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
    public ActActivityResultRecord setAddTime(Long addTime) {
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
    public ActActivityResultRecord setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActActivityResultRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActActivityResultRecord me(){
        return new ActActivityResultRecord();
    }

    private static class Mapper implements RowMapper<ActActivityResultRecord> {

        private Supplier<ActActivityResultRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActActivityResultRecord mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActActivityResultRecord bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkSalesOrderId(Utils.toCast(columnsName.contains(F_FK_SALES_ORDER_ID) ? rs.getObject(F_FK_SALES_ORDER_ID) : null, String.class));
            bean.setSalesOrderNo(Utils.toCast(columnsName.contains(F_SALES_ORDER_NO) ? rs.getObject(F_SALES_ORDER_NO) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setFkActivityId(Utils.toCast(columnsName.contains(F_FK_ACTIVITY_ID) ? rs.getObject(F_FK_ACTIVITY_ID) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setCouponCode(Utils.toCast(columnsName.contains(F_COUPON_CODE) ? rs.getObject(F_COUPON_CODE) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActActivityResultRecord> newMapper(){
        return newMapper(ActActivityResultRecord::new);
    }

    public RowMapper<ActActivityResultRecord> newMapper(Supplier<ActActivityResultRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActActivityResultRecord {
        @Override
        public abstract RowMapper<ActActivityResultRecord> newMapper();
    }
}
