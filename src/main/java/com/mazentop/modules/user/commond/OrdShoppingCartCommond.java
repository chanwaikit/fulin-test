package com.mazentop.modules.user.commond;

import com.mazentop.entity.OrdShoppingCart;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

import java.util.List;

@Data
public class OrdShoppingCartCommond extends PageCommond {

    private String id;

    private Integer proNum;

    private String productSubSku;

    private List<String> specList;

    private Integer isSingle;


    @Criteria(expression = Expression.LE, property = OrdShoppingCart.F_ADD_TIME, alias = OrdShoppingCart.TABLE_NAME)
    private Long endTime;

    @Criteria(expression = Expression.EQ, property = OrdShoppingCart.F_IS_GEN_BALANCE_ACCOUNTS, alias = OrdShoppingCart.TABLE_NAME)
    private Integer isGenBalanceAccounts;


    @Criteria(expression = Expression.EQ, property = OrdShoppingCart.F_IS_GEN_RETURN_ORDER, alias = OrdShoppingCart.TABLE_NAME)
    private Integer isGenReturnOrder;

    @Criteria(expression = Expression.EQ, property = OrdShoppingCart.F_IS_GEN_ORDER, alias = OrdShoppingCart.TABLE_NAME)
    private Integer isGenOrder;

}
