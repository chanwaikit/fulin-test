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
* Date:        17:42 2021/05/24
* Version:     1.0
* Description: SkinCountry实体
*/
@SuppressWarnings("all")
public class SkinCountry extends BaseBean<SkinCountry> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_country";

    /**
    * 主键ID
    */
    public static final String F_ID = "id";
    /**
    * 国家名称
    */
    public static final String F_COUNTRY_NAME = "country_name";
    /**
    * 国家名称_中文
    */
    public static final String F_COUNTRY_NAME_CN = "country_name_cn";
    /**
    * 国家编码alpha编码
    */
    public static final String F_COUNTRY_CODE = "country_code";
    /**
    * 货币单位
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 国旗
    */
    public static final String F_FLAG = "flag";
    /**
    * 是否启用
    */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COUNTRY_NAME, null);
        put(F_COUNTRY_NAME_CN, null);
        put(F_COUNTRY_CODE, null);
        put(F_CURRENCY, null);
        put(F_FLAG, null);
        put(F_IS_ENABLE, null);
    }

    public SkinCountry() {
        super();
    }

    public SkinCountry(Map<String, Object> map) {
        super(map);
    }

    public SkinCountry(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 主键ID<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 主键ID set
    */
    public SkinCountry setId(String id) {
        set(F_ID, id);
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
    public SkinCountry setCountryName(String countryName) {
        set(F_COUNTRY_NAME, countryName);
        return this;
    }
    /**
    * @return country_name_cn to countryNameCn 国家名称_中文<BR/>
    */
    public String getCountryNameCn() {
        return getTypedValue(F_COUNTRY_NAME_CN, String.class);
    }
    /**
    * @param countryNameCn to country_name_cn 国家名称_中文 set
    */
    public SkinCountry setCountryNameCn(String countryNameCn) {
        set(F_COUNTRY_NAME_CN, countryNameCn);
        return this;
    }
    /**
    * @return country_code to countryCode 国家编码alpha编码<BR/>
    */
    public String getCountryCode() {
        return getTypedValue(F_COUNTRY_CODE, String.class);
    }
    /**
    * @param countryCode to country_code 国家编码alpha编码 set
    */
    public SkinCountry setCountryCode(String countryCode) {
        set(F_COUNTRY_CODE, countryCode);
        return this;
    }
    /**
    * @return currency to currency 货币单位<BR/>
    */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
    * @param currency to currency 货币单位 set
    */
    public SkinCountry setCurrency(String currency) {
        set(F_CURRENCY, currency);
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
    public SkinCountry setFlag(String flag) {
        set(F_FLAG, flag);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启用<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启用 set
    */
    public SkinCountry setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinCountry setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinCountry me(){
        return new SkinCountry();
    }

    private static class Mapper implements RowMapper<SkinCountry> {

        private Supplier<SkinCountry> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinCountry mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinCountry bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setCountryName(Utils.toCast(columnsName.contains(F_COUNTRY_NAME) ? rs.getObject(F_COUNTRY_NAME) : null, String.class));
            bean.setCountryNameCn(Utils.toCast(columnsName.contains(F_COUNTRY_NAME_CN) ? rs.getObject(F_COUNTRY_NAME_CN) : null, String.class));
            bean.setCountryCode(Utils.toCast(columnsName.contains(F_COUNTRY_CODE) ? rs.getObject(F_COUNTRY_CODE) : null, String.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setFlag(Utils.toCast(columnsName.contains(F_FLAG) ? rs.getObject(F_FLAG) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinCountry> newMapper(){
        return newMapper(SkinCountry::new);
    }

    public RowMapper<SkinCountry> newMapper(Supplier<SkinCountry> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinCountry {
        @Override
        public abstract RowMapper<SkinCountry> newMapper();
    }
}
