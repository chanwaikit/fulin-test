package com.mazentop.modules.freemarker.tag;

import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.modules.web.User;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SkinProductDiscountTag extends Tag{

    @Autowired
    ProductSpecialService productSpecialService;

    @Override
    public void execute() {
        String viewPrice = getParam("viewPrice");
        String oldPrice = getParam("oldPrice");

        Map<String, Object> map = new HashMap<>(2);

        Double oldPrice_d = Double.parseDouble(oldPrice);
        Double viewPrice_d = Double.parseDouble(viewPrice);
        Double num = (((oldPrice_d - viewPrice_d)/oldPrice_d))*100;
        map.put("num",num.longValue());
        setVariable("discount", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_product_discount";
    }
}
