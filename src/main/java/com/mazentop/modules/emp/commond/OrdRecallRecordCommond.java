package com.mazentop.modules.emp.commond;

import com.mazentop.entity.OrdRecallRecord;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

/**
 * @author chen quan
 * @date 2020/4/13 16:40
 **/
@Data
public class OrdRecallRecordCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = OrdRecallRecord.F_IS_SEND_SUCCESS, alias = OrdRecallRecord.TABLE_NAME)
    private Integer isSendSuccess;

    @Criteria(expression = Expression.EQ, property = OrdRecallRecord.F_ID, alias = OrdRecallRecord.TABLE_NAME)
    private String id;

    @Criteria(expression = Expression.EQ, property = OrdRecallRecord.F_RECALL_STATUS, alias = OrdRecallRecord.TABLE_NAME)
    private String recallStatus;

    @Criteria(expression = Expression.BETWEEN, property = OrdRecallRecord.F_ADD_TIME, alias = OrdRecallRecord.TABLE_NAME)
    private SearchTime addTime;

    @Criteria(expression = Expression.LIKE,property = OrdRecallRecord.F_FK_BALANCE_THE_BOOKS_ID, alias = OrdRecallRecord.TABLE_NAME)
    private String fkBalanceTheBooksId;

    private Long startTime;

    private Long endTime;


}
