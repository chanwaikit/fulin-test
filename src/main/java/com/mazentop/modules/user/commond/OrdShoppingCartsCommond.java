package com.mazentop.modules.user.commond;

import com.mazentop.entity.OrdShoppingCart;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class OrdShoppingCartsCommond extends BaseCommond {

    @Criteria(expression = Expression.IN, property = OrdShoppingCart.F_ID, alias = OrdShoppingCart.TABLE_NAME)
    private List<String> carts;
}
