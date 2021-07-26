package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductStock;
import lombok.Data;

import java.util.List;

@Data
public class ProProductStockDto extends ProProductStock {

    private String productMallPriceStr;

    private String productMarketPriceStr;

    private List<ProProductSpecDto> list;
}
