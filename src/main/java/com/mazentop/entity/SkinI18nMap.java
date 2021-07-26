package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:49 2020/06/30
* Version:     1.0
* Description: SkinI18nMap实体
*/
@SuppressWarnings("all")
public class SkinI18nMap extends BaseBean<SkinI18nMap> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_i18n_map";

    /**
    * 主键 
    */
    public static final String F_ID = "id";
    /**
    * i18n主表主键
    */
    public static final String F_I18N_ID = "i18n_id";
    /**
    * 页面
    */
    public static final String F_PAGE = "page";
    /**
    * 语言编码
    */
    public static final String F_LANG_CODE = "lang_code";
    /**
    * 编码key
    */
    public static final String F_I18N_KEY = "i18n_key";
    /**
    *  编码映射值
    */
    public static final String F_I18N_VALUE = "i18n_value";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_I18N_ID, null);
        put(F_PAGE, null);
        put(F_LANG_CODE, null);
        put(F_I18N_KEY, null);
        put(F_I18N_VALUE, null);
    }

    public SkinI18nMap() {
        super();
    }

    public SkinI18nMap(Map<String, Object> map) {
        super(map);
    }

    public SkinI18nMap(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 主键 <BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 主键  set
    */
    public SkinI18nMap setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return i18n_id to i18nId i18n主表主键<BR/>
    */
    public String getI18nId() {
        return getTypedValue(F_I18N_ID, String.class);
    }
    /**
    * @param i18nId to i18n_id i18n主表主键 set
    */
    public SkinI18nMap setI18nId(String i18nId) {
        set(F_I18N_ID, i18nId);
        return this;
    }
    /**
    * @return page to page 页面<BR/>
    */
    public String getPage() {
        return getTypedValue(F_PAGE, String.class);
    }
    /**
    * @param page to page 页面 set
    */
    public SkinI18nMap setPage(String page) {
        set(F_PAGE, page);
        return this;
    }
    /**
    * @return lang_code to langCode 语言编码<BR/>
    */
    public String getLangCode() {
        return getTypedValue(F_LANG_CODE, String.class);
    }
    /**
    * @param langCode to lang_code 语言编码 set
    */
    public SkinI18nMap setLangCode(String langCode) {
        set(F_LANG_CODE, langCode);
        return this;
    }
    /**
    * @return i18n_key to i18nKey 编码key<BR/>
    */
    public String getI18nKey() {
        return getTypedValue(F_I18N_KEY, String.class);
    }
    /**
    * @param i18nKey to i18n_key 编码key set
    */
    public SkinI18nMap setI18nKey(String i18nKey) {
        set(F_I18N_KEY, i18nKey);
        return this;
    }
    /**
    * @return i18n_value to i18nValue  编码映射值<BR/>
    */
    public String getI18nValue() {
        return getTypedValue(F_I18N_VALUE, String.class);
    }
    /**
    * @param i18nValue to i18n_value  编码映射值 set
    */
    public SkinI18nMap setI18nValue(String i18nValue) {
        set(F_I18N_VALUE, i18nValue);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinI18nMap setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinI18nMap me(){
        return new SkinI18nMap();
    }

    private static class Mapper implements RowMapper<SkinI18nMap> {

        private Supplier<SkinI18nMap> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinI18nMap mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinI18nMap bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setI18nId(Utils.toCast(columnsName.contains(F_I18N_ID) ? rs.getObject(F_I18N_ID) : null, String.class));
            bean.setPage(Utils.toCast(columnsName.contains(F_PAGE) ? rs.getObject(F_PAGE) : null, String.class));
            bean.setLangCode(Utils.toCast(columnsName.contains(F_LANG_CODE) ? rs.getObject(F_LANG_CODE) : null, String.class));
            bean.setI18nKey(Utils.toCast(columnsName.contains(F_I18N_KEY) ? rs.getObject(F_I18N_KEY) : null, String.class));
            bean.setI18nValue(Utils.toCast(columnsName.contains(F_I18N_VALUE) ? rs.getObject(F_I18N_VALUE) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinI18nMap> newMapper(){
        return newMapper(SkinI18nMap::new);
    }

    public RowMapper<SkinI18nMap> newMapper(Supplier<SkinI18nMap> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinI18nMap {
        @Override
        public abstract RowMapper<SkinI18nMap> newMapper();
    }
}
