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
* Date:        11:43 2021/04/29
* Version:     1.0
* Description: EvaWithdrawal实体
*/
@SuppressWarnings("all")
public class EvaWithdrawal extends BaseBean<EvaWithdrawal> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "eva_withdrawal";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 本地单号
    */
    public static final String F_SN = "sn";
    /**
    * 用户id
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 提现金额
    */
    public static final String F_WITHDRAWAL_AMOUNT = "withdrawal_amount";
    /**
    * paypal账号
    */
    public static final String F_PAYPAL_ACCOUNT = "paypal_account";
    /**
    * 提现申请时间
    */
    public static final String F_APPLY_TIME = "apply_time";
    /**
    * 提现备注
    */
    public static final String F_WITHDRAWAL_REMARKS = "withdrawal_remarks";
    /**
    * 状态（1-待审核 2-审核通过 3-审核不通过）
    */
    public static final String F_STATUS = "status";
    /**
    * 审核人
    */
    public static final String F_REVIEWER_ID = "reviewer_id";
    /**
    * 审核时间
    */
    public static final String F_REVIEWER_TIME = "reviewer_time";
    /**
    * 审核备注
    */
    public static final String F_REVIEWER_REMARKS = "reviewer_remarks";
    /**
    * 转账凭证
    */
    public static final String F_TRANSFER_VOUCHER = "transfer_voucher";
    /**
    * 转账交易流水号
    */
    public static final String F_TRANSACTION_NO = "transaction_no";
    /**
    * 添加人编号
    */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 提现类型（1-返现、2-佣金）
    */
    public static final String F_TYPE = "type";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SN, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_WITHDRAWAL_AMOUNT, null);
        put(F_PAYPAL_ACCOUNT, null);
        put(F_APPLY_TIME, null);
        put(F_WITHDRAWAL_REMARKS, null);
        put(F_STATUS, null);
        put(F_REVIEWER_ID, null);
        put(F_REVIEWER_TIME, null);
        put(F_REVIEWER_REMARKS, null);
        put(F_TRANSFER_VOUCHER, null);
        put(F_TRANSACTION_NO, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_TIME, null);
        put(F_TYPE, null);
    }

    public EvaWithdrawal() {
        super();
    }

    public EvaWithdrawal(Map<String, Object> map) {
        super(map);
    }

    public EvaWithdrawal(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id id<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id id set
    */
    public EvaWithdrawal setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return sn to sn 本地单号<BR/>
    */
    public String getSn() {
        return getTypedValue(F_SN, String.class);
    }
    /**
    * @param sn to sn 本地单号 set
    */
    public EvaWithdrawal setSn(String sn) {
        set(F_SN, sn);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 用户id<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 用户id set
    */
    public EvaWithdrawal setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return withdrawal_amount to withdrawalAmount 提现金额<BR/>
    */
    public Long getWithdrawalAmount() {
        return getTypedValue(F_WITHDRAWAL_AMOUNT, Long.class);
    }
    /**
    * @param withdrawalAmount to withdrawal_amount 提现金额 set
    */
    public EvaWithdrawal setWithdrawalAmount(Long withdrawalAmount) {
        set(F_WITHDRAWAL_AMOUNT, withdrawalAmount);
        return this;
    }
    /**
    * @return paypal_account to paypalAccount paypal账号<BR/>
    */
    public String getPaypalAccount() {
        return getTypedValue(F_PAYPAL_ACCOUNT, String.class);
    }
    /**
    * @param paypalAccount to paypal_account paypal账号 set
    */
    public EvaWithdrawal setPaypalAccount(String paypalAccount) {
        set(F_PAYPAL_ACCOUNT, paypalAccount);
        return this;
    }
    /**
    * @return apply_time to applyTime 提现申请时间<BR/>
    */
    public Long getApplyTime() {
        return getTypedValue(F_APPLY_TIME, Long.class);
    }
    /**
    * @param applyTime to apply_time 提现申请时间 set
    */
    public EvaWithdrawal setApplyTime(Long applyTime) {
        set(F_APPLY_TIME, applyTime);
        return this;
    }
    /**
    * @return withdrawal_remarks to withdrawalRemarks 提现备注<BR/>
    */
    public String getWithdrawalRemarks() {
        return getTypedValue(F_WITHDRAWAL_REMARKS, String.class);
    }
    /**
    * @param withdrawalRemarks to withdrawal_remarks 提现备注 set
    */
    public EvaWithdrawal setWithdrawalRemarks(String withdrawalRemarks) {
        set(F_WITHDRAWAL_REMARKS, withdrawalRemarks);
        return this;
    }
    /**
    * @return status to status 状态（1-待审核 2-审核通过 3-审核不通过）<BR/>
    */
    public Integer getStatus() {
        return getTypedValue(F_STATUS, Integer.class);
    }
    /**
    * @param status to status 状态（1-待审核 2-审核通过 3-审核不通过） set
    */
    public EvaWithdrawal setStatus(Integer status) {
        set(F_STATUS, status);
        return this;
    }
    /**
    * @return reviewer_id to reviewerId 审核人<BR/>
    */
    public String getReviewerId() {
        return getTypedValue(F_REVIEWER_ID, String.class);
    }
    /**
    * @param reviewerId to reviewer_id 审核人 set
    */
    public EvaWithdrawal setReviewerId(String reviewerId) {
        set(F_REVIEWER_ID, reviewerId);
        return this;
    }
    /**
    * @return reviewer_time to reviewerTime 审核时间<BR/>
    */
    public Long getReviewerTime() {
        return getTypedValue(F_REVIEWER_TIME, Long.class);
    }
    /**
    * @param reviewerTime to reviewer_time 审核时间 set
    */
    public EvaWithdrawal setReviewerTime(Long reviewerTime) {
        set(F_REVIEWER_TIME, reviewerTime);
        return this;
    }
    /**
    * @return reviewer_remarks to reviewerRemarks 审核备注<BR/>
    */
    public String getReviewerRemarks() {
        return getTypedValue(F_REVIEWER_REMARKS, String.class);
    }
    /**
    * @param reviewerRemarks to reviewer_remarks 审核备注 set
    */
    public EvaWithdrawal setReviewerRemarks(String reviewerRemarks) {
        set(F_REVIEWER_REMARKS, reviewerRemarks);
        return this;
    }
    /**
    * @return transfer_voucher to transferVoucher 转账凭证<BR/>
    */
    public String getTransferVoucher() {
        return getTypedValue(F_TRANSFER_VOUCHER, String.class);
    }
    /**
    * @param transferVoucher to transfer_voucher 转账凭证 set
    */
    public EvaWithdrawal setTransferVoucher(String transferVoucher) {
        set(F_TRANSFER_VOUCHER, transferVoucher);
        return this;
    }
    /**
    * @return transaction_no to transactionNo 转账交易流水号<BR/>
    */
    public String getTransactionNo() {
        return getTypedValue(F_TRANSACTION_NO, String.class);
    }
    /**
    * @param transactionNo to transaction_no 转账交易流水号 set
    */
    public EvaWithdrawal setTransactionNo(String transactionNo) {
        set(F_TRANSACTION_NO, transactionNo);
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
    public EvaWithdrawal setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public EvaWithdrawal setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return type to type 提现类型（1-返现、2-佣金）<BR/>
    */
    public Integer getType() {
        return getTypedValue(F_TYPE, Integer.class);
    }
    /**
    * @param type to type 提现类型（1-返现、2-佣金） set
    */
    public EvaWithdrawal setType(Integer type) {
        set(F_TYPE, type);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaWithdrawal setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaWithdrawal me(){
        return new EvaWithdrawal();
    }

    private static class Mapper implements RowMapper<EvaWithdrawal> {

        private Supplier<EvaWithdrawal> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaWithdrawal mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaWithdrawal bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setSn(Utils.toCast(columnsName.contains(F_SN) ? rs.getObject(F_SN) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setWithdrawalAmount(Utils.toCast(columnsName.contains(F_WITHDRAWAL_AMOUNT) ? rs.getObject(F_WITHDRAWAL_AMOUNT) : null, Long.class));
            bean.setPaypalAccount(Utils.toCast(columnsName.contains(F_PAYPAL_ACCOUNT) ? rs.getObject(F_PAYPAL_ACCOUNT) : null, String.class));
            bean.setApplyTime(Utils.toCast(columnsName.contains(F_APPLY_TIME) ? rs.getObject(F_APPLY_TIME) : null, Long.class));
            bean.setWithdrawalRemarks(Utils.toCast(columnsName.contains(F_WITHDRAWAL_REMARKS) ? rs.getObject(F_WITHDRAWAL_REMARKS) : null, String.class));
            bean.setStatus(Utils.toCast(columnsName.contains(F_STATUS) ? rs.getObject(F_STATUS) : null, Integer.class));
            bean.setReviewerId(Utils.toCast(columnsName.contains(F_REVIEWER_ID) ? rs.getObject(F_REVIEWER_ID) : null, String.class));
            bean.setReviewerTime(Utils.toCast(columnsName.contains(F_REVIEWER_TIME) ? rs.getObject(F_REVIEWER_TIME) : null, Long.class));
            bean.setReviewerRemarks(Utils.toCast(columnsName.contains(F_REVIEWER_REMARKS) ? rs.getObject(F_REVIEWER_REMARKS) : null, String.class));
            bean.setTransferVoucher(Utils.toCast(columnsName.contains(F_TRANSFER_VOUCHER) ? rs.getObject(F_TRANSFER_VOUCHER) : null, String.class));
            bean.setTransactionNo(Utils.toCast(columnsName.contains(F_TRANSACTION_NO) ? rs.getObject(F_TRANSACTION_NO) : null, String.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setType(Utils.toCast(columnsName.contains(F_TYPE) ? rs.getObject(F_TYPE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaWithdrawal> newMapper(){
        return newMapper(EvaWithdrawal::new);
    }

    public RowMapper<EvaWithdrawal> newMapper(Supplier<EvaWithdrawal> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaWithdrawal {
        @Override
        public abstract RowMapper<EvaWithdrawal> newMapper();
    }
}
