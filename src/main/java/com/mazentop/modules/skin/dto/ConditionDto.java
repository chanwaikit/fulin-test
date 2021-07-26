package com.mazentop.modules.skin.dto;

import lombok.Data;

@Data
public class ConditionDto {

    //条件字段
    private String conditionField;
    //条件符号
    private String conditionkey;
    //条件值
    private String conditionValue;
}
