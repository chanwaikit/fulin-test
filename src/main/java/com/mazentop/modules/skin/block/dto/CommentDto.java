package com.mazentop.modules.skin.block.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品评论
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/20 16:29
 */
@Data
public class CommentDto {

    /**
     * 评论条数
     */
    private Integer commentNum = 0;

    /**
     * 评论分数
     */
    private BigDecimal commentScore = new BigDecimal(0);


}
