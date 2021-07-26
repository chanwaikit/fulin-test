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
* Date:        16:47 2020/04/24
* Company:     美赞拓
* Version:     1.0
* Description: ProProductShortcut实体
*/
@SuppressWarnings("all")
public class ProProductShortcut extends BaseBean<ProProductShortcut> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_product_shortcut";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 商品主键
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 分类主键
    */
    public static final String F_FK_PRODUCT_TYPE_ID = "fk_product_type_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_FK_PRODUCT_TYPE_ID, null);
    }

    public ProProductShortcut() {
        super();
    }

    public ProProductShortcut(Map<String, Object> map) {
        super(map);
    }

    public ProProductShortcut(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 主键<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 主键 set
    */
    public ProProductShortcut setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_product_id to fkProductId 商品主键<BR/>
    */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
    * @param fkProductId to fk_product_id 商品主键 set
    */
    public ProProductShortcut setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
    * @return fk_product_type_id to fkProductTypeId 分类主键<BR/>
    */
    public String getFkProductTypeId() {
        return getTypedValue(F_FK_PRODUCT_TYPE_ID, String.class);
    }
    /**
    * @param fkProductTypeId to fk_product_type_id 分类主键 set
    */
    public ProProductShortcut setFkProductTypeId(String fkProductTypeId) {
        set(F_FK_PRODUCT_TYPE_ID, fkProductTypeId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductShortcut setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductShortcut me(){
        return new ProProductShortcut();
    }

    private static class Mapper implements RowMapper<ProProductShortcut> {

        private Supplier<ProProductShortcut> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductShortcut mapRow(ResultSet rs, int rownum) throws SQLException {
            ProProductShortcut bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkProductId(Utils.toCast(rs.getObject(F_FK_PRODUCT_ID), String.class));
            bean.setFkProductTypeId(Utils.toCast(rs.getObject(F_FK_PRODUCT_TYPE_ID), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductShortcut> newMapper(){
        return newMapper(ProProductShortcut::new);
    }

    public RowMapper<ProProductShortcut> newMapper(Supplier<ProProductShortcut> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductShortcut {
        @Override
        public abstract RowMapper<ProProductShortcut> newMapper();
    }
}
