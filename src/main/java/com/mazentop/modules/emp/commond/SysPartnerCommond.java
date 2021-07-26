package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysFriendsLink;
import com.mazentop.entity.SysPartner;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysPartnerCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysPartner.F_PARTNER_TITLE, alias = SysPartner.TABLE_NAME)
    private String partnerTitle;
}
