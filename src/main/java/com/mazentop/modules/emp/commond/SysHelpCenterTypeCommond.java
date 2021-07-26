package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysHelpCenterType;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: dengy
 * @date: 2020/3/19
 * @description:
 */
@Data
public class SysHelpCenterTypeCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = SysHelpCenterType.F_COMPANY_ID, alias = SysHelpCenterType.TABLE_NAME)
    private String companyId;

    @Criteria(expression = Expression.LIKE, property = SysHelpCenterType.F_HELP_CENTER_TYPE_NAME, alias = SysHelpCenterType.TABLE_NAME)
    private String query;
}
