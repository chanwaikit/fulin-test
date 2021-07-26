package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        01:08 2020/06/14
* Version:     1.0
* Description: SkinPageLayout实体
*/
@SuppressWarnings("all")
public class SkinPageLayout extends BaseBean<SkinPageLayout> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_page_layout";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 归属模版主键 子级块可为null
    */
    public static final String F_TEMPLATE_ID = "template_id";
    /**
    * 布局编码，确定不可修改
    */
    public static final String F_CODE = "code";
    /**
    * 布局名称
    */
    public static final String F_NAME = "name";
    /**
    * 模版路径
    */
    public static final String F_TEMPLATE_PATH = "template_path";
    /**
    * 最大显示条目 0 表示不限制
    */
    public static final String F_MAX_LIMIT = "max_limit";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TEMPLATE_ID, null);
        put(F_CODE, null);
        put(F_NAME, null);
        put(F_TEMPLATE_PATH, null);
        put(F_MAX_LIMIT, null);
    }

    public SkinPageLayout() {
        super();
    }

    public SkinPageLayout(Map<String, Object> map) {
        super(map);
    }

    public SkinPageLayout(String id) {
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
    public SkinPageLayout setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return template_id to templateId 归属模版主键 子级块可为null<BR/>
    */
    public String getTemplateId() {
        return getTypedValue(F_TEMPLATE_ID, String.class);
    }
    /**
    * @param templateId to template_id 归属模版主键 子级块可为null set
    */
    public SkinPageLayout setTemplateId(String templateId) {
        set(F_TEMPLATE_ID, templateId);
        return this;
    }
    /**
    * @return code to code 布局编码，确定不可修改<BR/>
    */
    public String getCode() {
        return getTypedValue(F_CODE, String.class);
    }
    /**
    * @param code to code 布局编码，确定不可修改 set
    */
    public SkinPageLayout setCode(String code) {
        set(F_CODE, code);
        return this;
    }
    /**
    * @return name to name 布局名称<BR/>
    */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
    * @param name to name 布局名称 set
    */
    public SkinPageLayout setName(String name) {
        set(F_NAME, name);
        return this;
    }
    /**
    * @return template_path to templatePath 模版路径<BR/>
    */
    public String getTemplatePath() {
        return getTypedValue(F_TEMPLATE_PATH, String.class);
    }
    /**
    * @param templatePath to template_path 模版路径 set
    */
    public SkinPageLayout setTemplatePath(String templatePath) {
        set(F_TEMPLATE_PATH, templatePath);
        return this;
    }
    /**
    * @return max_limit to maxLimit 最大显示条目 0 表示不限制<BR/>
    */
    public Integer getMaxLimit() {
        return getTypedValue(F_MAX_LIMIT, Integer.class);
    }
    /**
    * @param maxLimit to max_limit 最大显示条目 0 表示不限制 set
    */
    public SkinPageLayout setMaxLimit(Integer maxLimit) {
        set(F_MAX_LIMIT, maxLimit);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPageLayout setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPageLayout me(){
        return new SkinPageLayout();
    }

    private static class Mapper implements RowMapper<SkinPageLayout> {

        private Supplier<SkinPageLayout> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPageLayout mapRow(ResultSet rs, int rownum) throws SQLException {
            SkinPageLayout bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setTemplateId(Utils.toCast(rs.getObject(F_TEMPLATE_ID), String.class));
            bean.setCode(Utils.toCast(rs.getObject(F_CODE), String.class));
            bean.setName(Utils.toCast(rs.getObject(F_NAME), String.class));
            bean.setTemplatePath(Utils.toCast(rs.getObject(F_TEMPLATE_PATH), String.class));
            bean.setMaxLimit(Utils.toCast(rs.getObject(F_MAX_LIMIT), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPageLayout> newMapper(){
        return newMapper(SkinPageLayout::new);
    }

    public RowMapper<SkinPageLayout> newMapper(Supplier<SkinPageLayout> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPageLayout {
        @Override
        public abstract RowMapper<SkinPageLayout> newMapper();
    }
}
