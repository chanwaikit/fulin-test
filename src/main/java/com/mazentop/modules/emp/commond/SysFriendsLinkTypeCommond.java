package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysFriendsLink;
import com.mazentop.entity.SysFriendsLinkType;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysFriendsLinkTypeCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property =  SysFriendsLinkType.F_FRIENDS_LINK_TYPE_NAME, alias =  SysFriendsLinkType.TABLE_NAME)
    private String friendsLnkTypeName;
}
