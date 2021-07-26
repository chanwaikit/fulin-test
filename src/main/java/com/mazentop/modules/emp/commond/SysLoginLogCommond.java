package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysLoginLog;
import com.mazentop.entity.SysNoticeSetting;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysLoginLogCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysLoginLog.F_LOGIN_NAME, alias = SysLoginLog.TABLE_NAME)
    private String loginName;
}
