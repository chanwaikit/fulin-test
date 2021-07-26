package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        10:14 2020/05/12
* Company:     美赞拓
* Version:     1.0
* Description: ProReportProduct实体
*/
@SuppressWarnings("all")
public class ProReportProduct extends BaseBean<ProReportProduct> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_report_product";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 商品id
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 购买用户
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 类型(1热卖商品,2加购商品)
    */
    public static final String F_TYPE = "type";
    /**
    * 商品数量
    */
    public static final String F_QUANTITY = "quantity";
    /**
    * 地区
    */
    public static final String F_ADDRESS = "address";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_TITLE, null);
        put(F_TYPE, null);
        put(F_QUANTITY, null);
        put(F_ADDRESS, null);
        put(F_ADD_TIME, null);
    }

    public ProReportProduct() {
        super();
    }

    public ProReportProduct(Map<String, Object> map) {
        super(map);
    }

    public ProReportProduct(String id) {
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
    public ProReportProduct setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_product_id to fkProductId 商品id<BR/>
    */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
    * @param fkProductId to fk_product_id 商品id set
    */
    public ProReportProduct setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 购买用户<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 购买用户 set
    */
    public ProReportProduct setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return title to title 标题<BR/>
    */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
    * @param title to title 标题 set
    */
    public ProReportProduct setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return type to type 类型(1热卖商品,2加购商品)<BR/>
    */
    public Integer getType() {
        return getTypedValue(F_TYPE, Integer.class);
    }
    /**
    * @param type to type 类型(1热卖商品,2加购商品) set
    */
    public ProReportProduct setType(Integer type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return quantity to quantity 商品数量<BR/>
    */
    public Integer getQuantity() {
        return getTypedValue(F_QUANTITY, Integer.class);
    }
    /**
    * @param quantity to quantity 商品数量 set
    */
    public ProReportProduct setQuantity(Integer quantity) {
        set(F_QUANTITY, quantity);
        return this;
    }
    /**
    * @return address to address 地区<BR/>
    */
    public String getAddress() {
        return getTypedValue(F_ADDRESS, String.class);
    }
    /**
    * @param address to address 地区 set
    */
    public ProReportProduct setAddress(String address) {
        set(F_ADDRESS, address);
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
    public ProReportProduct setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProReportProduct setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProReportProduct me(){
        return new ProReportProduct();
    }

    private static class Mapper implements RowMapper<ProReportProduct> {

        private Supplier<ProReportProduct> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProReportProduct mapRow(ResultSet rs, int rownum) throws SQLException {
            ProReportProduct bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkProductId(Utils.toCast(rs.getObject(F_FK_PRODUCT_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), Integer.class));
            bean.setQuantity(Utils.toCast(rs.getObject(F_QUANTITY), Integer.class));
            bean.setAddress(Utils.toCast(rs.getObject(F_ADDRESS), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProReportProduct> newMapper(){
        return newMapper(ProReportProduct::new);
    }

    public RowMapper<ProReportProduct> newMapper(Supplier<ProReportProduct> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProReportProduct {
        @Override
        public abstract RowMapper<ProReportProduct> newMapper();
    }
}
