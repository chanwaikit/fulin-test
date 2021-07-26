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
* Description: SysSiteMap实体
*/
@SuppressWarnings("all")
public class SysSiteMap extends BaseBean<SysSiteMap> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_site_map";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 网站地图类型编号
    */
    public static final String F_FK_SITE_TYPE_ID = "fk_site_type_id";
    /**
    * 网站地图类型名称
    */
    public static final String F_SITE_MAP_TYPE_NAME = "site_map_type_name";
    /**
    * 地址名称
    */
    public static final String F_URL_NAME = "url_name";
    /**
    * 地址链接
    */
    public static final String F_URL_LINK = "url_link";
    /**
    * 排序
    */
    public static final String F_SORT = "sort";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_SITE_TYPE_ID, null);
        put(F_SITE_MAP_TYPE_NAME, null);
        put(F_URL_NAME, null);
        put(F_URL_LINK, null);
        put(F_SORT, null);
        put(F_REMARK, null);
    }

    public SysSiteMap() {
        super();
    }

    public SysSiteMap(Map<String, Object> map) {
        super(map);
    }

    public SysSiteMap(String id) {
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
    public SysSiteMap setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_site_type_id to fkSiteTypeId 网站地图类型编号<BR/>
    */
    public String getFkSiteTypeId() {
        return getTypedValue(F_FK_SITE_TYPE_ID, String.class);
    }
    /**
    * @param fkSiteTypeId to fk_site_type_id 网站地图类型编号 set
    */
    public SysSiteMap setFkSiteTypeId(String fkSiteTypeId) {
        set(F_FK_SITE_TYPE_ID, fkSiteTypeId);
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
    public SysSiteMap setSiteMapTypeName(String siteMapTypeName) {
        set(F_SITE_MAP_TYPE_NAME, siteMapTypeName);
        return this;
    }
    /**
    * @return url_name to urlName 地址名称<BR/>
    */
    public String getUrlName() {
        return getTypedValue(F_URL_NAME, String.class);
    }
    /**
    * @param urlName to url_name 地址名称 set
    */
    public SysSiteMap setUrlName(String urlName) {
        set(F_URL_NAME, urlName);
        return this;
    }
    /**
    * @return url_link to urlLink 地址链接<BR/>
    */
    public String getUrlLink() {
        return getTypedValue(F_URL_LINK, String.class);
    }
    /**
    * @param urlLink to url_link 地址链接 set
    */
    public SysSiteMap setUrlLink(String urlLink) {
        set(F_URL_LINK, urlLink);
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
    public SysSiteMap setSort(Integer sort) {
        set(F_SORT, sort);
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
    public SysSiteMap setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysSiteMap setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysSiteMap me(){
        return new SysSiteMap();
    }

    private static class Mapper implements RowMapper<SysSiteMap> {

        private Supplier<SysSiteMap> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysSiteMap mapRow(ResultSet rs, int rownum) throws SQLException {
            SysSiteMap bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkSiteTypeId(Utils.toCast(rs.getObject(F_FK_SITE_TYPE_ID), String.class));
            bean.setSiteMapTypeName(Utils.toCast(rs.getObject(F_SITE_MAP_TYPE_NAME), String.class));
            bean.setUrlName(Utils.toCast(rs.getObject(F_URL_NAME), String.class));
            bean.setUrlLink(Utils.toCast(rs.getObject(F_URL_LINK), String.class));
            bean.setSort(Utils.toCast(rs.getObject(F_SORT), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysSiteMap> newMapper(){
        return newMapper(SysSiteMap::new);
    }

    public RowMapper<SysSiteMap> newMapper(Supplier<SysSiteMap> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysSiteMap {
        @Override
        public abstract RowMapper<SysSiteMap> newMapper();
    }
}
