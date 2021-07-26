package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import java.util.function.Supplier;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        17:43 2020/11/30
* Version:     1.0
* Description: SysCountryProvinceCity实体
*/
@SuppressWarnings("all")
public class SysCountryProvinceCity extends BaseBean<SysCountryProvinceCity> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_country_province_city";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 国家cn
    */
    public static final String F_COUNTRY_CN = "country_cn";
    /**
    * 国家cn
    */
    public static final String F_COUNTRY_EN = "country_en";
    /**
    * 国家(简)
    */
    public static final String F_COUNTRY_SORT = "country_sort";
    /**
    * 省
    */
    public static final String F_PROVINCE = "province";
    /**
    * 省(en)
    */
    public static final String F_PROVINCE_EN = "province_en";
    /**
    * 城市
    */
    public static final String F_CITY = "city";
    /**
    * 城市(en)
    */
    public static final String F_CITY_EN = "city_en";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COUNTRY_CN, null);
        put(F_COUNTRY_EN, null);
        put(F_COUNTRY_SORT, null);
        put(F_PROVINCE, null);
        put(F_PROVINCE_EN, null);
        put(F_CITY, null);
        put(F_CITY_EN, null);
    }

    public SysCountryProvinceCity() {
        super();
    }

    public SysCountryProvinceCity(Map<String, Object> map) {
        super(map);
    }

    public SysCountryProvinceCity(String id) {
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
    public SysCountryProvinceCity setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return country_cn to countryCn 国家cn<BR/>
    */
    public String getCountryCn() {
        return getTypedValue(F_COUNTRY_CN, String.class);
    }
    /**
    * @param countryCn to country_cn 国家cn set
    */
    public SysCountryProvinceCity setCountryCn(String countryCn) {
        set(F_COUNTRY_CN, countryCn);
        return this;
    }
    /**
    * @return country_en to countryEn 国家cn<BR/>
    */
    public String getCountryEn() {
        return getTypedValue(F_COUNTRY_EN, String.class);
    }
    /**
    * @param countryEn to country_en 国家cn set
    */
    public SysCountryProvinceCity setCountryEn(String countryEn) {
        set(F_COUNTRY_EN, countryEn);
        return this;
    }
    /**
    * @return country_sort to countrySort 国家(简)<BR/>
    */
    public String getCountrySort() {
        return getTypedValue(F_COUNTRY_SORT, String.class);
    }
    /**
    * @param countrySort to country_sort 国家(简) set
    */
    public SysCountryProvinceCity setCountrySort(String countrySort) {
        set(F_COUNTRY_SORT, countrySort);
        return this;
    }
    /**
    * @return province to province 省<BR/>
    */
    public String getProvince() {
        return getTypedValue(F_PROVINCE, String.class);
    }
    /**
    * @param province to province 省 set
    */
    public SysCountryProvinceCity setProvince(String province) {
        set(F_PROVINCE, province);
        return this;
    }
    /**
    * @return province_en to provinceEn 省(en)<BR/>
    */
    public String getProvinceEn() {
        return getTypedValue(F_PROVINCE_EN, String.class);
    }
    /**
    * @param provinceEn to province_en 省(en) set
    */
    public SysCountryProvinceCity setProvinceEn(String provinceEn) {
        set(F_PROVINCE_EN, provinceEn);
        return this;
    }
    /**
    * @return city to city 城市<BR/>
    */
    public String getCity() {
        return getTypedValue(F_CITY, String.class);
    }
    /**
    * @param city to city 城市 set
    */
    public SysCountryProvinceCity setCity(String city) {
        set(F_CITY, city);
        return this;
    }
    /**
    * @return city_en to cityEn 城市(en)<BR/>
    */
    public String getCityEn() {
        return getTypedValue(F_CITY_EN, String.class);
    }
    /**
    * @param cityEn to city_en 城市(en) set
    */
    public SysCountryProvinceCity setCityEn(String cityEn) {
        set(F_CITY_EN, cityEn);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysCountryProvinceCity setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysCountryProvinceCity me(){
        return new SysCountryProvinceCity();
    }

    private static class Mapper implements RowMapper<SysCountryProvinceCity> {

        private Supplier<SysCountryProvinceCity> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysCountryProvinceCity mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysCountryProvinceCity bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setCountryCn(Utils.toCast(columnsName.contains(F_COUNTRY_CN) ? rs.getObject(F_COUNTRY_CN) : null, String.class));
            bean.setCountryEn(Utils.toCast(columnsName.contains(F_COUNTRY_EN) ? rs.getObject(F_COUNTRY_EN) : null, String.class));
            bean.setCountrySort(Utils.toCast(columnsName.contains(F_COUNTRY_SORT) ? rs.getObject(F_COUNTRY_SORT) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setProvinceEn(Utils.toCast(columnsName.contains(F_PROVINCE_EN) ? rs.getObject(F_PROVINCE_EN) : null, String.class));
            bean.setCity(Utils.toCast(columnsName.contains(F_CITY) ? rs.getObject(F_CITY) : null, String.class));
            bean.setCityEn(Utils.toCast(columnsName.contains(F_CITY_EN) ? rs.getObject(F_CITY_EN) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysCountryProvinceCity> newMapper(){
        return newMapper(SysCountryProvinceCity::new);
    }

    public RowMapper<SysCountryProvinceCity> newMapper(Supplier<SysCountryProvinceCity> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysCountryProvinceCity {
        @Override
        public abstract RowMapper<SysCountryProvinceCity> newMapper();
    }
}
