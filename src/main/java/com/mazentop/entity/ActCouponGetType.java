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
* Author:      dengy
* Mail:        dengy@mazentop.com
* Date:        16:53 2020/03/17
* Company:     美赞拓
* Version:     1.0
* Description: ActCouponGetType实体
*/
@SuppressWarnings("all")
public class ActCouponGetType extends BaseBean<ActCouponGetType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "act_coupon_get_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 优惠卷获取方式名称
    */
    public static final String F_COUPON_GET_TYPE_NAME = "coupon_get_type_name";
    /**
    * 优惠卷获取方式说明
    */
    public static final String F_CONPON_GET_TYPE_CONTENT = "conpon_get_type_content";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COUPON_GET_TYPE_NAME, null);
        put(F_CONPON_GET_TYPE_CONTENT, null);
        put(F_REMARK, null);
    }

    public ActCouponGetType() {
        super();
    }

    public ActCouponGetType(Map<String, Object> map) {
        super(map);
    }

    public ActCouponGetType(String id) {
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
    public ActCouponGetType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return coupon_get_type_name to couponGetTypeName 优惠卷获取方式名称<BR/>
    */
    public String getCouponGetTypeName() {
        return getTypedValue(F_COUPON_GET_TYPE_NAME, String.class);
    }
    /**
    * @param couponGetTypeName to coupon_get_type_name 优惠卷获取方式名称 set
    */
    public ActCouponGetType setCouponGetTypeName(String couponGetTypeName) {
        set(F_COUPON_GET_TYPE_NAME, couponGetTypeName);
        return this;
    }
    /**
    * @return conpon_get_type_content to conponGetTypeContent 优惠卷获取方式说明<BR/>
    */
    public String getConponGetTypeContent() {
        return getTypedValue(F_CONPON_GET_TYPE_CONTENT, String.class);
    }
    /**
    * @param conponGetTypeContent to conpon_get_type_content 优惠卷获取方式说明 set
    */
    public ActCouponGetType setConponGetTypeContent(String conponGetTypeContent) {
        set(F_CONPON_GET_TYPE_CONTENT, conponGetTypeContent);
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
    public ActCouponGetType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActCouponGetType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActCouponGetType me(){
        return new ActCouponGetType();
    }

    private static class Mapper implements RowMapper<ActCouponGetType> {

        private Supplier<ActCouponGetType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActCouponGetType mapRow(ResultSet rs, int rownum) throws SQLException {
            ActCouponGetType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCouponGetTypeName(Utils.toCast(rs.getObject(F_COUPON_GET_TYPE_NAME), String.class));
            bean.setConponGetTypeContent(Utils.toCast(rs.getObject(F_CONPON_GET_TYPE_CONTENT), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActCouponGetType> newMapper(){
        return newMapper(ActCouponGetType::new);
    }

    public RowMapper<ActCouponGetType> newMapper(Supplier<ActCouponGetType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActCouponGetType {
        @Override
        public abstract RowMapper<ActCouponGetType> newMapper();
    }
}
