package com.mazentop.modules.emp.model;

import lombok.Data;

/**
 * 客户分组，筛选条件
 */
@Data
public class ScreenConditionDto {

    private String expression;

    private String field;

    private String name;

    private String value;
}
