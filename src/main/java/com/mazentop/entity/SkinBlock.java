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
* Date:        15:53 2021/04/16
* Version:     1.0
* Description: SkinBlock实体
*/
@SuppressWarnings("all")
public class SkinBlock extends BaseBean<SkinBlock> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_block";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * key
    */
    public static final String F_QUOTE = "quote";
    /**
    * 类型
    */
    public static final String F_TYPE = "type";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 副标题
    */
    public static final String F_SUB_TITLE = "sub_title";
    /**
    * 内容json
    */
    public static final String F_CONTENT = "content";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_QUOTE, null);
        put(F_TYPE, null);
        put(F_TITLE, null);
        put(F_SUB_TITLE, null);
        put(F_CONTENT, null);
        put(F_ADD_TIME, null);
    }

    public SkinBlock() {
        super();
    }

    public SkinBlock(Map<String, Object> map) {
        super(map);
    }

    public SkinBlock(String id) {
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
    public SkinBlock setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return quote to quote key<BR/>
    */
    public String getQuote() {
        return getTypedValue(F_QUOTE, String.class);
    }
    /**
    * @param quote to quote key set
    */
    public SkinBlock setQuote(String quote) {
        set(F_QUOTE, quote);
        return this;
    }
    /**
    * @return type to type 类型<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 类型 set
    */
    public SkinBlock setType(String type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return title to title 标题<BR/>
    */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
    * @param title to title 标题 set
    */
    public SkinBlock setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return sub_title to subTitle 副标题<BR/>
    */
    public String getSubTitle() {
        return getTypedValue(F_SUB_TITLE, String.class);
    }
    /**
    * @param subTitle to sub_title 副标题 set
    */
    public SkinBlock setSubTitle(String subTitle) {
        set(F_SUB_TITLE, subTitle);
        return this;
    }
    /**
    * @return content to content 内容json<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content 内容json set
    */
    public SkinBlock setContent(String content) {
        set(F_CONTENT, content);
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
    public SkinBlock setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinBlock setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinBlock me(){
        return new SkinBlock();
    }

    private static class Mapper implements RowMapper<SkinBlock> {

        private Supplier<SkinBlock> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinBlock mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinBlock bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setQuote(Utils.toCast(columnsName.contains(F_QUOTE) ? rs.getObject(F_QUOTE) : null, String.class));
            bean.setType(Utils.toCast(columnsName.contains(F_TYPE) ? rs.getObject(F_TYPE) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setSubTitle(Utils.toCast(columnsName.contains(F_SUB_TITLE) ? rs.getObject(F_SUB_TITLE) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinBlock> newMapper(){
        return newMapper(SkinBlock::new);
    }

    public RowMapper<SkinBlock> newMapper(Supplier<SkinBlock> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinBlock {
        @Override
        public abstract RowMapper<SkinBlock> newMapper();
    }
}
