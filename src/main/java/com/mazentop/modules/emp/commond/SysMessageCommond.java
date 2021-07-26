package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysHelpCenterType;
import com.mazentop.entity.SysMessage;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;
/**
 * @author: dengy
 * @date: 2020/3/20
 * @description:
 */
@Data
public class SysMessageCommond extends PageCommond {


    @Criteria(expression = Expression.EQ, property = SysMessage.F_COMPANY_ID, alias = SysMessage.TABLE_NAME)
    private String companyId;

    @Criteria(expression = Expression.LIKE, property = SysMessage.F_TITLE, alias = SysMessage.TABLE_NAME)
    private String title;
}
