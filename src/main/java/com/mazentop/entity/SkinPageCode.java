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
* Date:        11:26 2021/01/20
* Version:     1.0
* Description: SkinPageCode实体
*/
@SuppressWarnings("all")
public class SkinPageCode extends BaseBean<SkinPageCode> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_page_code";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 代码块标题
    */
    public static final String F_TITLE = "title";
    /**
    * js 脚本代码
    */
    public static final String F_CONTENT = "content";
    /**
    * 是否启用
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 代码插入位置 head, body
    */
    public static final String F_CODE_IN = "code_in";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TITLE, null);
        put(F_CONTENT, null);
        put(F_IS_ENABLE, null);
        put(F_CODE_IN, null);
        put(F_ADD_TIME, null);
    }

    public SkinPageCode() {
        super();
    }

    public SkinPageCode(Map<String, Object> map) {
        super(map);
    }

    public SkinPageCode(String id) {
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
    public SkinPageCode setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return title to title 代码块标题<BR/>
    */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
    * @param title to title 代码块标题 set
    */
    public SkinPageCode setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return content to content js 脚本代码<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content js 脚本代码 set
    */
    public SkinPageCode setContent(String content) {
        set(F_CONTENT, content);
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
    public SkinPageCode setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return code_in to codeIn 代码插入位置 head, body<BR/>
    */
    public String getCodeIn() {
        return getTypedValue(F_CODE_IN, String.class);
    }
    /**
    * @param codeIn to code_in 代码插入位置 head, body set
    */
    public SkinPageCode setCodeIn(String codeIn) {
        set(F_CODE_IN, codeIn);
        return this;
    }
    /**
    * @return add_time to addTime 添加时间<BR/>
    */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
    * @param addTime to add_time 添加时间 set
    */
    public SkinPageCode setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPageCode setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPageCode me(){
        return new SkinPageCode();
    }

    private static class Mapper implements RowMapper<SkinPageCode> {

        private Supplier<SkinPageCode> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPageCode mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinPageCode bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setCodeIn(Utils.toCast(columnsName.contains(F_CODE_IN) ? rs.getObject(F_CODE_IN) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPageCode> newMapper(){
        return newMapper(SkinPageCode::new);
    }

    public RowMapper<SkinPageCode> newMapper(Supplier<SkinPageCode> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPageCode {
        @Override
        public abstract RowMapper<SkinPageCode> newMapper();
    }
}
