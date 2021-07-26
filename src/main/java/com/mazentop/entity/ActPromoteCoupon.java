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
* Date:        14:52 2020/04/16
* Company:     美赞拓
* Version:     1.0
* Description: ActPromoteCoupon实体
*/

public class ActPromoteCoupon extends BaseBean<ActPromoteCoupon> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "act_promote_coupon";

    /**
     * ID
     */
    public static final String F_ID = "id";
    /**
     * 优惠券ID
     */
    public static final String F_FK_ACTIVITY_ID = "fk_activity_id";
    /**
     * 过期时间
     */
    public static final String F_EXPIRED_TIME = "expired_time";
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
        put(F_EXPIRED_TIME, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ActPromoteCoupon() {
        super();
    }

    public ActPromoteCoupon(Map<String, Object> map) {
        super(map);
    }

    public ActPromoteCoupon(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id ID<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id ID set
     */
    public ActPromoteCoupon setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_activity_id to fkActivityId 优惠券ID<BR/>
     */
    public String getFkActivityId() {
        return getTypedValue(F_FK_ACTIVITY_ID, String.class);
    }
    /**
     * @param fkActivityId to fk_activity_id 优惠券ID set
     */
    public ActPromoteCoupon setFkActivityId(String fkActivityId) {
        set(F_FK_ACTIVITY_ID, fkActivityId);
        return this;
    }
    /**
     * @return expired_time to expiredTime 过期时间<BR/>
     */
    public Long getExpiredTime() {
        return getTypedValue(F_EXPIRED_TIME, Long.class);
    }
    /**
     * @param expiredTime to expired_time 过期时间 set
     */
    public ActPromoteCoupon setExpiredTime(Long expiredTime) {
        set(F_EXPIRED_TIME, expiredTime);
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
    public ActPromoteCoupon setAddTime(Long addTime) {
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
    public ActPromoteCoupon setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActPromoteCoupon setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActPromoteCoupon me(){
        return new ActPromoteCoupon();
    }

    private static class Mapper implements RowMapper<ActPromoteCoupon> {

        private Supplier<ActPromoteCoupon> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActPromoteCoupon mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ActPromoteCoupon bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkActivityId(Utils.toCast(columnsName.contains(F_FK_ACTIVITY_ID) ? rs.getObject(F_FK_ACTIVITY_ID) : null, String.class));
            bean.setExpiredTime(Utils.toCast(columnsName.contains(F_EXPIRED_TIME) ? rs.getObject(F_EXPIRED_TIME) : null, Long.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActPromoteCoupon> newMapper(){
        return newMapper(ActPromoteCoupon::new);
    }

    public RowMapper<ActPromoteCoupon> newMapper(Supplier<ActPromoteCoupon> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActPromoteCoupon {
        @Override
        public abstract RowMapper<ActPromoteCoupon> newMapper();
    }
}
