package com.mazentop.modules.user.commond;

import com.mazentop.entity.ProProductSecInfo;
import com.mazentop.entity.ProProductSpec;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class ProProductSecInfoCommond extends BaseCommond {

    @Criteria(expression = Expression.IN, property = ProProductSecInfo.F_FK_SPEC_ID, alias = ProProductSecInfo.TABLE_NAME)
    private List<String> ids;

    @Criteria(expression = Expression.EQ, property = ProProductSecInfo.F_FK_STOCK_ID, alias = ProProductSecInfo.TABLE_NAME)
    private String stockId;
}
