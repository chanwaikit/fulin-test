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
* Description: ActPromotionActivity实体
*/
@SuppressWarnings("all")
public class ActPromotionActivity extends BaseBean<ActPromotionActivity> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "act_promotion_activity";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 公司编号
     */
    public static final String F_COMPANY_ID = "company_id";
    /**
     * 公司名称
     */
    public static final String F_COMPANY_NAME = "company_name";
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
     * 是否限购
     */
    public static final String F_IS_RESTRICT_BUY = "is_restrict_buy";
    /**
     * 限购数据
     */
    public static final String F_RESTRICT_BUY_NUMBER = "restrict_buy_number";
    /**
     * 是否无门槛
     */
    public static final String F_IS_USE_RESTRICT = "is_use_restrict";
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
     * 优惠类型编号：折扣、抵扣
     */
    public static final String F_DISCOUNT_TYPE_ID = "discount_type_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_ACTIVITY_NAME, null);
        put(F_START_TIME, null);
        put(F_END_TIME, null);
        put(F_IS_RESTRICT_BUY, null);
        put(F_RESTRICT_BUY_NUMBER, null);
        put(F_IS_USE_RESTRICT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ACTIVITY_STATUS, null);
        put(F_DISCOUNT_TYPE_ID, null);
    }

    public ActPromotionActivity() {
        super();
    }

    public ActPromotionActivity(Map<String, Object> map) {
        super(map);
    }

    public ActPromotionActivity(String id) {
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
    public ActPromotionActivity setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return company_id to companyId 公司编号<BR/>
     */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
     * @param companyId to company_id 公司编号 set
     */
    public ActPromotionActivity setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
     * @return company_name to companyName 公司名称<BR/>
     */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
     * @param companyName to company_name 公司名称 set
     */
    public ActPromotionActivity setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
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
    public ActPromotionActivity setActivityName(String activityName) {
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
    public ActPromotionActivity setStartTime(Long startTime) {
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
    public ActPromotionActivity setEndTime(Long endTime) {
        set(F_END_TIME, endTime);
        return this;
    }
    /**
     * @return is_restrict_buy to isRestrictBuy 是否限购<BR/>
     */
    public Integer getIsRestrictBuy() {
        return getTypedValue(F_IS_RESTRICT_BUY, Integer.class);
    }
    /**
     * @param isRestrictBuy to is_restrict_buy 是否限购 set
     */
    public ActPromotionActivity setIsRestrictBuy(Integer isRestrictBuy) {
        set(F_IS_RESTRICT_BUY, isRestrictBuy);
        return this;
    }
    /**
     * @return restrict_buy_number to restrictBuyNumber 限购数据<BR/>
     */
    public Integer getRestrictBuyNumber() {
        return getTypedValue(F_RESTRICT_BUY_NUMBER, Integer.class);
    }
    /**
     * @param restrictBuyNumber to restrict_buy_number 限购数据 set
     */
    public ActPromotionActivity setRestrictBuyNumber(Integer restrictBuyNumber) {
        set(F_RESTRICT_BUY_NUMBER, restrictBuyNumber);
        return this;
    }
    /**
     * @return is_use_restrict to isUseRestrict 是否无门槛<BR/>
     */
    public Integer getIsUseRestrict() {
        return getTypedValue(F_IS_USE_RESTRICT, Integer.class);
    }
    /**
     * @param isUseRestrict to is_use_restrict 是否无门槛 set
     */
    public ActPromotionActivity setIsUseRestrict(Integer isUseRestrict) {
        set(F_IS_USE_RESTRICT, isUseRestrict);
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
    public ActPromotionActivity setRemark(String remark) {
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
    public ActPromotionActivity setAddTime(Long addTime) {
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
    public ActPromotionActivity setAddUserId(String addUserId) {
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
    public ActPromotionActivity setActivityStatus(String activityStatus) {
        set(F_ACTIVITY_STATUS, activityStatus);
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
    public ActPromotionActivity setDiscountTypeId(String discountTypeId) {
        set(F_DISCOUNT_TYPE_ID, discountTypeId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActPromotionActivity setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActPromotionActivity me(){
        return new ActPromotionActivity();
    }

    private static class Mapper implements RowMapper<ActPromotionActivity> {

        private Supplier<ActPromotionActivity> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActPromotionActivity mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActPromotionActivity bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setCompanyId(Utils.toCast(columnsName.contains(F_COMPANY_ID) ? rs.getObject(F_COMPANY_ID) : null, String.class));
            bean.setCompanyName(Utils.toCast(columnsName.contains(F_COMPANY_NAME) ? rs.getObject(F_COMPANY_NAME) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setStartTime(Utils.toCast(columnsName.contains(F_START_TIME) ? rs.getObject(F_START_TIME) : null, Long.class));
            bean.setEndTime(Utils.toCast(columnsName.contains(F_END_TIME) ? rs.getObject(F_END_TIME) : null, Long.class));
            bean.setIsRestrictBuy(Utils.toCast(columnsName.contains(F_IS_RESTRICT_BUY) ? rs.getObject(F_IS_RESTRICT_BUY) : null, Integer.class));
            bean.setRestrictBuyNumber(Utils.toCast(columnsName.contains(F_RESTRICT_BUY_NUMBER) ? rs.getObject(F_RESTRICT_BUY_NUMBER) : null, Integer.class));
            bean.setIsUseRestrict(Utils.toCast(columnsName.contains(F_IS_USE_RESTRICT) ? rs.getObject(F_IS_USE_RESTRICT) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setActivityStatus(Utils.toCast(columnsName.contains(F_ACTIVITY_STATUS) ? rs.getObject(F_ACTIVITY_STATUS) : null, String.class));
            bean.setDiscountTypeId(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_ID) ? rs.getObject(F_DISCOUNT_TYPE_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActPromotionActivity> newMapper(){
        return newMapper(ActPromotionActivity::new);
    }

    public RowMapper<ActPromotionActivity> newMapper(Supplier<ActPromotionActivity> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActPromotionActivity {
        @Override
        public abstract RowMapper<ActPromotionActivity> newMapper();
    }
}
