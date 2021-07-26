package com.mazentop.searcher.keyword;

import com.mazentop.searcher.Keyword;
import com.mztframework.commons.Utils;
import lombok.Data;

/**
 * @author zhaoqt
 * @title: ProductKeyword
 * @description: 商品检索
 * @date 2019/5/1017:51
 */
@Data
public class ProductKeyword extends Keyword {

    public ProductKeyword() {
        setPagination(Boolean.TRUE);
    }

    public ProductKeyword(String value) {
        super(value);
    }

    private String c;

    private String tag;

    private String ev;

    private String color;

    private Double minPrice;

    private Double maxPrice;

    private Long pubdate;

    private String delivery;

    private Integer saleNum;

    private String country;

    public String getMakeKeyword() {
        if(!Utils.isBlank(this.ev)) {
            return String.format("%s %s", super.getValue(), this.ev);
        }
        return super.getValue();
    }
}
