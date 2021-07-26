package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinCountry;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author dengy
 */
@Data
public class
SkinCountryCommond extends PageCommond {


    @Criteria(expression = Expression.LIKE, property = SkinCountry.F_COUNTRY_NAME_CN)
    private String countryNameCn;

    @Criteria(expression = Expression.EQ, property = SkinCountry.F_IS_ENABLE)
    private Integer isEnable;

}
