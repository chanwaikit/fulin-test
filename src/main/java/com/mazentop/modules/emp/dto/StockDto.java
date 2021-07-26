package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductStock;
import lombok.Data;

@Data
public class StockDto extends ProProductStock{
    private String specParent;
    private String spec;
}
