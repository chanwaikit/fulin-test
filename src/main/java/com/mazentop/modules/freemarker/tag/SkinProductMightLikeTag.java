package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.evaluation.dto.ProProductDto;
import com.mazentop.modules.skin.block.dto.GoodsDto;
import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.render.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品关键词查询相关推荐
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 01:40
 */
@Component
public class SkinProductMightLikeTag extends Tag {

    @Autowired
    ProductSpecialService productSpecialService;

    @Autowired
    BaseDao baseDao;

    @Override
    public void execute() {
        String productId = getParam("productId", "");

        Map<String, Object> map = new HashMap<>(2);
        if(!Utils.isBlank(productId)) {
            List<EvaProProduct> productList =  new ArrayList<EvaProProduct>();
            String sql = "select eva_pro_product.id,eva_pro_product.product_name,eva_pro_product.product_sku,eva_pro_product.product_pic_image_url,eva_pro_product.price,eva_pro_product.original_price,eva_pro_product.rebates " +
                    "from eva_pro_product " +
                    "where eva_pro_product.id " +
                    "in( " +
                    "   select fk_product_id " +
                    "   from pro_product_shortcut " +
                    "   where fk_product_type_id in( " +
                    "       select fk_product_type_id  " +
                    "       from pro_product_shortcut " +
                    "       where fk_product_id =:productId  " +
                    "       group by fk_product_type_id " +
                    "   ) " +
                    ") " +
                    "and eva_pro_product.is_shelve = 1 " +
                    "and eva_pro_product.id != :productId " +
                    "limit 5";
            map.put("productId",productId);
            List<EvaProProduct> list = baseDao.queryForBeanList(sql,map,EvaProProduct.me());
            for (EvaProProduct product : list) {
                if (product.getPrice() != null) {
                    product.addExten("price",Helper.transformF2Y(product.getPrice()));
                } else {
                    product.addExten("price",0L);
                }
                if (product.getOriginalPrice() != null) {
                    product.addExten("originalPrice",Helper.transformF2Y(product.getOriginalPrice()));
                } else {
                    product.addExten("originalPrice",0L);
                }
                if (product.getRebates() != null) {
                    product.addExten("rebates",Helper.transformF2Y(product.getRebates()));
                } else {
                    product.addExten("rebates",0L);
                }
                // url
                product.addExten("url",Seo.getSeoUrlDetail(product.getId(), ProSeoTypeEnum.TYPE_PRODUCT.type()));
                productList.add(product);
            }
            map.put("list",productList);
        }
        setVariable("relevant", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_product_might_like";
    }
}
