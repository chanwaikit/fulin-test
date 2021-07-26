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
* Date:        18:53 2020/04/09
* Company:     美赞拓
* Version:     1.0
* Description: SysSiteMapType实体
*/
@SuppressWarnings("all")
public class SysSiteMapType extends BaseBean<SysSiteMapType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_site_map_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 网站地图类型名称
    */
    public static final String F_SITE_MAP_TYPE_NAME = "site_map_type_name";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SITE_MAP_TYPE_NAME, null);
        put(F_REMARK, null);
    }

    public SysSiteMapType() {
        super();
    }

    public SysSiteMapType(Map<String, Object> map) {
        super(map);
    }

    public SysSiteMapType(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 编号<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 编号 set
    */
    public SysSiteMapType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return site_map_type_name to siteMapTypeName 网站地图类型名称<BR/>
    */
    public String getSiteMapTypeName() {
        return getTypedValue(F_SITE_MAP_TYPE_NAME, String.class);
    }
    /**
    * @param siteMapTypeName to site_map_type_name 网站地图类型名称 set
    */
    public SysSiteMapType setSiteMapTypeName(String siteMapTypeName) {
        set(F_SITE_MAP_TYPE_NAME, siteMapTypeName);
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
    public SysSiteMapType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysSiteMapType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysSiteMapType me(){
        return new SysSiteMapType();
    }

    private static class Mapper implements RowMapper<SysSiteMapType> {

        private Supplier<SysSiteMapType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysSiteMapType mapRow(ResultSet rs, int rownum) throws SQLException {
            SysSiteMapType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setSiteMapTypeName(Utils.toCast(rs.getObject(F_SITE_MAP_TYPE_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysSiteMapType> newMapper(){
        return newMapper(SysSiteMapType::new);
    }

    public RowMapper<SysSiteMapType> newMapper(Supplier<SysSiteMapType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysSiteMapType {
        @Override
        public abstract RowMapper<SysSiteMapType> newMapper();
    }
}
