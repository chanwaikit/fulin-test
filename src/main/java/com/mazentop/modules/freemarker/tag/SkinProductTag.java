package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProProductMaster;
import com.mztframework.render.Tag;
import org.springframework.stereotype.Component;

@Component
public class SkinProductTag extends Tag {
    @Override
    public void execute() {
        String productId = getParam("productId");
        ProProductMaster proProductMaster = new ProProductMaster().setId(productId).get();
        setVariable("product", proProductMaster);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_product";
    }
}
