package com.mazentop.modules.emp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Title: BaseLinePlotDto
 * @Description: 折线图基础数据
 * @Author liuq
 * @Date 2020/4/26 14:15
 */
@Data
public class BaseGroupPlotDto {
    /**
     * 图表key
     */
    private String key;
    /**
     * 图表值
     */
    private BigDecimal value;

    /**
     * 图表附加参数
     */
    private String type;
}
