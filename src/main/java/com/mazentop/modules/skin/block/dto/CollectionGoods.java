package com.mazentop.modules.skin.block.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 专辑商品块
 *
 * @author dengy
 * @version 1.0
 * @date 2020/6/19 18:41
 */
@Data
public class CollectionGoods  implements Serializable {

    private String title;

    private String collection;

    private String specialTitle;

}
