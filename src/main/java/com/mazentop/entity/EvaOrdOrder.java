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
* Date:        17:02 2021/05/18
* Version:     1.0
* Description: EvaOrdOrder实体
*/
@SuppressWarnings("all")
public class EvaOrdOrder extends BaseBean<EvaOrdOrder> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "eva_ord_order";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 内部订单号
    */
    public static final String F_ORDER_NO = "order_no";
    /**
    * 订单号
    */
    public static final String F_LOCALHOST_SN = "localhost_sn";
    /**
    * 用户id
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 商品id
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 商品SKU
    */
    public static final String F_PRODUCT_SKU = "product_sku";
    /**
    * 商品名称
    */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
    * 商品数量
    */
    public static final String F_PRODUCT_NUM = "product_num";
    /**
    * 商品预览图
    */
    public static final String F_PRODUCT_IMAGE_URL = "product_image_url";
    /**
    * 现价
    */
    public static final String F_PRICE = "price";
    /**
    * 原价
    */
    public static final String F_ORIGINAL_PRICE = "original_price";
    /**
    * 回扣
    */
    public static final String F_REBATE = "rebate";
    /**
    * 订单状态 1-待处理 2-亚马逊待审核 3-亚马逊信息审核通过 4-亚马逊信息审核驳回 5-返现待审核 6-返现审核通过 7-返现审核驳回 8-关闭订单
    */
    public static final String F_STATUS = "status";
    /**
    * 添加人编号
    */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 是否有效
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 亚马逊地址
    */
    public static final String F_AMAZON_URL = "amazon_url";
    /**
    * 亚马逊个人店铺信息
    */
    public static final String F_AMAZON_STORE_URL = "amazon_store_url";
    /**
    * 亚马逊订单号
    */
    public static final String F_AMAZON_ORDER_NO = "amazon_order_no";
    /**
    * 亚马逊订单截图
    */
    public static final String F_AMAZON_ORDER_SCREENSHOT = "amazon_order_screenshot";
    /**
    * 申请时间
    */
    public static final String F_APPLY_TIME = "apply_time";
    /**
    * 申请人
    */
    public static final String F_APPLY_USER_ID = "apply_user_id";
    /**
    * 审核人
    */
    public static final String F_REVIEWER_ID = "reviewer_id";
    /**
    * 审核时间
    */
    public static final String F_REVIEWER_TIME = "reviewer_time";
    /**
    * 审核备注
    */
    public static final String F_REVIEWER_REMARKS = "reviewer_remarks";
    /**
    * 订单过期时间
    */
    public static final String F_EXPIRATION_TIME = "expiration_time";
    /**
    * 订单标签
    */
    public static final String F_TAG = "tag";
    /**
    * 评论审核
    */
    public static final String F_COMMENTS_REMARKS = "comments_remarks";
    /**
    * 审核时间
    */
    public static final String F_COMMENTS_TIME = "comments_time";
    /**
    * 审核人
    */
    public static final String F_COMMENTS_ID = "comments_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_ORDER_NO, null);
        put(F_LOCALHOST_SN, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_SKU, null);
        put(F_PRODUCT_NAME, null);
        put(F_PRODUCT_NUM, null);
        put(F_PRODUCT_IMAGE_URL, null);
        put(F_PRICE, null);
        put(F_ORIGINAL_PRICE, null);
        put(F_REBATE, null);
        put(F_STATUS, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_TIME, null);
        put(F_IS_ENABLE, null);
        put(F_AMAZON_URL, null);
        put(F_AMAZON_STORE_URL, null);
        put(F_AMAZON_ORDER_NO, null);
        put(F_AMAZON_ORDER_SCREENSHOT, null);
        put(F_APPLY_TIME, null);
        put(F_APPLY_USER_ID, null);
        put(F_REVIEWER_ID, null);
        put(F_REVIEWER_TIME, null);
        put(F_REVIEWER_REMARKS, null);
        put(F_EXPIRATION_TIME, null);
        put(F_TAG, null);
        put(F_COMMENTS_REMARKS, null);
        put(F_COMMENTS_TIME, null);
        put(F_COMMENTS_ID, null);
    }

    public EvaOrdOrder() {
        super();
    }

    public EvaOrdOrder(Map<String, Object> map) {
        super(map);
    }

    public EvaOrdOrder(String id) {
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
    public EvaOrdOrder setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return order_no to orderNo 内部订单号<BR/>
    */
    public String getOrderNo() {
        return getTypedValue(F_ORDER_NO, String.class);
    }
    /**
    * @param orderNo to order_no 内部订单号 set
    */
    public EvaOrdOrder setOrderNo(String orderNo) {
        set(F_ORDER_NO, orderNo);
        return this;
    }
    /**
    * @return localhost_sn to localhostSn 订单号<BR/>
    */
    public Long getLocalhostSn() {
        return getTypedValue(F_LOCALHOST_SN, Long.class);
    }
    /**
    * @param localhostSn to localhost_sn 订单号 set
    */
    public EvaOrdOrder setLocalhostSn(Long localhostSn) {
        set(F_LOCALHOST_SN, localhostSn);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 用户id<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 用户id set
    */
    public EvaOrdOrder setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return fk_product_id to fkProductId 商品id<BR/>
    */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
    * @param fkProductId to fk_product_id 商品id set
    */
    public EvaOrdOrder setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
    * @return product_sku to productSku 商品SKU<BR/>
    */
    public String getProductSku() {
        return getTypedValue(F_PRODUCT_SKU, String.class);
    }
    /**
    * @param productSku to product_sku 商品SKU set
    */
    public EvaOrdOrder setProductSku(String productSku) {
        set(F_PRODUCT_SKU, productSku);
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
    public EvaOrdOrder setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
    * @return product_num to productNum 商品数量<BR/>
    */
    public Integer getProductNum() {
        return getTypedValue(F_PRODUCT_NUM, Integer.class);
    }
    /**
    * @param productNum to product_num 商品数量 set
    */
    public EvaOrdOrder setProductNum(Integer productNum) {
        set(F_PRODUCT_NUM, productNum);
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
    public EvaOrdOrder setProductImageUrl(String productImageUrl) {
        set(F_PRODUCT_IMAGE_URL, productImageUrl);
        return this;
    }
    /**
    * @return price to price 现价<BR/>
    */
    public Long getPrice() {
        return getTypedValue(F_PRICE, Long.class);
    }
    /**
    * @param price to price 现价 set
    */
    public EvaOrdOrder setPrice(Long price) {
        set(F_PRICE, price);
        return this;
    }
    /**
    * @return original_price to originalPrice 原价<BR/>
    */
    public Long getOriginalPrice() {
        return getTypedValue(F_ORIGINAL_PRICE, Long.class);
    }
    /**
    * @param originalPrice to original_price 原价 set
    */
    public EvaOrdOrder setOriginalPrice(Long originalPrice) {
        set(F_ORIGINAL_PRICE, originalPrice);
        return this;
    }
    /**
    * @return rebate to rebate 回扣<BR/>
    */
    public Long getRebate() {
        return getTypedValue(F_REBATE, Long.class);
    }
    /**
    * @param rebate to rebate 回扣 set
    */
    public EvaOrdOrder setRebate(Long rebate) {
        set(F_REBATE, rebate);
        return this;
    }
    /**
    * @return status to status 订单状态 1-待处理 2-亚马逊待审核 3-亚马逊信息审核通过 4-亚马逊信息审核驳回 5-返现待审核 6-返现审核通过 7-返现审核驳回 8-关闭订单<BR/>
    */
    public Integer getStatus() {
        return getTypedValue(F_STATUS, Integer.class);
    }
    /**
    * @param status to status 订单状态 1-待处理 2-亚马逊待审核 3-亚马逊信息审核通过 4-亚马逊信息审核驳回 5-返现待审核 6-返现审核通过 7-返现审核驳回 8-关闭订单 set
    */
    public EvaOrdOrder setStatus(Integer status) {
        set(F_STATUS, status);
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
    public EvaOrdOrder setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public EvaOrdOrder setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
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
    public EvaOrdOrder setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return amazon_url to amazonUrl 亚马逊地址<BR/>
    */
    public String getAmazonUrl() {
        return getTypedValue(F_AMAZON_URL, String.class);
    }
    /**
    * @param amazonUrl to amazon_url 亚马逊地址 set
    */
    public EvaOrdOrder setAmazonUrl(String amazonUrl) {
        set(F_AMAZON_URL, amazonUrl);
        return this;
    }
    /**
    * @return amazon_store_url to amazonStoreUrl 亚马逊个人店铺信息<BR/>
    */
    public String getAmazonStoreUrl() {
        return getTypedValue(F_AMAZON_STORE_URL, String.class);
    }
    /**
    * @param amazonStoreUrl to amazon_store_url 亚马逊个人店铺信息 set
    */
    public EvaOrdOrder setAmazonStoreUrl(String amazonStoreUrl) {
        set(F_AMAZON_STORE_URL, amazonStoreUrl);
        return this;
    }
    /**
    * @return amazon_order_no to amazonOrderNo 亚马逊订单号<BR/>
    */
    public String getAmazonOrderNo() {
        return getTypedValue(F_AMAZON_ORDER_NO, String.class);
    }
    /**
    * @param amazonOrderNo to amazon_order_no 亚马逊订单号 set
    */
    public EvaOrdOrder setAmazonOrderNo(String amazonOrderNo) {
        set(F_AMAZON_ORDER_NO, amazonOrderNo);
        return this;
    }
    /**
    * @return amazon_order_screenshot to amazonOrderScreenshot 亚马逊订单截图<BR/>
    */
    public String getAmazonOrderScreenshot() {
        return getTypedValue(F_AMAZON_ORDER_SCREENSHOT, String.class);
    }
    /**
    * @param amazonOrderScreenshot to amazon_order_screenshot 亚马逊订单截图 set
    */
    public EvaOrdOrder setAmazonOrderScreenshot(String amazonOrderScreenshot) {
        set(F_AMAZON_ORDER_SCREENSHOT, amazonOrderScreenshot);
        return this;
    }
    /**
    * @return apply_time to applyTime 申请时间<BR/>
    */
    public Long getApplyTime() {
        return getTypedValue(F_APPLY_TIME, Long.class);
    }
    /**
    * @param applyTime to apply_time 申请时间 set
    */
    public EvaOrdOrder setApplyTime(Long applyTime) {
        set(F_APPLY_TIME, applyTime);
        return this;
    }
    /**
    * @return apply_user_id to applyUserId 申请人<BR/>
    */
    public String getApplyUserId() {
        return getTypedValue(F_APPLY_USER_ID, String.class);
    }
    /**
    * @param applyUserId to apply_user_id 申请人 set
    */
    public EvaOrdOrder setApplyUserId(String applyUserId) {
        set(F_APPLY_USER_ID, applyUserId);
        return this;
    }
    /**
    * @return reviewer_id to reviewerId 审核人<BR/>
    */
    public String getReviewerId() {
        return getTypedValue(F_REVIEWER_ID, String.class);
    }
    /**
    * @param reviewerId to reviewer_id 审核人 set
    */
    public EvaOrdOrder setReviewerId(String reviewerId) {
        set(F_REVIEWER_ID, reviewerId);
        return this;
    }
    /**
    * @return reviewer_time to reviewerTime 审核时间<BR/>
    */
    public Long getReviewerTime() {
        return getTypedValue(F_REVIEWER_TIME, Long.class);
    }
    /**
    * @param reviewerTime to reviewer_time 审核时间 set
    */
    public EvaOrdOrder setReviewerTime(Long reviewerTime) {
        set(F_REVIEWER_TIME, reviewerTime);
        return this;
    }
    /**
    * @return reviewer_remarks to reviewerRemarks 审核备注<BR/>
    */
    public String getReviewerRemarks() {
        return getTypedValue(F_REVIEWER_REMARKS, String.class);
    }
    /**
    * @param reviewerRemarks to reviewer_remarks 审核备注 set
    */
    public EvaOrdOrder setReviewerRemarks(String reviewerRemarks) {
        set(F_REVIEWER_REMARKS, reviewerRemarks);
        return this;
    }
    /**
    * @return expiration_time to expirationTime 订单过期时间<BR/>
    */
    public Long getExpirationTime() {
        return getTypedValue(F_EXPIRATION_TIME, Long.class);
    }
    /**
    * @param expirationTime to expiration_time 订单过期时间 set
    */
    public EvaOrdOrder setExpirationTime(Long expirationTime) {
        set(F_EXPIRATION_TIME, expirationTime);
        return this;
    }
    /**
    * @return tag to tag 订单标签<BR/>
    */
    public String getTag() {
        return getTypedValue(F_TAG, String.class);
    }
    /**
    * @param tag to tag 订单标签 set
    */
    public EvaOrdOrder setTag(String tag) {
        set(F_TAG, tag);
        return this;
    }
    /**
    * @return comments_remarks to commentsRemarks 评论审核<BR/>
    */
    public String getCommentsRemarks() {
        return getTypedValue(F_COMMENTS_REMARKS, String.class);
    }
    /**
    * @param commentsRemarks to comments_remarks 评论审核 set
    */
    public EvaOrdOrder setCommentsRemarks(String commentsRemarks) {
        set(F_COMMENTS_REMARKS, commentsRemarks);
        return this;
    }
    /**
    * @return comments_time to commentsTime 审核时间<BR/>
    */
    public Long getCommentsTime() {
        return getTypedValue(F_COMMENTS_TIME, Long.class);
    }
    /**
    * @param commentsTime to comments_time 审核时间 set
    */
    public EvaOrdOrder setCommentsTime(Long commentsTime) {
        set(F_COMMENTS_TIME, commentsTime);
        return this;
    }
    /**
    * @return comments_id to commentsId 审核人<BR/>
    */
    public String getCommentsId() {
        return getTypedValue(F_COMMENTS_ID, String.class);
    }
    /**
    * @param commentsId to comments_id 审核人 set
    */
    public EvaOrdOrder setCommentsId(String commentsId) {
        set(F_COMMENTS_ID, commentsId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaOrdOrder setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaOrdOrder me(){
        return new EvaOrdOrder();
    }

    private static class Mapper implements RowMapper<EvaOrdOrder> {

        private Supplier<EvaOrdOrder> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaOrdOrder mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaOrdOrder bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setOrderNo(Utils.toCast(columnsName.contains(F_ORDER_NO) ? rs.getObject(F_ORDER_NO) : null, String.class));
            bean.setLocalhostSn(Utils.toCast(columnsName.contains(F_LOCALHOST_SN) ? rs.getObject(F_LOCALHOST_SN) : null, Long.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductSku(Utils.toCast(columnsName.contains(F_PRODUCT_SKU) ? rs.getObject(F_PRODUCT_SKU) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setProductNum(Utils.toCast(columnsName.contains(F_PRODUCT_NUM) ? rs.getObject(F_PRODUCT_NUM) : null, Integer.class));
            bean.setProductImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_IMAGE_URL) ? rs.getObject(F_PRODUCT_IMAGE_URL) : null, String.class));
            bean.setPrice(Utils.toCast(columnsName.contains(F_PRICE) ? rs.getObject(F_PRICE) : null, Long.class));
            bean.setOriginalPrice(Utils.toCast(columnsName.contains(F_ORIGINAL_PRICE) ? rs.getObject(F_ORIGINAL_PRICE) : null, Long.class));
            bean.setRebate(Utils.toCast(columnsName.contains(F_REBATE) ? rs.getObject(F_REBATE) : null, Long.class));
            bean.setStatus(Utils.toCast(columnsName.contains(F_STATUS) ? rs.getObject(F_STATUS) : null, Integer.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setAmazonUrl(Utils.toCast(columnsName.contains(F_AMAZON_URL) ? rs.getObject(F_AMAZON_URL) : null, String.class));
            bean.setAmazonStoreUrl(Utils.toCast(columnsName.contains(F_AMAZON_STORE_URL) ? rs.getObject(F_AMAZON_STORE_URL) : null, String.class));
            bean.setAmazonOrderNo(Utils.toCast(columnsName.contains(F_AMAZON_ORDER_NO) ? rs.getObject(F_AMAZON_ORDER_NO) : null, String.class));
            bean.setAmazonOrderScreenshot(Utils.toCast(columnsName.contains(F_AMAZON_ORDER_SCREENSHOT) ? rs.getObject(F_AMAZON_ORDER_SCREENSHOT) : null, String.class));
            bean.setApplyTime(Utils.toCast(columnsName.contains(F_APPLY_TIME) ? rs.getObject(F_APPLY_TIME) : null, Long.class));
            bean.setApplyUserId(Utils.toCast(columnsName.contains(F_APPLY_USER_ID) ? rs.getObject(F_APPLY_USER_ID) : null, String.class));
            bean.setReviewerId(Utils.toCast(columnsName.contains(F_REVIEWER_ID) ? rs.getObject(F_REVIEWER_ID) : null, String.class));
            bean.setReviewerTime(Utils.toCast(columnsName.contains(F_REVIEWER_TIME) ? rs.getObject(F_REVIEWER_TIME) : null, Long.class));
            bean.setReviewerRemarks(Utils.toCast(columnsName.contains(F_REVIEWER_REMARKS) ? rs.getObject(F_REVIEWER_REMARKS) : null, String.class));
            bean.setExpirationTime(Utils.toCast(columnsName.contains(F_EXPIRATION_TIME) ? rs.getObject(F_EXPIRATION_TIME) : null, Long.class));
            bean.setTag(Utils.toCast(columnsName.contains(F_TAG) ? rs.getObject(F_TAG) : null, String.class));
            bean.setCommentsRemarks(Utils.toCast(columnsName.contains(F_COMMENTS_REMARKS) ? rs.getObject(F_COMMENTS_REMARKS) : null, String.class));
            bean.setCommentsTime(Utils.toCast(columnsName.contains(F_COMMENTS_TIME) ? rs.getObject(F_COMMENTS_TIME) : null, Long.class));
            bean.setCommentsId(Utils.toCast(columnsName.contains(F_COMMENTS_ID) ? rs.getObject(F_COMMENTS_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaOrdOrder> newMapper(){
        return newMapper(EvaOrdOrder::new);
    }

    public RowMapper<EvaOrdOrder> newMapper(Supplier<EvaOrdOrder> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaOrdOrder {
        @Override
        public abstract RowMapper<EvaOrdOrder> newMapper();
    }
}
