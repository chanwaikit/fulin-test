package com.mazentop.modules.emp.dto.query;

import com.mazentop.entity.ProComment;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;
@Data
public class ProCommentQueryDto extends BaseCommond {

    @Criteria(expression = Expression.LIKE, property = ProComment.F_PRODUCT_NAME, alias = ProComment.TABLE_NAME)
    private String productName;

    @Criteria(expression = Expression.EQ, property = ProComment.F_FK_PRODUCT_ID, alias = ProComment.TABLE_NAME)
    private String productId;

//    @ApiModelProperty(value = "导出条数")
//    private Integer size;

    @Criteria(expression = Expression.IN, property = ProComment.F_ID, alias = ProComment.TABLE_NAME)
    private List<String> ids;
}
