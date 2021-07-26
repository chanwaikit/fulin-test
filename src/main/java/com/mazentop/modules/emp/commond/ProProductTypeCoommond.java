package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProProductType;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/13
 * @description:
 */
@Data
public class ProProductTypeCoommond extends PageCommond{

    @Criteria(expression = Expression.LIKE, property = ProProductType.F_PRODUCT_TYPE_NAME, alias = ProProductType.TABLE_NAME)
    private String productTypeName;

    private String root = "root";

}
