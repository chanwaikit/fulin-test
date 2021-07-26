package com.mazentop.modules.skin.service;

import com.mazentop.entity.*;
import com.mazentop.model.Constant;
import com.mazentop.modules.skin.block.dto.GoodsDto;
import com.mazentop.modules.web.User;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.plugins.session.SessionUtil;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EvaProductSpecialService {

    @Autowired
    BaseDao baseDao;


    /**
     * 获取测评专辑商品列表
     * @param pageId
     * @param limit
     * @return
     */
    public List<GoodsDto> getEvaGoodsDtoList(String pageId,Integer limit){
        List<EvaProProduct> productList = getSpecialData(pageId, limit);
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        productList.forEach(product->{
            GoodsDto goodsDto = new GoodsDto();
            //封装商品数据
            goodsDto.setId(product.getId());
            goodsDto.setTitle(product.getProductName());
            goodsDto.setCoverUrl(product.getProductPicImageUrl());

            //封装商品图片
            List<ProProductImage> productImages = ProProductImage.me().setFkProductId(product.getId()).find();

            List<String> images = productImages.stream().map(ProProductImage::getProductImageUrl).collect(Collectors.toList());
            goodsDto.setSwitchImgs(images);
            // 获取商品调整链接
            goodsDto.setUrl(Seo.getSeoUrlForPorduct(product.getId()));
            // 处理商品金额
            if (product.getPrice() != null) {
                goodsDto.setMinPrice(Helper.transformF2Y(product.getPrice()));
            } else {
                goodsDto.setMinPrice(new BigDecimal(0));
            }
            if (product.getOriginalPrice() != null) {
                goodsDto.setMinMarketPrice(Helper.transformF2Y(product.getOriginalPrice()));
            } else {
                goodsDto.setMinMarketPrice(new BigDecimal(0));
            }
            if (product.getRebates() != null) {
                goodsDto.setCashBack(Helper.transformF2Y(product.getRebates()));
            } else {
                goodsDto.setCashBack(new BigDecimal(0));
            }

            goodsDtoList.add(goodsDto);
        });
        return goodsDtoList;
    }



    public List<EvaProProduct> getSpecialData(String pageId,Integer limit){
        SkinCountry skinCountry = SessionUtil.getSessionCountry();
        String sql = "select pro.id,pro.product_name,pro.product_sku,pro.product_pic_image_url,pro.price,pro.original_price,pro.rebates " +
                "from eva_pro_product pro,skin_page_data_snapshot dataSnapshot " +
                "where pro.id = dataSnapshot.data_id and dataSnapshot.page_id =:id  and pro.country_id =:country and is_shelve=1 " +
                "limit :limit";
        Map<String,Object> param = new HashMap<>(2);
        param.put("id", pageId);
        param.put("country", skinCountry.getCountryCode());
        param.put("limit", limit);
        return baseDao.queryForBeanList(sql,param,EvaProProduct.me());
    }



}
