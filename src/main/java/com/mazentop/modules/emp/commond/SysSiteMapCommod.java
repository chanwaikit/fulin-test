package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysNoticeSetting;
import com.mazentop.entity.SysSiteMap;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysSiteMapCommod extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysSiteMap.F_URL_NAME, alias = SysSiteMap.TABLE_NAME)
    private String urlName;
}
