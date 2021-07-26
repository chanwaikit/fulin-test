package com.mazentop.modules.emp.commond;

import com.mazentop.entity.CliClienteleGroup;
import com.mazentop.entity.CliClienteleInfo;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @title: CliClienteleGroupCommond
 * @description: 顾客分组
 * @date 2020/011/09 16:31
 */
@Data
public class CliClienteleGroupCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = CliClienteleGroup.F_NAME, alias = CliClienteleGroup.TABLE_NAME)
    private String name;



}
