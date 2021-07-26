package com.mazentop.model;

public enum SettlementTypeEnum {
    INFORMATION("information","信息页面"),
    SHIPPING("shipping","物流页面"),
    PAY("payment","发票地址");

    private final String type;

    private final String desc;

    SettlementTypeEnum(String type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static String getDesc(String value) {
        SettlementTypeEnum[] settlementTypeEnums = values();
        for (SettlementTypeEnum settlementTypeEnum : settlementTypeEnums) {
            if (settlementTypeEnum.type().equals(value)) {
                return settlementTypeEnum.desc();
            }
        }
        return null;
    }

    public String type(){
        return this.type;
    }

    public String desc(){
        return this.desc;
    }
}
