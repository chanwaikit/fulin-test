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
* Date:        14:11 2020/06/08
* Company:     美赞拓
* Version:     1.0
* Description: SkinPageDataSnapshot实体
*/
@SuppressWarnings("all")
public class SkinPageDataSnapshot extends BaseBean<SkinPageDataSnapshot> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_page_data_snapshot";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 页面主键
    */
    public static final String F_PAGE_ID = "page_id";
    /**
    * 数据主键
    */
    public static final String F_DATA_ID = "data_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PAGE_ID, null);
        put(F_DATA_ID, null);
    }

    public SkinPageDataSnapshot() {
        super();
    }

    public SkinPageDataSnapshot(Map<String, Object> map) {
        super(map);
    }

    public SkinPageDataSnapshot(String id) {
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
    public SkinPageDataSnapshot setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return page_id to pageId 页面主键<BR/>
    */
    public String getPageId() {
        return getTypedValue(F_PAGE_ID, String.class);
    }
    /**
    * @param pageId to page_id 页面主键 set
    */
    public SkinPageDataSnapshot setPageId(String pageId) {
        set(F_PAGE_ID, pageId);
        return this;
    }
    /**
    * @return data_id to dataId 数据主键<BR/>
    */
    public String getDataId() {
        return getTypedValue(F_DATA_ID, String.class);
    }
    /**
    * @param dataId to data_id 数据主键 set
    */
    public SkinPageDataSnapshot setDataId(String dataId) {
        set(F_DATA_ID, dataId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPageDataSnapshot setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPageDataSnapshot me(){
        return new SkinPageDataSnapshot();
    }

    private static class Mapper implements RowMapper<SkinPageDataSnapshot> {

        private Supplier<SkinPageDataSnapshot> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPageDataSnapshot mapRow(ResultSet rs, int rownum) throws SQLException {
            SkinPageDataSnapshot bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setPageId(Utils.toCast(rs.getObject(F_PAGE_ID), String.class));
            bean.setDataId(Utils.toCast(rs.getObject(F_DATA_ID), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPageDataSnapshot> newMapper(){
        return newMapper(SkinPageDataSnapshot::new);
    }

    public RowMapper<SkinPageDataSnapshot> newMapper(Supplier<SkinPageDataSnapshot> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPageDataSnapshot {
        @Override
        public abstract RowMapper<SkinPageDataSnapshot> newMapper();
    }
}
