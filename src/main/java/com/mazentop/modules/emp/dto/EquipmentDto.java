package com.mazentop.modules.emp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentDto {

    private String item;

    private Integer count;

    private BigDecimal percent;
}
