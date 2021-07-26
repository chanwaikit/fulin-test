package com.mazentop.modules.emp.dto;

import lombok.Data;

/**
 * @author: SRC
 * @create: 2021-05-18 16:59
 **/
@Data
public class EvaOrderDto {
    private String id;
    private String reviewerRemarks;
    private String type;
    private Integer status;
}
