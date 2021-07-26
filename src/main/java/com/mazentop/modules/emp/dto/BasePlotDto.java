package com.mazentop.modules.emp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Title: BasePlotDto
 * @Description: 基础图表对象
 * @Author liuq
 * @Date 2020/4/27 17:22
 */
@Data
public class BasePlotDto {
    /**
     * 图表key
     */
    private String key;
    /**
     * 图表值
     */
    private BigDecimal value;
}
