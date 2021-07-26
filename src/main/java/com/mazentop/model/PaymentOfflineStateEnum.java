package com.mazentop.model;

public enum PaymentOfflineStateEnum {
    PAYMENT_STATE_APPLY(0,"申请"),
    PAYMENT_STATE_SUCCESS(1,"通过"),
    PAYMENT_STATE_REJECT(2,"驳回"),
    PAYMENT_STATE_REPEAT(3,"复审");

    private final Integer state;

    private final String type;

    PaymentOfflineStateEnum(Integer state,String type){
        this.state = state;
        this.type = type;
    }

    public static PaymentOfflineStateEnum getState(Integer value) {
        PaymentOfflineStateEnum[] uploadProductStatusEnums = values();
        for (PaymentOfflineStateEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.state.equals(value)) {
                return uploadProductStatusEnum;
            }
        }
        return PAYMENT_STATE_APPLY;
    }

    public Integer state(){
        return this.state;
    }

    public String type(){
        return this.type;
    }
}
