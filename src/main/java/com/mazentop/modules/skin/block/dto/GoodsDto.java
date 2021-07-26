package com.mazentop.modules.skin.block.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 块商品dto 只是布局块使用
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/20 16:29
 */
@Data
public class GoodsDto {

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品副标题
     */
    private String subTitle;

    /**
     * 商品封面图片。默认获取第一张即可
     */
    private String coverUrl;

    /**
     * 图片切换使用
     */
    private List<String> switchImgs;

    /**
     * 商品详情页面跳转地址
     */
    private String url;

    /**
     * 商品评分
     */
    private String scoreCount;

    /**
     * 商品最高价格
     */
    private BigDecimal maxPrice;

    /**
     * 商品最低价格
     */
    private BigDecimal minPrice;

    /**
     * 商品最低市场价格
     */
    private BigDecimal minMarketPrice;


    /**
     * 商品返现
     */
    private BigDecimal cashBack;

    /**
     * 活动编号
     */
    private String activity;

    /**
     * 活动名称
     */
    private String activityId;

    /**
     * 优惠数值
     */
    private String discountValue;


    /**
     * 评论对象
     */
    private CommentDto comment;


    /**
     *  是否加入购物车
     */
    private Boolean isCart = false;

    /**
     * 是否收藏
     */
    private Boolean isCollection = false;



}
