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
* Date:        16:09 2020/03/20
* Company:     美赞拓
* Version:     1.0
* Description: SysCountry实体
*/
@SuppressWarnings("all")
public class SysCountry extends BaseBean<SysCountry> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_country";

    /**
    * 主键国家alpha2编码
    */
    public static final String F_ID = "id";
    /**
    * 洲编号
    */
    public static final String F_CONTINENT_ID = "continent_id";
    /**
    * 洲名称
    */
    public static final String F_CONTINENT_NAME = "continent_name";
    /**
    * 洲名称(英文)
    */
    public static final String F_CONTINENT_NAME_EN = "continent_name_en";
    /**
    * 电话区别
    */
    public static final String F_PHONE_AREA_CODE = "phone_area_code";
    /**
    * 税率(百分比的值)
    */
    public static final String F_TAX_RATE = "tax_rate";
    /**
    * 是否开启税率
    */
    public static final String F_IS_OPEN_TAX_RATE = "is_open_tax_rate";
    /**
    * alpha3编码
    */
    public static final String F_ALPHA3 = "alpha3";
    /**
    * 国家名称
    */
    public static final String F_NAME = "name";
    /**
    * 国家中文名称
    */
    public static final String F_NAME_CN = "name_cn";
    /**
    * 数字编码
    */
    public static final String F_NUMERAL = "numeral";
    /**
    * 国旗
    */
    public static final String F_FLAG = "flag";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_CONTINENT_ID, null);
        put(F_CONTINENT_NAME, null);
        put(F_CONTINENT_NAME_EN, null);
        put(F_PHONE_AREA_CODE, null);
        put(F_TAX_RATE, null);
        put(F_IS_OPEN_TAX_RATE, null);
        put(F_ALPHA3, null);
        put(F_NAME, null);
        put(F_NAME_CN, null);
        put(F_NUMERAL, null);
        put(F_FLAG, null);
    }

    public SysCountry() {
        super();
    }

    public SysCountry(Map<String, Object> map) {
        super(map);
    }

    public SysCountry(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 主键国家alpha2编码<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 主键国家alpha2编码 set
    */
    public SysCountry setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return continent_id to continentId 洲编号<BR/>
    */
    public String getContinentId() {
        return getTypedValue(F_CONTINENT_ID, String.class);
    }
    /**
    * @param continentId to continent_id 洲编号 set
    */
    public SysCountry setContinentId(String continentId) {
        set(F_CONTINENT_ID, continentId);
        return this;
    }
    /**
    * @return continent_name to continentName 洲名称<BR/>
    */
    public String getContinentName() {
        return getTypedValue(F_CONTINENT_NAME, String.class);
    }
    /**
    * @param continentName to continent_name 洲名称 set
    */
    public SysCountry setContinentName(String continentName) {
        set(F_CONTINENT_NAME, continentName);
        return this;
    }
    /**
    * @return continent_name_en to continentNameEn 洲名称(英文)<BR/>
    */
    public String getContinentNameEn() {
        return getTypedValue(F_CONTINENT_NAME_EN, String.class);
    }
    /**
    * @param continentNameEn to continent_name_en 洲名称(英文) set
    */
    public SysCountry setContinentNameEn(String continentNameEn) {
        set(F_CONTINENT_NAME_EN, continentNameEn);
        return this;
    }
    /**
    * @return phone_area_code to phoneAreaCode 电话区别<BR/>
    */
    public String getPhoneAreaCode() {
        return getTypedValue(F_PHONE_AREA_CODE, String.class);
    }
    /**
    * @param phoneAreaCode to phone_area_code 电话区别 set
    */
    public SysCountry setPhoneAreaCode(String phoneAreaCode) {
        set(F_PHONE_AREA_CODE, phoneAreaCode);
        return this;
    }
    /**
    * @return tax_rate to taxRate 税率(百分比的值)<BR/>
    */
    public Long getTaxRate() {
        return getTypedValue(F_TAX_RATE, Long.class);
    }
    /**
    * @param taxRate to tax_rate 税率(百分比的值) set
    */
    public SysCountry setTaxRate(Long taxRate) {
        set(F_TAX_RATE, taxRate);
        return this;
    }
    /**
    * @return is_open_tax_rate to isOpenTaxRate 是否开启税率<BR/>
    */
    public Integer getIsOpenTaxRate() {
        return getTypedValue(F_IS_OPEN_TAX_RATE, Integer.class);
    }
    /**
    * @param isOpenTaxRate to is_open_tax_rate 是否开启税率 set
    */
    public SysCountry setIsOpenTaxRate(Integer isOpenTaxRate) {
        set(F_IS_OPEN_TAX_RATE, isOpenTaxRate);
        return this;
    }
    /**
    * @return alpha3 to alpha3 alpha3编码<BR/>
    */
    public String getAlpha3() {
        return getTypedValue(F_ALPHA3, String.class);
    }
    /**
    * @param alpha3 to alpha3 alpha3编码 set
    */
    public SysCountry setAlpha3(String alpha3) {
        set(F_ALPHA3, alpha3);
        return this;
    }
    /**
    * @return name to name 国家名称<BR/>
    */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
    * @param name to name 国家名称 set
    */
    public SysCountry setName(String name) {
        set(F_NAME, name);
        return this;
    }
    /**
    * @return name_cn to nameCn 国家中文名称<BR/>
    */
    public String getNameCn() {
        return getTypedValue(F_NAME_CN, String.class);
    }
    /**
    * @param nameCn to name_cn 国家中文名称 set
    */
    public SysCountry setNameCn(String nameCn) {
        set(F_NAME_CN, nameCn);
        return this;
    }
    /**
    * @return numeral to numeral 数字编码<BR/>
    */
    public Integer getNumeral() {
        return getTypedValue(F_NUMERAL, Integer.class);
    }
    /**
    * @param numeral to numeral 数字编码 set
    */
    public SysCountry setNumeral(Integer numeral) {
        set(F_NUMERAL, numeral);
        return this;
    }
    /**
    * @return flag to flag 国旗<BR/>
    */
    public String getFlag() {
        return getTypedValue(F_FLAG, String.class);
    }
    /**
    * @param flag to flag 国旗 set
    */
    public SysCountry setFlag(String flag) {
        set(F_FLAG, flag);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysCountry setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysCountry me(){
        return new SysCountry();
    }

    private static class Mapper implements RowMapper<SysCountry> {

        private Supplier<SysCountry> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysCountry mapRow(ResultSet rs, int rownum) throws SQLException {
            SysCountry bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setContinentId(Utils.toCast(rs.getObject(F_CONTINENT_ID), String.class));
            bean.setContinentName(Utils.toCast(rs.getObject(F_CONTINENT_NAME), String.class));
            bean.setContinentNameEn(Utils.toCast(rs.getObject(F_CONTINENT_NAME_EN), String.class));
            bean.setPhoneAreaCode(Utils.toCast(rs.getObject(F_PHONE_AREA_CODE), String.class));
            bean.setTaxRate(Utils.toCast(rs.getObject(F_TAX_RATE), Long.class));
            bean.setIsOpenTaxRate(Utils.toCast(rs.getObject(F_IS_OPEN_TAX_RATE), Integer.class));
            bean.setAlpha3(Utils.toCast(rs.getObject(F_ALPHA3), String.class));
            bean.setName(Utils.toCast(rs.getObject(F_NAME), String.class));
            bean.setNameCn(Utils.toCast(rs.getObject(F_NAME_CN), String.class));
            bean.setNumeral(Utils.toCast(rs.getObject(F_NUMERAL), Integer.class));
            bean.setFlag(Utils.toCast(rs.getObject(F_FLAG), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysCountry> newMapper(){
        return newMapper(SysCountry::new);
    }

    public RowMapper<SysCountry> newMapper(Supplier<SysCountry> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysCountry {
        @Override
        public abstract RowMapper<SysCountry> newMapper();
    }
}
