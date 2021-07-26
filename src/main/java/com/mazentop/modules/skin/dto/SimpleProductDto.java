package com.mazentop.modules.skin.dto;

import com.mazentop.modules.skin.block.dto.CommentDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SimpleProductDto implements Serializable {

    private String id;

    private String imgUrl;

    private String name;

    private String scoreCount;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private String url;

    /**
     * 评论对象
     */
    private CommentDto comment;


    private Boolean isCart;

    private Boolean isCollection;
}
