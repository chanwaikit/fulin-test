package com.mazentop.modules.freemarker.tag;


import com.alibaba.druid.support.logging.Jdk14LoggingImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductStock;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.web.dto.ProProductStockDto;
import com.mazentop.modules.web.service.ProductService;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品类型
 *
 * @author dengy
 * @version 1.0
 * @date 2020/6/28 15:00
 */
@Component
@Slf4j
public class SkinProductStockTag extends Tag {

    @Autowired
    ProductService productService;

    @Autowired
    ProProductMasterService proProductMasterService;

    @Override
    public void execute() {
        String productId = getParam("productId");
        if(!Utils.isBlank(productId)) {
            setVariable("stockMap", productService.getSkinProductSkuData(productId));
            renderBody();
        }else{
            log.error("[@skin_product_stock /] 缺少必要参数 productId，例如：[@skin_product_stock productId=\"产品ID\" /]");
        }
    }

    @Override
    public String name() {
        return "skin_product_stock";
    }
}
