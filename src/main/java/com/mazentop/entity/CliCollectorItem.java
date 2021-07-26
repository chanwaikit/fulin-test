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
* Date:        09:12 2020/03/31
* Company:     美赞拓
* Version:     1.0
* Description: CliCollectorItem实体
*/
@SuppressWarnings("all")
public class CliCollectorItem extends BaseBean<CliCollectorItem> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "cli_collector_item";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 收藏类型编号
     */
    public static final String F_FK_COLLECTOR_ITEM_TYPE_ID = "fk_collector_item_type_id";
    /**
     * 收藏类型名称
     */
    public static final String F_COLLECTOR_ITEM_TYPE_NAME = "collector_item_type_name";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品名称
     */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
     * 商品访问地址
     */
    public static final String F_PRODUCT_VIEW_URL = "product_view_url";
    /**
     * 商品预览图
     */
    public static final String F_PRODUCT_IMAGE_URL = "product_image_url";
    /**
     * 是否有效
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
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_FK_COLLECTOR_ITEM_TYPE_ID, null);
        put(F_COLLECTOR_ITEM_TYPE_NAME, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_PRODUCT_VIEW_URL, null);
        put(F_PRODUCT_IMAGE_URL, null);
        put(F_IS_ENABLE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public CliCollectorItem() {
        super();
    }

    public CliCollectorItem(Map<String, Object> map) {
        super(map);
    }

    public CliCollectorItem(String id) {
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
    public CliCollectorItem setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 客户编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 客户编号 set
     */
    public CliCollectorItem setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_name to clientName 客户名称<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 客户名称 set
     */
    public CliCollectorItem setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return fk_collector_item_type_id to fkCollectorItemTypeId 收藏类型编号<BR/>
     */
    public String getFkCollectorItemTypeId() {
        return getTypedValue(F_FK_COLLECTOR_ITEM_TYPE_ID, String.class);
    }
    /**
     * @param fkCollectorItemTypeId to fk_collector_item_type_id 收藏类型编号 set
     */
    public CliCollectorItem setFkCollectorItemTypeId(String fkCollectorItemTypeId) {
        set(F_FK_COLLECTOR_ITEM_TYPE_ID, fkCollectorItemTypeId);
        return this;
    }
    /**
     * @return collector_item_type_name to collectorItemTypeName 收藏类型名称<BR/>
     */
    public String getCollectorItemTypeName() {
        return getTypedValue(F_COLLECTOR_ITEM_TYPE_NAME, String.class);
    }
    /**
     * @param collectorItemTypeName to collector_item_type_name 收藏类型名称 set
     */
    public CliCollectorItem setCollectorItemTypeName(String collectorItemTypeName) {
        set(F_COLLECTOR_ITEM_TYPE_NAME, collectorItemTypeName);
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
    public CliCollectorItem setFkProductId(String fkProductId) {
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
    public CliCollectorItem setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
     * @return product_view_url to productViewUrl 商品访问地址<BR/>
     */
    public String getProductViewUrl() {
        return getTypedValue(F_PRODUCT_VIEW_URL, String.class);
    }
    /**
     * @param productViewUrl to product_view_url 商品访问地址 set
     */
    public CliCollectorItem setProductViewUrl(String productViewUrl) {
        set(F_PRODUCT_VIEW_URL, productViewUrl);
        return this;
    }
    /**
     * @return product_image_url to productImageUrl 商品预览图<BR/>
     */
    public String getProductImageUrl() {
        return getTypedValue(F_PRODUCT_IMAGE_URL, String.class);
    }
    /**
     * @param productImageUrl to product_image_url 商品预览图 set
     */
    public CliCollectorItem setProductImageUrl(String productImageUrl) {
        set(F_PRODUCT_IMAGE_URL, productImageUrl);
        return this;
    }
    /**
     * @return is_enable to isEnable 是否有效<BR/>
     */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
     * @param isEnable to is_enable 是否有效 set
     */
    public CliCollectorItem setIsEnable(Integer isEnable) {
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
    public CliCollectorItem setRemark(String remark) {
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
    public CliCollectorItem setAddTime(Long addTime) {
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
    public CliCollectorItem setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliCollectorItem setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliCollectorItem me(){
        return new CliCollectorItem();
    }

    private static class Mapper implements RowMapper<CliCollectorItem> {

        private Supplier<CliCollectorItem> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliCollectorItem mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CliCollectorItem bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setFkCollectorItemTypeId(Utils.toCast(columnsName.contains(F_FK_COLLECTOR_ITEM_TYPE_ID) ? rs.getObject(F_FK_COLLECTOR_ITEM_TYPE_ID) : null, String.class));
            bean.setCollectorItemTypeName(Utils.toCast(columnsName.contains(F_COLLECTOR_ITEM_TYPE_NAME) ? rs.getObject(F_COLLECTOR_ITEM_TYPE_NAME) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setProductViewUrl(Utils.toCast(columnsName.contains(F_PRODUCT_VIEW_URL) ? rs.getObject(F_PRODUCT_VIEW_URL) : null, String.class));
            bean.setProductImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_IMAGE_URL) ? rs.getObject(F_PRODUCT_IMAGE_URL) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliCollectorItem> newMapper(){
        return newMapper(CliCollectorItem::new);
    }

    public RowMapper<CliCollectorItem> newMapper(Supplier<CliCollectorItem> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliCollectorItem {
        @Override
        public abstract RowMapper<CliCollectorItem> newMapper();
    }
}
