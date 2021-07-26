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
* Date:        10:18 2020/05/21
* Company:     美赞拓
* Version:     1.0
* Description: ProProductColor实体
*/
@SuppressWarnings("all")
public class ProProductColor extends BaseBean<ProProductColor> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "pro_product_color";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 颜色编码
     */
    public static final String F_COLOR = "color";
    /**
     * 颜色名称
     */
    public static final String F_COLOR_NAME = "color_name";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COLOR, null);
        put(F_COLOR_NAME, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ProProductColor() {
        super();
    }

    public ProProductColor(Map<String, Object> map) {
        super(map);
    }

    public ProProductColor(String id) {
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
    public ProProductColor setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return color to color 颜色编码<BR/>
     */
    public String getColor() {
        return getTypedValue(F_COLOR, String.class);
    }
    /**
     * @param color to color 颜色编码 set
     */
    public ProProductColor setColor(String color) {
        set(F_COLOR, color);
        return this;
    }
    /**
     * @return color_name to colorName 颜色名称<BR/>
     */
    public String getColorName() {
        return getTypedValue(F_COLOR_NAME, String.class);
    }
    /**
     * @param colorName to color_name 颜色名称 set
     */
    public ProProductColor setColorName(String colorName) {
        set(F_COLOR_NAME, colorName);
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
    public ProProductColor setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return add_user_id to addUserId 添加人编号<BR/>
     */
    public String getAddUserId() {
        return getTypedValue(F_ADD_USER_ID, String.class);
    }
    /**
     * @param addUserId to add_user_id 添加人编号 set
     */
    public ProProductColor setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductColor setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductColor me(){
        return new ProProductColor();
    }

    private static class Mapper implements RowMapper<ProProductColor> {

        private Supplier<ProProductColor> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductColor mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductColor bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setColor(Utils.toCast(columnsName.contains(F_COLOR) ? rs.getObject(F_COLOR) : null, String.class));
            bean.setColorName(Utils.toCast(columnsName.contains(F_COLOR_NAME) ? rs.getObject(F_COLOR_NAME) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductColor> newMapper(){
        return newMapper(ProProductColor::new);
    }

    public RowMapper<ProProductColor> newMapper(Supplier<ProProductColor> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductColor {
        @Override
        public abstract RowMapper<ProProductColor> newMapper();
    }
}
