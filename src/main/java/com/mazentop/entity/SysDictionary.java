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
* Author:      deny
* Mail:        dengy@mazentop.com
* Date:        16:27 2020/07/07
* Version:     1.0
* Description: SysDictionary实体
*/
@SuppressWarnings("all")
public class SysDictionary extends BaseBean<SysDictionary> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_dictionary";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 名称
    */
    public static final String F_NAME = "name";
    /**
    * 编码
    */
    public static final String F_CODE = "code";
    /**
    * 上级编码 顶级root
    */
    public static final String F_PCODE = "pcode";
    /**
    * 数据词典是否启用 1启用 0作废 默认启用
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 排序
    */
    public static final String F_SORTING = "sorting";
    /**
    * 数据类型
    */
    public static final String F_TYPE = "type";
    /**
    * 词典内容
    */
    public static final String F_VALUE = "value";
    /**
    * 是否默认 1 (默认) 0 （非默认）
    */
    public static final String F_IS_DEFAULT = "is_default";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_NAME, null);
        put(F_CODE, null);
        put(F_PCODE, null);
        put(F_IS_ENABLE, null);
        put(F_SORTING, null);
        put(F_TYPE, null);
        put(F_VALUE, null);
        put(F_IS_DEFAULT, null);
    }

    public SysDictionary() {
        super();
    }

    public SysDictionary(Map<String, Object> map) {
        super(map);
    }

    public SysDictionary(String id) {
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
    public SysDictionary setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return name to name 名称<BR/>
    */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
    * @param name to name 名称 set
    */
    public SysDictionary setName(String name) {
        set(F_NAME, name);
        return this;
    }
    /**
    * @return code to code 编码<BR/>
    */
    public String getCode() {
        return getTypedValue(F_CODE, String.class);
    }
    /**
    * @param code to code 编码 set
    */
    public SysDictionary setCode(String code) {
        set(F_CODE, code);
        return this;
    }
    /**
    * @return pcode to pcode 上级编码 顶级root<BR/>
    */
    public String getPcode() {
        return getTypedValue(F_PCODE, String.class);
    }
    /**
    * @param pcode to pcode 上级编码 顶级root set
    */
    public SysDictionary setPcode(String pcode) {
        set(F_PCODE, pcode);
        return this;
    }
    /**
    * @return is_enable to isEnable 数据词典是否启用 1启用 0作废 默认启用<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 数据词典是否启用 1启用 0作废 默认启用 set
    */
    public SysDictionary setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return sorting to sorting 排序<BR/>
    */
    public Integer getSorting() {
        return getTypedValue(F_SORTING, Integer.class);
    }
    /**
    * @param sorting to sorting 排序 set
    */
    public SysDictionary setSorting(Integer sorting) {
        set(F_SORTING, sorting);
        return this;
    }
    /**
    * @return type to type 数据类型<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 数据类型 set
    */
    public SysDictionary setType(String type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return value to value 词典内容<BR/>
    */
    public String getValue() {
        return getTypedValue(F_VALUE, String.class);
    }
    /**
    * @param value to value 词典内容 set
    */
    public SysDictionary setValue(String value) {
        set(F_VALUE, value);
        return this;
    }
    /**
    * @return is_default to isDefault 是否默认 1 (默认) 0 （非默认）<BR/>
    */
    public Integer getIsDefault() {
        return getTypedValue(F_IS_DEFAULT, Integer.class);
    }
    /**
    * @param isDefault to is_default 是否默认 1 (默认) 0 （非默认） set
    */
    public SysDictionary setIsDefault(Integer isDefault) {
        set(F_IS_DEFAULT, isDefault);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysDictionary setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysDictionary me(){
        return new SysDictionary();
    }

    private static class Mapper implements RowMapper<SysDictionary> {

        private Supplier<SysDictionary> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysDictionary mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysDictionary bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setName(Utils.toCast(columnsName.contains(F_NAME) ? rs.getObject(F_NAME) : null, String.class));
            bean.setCode(Utils.toCast(columnsName.contains(F_CODE) ? rs.getObject(F_CODE) : null, String.class));
            bean.setPcode(Utils.toCast(columnsName.contains(F_PCODE) ? rs.getObject(F_PCODE) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setSorting(Utils.toCast(columnsName.contains(F_SORTING) ? rs.getObject(F_SORTING) : null, Integer.class));
            bean.setType(Utils.toCast(columnsName.contains(F_TYPE) ? rs.getObject(F_TYPE) : null, String.class));
            bean.setValue(Utils.toCast(columnsName.contains(F_VALUE) ? rs.getObject(F_VALUE) : null, String.class));
            bean.setIsDefault(Utils.toCast(columnsName.contains(F_IS_DEFAULT) ? rs.getObject(F_IS_DEFAULT) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysDictionary> newMapper(){
        return newMapper(SysDictionary::new);
    }

    public RowMapper<SysDictionary> newMapper(Supplier<SysDictionary> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysDictionary {
        @Override
        public abstract RowMapper<SysDictionary> newMapper();
    }
}
