package com.mazentop.modules.emp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SelectDto implements Serializable {

    private String value;

    private String label;
}
