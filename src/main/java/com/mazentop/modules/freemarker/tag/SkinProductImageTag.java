package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProProductImage;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 产品图片
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/19 17:28
 */
@Component
@Slf4j
public class SkinProductImageTag extends Tag {

    @Override
    public void execute() {
        String productId = getParam("productId", "");

        if(!Utils.isBlank(productId)) {
            Map<String, Object> map = new HashMap<>(2);
            // 获取商品图片
            List<ProProductImage> proProductImages = ProProductImage.me().setOrderByFields(Order.asc(ProProductImage.F_SORT)).setFkProductId(productId).find();
            map.put("list", proProductImages);
            // 得到首图
            Optional<ProProductImage> productFirstImageOptional = proProductImages.stream().findFirst();
            productFirstImageOptional.ifPresent(productFirstImage -> map.put("firstImage", productFirstImage));

            setVariable("productImage", map);
            renderBody();
        } else {
            log.error("[@skin_product_image /] 缺少必要参数 productId，例如：[@skin_product_image productId=\"产品ID\" /]");
        }
    }

    @Override
    public String name() {
        return "skin_product_image";
    }
}
