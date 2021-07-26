package com.mazentop.modules.emp.commond;


import com.mazentop.entity.SysEmailSendRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SysEmailSendRecordCommond extends PageCommond {
    @Criteria(expression = Expression.LIKE, property = SysEmailSendRecord.F_SEND_PERSON_LIST, alias = SysEmailSendRecord.TABLE_NAME)
    private String query;
}
