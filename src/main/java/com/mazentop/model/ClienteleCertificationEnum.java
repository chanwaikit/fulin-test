package com.mazentop.model;

/**
 * 用户认证状态
 * @author Administrator
 */
public enum ClienteleCertificationEnum {
    /**
     * 未认证
      */
    UNAUTHORIZED("unauthorized","未认证"),

    PENDING_UNAUTHORIZED("pendingUnauthorized","待认证"),
    /***
     * 认证通过
     */
    AUTHENTICATION("authentication","认证通过"),
    /**
     * 认证驳回
     */
    REJECTED("rejected","认证驳回"),
    ;

    private final String status;

    private final String desc;

    ClienteleCertificationEnum(String status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public static String getStatus(String value) {
        ClienteleCertificationEnum[] uploadProductStatusEnums = values();
        for (ClienteleCertificationEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.status().equals(value)) {
                return uploadProductStatusEnum.status();
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
