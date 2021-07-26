package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        15:41 2020/04/02
* Company:     美赞拓
* Version:     1.0
* Description: SysSiteSearchRecordDetail实体
*/
@SuppressWarnings("all")
public class SysSiteSearchRecordDetail extends BaseBean<SysSiteSearchRecordDetail> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_site_search_record_detail";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 搜索记录编号
     */
    public static final String F_FK_SITE_SEARCH_RECORD_ID = "fk_site_search_record_id";
    /**
     * 关键词
     */
    public static final String F_KEYWORDS = "keywords";
    /**
     * 搜索时间
     */
    public static final String F_SEARCH_TIME = "search_time";
    /**
     * 搜索人编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 搜索人名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品名称
     */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
     * IP
     */
    public static final String F_IP = "ip";
    /**
     * 国家
     */
    public static final String F_COUNTRY_NAME = "country_name";
    /**
     * 省
     */
    public static final String F_PROVINCE = "province";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
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
        put(F_FK_SITE_SEARCH_RECORD_ID, null);
        put(F_KEYWORDS, null);
        put(F_SEARCH_TIME, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_IP, null);
        put(F_COUNTRY_NAME, null);
        put(F_PROVINCE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysSiteSearchRecordDetail() {
        super();
    }

    public SysSiteSearchRecordDetail(Map<String, Object> map) {
        super(map);
    }

    public SysSiteSearchRecordDetail(String id) {
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
    public SysSiteSearchRecordDetail setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_site_search_record_id to fkSiteSearchRecordId 搜索记录编号<BR/>
     */
    public String getFkSiteSearchRecordId() {
        return getTypedValue(F_FK_SITE_SEARCH_RECORD_ID, String.class);
    }
    /**
     * @param fkSiteSearchRecordId to fk_site_search_record_id 搜索记录编号 set
     */
    public SysSiteSearchRecordDetail setFkSiteSearchRecordId(String fkSiteSearchRecordId) {
        set(F_FK_SITE_SEARCH_RECORD_ID, fkSiteSearchRecordId);
        return this;
    }
    /**
     * @return keywords to keywords 关键词<BR/>
     */
    public String getKeywords() {
        return getTypedValue(F_KEYWORDS, String.class);
    }
    /**
     * @param keywords to keywords 关键词 set
     */
    public SysSiteSearchRecordDetail setKeywords(String keywords) {
        set(F_KEYWORDS, keywords);
        return this;
    }
    /**
     * @return search_time to searchTime 搜索时间<BR/>
     */
    public Long getSearchTime() {
        return getTypedValue(F_SEARCH_TIME, Long.class);
    }
    /**
     * @param searchTime to search_time 搜索时间 set
     */
    public SysSiteSearchRecordDetail setSearchTime(Long searchTime) {
        set(F_SEARCH_TIME, searchTime);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 搜索人编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 搜索人编号 set
     */
    public SysSiteSearchRecordDetail setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_name to clientName 搜索人名称<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 搜索人名称 set
     */
    public SysSiteSearchRecordDetail setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return fk_product_id to fkProductId 商品编号<BR/>
     */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
     * @param fkProductId to fk_product_id 商品编号 set
     */
    public SysSiteSearchRecordDetail setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
     * @return product_name to productName 商品名称<BR/>
     */
    public String getProductName() {
        return getTypedValue(F_PRODUCT_NAME, String.class);
    }
    /**
     * @param productName to product_name 商品名称 set
     */
    public SysSiteSearchRecordDetail setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
     * @return ip to ip IP<BR/>
     */
    public String getIp() {
        return getTypedValue(F_IP, String.class);
    }
    /**
     * @param ip to ip IP set
     */
    public SysSiteSearchRecordDetail setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
     * @return country_name to countryName 国家<BR/>
     */
    public String getCountryName() {
        return getTypedValue(F_COUNTRY_NAME, String.class);
    }
    /**
     * @param countryName to country_name 国家 set
     */
    public SysSiteSearchRecordDetail setCountryName(String countryName) {
        set(F_COUNTRY_NAME, countryName);
        return this;
    }
    /**
     * @return province to province 省<BR/>
     */
    public String getProvince() {
        return getTypedValue(F_PROVINCE, String.class);
    }
    /**
     * @param province to province 省 set
     */
    public SysSiteSearchRecordDetail setProvince(String province) {
        set(F_PROVINCE, province);
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
    public SysSiteSearchRecordDetail setRemark(String remark) {
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
    public SysSiteSearchRecordDetail setAddTime(Long addTime) {
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
    public SysSiteSearchRecordDetail setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysSiteSearchRecordDetail setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysSiteSearchRecordDetail me(){
        return new SysSiteSearchRecordDetail();
    }

    private static class Mapper implements RowMapper<SysSiteSearchRecordDetail> {

        private Supplier<SysSiteSearchRecordDetail> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysSiteSearchRecordDetail mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysSiteSearchRecordDetail bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkSiteSearchRecordId(Utils.toCast(columnsName.contains(F_FK_SITE_SEARCH_RECORD_ID) ? rs.getObject(F_FK_SITE_SEARCH_RECORD_ID) : null, String.class));
            bean.setKeywords(Utils.toCast(columnsName.contains(F_KEYWORDS) ? rs.getObject(F_KEYWORDS) : null, String.class));
            bean.setSearchTime(Utils.toCast(columnsName.contains(F_SEARCH_TIME) ? rs.getObject(F_SEARCH_TIME) : null, Long.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setIp(Utils.toCast(columnsName.contains(F_IP) ? rs.getObject(F_IP) : null, String.class));
            bean.setCountryName(Utils.toCast(columnsName.contains(F_COUNTRY_NAME) ? rs.getObject(F_COUNTRY_NAME) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysSiteSearchRecordDetail> newMapper(){
        return newMapper(SysSiteSearchRecordDetail::new);
    }

    public RowMapper<SysSiteSearchRecordDetail> newMapper(Supplier<SysSiteSearchRecordDetail> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysSiteSearchRecordDetail {
        @Override
        public abstract RowMapper<SysSiteSearchRecordDetail> newMapper();
    }
}
