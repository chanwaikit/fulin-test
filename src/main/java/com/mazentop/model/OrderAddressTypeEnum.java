package com.mazentop.model;

/**
 * @author lifl
 * 订单收货地址类型枚举
 */
public enum OrderAddressTypeEnum {

    SHIPPING("shipping","收货地址"),
    INVOICE("invoice","发票地址");

    private final String type;

    private final String desc;

    OrderAddressTypeEnum(String type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static String getDesc(String value) {
        OrderAddressTypeEnum[] orderAddressTypeEnums = values();
        for (OrderAddressTypeEnum orderAddressTypeEnum : orderAddressTypeEnums) {
            if (orderAddressTypeEnum.type().equals(value)) {
                return orderAddressTypeEnum.desc();
            }
        }
        return null;
    }

    public String type(){
        return this.type;
    }

    public String desc(){
        return this.desc;
    }
}
