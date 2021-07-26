package com.mazentop.modules.web.commond;

import com.mazentop.entity.CliCollectorItem;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * 我的收藏分页查询
 *
 * @author mzt
 */
@Data
public class CliCollectorItemCommond extends PageCommond {

    @Criteria(expression = Expression.EQ
            , property = CliCollectorItem.F_FK_CLIENTELE_ID
            , alias = CliCollectorItem.TABLE_NAME)
    private String clientId;

    @Criteria(expression = Expression.EQ
            , property = CliCollectorItem.F_FK_COLLECTOR_ITEM_TYPE_ID
            , alias = CliCollectorItem.TABLE_NAME)
    private String collectorItemTypeId;


}
