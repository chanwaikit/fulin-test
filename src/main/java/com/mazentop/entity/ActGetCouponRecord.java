package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        15:21 2020/03/31
* Company:     美赞拓
* Version:     1.0
* Description: ActGetCouponRecord实体
*/
@SuppressWarnings("all")
public class ActGetCouponRecord extends BaseBean<ActGetCouponRecord> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "act_get_coupon_record";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 优惠卷活动编号
     */
    public static final String F_FK_ACTIVITY_ID = "fk_activity_id";
    /**
     * 优惠卷活动名称
     */
    public static final String F_ACTIVITY_NAME = "activity_name";
    /**
     * 使用规则编号
     */
    public static final String F_FK_CONDITION_TYPE_ID = "fk_condition_type_id";
    /**
     * 优惠卷码
     */
    public static final String F_COUPON_CODE = "coupon_code";
    /**
     * 优惠卷获取方式编号
     */
    public static final String F_FK_COUPON_GET_TYPE_ID = "fk_coupon_get_type_id";
    /**
     * 优惠卷获取方式名称
     */
    public static final String F_COUPON_GET_TYPE_NAME = "coupon_get_type_name";
    /**
     * 开始时间
     */
    public static final String F_START_TIME = "start_time";
    /**
     * 结束时间
     */
    public static final String F_END_TIME = "end_time";
    /**
     * 是否已使用
     */
    public static final String F_IS_USE = "is_use";
    /**
     * 消息时间
     */
    public static final String F_USE_TIME = "use_time";
    /**
     * 消息结算单号
     */
    public static final String F_USE_BALANCE_THE_BOOKS_NO = "use_balance_the_books_no";
    /**
     * 优惠类型名称(折扣、抵扣)
     */
    public static final String F_COUPON_DISCOUNT_TYPE_NAME = "coupon_discount_type_name";
    /**
     * 优惠数值
     */
    public static final String F_COUPON_DISCOUNT_VALUE = "coupon_discount_value";
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
     * 客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_ACTIVITY_ID, null);
        put(F_ACTIVITY_NAME, null);
        put(F_FK_CONDITION_TYPE_ID, null);
        put(F_COUPON_CODE, null);
        put(F_FK_COUPON_GET_TYPE_ID, null);
        put(F_COUPON_GET_TYPE_NAME, null);
        put(F_START_TIME, null);
        put(F_END_TIME, null);
        put(F_IS_USE, null);
        put(F_USE_TIME, null);
        put(F_USE_BALANCE_THE_BOOKS_NO, null);
        put(F_COUPON_DISCOUNT_TYPE_NAME, null);
        put(F_COUPON_DISCOUNT_VALUE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
    }

    public ActGetCouponRecord() {
        super();
    }

    public ActGetCouponRecord(Map<String, Object> map) {
        super(map);
    }

    public ActGetCouponRecord(String id) {
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
    public ActGetCouponRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_activity_id to fkActivityId 优惠卷活动编号<BR/>
     */
    public String getFkActivityId() {
        return getTypedValue(F_FK_ACTIVITY_ID, String.class);
    }
    /**
     * @param fkActivityId to fk_activity_id 优惠卷活动编号 set
     */
    public ActGetCouponRecord setFkActivityId(String fkActivityId) {
        set(F_FK_ACTIVITY_ID, fkActivityId);
        return this;
    }
    /**
     * @return activity_name to activityName 优惠卷活动名称<BR/>
     */
    public String getActivityName() {
        return getTypedValue(F_ACTIVITY_NAME, String.class);
    }
    /**
     * @param activityName to activity_name 优惠卷活动名称 set
     */
    public ActGetCouponRecord setActivityName(String activityName) {
        set(F_ACTIVITY_NAME, activityName);
        return this;
    }
    /**
     * @return fk_condition_type_id to fkConditionTypeId 使用规则编号<BR/>
     */
    public String getFkConditionTypeId() {
        return getTypedValue(F_FK_CONDITION_TYPE_ID, String.class);
    }
    /**
     * @param fkConditionTypeId to fk_condition_type_id 使用规则编号 set
     */
    public ActGetCouponRecord setFkConditionTypeId(String fkConditionTypeId) {
        set(F_FK_CONDITION_TYPE_ID, fkConditionTypeId);
        return this;
    }
    /**
     * @return coupon_code to couponCode 优惠卷码<BR/>
     */
    public String getCouponCode() {
        return getTypedValue(F_COUPON_CODE, String.class);
    }
    /**
     * @param couponCode to coupon_code 优惠卷码 set
     */
    public ActGetCouponRecord setCouponCode(String couponCode) {
        set(F_COUPON_CODE, couponCode);
        return this;
    }
    /**
     * @return fk_coupon_get_type_id to fkCouponGetTypeId 优惠卷获取方式编号<BR/>
     */
    public String getFkCouponGetTypeId() {
        return getTypedValue(F_FK_COUPON_GET_TYPE_ID, String.class);
    }
    /**
     * @param fkCouponGetTypeId to fk_coupon_get_type_id 优惠卷获取方式编号 set
     */
    public ActGetCouponRecord setFkCouponGetTypeId(String fkCouponGetTypeId) {
        set(F_FK_COUPON_GET_TYPE_ID, fkCouponGetTypeId);
        return this;
    }
    /**
     * @return coupon_get_type_name to couponGetTypeName 优惠卷获取方式名称<BR/>
     */
    public String getCouponGetTypeName() {
        return getTypedValue(F_COUPON_GET_TYPE_NAME, String.class);
    }
    /**
     * @param couponGetTypeName to coupon_get_type_name 优惠卷获取方式名称 set
     */
    public ActGetCouponRecord setCouponGetTypeName(String couponGetTypeName) {
        set(F_COUPON_GET_TYPE_NAME, couponGetTypeName);
        return this;
    }
    /**
     * @return start_time to startTime 开始时间<BR/>
     */
    public Long getStartTime() {
        return getTypedValue(F_START_TIME, Long.class);
    }
    /**
     * @param startTime to start_time 开始时间 set
     */
    public ActGetCouponRecord setStartTime(Long startTime) {
        set(F_START_TIME, startTime);
        return this;
    }
    /**
     * @return end_time to endTime 结束时间<BR/>
     */
    public Long getEndTime() {
        return getTypedValue(F_END_TIME, Long.class);
    }
    /**
     * @param endTime to end_time 结束时间 set
     */
    public ActGetCouponRecord setEndTime(Long endTime) {
        set(F_END_TIME, endTime);
        return this;
    }
    /**
     * @return is_use to isUse 是否已使用<BR/>
     */
    public Integer getIsUse() {
        return getTypedValue(F_IS_USE, Integer.class);
    }
    /**
     * @param isUse to is_use 是否已使用 set
     */
    public ActGetCouponRecord setIsUse(Integer isUse) {
        set(F_IS_USE, isUse);
        return this;
    }
    /**
     * @return use_time to useTime 消息时间<BR/>
     */
    public Long getUseTime() {
        return getTypedValue(F_USE_TIME, Long.class);
    }
    /**
     * @param useTime to use_time 消息时间 set
     */
    public ActGetCouponRecord setUseTime(Long useTime) {
        set(F_USE_TIME, useTime);
        return this;
    }
    /**
     * @return use_balance_the_books_no to useBalanceTheBooksNo 消息结算单号<BR/>
     */
    public String getUseBalanceTheBooksNo() {
        return getTypedValue(F_USE_BALANCE_THE_BOOKS_NO, String.class);
    }
    /**
     * @param useBalanceTheBooksNo to use_balance_the_books_no 消息结算单号 set
     */
    public ActGetCouponRecord setUseBalanceTheBooksNo(String useBalanceTheBooksNo) {
        set(F_USE_BALANCE_THE_BOOKS_NO, useBalanceTheBooksNo);
        return this;
    }
    /**
     * @return coupon_discount_type_name to couponDiscountTypeName 优惠类型名称(折扣、抵扣)<BR/>
     */
    public String getCouponDiscountTypeName() {
        return getTypedValue(F_COUPON_DISCOUNT_TYPE_NAME, String.class);
    }
    /**
     * @param couponDiscountTypeName to coupon_discount_type_name 优惠类型名称(折扣、抵扣) set
     */
    public ActGetCouponRecord setCouponDiscountTypeName(String couponDiscountTypeName) {
        set(F_COUPON_DISCOUNT_TYPE_NAME, couponDiscountTypeName);
        return this;
    }
    /**
     * @return coupon_discount_value to couponDiscountValue 优惠数值<BR/>
     */
    public Long getCouponDiscountValue() {
        return getTypedValue(F_COUPON_DISCOUNT_VALUE, Long.class);
    }
    /**
     * @param couponDiscountValue to coupon_discount_value 优惠数值 set
     */
    public ActGetCouponRecord setCouponDiscountValue(Long couponDiscountValue) {
        set(F_COUPON_DISCOUNT_VALUE, couponDiscountValue);
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
    public ActGetCouponRecord setRemark(String remark) {
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
    public ActGetCouponRecord setAddTime(Long addTime) {
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
    public ActGetCouponRecord setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public ActGetCouponRecord setFkClienteleId(String fkClienteleId) {
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
    public ActGetCouponRecord setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActGetCouponRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActGetCouponRecord me(){
        return new ActGetCouponRecord();
    }

    private static class Mapper implements RowMapper<ActGetCouponRecord> {

        private Supplier<ActGetCouponRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActGetCouponRecord mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActGetCouponRecord bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkActivityId(Utils.toCast(columnsName.contains(F_FK_ACTIVITY_ID) ? rs.getObject(F_FK_ACTIVITY_ID) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setFkConditionTypeId(Utils.toCast(columnsName.contains(F_FK_CONDITION_TYPE_ID) ? rs.getObject(F_FK_CONDITION_TYPE_ID) : null, String.class));
            bean.setCouponCode(Utils.toCast(columnsName.contains(F_COUPON_CODE) ? rs.getObject(F_COUPON_CODE) : null, String.class));
            bean.setFkCouponGetTypeId(Utils.toCast(columnsName.contains(F_FK_COUPON_GET_TYPE_ID) ? rs.getObject(F_FK_COUPON_GET_TYPE_ID) : null, String.class));
            bean.setCouponGetTypeName(Utils.toCast(columnsName.contains(F_COUPON_GET_TYPE_NAME) ? rs.getObject(F_COUPON_GET_TYPE_NAME) : null, String.class));
            bean.setStartTime(Utils.toCast(columnsName.contains(F_START_TIME) ? rs.getObject(F_START_TIME) : null, Long.class));
            bean.setEndTime(Utils.toCast(columnsName.contains(F_END_TIME) ? rs.getObject(F_END_TIME) : null, Long.class));
            bean.setIsUse(Utils.toCast(columnsName.contains(F_IS_USE) ? rs.getObject(F_IS_USE) : null, Integer.class));
            bean.setUseTime(Utils.toCast(columnsName.contains(F_USE_TIME) ? rs.getObject(F_USE_TIME) : null, Long.class));
            bean.setUseBalanceTheBooksNo(Utils.toCast(columnsName.contains(F_USE_BALANCE_THE_BOOKS_NO) ? rs.getObject(F_USE_BALANCE_THE_BOOKS_NO) : null, String.class));
            bean.setCouponDiscountTypeName(Utils.toCast(columnsName.contains(F_COUPON_DISCOUNT_TYPE_NAME) ? rs.getObject(F_COUPON_DISCOUNT_TYPE_NAME) : null, String.class));
            bean.setCouponDiscountValue(Utils.toCast(columnsName.contains(F_COUPON_DISCOUNT_VALUE) ? rs.getObject(F_COUPON_DISCOUNT_VALUE) : null, Long.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActGetCouponRecord> newMapper(){
        return newMapper(ActGetCouponRecord::new);
    }

    public RowMapper<ActGetCouponRecord> newMapper(Supplier<ActGetCouponRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActGetCouponRecord {
        @Override
        public abstract RowMapper<ActGetCouponRecord> newMapper();
    }
}
