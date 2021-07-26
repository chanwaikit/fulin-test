package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:57 2020/05/11
* Company:     美赞拓
* Version:     1.0
* Description: OrdPaymentRecord实体
*/
@SuppressWarnings("all")
public class OrdPaymentRecord extends BaseBean<OrdPaymentRecord> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_payment_record";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 结算单编号
     */
    public static final String F_BALANCE_THE_BOOKS_NO = "balance_the_books_no";
    /**
     * 客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 支付方式编号
     */
    public static final String F_FK_PAYMENT_TYPE_ID = "fk_payment_type_id";
    /**
     * 支付方式名称
     */
    public static final String F_PAYMENT_TYPE_NAME = "payment_type_name";
    /**
     * 商品总价格
     */
    public static final String F_PRODUCT_TOTAL_PRICE = "product_total_price";
    /**
     * 商品优惠后总价格
     */
    public static final String F_PRODUCT_DISCOUNT_PRICE = "product_discount_price";
    /**
     * 物流费用费用价格
     */
    public static final String F_FREE_SCHEME_TOTAL_PRICE = "free_scheme_total_price";
    /**
     * 待支付总价格
     */
    public static final String F_PAYMENT_TOTAL_PRICE = "payment_total_price";
    /**
     * 支付时间
     */
    public static final String F_PAYMENT_TIME = "payment_time";
    /**
     * 交易设备
     */
    public static final String F_PAYMENT_EQUIPMENT = "payment_equipment";
    /**
     * 支付是否成功
     */
    public static final String F_IS_PAY_SUCCESS = "is_pay_success";
    /**
     * 支付返回记录
     */
    public static final String F_PAYMENT_RETURN_RECORD = "payment_return_record";
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
    /**
     * 回调支付订单号
     */
    public static final String F_PAY_ID = "pay_id";
    /**
     *
     */
    public static final String F_ORDER_ID = "order_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_BALANCE_THE_BOOKS_NO, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_FK_PAYMENT_TYPE_ID, null);
        put(F_PAYMENT_TYPE_NAME, null);
        put(F_PRODUCT_TOTAL_PRICE, null);
        put(F_PRODUCT_DISCOUNT_PRICE, null);
        put(F_FREE_SCHEME_TOTAL_PRICE, null);
        put(F_PAYMENT_TOTAL_PRICE, null);
        put(F_PAYMENT_TIME, null);
        put(F_PAYMENT_EQUIPMENT, null);
        put(F_IS_PAY_SUCCESS, null);
        put(F_PAYMENT_RETURN_RECORD, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_PAY_ID, null);
        put(F_ORDER_ID, null);
    }

    public OrdPaymentRecord() {
        super();
    }

    public OrdPaymentRecord(Map<String, Object> map) {
        super(map);
    }

    public OrdPaymentRecord(String id) {
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
    public OrdPaymentRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return balance_the_books_no to balanceTheBooksNo 结算单编号<BR/>
     */
    public String getBalanceTheBooksNo() {
        return getTypedValue(F_BALANCE_THE_BOOKS_NO, String.class);
    }
    /**
     * @param balanceTheBooksNo to balance_the_books_no 结算单编号 set
     */
    public OrdPaymentRecord setBalanceTheBooksNo(String balanceTheBooksNo) {
        set(F_BALANCE_THE_BOOKS_NO, balanceTheBooksNo);
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
    public OrdPaymentRecord setFkClienteleId(String fkClienteleId) {
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
    public OrdPaymentRecord setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return fk_payment_type_id to fkPaymentTypeId 支付方式编号<BR/>
     */
    public String getFkPaymentTypeId() {
        return getTypedValue(F_FK_PAYMENT_TYPE_ID, String.class);
    }
    /**
     * @param fkPaymentTypeId to fk_payment_type_id 支付方式编号 set
     */
    public OrdPaymentRecord setFkPaymentTypeId(String fkPaymentTypeId) {
        set(F_FK_PAYMENT_TYPE_ID, fkPaymentTypeId);
        return this;
    }
    /**
     * @return payment_type_name to paymentTypeName 支付方式名称<BR/>
     */
    public String getPaymentTypeName() {
        return getTypedValue(F_PAYMENT_TYPE_NAME, String.class);
    }
    /**
     * @param paymentTypeName to payment_type_name 支付方式名称 set
     */
    public OrdPaymentRecord setPaymentTypeName(String paymentTypeName) {
        set(F_PAYMENT_TYPE_NAME, paymentTypeName);
        return this;
    }
    /**
     * @return product_total_price to productTotalPrice 商品总价格<BR/>
     */
    public Long getProductTotalPrice() {
        return getTypedValue(F_PRODUCT_TOTAL_PRICE, Long.class);
    }
    /**
     * @param productTotalPrice to product_total_price 商品总价格 set
     */
    public OrdPaymentRecord setProductTotalPrice(Long productTotalPrice) {
        set(F_PRODUCT_TOTAL_PRICE, productTotalPrice);
        return this;
    }
    /**
     * @return product_discount_price to productDiscountPrice 商品优惠后总价格<BR/>
     */
    public Long getProductDiscountPrice() {
        return getTypedValue(F_PRODUCT_DISCOUNT_PRICE, Long.class);
    }
    /**
     * @param productDiscountPrice to product_discount_price 商品优惠后总价格 set
     */
    public OrdPaymentRecord setProductDiscountPrice(Long productDiscountPrice) {
        set(F_PRODUCT_DISCOUNT_PRICE, productDiscountPrice);
        return this;
    }
    /**
     * @return free_scheme_total_price to freeSchemeTotalPrice 物流费用费用价格<BR/>
     */
    public Long getFreeSchemeTotalPrice() {
        return getTypedValue(F_FREE_SCHEME_TOTAL_PRICE, Long.class);
    }
    /**
     * @param freeSchemeTotalPrice to free_scheme_total_price 物流费用费用价格 set
     */
    public OrdPaymentRecord setFreeSchemeTotalPrice(Long freeSchemeTotalPrice) {
        set(F_FREE_SCHEME_TOTAL_PRICE, freeSchemeTotalPrice);
        return this;
    }
    /**
     * @return payment_total_price to paymentTotalPrice 待支付总价格<BR/>
     */
    public Long getPaymentTotalPrice() {
        return getTypedValue(F_PAYMENT_TOTAL_PRICE, Long.class);
    }
    /**
     * @param paymentTotalPrice to payment_total_price 待支付总价格 set
     */
    public OrdPaymentRecord setPaymentTotalPrice(Long paymentTotalPrice) {
        set(F_PAYMENT_TOTAL_PRICE, paymentTotalPrice);
        return this;
    }
    /**
     * @return payment_time to paymentTime 支付时间<BR/>
     */
    public Long getPaymentTime() {
        return getTypedValue(F_PAYMENT_TIME, Long.class);
    }
    /**
     * @param paymentTime to payment_time 支付时间 set
     */
    public OrdPaymentRecord setPaymentTime(Long paymentTime) {
        set(F_PAYMENT_TIME, paymentTime);
        return this;
    }
    /**
     * @return payment_equipment to paymentEquipment 交易设备<BR/>
     */
    public String getPaymentEquipment() {
        return getTypedValue(F_PAYMENT_EQUIPMENT, String.class);
    }
    /**
     * @param paymentEquipment to payment_equipment 交易设备 set
     */
    public OrdPaymentRecord setPaymentEquipment(String paymentEquipment) {
        set(F_PAYMENT_EQUIPMENT, paymentEquipment);
        return this;
    }
    /**
     * @return is_pay_success to isPaySuccess 支付是否成功<BR/>
     */
    public Integer getIsPaySuccess() {
        return getTypedValue(F_IS_PAY_SUCCESS, Integer.class);
    }
    /**
     * @param isPaySuccess to is_pay_success 支付是否成功 set
     */
    public OrdPaymentRecord setIsPaySuccess(Integer isPaySuccess) {
        set(F_IS_PAY_SUCCESS, isPaySuccess);
        return this;
    }
    /**
     * @return payment_return_record to paymentReturnRecord 支付返回记录<BR/>
     */
    public String getPaymentReturnRecord() {
        return getTypedValue(F_PAYMENT_RETURN_RECORD, String.class);
    }
    /**
     * @param paymentReturnRecord to payment_return_record 支付返回记录 set
     */
    public OrdPaymentRecord setPaymentReturnRecord(String paymentReturnRecord) {
        set(F_PAYMENT_RETURN_RECORD, paymentReturnRecord);
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
    public OrdPaymentRecord setRemark(String remark) {
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
    public OrdPaymentRecord setAddTime(Long addTime) {
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
    public OrdPaymentRecord setAddUserId(String addUserId) {
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
    public OrdPaymentRecord setAddUserName(String addUserName) {
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
    public OrdPaymentRecord setOperationTime(Long operationTime) {
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
    public OrdPaymentRecord setOperationUserId(String operationUserId) {
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
    public OrdPaymentRecord setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
     * @return pay_id to payId 回调支付订单号<BR/>
     */
    public String getPayId() {
        return getTypedValue(F_PAY_ID, String.class);
    }
    /**
     * @param payId to pay_id 回调支付订单号 set
     */
    public OrdPaymentRecord setPayId(String payId) {
        set(F_PAY_ID, payId);
        return this;
    }
    /**
     * @return order_id to orderId <BR/>
     */
    public String getOrderId() {
        return getTypedValue(F_ORDER_ID, String.class);
    }
    /**
     * @param orderId to order_id  set
     */
    public OrdPaymentRecord setOrderId(String orderId) {
        set(F_ORDER_ID, orderId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdPaymentRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdPaymentRecord me(){
        return new OrdPaymentRecord();
    }

    private static class Mapper implements RowMapper<OrdPaymentRecord> {

        private Supplier<OrdPaymentRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdPaymentRecord mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            OrdPaymentRecord bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setBalanceTheBooksNo(Utils.toCast(columnsName.contains(F_BALANCE_THE_BOOKS_NO) ? rs.getObject(F_BALANCE_THE_BOOKS_NO) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setFkPaymentTypeId(Utils.toCast(columnsName.contains(F_FK_PAYMENT_TYPE_ID) ? rs.getObject(F_FK_PAYMENT_TYPE_ID) : null, String.class));
            bean.setPaymentTypeName(Utils.toCast(columnsName.contains(F_PAYMENT_TYPE_NAME) ? rs.getObject(F_PAYMENT_TYPE_NAME) : null, String.class));
            bean.setProductTotalPrice(Utils.toCast(columnsName.contains(F_PRODUCT_TOTAL_PRICE) ? rs.getObject(F_PRODUCT_TOTAL_PRICE) : null, Long.class));
            bean.setProductDiscountPrice(Utils.toCast(columnsName.contains(F_PRODUCT_DISCOUNT_PRICE) ? rs.getObject(F_PRODUCT_DISCOUNT_PRICE) : null, Long.class));
            bean.setFreeSchemeTotalPrice(Utils.toCast(columnsName.contains(F_FREE_SCHEME_TOTAL_PRICE) ? rs.getObject(F_FREE_SCHEME_TOTAL_PRICE) : null, Long.class));
            bean.setPaymentTotalPrice(Utils.toCast(columnsName.contains(F_PAYMENT_TOTAL_PRICE) ? rs.getObject(F_PAYMENT_TOTAL_PRICE) : null, Long.class));
            bean.setPaymentTime(Utils.toCast(columnsName.contains(F_PAYMENT_TIME) ? rs.getObject(F_PAYMENT_TIME) : null, Long.class));
            bean.setPaymentEquipment(Utils.toCast(columnsName.contains(F_PAYMENT_EQUIPMENT) ? rs.getObject(F_PAYMENT_EQUIPMENT) : null, String.class));
            bean.setIsPaySuccess(Utils.toCast(columnsName.contains(F_IS_PAY_SUCCESS) ? rs.getObject(F_IS_PAY_SUCCESS) : null, Integer.class));
            bean.setPaymentReturnRecord(Utils.toCast(columnsName.contains(F_PAYMENT_RETURN_RECORD) ? rs.getObject(F_PAYMENT_RETURN_RECORD) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddUserName(Utils.toCast(columnsName.contains(F_ADD_USER_NAME) ? rs.getObject(F_ADD_USER_NAME) : null, String.class));
            bean.setOperationTime(Utils.toCast(columnsName.contains(F_OPERATION_TIME) ? rs.getObject(F_OPERATION_TIME) : null, Long.class));
            bean.setOperationUserId(Utils.toCast(columnsName.contains(F_OPERATION_USER_ID) ? rs.getObject(F_OPERATION_USER_ID) : null, String.class));
            bean.setOperationUserName(Utils.toCast(columnsName.contains(F_OPERATION_USER_NAME) ? rs.getObject(F_OPERATION_USER_NAME) : null, String.class));
            bean.setPayId(Utils.toCast(columnsName.contains(F_PAY_ID) ? rs.getObject(F_PAY_ID) : null, String.class));
            bean.setOrderId(Utils.toCast(columnsName.contains(F_ORDER_ID) ? rs.getObject(F_ORDER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdPaymentRecord> newMapper(){
        return newMapper(OrdPaymentRecord::new);
    }

    public RowMapper<OrdPaymentRecord> newMapper(Supplier<OrdPaymentRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdPaymentRecord {
        @Override
        public abstract RowMapper<OrdPaymentRecord> newMapper();
    }
}
