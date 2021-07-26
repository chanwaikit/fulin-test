package com.mazentop.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 正确，错误
 */
public enum BooleanEnum {

    TRUE(1, true),

    FALSE(0, false);

    @Getter
    @Setter
    private Integer value;
    @Getter
    @Setter
    private Boolean bool;

    BooleanEnum(Integer value, Boolean bool) {
        this.value = value;
        this.bool = bool;
    }

    public static BooleanEnum getBooleanEnumByValue(Integer value) {
        for (BooleanEnum booleanEnum : BooleanEnum.values()) {
            if (booleanEnum.getValue().equals(value)) {
                return booleanEnum;
            }
        }
        return null;
    }
}
