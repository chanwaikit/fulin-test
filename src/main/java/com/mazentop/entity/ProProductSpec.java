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
* Date:        18:07 2020/03/12
* Company:     美赞拓
* Version:     1.0
* Description: ProProductSpec实体
*/
@SuppressWarnings("all")
public class ProProductSpec extends BaseBean<ProProductSpec> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "pro_product_spec";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 父级编号
     */
    public static final String F_PARENT_ID = "parent_id";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品规格名称
     */
    public static final String F_SPEC_NAME = "spec_name";
    /**
     * 商品规格值列表
     */
    public static final String F_SPEC_VALUE = "spec_value";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PARENT_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_SPEC_NAME, null);
        put(F_SPEC_VALUE, null);
    }

    public ProProductSpec() {
        super();
    }

    public ProProductSpec(Map<String, Object> map) {
        super(map);
    }

    public ProProductSpec(String id) {
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
    public ProProductSpec setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return parent_id to parentId 父级编号<BR/>
     */
    public String getParentId() {
        return getTypedValue(F_PARENT_ID, String.class);
    }
    /**
     * @param parentId to parent_id 父级编号 set
     */
    public ProProductSpec setParentId(String parentId) {
        set(F_PARENT_ID, parentId);
        return this;
    }
    /**
     * @return fk_product_id to fkProductId 商品编号<BR/>
     */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
     * @param fkProductId to fk_product_id 商品编号 set
     */
    public ProProductSpec setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
     * @return spec_name to specName 商品规格名称<BR/>
     */
    public String getSpecName() {
        return getTypedValue(F_SPEC_NAME, String.class);
    }
    /**
     * @param specName to spec_name 商品规格名称 set
     */
    public ProProductSpec setSpecName(String specName) {
        set(F_SPEC_NAME, specName);
        return this;
    }
    /**
     * @return spec_value to specValue 商品规格值列表<BR/>
     */
    public String getSpecValue() {
        return getTypedValue(F_SPEC_VALUE, String.class);
    }
    /**
     * @param specValue to spec_value 商品规格值列表 set
     */
    public ProProductSpec setSpecValue(String specValue) {
        set(F_SPEC_VALUE, specValue);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductSpec setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductSpec me(){
        return new ProProductSpec();
    }

    private static class Mapper implements RowMapper<ProProductSpec> {

        private Supplier<ProProductSpec> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductSpec mapRow(ResultSet rs, int rownum) throws SQLException {
            ProProductSpec bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setParentId(Utils.toCast(rs.getObject(F_PARENT_ID), String.class));
            bean.setFkProductId(Utils.toCast(rs.getObject(F_FK_PRODUCT_ID), String.class));
            bean.setSpecName(Utils.toCast(rs.getObject(F_SPEC_NAME), String.class));
            bean.setSpecValue(Utils.toCast(rs.getObject(F_SPEC_VALUE), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductSpec> newMapper(){
        return newMapper(ProProductSpec::new);
    }

    public RowMapper<ProProductSpec> newMapper(Supplier<ProProductSpec> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductSpec {
        @Override
        public abstract RowMapper<ProProductSpec> newMapper();
    }
}
