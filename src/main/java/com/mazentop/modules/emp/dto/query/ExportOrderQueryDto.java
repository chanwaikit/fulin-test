package com.mazentop.modules.emp.dto.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExportOrderQueryDto {
    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "查询关键字")
    private String query;

    @ApiModelProperty(value = "订单状态")
    private String salesOrderStatus;

    @ApiModelProperty(value = "导出条数")
    private Integer size;

    @ApiModelProperty(value = "订单号数组")
    private List<String> salesOrderNo;

    @ApiModelProperty(value = "订单类型线上/线下")
    private Integer type;


}
