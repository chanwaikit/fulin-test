package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysAdvertisementPop;
import com.mazentop.entity.SysNoticeSetting;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysNoticeSettingCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysNoticeSetting.F_NOTICE_TITLE, alias = SysNoticeSetting.TABLE_NAME)
    private String noticeTitle;
}
