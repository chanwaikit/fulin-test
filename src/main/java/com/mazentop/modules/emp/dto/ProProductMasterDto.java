package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductSpec;
import com.mazentop.entity.ProSeo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author dengy
 * @title: ProProductMasterDto
 * @description: 商品管理新增/修改参数dto
 * @date 2020/3/13 11:29
 */
@Data
public class ProProductMasterDto extends ProProductMaster {

    List<ProProductSpec> specsList;

    List<ImageDto> images;

    List<Map<String,Object>> colorSizeList;

    List<Map<String,Object>> parentList;

    private String productMallPriceStr;

    private String purchasePriceStr;

    private String productMarketPriceStr;

    private String cusPriceStr;

    private String netWeightStr;

    private String totalWeightStr;

    private Integer num;

    private List<List<String>> typeIds;

    private List<Object> categoryIds;

    private ProSeo seo;

    private List<ProProductStockDto> proProductStock;

    private List<String> explain;

    /**
     * 商品库存数量
     */
    private Integer productStockNumber;


    private String productMallPrice;


    private String productMarketPrice;


    private String barCode;

}
