package com.mazentop.modules.emp.commond;

import com.mazentop.entity.OrdPaymentOfflineRecord;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

@Data
public class OrdOfflinePaymentRecordCommond {
    @Criteria(expression = Expression.EQ, property = OrdPaymentOfflineRecord.F_FK_SALES_ORDER_ID, alias = OrdPaymentOfflineRecord.TABLE_NAME)
    private SearchTime OrderId;
}
