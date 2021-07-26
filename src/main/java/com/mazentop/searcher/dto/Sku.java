package com.mazentop.searcher.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * sku
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/5/19 03:56
 */
@Data
public class Sku implements Serializable {

    private static final long serialVersionUID = 5352347703638357568L;

    private String code;

    private String name;

    private String value;

    private String imgUrl;

    private Double weight;

    private String costPrice;

    private String price;

    private Integer stock;

    private List<Attr> attrs;

    @Data
    public static class Attr implements Serializable {

        private static final long serialVersionUID = -5618211171153099426L;

        private String name;

        private String nameCode;

        private String val;

        private String valCode;

        private String imgUrl;

    }
}
