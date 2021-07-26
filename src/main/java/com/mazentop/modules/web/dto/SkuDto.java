package com.mazentop.modules.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class SkuDto {
    private String spec;

    private List<String> specItem;
}
