package com.mazentop.model;

public enum ReportProductEnum {
    POPULAR_PRODUCT(1,"热卖商品"),
    POPULAR_PRODUCT_REPURCHASE(2,"加购商品");
    ReportProductEnum (Integer type,String tile){
        this.tile = tile;
        this.type = type;
    }
    private  Integer type;

    private  String tile;

    public static ReportProductEnum getReportProductEnumByType(Integer type) {
        ReportProductEnum[] uploadProductStatusEnums = values();
        for (ReportProductEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.type().equals(type)) {
                return uploadProductStatusEnum;
            }
        }
        return ReportProductEnum.POPULAR_PRODUCT;
    }
    public Integer type(){
        return this.type;
    }

    public String tile(){
        return this.tile;
    }


}
