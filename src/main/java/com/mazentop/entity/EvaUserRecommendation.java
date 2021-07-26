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
* Date:        17:44 2021/02/20
* Version:     1.0
* Description: EvaUserRecommendation实体
*/
@SuppressWarnings("all")
public class EvaUserRecommendation extends BaseBean<EvaUserRecommendation> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "eva_user_recommendation";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 推荐人id
    */
    public static final String F_REFERRER_ID = "referrer_id";
    /**
    * 被推荐人id
    */
    public static final String F_USER_ID = "user_id";
    /**
    * 添加人编号
    */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 是否发放佣金
    */
    public static final String F_IS_PAID = "is_paid";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_REFERRER_ID, null);
        put(F_USER_ID, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_TIME, null);
        put(F_IS_PAID, null);
    }

    public EvaUserRecommendation() {
        super();
    }

    public EvaUserRecommendation(Map<String, Object> map) {
        super(map);
    }

    public EvaUserRecommendation(String id) {
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
    public EvaUserRecommendation setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return referrer_id to referrerId 推荐人id<BR/>
    */
    public String getReferrerId() {
        return getTypedValue(F_REFERRER_ID, String.class);
    }
    /**
    * @param referrerId to referrer_id 推荐人id set
    */
    public EvaUserRecommendation setReferrerId(String referrerId) {
        set(F_REFERRER_ID, referrerId);
        return this;
    }
    /**
    * @return user_id to userId 被推荐人id<BR/>
    */
    public String getUserId() {
        return getTypedValue(F_USER_ID, String.class);
    }
    /**
    * @param userId to user_id 被推荐人id set
    */
    public EvaUserRecommendation setUserId(String userId) {
        set(F_USER_ID, userId);
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
    public EvaUserRecommendation setAddUserId(String addUserId) {
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
    public EvaUserRecommendation setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return is_paid to isPaid 是否发放佣金<BR/>
    */
    public Integer getIsPaid() {
        return getTypedValue(F_IS_PAID, Integer.class);
    }
    /**
    * @param isPaid to is_paid 是否发放佣金 set
    */
    public EvaUserRecommendation setIsPaid(Integer isPaid) {
        set(F_IS_PAID, isPaid);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaUserRecommendation setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaUserRecommendation me(){
        return new EvaUserRecommendation();
    }

    private static class Mapper implements RowMapper<EvaUserRecommendation> {

        private Supplier<EvaUserRecommendation> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaUserRecommendation mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaUserRecommendation bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setReferrerId(Utils.toCast(columnsName.contains(F_REFERRER_ID) ? rs.getObject(F_REFERRER_ID) : null, String.class));
            bean.setUserId(Utils.toCast(columnsName.contains(F_USER_ID) ? rs.getObject(F_USER_ID) : null, String.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setIsPaid(Utils.toCast(columnsName.contains(F_IS_PAID) ? rs.getObject(F_IS_PAID) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaUserRecommendation> newMapper(){
        return newMapper(EvaUserRecommendation::new);
    }

    public RowMapper<EvaUserRecommendation> newMapper(Supplier<EvaUserRecommendation> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaUserRecommendation {
        @Override
        public abstract RowMapper<EvaUserRecommendation> newMapper();
    }
}
