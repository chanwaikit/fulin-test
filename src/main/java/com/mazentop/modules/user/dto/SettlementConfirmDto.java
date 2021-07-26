package com.mazentop.modules.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class SettlementConfirmDto {
    /**
     * 优惠券编码
     */
    private String couponCode;

    /**
     * 满减活动
     */
    private String reduceActivity;

    private String addressId;

    /**
     * 收货地址
     */
    private Address shippingAddress;

    /**
     * 发票邮寄地址
     */
    private Address invoiceAddress;

    /**
     * 发票地址是否与收货地址相同
     */
    private Boolean shippingAndInvoice;

    /**
     * 购物车内商品
     */
    private List<String> carts;

    /**
     * 物流方案
     */
    private String freeSchemes;

    /**
     * 是否保存该地址
     */
    private Boolean isSaveAddress;

    /**
     * 购物车paypal支付标识
     */
    private Boolean cartFlag;

    @Data
    public static class Address {
        /**
         * 前名称
         */
        private String name;
        /**
         * 后名称
         */
        private String surname;
        /**
         * 邮编
         */
        private String post;
        /**
         * 邮箱
         */
        private String email;
        /**
         * 国家
         */
        private String country;
        /**
         * 省
         */
        private String province;
        /**
         * 市
         */
        private String city;
        /**
         * 具体地址
         */
        private String address;
    }
}
