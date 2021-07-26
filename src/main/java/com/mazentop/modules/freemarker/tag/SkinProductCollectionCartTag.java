package com.mazentop.modules.freemarker.tag;

import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.modules.web.User;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SkinProductCollectionCartTag extends Tag{

    @Autowired
    ProductSpecialService productSpecialService;

    @Override
    public void execute() {
        String productId = getParam("productId");

        Map<String, Object> map = new HashMap<>(2);
        if(StringUtils.isNotBlank(productId)){
            if(User.isAuth()){
                map.put("isCollection", productSpecialService.handleCollection(productId));
                map.put("isCart", productSpecialService.handleCart(productId));
            }else{
                map.put("isCollection", false);
                map.put("isCart", false);
            }
            setVariable("collectionCart", map);
            renderBody();
        }else {
            log.error("[@skin_product_collection /] 缺少必要参数 id，例如：[@skin_product_collection id=\"块ID\" /]");
        }
    }

    @Override
    public String name() {
        return "skin_product_collection_cart";
    }
}
