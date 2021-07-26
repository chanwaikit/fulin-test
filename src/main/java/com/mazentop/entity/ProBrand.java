package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
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
* Date:        10:05 2020/03/17
* Company:     美赞拓
* Version:     1.0
* Description: ProBrand实体
*/
@SuppressWarnings("all")
public class ProBrand extends BaseBean<ProBrand> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "pro_brand";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 品牌名称
     */
    public static final String F_BRAND_NAME = "brand_name";
    /**
     * 品牌概述
     */
    public static final String F_BRAND_DESCRIPTION = "brand_description";
    /**
     * 排序
     */
    public static final String F_SORT = "sort";
    /**
     * 是否启用
     */
    public static final String F_IS_ENABLE = "is_enable";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人物编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_BRAND_NAME, null);
        put(F_BRAND_DESCRIPTION, null);
        put(F_SORT, null);
        put(F_IS_ENABLE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ProBrand() {
        super();
    }

    public ProBrand(Map<String, Object> map) {
        super(map);
    }

    public ProBrand(String id) {
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
    public ProBrand setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return brand_name to brandName 品牌名称<BR/>
     */
    public String getBrandName() {
        return getTypedValue(F_BRAND_NAME, String.class);
    }
    /**
     * @param brandName to brand_name 品牌名称 set
     */
    public ProBrand setBrandName(String brandName) {
        set(F_BRAND_NAME, brandName);
        return this;
    }
    /**
     * @return brand_description to brandDescription 品牌概述<BR/>
     */
    public String getBrandDescription() {
        return getTypedValue(F_BRAND_DESCRIPTION, String.class);
    }
    /**
     * @param brandDescription to brand_description 品牌概述 set
     */
    public ProBrand setBrandDescription(String brandDescription) {
        set(F_BRAND_DESCRIPTION, brandDescription);
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
    public ProBrand setSort(Integer sort) {
        set(F_SORT, sort);
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
    public ProBrand setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
     * @return remark to remark 备注<BR/>
     */
    public String getRemark() {
        return getTypedValue(F_REMARK, String.class);
    }
    /**
     * @param remark to remark 备注 set
     */
    public ProBrand setRemark(String remark) {
        set(F_REMARK, remark);
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
    public ProBrand setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return add_user_id to addUserId 添加人物编号<BR/>
     */
    public String getAddUserId() {
        return getTypedValue(F_ADD_USER_ID, String.class);
    }
    /**
     * @param addUserId to add_user_id 添加人物编号 set
     */
    public ProBrand setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProBrand setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProBrand me(){
        return new ProBrand();
    }

    private static class Mapper implements RowMapper<ProBrand> {

        private Supplier<ProBrand> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProBrand mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProBrand bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setBrandName(Utils.toCast(columnsName.contains(F_BRAND_NAME) ? rs.getObject(F_BRAND_NAME) : null, String.class));
            bean.setBrandDescription(Utils.toCast(columnsName.contains(F_BRAND_DESCRIPTION) ? rs.getObject(F_BRAND_DESCRIPTION) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProBrand> newMapper(){
        return newMapper(ProBrand::new);
    }

    public RowMapper<ProBrand> newMapper(Supplier<ProBrand> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProBrand {
        @Override
        public abstract RowMapper<ProBrand> newMapper();
    }
}
