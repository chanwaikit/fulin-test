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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        15:48 2020/12/26
* Version:     1.0
* Description: ProProductCommon实体
*/
@SuppressWarnings("all")
public class ProProductCommon extends BaseBean<ProProductCommon> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_product_common";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 商品介绍
    */
    public static final String F_DESCRIPTION = "description";
    /**
    * 排序
    */
    public static final String F_SORT = "sort";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 是否启用
    */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TITLE, null);
        put(F_DESCRIPTION, null);
        put(F_SORT, null);
        put(F_ADD_TIME, null);
        put(F_IS_ENABLE, null);
    }

    public ProProductCommon() {
        super();
    }

    public ProProductCommon(Map<String, Object> map) {
        super(map);
    }

    public ProProductCommon(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id id<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id id set
    */
    public ProProductCommon setId(String id) {
        set(F_ID, id);
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
    public ProProductCommon setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return description to description 商品介绍<BR/>
    */
    public String getDescription() {
        return getTypedValue(F_DESCRIPTION, String.class);
    }
    /**
    * @param description to description 商品介绍 set
    */
    public ProProductCommon setDescription(String description) {
        set(F_DESCRIPTION, description);
        return this;
    }
    /**
    * @return sort to sort 排序<BR/>
    */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
    * @param sort to sort 排序 set
    */
    public ProProductCommon setSort(Integer sort) {
        set(F_SORT, sort);
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
    public ProProductCommon setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
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
    public ProProductCommon setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductCommon setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductCommon me(){
        return new ProProductCommon();
    }

    private static class Mapper implements RowMapper<ProProductCommon> {

        private Supplier<ProProductCommon> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductCommon mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductCommon bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setDescription(Utils.toCast(columnsName.contains(F_DESCRIPTION) ? rs.getObject(F_DESCRIPTION) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductCommon> newMapper(){
        return newMapper(ProProductCommon::new);
    }

    public RowMapper<ProProductCommon> newMapper(Supplier<ProProductCommon> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductCommon {
        @Override
        public abstract RowMapper<ProProductCommon> newMapper();
    }
}
