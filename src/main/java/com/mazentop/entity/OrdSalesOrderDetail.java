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
* Date:        11:35 2020/11/26
* Version:     1.0
* Description: OrdSalesOrderDetail实体
*/
@SuppressWarnings("all")
public class OrdSalesOrderDetail extends BaseBean<OrdSalesOrderDetail> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_sales_order_detail";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 公司编号
    */
    public static final String F_COMPANY_ID = "company_id";
    /**
    * 公司名称
    */
    public static final String F_COMPANY_NAME = "company_name";
    /**
    * 销售单编号
    */
    public static final String F_FK_SALES_ORDER_ID = "fk_sales_order_id";
    /**
    * 商品编号
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 商品名称
    */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
    * 商品SKU
    */
    public static final String F_PRODUCT_SKU = "product_sku";
    /**
    * 商品分类编号
    */
    public static final String F_FK_PRODUCT_TYPE_ID = "fk_product_type_id";
    /**
    * 商品分类名称
    */
    public static final String F_PRODUCT_TYPE_NAME = "product_type_name";
    /**
    * 商品数量
    */
    public static final String F_PRODUCT_NUM = "product_num";
    /**
    * 商品条码
    */
    public static final String F_PRODUCT_BARCODE = "product_barcode";
    /**
    * 商品预览图
    */
    public static final String F_PRODUCT_IMAGE_URL = "product_image_url";
    /**
    * 商品市场价格
    */
    public static final String F_PRODUCT_MARKET_PRICE = "product_market_price";
    /**
    * 商品商城价格
    */
    public static final String F_PRODUCT_MALL_PRICE = "product_mall_price";
    /**
    * 商品活动后价格
    */
    public static final String F_PRODUCT_ACTIVITY_PRICE = "product_activity_price";
    /**
    * 颜色
    */
    public static final String F_COLOR = "color";
    /**
    * 尺寸
    */
    public static final String F_SIZE = "size";
    /**
    * 规格型号
    */
    public static final String F_SPEC = "spec";
    /**
    * 商品产地
    */
    public static final String F_PRODUCT_AREA = "product_area";
    /**
    * 限时促销活动编号
    */
    public static final String F_PROMOTION_ACTIVITY_ID = "promotion_activity_id";
    /**
    * 限时促销活动名称
    */
    public static final String F_PROMOTION_ACTIVITY_NAME = "promotion_activity_name";
    /**
    * 限时促销活动结果
    */
    public static final String F_PROMOTION_ACTIVITY_RESULT = "promotion_activity_result";
    /**
    * 单位体积
    */
    public static final String F_UNIT_VOL = "unit_vol";
    /**
    * 单位净重
    */
    public static final String F_UNIT_NET_WEIGHT = "unit_net_weight";
    /**
    * 单位重量
    */
    public static final String F_UNIT_WEIGHT = "unit_weight";
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
    * 品牌
    */
    public static final String F_BRAND_NAME = "brand_name";
    /**
    * 品牌编码
    */
    public static final String F_FK_BRAND_ID = "fk_brand_id";
    /**
    * 采购价
    */
    public static final String F_PURCHASE_PRICE = "purchase_price";
    /**
    * 供应商编号
    */
    public static final String F_SUPPLIER_NO = "supplier_no";
    /**
    * 供应商名称
    */
    public static final String F_SUPPLIER_NAME = "supplier_name";
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
    * 币种
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 销售单号
    */
    public static final String F_SALES_ORDER_NO = "sales_order_no";
    /**
    * 规格型号信息
    */
    public static final String F_PRODUCT_SPEC = "product_spec";
    /**
    * 
    */
    public static final String F_LOCALHOST_SN = "localhost_sn";
    /**
    * 商品子SKU
    */
    public static final String F_PRODUCT_SUB_SKU = "product_sub_sku";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_FK_SALES_ORDER_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_PRODUCT_SKU, null);
        put(F_FK_PRODUCT_TYPE_ID, null);
        put(F_PRODUCT_TYPE_NAME, null);
        put(F_PRODUCT_NUM, null);
        put(F_PRODUCT_BARCODE, null);
        put(F_PRODUCT_IMAGE_URL, null);
        put(F_PRODUCT_MARKET_PRICE, null);
        put(F_PRODUCT_MALL_PRICE, null);
        put(F_PRODUCT_ACTIVITY_PRICE, null);
        put(F_COLOR, null);
        put(F_SIZE, null);
        put(F_SPEC, null);
        put(F_PRODUCT_AREA, null);
        put(F_PROMOTION_ACTIVITY_ID, null);
        put(F_PROMOTION_ACTIVITY_NAME, null);
        put(F_PROMOTION_ACTIVITY_RESULT, null);
        put(F_UNIT_VOL, null);
        put(F_UNIT_NET_WEIGHT, null);
        put(F_UNIT_WEIGHT, null);
        put(F_CUS_PRODUCT_TYPE, null);
        put(F_CUS_PRICE, null);
        put(F_CUS_HS_CODE, null);
        put(F_CUS_ZH_DESCRIPTION, null);
        put(F_CUS_EN_DESCRIPTION, null);
        put(F_LENGTH, null);
        put(F_WIDTH, null);
        put(F_HEIGHT, null);
        put(F_BRAND_NAME, null);
        put(F_FK_BRAND_ID, null);
        put(F_PURCHASE_PRICE, null);
        put(F_SUPPLIER_NO, null);
        put(F_SUPPLIER_NAME, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_CURRENCY, null);
        put(F_SALES_ORDER_NO, null);
        put(F_PRODUCT_SPEC, null);
        put(F_LOCALHOST_SN, null);
        put(F_PRODUCT_SUB_SKU, null);
    }

    public OrdSalesOrderDetail() {
        super();
    }

    public OrdSalesOrderDetail(Map<String, Object> map) {
        super(map);
    }

    public OrdSalesOrderDetail(String id) {
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
    public OrdSalesOrderDetail setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return company_id to companyId 公司编号<BR/>
    */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
    * @param companyId to company_id 公司编号 set
    */
    public OrdSalesOrderDetail setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
    * @return company_name to companyName 公司名称<BR/>
    */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
    * @param companyName to company_name 公司名称 set
    */
    public OrdSalesOrderDetail setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
    * @return fk_sales_order_id to fkSalesOrderId 销售单编号<BR/>
    */
    public String getFkSalesOrderId() {
        return getTypedValue(F_FK_SALES_ORDER_ID, String.class);
    }
    /**
    * @param fkSalesOrderId to fk_sales_order_id 销售单编号 set
    */
    public OrdSalesOrderDetail setFkSalesOrderId(String fkSalesOrderId) {
        set(F_FK_SALES_ORDER_ID, fkSalesOrderId);
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
    public OrdSalesOrderDetail setFkProductId(String fkProductId) {
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
    public OrdSalesOrderDetail setProductName(String productName) {
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
    public OrdSalesOrderDetail setProductSku(String productSku) {
        set(F_PRODUCT_SKU, productSku);
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
    public OrdSalesOrderDetail setFkProductTypeId(String fkProductTypeId) {
        set(F_FK_PRODUCT_TYPE_ID, fkProductTypeId);
        return this;
    }
    /**
    * @return product_type_name to productTypeName 商品分类名称<BR/>
    */
    public String getProductTypeName() {
        return getTypedValue(F_PRODUCT_TYPE_NAME, String.class);
    }
    /**
    * @param productTypeName to product_type_name 商品分类名称 set
    */
    public OrdSalesOrderDetail setProductTypeName(String productTypeName) {
        set(F_PRODUCT_TYPE_NAME, productTypeName);
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
    public OrdSalesOrderDetail setProductNum(Integer productNum) {
        set(F_PRODUCT_NUM, productNum);
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
    public OrdSalesOrderDetail setProductBarcode(String productBarcode) {
        set(F_PRODUCT_BARCODE, productBarcode);
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
    public OrdSalesOrderDetail setProductImageUrl(String productImageUrl) {
        set(F_PRODUCT_IMAGE_URL, productImageUrl);
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
    public OrdSalesOrderDetail setProductMarketPrice(Long productMarketPrice) {
        set(F_PRODUCT_MARKET_PRICE, productMarketPrice);
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
    public OrdSalesOrderDetail setProductMallPrice(Long productMallPrice) {
        set(F_PRODUCT_MALL_PRICE, productMallPrice);
        return this;
    }
    /**
    * @return product_activity_price to productActivityPrice 商品活动后价格<BR/>
    */
    public Long getProductActivityPrice() {
        return getTypedValue(F_PRODUCT_ACTIVITY_PRICE, Long.class);
    }
    /**
    * @param productActivityPrice to product_activity_price 商品活动后价格 set
    */
    public OrdSalesOrderDetail setProductActivityPrice(Long productActivityPrice) {
        set(F_PRODUCT_ACTIVITY_PRICE, productActivityPrice);
        return this;
    }
    /**
    * @return color to color 颜色<BR/>
    */
    public String getColor() {
        return getTypedValue(F_COLOR, String.class);
    }
    /**
    * @param color to color 颜色 set
    */
    public OrdSalesOrderDetail setColor(String color) {
        set(F_COLOR, color);
        return this;
    }
    /**
    * @return size to size 尺寸<BR/>
    */
    public String getSize() {
        return getTypedValue(F_SIZE, String.class);
    }
    /**
    * @param size to size 尺寸 set
    */
    public OrdSalesOrderDetail setSize(String size) {
        set(F_SIZE, size);
        return this;
    }
    /**
    * @return spec to spec 规格型号<BR/>
    */
    public String getSpec() {
        return getTypedValue(F_SPEC, String.class);
    }
    /**
    * @param spec to spec 规格型号 set
    */
    public OrdSalesOrderDetail setSpec(String spec) {
        set(F_SPEC, spec);
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
    public OrdSalesOrderDetail setProductArea(String productArea) {
        set(F_PRODUCT_AREA, productArea);
        return this;
    }
    /**
    * @return promotion_activity_id to promotionActivityId 限时促销活动编号<BR/>
    */
    public String getPromotionActivityId() {
        return getTypedValue(F_PROMOTION_ACTIVITY_ID, String.class);
    }
    /**
    * @param promotionActivityId to promotion_activity_id 限时促销活动编号 set
    */
    public OrdSalesOrderDetail setPromotionActivityId(String promotionActivityId) {
        set(F_PROMOTION_ACTIVITY_ID, promotionActivityId);
        return this;
    }
    /**
    * @return promotion_activity_name to promotionActivityName 限时促销活动名称<BR/>
    */
    public String getPromotionActivityName() {
        return getTypedValue(F_PROMOTION_ACTIVITY_NAME, String.class);
    }
    /**
    * @param promotionActivityName to promotion_activity_name 限时促销活动名称 set
    */
    public OrdSalesOrderDetail setPromotionActivityName(String promotionActivityName) {
        set(F_PROMOTION_ACTIVITY_NAME, promotionActivityName);
        return this;
    }
    /**
    * @return promotion_activity_result to promotionActivityResult 限时促销活动结果<BR/>
    */
    public String getPromotionActivityResult() {
        return getTypedValue(F_PROMOTION_ACTIVITY_RESULT, String.class);
    }
    /**
    * @param promotionActivityResult to promotion_activity_result 限时促销活动结果 set
    */
    public OrdSalesOrderDetail setPromotionActivityResult(String promotionActivityResult) {
        set(F_PROMOTION_ACTIVITY_RESULT, promotionActivityResult);
        return this;
    }
    /**
    * @return unit_vol to unitVol 单位体积<BR/>
    */
    public Long getUnitVol() {
        return getTypedValue(F_UNIT_VOL, Long.class);
    }
    /**
    * @param unitVol to unit_vol 单位体积 set
    */
    public OrdSalesOrderDetail setUnitVol(Long unitVol) {
        set(F_UNIT_VOL, unitVol);
        return this;
    }
    /**
    * @return unit_net_weight to unitNetWeight 单位净重<BR/>
    */
    public Long getUnitNetWeight() {
        return getTypedValue(F_UNIT_NET_WEIGHT, Long.class);
    }
    /**
    * @param unitNetWeight to unit_net_weight 单位净重 set
    */
    public OrdSalesOrderDetail setUnitNetWeight(Long unitNetWeight) {
        set(F_UNIT_NET_WEIGHT, unitNetWeight);
        return this;
    }
    /**
    * @return unit_weight to unitWeight 单位重量<BR/>
    */
    public Long getUnitWeight() {
        return getTypedValue(F_UNIT_WEIGHT, Long.class);
    }
    /**
    * @param unitWeight to unit_weight 单位重量 set
    */
    public OrdSalesOrderDetail setUnitWeight(Long unitWeight) {
        set(F_UNIT_WEIGHT, unitWeight);
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
    public OrdSalesOrderDetail setCusProductType(String cusProductType) {
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
    public OrdSalesOrderDetail setCusPrice(Long cusPrice) {
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
    public OrdSalesOrderDetail setCusHsCode(String cusHsCode) {
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
    public OrdSalesOrderDetail setCusZhDescription(String cusZhDescription) {
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
    public OrdSalesOrderDetail setCusEnDescription(String cusEnDescription) {
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
    public OrdSalesOrderDetail setLength(Long length) {
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
    public OrdSalesOrderDetail setWidth(Long width) {
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
    public OrdSalesOrderDetail setHeight(Long height) {
        set(F_HEIGHT, height);
        return this;
    }
    /**
    * @return brand_name to brandName 品牌<BR/>
    */
    public String getBrandName() {
        return getTypedValue(F_BRAND_NAME, String.class);
    }
    /**
    * @param brandName to brand_name 品牌 set
    */
    public OrdSalesOrderDetail setBrandName(String brandName) {
        set(F_BRAND_NAME, brandName);
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
    public OrdSalesOrderDetail setFkBrandId(String fkBrandId) {
        set(F_FK_BRAND_ID, fkBrandId);
        return this;
    }
    /**
    * @return purchase_price to purchasePrice 采购价<BR/>
    */
    public Long getPurchasePrice() {
        return getTypedValue(F_PURCHASE_PRICE, Long.class);
    }
    /**
    * @param purchasePrice to purchase_price 采购价 set
    */
    public OrdSalesOrderDetail setPurchasePrice(Long purchasePrice) {
        set(F_PURCHASE_PRICE, purchasePrice);
        return this;
    }
    /**
    * @return supplier_no to supplierNo 供应商编号<BR/>
    */
    public String getSupplierNo() {
        return getTypedValue(F_SUPPLIER_NO, String.class);
    }
    /**
    * @param supplierNo to supplier_no 供应商编号 set
    */
    public OrdSalesOrderDetail setSupplierNo(String supplierNo) {
        set(F_SUPPLIER_NO, supplierNo);
        return this;
    }
    /**
    * @return supplier_name to supplierName 供应商名称<BR/>
    */
    public String getSupplierName() {
        return getTypedValue(F_SUPPLIER_NAME, String.class);
    }
    /**
    * @param supplierName to supplier_name 供应商名称 set
    */
    public OrdSalesOrderDetail setSupplierName(String supplierName) {
        set(F_SUPPLIER_NAME, supplierName);
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
    public OrdSalesOrderDetail setRemark(String remark) {
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
    public OrdSalesOrderDetail setAddTime(Long addTime) {
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
    public OrdSalesOrderDetail setAddUserId(String addUserId) {
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
    public OrdSalesOrderDetail setAddUserName(String addUserName) {
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
    public OrdSalesOrderDetail setOperationTime(Long operationTime) {
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
    public OrdSalesOrderDetail setOperationUserId(String operationUserId) {
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
    public OrdSalesOrderDetail setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
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
    public OrdSalesOrderDetail setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return sales_order_no to salesOrderNo 销售单号<BR/>
    */
    public String getSalesOrderNo() {
        return getTypedValue(F_SALES_ORDER_NO, String.class);
    }
    /**
    * @param salesOrderNo to sales_order_no 销售单号 set
    */
    public OrdSalesOrderDetail setSalesOrderNo(String salesOrderNo) {
        set(F_SALES_ORDER_NO, salesOrderNo);
        return this;
    }
    /**
    * @return product_spec to productSpec 规格型号信息<BR/>
    */
    public String getProductSpec() {
        return getTypedValue(F_PRODUCT_SPEC, String.class);
    }
    /**
    * @param productSpec to product_spec 规格型号信息 set
    */
    public OrdSalesOrderDetail setProductSpec(String productSpec) {
        set(F_PRODUCT_SPEC, productSpec);
        return this;
    }
    /**
    * @return localhost_sn to localhostSn <BR/>
    */
    public Long getLocalhostSn() {
        return getTypedValue(F_LOCALHOST_SN, Long.class);
    }
    /**
    * @param localhostSn to localhost_sn  set
    */
    public OrdSalesOrderDetail setLocalhostSn(Long localhostSn) {
        set(F_LOCALHOST_SN, localhostSn);
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
    public OrdSalesOrderDetail setProductSubSku(String productSubSku) {
        set(F_PRODUCT_SUB_SKU, productSubSku);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdSalesOrderDetail setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdSalesOrderDetail me(){
        return new OrdSalesOrderDetail();
    }

    private static class Mapper implements RowMapper<OrdSalesOrderDetail> {

        private Supplier<OrdSalesOrderDetail> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdSalesOrderDetail mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            OrdSalesOrderDetail bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setCompanyId(Utils.toCast(columnsName.contains(F_COMPANY_ID) ? rs.getObject(F_COMPANY_ID) : null, String.class));
            bean.setCompanyName(Utils.toCast(columnsName.contains(F_COMPANY_NAME) ? rs.getObject(F_COMPANY_NAME) : null, String.class));
            bean.setFkSalesOrderId(Utils.toCast(columnsName.contains(F_FK_SALES_ORDER_ID) ? rs.getObject(F_FK_SALES_ORDER_ID) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setProductSku(Utils.toCast(columnsName.contains(F_PRODUCT_SKU) ? rs.getObject(F_PRODUCT_SKU) : null, String.class));
            bean.setFkProductTypeId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_TYPE_ID) ? rs.getObject(F_FK_PRODUCT_TYPE_ID) : null, String.class));
            bean.setProductTypeName(Utils.toCast(columnsName.contains(F_PRODUCT_TYPE_NAME) ? rs.getObject(F_PRODUCT_TYPE_NAME) : null, String.class));
            bean.setProductNum(Utils.toCast(columnsName.contains(F_PRODUCT_NUM) ? rs.getObject(F_PRODUCT_NUM) : null, Integer.class));
            bean.setProductBarcode(Utils.toCast(columnsName.contains(F_PRODUCT_BARCODE) ? rs.getObject(F_PRODUCT_BARCODE) : null, String.class));
            bean.setProductImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_IMAGE_URL) ? rs.getObject(F_PRODUCT_IMAGE_URL) : null, String.class));
            bean.setProductMarketPrice(Utils.toCast(columnsName.contains(F_PRODUCT_MARKET_PRICE) ? rs.getObject(F_PRODUCT_MARKET_PRICE) : null, Long.class));
            bean.setProductMallPrice(Utils.toCast(columnsName.contains(F_PRODUCT_MALL_PRICE) ? rs.getObject(F_PRODUCT_MALL_PRICE) : null, Long.class));
            bean.setProductActivityPrice(Utils.toCast(columnsName.contains(F_PRODUCT_ACTIVITY_PRICE) ? rs.getObject(F_PRODUCT_ACTIVITY_PRICE) : null, Long.class));
            bean.setColor(Utils.toCast(columnsName.contains(F_COLOR) ? rs.getObject(F_COLOR) : null, String.class));
            bean.setSize(Utils.toCast(columnsName.contains(F_SIZE) ? rs.getObject(F_SIZE) : null, String.class));
            bean.setSpec(Utils.toCast(columnsName.contains(F_SPEC) ? rs.getObject(F_SPEC) : null, String.class));
            bean.setProductArea(Utils.toCast(columnsName.contains(F_PRODUCT_AREA) ? rs.getObject(F_PRODUCT_AREA) : null, String.class));
            bean.setPromotionActivityId(Utils.toCast(columnsName.contains(F_PROMOTION_ACTIVITY_ID) ? rs.getObject(F_PROMOTION_ACTIVITY_ID) : null, String.class));
            bean.setPromotionActivityName(Utils.toCast(columnsName.contains(F_PROMOTION_ACTIVITY_NAME) ? rs.getObject(F_PROMOTION_ACTIVITY_NAME) : null, String.class));
            bean.setPromotionActivityResult(Utils.toCast(columnsName.contains(F_PROMOTION_ACTIVITY_RESULT) ? rs.getObject(F_PROMOTION_ACTIVITY_RESULT) : null, String.class));
            bean.setUnitVol(Utils.toCast(columnsName.contains(F_UNIT_VOL) ? rs.getObject(F_UNIT_VOL) : null, Long.class));
            bean.setUnitNetWeight(Utils.toCast(columnsName.contains(F_UNIT_NET_WEIGHT) ? rs.getObject(F_UNIT_NET_WEIGHT) : null, Long.class));
            bean.setUnitWeight(Utils.toCast(columnsName.contains(F_UNIT_WEIGHT) ? rs.getObject(F_UNIT_WEIGHT) : null, Long.class));
            bean.setCusProductType(Utils.toCast(columnsName.contains(F_CUS_PRODUCT_TYPE) ? rs.getObject(F_CUS_PRODUCT_TYPE) : null, String.class));
            bean.setCusPrice(Utils.toCast(columnsName.contains(F_CUS_PRICE) ? rs.getObject(F_CUS_PRICE) : null, Long.class));
            bean.setCusHsCode(Utils.toCast(columnsName.contains(F_CUS_HS_CODE) ? rs.getObject(F_CUS_HS_CODE) : null, String.class));
            bean.setCusZhDescription(Utils.toCast(columnsName.contains(F_CUS_ZH_DESCRIPTION) ? rs.getObject(F_CUS_ZH_DESCRIPTION) : null, String.class));
            bean.setCusEnDescription(Utils.toCast(columnsName.contains(F_CUS_EN_DESCRIPTION) ? rs.getObject(F_CUS_EN_DESCRIPTION) : null, String.class));
            bean.setLength(Utils.toCast(columnsName.contains(F_LENGTH) ? rs.getObject(F_LENGTH) : null, Long.class));
            bean.setWidth(Utils.toCast(columnsName.contains(F_WIDTH) ? rs.getObject(F_WIDTH) : null, Long.class));
            bean.setHeight(Utils.toCast(columnsName.contains(F_HEIGHT) ? rs.getObject(F_HEIGHT) : null, Long.class));
            bean.setBrandName(Utils.toCast(columnsName.contains(F_BRAND_NAME) ? rs.getObject(F_BRAND_NAME) : null, String.class));
            bean.setFkBrandId(Utils.toCast(columnsName.contains(F_FK_BRAND_ID) ? rs.getObject(F_FK_BRAND_ID) : null, String.class));
            bean.setPurchasePrice(Utils.toCast(columnsName.contains(F_PURCHASE_PRICE) ? rs.getObject(F_PURCHASE_PRICE) : null, Long.class));
            bean.setSupplierNo(Utils.toCast(columnsName.contains(F_SUPPLIER_NO) ? rs.getObject(F_SUPPLIER_NO) : null, String.class));
            bean.setSupplierName(Utils.toCast(columnsName.contains(F_SUPPLIER_NAME) ? rs.getObject(F_SUPPLIER_NAME) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddUserName(Utils.toCast(columnsName.contains(F_ADD_USER_NAME) ? rs.getObject(F_ADD_USER_NAME) : null, String.class));
            bean.setOperationTime(Utils.toCast(columnsName.contains(F_OPERATION_TIME) ? rs.getObject(F_OPERATION_TIME) : null, Long.class));
            bean.setOperationUserId(Utils.toCast(columnsName.contains(F_OPERATION_USER_ID) ? rs.getObject(F_OPERATION_USER_ID) : null, String.class));
            bean.setOperationUserName(Utils.toCast(columnsName.contains(F_OPERATION_USER_NAME) ? rs.getObject(F_OPERATION_USER_NAME) : null, String.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setSalesOrderNo(Utils.toCast(columnsName.contains(F_SALES_ORDER_NO) ? rs.getObject(F_SALES_ORDER_NO) : null, String.class));
            bean.setProductSpec(Utils.toCast(columnsName.contains(F_PRODUCT_SPEC) ? rs.getObject(F_PRODUCT_SPEC) : null, String.class));
            bean.setLocalhostSn(Utils.toCast(columnsName.contains(F_LOCALHOST_SN) ? rs.getObject(F_LOCALHOST_SN) : null, Long.class));
            bean.setProductSubSku(Utils.toCast(columnsName.contains(F_PRODUCT_SUB_SKU) ? rs.getObject(F_PRODUCT_SUB_SKU) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdSalesOrderDetail> newMapper(){
        return newMapper(OrdSalesOrderDetail::new);
    }

    public RowMapper<OrdSalesOrderDetail> newMapper(Supplier<OrdSalesOrderDetail> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdSalesOrderDetail {
        @Override
        public abstract RowMapper<OrdSalesOrderDetail> newMapper();
    }
}
