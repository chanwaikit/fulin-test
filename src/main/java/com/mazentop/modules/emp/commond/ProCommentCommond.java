package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProComment;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

import java.util.List;

/**
 * @author: dengy
 * @date: 2020/3/12
 * @description:
 */
@Data
public class ProCommentCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = ProComment.F_PRODUCT_NAME, alias = ProComment.TABLE_NAME)
    private String productName;

    /**
     * 评论内容
     */
    @Criteria(expression = Expression.LIKE, property = ProComment.F_CONTENT, alias = ProComment.TABLE_NAME)
    private String content;


    @Criteria(expression = Expression.EQ, property = ProComment.F_RANGE_NUM, alias = ProComment.TABLE_NAME)
    private Integer rangeNum;


    @Criteria(expression = Expression.EQ, property = ProComment.F_FK_PRODUCT_ID, alias = ProComment.TABLE_NAME)
    private String productId;


    @Criteria(expression = Expression.EQ, property = ProComment.F_IS_DISPLAY, alias = ProComment.TABLE_NAME)
    private String isDisplay;


    @Criteria(expression = Expression.EQ, property = ProComment.F_IS_AUDIT_PASS, alias = ProComment.TABLE_NAME)
    private Integer isAuditPass;


    private String proProductColorSizeId;

    private String colorName;

    @Criteria(expression = Expression.IN, property = ProComment.F_ID, alias = ProComment.TABLE_NAME)
    private List<String> ids;

}
