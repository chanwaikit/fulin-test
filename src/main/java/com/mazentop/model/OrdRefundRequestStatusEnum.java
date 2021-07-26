package com.mazentop.model;

/**
 * @author dengy
 * 退货订单状态枚举
 */
public enum OrdRefundRequestStatusEnum {

    AUDITED_ING("01","待审核"),
    AUDITED_ADOPT("02","审核通过"),
    AUDITED_NOT_ADOPT("03","审核不通过"),
    COMPLETE("04","退款完成");
    private final String status;

    private final String desc;

    OrdRefundRequestStatusEnum(String status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(String value) {
        OrdRefundRequestStatusEnum[] uploadProductStatusEnums = values();
        for (OrdRefundRequestStatusEnum uploadProductStatusEnum : uploadProductStatusEnums) {
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
