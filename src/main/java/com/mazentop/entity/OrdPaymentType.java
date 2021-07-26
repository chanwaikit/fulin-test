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
* Date:        11:23 2020/03/26
* Company:     美赞拓
* Version:     1.0
* Description: OrdPaymentType实体
*/
@SuppressWarnings("all")
public class OrdPaymentType extends BaseBean<OrdPaymentType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_payment_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 支付类型名称
    */
    public static final String F_PAYMENT_TYPE_NAME = "payment_type_name";
    /**
    * 支付类型图标
    */
    public static final String F_PAYMENT_TYPE_ICON_URL = "payment_type_icon_url";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PAYMENT_TYPE_NAME, null);
        put(F_PAYMENT_TYPE_ICON_URL, null);
        put(F_REMARK, null);
    }

    public OrdPaymentType() {
        super();
    }

    public OrdPaymentType(Map<String, Object> map) {
        super(map);
    }

    public OrdPaymentType(String id) {
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
    public OrdPaymentType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return payment_type_name to paymentTypeName 支付类型名称<BR/>
    */
    public String getPaymentTypeName() {
        return getTypedValue(F_PAYMENT_TYPE_NAME, String.class);
    }
    /**
    * @param paymentTypeName to payment_type_name 支付类型名称 set
    */
    public OrdPaymentType setPaymentTypeName(String paymentTypeName) {
        set(F_PAYMENT_TYPE_NAME, paymentTypeName);
        return this;
    }
    /**
    * @return payment_type_icon_url to paymentTypeIconUrl 支付类型图标<BR/>
    */
    public String getPaymentTypeIconUrl() {
        return getTypedValue(F_PAYMENT_TYPE_ICON_URL, String.class);
    }
    /**
    * @param paymentTypeIconUrl to payment_type_icon_url 支付类型图标 set
    */
    public OrdPaymentType setPaymentTypeIconUrl(String paymentTypeIconUrl) {
        set(F_PAYMENT_TYPE_ICON_URL, paymentTypeIconUrl);
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
    public OrdPaymentType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdPaymentType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdPaymentType me(){
        return new OrdPaymentType();
    }

    private static class Mapper implements RowMapper<OrdPaymentType> {

        private Supplier<OrdPaymentType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdPaymentType mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdPaymentType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setPaymentTypeName(Utils.toCast(rs.getObject(F_PAYMENT_TYPE_NAME), String.class));
            bean.setPaymentTypeIconUrl(Utils.toCast(rs.getObject(F_PAYMENT_TYPE_ICON_URL), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdPaymentType> newMapper(){
        return newMapper(OrdPaymentType::new);
    }

    public RowMapper<OrdPaymentType> newMapper(Supplier<OrdPaymentType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdPaymentType {
        @Override
        public abstract RowMapper<OrdPaymentType> newMapper();
    }
}
