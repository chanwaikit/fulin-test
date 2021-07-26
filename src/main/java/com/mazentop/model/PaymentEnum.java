package com.mazentop.model;

public enum  PaymentEnum {
    PAYMENT_TYPE_PAYPAL("0","PayPal"),
    PAYMENT_TYPE_ASIABLILL("1","Asiabill"),
    PAYMENT_TYPE_OFFLINE("2","offline");

    private final String type;

    private final String names;

    PaymentEnum(String type, String names) {
        this.type = type;
        this.names = names;
    }
    public static String getName(String value) {
        PaymentEnum[] uploadProductStatusEnums = values();
        for (PaymentEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.type().equals(value)) {
                return uploadProductStatusEnum.names();
            }
        }
        return null;
    }

    public String type(){
        return this.type;
    }

    public String names(){
        return this.names;
    }
}
