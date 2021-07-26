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
* Date:        11:04 2020/04/26
* Company:     美赞拓
* Version:     1.0
* Description: OrdPaymentOfflineRecord实体
*/
@SuppressWarnings("all")
public class OrdPaymentOfflineRecord extends BaseBean<OrdPaymentOfflineRecord> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_payment_offline_record";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 订单id
    */
    public static final String F_FK_SALES_ORDER_ID = "fk_sales_order_id";
    /**
    * 支付用户Id
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 收款银行卡
    */
    public static final String F_CARD_NO = "card_no";
    /**
    * 收款人
    */
    public static final String F_CARD_NAME = "card_name";
    /**
    * 支付人
    */
    public static final String F_PAYMENT_NAME = "payment_name";
    /**
    * 支付人邮箱
    */
    public static final String F_PAYMENT_EMAIL = "payment_email";
    /**
    * 支付凭证
    */
    public static final String F_PAYMENT_VOUCHER = "payment_voucher";
    /**
    * 预留字段
    */
    public static final String F_PAYMENT_VOUCHER_TAG = "payment_voucher_tag";
    /**
    * 审核状态，0提交、1审核成功、2驳回、3复审
    */
    public static final String F_PAYMENT_STATE = "payment_state";
    /**
    * 驳回备注
    */
    public static final String F_REMARK = "remark";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
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
        put(F_FK_CLIENTELE_ID, null);
        put(F_CARD_NO, null);
        put(F_CARD_NAME, null);
        put(F_PAYMENT_NAME, null);
        put(F_PAYMENT_EMAIL, null);
        put(F_PAYMENT_VOUCHER, null);
        put(F_PAYMENT_VOUCHER_TAG, null);
        put(F_PAYMENT_STATE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public OrdPaymentOfflineRecord() {
        super();
    }

    public OrdPaymentOfflineRecord(Map<String, Object> map) {
        super(map);
    }

    public OrdPaymentOfflineRecord(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id <BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id  set
    */
    public OrdPaymentOfflineRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_sales_order_id to fkSalesOrderId 订单id<BR/>
    */
    public String getFkSalesOrderId() {
        return getTypedValue(F_FK_SALES_ORDER_ID, String.class);
    }
    /**
    * @param fkSalesOrderId to fk_sales_order_id 订单id set
    */
    public OrdPaymentOfflineRecord setFkSalesOrderId(String fkSalesOrderId) {
        set(F_FK_SALES_ORDER_ID, fkSalesOrderId);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 支付用户Id<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 支付用户Id set
    */
    public OrdPaymentOfflineRecord setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return card_no to cardNo 收款银行卡<BR/>
    */
    public String getCardNo() {
        return getTypedValue(F_CARD_NO, String.class);
    }
    /**
    * @param cardNo to card_no 收款银行卡 set
    */
    public OrdPaymentOfflineRecord setCardNo(String cardNo) {
        set(F_CARD_NO, cardNo);
        return this;
    }
    /**
    * @return card_name to cardName 收款人<BR/>
    */
    public String getCardName() {
        return getTypedValue(F_CARD_NAME, String.class);
    }
    /**
    * @param cardName to card_name 收款人 set
    */
    public OrdPaymentOfflineRecord setCardName(String cardName) {
        set(F_CARD_NAME, cardName);
        return this;
    }
    /**
    * @return payment_name to paymentName 支付人<BR/>
    */
    public String getPaymentName() {
        return getTypedValue(F_PAYMENT_NAME, String.class);
    }
    /**
    * @param paymentName to payment_name 支付人 set
    */
    public OrdPaymentOfflineRecord setPaymentName(String paymentName) {
        set(F_PAYMENT_NAME, paymentName);
        return this;
    }
    /**
    * @return payment_email to paymentEmail 支付人邮箱<BR/>
    */
    public String getPaymentEmail() {
        return getTypedValue(F_PAYMENT_EMAIL, String.class);
    }
    /**
    * @param paymentEmail to payment_email 支付人邮箱 set
    */
    public OrdPaymentOfflineRecord setPaymentEmail(String paymentEmail) {
        set(F_PAYMENT_EMAIL, paymentEmail);
        return this;
    }
    /**
    * @return payment_voucher to paymentVoucher 支付凭证<BR/>
    */
    public String getPaymentVoucher() {
        return getTypedValue(F_PAYMENT_VOUCHER, String.class);
    }
    /**
    * @param paymentVoucher to payment_voucher 支付凭证 set
    */
    public OrdPaymentOfflineRecord setPaymentVoucher(String paymentVoucher) {
        set(F_PAYMENT_VOUCHER, paymentVoucher);
        return this;
    }
    /**
    * @return payment_voucher_tag to paymentVoucherTag 预留字段<BR/>
    */
    public String getPaymentVoucherTag() {
        return getTypedValue(F_PAYMENT_VOUCHER_TAG, String.class);
    }
    /**
    * @param paymentVoucherTag to payment_voucher_tag 预留字段 set
    */
    public OrdPaymentOfflineRecord setPaymentVoucherTag(String paymentVoucherTag) {
        set(F_PAYMENT_VOUCHER_TAG, paymentVoucherTag);
        return this;
    }
    /**
    * @return payment_state to paymentState 审核状态，0提交、1审核成功、2驳回、3复审<BR/>
    */
    public Integer getPaymentState() {
        return getTypedValue(F_PAYMENT_STATE, Integer.class);
    }
    /**
    * @param paymentState to payment_state 审核状态，0提交、1审核成功、2驳回、3复审 set
    */
    public OrdPaymentOfflineRecord setPaymentState(Integer paymentState) {
        set(F_PAYMENT_STATE, paymentState);
        return this;
    }
    /**
    * @return remark to remark 驳回备注<BR/>
    */
    public String getRemark() {
        return getTypedValue(F_REMARK, String.class);
    }
    /**
    * @param remark to remark 驳回备注 set
    */
    public OrdPaymentOfflineRecord setRemark(String remark) {
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
    public OrdPaymentOfflineRecord setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
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
    public OrdPaymentOfflineRecord setOperationTime(Long operationTime) {
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
    public OrdPaymentOfflineRecord setOperationUserId(String operationUserId) {
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
    public OrdPaymentOfflineRecord setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdPaymentOfflineRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdPaymentOfflineRecord me(){
        return new OrdPaymentOfflineRecord();
    }

    private static class Mapper implements RowMapper<OrdPaymentOfflineRecord> {

        private Supplier<OrdPaymentOfflineRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdPaymentOfflineRecord mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdPaymentOfflineRecord bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkSalesOrderId(Utils.toCast(rs.getObject(F_FK_SALES_ORDER_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setCardNo(Utils.toCast(rs.getObject(F_CARD_NO), String.class));
            bean.setCardName(Utils.toCast(rs.getObject(F_CARD_NAME), String.class));
            bean.setPaymentName(Utils.toCast(rs.getObject(F_PAYMENT_NAME), String.class));
            bean.setPaymentEmail(Utils.toCast(rs.getObject(F_PAYMENT_EMAIL), String.class));
            bean.setPaymentVoucher(Utils.toCast(rs.getObject(F_PAYMENT_VOUCHER), String.class));
            bean.setPaymentVoucherTag(Utils.toCast(rs.getObject(F_PAYMENT_VOUCHER_TAG), String.class));
            bean.setPaymentState(Utils.toCast(rs.getObject(F_PAYMENT_STATE), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdPaymentOfflineRecord> newMapper(){
        return newMapper(OrdPaymentOfflineRecord::new);
    }

    public RowMapper<OrdPaymentOfflineRecord> newMapper(Supplier<OrdPaymentOfflineRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdPaymentOfflineRecord {
        @Override
        public abstract RowMapper<OrdPaymentOfflineRecord> newMapper();
    }
}
