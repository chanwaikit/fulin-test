package com.mazentop.model;

public enum OptionsEnum {
    //seo key
    SITE_SEO_TITLE("site_seo_title"),
    SITE_SEO_LOGO("site_seo_logo"),
    SITE_SEO_FAVICON("site_seo_favicon"),
    SITE_SEO_KEYWORD("site_seo_keyword"),
    SITE_SEO_DESCRIPTION("site_seo_description"),
    SITE_SETTING_EMAIL("site_setting_site_email"),
    SITE_SETTING_DOMAIN_NAME("site_setting_domain_name");


    private final  String key;


    OptionsEnum(String key){
        this.key = key;
    }

    public String key(){
        return this.key;
    }


}
