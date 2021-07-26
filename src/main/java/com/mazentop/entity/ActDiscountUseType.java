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
* Date:        11:11 2020/03/18
* Company:     美赞拓
* Version:     1.0
* Description: ActDiscountUseType实体
*/
@SuppressWarnings("all")
public class ActDiscountUseType extends BaseBean<ActDiscountUseType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "act_discount_use_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 优惠使用类型名称(中文)
    */
    public static final String F_DISCOUNT_USE_TYPE_NAME = "discount_use_type_name";
    /**
    * 优惠使用类型名称(英文)
    */
    public static final String F_DISCOUNT_USE_TYPE_NAME_EN = "discount_use_type_name_en";
    /**
    * 优惠使用类型简码
    */
    public static final String F_DISCOUNT_USE_TYPE_CODE = "discount_use_type_code";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_DISCOUNT_USE_TYPE_NAME, null);
        put(F_DISCOUNT_USE_TYPE_NAME_EN, null);
        put(F_DISCOUNT_USE_TYPE_CODE, null);
        put(F_REMARK, null);
    }

    public ActDiscountUseType() {
        super();
    }

    public ActDiscountUseType(Map<String, Object> map) {
        super(map);
    }

    public ActDiscountUseType(String id) {
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
    public ActDiscountUseType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return discount_use_type_name to discountUseTypeName 优惠使用类型名称(中文)<BR/>
    */
    public String getDiscountUseTypeName() {
        return getTypedValue(F_DISCOUNT_USE_TYPE_NAME, String.class);
    }
    /**
    * @param discountUseTypeName to discount_use_type_name 优惠使用类型名称(中文) set
    */
    public ActDiscountUseType setDiscountUseTypeName(String discountUseTypeName) {
        set(F_DISCOUNT_USE_TYPE_NAME, discountUseTypeName);
        return this;
    }
    /**
    * @return discount_use_type_name_en to discountUseTypeNameEn 优惠使用类型名称(英文)<BR/>
    */
    public String getDiscountUseTypeNameEn() {
        return getTypedValue(F_DISCOUNT_USE_TYPE_NAME_EN, String.class);
    }
    /**
    * @param discountUseTypeNameEn to discount_use_type_name_en 优惠使用类型名称(英文) set
    */
    public ActDiscountUseType setDiscountUseTypeNameEn(String discountUseTypeNameEn) {
        set(F_DISCOUNT_USE_TYPE_NAME_EN, discountUseTypeNameEn);
        return this;
    }
    /**
    * @return discount_use_type_code to discountUseTypeCode 优惠使用类型简码<BR/>
    */
    public String getDiscountUseTypeCode() {
        return getTypedValue(F_DISCOUNT_USE_TYPE_CODE, String.class);
    }
    /**
    * @param discountUseTypeCode to discount_use_type_code 优惠使用类型简码 set
    */
    public ActDiscountUseType setDiscountUseTypeCode(String discountUseTypeCode) {
        set(F_DISCOUNT_USE_TYPE_CODE, discountUseTypeCode);
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
    public ActDiscountUseType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ActDiscountUseType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ActDiscountUseType me(){
        return new ActDiscountUseType();
    }

    private static class Mapper implements RowMapper<ActDiscountUseType> {

        private Supplier<ActDiscountUseType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ActDiscountUseType mapRow(ResultSet rs, int rownum) throws SQLException {
            ActDiscountUseType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setDiscountUseTypeName(Utils.toCast(rs.getObject(F_DISCOUNT_USE_TYPE_NAME), String.class));
            bean.setDiscountUseTypeNameEn(Utils.toCast(rs.getObject(F_DISCOUNT_USE_TYPE_NAME_EN), String.class));
            bean.setDiscountUseTypeCode(Utils.toCast(rs.getObject(F_DISCOUNT_USE_TYPE_CODE), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ActDiscountUseType> newMapper(){
        return newMapper(ActDiscountUseType::new);
    }

    public RowMapper<ActDiscountUseType> newMapper(Supplier<ActDiscountUseType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ActDiscountUseType {
        @Override
        public abstract RowMapper<ActDiscountUseType> newMapper();
    }
}
