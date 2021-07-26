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
* Author:      dengy
* Mail:        dengy@mazentop.com
* Date:        14:40 2020/03/11
* Company:     美赞拓
* Version:     1.0
* Description: CliClienteleGoodsTrail实体
*/
@SuppressWarnings("all")
public class CliClienteleGoodsTrail extends BaseBean<CliClienteleGoodsTrail> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "cli_clientele_goods_trail";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 顾客编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 顾客名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 商品编号
     */
    public static final String F_FK_GOODS_ID = "fk_goods_id";
    /**
     * 商品名称
     */
    public static final String F_GOODS_NAME = "goods_name";
    /**
     * 商品预览图
     */
    public static final String F_DEFAULT_IMAGE_URL = "default_image_url";
    /**
     * ip地址
     */
    public static final String F_IP = "ip";
    /**
     * 国家
     */
    public static final String F_COUNTRY = "country";
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
    /**
     * 0.否1.是（是否启用）
     */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_FK_GOODS_ID, null);
        put(F_GOODS_NAME, null);
        put(F_DEFAULT_IMAGE_URL, null);
        put(F_IP, null);
        put(F_COUNTRY, null);
        put(F_PROVINCE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_IS_ENABLE, null);
    }

    public CliClienteleGoodsTrail() {
        super();
    }

    public CliClienteleGoodsTrail(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleGoodsTrail(String id) {
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
    public CliClienteleGoodsTrail setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 顾客编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 顾客编号 set
     */
    public CliClienteleGoodsTrail setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_name to clientName 顾客名称<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 顾客名称 set
     */
    public CliClienteleGoodsTrail setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return fk_goods_id to fkGoodsId 商品编号<BR/>
     */
    public String getFkGoodsId() {
        return getTypedValue(F_FK_GOODS_ID, String.class);
    }
    /**
     * @param fkGoodsId to fk_goods_id 商品编号 set
     */
    public CliClienteleGoodsTrail setFkGoodsId(String fkGoodsId) {
        set(F_FK_GOODS_ID, fkGoodsId);
        return this;
    }
    /**
     * @return goods_name to goodsName 商品名称<BR/>
     */
    public String getGoodsName() {
        return getTypedValue(F_GOODS_NAME, String.class);
    }
    /**
     * @param goodsName to goods_name 商品名称 set
     */
    public CliClienteleGoodsTrail setGoodsName(String goodsName) {
        set(F_GOODS_NAME, goodsName);
        return this;
    }
    /**
     * @return default_image_url to defaultImageUrl 商品预览图<BR/>
     */
    public String getDefaultImageUrl() {
        return getTypedValue(F_DEFAULT_IMAGE_URL, String.class);
    }
    /**
     * @param defaultImageUrl to default_image_url 商品预览图 set
     */
    public CliClienteleGoodsTrail setDefaultImageUrl(String defaultImageUrl) {
        set(F_DEFAULT_IMAGE_URL, defaultImageUrl);
        return this;
    }
    /**
     * @return ip to ip ip地址<BR/>
     */
    public String getIp() {
        return getTypedValue(F_IP, String.class);
    }
    /**
     * @param ip to ip ip地址 set
     */
    public CliClienteleGoodsTrail setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
     * @return country to country 国家<BR/>
     */
    public String getCountry() {
        return getTypedValue(F_COUNTRY, String.class);
    }
    /**
     * @param country to country 国家 set
     */
    public CliClienteleGoodsTrail setCountry(String country) {
        set(F_COUNTRY, country);
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
    public CliClienteleGoodsTrail setProvince(String province) {
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
    public CliClienteleGoodsTrail setRemark(String remark) {
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
    public CliClienteleGoodsTrail setAddTime(Long addTime) {
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
    public CliClienteleGoodsTrail setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
     * @return is_enable to isEnable 0.否1.是（是否启用）<BR/>
     */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
     * @param isEnable to is_enable 0.否1.是（是否启用） set
     */
    public CliClienteleGoodsTrail setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleGoodsTrail setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleGoodsTrail me(){
        return new CliClienteleGoodsTrail();
    }

    private static class Mapper implements RowMapper<CliClienteleGoodsTrail> {

        private Supplier<CliClienteleGoodsTrail> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleGoodsTrail mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CliClienteleGoodsTrail bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setFkGoodsId(Utils.toCast(columnsName.contains(F_FK_GOODS_ID) ? rs.getObject(F_FK_GOODS_ID) : null, String.class));
            bean.setGoodsName(Utils.toCast(columnsName.contains(F_GOODS_NAME) ? rs.getObject(F_GOODS_NAME) : null, String.class));
            bean.setDefaultImageUrl(Utils.toCast(columnsName.contains(F_DEFAULT_IMAGE_URL) ? rs.getObject(F_DEFAULT_IMAGE_URL) : null, String.class));
            bean.setIp(Utils.toCast(columnsName.contains(F_IP) ? rs.getObject(F_IP) : null, String.class));
            bean.setCountry(Utils.toCast(columnsName.contains(F_COUNTRY) ? rs.getObject(F_COUNTRY) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleGoodsTrail> newMapper(){
        return newMapper(CliClienteleGoodsTrail::new);
    }

    public RowMapper<CliClienteleGoodsTrail> newMapper(Supplier<CliClienteleGoodsTrail> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleGoodsTrail {
        @Override
        public abstract RowMapper<CliClienteleGoodsTrail> newMapper();
    }
}
