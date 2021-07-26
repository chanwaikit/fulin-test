package com.mazentop.model;

/**
 * @author dengy
 * 订单状态枚举
 */
public enum CountryEnum {

    AS("100","亚洲"),
    NA("200","北美洲"),
    SA("300","南美洲"),
    AN("400","已结束"),
    OA("500","大洋洲"),
    EU("600","欧洲"),
    AF("700","非洲");

    private final String id;

    private final String desc;

    CountryEnum(String id, String desc){
        this.id = id;
        this.desc = desc;
    }

    public static String getId(String value) {
        CountryEnum[] uploadProductStatusEnums = values();
        for (CountryEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.desc().equals(value)) {
                return uploadProductStatusEnum.id();
            }
        }
        return null;
    }

    public String id(){
        return this.id;
    }

    public String desc(){
        return this.desc;
    }
}
