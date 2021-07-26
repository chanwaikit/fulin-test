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
* Date:        11:30 2020/11/26
* Version:     1.0
* Description: OrdShoppingCart实体
*/
@SuppressWarnings("all")
public class OrdShoppingCart extends BaseBean<OrdShoppingCart> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_shopping_cart";

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
    * 商品子SKU
    */
    public static final String F_PRODUCT_SUB_SKU = "product_sub_sku";
    /**
    * 商品编号
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 商品名称
    */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
    * 商品预览图
    */
    public static final String F_PRDUCT_PIC_IMAGE_URL = "prduct_pic_image_url";
    /**
    * 商品商城价格
    */
    public static final String F_PRODUCT_MALL_PRICE = "product_mall_price";
    /**
    * 商品市场价格
    */
    public static final String F_PRODUCT_MARKET_PRICE = "product_market_price";
    /**
    * 商品促销价格
    */
    public static final String F_PRODUCT_PROMOTION_PRICE = "product_promotion_price";
    /**
    * 促销活动编号
    */
    public static final String F_FK_ACTIVITY_ID = "fk_activity_id";
    /**
    * 促销活动名称
    */
    public static final String F_ACTIVITY_NAME = "activity_name";
    /**
    * 优惠类型名称(折扣、抵扣)
    */
    public static final String F_DISCOUNT_TYPE_NAME = "discount_type_name";
    /**
    * 优惠数据
    */
    public static final String F_DISCOUNT_VALUE = "discount_value";
    /**
    * 商品数量
    */
    public static final String F_TOTAL_PRODUCT_NUMBER = "total_product_number";
    /**
    * 商品总重量
    */
    public static final String F_TOTAL_VOL = "total_vol";
    /**
    * 币制
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 购物批次号
    */
    public static final String F_BATCH_NUMBER = "batch_number";
    /**
    * 是否已生成结算单
    */
    public static final String F_IS_GEN_BALANCE_ACCOUNTS = "is_gen_balance_accounts";
    /**
    * 是否已生成召回单
    */
    public static final String F_IS_GEN_RETURN_ORDER = "is_gen_return_order";
    /**
    * 是否已生成订单
    */
    public static final String F_IS_GEN_ORDER = "is_gen_order";
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
    * 添加人名称
    */
    public static final String F_ADD_USER_NAME = "add_user_name";
    /**
    * 操作时间
    */
    public static final String F_OPERATION_TIME = "operation_time";
    /**
    * 操作人编号
    */
    public static final String F_OPERATION_USER_ID = "operation_user_id";
    /**
    * 操作人名称
    */
    public static final String F_OPERATION_USER_NAME = "operation_user_name";
    /**
    * 是否删除
    */
    public static final String F_IS_DEL = "is_del";
    /**
    * 商品规格串
    */
    public static final String F_PRODUCT_SPEC = "product_spec";
    /**
    * 是否选中 1是 0否 
    */
    public static final String F_SELECTED = "selected";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_PRODUCT_SUB_SKU, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_PRDUCT_PIC_IMAGE_URL, null);
        put(F_PRODUCT_MALL_PRICE, null);
        put(F_PRODUCT_MARKET_PRICE, null);
        put(F_PRODUCT_PROMOTION_PRICE, null);
        put(F_FK_ACTIVITY_ID, null);
        put(F_ACTIVITY_NAME, null);
        put(F_DISCOUNT_TYPE_NAME, null);
        put(F_DISCOUNT_VALUE, null);
        put(F_TOTAL_PRODUCT_NUMBER, null);
        put(F_TOTAL_VOL, null);
        put(F_CURRENCY, null);
        put(F_BATCH_NUMBER, null);
        put(F_IS_GEN_BALANCE_ACCOUNTS, null);
        put(F_IS_GEN_RETURN_ORDER, null);
        put(F_IS_GEN_ORDER, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_IS_DEL, null);
        put(F_PRODUCT_SPEC, null);
        put(F_SELECTED, null);
    }

    public OrdShoppingCart() {
        super();
    }

    public OrdShoppingCart(Map<String, Object> map) {
        super(map);
    }

    public OrdShoppingCart(String id) {
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
    public OrdShoppingCart setId(String id) {
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
    public OrdShoppingCart setFkClienteleId(String fkClienteleId) {
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
    public OrdShoppingCart setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
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
    public OrdShoppingCart setProductSubSku(String productSubSku) {
        set(F_PRODUCT_SUB_SKU, productSubSku);
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
    public OrdShoppingCart setFkProductId(String fkProductId) {
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
    public OrdShoppingCart setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
    * @return prduct_pic_image_url to prductPicImageUrl 商品预览图<BR/>
    */
    public String getPrductPicImageUrl() {
        return getTypedValue(F_PRDUCT_PIC_IMAGE_URL, String.class);
    }
    /**
    * @param prductPicImageUrl to prduct_pic_image_url 商品预览图 set
    */
    public OrdShoppingCart setPrductPicImageUrl(String prductPicImageUrl) {
        set(F_PRDUCT_PIC_IMAGE_URL, prductPicImageUrl);
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
    public OrdShoppingCart setProductMallPrice(Long productMallPrice) {
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
    public OrdShoppingCart setProductMarketPrice(Long productMarketPrice) {
        set(F_PRODUCT_MARKET_PRICE, productMarketPrice);
        return this;
    }
    /**
    * @return product_promotion_price to productPromotionPrice 商品促销价格<BR/>
    */
    public Long getProductPromotionPrice() {
        return getTypedValue(F_PRODUCT_PROMOTION_PRICE, Long.class);
    }
    /**
    * @param productPromotionPrice to product_promotion_price 商品促销价格 set
    */
    public OrdShoppingCart setProductPromotionPrice(Long productPromotionPrice) {
        set(F_PRODUCT_PROMOTION_PRICE, productPromotionPrice);
        return this;
    }
    /**
    * @return fk_activity_id to fkActivityId 促销活动编号<BR/>
    */
    public String getFkActivityId() {
        return getTypedValue(F_FK_ACTIVITY_ID, String.class);
    }
    /**
    * @param fkActivityId to fk_activity_id 促销活动编号 set
    */
    public OrdShoppingCart setFkActivityId(String fkActivityId) {
        set(F_FK_ACTIVITY_ID, fkActivityId);
        return this;
    }
    /**
    * @return activity_name to activityName 促销活动名称<BR/>
    */
    public String getActivityName() {
        return getTypedValue(F_ACTIVITY_NAME, String.class);
    }
    /**
    * @param activityName to activity_name 促销活动名称 set
    */
    public OrdShoppingCart setActivityName(String activityName) {
        set(F_ACTIVITY_NAME, activityName);
        return this;
    }
    /**
    * @return discount_type_name to discountTypeName 优惠类型名称(折扣、抵扣)<BR/>
    */
    public String getDiscountTypeName() {
        return getTypedValue(F_DISCOUNT_TYPE_NAME, String.class);
    }
    /**
    * @param discountTypeName to discount_type_name 优惠类型名称(折扣、抵扣) set
    */
    public OrdShoppingCart setDiscountTypeName(String discountTypeName) {
        set(F_DISCOUNT_TYPE_NAME, discountTypeName);
        return this;
    }
    /**
    * @return discount_value to discountValue 优惠数据<BR/>
    */
    public Long getDiscountValue() {
        return getTypedValue(F_DISCOUNT_VALUE, Long.class);
    }
    /**
    * @param discountValue to discount_value 优惠数据 set
    */
    public OrdShoppingCart setDiscountValue(Long discountValue) {
        set(F_DISCOUNT_VALUE, discountValue);
        return this;
    }
    /**
    * @return total_product_number to totalProductNumber 商品数量<BR/>
    */
    public Integer getTotalProductNumber() {
        return getTypedValue(F_TOTAL_PRODUCT_NUMBER, Integer.class);
    }
    /**
    * @param totalProductNumber to total_product_number 商品数量 set
    */
    public OrdShoppingCart setTotalProductNumber(Integer totalProductNumber) {
        set(F_TOTAL_PRODUCT_NUMBER, totalProductNumber);
        return this;
    }
    /**
    * @return total_vol to totalVol 商品总重量<BR/>
    */
    public Long getTotalVol() {
        return getTypedValue(F_TOTAL_VOL, Long.class);
    }
    /**
    * @param totalVol to total_vol 商品总重量 set
    */
    public OrdShoppingCart setTotalVol(Long totalVol) {
        set(F_TOTAL_VOL, totalVol);
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
    public OrdShoppingCart setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return batch_number to batchNumber 购物批次号<BR/>
    */
    public String getBatchNumber() {
        return getTypedValue(F_BATCH_NUMBER, String.class);
    }
    /**
    * @param batchNumber to batch_number 购物批次号 set
    */
    public OrdShoppingCart setBatchNumber(String batchNumber) {
        set(F_BATCH_NUMBER, batchNumber);
        return this;
    }
    /**
    * @return is_gen_balance_accounts to isGenBalanceAccounts 是否已生成结算单<BR/>
    */
    public Integer getIsGenBalanceAccounts() {
        return getTypedValue(F_IS_GEN_BALANCE_ACCOUNTS, Integer.class);
    }
    /**
    * @param isGenBalanceAccounts to is_gen_balance_accounts 是否已生成结算单 set
    */
    public OrdShoppingCart setIsGenBalanceAccounts(Integer isGenBalanceAccounts) {
        set(F_IS_GEN_BALANCE_ACCOUNTS, isGenBalanceAccounts);
        return this;
    }
    /**
    * @return is_gen_return_order to isGenReturnOrder 是否已生成召回单<BR/>
    */
    public Integer getIsGenReturnOrder() {
        return getTypedValue(F_IS_GEN_RETURN_ORDER, Integer.class);
    }
    /**
    * @param isGenReturnOrder to is_gen_return_order 是否已生成召回单 set
    */
    public OrdShoppingCart setIsGenReturnOrder(Integer isGenReturnOrder) {
        set(F_IS_GEN_RETURN_ORDER, isGenReturnOrder);
        return this;
    }
    /**
    * @return is_gen_order to isGenOrder 是否已生成订单<BR/>
    */
    public Integer getIsGenOrder() {
        return getTypedValue(F_IS_GEN_ORDER, Integer.class);
    }
    /**
    * @param isGenOrder to is_gen_order 是否已生成订单 set
    */
    public OrdShoppingCart setIsGenOrder(Integer isGenOrder) {
        set(F_IS_GEN_ORDER, isGenOrder);
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
    public OrdShoppingCart setRemark(String remark) {
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
    public OrdShoppingCart setAddTime(Long addTime) {
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
    public OrdShoppingCart setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
    * @return add_user_name to addUserName 添加人名称<BR/>
    */
    public String getAddUserName() {
        return getTypedValue(F_ADD_USER_NAME, String.class);
    }
    /**
    * @param addUserName to add_user_name 添加人名称 set
    */
    public OrdShoppingCart setAddUserName(String addUserName) {
        set(F_ADD_USER_NAME, addUserName);
        return this;
    }
    /**
    * @return operation_time to operationTime 操作时间<BR/>
    */
    public Long getOperationTime() {
        return getTypedValue(F_OPERATION_TIME, Long.class);
    }
    /**
    * @param operationTime to operation_time 操作时间 set
    */
    public OrdShoppingCart setOperationTime(Long operationTime) {
        set(F_OPERATION_TIME, operationTime);
        return this;
    }
    /**
    * @return operation_user_id to operationUserId 操作人编号<BR/>
    */
    public String getOperationUserId() {
        return getTypedValue(F_OPERATION_USER_ID, String.class);
    }
    /**
    * @param operationUserId to operation_user_id 操作人编号 set
    */
    public OrdShoppingCart setOperationUserId(String operationUserId) {
        set(F_OPERATION_USER_ID, operationUserId);
        return this;
    }
    /**
    * @return operation_user_name to operationUserName 操作人名称<BR/>
    */
    public String getOperationUserName() {
        return getTypedValue(F_OPERATION_USER_NAME, String.class);
    }
    /**
    * @param operationUserName to operation_user_name 操作人名称 set
    */
    public OrdShoppingCart setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
    * @return is_del to isDel 是否删除<BR/>
    */
    public Integer getIsDel() {
        return getTypedValue(F_IS_DEL, Integer.class);
    }
    /**
    * @param isDel to is_del 是否删除 set
    */
    public OrdShoppingCart setIsDel(Integer isDel) {
        set(F_IS_DEL, isDel);
        return this;
    }
    /**
    * @return product_spec to productSpec 商品规格串<BR/>
    */
    public String getProductSpec() {
        return getTypedValue(F_PRODUCT_SPEC, String.class);
    }
    /**
    * @param productSpec to product_spec 商品规格串 set
    */
    public OrdShoppingCart setProductSpec(String productSpec) {
        set(F_PRODUCT_SPEC, productSpec);
        return this;
    }
    /**
    * @return selected to selected 是否选中 1是 0否 <BR/>
    */
    public Integer getSelected() {
        return getTypedValue(F_SELECTED, Integer.class);
    }
    /**
    * @param selected to selected 是否选中 1是 0否  set
    */
    public OrdShoppingCart setSelected(Integer selected) {
        set(F_SELECTED, selected);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdShoppingCart setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdShoppingCart me(){
        return new OrdShoppingCart();
    }

    private static class Mapper implements RowMapper<OrdShoppingCart> {

        private Supplier<OrdShoppingCart> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdShoppingCart mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            OrdShoppingCart bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setProductSubSku(Utils.toCast(columnsName.contains(F_PRODUCT_SUB_SKU) ? rs.getObject(F_PRODUCT_SUB_SKU) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setPrductPicImageUrl(Utils.toCast(columnsName.contains(F_PRDUCT_PIC_IMAGE_URL) ? rs.getObject(F_PRDUCT_PIC_IMAGE_URL) : null, String.class));
            bean.setProductMallPrice(Utils.toCast(columnsName.contains(F_PRODUCT_MALL_PRICE) ? rs.getObject(F_PRODUCT_MALL_PRICE) : null, Long.class));
            bean.setProductMarketPrice(Utils.toCast(columnsName.contains(F_PRODUCT_MARKET_PRICE) ? rs.getObject(F_PRODUCT_MARKET_PRICE) : null, Long.class));
            bean.setProductPromotionPrice(Utils.toCast(columnsName.contains(F_PRODUCT_PROMOTION_PRICE) ? rs.getObject(F_PRODUCT_PROMOTION_PRICE) : null, Long.class));
            bean.setFkActivityId(Utils.toCast(columnsName.contains(F_FK_ACTIVITY_ID) ? rs.getObject(F_FK_ACTIVITY_ID) : null, String.class));
            bean.setActivityName(Utils.toCast(columnsName.contains(F_ACTIVITY_NAME) ? rs.getObject(F_ACTIVITY_NAME) : null, String.class));
            bean.setDiscountTypeName(Utils.toCast(columnsName.contains(F_DISCOUNT_TYPE_NAME) ? rs.getObject(F_DISCOUNT_TYPE_NAME) : null, String.class));
            bean.setDiscountValue(Utils.toCast(columnsName.contains(F_DISCOUNT_VALUE) ? rs.getObject(F_DISCOUNT_VALUE) : null, Long.class));
            bean.setTotalProductNumber(Utils.toCast(columnsName.contains(F_TOTAL_PRODUCT_NUMBER) ? rs.getObject(F_TOTAL_PRODUCT_NUMBER) : null, Integer.class));
            bean.setTotalVol(Utils.toCast(columnsName.contains(F_TOTAL_VOL) ? rs.getObject(F_TOTAL_VOL) : null, Long.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setBatchNumber(Utils.toCast(columnsName.contains(F_BATCH_NUMBER) ? rs.getObject(F_BATCH_NUMBER) : null, String.class));
            bean.setIsGenBalanceAccounts(Utils.toCast(columnsName.contains(F_IS_GEN_BALANCE_ACCOUNTS) ? rs.getObject(F_IS_GEN_BALANCE_ACCOUNTS) : null, Integer.class));
            bean.setIsGenReturnOrder(Utils.toCast(columnsName.contains(F_IS_GEN_RETURN_ORDER) ? rs.getObject(F_IS_GEN_RETURN_ORDER) : null, Integer.class));
            bean.setIsGenOrder(Utils.toCast(columnsName.contains(F_IS_GEN_ORDER) ? rs.getObject(F_IS_GEN_ORDER) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddUserName(Utils.toCast(columnsName.contains(F_ADD_USER_NAME) ? rs.getObject(F_ADD_USER_NAME) : null, String.class));
            bean.setOperationTime(Utils.toCast(columnsName.contains(F_OPERATION_TIME) ? rs.getObject(F_OPERATION_TIME) : null, Long.class));
            bean.setOperationUserId(Utils.toCast(columnsName.contains(F_OPERATION_USER_ID) ? rs.getObject(F_OPERATION_USER_ID) : null, String.class));
            bean.setOperationUserName(Utils.toCast(columnsName.contains(F_OPERATION_USER_NAME) ? rs.getObject(F_OPERATION_USER_NAME) : null, String.class));
            bean.setIsDel(Utils.toCast(columnsName.contains(F_IS_DEL) ? rs.getObject(F_IS_DEL) : null, Integer.class));
            bean.setProductSpec(Utils.toCast(columnsName.contains(F_PRODUCT_SPEC) ? rs.getObject(F_PRODUCT_SPEC) : null, String.class));
            bean.setSelected(Utils.toCast(columnsName.contains(F_SELECTED) ? rs.getObject(F_SELECTED) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdShoppingCart> newMapper(){
        return newMapper(OrdShoppingCart::new);
    }

    public RowMapper<OrdShoppingCart> newMapper(Supplier<OrdShoppingCart> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdShoppingCart {
        @Override
        public abstract RowMapper<OrdShoppingCart> newMapper();
    }
}
