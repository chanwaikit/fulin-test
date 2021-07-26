package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysFriendsLink;
import com.mazentop.entity.SysHelpCenterContent;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysFriendsLinkCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysFriendsLink.F_FRIENDS_LINK_TITLE, alias = SysFriendsLink.TABLE_NAME)
    private String friendsLinkTitle;
}
