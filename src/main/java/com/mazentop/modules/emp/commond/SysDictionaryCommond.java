package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysCountryProvinceCity;
import com.mazentop.entity.SysDictionary;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * dictionary 字典查询
 *
 * @author dengy
 * @version 1.0
 * @date 2020/7/7 19:39
 */
@Data
public class SysDictionaryCommond extends PageCommond {

    @Criteria(expression = Expression.EQ,property = SysDictionary.F_PCODE, alias = SysDictionary.TABLE_NAME)
    private String model;

    @Criteria(expression = Expression.LIKE,property = SysDictionary.F_NAME, alias = SysDictionary.TABLE_NAME)
    private String name;

}
