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
* Date:        10:55 2021/05/20
* Version:     1.0
* Description: EvaProProduct实体
*/
@SuppressWarnings("all")
public class EvaProProduct extends BaseBean<EvaProProduct> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "eva_pro_product";

    /**
    * id
    */
    public static final String F_ID = "id";
    /**
    * 商品名称
    */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
    * 商品SKU
    */
    public static final String F_PRODUCT_SKU = "product_sku";
    /**
    * 商品视频地址
    */
    public static final String F_PRODUCT_VIDEO_URL = "product_video_url";
    /**
    * 商品视频预览图
    */
    public static final String F_PRODUCT_VIDEO_IMAGE_URL = "product_video_image_url";
    /**
    * 商品预览图
    */
    public static final String F_PRODUCT_PIC_IMAGE_URL = "product_pic_image_url";
    /**
    * 商品简介
    */
    public static final String F_PRODUCT_DESCRIPTION = "product_description";
    /**
    * 是否上架
    */
    public static final String F_IS_SHELVE = "is_shelve";
    /**
    * 上架时间
    */
    public static final String F_SHELVE_TIME = "shelve_time";
    /**
    * 销量
    */
    public static final String F_SALES = "sales";
    /**
    * 关键词
    */
    public static final String F_KEYWORDS = "keywords";
    /**
    * 现价
    */
    public static final String F_PRICE = "price";
    /**
    * 原价
    */
    public static final String F_ORIGINAL_PRICE = "original_price";
    /**
    * 亚马逊地址
    */
    public static final String F_AMAZON_URL = "amazon_url";
    /**
    * 亚马逊卖家
    */
    public static final String F_AMAZON_SELLER = "amazon_seller";
    /**
    * 购买提示
    */
    public static final String F_TIPS = "tips";
    /**
    * 回扣
    */
    public static final String F_REBATES = "rebates";
    /**
    * 返现活动总次数
    */
    public static final String F_TRIALS_TOTAL_TIMES = "trials_total_times";
    /**
    * 返现活动已有次数
    */
    public static final String F_TRIALS_TIMES = "trials_times";
    /**
    * 是否启动
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";
    /**
    * 添加人编号
    */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 国家id
    */
    public static final String F_COUNTRY_ID = "country_id";
    /**
    * 标签编号
    */
    public static final String F_FK_TAG_ID = "fk_tag_id";
    /**
    * 订单定时
    */
    public static final String F_ORDER_TIMING = "order_timing";
    /**
    * 下单模式(keyword、link)
    */
    public static final String F_BUYING_PATTERNS = "buying_patterns";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_PRODUCT_SKU, null);
        put(F_PRODUCT_VIDEO_URL, null);
        put(F_PRODUCT_VIDEO_IMAGE_URL, null);
        put(F_PRODUCT_PIC_IMAGE_URL, null);
        put(F_PRODUCT_DESCRIPTION, null);
        put(F_IS_SHELVE, null);
        put(F_SHELVE_TIME, null);
        put(F_SALES, null);
        put(F_KEYWORDS, null);
        put(F_PRICE, null);
        put(F_ORIGINAL_PRICE, null);
        put(F_AMAZON_URL, null);
        put(F_AMAZON_SELLER, null);
        put(F_TIPS, null);
        put(F_REBATES, null);
        put(F_TRIALS_TOTAL_TIMES, null);
        put(F_TRIALS_TIMES, null);
        put(F_IS_ENABLE, null);
        put(F_REMARK, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_TIME, null);
        put(F_COUNTRY_ID, null);
        put(F_FK_TAG_ID, null);
        put(F_ORDER_TIMING, null);
        put(F_BUYING_PATTERNS, null);
    }

    public EvaProProduct() {
        super();
    }

    public EvaProProduct(Map<String, Object> map) {
        super(map);
    }

    public EvaProProduct(String id) {
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
    public EvaProProduct setId(String id) {
        set(F_ID, id);
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
    public EvaProProduct setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
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
    public EvaProProduct setProductSku(String productSku) {
        set(F_PRODUCT_SKU, productSku);
        return this;
    }
    /**
    * @return product_video_url to productVideoUrl 商品视频地址<BR/>
    */
    public String getProductVideoUrl() {
        return getTypedValue(F_PRODUCT_VIDEO_URL, String.class);
    }
    /**
    * @param productVideoUrl to product_video_url 商品视频地址 set
    */
    public EvaProProduct setProductVideoUrl(String productVideoUrl) {
        set(F_PRODUCT_VIDEO_URL, productVideoUrl);
        return this;
    }
    /**
    * @return product_video_image_url to productVideoImageUrl 商品视频预览图<BR/>
    */
    public String getProductVideoImageUrl() {
        return getTypedValue(F_PRODUCT_VIDEO_IMAGE_URL, String.class);
    }
    /**
    * @param productVideoImageUrl to product_video_image_url 商品视频预览图 set
    */
    public EvaProProduct setProductVideoImageUrl(String productVideoImageUrl) {
        set(F_PRODUCT_VIDEO_IMAGE_URL, productVideoImageUrl);
        return this;
    }
    /**
    * @return product_pic_image_url to productPicImageUrl 商品预览图<BR/>
    */
    public String getProductPicImageUrl() {
        return getTypedValue(F_PRODUCT_PIC_IMAGE_URL, String.class);
    }
    /**
    * @param productPicImageUrl to product_pic_image_url 商品预览图 set
    */
    public EvaProProduct setProductPicImageUrl(String productPicImageUrl) {
        set(F_PRODUCT_PIC_IMAGE_URL, productPicImageUrl);
        return this;
    }
    /**
    * @return product_description to productDescription 商品简介<BR/>
    */
    public String getProductDescription() {
        return getTypedValue(F_PRODUCT_DESCRIPTION, String.class);
    }
    /**
    * @param productDescription to product_description 商品简介 set
    */
    public EvaProProduct setProductDescription(String productDescription) {
        set(F_PRODUCT_DESCRIPTION, productDescription);
        return this;
    }
    /**
    * @return is_shelve to isShelve 是否上架<BR/>
    */
    public Integer getIsShelve() {
        return getTypedValue(F_IS_SHELVE, Integer.class);
    }
    /**
    * @param isShelve to is_shelve 是否上架 set
    */
    public EvaProProduct setIsShelve(Integer isShelve) {
        set(F_IS_SHELVE, isShelve);
        return this;
    }
    /**
    * @return shelve_time to shelveTime 上架时间<BR/>
    */
    public Long getShelveTime() {
        return getTypedValue(F_SHELVE_TIME, Long.class);
    }
    /**
    * @param shelveTime to shelve_time 上架时间 set
    */
    public EvaProProduct setShelveTime(Long shelveTime) {
        set(F_SHELVE_TIME, shelveTime);
        return this;
    }
    /**
    * @return sales to sales 销量<BR/>
    */
    public Integer getSales() {
        return getTypedValue(F_SALES, Integer.class);
    }
    /**
    * @param sales to sales 销量 set
    */
    public EvaProProduct setSales(Integer sales) {
        set(F_SALES, sales);
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
    public EvaProProduct setKeywords(String keywords) {
        set(F_KEYWORDS, keywords);
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
    public EvaProProduct setPrice(Long price) {
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
    public EvaProProduct setOriginalPrice(Long originalPrice) {
        set(F_ORIGINAL_PRICE, originalPrice);
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
    public EvaProProduct setAmazonUrl(String amazonUrl) {
        set(F_AMAZON_URL, amazonUrl);
        return this;
    }
    /**
    * @return amazon_seller to amazonSeller 亚马逊卖家<BR/>
    */
    public String getAmazonSeller() {
        return getTypedValue(F_AMAZON_SELLER, String.class);
    }
    /**
    * @param amazonSeller to amazon_seller 亚马逊卖家 set
    */
    public EvaProProduct setAmazonSeller(String amazonSeller) {
        set(F_AMAZON_SELLER, amazonSeller);
        return this;
    }
    /**
    * @return tips to tips 购买提示<BR/>
    */
    public String getTips() {
        return getTypedValue(F_TIPS, String.class);
    }
    /**
    * @param tips to tips 购买提示 set
    */
    public EvaProProduct setTips(String tips) {
        set(F_TIPS, tips);
        return this;
    }
    /**
    * @return rebates to rebates 回扣<BR/>
    */
    public Long getRebates() {
        return getTypedValue(F_REBATES, Long.class);
    }
    /**
    * @param rebates to rebates 回扣 set
    */
    public EvaProProduct setRebates(Long rebates) {
        set(F_REBATES, rebates);
        return this;
    }
    /**
    * @return trials_total_times to trialsTotalTimes 返现活动总次数<BR/>
    */
    public Integer getTrialsTotalTimes() {
        return getTypedValue(F_TRIALS_TOTAL_TIMES, Integer.class);
    }
    /**
    * @param trialsTotalTimes to trials_total_times 返现活动总次数 set
    */
    public EvaProProduct setTrialsTotalTimes(Integer trialsTotalTimes) {
        set(F_TRIALS_TOTAL_TIMES, trialsTotalTimes);
        return this;
    }
    /**
    * @return trials_times to trialsTimes 返现活动已有次数<BR/>
    */
    public Integer getTrialsTimes() {
        return getTypedValue(F_TRIALS_TIMES, Integer.class);
    }
    /**
    * @param trialsTimes to trials_times 返现活动已有次数 set
    */
    public EvaProProduct setTrialsTimes(Integer trialsTimes) {
        set(F_TRIALS_TIMES, trialsTimes);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启动<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启动 set
    */
    public EvaProProduct setIsEnable(Integer isEnable) {
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
    public EvaProProduct setRemark(String remark) {
        set(F_REMARK, remark);
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
    public EvaProProduct setAddUserId(String addUserId) {
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
    public EvaProProduct setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return country_id to countryId 国家id<BR/>
    */
    public String getCountryId() {
        return getTypedValue(F_COUNTRY_ID, String.class);
    }
    /**
    * @param countryId to country_id 国家id set
    */
    public EvaProProduct setCountryId(String countryId) {
        set(F_COUNTRY_ID, countryId);
        return this;
    }
    /**
    * @return fk_tag_id to fkTagId 标签编号<BR/>
    */
    public String getFkTagId() {
        return getTypedValue(F_FK_TAG_ID, String.class);
    }
    /**
    * @param fkTagId to fk_tag_id 标签编号 set
    */
    public EvaProProduct setFkTagId(String fkTagId) {
        set(F_FK_TAG_ID, fkTagId);
        return this;
    }
    /**
    * @return order_timing to orderTiming 订单定时<BR/>
    */
    public Integer getOrderTiming() {
        return getTypedValue(F_ORDER_TIMING, Integer.class);
    }
    /**
    * @param orderTiming to order_timing 订单定时 set
    */
    public EvaProProduct setOrderTiming(Integer orderTiming) {
        set(F_ORDER_TIMING, orderTiming);
        return this;
    }
    /**
    * @return buying_patterns to buyingPatterns 下单模式(keyword、link)<BR/>
    */
    public String getBuyingPatterns() {
        return getTypedValue(F_BUYING_PATTERNS, String.class);
    }
    /**
    * @param buyingPatterns to buying_patterns 下单模式(keyword、link) set
    */
    public EvaProProduct setBuyingPatterns(String buyingPatterns) {
        set(F_BUYING_PATTERNS, buyingPatterns);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaProProduct setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaProProduct me(){
        return new EvaProProduct();
    }

    private static class Mapper implements RowMapper<EvaProProduct> {

        private Supplier<EvaProProduct> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaProProduct mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaProProduct bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setProductSku(Utils.toCast(columnsName.contains(F_PRODUCT_SKU) ? rs.getObject(F_PRODUCT_SKU) : null, String.class));
            bean.setProductVideoUrl(Utils.toCast(columnsName.contains(F_PRODUCT_VIDEO_URL) ? rs.getObject(F_PRODUCT_VIDEO_URL) : null, String.class));
            bean.setProductVideoImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_VIDEO_IMAGE_URL) ? rs.getObject(F_PRODUCT_VIDEO_IMAGE_URL) : null, String.class));
            bean.setProductPicImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_PIC_IMAGE_URL) ? rs.getObject(F_PRODUCT_PIC_IMAGE_URL) : null, String.class));
            bean.setProductDescription(Utils.toCast(columnsName.contains(F_PRODUCT_DESCRIPTION) ? rs.getObject(F_PRODUCT_DESCRIPTION) : null, String.class));
            bean.setIsShelve(Utils.toCast(columnsName.contains(F_IS_SHELVE) ? rs.getObject(F_IS_SHELVE) : null, Integer.class));
            bean.setShelveTime(Utils.toCast(columnsName.contains(F_SHELVE_TIME) ? rs.getObject(F_SHELVE_TIME) : null, Long.class));
            bean.setSales(Utils.toCast(columnsName.contains(F_SALES) ? rs.getObject(F_SALES) : null, Integer.class));
            bean.setKeywords(Utils.toCast(columnsName.contains(F_KEYWORDS) ? rs.getObject(F_KEYWORDS) : null, String.class));
            bean.setPrice(Utils.toCast(columnsName.contains(F_PRICE) ? rs.getObject(F_PRICE) : null, Long.class));
            bean.setOriginalPrice(Utils.toCast(columnsName.contains(F_ORIGINAL_PRICE) ? rs.getObject(F_ORIGINAL_PRICE) : null, Long.class));
            bean.setAmazonUrl(Utils.toCast(columnsName.contains(F_AMAZON_URL) ? rs.getObject(F_AMAZON_URL) : null, String.class));
            bean.setAmazonSeller(Utils.toCast(columnsName.contains(F_AMAZON_SELLER) ? rs.getObject(F_AMAZON_SELLER) : null, String.class));
            bean.setTips(Utils.toCast(columnsName.contains(F_TIPS) ? rs.getObject(F_TIPS) : null, String.class));
            bean.setRebates(Utils.toCast(columnsName.contains(F_REBATES) ? rs.getObject(F_REBATES) : null, Long.class));
            bean.setTrialsTotalTimes(Utils.toCast(columnsName.contains(F_TRIALS_TOTAL_TIMES) ? rs.getObject(F_TRIALS_TOTAL_TIMES) : null, Integer.class));
            bean.setTrialsTimes(Utils.toCast(columnsName.contains(F_TRIALS_TIMES) ? rs.getObject(F_TRIALS_TIMES) : null, Integer.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setCountryId(Utils.toCast(columnsName.contains(F_COUNTRY_ID) ? rs.getObject(F_COUNTRY_ID) : null, String.class));
            bean.setFkTagId(Utils.toCast(columnsName.contains(F_FK_TAG_ID) ? rs.getObject(F_FK_TAG_ID) : null, String.class));
            bean.setOrderTiming(Utils.toCast(columnsName.contains(F_ORDER_TIMING) ? rs.getObject(F_ORDER_TIMING) : null, Integer.class));
            bean.setBuyingPatterns(Utils.toCast(columnsName.contains(F_BUYING_PATTERNS) ? rs.getObject(F_BUYING_PATTERNS) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaProProduct> newMapper(){
        return newMapper(EvaProProduct::new);
    }

    public RowMapper<EvaProProduct> newMapper(Supplier<EvaProProduct> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaProProduct {
        @Override
        public abstract RowMapper<EvaProProduct> newMapper();
    }
}
