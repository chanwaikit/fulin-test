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
* Description: ActConditionType实体
*/
@SuppressWarnings("all")
public class ActConditionType extends BaseBean<ActConditionType> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "act_condition_type";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 活动编号
     */
    public static final String F_FK_ACTIVITY_ID = "fk_activity_id";
    /**
     * 活动名称
     */
    public static final String F_ACTIVITY_NAME = "activity_name";
    /**
     * 使用条件
     */
    public static final String F_TYPE_CONDITION = "type_condition";
    /**
     * 优惠类型编号：折扣、抵扣
     */
    public static final String F_DISCOUNT_TYPE_ID = "discount_type_id";
    /**
     * 优惠类型名称：折扣、抵扣
     */
    public static final String F_DISCOUNT_TYPE_NAME = "discount_type_name";
    /**
     * 优惠数据
     */
    public static final String F_DISCOUNT_VALUE = "discount_value";
    /**
     * 是否无门槛
     */
    public static final String F_IS_NOT_THRESHOLD = "is_not_threshold";
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
        put(F_FK_ACTIVITY_ID, null);
        put(F_ACTIVITY_NAME, null);
        put(F_TYPE_CONDITION, null);
        put(F_DISCOUNT_TYPE_ID, null);
        put(F_DISCOUNT_TYPE_NAME, null);
        put(F_DISCOUNT_VALUE, null);
        put(F_IS_NOT_THRESHOLD, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ActConditionType() {
        super();
    }

    public ActConditionType(Map<String, Object> map) {
        super(map);
    }

    public ActConditionType(String id) {
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
    public ActConditionType setId(String id) {
        set(F_ID, id);
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
    public ActConditionType setFkActivityId(String fkActivityId) {
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
    public ActConditionType setActivityName(String activityName) {
        set(F_ACTIVITY_NAME, activityName);
        return this;
    }
    /**
     * @return type_condition to typeCondition 使用条件<BR/>
     */
    public Long getTypeCondition() {
        return getTypedValue(F_TYPE_CONDITION, Long.class);
    }
    /**
     * @param typeCondition to type_condition 使用条件 set
     */
    public ActConditionType setTypeCondition(Long typeCondition) {
        set(F_TYPE_CONDITION, typeCondition);
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
    public ActConditionType setDiscountTypeId(String discountTypeId) {
        set(F_DISCOUNT_TYPE_ID, discountTypeId);
        return this;
    }
    /**
     * @return discount_type_name to discountTypeName 优惠类型名称：折扣、抵扣<BR/>
     */
    public String getDiscountTypeName() {
        return getTypedValue(F_DISCOUNT_TYPE_NAME, String.class);
    }
    /**
     * @param discountTypeName to discount_type_name 优惠类型名称：折扣、抵扣 set
     */
    public ActConditionType setDiscountTypeName(String discountTypeName) {
        set(F_DISCOUNT_TYPE_NAME, discountTypeName);
        return this;
    }
    /**
     * @return discount_value to discountValue 优惠数据<BR/>
     */
    public Long getDiscountValue() {
        return getTypedValue(F_DISCOUNT_VALUE, Long.class);
    }
    /**
     * @param discountValue to discount_value 优惠数据 set
     */
    public ActConditionType setDiscountValue(Long discountValue) {
        set(F_DISCOUNT_VALUE, discountValue);
        return this;
    }
    /**
     * @return is_not_threshold to isNotThreshold 是否无门槛<BR/>
     */
    public Integer getIsNotThreshold() {
        return getTypedValue(F_IS_NOT_THRESHOLD, Integer.class);
    }
    /**
     * @param isNotThreshold to is_not_threshold 是否无门槛 set
     */
    public ActConditionType setIsNotThreshold(Integer isNotThreshold) {
        set(F_IS_NOT_THRESHOLD, isNotThreshold);
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
    public ActConditionType setRemark(String remark) {
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
    public ActConditionType setAddTime(Long addTime) {
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
    public ActConditionType setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActConditionType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActConditionType me(){
        return new ActConditionType();
    }

    private static class Mapper implements RowMapper<ActConditionType> {

        private Supplier<ActConditionType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActConditionType mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActConditionType bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkActivityId(Utils.toCast(columnsName.contains(F_FK_ACTIVITY_ID) ? rs.getObject(F_FK_ACTIVITY_ID) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setTypeCondition(Utils.toCast(columnsName.contains(F_TYPE_CONDITION) ? rs.getObject(F_TYPE_CONDITION) : null, Long.class));
            bean.setDiscountTypeId(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_ID) ? rs.getObject(F_DISCOUNT_TYPE_ID) : null, String.class));
            bean.setDiscountTypeName(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_NAME) ? rs.getObject(F_DISCOUNT_TYPE_NAME) : null, String.class));
            bean.setDiscountValue(Utils.toCast(columnsName.contains(F_DISCOUNT_VALUE) ? rs.getObject(F_DISCOUNT_VALUE) : null, Long.class));
            bean.setIsNotThreshold(Utils.toCast(columnsName.contains(F_IS_NOT_THRESHOLD) ? rs.getObject(F_IS_NOT_THRESHOLD) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActConditionType> newMapper(){
        return newMapper(ActConditionType::new);
    }

    public RowMapper<ActConditionType> newMapper(Supplier<ActConditionType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActConditionType {
        @Override
        public abstract RowMapper<ActConditionType> newMapper();
    }
}
