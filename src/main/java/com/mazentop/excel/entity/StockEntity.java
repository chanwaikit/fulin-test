package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhoumei
 * @date: 2020/11/2
 * @description: 导出库存Excel实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity implements Serializable {

    @Excel(name = "库存ID", height = 20, width = 30)
    private String stockId;


    @Excel(name = "SKU", height = 20, width = 30)
    private String SKU;


    @Excel(name = "条形码", height = 20, width = 30)
    private String barCode;


    @Excel(name = "商品名称", height = 20, width = 30)
    private String productName;

    @Excel(name = "商品属性", height = 20, width = 30)
    private String productSpec;


    @Excel(name = "售价", height = 20, width = 30)
    private String mallPrice;


    @Excel(name = "原价", height = 20, width = 30)
    private String marketPrice;


    @Excel(name = "库存", height = 20, width = 30)
    private String productStockNumber;


    @Excel(name = "错误信息", height = 20, width = 30)
    private String error;


    /**
     * 库存表格填写描述信息
     */
    public static class TipInfo{
        public static final String ID_INFO = "（此列导入时不可删除,不能为空）库存 ID 是系统生成的唯一标识符，库存主体此列代表库存ID，导出后不可手动修改";
        public static final String SKU_INFO = "";
        public static final String BARCODE_INFO = "";
        public static final String PRODUCTNAME_INFO = "";
        public static final String PRODUCTSPEC_INFO = "";
        public static final String MALLPRICE_INFO = "";
        public static final String MARKETPRICE_INFO = "";
        public static final String PRODUCTSTOCKNUMBER_INFO = "必填";
    }

}
