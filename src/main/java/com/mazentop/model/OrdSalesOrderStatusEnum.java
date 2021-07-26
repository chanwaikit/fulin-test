package com.mazentop.model;

/**
 * @author dengy
 * 订单状态枚举
 */
public enum OrdSalesOrderStatusEnum {

    /**
     * 订单状态-待支付
     */
    CREATE_COMPLETE("01","待支付","To be paid"),

    PAYMENT_EXCEPTION("02","支付异常","Abnormal payment"),

    DELIVER_ING_COMPLETE("03","待发货" ,"To be delivered"),

    DELIVER_COMPLETE("04","已发货" ,"Wait For Receiving"),

    COMPLETE("05","已完成","Completed"),

    REFUND_ING("06","退款申请中" ,"Return/after sale"),

    CANCEL_COMPLETE("99","已取消" , "Cancelled");

    private final String status;

    private final String desc;

    private final String descEn;

    OrdSalesOrderStatusEnum(String status, String desc,String descEn){
        this.status = status;
        this.desc = desc;
        this.descEn = descEn;
    }

    public static String getDesc(String value) {
        OrdSalesOrderStatusEnum[] uploadProductStatusEnums = values();
        for (OrdSalesOrderStatusEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.status().equals(value)) {
                return uploadProductStatusEnum.desc();
            }
        }
        return null;
    }


    public static String getDescEn(String value) {
        OrdSalesOrderStatusEnum[] uploadProductStatusEnums = values();
        for (OrdSalesOrderStatusEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.status().equals(value)) {
                return uploadProductStatusEnum.descEn();
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

    public String descEn(){
        return this.descEn;
    }


}
