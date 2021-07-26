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
* Date:        19:24 2020/03/19
* Company:     美赞拓
* Version:     1.0
* Description: SysOptions实体
*/
@SuppressWarnings("all")
public class SysOptions extends BaseBean<SysOptions> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_options";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 公司编号
    */
    public static final String F_COMPANY_ID = "company_id";
    /**
    * 公司名称
    */
    public static final String F_COMPANY_NAME = "company_name";
    /**
    * 配置key
    */
    public static final String F_OPTION_KEY = "option_key";
    /**
    * 配置value
    */
    public static final String F_OPTION_VALUE = "option_value";
    /**
    * 字段说明
    */
    public static final String F_CONTENT = "content";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_OPTION_KEY, null);
        put(F_OPTION_VALUE, null);
        put(F_CONTENT, null);
    }

    public SysOptions() {
        super();
    }

    public SysOptions(Map<String, Object> map) {
        super(map);
    }

    public SysOptions(String id) {
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
    public SysOptions setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return company_id to companyId 公司编号<BR/>
    */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
    * @param companyId to company_id 公司编号 set
    */
    public SysOptions setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
    * @return company_name to companyName 公司名称<BR/>
    */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
    * @param companyName to company_name 公司名称 set
    */
    public SysOptions setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
    * @return option_key to optionKey 配置key<BR/>
    */
    public String getOptionKey() {
        return getTypedValue(F_OPTION_KEY, String.class);
    }
    /**
    * @param optionKey to option_key 配置key set
    */
    public SysOptions setOptionKey(String optionKey) {
        set(F_OPTION_KEY, optionKey);
        return this;
    }
    /**
    * @return option_value to optionValue 配置value<BR/>
    */
    public String getOptionValue() {
        return getTypedValue(F_OPTION_VALUE, String.class);
    }
    /**
    * @param optionValue to option_value 配置value set
    */
    public SysOptions setOptionValue(String optionValue) {
        set(F_OPTION_VALUE, optionValue);
        return this;
    }
    /**
    * @return content to content 字段说明<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content 字段说明 set
    */
    public SysOptions setContent(String content) {
        set(F_CONTENT, content);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysOptions setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysOptions me(){
        return new SysOptions();
    }

    private static class Mapper implements RowMapper<SysOptions> {

        private Supplier<SysOptions> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysOptions mapRow(ResultSet rs, int rownum) throws SQLException {
            SysOptions bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setOptionKey(Utils.toCast(rs.getObject(F_OPTION_KEY), String.class));
            bean.setOptionValue(Utils.toCast(rs.getObject(F_OPTION_VALUE), String.class));
            bean.setContent(Utils.toCast(rs.getObject(F_CONTENT), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysOptions> newMapper(){
        return newMapper(SysOptions::new);
    }

    public RowMapper<SysOptions> newMapper(Supplier<SysOptions> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysOptions {
        @Override
        public abstract RowMapper<SysOptions> newMapper();
    }
}
