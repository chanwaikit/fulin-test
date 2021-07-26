package com.mazentop.model;

public enum ProSeoTypeEnum {

    /**
     * 产品
     */
    TYPE_PRODUCT("产品", "product", "%s/product/"),

    /**
     * 博客
     */
    TYPE_BLOG("博客", "blog", "%s/blog/"),

    /**
     * 分类
     */
    TYPE_CATEGORY("分类", "category", "%s/category/"),

    /**
     * 帮助中心
     */
    TYPE_HELP("帮助", "help", "%s/help/"),

    /**
     * 模板
     */
    TYPE_SKIN("主题","collections","%s/collections/"),

    /**
     * 自定义页面
     */
    TYPE_PAGE("页面", "pages", "%s/pages/");

    private  String type;

    private  String names;

    private  String uri;

    ProSeoTypeEnum(String names, String type, String uri) {
        this.names = names;
        this.type = type;
        this.uri = uri;
    }
    public static ProSeoTypeEnum getProSeoTypeEnumByType(String type) {
        ProSeoTypeEnum[] uploadProductStatusEnums = values();
        for (ProSeoTypeEnum uploadProductStatusEnum : uploadProductStatusEnums) {
            if (uploadProductStatusEnum.type().equals(type)) {
                return uploadProductStatusEnum;
            }
        }
        return ProSeoTypeEnum.TYPE_PRODUCT;
    }

    public String type(){
        return this.type;
    }

    public String names(){
        return this.names;
    }

    public String getUri() {
        return uri;
    }
}
