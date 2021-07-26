package com.mazentop.model;

/**
 * @author zhoumei
 * 订单物流枚举
 */
public enum OrderLogisticsStatusEnum {

    /**
     *
     */
    PAID("已付款","Paid"),

    TO_bE_DELIVERED("待发货","To be delivered"),

    DELIVERED("已发货","Delivered"),

    PENDING("待处理","Pending"),

    SIGNED("已签收","Signed in"),

    REFUNDED("已退款","Refunded"),

    PROCESSED("已处理","Processed"),

    PAID2("已支付","Paid");








    private final String status;

    private final String desc;

    OrderLogisticsStatusEnum(String status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(String status) {
        OrderLogisticsStatusEnum[] orderStatusEnums = values();
        for (OrderLogisticsStatusEnum orderStatusEnum : orderStatusEnums) {
            if (orderStatusEnum.status().equals(status)) {
                return orderStatusEnum.desc();
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
