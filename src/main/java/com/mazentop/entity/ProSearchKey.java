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
* Date:        09:48 2020/05/11
* Company:     美赞拓
* Version:     1.0
* Description: ProSearchKey实体
*/
@SuppressWarnings("all")
public class ProSearchKey extends BaseBean<ProSearchKey> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_search_key";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 关键词
    */
    public static final String F_KEYWORD = "keyword";
    /**
    * 搜索时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_KEYWORD, null);
        put(F_ADD_TIME, null);
    }

    public ProSearchKey() {
        super();
    }

    public ProSearchKey(Map<String, Object> map) {
        super(map);
    }

    public ProSearchKey(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id <BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id  set
    */
    public ProSearchKey setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return keyword to keyword 关键词<BR/>
    */
    public String getKeyword() {
        return getTypedValue(F_KEYWORD, String.class);
    }
    /**
    * @param keyword to keyword 关键词 set
    */
    public ProSearchKey setKeyword(String keyword) {
        set(F_KEYWORD, keyword);
        return this;
    }
    /**
    * @return add_time to addTime 搜索时间<BR/>
    */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
    * @param addTime to add_time 搜索时间 set
    */
    public ProSearchKey setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProSearchKey setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProSearchKey me(){
        return new ProSearchKey();
    }

    private static class Mapper implements RowMapper<ProSearchKey> {

        private Supplier<ProSearchKey> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProSearchKey mapRow(ResultSet rs, int rownum) throws SQLException {
            ProSearchKey bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setKeyword(Utils.toCast(rs.getObject(F_KEYWORD), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProSearchKey> newMapper(){
        return newMapper(ProSearchKey::new);
    }

    public RowMapper<ProSearchKey> newMapper(Supplier<ProSearchKey> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProSearchKey {
        @Override
        public abstract RowMapper<ProSearchKey> newMapper();
    }
}
