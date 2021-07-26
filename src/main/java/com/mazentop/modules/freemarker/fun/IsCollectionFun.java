package com.mazentop.modules.freemarker.fun;

import com.mazentop.entity.CliCollectorItem;
import com.mazentop.modules.web.User;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;


/**
 * 是否收藏
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/27 09:38
 */
@Component
public class IsCollectionFun extends Function {

    private static String TYPE_PRODUCT = "product";

    @Override
    public Object exec() {
        String type = getToString(1, TYPE_PRODUCT);
        String id = getToString(0);
        if (User.isAuth()) {
            if(TYPE_PRODUCT.equals(type)) {
                Long collectionCount = CliCollectorItem.me().setFkProductId(id)
                        .setIsEnable(1).setFkClienteleId(User.id()).findCount();
                return collectionCount > 0;
            }
        }
        return false;
    }

    @Override
    public String name() {
        return "isCollection";
    }
}
