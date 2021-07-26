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
* Date:        13:47 2021/01/08
* Version:     1.0
* Description: EvaCommissionDetails实体
*/
@SuppressWarnings("all")
public class EvaCommissionDetails extends BaseBean<EvaCommissionDetails> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "eva_commission_details";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 用户id
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 佣金金额
    */
    public static final String F_AMOUNT = "amount";
    /**
    * 佣金说明
    */
    public static final String F_EXPLAIN = "explain";
    /**
    * 添加人编号
    */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_AMOUNT, null);
        put(F_EXPLAIN, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_TIME, null);
    }

    public EvaCommissionDetails() {
        super();
    }

    public EvaCommissionDetails(Map<String, Object> map) {
        super(map);
    }

    public EvaCommissionDetails(Integer id) {
        super();
        setId(id);
    }

    /**
    * @return id to id id<BR/>
    */
    public Integer getId() {
        return getTypedValue(F_ID, Integer.class);
    }
    /**
    * @param id to id id set
    */
    public EvaCommissionDetails setId(Integer id) {
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
    public EvaCommissionDetails setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return amount to amount 佣金金额<BR/>
    */
    public Long getAmount() {
        return getTypedValue(F_AMOUNT, Long.class);
    }
    /**
    * @param amount to amount 佣金金额 set
    */
    public EvaCommissionDetails setAmount(Long amount) {
        set(F_AMOUNT, amount);
        return this;
    }
    /**
    * @return explain to explain 佣金说明<BR/>
    */
    public String getExplain() {
        return getTypedValue(F_EXPLAIN, String.class);
    }
    /**
    * @param explain to explain 佣金说明 set
    */
    public EvaCommissionDetails setExplain(String explain) {
        set(F_EXPLAIN, explain);
        return this;
    }
    /**
    * @return add_user_id to addUserId 添加人编号<BR/>
    */
    public Long getAddUserId() {
        return getTypedValue(F_ADD_USER_ID, Long.class);
    }
    /**
    * @param addUserId to add_user_id 添加人编号 set
    */
    public EvaCommissionDetails setAddUserId(Long addUserId) {
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
    public EvaCommissionDetails setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaCommissionDetails setPrimaryKey(Object key) {
        setId(Utils.toCast(key, Integer.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaCommissionDetails me(){
        return new EvaCommissionDetails();
    }

    private static class Mapper implements RowMapper<EvaCommissionDetails> {

        private Supplier<EvaCommissionDetails> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaCommissionDetails mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaCommissionDetails bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, Integer.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setAmount(Utils.toCast(columnsName.contains(F_AMOUNT) ? rs.getObject(F_AMOUNT) : null, Long.class));
            bean.setExplain(Utils.toCast(columnsName.contains(F_EXPLAIN) ? rs.getObject(F_EXPLAIN) : null, String.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, Long.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaCommissionDetails> newMapper(){
        return newMapper(EvaCommissionDetails::new);
    }

    public RowMapper<EvaCommissionDetails> newMapper(Supplier<EvaCommissionDetails> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaCommissionDetails {
        @Override
        public abstract RowMapper<EvaCommissionDetails> newMapper();
    }
}
