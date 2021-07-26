package com.mazentop.modules.web.dto;

import com.mazentop.entity.ActPromotionProduct;
import com.mazentop.entity.ProProductStock;
import lombok.Data;

import java.util.List;

@Data
public class ProProductStockDto {

    private String productMallPrice;

    private String productMarketPrice;

    private String productMasterImageUrl;

    private Integer productStockNumber;

    private String id;

    private String productName;

    private String spec;

    private String specName;

    private ActPromotionProduct actPromotionProduct;
}
