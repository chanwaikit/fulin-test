package com.mazentop.model;

public enum  SourceEnum {

    POPULAR_PLATFORM(1,"platform");


    SourceEnum (Integer type,String tile){
        this.tile = tile;
        this.type = type;
    }
    private  Integer type;

    private  String tile;

    public static SourceEnum getReportProductEnumByType(Integer type) {
        SourceEnum[] uploadProductStatusEnums = values();
        for (SourceEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.type().equals(type)) {
                return uploadProductStatusEnum;
            }
        }
        return SourceEnum.POPULAR_PLATFORM;
    }
    public Integer type(){
        return this.type;
    }

    public String tile(){
        return this.tile;
    }
}
