package com.mazentop.model;

public enum EmailTemplateTypeEnum {
    TYPE_CONFIRM_ORDER("confirm_order"),
    TYPE_RECOVERY_ORDER("recovery_order"),
    TYPE_CANCEL_ORDER("cancel_order"),
    TYPE_REFUND_ORDER("refund_order"),
    TYPE_DELIVERY_ORDER("delivery_order"),

    TYPE_WELCOME_USER("welcome_user"),
    TYPE_RESET_PWD("reset_pwd"),
    ;


    private final String type;

    EmailTemplateTypeEnum( String type){
        this.type = type;
    }
    public String type(){
        return this.type;
    }
}
