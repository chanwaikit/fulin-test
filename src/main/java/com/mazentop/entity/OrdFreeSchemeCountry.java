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
* Date:        11:05 2020/03/23
* Company:     美赞拓
* Version:     1.0
* Description: OrdFreeSchemeCountry实体
*/
@SuppressWarnings("all")
public class OrdFreeSchemeCountry extends BaseBean<OrdFreeSchemeCountry> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_free_scheme_country";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 国家编号
     */
    public static final String F_COUNTRY_ID = "country_id";
    /**
     * 国家名称
     */
    public static final String F_COUNTRY_NAME = "country_name";
    /**
     * 国旗
     */
    public static final String F_FLAG_DATA = "flag_data";
    /**
     * 方案主表编号列表
     */
    public static final String F_FK_SCHEME_ID = "fk_scheme_id";
    /**
     * 方案外部名称列表
     */
    public static final String F_SCHEME_OUTSIDE_NAME = "scheme_outside_name";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COUNTRY_ID, null);
        put(F_COUNTRY_NAME, null);
        put(F_FLAG_DATA, null);
        put(F_FK_SCHEME_ID, null);
        put(F_SCHEME_OUTSIDE_NAME, null);
        put(F_REMARK, null);
    }

    public OrdFreeSchemeCountry() {
        super();
    }

    public OrdFreeSchemeCountry(Map<String, Object> map) {
        super(map);
    }

    public OrdFreeSchemeCountry(String id) {
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
    public OrdFreeSchemeCountry setId(String id) {
        set(F_ID, id);
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
    public OrdFreeSchemeCountry setCountryId(String countryId) {
        set(F_COUNTRY_ID, countryId);
        return this;
    }
    /**
     * @return country_name to countryName 国家名称<BR/>
     */
    public String getCountryName() {
        return getTypedValue(F_COUNTRY_NAME, String.class);
    }
    /**
     * @param countryName to country_name 国家名称 set
     */
    public OrdFreeSchemeCountry setCountryName(String countryName) {
        set(F_COUNTRY_NAME, countryName);
        return this;
    }
    /**
     * @return flag_data to flagData 国旗<BR/>
     */
    public String getFlagData() {
        return getTypedValue(F_FLAG_DATA, String.class);
    }
    /**
     * @param flagData to flag_data 国旗 set
     */
    public OrdFreeSchemeCountry setFlagData(String flagData) {
        set(F_FLAG_DATA, flagData);
        return this;
    }
    /**
     * @return fk_scheme_id to fkSchemeId 方案主表编号列表<BR/>
     */
    public String getFkSchemeId() {
        return getTypedValue(F_FK_SCHEME_ID, String.class);
    }
    /**
     * @param fkSchemeId to fk_scheme_id 方案主表编号列表 set
     */
    public OrdFreeSchemeCountry setFkSchemeId(String fkSchemeId) {
        set(F_FK_SCHEME_ID, fkSchemeId);
        return this;
    }
    /**
     * @return scheme_outside_name to schemeOutsideName 方案外部名称列表<BR/>
     */
    public String getSchemeOutsideName() {
        return getTypedValue(F_SCHEME_OUTSIDE_NAME, String.class);
    }
    /**
     * @param schemeOutsideName to scheme_outside_name 方案外部名称列表 set
     */
    public OrdFreeSchemeCountry setSchemeOutsideName(String schemeOutsideName) {
        set(F_SCHEME_OUTSIDE_NAME, schemeOutsideName);
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
    public OrdFreeSchemeCountry setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdFreeSchemeCountry setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdFreeSchemeCountry me(){
        return new OrdFreeSchemeCountry();
    }

    private static class Mapper implements RowMapper<OrdFreeSchemeCountry> {

        private Supplier<OrdFreeSchemeCountry> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdFreeSchemeCountry mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdFreeSchemeCountry bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCountryId(Utils.toCast(rs.getObject(F_COUNTRY_ID), String.class));
            bean.setCountryName(Utils.toCast(rs.getObject(F_COUNTRY_NAME), String.class));
            bean.setFlagData(Utils.toCast(rs.getObject(F_FLAG_DATA), String.class));
            bean.setFkSchemeId(Utils.toCast(rs.getObject(F_FK_SCHEME_ID), String.class));
            bean.setSchemeOutsideName(Utils.toCast(rs.getObject(F_SCHEME_OUTSIDE_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdFreeSchemeCountry> newMapper(){
        return newMapper(OrdFreeSchemeCountry::new);
    }

    public RowMapper<OrdFreeSchemeCountry> newMapper(Supplier<OrdFreeSchemeCountry> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdFreeSchemeCountry {
        @Override
        public abstract RowMapper<OrdFreeSchemeCountry> newMapper();
    }
}
