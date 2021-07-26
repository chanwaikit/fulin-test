package com.mazentop.modules.skin.dto;

import com.mazentop.modules.web.dto.ProProductSpecDto;
import com.mazentop.modules.web.dto.ProProductStockDto;
import lombok.Data;

import java.util.List;

@Data
public class SkinProductSkuDto {

    private List<SkinProductSkuDto.Stock> stocks;

    private List<SkinProductSkuDto.Sku> skus;

    private String maxPrice;

    private String minPrice;

    private String minMarketPrice;

    private Integer stockNumber;

    private Integer isSingle;

    private String productId;
    @Data
    public static class Stock {

        private String id;

        private String productSubSku;

        private String mallPrice;

        private String marketPrice;

        private String imageUrl;

        private Integer stockNumber;

        private String productName;

        private String spec;

        private String specName;
    }

    @Data
    public static class Sku{
        private List<SkinProductSkuDto.Spec> specList;

        private Integer colorFlag;

        private String specName;
    }

    @Data
    public static class Spec{
        private String id;

        private String specName;

        private String colorCode;
    }

}
