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
* Date:        14:38 2020/03/30
* Company:     美赞拓
* Version:     1.0
* Description: SysTemplate实体
*/
@SuppressWarnings("all")
public class SysTemplate extends BaseBean<SysTemplate> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_template";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 模板名称
    */
    public static final String F_TEMPLATE_NAME = "template_name";
    /**
    * 模板路径
    */
    public static final String F_TEMPLATE_PATH = "template_path";
    /**
    * 是否启用 0.否1.是
    */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TEMPLATE_NAME, null);
        put(F_TEMPLATE_PATH, null);
        put(F_IS_ENABLE, null);
    }

    public SysTemplate() {
        super();
    }

    public SysTemplate(Map<String, Object> map) {
        super(map);
    }

    public SysTemplate(String id) {
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
    public SysTemplate setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return template_name to templateName 模板名称<BR/>
    */
    public String getTemplateName() {
        return getTypedValue(F_TEMPLATE_NAME, String.class);
    }
    /**
    * @param templateName to template_name 模板名称 set
    */
    public SysTemplate setTemplateName(String templateName) {
        set(F_TEMPLATE_NAME, templateName);
        return this;
    }
    /**
    * @return template_path to templatePath 模板路径<BR/>
    */
    public String getTemplatePath() {
        return getTypedValue(F_TEMPLATE_PATH, String.class);
    }
    /**
    * @param templatePath to template_path 模板路径 set
    */
    public SysTemplate setTemplatePath(String templatePath) {
        set(F_TEMPLATE_PATH, templatePath);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启用 0.否1.是<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启用 0.否1.是 set
    */
    public SysTemplate setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysTemplate setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysTemplate me(){
        return new SysTemplate();
    }

    private static class Mapper implements RowMapper<SysTemplate> {

        private Supplier<SysTemplate> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysTemplate mapRow(ResultSet rs, int rownum) throws SQLException {
            SysTemplate bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setTemplateName(Utils.toCast(rs.getObject(F_TEMPLATE_NAME), String.class));
            bean.setTemplatePath(Utils.toCast(rs.getObject(F_TEMPLATE_PATH), String.class));
            bean.setIsEnable(Utils.toCast(rs.getObject(F_IS_ENABLE), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysTemplate> newMapper(){
        return newMapper(SysTemplate::new);
    }

    public RowMapper<SysTemplate> newMapper(Supplier<SysTemplate> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysTemplate {
        @Override
        public abstract RowMapper<SysTemplate> newMapper();
    }
}
