package com.mazentop.modules.emp.commond;

import com.mazentop.entity.CliClienteleReceiverAddress;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @title: cliClienteleReceiverAddressCommond
 * @description: 客户收货地址
 * @date 2020/11/10 14:31
 */
@Data
public class CliClienteleReceiverAddressCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = CliClienteleReceiverAddress.F_FK_CLIENTELE_ID, alias = CliClienteleReceiverAddress.TABLE_NAME)
    private String fkClienteleId;
}
