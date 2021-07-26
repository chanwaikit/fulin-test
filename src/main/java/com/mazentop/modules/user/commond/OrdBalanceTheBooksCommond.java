package com.mazentop.modules.user.commond;

import com.mazentop.entity.OrdBalanceTheBooks;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

/**
 * @author chen quan
 * @date 2020/4/13 15:30
 **/
@Data
public class OrdBalanceTheBooksCommond extends BaseCommond {

    @Criteria(expression = Expression.LIKE, property = OrdBalanceTheBooks.F_SHOPPING_CART_LIST, alias = OrdBalanceTheBooks.TABLE_NAME)
    private String shoppingCartList;

}
