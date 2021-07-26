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
* Date:        10:16 2020/04/15
* Company:     美赞拓
* Version:     1.0
* Description: OrdFreeSchemeCity实体
*/
@SuppressWarnings("all")
public class OrdFreeSchemeCity extends BaseBean<OrdFreeSchemeCity> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_free_scheme_city";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 物流国家编号
     */
    public static final String F_FK_SCHEME_COUNT_ID = "fk_scheme_count_id";
    /**
     * 省/市编号
     */
    public static final String F_FK_PROVINCE_CITY_ID = "fk_province_city_id";
    /**
     * 国家编号
     */
    public static final String F_COUNTRY_ID = "country_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_SCHEME_COUNT_ID, null);
        put(F_FK_PROVINCE_CITY_ID, null);
        put(F_COUNTRY_ID, null);
    }

    public OrdFreeSchemeCity() {
        super();
    }

    public OrdFreeSchemeCity(Map<String, Object> map) {
        super(map);
    }

    public OrdFreeSchemeCity(String id) {
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
    public OrdFreeSchemeCity setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_scheme_count_id to fkSchemeCountId 物流国家编号<BR/>
     */
    public String getFkSchemeCountId() {
        return getTypedValue(F_FK_SCHEME_COUNT_ID, String.class);
    }
    /**
     * @param fkSchemeCountId to fk_scheme_count_id 物流国家编号 set
     */
    public OrdFreeSchemeCity setFkSchemeCountId(String fkSchemeCountId) {
        set(F_FK_SCHEME_COUNT_ID, fkSchemeCountId);
        return this;
    }
    /**
     * @return fk_province_city_id to fkProvinceCityId 省/市编号<BR/>
     */
    public String getFkProvinceCityId() {
        return getTypedValue(F_FK_PROVINCE_CITY_ID, String.class);
    }
    /**
     * @param fkProvinceCityId to fk_province_city_id 省/市编号 set
     */
    public OrdFreeSchemeCity setFkProvinceCityId(String fkProvinceCityId) {
        set(F_FK_PROVINCE_CITY_ID, fkProvinceCityId);
        return this;
    }
    /**
     * @return country_id to countryId 国家编号<BR/>
     */
    public String getCountryId() {
        return getTypedValue(F_COUNTRY_ID, String.class);
    }
    /**
     * @param countryId to country_id 国家编号 set
     */
    public OrdFreeSchemeCity setCountryId(String countryId) {
        set(F_COUNTRY_ID, countryId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdFreeSchemeCity setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdFreeSchemeCity me(){
        return new OrdFreeSchemeCity();
    }

    private static class Mapper implements RowMapper<OrdFreeSchemeCity> {

        private Supplier<OrdFreeSchemeCity> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdFreeSchemeCity mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdFreeSchemeCity bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkSchemeCountId(Utils.toCast(rs.getObject(F_FK_SCHEME_COUNT_ID), String.class));
            bean.setFkProvinceCityId(Utils.toCast(rs.getObject(F_FK_PROVINCE_CITY_ID), String.class));
            bean.setCountryId(Utils.toCast(rs.getObject(F_COUNTRY_ID), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdFreeSchemeCity> newMapper(){
        return newMapper(OrdFreeSchemeCity::new);
    }

    public RowMapper<OrdFreeSchemeCity> newMapper(Supplier<OrdFreeSchemeCity> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdFreeSchemeCity {
        @Override
        public abstract RowMapper<OrdFreeSchemeCity> newMapper();
    }
}
