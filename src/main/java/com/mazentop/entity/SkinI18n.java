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
* Date:        09:41 2020/06/30
* Version:     1.0
* Description: SkinI18n实体
*/
@SuppressWarnings("all")
public class SkinI18n extends BaseBean<SkinI18n> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_i18n";

    /**
    * 主键 
    */
    public static final String F_ID = "id";
    /**
    * 语言标题
    */
    public static final String F_LANG_TITLE = "lang_title";
    /**
    * 语言编码
    */
    public static final String F_LANG_CODE = "lang_code";
    /**
    * 归属模版主键
    */
    public static final String F_TEMPLATE_ID = "template_id";
    /**
    * 是否启用 0 否 1是
    */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_LANG_TITLE, null);
        put(F_LANG_CODE, null);
        put(F_TEMPLATE_ID, null);
        put(F_IS_ENABLE, null);
    }

    public SkinI18n() {
        super();
    }

    public SkinI18n(Map<String, Object> map) {
        super(map);
    }

    public SkinI18n(String id) {
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
    public SkinI18n setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return lang_title to langTitle 语言标题<BR/>
    */
    public String getLangTitle() {
        return getTypedValue(F_LANG_TITLE, String.class);
    }
    /**
    * @param langTitle to lang_title 语言标题 set
    */
    public SkinI18n setLangTitle(String langTitle) {
        set(F_LANG_TITLE, langTitle);
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
    public SkinI18n setLangCode(String langCode) {
        set(F_LANG_CODE, langCode);
        return this;
    }
    /**
    * @return template_id to templateId 归属模版主键<BR/>
    */
    public String getTemplateId() {
        return getTypedValue(F_TEMPLATE_ID, String.class);
    }
    /**
    * @param templateId to template_id 归属模版主键 set
    */
    public SkinI18n setTemplateId(String templateId) {
        set(F_TEMPLATE_ID, templateId);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启用 0 否 1是<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启用 0 否 1是 set
    */
    public SkinI18n setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinI18n setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinI18n me(){
        return new SkinI18n();
    }

    private static class Mapper implements RowMapper<SkinI18n> {

        private Supplier<SkinI18n> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinI18n mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinI18n bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setLangTitle(Utils.toCast(columnsName.contains(F_LANG_TITLE) ? rs.getObject(F_LANG_TITLE) : null, String.class));
            bean.setLangCode(Utils.toCast(columnsName.contains(F_LANG_CODE) ? rs.getObject(F_LANG_CODE) : null, String.class));
            bean.setTemplateId(Utils.toCast(columnsName.contains(F_TEMPLATE_ID) ? rs.getObject(F_TEMPLATE_ID) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinI18n> newMapper(){
        return newMapper(SkinI18n::new);
    }

    public RowMapper<SkinI18n> newMapper(Supplier<SkinI18n> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinI18n {
        @Override
        public abstract RowMapper<SkinI18n> newMapper();
    }
}
