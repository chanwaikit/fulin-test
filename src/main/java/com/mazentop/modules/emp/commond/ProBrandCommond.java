package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProBrand;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/12
 * @description:
 */

@Data
public class ProBrandCommond extends PageCommond{


    @Criteria(expression = Expression.LIKE, property = ProBrand.F_BRAND_NAME, alias = ProBrand.TABLE_NAME)
    private String name;


    @Criteria(expression = Expression.EQ, property = ProBrand.F_IS_ENABLE, alias = ProBrand.TABLE_NAME)
    private Integer isEnable;

}
