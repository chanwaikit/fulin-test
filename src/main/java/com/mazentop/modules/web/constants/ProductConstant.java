package com.mazentop.modules.web.constants;

/**
 * @author cq
 * 商品常量类
 */
public interface ProductConstant {


    /**
     * 商品置顶
     */
    Integer SHOW_TOP = 1;

    /**
     * 商品不置顶
     */
    Integer NOT_SHOW_TOP = 0;

    /**
     * 商品推荐
     */
    Integer RECOMMEND = 1;

    /**
     * 商品不推荐
     */
    Integer NOT_RECOMMEND = 0;

    /**
     * 商品首页展示
     */
    Integer HOME_SHOW = 1;

    /**
     * 商品首页不展示
     */
    Integer NOT_HOME_SHOW = 0;

    /**
     * 推荐和置顶首页商品展示数量
     */
    Integer HOME_RECOMMEND_STICKY_SIZE = 3;

    /**
     * 推荐和首页展示商品数量
    */
    Integer HOME_RECOMMEND_AND_HOME_SHOW_SIZE = 2;

    /**
     * 首页推荐商品展示数量
     */
    Integer HOME_SHOW_SIZE = 8;

    /**
     * 商品为根类别
     */
    Integer ROOT_TYPE = 1;

    /**
     * 导航栏类别数量
     */
    Integer NAVIGATION_SIZE = 3;

    /**
     * 导航栏每个类别下的产品数量
     */
    Integer NAVIGATION_PRODUCT_SIZE = 3;

    /**
     * 首页轮播图名称
     */
    String CAROUSEL_NAME = "index_banner";

    /**
     * 轮播图展示
     */
    Integer CAROUSEL_ENABLE = 1;

}
