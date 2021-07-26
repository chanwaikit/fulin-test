package com.mazentop.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 筛选条件枚举
 */
@Getter
@AllArgsConstructor
public enum ConditionEnum {

    /**
     * 在之间
     */
    BETWEEN("BETWEEN", "在之间"),


    EQ("EQ", "等于"),


    UEQ("UEQ", "不等于"),


    WITHIN("WITHIN", "多少天以内");


    private String key;

    private String desc;


}
