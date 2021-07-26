package com.mazentop.model;

public enum ExcelEnum {
    /**
     *
     */

    EXCEL_ORDER("order"),

    EXCEL_CLIENTELE("clientele"),

    /**
     * 产品导出
     */
    EXCEL_PRODUCT("product"),


    /**
     * 测评-产品导出
     */
    EXCEL_EVAPRODUCT("evaProduct"),

    /**
     * 库存导出
     */
    EXCEL_STOCK("stock"),

    /**
     * 评论导出
     */
    EXCEL_COMMENT("comment"),

    /**
     * 返现订单
     */
    EXCEL_EVACASHBACKAPPLY("evaCashBackApply"),

    EXCEL_ERROR("e");

    private final String key;

    ExcelEnum(String key){
        this.key = key;
    }

    public static String getKye(String value) {
        ExcelEnum[] uploadProductStatusEnums = values();
        for (ExcelEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.key().equals(value)) {
                return uploadProductStatusEnum.key();
            }
        }
        return null;
    }

    public String key(){
        return this.key;
    }
}
