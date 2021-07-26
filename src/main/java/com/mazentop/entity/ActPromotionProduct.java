package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import java.util.function.Supplier;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:22 2020/11/05
* Version:     1.0
* Description: ActPromotionProduct实体
*/
@SuppressWarnings("all")
public class ActPromotionProduct extends BaseBean<ActPromotionProduct> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "act_promotion_product";

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
    * 商品编号
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 优惠类型编号：折扣、抵扣
    */
    public static final String F_DISCOUNT_TYPE_ID = "discount_type_id";
    /**
    * 优惠类型名称(折扣、抵扣)
    */
    public static final String F_DISCOUNT_TYPE_NAME = "discount_type_name";
    /**
    * 优惠数据
    */
    public static final String F_DISCOUNT_VALUE = "discount_value";
    /**
    * 限卖上限数量
    */
    public static final String F_PRODUCT_CEIL_NUMBER = "product_ceil_number";
    /**
    * 商品默认数量
    */
    public static final String F_PRODUCT_DEFAULT_NUMBER = "product_default_number";
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
        put(F_FK_ACTIVITY_ID, null);
        put(F_ACTIVITY_NAME, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_DISCOUNT_TYPE_ID, null);
        put(F_DISCOUNT_TYPE_NAME, null);
        put(F_DISCOUNT_VALUE, null);
        put(F_PRODUCT_CEIL_NUMBER, null);
        put(F_PRODUCT_DEFAULT_NUMBER, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public ActPromotionProduct() {
        super();
    }

    public ActPromotionProduct(Map<String, Object> map) {
        super(map);
    }

    public ActPromotionProduct(String id) {
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
    public ActPromotionProduct setId(String id) {
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
    public ActPromotionProduct setFkActivityId(String fkActivityId) {
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
    public ActPromotionProduct setActivityName(String activityName) {
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
    public ActPromotionProduct setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
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
    public ActPromotionProduct setDiscountTypeId(String discountTypeId) {
        set(F_DISCOUNT_TYPE_ID, discountTypeId);
        return this;
    }
    /**
    * @return discount_type_name to discountTypeName 优惠类型名称(折扣、抵扣)<BR/>
    */
    public String getDiscountTypeName() {
        return getTypedValue(F_DISCOUNT_TYPE_NAME, String.class);
    }
    /**
    * @param discountTypeName to discount_type_name 优惠类型名称(折扣、抵扣) set
    */
    public ActPromotionProduct setDiscountTypeName(String discountTypeName) {
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
    public ActPromotionProduct setDiscountValue(Long discountValue) {
        set(F_DISCOUNT_VALUE, discountValue);
        return this;
    }
    /**
    * @return product_ceil_number to productCeilNumber 限卖上限数量<BR/>
    */
    public Integer getProductCeilNumber() {
        return getTypedValue(F_PRODUCT_CEIL_NUMBER, Integer.class);
    }
    /**
    * @param productCeilNumber to product_ceil_number 限卖上限数量 set
    */
    public ActPromotionProduct setProductCeilNumber(Integer productCeilNumber) {
        set(F_PRODUCT_CEIL_NUMBER, productCeilNumber);
        return this;
    }
    /**
    * @return product_default_number to productDefaultNumber 商品默认数量<BR/>
    */
    public Integer getProductDefaultNumber() {
        return getTypedValue(F_PRODUCT_DEFAULT_NUMBER, Integer.class);
    }
    /**
    * @param productDefaultNumber to product_default_number 商品默认数量 set
    */
    public ActPromotionProduct setProductDefaultNumber(Integer productDefaultNumber) {
        set(F_PRODUCT_DEFAULT_NUMBER, productDefaultNumber);
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
    public ActPromotionProduct setRemark(String remark) {
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
    public ActPromotionProduct setAddTime(Long addTime) {
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
    public ActPromotionProduct setAddUserId(String addUserId) {
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
    public ActPromotionProduct setAddUserName(String addUserName) {
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
    public ActPromotionProduct setOperationTime(Long operationTime) {
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
    public ActPromotionProduct setOperationUserId(String operationUserId) {
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
    public ActPromotionProduct setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActPromotionProduct setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActPromotionProduct me(){
        return new ActPromotionProduct();
    }

    private static class Mapper implements RowMapper<ActPromotionProduct> {

        private Supplier<ActPromotionProduct> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActPromotionProduct mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActPromotionProduct bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkActivityId(Utils.toCast(columnsName.contains(F_FK_ACTIVITY_ID) ? rs.getObject(F_FK_ACTIVITY_ID) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setDiscountTypeId(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_ID) ? rs.getObject(F_DISCOUNT_TYPE_ID) : null, String.class));
            bean.setDiscountTypeName(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_NAME) ? rs.getObject(F_DISCOUNT_TYPE_NAME) : null, String.class));
            bean.setDiscountValue(Utils.toCast(columnsName.contains(F_DISCOUNT_VALUE) ? rs.getObject(F_DISCOUNT_VALUE) : null, Long.class));
            bean.setProductCeilNumber(Utils.toCast(columnsName.contains(F_PRODUCT_CEIL_NUMBER) ? rs.getObject(F_PRODUCT_CEIL_NUMBER) : null, Integer.class));
            bean.setProductDefaultNumber(Utils.toCast(columnsName.contains(F_PRODUCT_DEFAULT_NUMBER) ? rs.getObject(F_PRODUCT_DEFAULT_NUMBER) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddUserName(Utils.toCast(columnsName.contains(F_ADD_USER_NAME) ? rs.getObject(F_ADD_USER_NAME) : null, String.class));
            bean.setOperationTime(Utils.toCast(columnsName.contains(F_OPERATION_TIME) ? rs.getObject(F_OPERATION_TIME) : null, Long.class));
            bean.setOperationUserId(Utils.toCast(columnsName.contains(F_OPERATION_USER_ID) ? rs.getObject(F_OPERATION_USER_ID) : null, String.class));
            bean.setOperationUserName(Utils.toCast(columnsName.contains(F_OPERATION_USER_NAME) ? rs.getObject(F_OPERATION_USER_NAME) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActPromotionProduct> newMapper(){
        return newMapper(ActPromotionProduct::new);
    }

    public RowMapper<ActPromotionProduct> newMapper(Supplier<ActPromotionProduct> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActPromotionProduct {
        @Override
        public abstract RowMapper<ActPromotionProduct> newMapper();
    }
}
