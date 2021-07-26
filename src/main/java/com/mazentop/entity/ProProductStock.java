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
* Date:        21:58 2020/04/27
* Company:     美赞拓
* Version:     1.0
* Description: ProProductStock实体
*/
@SuppressWarnings("all")
public class ProProductStock extends BaseBean<ProProductStock> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "pro_product_stock";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品颜色
     */
    public static final String F_COLOR = "color";
    /**
     * 商品尺码
     */
    public static final String F_SIZE = "size";
    /**
     * 商品商城价格
     */
    public static final String F_PRODUCT_MALL_PRICE = "product_mall_price";
    /**
     * 商品市场价格
     */
    public static final String F_PRODUCT_MARKET_PRICE = "product_market_price";
    /**
     * 商品库存
     */
    public static final String F_PRODUCT_STOCK_NUMBER = "product_stock_number";
    /**
     * 重量
     */
    public static final String F_NET_WEIGHT = "net_weight";
    /**
     * 币制
     */
    public static final String F_CURRENCY = "currency";
    /**
     * 商品主图
     */
    public static final String F_PRODUCT_MASTER_IMAGE_URL = "product_master_image_url";
    /**
     * 商品子SKU
     */
    public static final String F_PRODUCT_SUB_SKU = "product_sub_sku";
    /**
     * 条形码
     */
    public static final String F_BAR_CODE = "bar_code";
    /**
     * 色码
     */
    public static final String F_COLOR_CODE = "color_code";
    /**
     * 是否启用
     */
    public static final String F_IS_ENABLE = "is_enable";
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
        put(F_FK_PRODUCT_ID, null);
        put(F_COLOR, null);
        put(F_SIZE, null);
        put(F_PRODUCT_MALL_PRICE, null);
        put(F_PRODUCT_MARKET_PRICE, null);
        put(F_PRODUCT_STOCK_NUMBER, null);
        put(F_NET_WEIGHT, null);
        put(F_CURRENCY, null);
        put(F_PRODUCT_MASTER_IMAGE_URL, null);
        put(F_PRODUCT_SUB_SKU, null);
        put(F_BAR_CODE, null);
        put(F_COLOR_CODE, null);
        put(F_IS_ENABLE, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ProProductStock() {
        super();
    }

    public ProProductStock(Map<String, Object> map) {
        super(map);
    }

    public ProProductStock(String id) {
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
    public ProProductStock setId(String id) {
        set(F_ID, id);
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
    public ProProductStock setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
     * @return color to color 商品颜色<BR/>
     */
    public String getColor() {
        return getTypedValue(F_COLOR, String.class);
    }
    /**
     * @param color to color 商品颜色 set
     */
    public ProProductStock setColor(String color) {
        set(F_COLOR, color);
        return this;
    }
    /**
     * @return size to size 商品尺码<BR/>
     */
    public String getSize() {
        return getTypedValue(F_SIZE, String.class);
    }
    /**
     * @param size to size 商品尺码 set
     */
    public ProProductStock setSize(String size) {
        set(F_SIZE, size);
        return this;
    }
    /**
     * @return product_mall_price to productMallPrice 商品商城价格<BR/>
     */
    public Long getProductMallPrice() {
        return getTypedValue(F_PRODUCT_MALL_PRICE, Long.class);
    }
    /**
     * @param productMallPrice to product_mall_price 商品商城价格 set
     */
    public ProProductStock setProductMallPrice(Long productMallPrice) {
        set(F_PRODUCT_MALL_PRICE, productMallPrice);
        return this;
    }
    /**
     * @return product_market_price to productMarketPrice 商品市场价格<BR/>
     */
    public Long getProductMarketPrice() {
        return getTypedValue(F_PRODUCT_MARKET_PRICE, Long.class);
    }
    /**
     * @param productMarketPrice to product_market_price 商品市场价格 set
     */
    public ProProductStock setProductMarketPrice(Long productMarketPrice) {
        set(F_PRODUCT_MARKET_PRICE, productMarketPrice);
        return this;
    }
    /**
     * @return product_stock_number to productStockNumber 商品库存<BR/>
     */
    public Integer getProductStockNumber() {
        return getTypedValue(F_PRODUCT_STOCK_NUMBER, Integer.class);
    }
    /**
     * @param productStockNumber to product_stock_number 商品库存 set
     */
    public ProProductStock setProductStockNumber(Integer productStockNumber) {
        set(F_PRODUCT_STOCK_NUMBER, productStockNumber);
        return this;
    }
    /**
     * @return net_weight to netWeight 重量<BR/>
     */
    public Long getNetWeight() {
        return getTypedValue(F_NET_WEIGHT, Long.class);
    }
    /**
     * @param netWeight to net_weight 重量 set
     */
    public ProProductStock setNetWeight(Long netWeight) {
        set(F_NET_WEIGHT, netWeight);
        return this;
    }
    /**
     * @return currency to currency 币制<BR/>
     */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
     * @param currency to currency 币制 set
     */
    public ProProductStock setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
     * @return product_master_image_url to productMasterImageUrl 商品主图<BR/>
     */
    public String getProductMasterImageUrl() {
        return getTypedValue(F_PRODUCT_MASTER_IMAGE_URL, String.class);
    }
    /**
     * @param productMasterImageUrl to product_master_image_url 商品主图 set
     */
    public ProProductStock setProductMasterImageUrl(String productMasterImageUrl) {
        set(F_PRODUCT_MASTER_IMAGE_URL, productMasterImageUrl);
        return this;
    }
    /**
     * @return product_sub_sku to productSubSku 商品子SKU<BR/>
     */
    public String getProductSubSku() {
        return getTypedValue(F_PRODUCT_SUB_SKU, String.class);
    }
    /**
     * @param productSubSku to product_sub_sku 商品子SKU set
     */
    public ProProductStock setProductSubSku(String productSubSku) {
        set(F_PRODUCT_SUB_SKU, productSubSku);
        return this;
    }
    /**
     * @return bar_code to barCode 条形码<BR/>
     */
    public String getBarCode() {
        return getTypedValue(F_BAR_CODE, String.class);
    }
    /**
     * @param barCode to bar_code 条形码 set
     */
    public ProProductStock setBarCode(String barCode) {
        set(F_BAR_CODE, barCode);
        return this;
    }
    /**
     * @return color_code to colorCode 色码<BR/>
     */
    public String getColorCode() {
        return getTypedValue(F_COLOR_CODE, String.class);
    }
    /**
     * @param colorCode to color_code 色码 set
     */
    public ProProductStock setColorCode(String colorCode) {
        set(F_COLOR_CODE, colorCode);
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
    public ProProductStock setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
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
    public ProProductStock setAddTime(Long addTime) {
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
    public ProProductStock setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductStock setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductStock me(){
        return new ProProductStock();
    }

    private static class Mapper implements RowMapper<ProProductStock> {

        private Supplier<ProProductStock> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductStock mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductStock bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setColor(Utils.toCast(columnsName.contains(F_COLOR) ? rs.getObject(F_COLOR) : null, String.class));
            bean.setSize(Utils.toCast(columnsName.contains(F_SIZE) ? rs.getObject(F_SIZE) : null, String.class));
            bean.setProductMallPrice(Utils.toCast(columnsName.contains(F_PRODUCT_MALL_PRICE) ? rs.getObject(F_PRODUCT_MALL_PRICE) : null, Long.class));
            bean.setProductMarketPrice(Utils.toCast(columnsName.contains(F_PRODUCT_MARKET_PRICE) ? rs.getObject(F_PRODUCT_MARKET_PRICE) : null, Long.class));
            bean.setProductStockNumber(Utils.toCast(columnsName.contains(F_PRODUCT_STOCK_NUMBER) ? rs.getObject(F_PRODUCT_STOCK_NUMBER) : null, Integer.class));
            bean.setNetWeight(Utils.toCast(columnsName.contains(F_NET_WEIGHT) ? rs.getObject(F_NET_WEIGHT) : null, Long.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setProductMasterImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_MASTER_IMAGE_URL) ? rs.getObject(F_PRODUCT_MASTER_IMAGE_URL) : null, String.class));
            bean.setProductSubSku(Utils.toCast(columnsName.contains(F_PRODUCT_SUB_SKU) ? rs.getObject(F_PRODUCT_SUB_SKU) : null, String.class));
            bean.setBarCode(Utils.toCast(columnsName.contains(F_BAR_CODE) ? rs.getObject(F_BAR_CODE) : null, String.class));
            bean.setColorCode(Utils.toCast(columnsName.contains(F_COLOR_CODE) ? rs.getObject(F_COLOR_CODE) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductStock> newMapper(){
        return newMapper(ProProductStock::new);
    }

    public RowMapper<ProProductStock> newMapper(Supplier<ProProductStock> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductStock {
        @Override
        public abstract RowMapper<ProProductStock> newMapper();
    }
}
