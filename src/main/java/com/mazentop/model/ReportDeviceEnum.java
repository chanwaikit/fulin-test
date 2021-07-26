package com.mazentop.model;

public enum  ReportDeviceEnum {

    POPULAR_ACCESS(1,"访问"),

    POPULAR_TRANSACTIONE(2,"订单交易");


    ReportDeviceEnum (Integer type,String tile){
        this.tile = tile;
        this.type = type;
    }
    private  Integer type;

    private  String tile;

    public static ReportDeviceEnum getReportProductEnumByType(Integer type) {
        ReportDeviceEnum[] uploadProductStatusEnums = values();
        for (ReportDeviceEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.type().equals(type)) {
                return uploadProductStatusEnum;
            }
        }
        return ReportDeviceEnum.POPULAR_ACCESS;
    }
    public Integer type(){
        return this.type;
    }

    public String tile(){
        return this.tile;
    }
}
