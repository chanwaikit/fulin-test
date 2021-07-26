package com.mazentop.modules.evaluation.dto;

import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProSeo;
import com.mazentop.modules.emp.dto.ImageDto;
import lombok.Data;

import java.util.List;

/**
 * @author dengy
 * @title: ProProductMasterDto
 * @description: 商品管理新增/修改参数dto
 * @date 2020/3/13 11:29
 */
@Data
public class ProProductDto extends EvaProProduct {


    /**
     * 商品图片集合
     */
    List<ImageDto> images;

    /**
     * 商品id
     */
    List<String> ids;

    /**
     * 商品分类
     */
    private List<List<String>> typeIds;


    /**
     * 购买提示
     */
    private List<String> tipList;


    /**
     * 商品seo
     */
    private ProSeo seo;


    /**
     * 商品价格
     */
    private String priceStr;

    /**
     * 商品原价
     */
    private String originalPriceStr;


    private String rebatesStr;

}
