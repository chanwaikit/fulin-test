package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysCountry;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/19
 * @description:
 */
@Data
public class SysCountryCommond extends PageCommond{

    @Criteria(expression = Expression.EQ, property = SysCountry.F_CONTINENT_ID, alias = SysCountry.TABLE_NAME)
    private String continentId;

}
