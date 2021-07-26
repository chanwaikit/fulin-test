package com.mazentop.model;

/**
 * @author dengy
 * 优惠券状态枚举
 */
public enum ActCouponEnum {

    /**
     * 订单状态 - 未开始
     */
    NOT_START("01","未开始"),

    /**
     * 订单状态 - 进行中
     */
    UNDERWAY("02","进行中"),

    /**
     * 订单状态 - 已结束
     */
    FINISHED("03","已结束");



    private final String status;

    private final String desc;

    ActCouponEnum(String status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(String value) {
        ActCouponEnum[] uploadProductStatusEnums = values();
        for (ActCouponEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.status().equals(value)) {
                return uploadProductStatusEnum.desc();
            }
        }
        return null;
    }

    public String status(){
        return this.status;
    }

    public String desc(){
        return this.desc;
    }
}
