package com.mazentop.modules.emp.commond;

import com.mazentop.entity.BloBlog;
import com.mazentop.entity.EmpDepartmentInfo;
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
public class BloBlogCommond extends PageCommond{

    @Criteria(expression = Expression.LIKE_R, property = BloBlog.F_TITLE, alias = BloBlog.TABLE_NAME)
    private String title;

    @Criteria(expression = Expression.EQ, property = BloBlog.F_COMPANY_ID, alias = BloBlog.TABLE_NAME)
    private String companyId;

    @Criteria(expression = Expression.EQ, property = BloBlog.F_IS_PUBLISH, alias = BloBlog.TABLE_NAME)
    private Integer publish;

    @Criteria(expression = Expression.EQ,property = BloBlog.F_ID,alias = BloBlog.TABLE_NAME)
    private String id;
}
