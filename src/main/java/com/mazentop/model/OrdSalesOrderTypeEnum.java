package com.mazentop.model;

public enum OrdSalesOrderTypeEnum {
    TYPE_PAY("1","线上"),
    TYPE_SIGN("2","线下");

    private final String status;

    private final String type;

    OrdSalesOrderTypeEnum(String status, String type){
        this.status = status;
        this.type = type;
    }
    public static String getType(String value) {
        OrdSalesOrderTypeEnum[] uploadProductStatusEnums = values();
        for (OrdSalesOrderTypeEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.status().equals(value)) {
                return uploadProductStatusEnum.type();
            }
        }
        return null;
    }
    public String status(){
        return this.status;
    }

    public String type(){
        return this.type;
    }
}
