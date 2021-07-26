package com.mazentop.model;

/**
 * @author dengy
 * 订单状态枚举
 */
public enum ProductEnum {
    /**
     * 购买模式
     */
    KEYWORD("keyword","复制关键字购买"),
    LINK("link","跳转亚马逊产品详情链接"),
   ;

    private final String key;

    private final String value;

    ProductEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getKey(String key) {
        ProductEnum[] uploadProductStatusEnums = values();
        for (ProductEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.key().equals(key)) {
                return uploadProductStatusEnum.key();
            }
        }
        return ProductEnum.KEYWORD.key();
    }

    public String key(){
        return this.key;
    }

    public String value(){
        return this.value;
    }
}
