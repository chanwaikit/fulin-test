package com.mazentop.modules.emp.commond;

import com.mazentop.entity.OrdShoppingCart;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class OrdShoppingCartCommond extends BaseCommond {
    @Criteria(expression = Expression.IN, property = OrdShoppingCart.F_ID, alias = OrdShoppingCart.TABLE_NAME)
    private List<String> ids;
}
