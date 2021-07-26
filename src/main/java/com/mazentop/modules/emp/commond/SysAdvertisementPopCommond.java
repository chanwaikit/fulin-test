package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysAdvertisementPop;
import com.mazentop.entity.SysCountry;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysAdvertisementPopCommond extends PageCommond {
    @Criteria(expression = Expression.EQ, property = SysAdvertisementPop.F_ADVERTISEMENT_NAME, alias = SysAdvertisementPop.TABLE_NAME)
    private String query;


}
