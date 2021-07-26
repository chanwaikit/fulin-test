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
* Date:        16:14 2020/03/19
* Company:     美赞拓
* Version:     1.0
* Description: ActCouponActivity实体
*/
@SuppressWarnings("all")
public class ActCouponActivity extends BaseBean<ActCouponActivity> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "act_coupon_activity";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 活动名称
     */
    public static final String F_ACTIVITY_NAME = "activity_name";
    /**
     * 开始时间
     */
    public static final String F_START_TIME = "start_time";
    /**
     * 结束时间
     */
    public static final String F_END_TIME = "end_time";
    /**
     * 获取方式编号
     */
    public static final String F_FK_COUPON_GET_TYPE_ID = "fk_coupon_get_type_id";
    /**
     * 获取方式名称
     */
    public static final String F_COUPON_GET_TYPE_NAME = "coupon_get_type_name";
    /**
     * 是否限制领取数据
     */
    public static final String F_IS_GET_RESTRICT = "is_get_restrict";
    /**
     * 限制数据
     */
    public static final String F_GET_RESTRICT_NUMBER = "get_restrict_number";
    /**
     * 是否限制使用次数
     */
    public static final String F_IS_USE_RESTRICT = "is_use_restrict";
    /**
     * 使用次数
     */
    public static final String F_USE_RESTRICT_NUMBER = "use_restrict_number";
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
     * 活动状态
     */
    public static final String F_ACTIVITY_STATUS = "activity_status";
    /**
     * 优惠码
     */
    public static final String F_COUPON_CODE = "coupon_code";
    /**
     * 优惠类型编号：折扣、抵扣
     */
    public static final String F_DISCOUNT_TYPE_ID = "discount_type_id";
    /**
     * 优惠是否限制
     */
    public static final String F_IS_LIMIT = "is_limit";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_ACTIVITY_NAME, null);
        put(F_START_TIME, null);
        put(F_END_TIME, null);
        put(F_FK_COUPON_GET_TYPE_ID, null);
        put(F_COUPON_GET_TYPE_NAME, null);
        put(F_IS_GET_RESTRICT, null);
        put(F_GET_RESTRICT_NUMBER, null);
        put(F_IS_USE_RESTRICT, null);
        put(F_USE_RESTRICT_NUMBER, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ACTIVITY_STATUS, null);
        put(F_COUPON_CODE, null);
        put(F_DISCOUNT_TYPE_ID, null);
        put(F_IS_LIMIT, null);
    }

    public ActCouponActivity() {
        super();
    }

    public ActCouponActivity(Map<String, Object> map) {
        super(map);
    }

    public ActCouponActivity(String id) {
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
    public ActCouponActivity setId(String id) {
        set(F_ID, id);
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
    public ActCouponActivity setActivityName(String activityName) {
        set(F_ACTIVITY_NAME, activityName);
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
    public ActCouponActivity setStartTime(Long startTime) {
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
    public ActCouponActivity setEndTime(Long endTime) {
        set(F_END_TIME, endTime);
        return this;
    }
    /**
     * @return fk_coupon_get_type_id to fkCouponGetTypeId 获取方式编号<BR/>
     */
    public String getFkCouponGetTypeId() {
        return getTypedValue(F_FK_COUPON_GET_TYPE_ID, String.class);
    }
    /**
     * @param fkCouponGetTypeId to fk_coupon_get_type_id 获取方式编号 set
     */
    public ActCouponActivity setFkCouponGetTypeId(String fkCouponGetTypeId) {
        set(F_FK_COUPON_GET_TYPE_ID, fkCouponGetTypeId);
        return this;
    }
    /**
     * @return coupon_get_type_name to couponGetTypeName 获取方式名称<BR/>
     */
    public String getCouponGetTypeName() {
        return getTypedValue(F_COUPON_GET_TYPE_NAME, String.class);
    }
    /**
     * @param couponGetTypeName to coupon_get_type_name 获取方式名称 set
     */
    public ActCouponActivity setCouponGetTypeName(String couponGetTypeName) {
        set(F_COUPON_GET_TYPE_NAME, couponGetTypeName);
        return this;
    }
    /**
     * @return is_get_restrict to isGetRestrict 是否限制领取数据<BR/>
     */
    public Integer getIsGetRestrict() {
        return getTypedValue(F_IS_GET_RESTRICT, Integer.class);
    }
    /**
     * @param isGetRestrict to is_get_restrict 是否限制领取数据 set
     */
    public ActCouponActivity setIsGetRestrict(Integer isGetRestrict) {
        set(F_IS_GET_RESTRICT, isGetRestrict);
        return this;
    }
    /**
     * @return get_restrict_number to getRestrictNumber 限制数据<BR/>
     */
    public Integer getGetRestrictNumber() {
        return getTypedValue(F_GET_RESTRICT_NUMBER, Integer.class);
    }
    /**
     * @param getRestrictNumber to get_restrict_number 限制数据 set
     */
    public ActCouponActivity setGetRestrictNumber(Integer getRestrictNumber) {
        set(F_GET_RESTRICT_NUMBER, getRestrictNumber);
        return this;
    }
    /**
     * @return is_use_restrict to isUseRestrict 是否限制使用次数<BR/>
     */
    public Integer getIsUseRestrict() {
        return getTypedValue(F_IS_USE_RESTRICT, Integer.class);
    }
    /**
     * @param isUseRestrict to is_use_restrict 是否限制使用次数 set
     */
    public ActCouponActivity setIsUseRestrict(Integer isUseRestrict) {
        set(F_IS_USE_RESTRICT, isUseRestrict);
        return this;
    }
    /**
     * @return use_restrict_number to useRestrictNumber 使用次数<BR/>
     */
    public Integer getUseRestrictNumber() {
        return getTypedValue(F_USE_RESTRICT_NUMBER, Integer.class);
    }
    /**
     * @param useRestrictNumber to use_restrict_number 使用次数 set
     */
    public ActCouponActivity setUseRestrictNumber(Integer useRestrictNumber) {
        set(F_USE_RESTRICT_NUMBER, useRestrictNumber);
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
    public ActCouponActivity setRemark(String remark) {
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
    public ActCouponActivity setAddTime(Long addTime) {
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
    public ActCouponActivity setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
     * @return activity_status to activityStatus 活动状态<BR/>
     */
    public String getActivityStatus() {
        return getTypedValue(F_ACTIVITY_STATUS, String.class);
    }
    /**
     * @param activityStatus to activity_status 活动状态 set
     */
    public ActCouponActivity setActivityStatus(String activityStatus) {
        set(F_ACTIVITY_STATUS, activityStatus);
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
    public ActCouponActivity setCouponCode(String couponCode) {
        set(F_COUPON_CODE, couponCode);
        return this;
    }
    /**
     * @return discount_type_id to discountTypeId 优惠类型编号：折扣、抵扣<BR/>
     */
    public String getDiscountTypeId() {
        return getTypedValue(F_DISCOUNT_TYPE_ID, String.class);
    }
    /**
     * @param discountTypeId to discount_type_id 优惠类型编号：折扣、抵扣 set
     */
    public ActCouponActivity setDiscountTypeId(String discountTypeId) {
        set(F_DISCOUNT_TYPE_ID, discountTypeId);
        return this;
    }
    /**
     * @return is_limit to isLimit 优惠是否限制<BR/>
     */
    public Integer getIsLimit() {
        return getTypedValue(F_IS_LIMIT, Integer.class);
    }
    /**
     * @param isLimit to is_limit 优惠是否限制 set
     */
    public ActCouponActivity setIsLimit(Integer isLimit) {
        set(F_IS_LIMIT, isLimit);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActCouponActivity setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActCouponActivity me(){
        return new ActCouponActivity();
    }

    private static class Mapper implements RowMapper<ActCouponActivity> {

        private Supplier<ActCouponActivity> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActCouponActivity mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActCouponActivity bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setStartTime(Utils.toCast(columnsName.contains(F_START_TIME) ? rs.getObject(F_START_TIME) : null, Long.class));
            bean.setEndTime(Utils.toCast(columnsName.contains(F_END_TIME) ? rs.getObject(F_END_TIME) : null, Long.class));
            bean.setFkCouponGetTypeId(Utils.toCast(columnsName.contains(F_FK_COUPON_GET_TYPE_ID) ? rs.getObject(F_FK_COUPON_GET_TYPE_ID) : null, String.class));
            bean.setCouponGetTypeName(Utils.toCast(columnsName.contains(F_COUPON_GET_TYPE_NAME) ? rs.getObject(F_COUPON_GET_TYPE_NAME) : null, String.class));
            bean.setIsGetRestrict(Utils.toCast(columnsName.contains(F_IS_GET_RESTRICT) ? rs.getObject(F_IS_GET_RESTRICT) : null, Integer.class));
            bean.setGetRestrictNumber(Utils.toCast(columnsName.contains(F_GET_RESTRICT_NUMBER) ? rs.getObject(F_GET_RESTRICT_NUMBER) : null, Integer.class));
            bean.setIsUseRestrict(Utils.toCast(columnsName.contains(F_IS_USE_RESTRICT) ? rs.getObject(F_IS_USE_RESTRICT) : null, Integer.class));
            bean.setUseRestrictNumber(Utils.toCast(columnsName.contains(F_USE_RESTRICT_NUMBER) ? rs.getObject(F_USE_RESTRICT_NUMBER) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setActivityStatus(Utils.toCast(columnsName.contains(F_ACTIVITY_STATUS) ? rs.getObject(F_ACTIVITY_STATUS) : null, String.class));
            bean.setCouponCode(Utils.toCast(columnsName.contains(F_COUPON_CODE) ? rs.getObject(F_COUPON_CODE) : null, String.class));
            bean.setDiscountTypeId(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_ID) ? rs.getObject(F_DISCOUNT_TYPE_ID) : null, String.class));
            bean.setIsLimit(Utils.toCast(columnsName.contains(F_IS_LIMIT) ? rs.getObject(F_IS_LIMIT) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActCouponActivity> newMapper(){
        return newMapper(ActCouponActivity::new);
    }

    public RowMapper<ActCouponActivity> newMapper(Supplier<ActCouponActivity> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActCouponActivity {
        @Override
        public abstract RowMapper<ActCouponActivity> newMapper();
    }
}
