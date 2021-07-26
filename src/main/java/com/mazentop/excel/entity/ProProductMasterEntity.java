package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProProductMasterEntity {
    @Excel(name = "商品ids",isColumnHidden=true)
    private String ids;

    @Excel(name = "商品id")
    private String id;

    @Excel(name = "标题")
    private String productName;

    @Excel(name = "款式图片", type = 2, width = 20,height = 30)
    private String styleImage;

    @Excel(name = "商品商城价格")
    private String productMallPrice;

    @Excel(name = "商品市场价格")
    private String productMarketPrice;

    @Excel(name = "商品库存")
    private String productStockNumber;

    @Excel(name = "商品简介")
    private String productDescription;

    @Excel(name = "商品描述")
    private String productContent;

    @Excel(name = "商品款式类型")
    private String isSingleProduct;

    @Excel(name = "商品属性")
    private String attribute;

    @Excel(name = "款式1")
    private String style1;

    @Excel(name = "款式2")
    private String style2;

    @Excel(name = "款式3")
    private String style3;

    @Excel(name = "商品SKU")
    private String productSku;

    @Excel(name = "商品预览图")
    private String prductPicImageUrl;

    @Excel(name = "商品视频预览图" )
    private String productVideoImageUrl;

    @Excel(name = "商品是否上架")
    private String isShelve;

    @Excel(name = "商品视频地址")
    private String productVideoUrl;

    @Excel(name = "商品关键词")
    private String keyword;

    @Excel(name = "长")
    private String length;

    @Excel(name = "宽")
    private String width;

    @Excel(name = "高")
    private String height;

    @Excel(name = "净重")
    private String netWeight;

    @Excel(name = "错误信息")
    private String error;

    /**
     * 商品表格填写描述信息
     */
    public static class TipInfo{
        public static final String ID_INFO = "（此列导入时不可删除 可为空）商品 ID 是系统生成的唯一标识符，商品主体此列代表商品ID，款式部分此列代表skuCode，新增商品无需填写,导出后不可手动修改";
        public static final String NAME_INFO = "请确保款式部分的标题与商品主体的标题一致，且保持连续排列（中间请勿插入其他商品）\n最多260字符，（必填）";
        public static final String DESCRIPTION_INFO = "仅商品主体填写即可，上传不带样式，若需要样式，请用html代码上传，仅商品主体填写即可";
        public static final String STATUS_INFO = "商品上传完成后直接上架则输入Y，反之则填N。\n仅商品主体填写即可（必填,大小写字母均可）";
        public static final String PROPERTY_INFO = "商品主体（Main）：填入M款式\n部分（Part）：填入P\n（必填M或P，只能有一个M属性和一个或多个P属性）";
        public static final String SKU1_INFO = "商品主体填写款式名称，款式部分填写款式信息，商品主体属性与款式2，款式3不重复，切勿相隔填写或填写了商品主题没填款式部分属性（必填，且款式1商品属性必须为颜色）";
        public static final String SKU2_INFO = "商品主体填写款式名称，款式部分填写款式信息";
        public static final String SKU3_INFO = "商品主体填写款式名称，款式部分填写款式信息";
        public static final String PRICE_INFO = "设置商品单价，填写非负数数字，商品库存填写即可（必填，） 单商品直接填写到主体";
        public static final String PRICE_KET_INFO = "设置商品市场单价，填写非负数数字，商品库存填写即可（必填，）单商品直接填写到主体";
        public static final String SKUNAME_INFO = "商品SKU，不填自带生成";
        public static final String PRODUCT_WEIGHT_INFO = "仅款式部分及单商品需要填写，单位(g)，不填为0";
        public static final String PRODUCT_STOCK_INFO = "商品库存，填入P款式库存 请填写非负数存数字，不填为0，";
        public static final String VIDEO_INFO = "仅能一个视频（视频URL），仅商品主体需要填写该项，不超过400字符";
        public static final String VIDEO_IMAGE_INFO = "视频预览图，仅商品主体需要填写该项，不超过400字符";
        public static final String PRODUCT_PARTIMG_INFO = "可多张图片（图片URL），URL用「英文逗号」隔开，仅商品主体需要填写该项，每个URL不超过200字符";
        public static final String PRODUCT_STYLE_INFO = "src/main/resources/static/image/75258745.png";
        public static final String TRANSPORTATION_INFO = "仅商品主体填写即可，上传不带样式，若需要样式，请用html代码上传";
        public static final String PRODUCT_KEYWORD_INFO = "仅商品主体填写即可，商品关键词，多个用「英文逗号」隔开 最多200字符";
        public static final String PRODUCT_HEIGHT_INFO = "仅商品主体填写即可，商品高度不填为0";
        public static final String PRODUCT_NEW_HEIGHT = "商品款式填写即可，商品净重不填为0 单位(g)";
        public static final String PRODUCT_LENGTH_INFO = "仅商品主体填写即可，商品长度不填为0 ";
        public static final String PRODUCT_IS_SINGLE = "仅商品主体填写即可 是否为单体商品（注：单体商品指的是没有其他款式的 不填默认N （Y / N））";
    }
}
