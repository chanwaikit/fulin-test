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
* Date:        16:09 2020/12/16
* Version:     1.0
* Description: ProProductMaster实体
*/
@SuppressWarnings("all")
public class ProProductMaster extends BaseBean<ProProductMaster> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_product_master";

    /**
    * 编号
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
    * 商品条码
    */
    public static final String F_PRODUCT_BARCODE = "product_barcode";
    /**
    * 商品分类编号
    */
    public static final String F_FK_PRODUCT_TYPE_ID = "fk_product_type_id";
    /**
    * 商品简介
    */
    public static final String F_PRODUCT_DESCRIPTION = "product_description";
    /**
    * 商品介绍
    */
    public static final String F_PRODUCT_CONTENT = "product_content";
    /**
    * 商品app介绍
    */
    public static final String F_PRODUCT_APP_CONTENT = "product_app_content";
    /**
    * 商品保证
    */
    public static final String F_PRODUCT_GUARANTEES = "product_guarantees";
    /**
    * 商品尺寸测量
    */
    public static final String F_PRODUCT_SIZE_MEASUREMENT = "product_size_measurement";
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
    public static final String F_PRDUCT_PIC_IMAGE_URL = "prduct_pic_image_url";
    /**
    * 是否启动
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 是否上架
    */
    public static final String F_IS_SHELVE = "is_shelve";
    /**
    * 商品产地
    */
    public static final String F_PRODUCT_AREA = "product_area";
    /**
    * 海关商品类型
    */
    public static final String F_CUS_PRODUCT_TYPE = "cus_product_type";
    /**
    * 海关报关价
    */
    public static final String F_CUS_PRICE = "cus_price";
    /**
    * HSCode码
    */
    public static final String F_CUS_HS_CODE = "cus_hs_code";
    /**
    * 海关中文描述
    */
    public static final String F_CUS_ZH_DESCRIPTION = "cus_zh_description";
    /**
    * 海关英文描述
    */
    public static final String F_CUS_EN_DESCRIPTION = "cus_en_description";
    /**
    * 长
    */
    public static final String F_LENGTH = "length";
    /**
    * 宽
    */
    public static final String F_WIDTH = "width";
    /**
    * 高
    */
    public static final String F_HEIGHT = "height";
    /**
    * 总体积
    */
    public static final String F_TOTAL_VOL = "total_vol";
    /**
    * 总重量
    */
    public static final String F_TOTAL_WEIGHT = "total_weight";
    /**
    * 净重
    */
    public static final String F_NET_WEIGHT = "net_weight";
    /**
    * 是否免运费
    */
    public static final String F_IS_TRANS_FREE = "is_trans_free";
    /**
    * 运费模板编号
    */
    public static final String F_FK_TRANS_TEMPLATE_ID = "fk_trans_template_id";
    /**
    * 运费模板名称
    */
    public static final String F_TRANS_TEMPLATE_NAME = "trans_template_name";
    /**
    * 品牌编码
    */
    public static final String F_FK_BRAND_ID = "fk_brand_id";
    /**
    * 同级排序
    */
    public static final String F_SORT = "sort";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";
    /**
    * 添加人编号
    */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
    * 币种
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 是否首页显示 0否 1是
    */
    public static final String F_IS_SHOW_INDEX = "is_show_index";
    /**
    * 是否置頂 0否 1是
    */
    public static final String F_IS_SHOW_TOP = "is_show_top";
    /**
    * 是否推薦 0否 1是
    */
    public static final String F_IS_SHOW_RECOMMEND = "is_show_recommend";
    /**
    * 关键词
    */
    public static final String F_KEYWORDS = "keywords";
    /**
    * 是否单品 0否 1是
    */
    public static final String F_IS_SINGLE_PRODUCT = "is_single_product";
    /**
    * 上架时间
    */
    public static final String F_SHELVE_TIME = "shelve_time";
    /**
    * 销量
    */
    public static final String F_SALES = "sales";
    /**
    * 标签编号
    */
    public static final String F_FK_TAG_ID = "fk_tag_id";
    /**
    * 服务说明编号
    */
    public static final String F_EXPLAIN_DATA = "explain_data";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 详情页面图片展示方式
    */
    public static final String F_IMAGE_DISPLAY_MODE = "image_display_mode";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_PRODUCT_SKU, null);
        put(F_PRODUCT_BARCODE, null);
        put(F_FK_PRODUCT_TYPE_ID, null);
        put(F_PRODUCT_DESCRIPTION, null);
        put(F_PRODUCT_CONTENT, null);
        put(F_PRODUCT_APP_CONTENT, null);
        put(F_PRODUCT_GUARANTEES, null);
        put(F_PRODUCT_SIZE_MEASUREMENT, null);
        put(F_PRODUCT_VIDEO_URL, null);
        put(F_PRODUCT_VIDEO_IMAGE_URL, null);
        put(F_PRDUCT_PIC_IMAGE_URL, null);
        put(F_IS_ENABLE, null);
        put(F_IS_SHELVE, null);
        put(F_PRODUCT_AREA, null);
        put(F_CUS_PRODUCT_TYPE, null);
        put(F_CUS_PRICE, null);
        put(F_CUS_HS_CODE, null);
        put(F_CUS_ZH_DESCRIPTION, null);
        put(F_CUS_EN_DESCRIPTION, null);
        put(F_LENGTH, null);
        put(F_WIDTH, null);
        put(F_HEIGHT, null);
        put(F_TOTAL_VOL, null);
        put(F_TOTAL_WEIGHT, null);
        put(F_NET_WEIGHT, null);
        put(F_IS_TRANS_FREE, null);
        put(F_FK_TRANS_TEMPLATE_ID, null);
        put(F_TRANS_TEMPLATE_NAME, null);
        put(F_FK_BRAND_ID, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_ADD_USER_ID, null);
        put(F_CURRENCY, null);
        put(F_IS_SHOW_INDEX, null);
        put(F_IS_SHOW_TOP, null);
        put(F_IS_SHOW_RECOMMEND, null);
        put(F_KEYWORDS, null);
        put(F_IS_SINGLE_PRODUCT, null);
        put(F_SHELVE_TIME, null);
        put(F_SALES, null);
        put(F_FK_TAG_ID, null);
        put(F_EXPLAIN_DATA, null);
        put(F_ADD_TIME, null);
        put(F_IMAGE_DISPLAY_MODE, null);
    }

    public ProProductMaster() {
        super();
    }

    public ProProductMaster(Map<String, Object> map) {
        super(map);
    }

    public ProProductMaster(String id) {
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
    public ProProductMaster setId(String id) {
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
    public ProProductMaster setProductName(String productName) {
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
    public ProProductMaster setProductSku(String productSku) {
        set(F_PRODUCT_SKU, productSku);
        return this;
    }
    /**
    * @return product_barcode to productBarcode 商品条码<BR/>
    */
    public String getProductBarcode() {
        return getTypedValue(F_PRODUCT_BARCODE, String.class);
    }
    /**
    * @param productBarcode to product_barcode 商品条码 set
    */
    public ProProductMaster setProductBarcode(String productBarcode) {
        set(F_PRODUCT_BARCODE, productBarcode);
        return this;
    }
    /**
    * @return fk_product_type_id to fkProductTypeId 商品分类编号<BR/>
    */
    public String getFkProductTypeId() {
        return getTypedValue(F_FK_PRODUCT_TYPE_ID, String.class);
    }
    /**
    * @param fkProductTypeId to fk_product_type_id 商品分类编号 set
    */
    public ProProductMaster setFkProductTypeId(String fkProductTypeId) {
        set(F_FK_PRODUCT_TYPE_ID, fkProductTypeId);
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
    public ProProductMaster setProductDescription(String productDescription) {
        set(F_PRODUCT_DESCRIPTION, productDescription);
        return this;
    }
    /**
    * @return product_content to productContent 商品介绍<BR/>
    */
    public String getProductContent() {
        return getTypedValue(F_PRODUCT_CONTENT, String.class);
    }
    /**
    * @param productContent to product_content 商品介绍 set
    */
    public ProProductMaster setProductContent(String productContent) {
        set(F_PRODUCT_CONTENT, productContent);
        return this;
    }
    /**
    * @return product_app_content to productAppContent 商品app介绍<BR/>
    */
    public String getProductAppContent() {
        return getTypedValue(F_PRODUCT_APP_CONTENT, String.class);
    }
    /**
    * @param productAppContent to product_app_content 商品app介绍 set
    */
    public ProProductMaster setProductAppContent(String productAppContent) {
        set(F_PRODUCT_APP_CONTENT, productAppContent);
        return this;
    }
    /**
    * @return product_guarantees to productGuarantees 商品保证<BR/>
    */
    public String getProductGuarantees() {
        return getTypedValue(F_PRODUCT_GUARANTEES, String.class);
    }
    /**
    * @param productGuarantees to product_guarantees 商品保证 set
    */
    public ProProductMaster setProductGuarantees(String productGuarantees) {
        set(F_PRODUCT_GUARANTEES, productGuarantees);
        return this;
    }
    /**
    * @return product_size_measurement to productSizeMeasurement 商品尺寸测量<BR/>
    */
    public String getProductSizeMeasurement() {
        return getTypedValue(F_PRODUCT_SIZE_MEASUREMENT, String.class);
    }
    /**
    * @param productSizeMeasurement to product_size_measurement 商品尺寸测量 set
    */
    public ProProductMaster setProductSizeMeasurement(String productSizeMeasurement) {
        set(F_PRODUCT_SIZE_MEASUREMENT, productSizeMeasurement);
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
    public ProProductMaster setProductVideoUrl(String productVideoUrl) {
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
    public ProProductMaster setProductVideoImageUrl(String productVideoImageUrl) {
        set(F_PRODUCT_VIDEO_IMAGE_URL, productVideoImageUrl);
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
    public ProProductMaster setPrductPicImageUrl(String prductPicImageUrl) {
        set(F_PRDUCT_PIC_IMAGE_URL, prductPicImageUrl);
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
    public ProProductMaster setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
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
    public ProProductMaster setIsShelve(Integer isShelve) {
        set(F_IS_SHELVE, isShelve);
        return this;
    }
    /**
    * @return product_area to productArea 商品产地<BR/>
    */
    public String getProductArea() {
        return getTypedValue(F_PRODUCT_AREA, String.class);
    }
    /**
    * @param productArea to product_area 商品产地 set
    */
    public ProProductMaster setProductArea(String productArea) {
        set(F_PRODUCT_AREA, productArea);
        return this;
    }
    /**
    * @return cus_product_type to cusProductType 海关商品类型<BR/>
    */
    public String getCusProductType() {
        return getTypedValue(F_CUS_PRODUCT_TYPE, String.class);
    }
    /**
    * @param cusProductType to cus_product_type 海关商品类型 set
    */
    public ProProductMaster setCusProductType(String cusProductType) {
        set(F_CUS_PRODUCT_TYPE, cusProductType);
        return this;
    }
    /**
    * @return cus_price to cusPrice 海关报关价<BR/>
    */
    public Long getCusPrice() {
        return getTypedValue(F_CUS_PRICE, Long.class);
    }
    /**
    * @param cusPrice to cus_price 海关报关价 set
    */
    public ProProductMaster setCusPrice(Long cusPrice) {
        set(F_CUS_PRICE, cusPrice);
        return this;
    }
    /**
    * @return cus_hs_code to cusHsCode HSCode码<BR/>
    */
    public String getCusHsCode() {
        return getTypedValue(F_CUS_HS_CODE, String.class);
    }
    /**
    * @param cusHsCode to cus_hs_code HSCode码 set
    */
    public ProProductMaster setCusHsCode(String cusHsCode) {
        set(F_CUS_HS_CODE, cusHsCode);
        return this;
    }
    /**
    * @return cus_zh_description to cusZhDescription 海关中文描述<BR/>
    */
    public String getCusZhDescription() {
        return getTypedValue(F_CUS_ZH_DESCRIPTION, String.class);
    }
    /**
    * @param cusZhDescription to cus_zh_description 海关中文描述 set
    */
    public ProProductMaster setCusZhDescription(String cusZhDescription) {
        set(F_CUS_ZH_DESCRIPTION, cusZhDescription);
        return this;
    }
    /**
    * @return cus_en_description to cusEnDescription 海关英文描述<BR/>
    */
    public String getCusEnDescription() {
        return getTypedValue(F_CUS_EN_DESCRIPTION, String.class);
    }
    /**
    * @param cusEnDescription to cus_en_description 海关英文描述 set
    */
    public ProProductMaster setCusEnDescription(String cusEnDescription) {
        set(F_CUS_EN_DESCRIPTION, cusEnDescription);
        return this;
    }
    /**
    * @return length to length 长<BR/>
    */
    public Long getLength() {
        return getTypedValue(F_LENGTH, Long.class);
    }
    /**
    * @param length to length 长 set
    */
    public ProProductMaster setLength(Long length) {
        set(F_LENGTH, length);
        return this;
    }
    /**
    * @return width to width 宽<BR/>
    */
    public Long getWidth() {
        return getTypedValue(F_WIDTH, Long.class);
    }
    /**
    * @param width to width 宽 set
    */
    public ProProductMaster setWidth(Long width) {
        set(F_WIDTH, width);
        return this;
    }
    /**
    * @return height to height 高<BR/>
    */
    public Long getHeight() {
        return getTypedValue(F_HEIGHT, Long.class);
    }
    /**
    * @param height to height 高 set
    */
    public ProProductMaster setHeight(Long height) {
        set(F_HEIGHT, height);
        return this;
    }
    /**
    * @return total_vol to totalVol 总体积<BR/>
    */
    public Long getTotalVol() {
        return getTypedValue(F_TOTAL_VOL, Long.class);
    }
    /**
    * @param totalVol to total_vol 总体积 set
    */
    public ProProductMaster setTotalVol(Long totalVol) {
        set(F_TOTAL_VOL, totalVol);
        return this;
    }
    /**
    * @return total_weight to totalWeight 总重量<BR/>
    */
    public Long getTotalWeight() {
        return getTypedValue(F_TOTAL_WEIGHT, Long.class);
    }
    /**
    * @param totalWeight to total_weight 总重量 set
    */
    public ProProductMaster setTotalWeight(Long totalWeight) {
        set(F_TOTAL_WEIGHT, totalWeight);
        return this;
    }
    /**
    * @return net_weight to netWeight 净重<BR/>
    */
    public Long getNetWeight() {
        return getTypedValue(F_NET_WEIGHT, Long.class);
    }
    /**
    * @param netWeight to net_weight 净重 set
    */
    public ProProductMaster setNetWeight(Long netWeight) {
        set(F_NET_WEIGHT, netWeight);
        return this;
    }
    /**
    * @return is_trans_free to isTransFree 是否免运费<BR/>
    */
    public Integer getIsTransFree() {
        return getTypedValue(F_IS_TRANS_FREE, Integer.class);
    }
    /**
    * @param isTransFree to is_trans_free 是否免运费 set
    */
    public ProProductMaster setIsTransFree(Integer isTransFree) {
        set(F_IS_TRANS_FREE, isTransFree);
        return this;
    }
    /**
    * @return fk_trans_template_id to fkTransTemplateId 运费模板编号<BR/>
    */
    public String getFkTransTemplateId() {
        return getTypedValue(F_FK_TRANS_TEMPLATE_ID, String.class);
    }
    /**
    * @param fkTransTemplateId to fk_trans_template_id 运费模板编号 set
    */
    public ProProductMaster setFkTransTemplateId(String fkTransTemplateId) {
        set(F_FK_TRANS_TEMPLATE_ID, fkTransTemplateId);
        return this;
    }
    /**
    * @return trans_template_name to transTemplateName 运费模板名称<BR/>
    */
    public String getTransTemplateName() {
        return getTypedValue(F_TRANS_TEMPLATE_NAME, String.class);
    }
    /**
    * @param transTemplateName to trans_template_name 运费模板名称 set
    */
    public ProProductMaster setTransTemplateName(String transTemplateName) {
        set(F_TRANS_TEMPLATE_NAME, transTemplateName);
        return this;
    }
    /**
    * @return fk_brand_id to fkBrandId 品牌编码<BR/>
    */
    public String getFkBrandId() {
        return getTypedValue(F_FK_BRAND_ID, String.class);
    }
    /**
    * @param fkBrandId to fk_brand_id 品牌编码 set
    */
    public ProProductMaster setFkBrandId(String fkBrandId) {
        set(F_FK_BRAND_ID, fkBrandId);
        return this;
    }
    /**
    * @return sort to sort 同级排序<BR/>
    */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
    * @param sort to sort 同级排序 set
    */
    public ProProductMaster setSort(Integer sort) {
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
    public ProProductMaster setRemark(String remark) {
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
    public ProProductMaster setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
    * @return currency to currency 币种<BR/>
    */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
    * @param currency to currency 币种 set
    */
    public ProProductMaster setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return is_show_index to isShowIndex 是否首页显示 0否 1是<BR/>
    */
    public Integer getIsShowIndex() {
        return getTypedValue(F_IS_SHOW_INDEX, Integer.class);
    }
    /**
    * @param isShowIndex to is_show_index 是否首页显示 0否 1是 set
    */
    public ProProductMaster setIsShowIndex(Integer isShowIndex) {
        set(F_IS_SHOW_INDEX, isShowIndex);
        return this;
    }
    /**
    * @return is_show_top to isShowTop 是否置頂 0否 1是<BR/>
    */
    public Integer getIsShowTop() {
        return getTypedValue(F_IS_SHOW_TOP, Integer.class);
    }
    /**
    * @param isShowTop to is_show_top 是否置頂 0否 1是 set
    */
    public ProProductMaster setIsShowTop(Integer isShowTop) {
        set(F_IS_SHOW_TOP, isShowTop);
        return this;
    }
    /**
    * @return is_show_recommend to isShowRecommend 是否推薦 0否 1是<BR/>
    */
    public Integer getIsShowRecommend() {
        return getTypedValue(F_IS_SHOW_RECOMMEND, Integer.class);
    }
    /**
    * @param isShowRecommend to is_show_recommend 是否推薦 0否 1是 set
    */
    public ProProductMaster setIsShowRecommend(Integer isShowRecommend) {
        set(F_IS_SHOW_RECOMMEND, isShowRecommend);
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
    public ProProductMaster setKeywords(String keywords) {
        set(F_KEYWORDS, keywords);
        return this;
    }
    /**
    * @return is_single_product to isSingleProduct 是否单品 0否 1是<BR/>
    */
    public Integer getIsSingleProduct() {
        return getTypedValue(F_IS_SINGLE_PRODUCT, Integer.class);
    }
    /**
    * @param isSingleProduct to is_single_product 是否单品 0否 1是 set
    */
    public ProProductMaster setIsSingleProduct(Integer isSingleProduct) {
        set(F_IS_SINGLE_PRODUCT, isSingleProduct);
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
    public ProProductMaster setShelveTime(Long shelveTime) {
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
    public ProProductMaster setSales(Integer sales) {
        set(F_SALES, sales);
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
    public ProProductMaster setFkTagId(String fkTagId) {
        set(F_FK_TAG_ID, fkTagId);
        return this;
    }
    /**
    * @return explain_data to explainData 服务说明编号<BR/>
    */
    public String getExplainData() {
        return getTypedValue(F_EXPLAIN_DATA, String.class);
    }
    /**
    * @param explainData to explain_data 服务说明编号 set
    */
    public ProProductMaster setExplainData(String explainData) {
        set(F_EXPLAIN_DATA, explainData);
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
    public ProProductMaster setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return image_display_mode to imageDisplayMode 详情页面图片展示方式<BR/>
    */
    public String getImageDisplayMode() {
        return getTypedValue(F_IMAGE_DISPLAY_MODE, String.class);
    }
    /**
    * @param imageDisplayMode to image_display_mode 详情页面图片展示方式 set
    */
    public ProProductMaster setImageDisplayMode(String imageDisplayMode) {
        set(F_IMAGE_DISPLAY_MODE, imageDisplayMode);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductMaster setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductMaster me(){
        return new ProProductMaster();
    }

    private static class Mapper implements RowMapper<ProProductMaster> {

        private Supplier<ProProductMaster> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductMaster mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductMaster bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setProductSku(Utils.toCast(columnsName.contains(F_PRODUCT_SKU) ? rs.getObject(F_PRODUCT_SKU) : null, String.class));
            bean.setProductBarcode(Utils.toCast(columnsName.contains(F_PRODUCT_BARCODE) ? rs.getObject(F_PRODUCT_BARCODE) : null, String.class));
            bean.setFkProductTypeId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_TYPE_ID) ? rs.getObject(F_FK_PRODUCT_TYPE_ID) : null, String.class));
            bean.setProductDescription(Utils.toCast(columnsName.contains(F_PRODUCT_DESCRIPTION) ? rs.getObject(F_PRODUCT_DESCRIPTION) : null, String.class));
            bean.setProductContent(Utils.toCast(columnsName.contains(F_PRODUCT_CONTENT) ? rs.getObject(F_PRODUCT_CONTENT) : null, String.class));
            bean.setProductAppContent(Utils.toCast(columnsName.contains(F_PRODUCT_APP_CONTENT) ? rs.getObject(F_PRODUCT_APP_CONTENT) : null, String.class));
            bean.setProductGuarantees(Utils.toCast(columnsName.contains(F_PRODUCT_GUARANTEES) ? rs.getObject(F_PRODUCT_GUARANTEES) : null, String.class));
            bean.setProductSizeMeasurement(Utils.toCast(columnsName.contains(F_PRODUCT_SIZE_MEASUREMENT) ? rs.getObject(F_PRODUCT_SIZE_MEASUREMENT) : null, String.class));
            bean.setProductVideoUrl(Utils.toCast(columnsName.contains(F_PRODUCT_VIDEO_URL) ? rs.getObject(F_PRODUCT_VIDEO_URL) : null, String.class));
            bean.setProductVideoImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_VIDEO_IMAGE_URL) ? rs.getObject(F_PRODUCT_VIDEO_IMAGE_URL) : null, String.class));
            bean.setPrductPicImageUrl(Utils.toCast(columnsName.contains(F_PRDUCT_PIC_IMAGE_URL) ? rs.getObject(F_PRDUCT_PIC_IMAGE_URL) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setIsShelve(Utils.toCast(columnsName.contains(F_IS_SHELVE) ? rs.getObject(F_IS_SHELVE) : null, Integer.class));
            bean.setProductArea(Utils.toCast(columnsName.contains(F_PRODUCT_AREA) ? rs.getObject(F_PRODUCT_AREA) : null, String.class));
            bean.setCusProductType(Utils.toCast(columnsName.contains(F_CUS_PRODUCT_TYPE) ? rs.getObject(F_CUS_PRODUCT_TYPE) : null, String.class));
            bean.setCusPrice(Utils.toCast(columnsName.contains(F_CUS_PRICE) ? rs.getObject(F_CUS_PRICE) : null, Long.class));
            bean.setCusHsCode(Utils.toCast(columnsName.contains(F_CUS_HS_CODE) ? rs.getObject(F_CUS_HS_CODE) : null, String.class));
            bean.setCusZhDescription(Utils.toCast(columnsName.contains(F_CUS_ZH_DESCRIPTION) ? rs.getObject(F_CUS_ZH_DESCRIPTION) : null, String.class));
            bean.setCusEnDescription(Utils.toCast(columnsName.contains(F_CUS_EN_DESCRIPTION) ? rs.getObject(F_CUS_EN_DESCRIPTION) : null, String.class));
            bean.setLength(Utils.toCast(columnsName.contains(F_LENGTH) ? rs.getObject(F_LENGTH) : null, Long.class));
            bean.setWidth(Utils.toCast(columnsName.contains(F_WIDTH) ? rs.getObject(F_WIDTH) : null, Long.class));
            bean.setHeight(Utils.toCast(columnsName.contains(F_HEIGHT) ? rs.getObject(F_HEIGHT) : null, Long.class));
            bean.setTotalVol(Utils.toCast(columnsName.contains(F_TOTAL_VOL) ? rs.getObject(F_TOTAL_VOL) : null, Long.class));
            bean.setTotalWeight(Utils.toCast(columnsName.contains(F_TOTAL_WEIGHT) ? rs.getObject(F_TOTAL_WEIGHT) : null, Long.class));
            bean.setNetWeight(Utils.toCast(columnsName.contains(F_NET_WEIGHT) ? rs.getObject(F_NET_WEIGHT) : null, Long.class));
            bean.setIsTransFree(Utils.toCast(columnsName.contains(F_IS_TRANS_FREE) ? rs.getObject(F_IS_TRANS_FREE) : null, Integer.class));
            bean.setFkTransTemplateId(Utils.toCast(columnsName.contains(F_FK_TRANS_TEMPLATE_ID) ? rs.getObject(F_FK_TRANS_TEMPLATE_ID) : null, String.class));
            bean.setTransTemplateName(Utils.toCast(columnsName.contains(F_TRANS_TEMPLATE_NAME) ? rs.getObject(F_TRANS_TEMPLATE_NAME) : null, String.class));
            bean.setFkBrandId(Utils.toCast(columnsName.contains(F_FK_BRAND_ID) ? rs.getObject(F_FK_BRAND_ID) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setIsShowIndex(Utils.toCast(columnsName.contains(F_IS_SHOW_INDEX) ? rs.getObject(F_IS_SHOW_INDEX) : null, Integer.class));
            bean.setIsShowTop(Utils.toCast(columnsName.contains(F_IS_SHOW_TOP) ? rs.getObject(F_IS_SHOW_TOP) : null, Integer.class));
            bean.setIsShowRecommend(Utils.toCast(columnsName.contains(F_IS_SHOW_RECOMMEND) ? rs.getObject(F_IS_SHOW_RECOMMEND) : null, Integer.class));
            bean.setKeywords(Utils.toCast(columnsName.contains(F_KEYWORDS) ? rs.getObject(F_KEYWORDS) : null, String.class));
            bean.setIsSingleProduct(Utils.toCast(columnsName.contains(F_IS_SINGLE_PRODUCT) ? rs.getObject(F_IS_SINGLE_PRODUCT) : null, Integer.class));
            bean.setShelveTime(Utils.toCast(columnsName.contains(F_SHELVE_TIME) ? rs.getObject(F_SHELVE_TIME) : null, Long.class));
            bean.setSales(Utils.toCast(columnsName.contains(F_SALES) ? rs.getObject(F_SALES) : null, Integer.class));
            bean.setFkTagId(Utils.toCast(columnsName.contains(F_FK_TAG_ID) ? rs.getObject(F_FK_TAG_ID) : null, String.class));
            bean.setExplainData(Utils.toCast(columnsName.contains(F_EXPLAIN_DATA) ? rs.getObject(F_EXPLAIN_DATA) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setImageDisplayMode(Utils.toCast(columnsName.contains(F_IMAGE_DISPLAY_MODE) ? rs.getObject(F_IMAGE_DISPLAY_MODE) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductMaster> newMapper(){
        return newMapper(ProProductMaster::new);
    }

    public RowMapper<ProProductMaster> newMapper(Supplier<ProProductMaster> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductMaster {
        @Override
        public abstract RowMapper<ProProductMaster> newMapper();
    }
}
