package com.mazentop.modules.emp.commond;

import com.mazentop.entity.BloBlog;
import com.mazentop.entity.BloBlogType;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/11
 * @description:
 */
@Data
public class BloBlogTypeCommond extends PageCommond{

    @Criteria(expression = Expression.LIKE, property = BloBlogType.F_BLOG_TYPE_NAME, alias = BloBlogType.TABLE_NAME)
    private String typeName;

    @Criteria(expression = Expression.EQ , property = BloBlogType.F_COMPANY_ID, alias = BloBlogType.TABLE_NAME)
    private String companyId;
}
