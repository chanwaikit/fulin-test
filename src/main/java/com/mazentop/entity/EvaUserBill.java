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
* Date:        17:43 2021/02/20
* Version:     1.0
* Description: EvaUserBill实体
*/
@SuppressWarnings("all")
public class EvaUserBill extends BaseBean<EvaUserBill> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "eva_user_bill";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 用户id
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 返现总额
    */
    public static final String F_TOTAL_CASH_BACK = "total_cash_back";
    /**
    * 已提现返现
    */
    public static final String F_TOTAL_CASH_WITHDRAWAL = "total_cash_withdrawal";
    /**
    * 返现余额
    */
    public static final String F_CASH_BACK_BALANCE = "cash_back_balance";
    /**
    * 佣金总额
    */
    public static final String F_TOTAL_COMMISSION = "total_commission";
    /**
    * 已提现佣金
    */
    public static final String F_COMMISSION_WITHDRAWAL = "commission_withdrawal";
    /**
    * 佣金余额
    */
    public static final String F_COMMISSION_BALANCE = "commission_balance";
    /**
    * 佣金待提现
    */
    public static final String F_COMMISSION_TO_BE_WITHDRAWN = "commission_to_be_withdrawn";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_TOTAL_CASH_BACK, null);
        put(F_TOTAL_CASH_WITHDRAWAL, null);
        put(F_CASH_BACK_BALANCE, null);
        put(F_TOTAL_COMMISSION, null);
        put(F_COMMISSION_WITHDRAWAL, null);
        put(F_COMMISSION_BALANCE, null);
        put(F_COMMISSION_TO_BE_WITHDRAWN, null);
    }

    public EvaUserBill() {
        super();
    }

    public EvaUserBill(Map<String, Object> map) {
        super(map);
    }

    public EvaUserBill(String id) {
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
    public EvaUserBill setId(String id) {
        set(F_ID, id);
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
    public EvaUserBill setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return total_cash_back to totalCashBack 返现总额<BR/>
    */
    public Long getTotalCashBack() {
        return getTypedValue(F_TOTAL_CASH_BACK, Long.class);
    }
    /**
    * @param totalCashBack to total_cash_back 返现总额 set
    */
    public EvaUserBill setTotalCashBack(Long totalCashBack) {
        set(F_TOTAL_CASH_BACK, totalCashBack);
        return this;
    }
    /**
    * @return total_cash_withdrawal to totalCashWithdrawal 已提现返现<BR/>
    */
    public Long getTotalCashWithdrawal() {
        return getTypedValue(F_TOTAL_CASH_WITHDRAWAL, Long.class);
    }
    /**
    * @param totalCashWithdrawal to total_cash_withdrawal 已提现返现 set
    */
    public EvaUserBill setTotalCashWithdrawal(Long totalCashWithdrawal) {
        set(F_TOTAL_CASH_WITHDRAWAL, totalCashWithdrawal);
        return this;
    }
    /**
    * @return cash_back_balance to cashBackBalance 返现余额<BR/>
    */
    public Long getCashBackBalance() {
        return getTypedValue(F_CASH_BACK_BALANCE, Long.class);
    }
    /**
    * @param cashBackBalance to cash_back_balance 返现余额 set
    */
    public EvaUserBill setCashBackBalance(Long cashBackBalance) {
        set(F_CASH_BACK_BALANCE, cashBackBalance);
        return this;
    }
    /**
    * @return total_commission to totalCommission 佣金总额<BR/>
    */
    public Long getTotalCommission() {
        return getTypedValue(F_TOTAL_COMMISSION, Long.class);
    }
    /**
    * @param totalCommission to total_commission 佣金总额 set
    */
    public EvaUserBill setTotalCommission(Long totalCommission) {
        set(F_TOTAL_COMMISSION, totalCommission);
        return this;
    }
    /**
    * @return commission_withdrawal to commissionWithdrawal 已提现佣金<BR/>
    */
    public Long getCommissionWithdrawal() {
        return getTypedValue(F_COMMISSION_WITHDRAWAL, Long.class);
    }
    /**
    * @param commissionWithdrawal to commission_withdrawal 已提现佣金 set
    */
    public EvaUserBill setCommissionWithdrawal(Long commissionWithdrawal) {
        set(F_COMMISSION_WITHDRAWAL, commissionWithdrawal);
        return this;
    }
    /**
    * @return commission_balance to commissionBalance 佣金余额<BR/>
    */
    public Long getCommissionBalance() {
        return getTypedValue(F_COMMISSION_BALANCE, Long.class);
    }
    /**
    * @param commissionBalance to commission_balance 佣金余额 set
    */
    public EvaUserBill setCommissionBalance(Long commissionBalance) {
        set(F_COMMISSION_BALANCE, commissionBalance);
        return this;
    }
    /**
    * @return commission_to_be_withdrawn to commissionToBeWithdrawn 佣金待提现<BR/>
    */
    public Long getCommissionToBeWithdrawn() {
        return getTypedValue(F_COMMISSION_TO_BE_WITHDRAWN, Long.class);
    }
    /**
    * @param commissionToBeWithdrawn to commission_to_be_withdrawn 佣金待提现 set
    */
    public EvaUserBill setCommissionToBeWithdrawn(Long commissionToBeWithdrawn) {
        set(F_COMMISSION_TO_BE_WITHDRAWN, commissionToBeWithdrawn);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaUserBill setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaUserBill me(){
        return new EvaUserBill();
    }

    private static class Mapper implements RowMapper<EvaUserBill> {

        private Supplier<EvaUserBill> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaUserBill mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaUserBill bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setTotalCashBack(Utils.toCast(columnsName.contains(F_TOTAL_CASH_BACK) ? rs.getObject(F_TOTAL_CASH_BACK) : null, Long.class));
            bean.setTotalCashWithdrawal(Utils.toCast(columnsName.contains(F_TOTAL_CASH_WITHDRAWAL) ? rs.getObject(F_TOTAL_CASH_WITHDRAWAL) : null, Long.class));
            bean.setCashBackBalance(Utils.toCast(columnsName.contains(F_CASH_BACK_BALANCE) ? rs.getObject(F_CASH_BACK_BALANCE) : null, Long.class));
            bean.setTotalCommission(Utils.toCast(columnsName.contains(F_TOTAL_COMMISSION) ? rs.getObject(F_TOTAL_COMMISSION) : null, Long.class));
            bean.setCommissionWithdrawal(Utils.toCast(columnsName.contains(F_COMMISSION_WITHDRAWAL) ? rs.getObject(F_COMMISSION_WITHDRAWAL) : null, Long.class));
            bean.setCommissionBalance(Utils.toCast(columnsName.contains(F_COMMISSION_BALANCE) ? rs.getObject(F_COMMISSION_BALANCE) : null, Long.class));
            bean.setCommissionToBeWithdrawn(Utils.toCast(columnsName.contains(F_COMMISSION_TO_BE_WITHDRAWN) ? rs.getObject(F_COMMISSION_TO_BE_WITHDRAWN) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaUserBill> newMapper(){
        return newMapper(EvaUserBill::new);
    }

    public RowMapper<EvaUserBill> newMapper(Supplier<EvaUserBill> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaUserBill {
        @Override
        public abstract RowMapper<EvaUserBill> newMapper();
    }
}
