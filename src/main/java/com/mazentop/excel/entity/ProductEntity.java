package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ProductEntity {
    @Excel(name = "商品名称", width = 15 )
    private String productName;

    @Excel(name = "商品Sku", width = 15 )
    private String productSku;

    @Excel(name = "商品商城价格", width = 15 )
    private String productMallPrice;

    @Excel(name = "商品活动后价格", width = 15 )
    private String productActivityPrice;

    @Excel(name = "商品数量", width = 15 )
    private String productNum;

    @Excel(name = "商品预览图", type = 2, width = 20,height = 30 )
    private String productImageUrl;
}
