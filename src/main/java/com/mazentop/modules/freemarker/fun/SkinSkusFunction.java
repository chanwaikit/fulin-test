package com.mazentop.modules.freemarker.fun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.modules.web.service.ProductService;
import com.mztframework.commons.Utils;
import com.mztframework.render.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author dengy
 */
@Component
public class SkinSkusFunction extends Function {


    @Autowired
    ProductService productService;

    @Override
    public Object exec() {
        String productId = getToString(0);
        if(!Utils.isBlank(productId)) {
            return JSON.toJSONString(productService.getSkinProductSkuData(productId));
        }
        return null;
    }

    @Override
    public String name() {
        return "skinSku";
    }
}
