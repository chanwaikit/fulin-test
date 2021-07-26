package com.mazentop.modules.skin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mazentop.entity.*;
import com.mazentop.modules.skin.block.dto.CommentDto;
import com.mazentop.modules.skin.block.dto.GoodsDto;
import com.mazentop.modules.skin.commond.ProductSpecialCommond;
import com.mazentop.modules.skin.dto.ConditionDto;
import com.mazentop.modules.skin.dto.SimpleProductDto;
import com.mazentop.modules.skin.warpper.ConditionWarpper;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.service.ProductService;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductSpecialService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    SkinPageService skinPageService;

    @Autowired
    ProductService productService;

    public List<Map<String,Object>> getSpecialData(String pageId,Integer limit){
        List<Map<String,Object>> mapList;
        //根据页面编号获取商品专辑信息
        SkinPageData skinPageData = SkinPageData.me().setId(pageId).get();
        if("automatic".equals(skinPageData.getDataSource())) {

            Map<String, Object> maps =  JSON.parseObject(skinPageData.getDataOptions());
            List<ConditionWarpper> conditionData = JSON.parseArray(maps.get("conditionData").toString(), ConditionWarpper.class);
            Integer conditionFlag = Integer.parseInt(maps.get("conditionFlag").toString());

            mapList = getConditionGoodsSql(conditionData,conditionFlag, limit);
        }else{
            String sql = "select pro.* " +
                    "from pro_product_master pro,skin_page_data_snapshot dataSnapshot " +
                    "where 1=1 and pro.id = dataSnapshot.data_id and dataSnapshot.page_id =:id  " +
                    "limit :limit";
            Map<String,Object>param = new HashMap<>();
            param.put("id", pageId);
            param.put("limit", limit);
            mapList = baseDao.queryForList(sql,param);
        }
        return mapList;
    }


    private List<Map<String, Object>> getConditionGoodsSql(List<ConditionWarpper> conditionData, Integer conditionFlag, Integer limit) {
        List<Map<String, Object>> data;
        StringBuilder sql = new StringBuilder("SELECT pro.*,stock.product_mall_price,stock.product_market_price,stock.product_stock_number");
        sql.append(" FROM pro_product_master pro LEFT JOIN pro_product_stock stock ON pro.id = stock.fk_product_id ");
        sql.append(" WHERE 1 = 1 AND pro.is_enable = 1 ");
        String splicing = " and ";
        if(conditionFlag.equals(1)){
            splicing=" or ";
        }
        int i = 0;
        Map<String,Object> param = new HashMap<>();
        for (ConditionWarpper conditionWarpper : conditionData){
            //条件拼接符
            if(i == 0) {
                sql.append("and ");
            }else{
                sql.append(splicing);
            }
            //条件字段
            if("product_mall_price".equals(conditionWarpper.getConditionField()) || "product_stock_number".equals(conditionWarpper.getConditionField())){
                sql.append(" stock."+conditionWarpper.getConditionField());
                sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField());
                if("product_mall_price".equals(conditionWarpper.getConditionField())) {
                    param.put(conditionWarpper.getConditionField(), Helper.transformY2F(new BigDecimal(conditionWarpper.getConditionValue())));
                }else{
                    param.put(conditionWarpper.getConditionField(), conditionWarpper.getConditionValue());
                }
            }else{
                sql.append("pro."+conditionWarpper.getConditionField());
                if(!"like".equals(conditionWarpper.getConditionkey()) && !"not".equals(conditionWarpper.getConditionkey())){
                    param.put(conditionWarpper.getConditionField(), conditionWarpper.getConditionValue());
                }
                if("like".equals(conditionWarpper.getConditionkey()) || "not".equals(conditionWarpper.getConditionkey())){
                    param.put(conditionWarpper.getConditionField(), "%"+conditionWarpper.getConditionValue()+"%");
                }
                if("not".equals(conditionWarpper.getConditionkey())){
                    sql.append(" "+conditionWarpper.getConditionkey()+"like :"+conditionWarpper.getConditionField());
                }else{
                    sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField());
                }
            }
            i++;
        }
        sql.append(" GROUP BY stock.fk_product_id");
        if(limit != null){
             sql.append(" LIMIT " +limit);
        }
        data = baseDao.queryForList(sql.toString(),param);
        return data;
    }

    /**
     *  获取商品评论分数
     * @param productId
     * @return
     */
    public CommentDto handleComment(String productId){
        CommentDto commentDto = new CommentDto();
        List<ProComment> comments = ProComment.me().setFkProductId(productId).setIsAuditPass(1).find();
        if (comments.size() > 0) {
            OptionalDouble average = comments.stream().mapToInt(ProComment::getRangeNum).average();
            BigDecimal avg = new BigDecimal(average.getAsDouble());
            commentDto.setCommentScore(avg.setScale(1,BigDecimal.ROUND_DOWN));
            commentDto.setCommentNum(comments.size());
        }
        return commentDto;
    }

    /**
     * 是否当前用户加入购物车
     * @param productId
     * @return
     */
    public Boolean handleCart(String productId){
        Long countCart = OrdShoppingCart.me().setFkClienteleId(User.id()).setFkProductId(productId).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).findCount();
        if(countCart > 0){
           return true;
        }
        return false;
    }


    public Boolean handleCollection(String productId){
        Long count = CliCollectorItem.me().setFkProductId(productId).setFkClienteleId(User.id()).findCount();
        if(count > 0){
            return true;
        }
        return false;
    }



    public List<GoodsDto> getGoodsDtoList(String pageId,Integer limit){
        List<Map<String,Object>> mapList = getSpecialData(pageId, limit);
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        mapList.forEach(map->{
            GoodsDto goodsDto = new GoodsDto();
            //获取商品信息
            ProProductMaster productMaster = ProProductMaster.me().setId(map.get("id").toString()).get();
            //封装商品数据
            goodsDto.setId(productMaster.getId());
            goodsDto.setTitle(productMaster.getProductName());
            goodsDto.setSubTitle(productMaster.getProductDescription());
            goodsDto.setCoverUrl(productMaster.getPrductPicImageUrl());

            //封装商品图片
            List<ProProductImage> productImages = ProProductImage.me().setFkProductId(productMaster.getId()).find();
            List<String> images = new ArrayList<>();
            if(!productImages.isEmpty()){
                productImages.forEach(productImage->{
                    images.add(productImage.getProductImageUrl());
                });
            }
            goodsDto.setSwitchImgs(images);
            //获取商品调整链接
            goodsDto.setUrl(Seo.getSeoUrlForPorduct(productMaster.getId()));
            //处理商品金额
            List<ProProductStock> stockList = ProProductStock.me().setFkProductId(productMaster.getId()).find();
            List<Long> price = new ArrayList<>();
            List<Long> marketPrice = new ArrayList<>();
            stockList.forEach(stock->{
                price.add(stock.getProductMallPrice());
                marketPrice.add(stock.getProductMarketPrice());
            });
            if(!price.isEmpty()){
                goodsDto.setMaxPrice(Helper.transformF2Y(Collections.max(price)));
                goodsDto.setMinPrice(Helper.transformF2Y(Collections.min(price)));
            }else{
                goodsDto.setMaxPrice(new BigDecimal(0));
                goodsDto.setMinPrice(new BigDecimal(0));
            }
            if(!marketPrice.isEmpty()){
                goodsDto.setMinMarketPrice(Helper.transformF2Y(Collections.min(marketPrice)));
            }else{
                goodsDto.setMinMarketPrice(new BigDecimal(0));
            }
            // 获取商品评论
            CommentDto commentDto = handleComment(productMaster.getId());
            goodsDto.setComment(commentDto);
            if(User.isAuth()){
                //是否当前用户加入购物车
                goodsDto.setIsCart(handleCart(productMaster.getId()));
                //是否当前用户加入收藏
                goodsDto.setIsCollection(handleCollection(productMaster.getId()));
            }
            goodsDtoList.add(goodsDto);
        });
        return goodsDtoList;
    }


    public List<SimpleProductDto> getProductSpecial(String id, Integer page, Integer pageSize){
        if(Utils.isBlank(id)) {
            return Lists.newArrayList();
        }
        ProductSpecialCommond productSpecialCommond = new ProductSpecialCommond();
        productSpecialCommond.setP(page);
        productSpecialCommond.setPageSize(pageSize);
        List<Map<String,Object>> mapList = getSpecialData(id,productSpecialCommond);
        List<SimpleProductDto> goodsDtoList = new ArrayList<>();
        mapList.forEach(map->{
            SimpleProductDto goodsDto = new SimpleProductDto();
            //获取商品信息
            ProProductMaster productMaster = ProProductMaster.me().setId(map.get("id").toString()).get();
            //封装商品数据
            goodsDto.setId(productMaster.getId());
            goodsDto.setName(productMaster.getProductName());
            goodsDto.setImgUrl(productMaster.getPrductPicImageUrl());
            //获取商品调整链接
            goodsDto.setUrl(Seo.getSeoUrlForPorduct(productMaster.getId()));
            //获取商品评分
//            goodsDto.setScoreCount(productService.getScoreCount(productMaster.getId()));
            //处理商品金额
            if (productMaster.getIsSingleProduct() == 1) {
                ProProductStock proProductStock = new ProProductStock().setFkProductId(productMaster.getId()).get();
                if(!Objects.isNull(proProductStock)) {
                    goodsDto.setPrice(Helper.transformF2Y(proProductStock.getProductMallPrice()));
                    goodsDto.setOldPrice(Helper.transformF2Y(proProductStock.getProductMarketPrice()));
                }
            }else{
                List<ProProductStock> stockList = ProProductStock.me().setFkProductId(productMaster.getId()).find();
                List<Long> price = new ArrayList<>();
                List<Long> oldPrice = new ArrayList<>();
                stockList.forEach(stock->{
                    price.add(stock.getProductMallPrice());
                    oldPrice.add(stock.getProductMarketPrice());
                });
                if(!price.isEmpty()){
                    goodsDto.setPrice(Helper.transformF2Y(Collections.min(price)));
                    goodsDto.setOldPrice(Helper.transformF2Y(Collections.min(oldPrice)));
                }else{
                    goodsDto.setPrice(new BigDecimal(0));
                    goodsDto.setOldPrice(new BigDecimal(0));
                }
            }
            // 获取商品评论
            CommentDto commentDto = handleComment(productMaster.getId());
            goodsDto.setComment(commentDto);
            //是否当前用户加入购物车
            goodsDto.setIsCart(handleCart(productMaster.getId()));
            //是否当前用户加入收藏
            goodsDto.setIsCollection(handleCollection(productMaster.getId()));

            goodsDtoList.add(goodsDto);
        });
        return goodsDtoList;
    }

    public List<SimpleProductDto> getProductSpecialNoLogin(String id, Integer page, Integer pageSize){
        if(Utils.isBlank(id)) {
            return Lists.newArrayList();
        }
        ProductSpecialCommond productSpecialCommond = new ProductSpecialCommond();
        productSpecialCommond.setP(page);
        productSpecialCommond.setPageSize(pageSize);
        List<Map<String,Object>> mapList = getSpecialData(id,productSpecialCommond);
        List<SimpleProductDto> goodsDtoList = new ArrayList<>();
        mapList.forEach(map->{
            SimpleProductDto goodsDto = new SimpleProductDto();
            //获取商品信息
            ProProductMaster productMaster = ProProductMaster.me().setId(map.get("id").toString()).get();
            //封装商品数据
            goodsDto.setId(productMaster.getId());
            goodsDto.setName(productMaster.getProductName());
            goodsDto.setImgUrl(productMaster.getPrductPicImageUrl());
            //获取商品调整链接
            goodsDto.setUrl(Seo.getSeoUrlForPorduct(productMaster.getId()));
            //获取商品评分
//            goodsDto.setScoreCount(productService.getScoreCount(productMaster.getId()));
            //处理商品金额
            if (productMaster.getIsSingleProduct() == 1) {
                ProProductStock proProductStock = new ProProductStock().setFkProductId(productMaster.getId()).get();
                if(!Objects.isNull(proProductStock)) {
                    goodsDto.setPrice(Helper.transformF2Y(proProductStock.getProductMallPrice()));
                    goodsDto.setOldPrice(Helper.transformF2Y(proProductStock.getProductMarketPrice()));
                }
            }else{
                List<ProProductStock> stockList = ProProductStock.me().setFkProductId(productMaster.getId()).find();
                List<Long> price = new ArrayList<>();
                List<Long> oldPrice = new ArrayList<>();
                stockList.forEach(stock->{
                    price.add(stock.getProductMallPrice());
                    oldPrice.add(stock.getProductMarketPrice());
                });
                if(!price.isEmpty()){
                    goodsDto.setPrice(Helper.transformF2Y(Collections.min(price)));
                    goodsDto.setOldPrice(Helper.transformF2Y(Collections.min(oldPrice)));
                }else{
                    goodsDto.setPrice(new BigDecimal(0));
                    goodsDto.setOldPrice(new BigDecimal(0));
                }
            }
            // 获取商品评论
            CommentDto commentDto = handleComment(productMaster.getId());
            goodsDto.setComment(commentDto);
            goodsDtoList.add(goodsDto);
        });
        return goodsDtoList;
    }

    public List<Map<String,Object>> getSpecialData(String pageId, ProductSpecialCommond productSpecialCommond){
        List<Map<String,Object>> mapList;
        //根据页面编号获取商品专辑信息
        SkinPageData skinPageData = SkinPageData.me().setId(pageId).get();
        switch (skinPageData.getDataSource()){
            case "automatic":
                Map<String,Object> maps = (Map<String, Object>) JSONObject.parse(skinPageData.getDataOptions());
                List<ConditionDto> conditionData = JSONArray.parseArray(maps.get("conditionData").toString(), ConditionDto.class);
                Integer conditionFlag = Integer.parseInt(maps.get("conditionFlag").toString());
                mapList = getConditionGoodsSql(conditionData,conditionFlag,productSpecialCommond);
                break;
            case "manual":
                String sql = "select pro.* from pro_product_master pro, skin_page_data_snapshot dataSnapshot where 1=1 and pro.id = dataSnapshot.data_id and dataSnapshot.page_id =:id";
                Map<String,Object>param = new HashMap<>();
                param.put("id",pageId);
                mapList = baseDao.queryForListMapLimit(sql,param,productSpecialCommond);
                break;
            default:
                mapList = getActProductData(skinPageData.getDataOptions(),productSpecialCommond);
        }
        return mapList;
    }


    public List<Map<String,Object>> getActProductData(String id ,ProductSpecialCommond productSpecialCommond){
        Map<String,Object> param =new HashMap<>(1);
        String sql = "select pro.* " +
                "from pro_product_master pro, act_promotion_product act where 1=1 and pro.id = act.fk_product_id and act.fk_activity_id =:id";
        param.put("id",id);
        return baseDao.queryForListMapLimit(sql, param, productSpecialCommond);
    }

    private List<Map<String, Object>> getConditionGoodsSql(List<ConditionDto> conditionData, Integer conditionFlag,ProductSpecialCommond productSpecialCommond) {
        StringBuilder sql = new StringBuilder("SELECT pro.*");
        sql.append(" FROM pro_product_master pro LEFT JOIN pro_product_stock stock ON pro.id = stock.fk_product_id ");
        sql.append(" WHERE 1 = 1 AND pro.is_enable = 1 ");
        String splicing = " and ";
        if(conditionFlag.equals(1)){
            splicing=" or ";
        }
        int i = 0;
        Map<String,Object> param = new HashMap<>();
        for (ConditionDto conditionWarpper : conditionData){
            //条件拼接符
            if(i == 0) {
                sql.append("and ");
            }else{
                sql.append(splicing);
            }
            //条件字段
            if("product_mall_price".equals(conditionWarpper.getConditionField()) || "product_stock_number".equals(conditionWarpper.getConditionField())){
                sql.append("(pro."+conditionWarpper.getConditionField());
                sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField() +" or ");
                sql.append(" stock."+conditionWarpper.getConditionField());
                sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField()+" )");
                if("product_mall_price".equals(conditionWarpper.getConditionField())) {
                    param.put(conditionWarpper.getConditionField(), Helper.transformY2F(new BigDecimal(conditionWarpper.getConditionValue())));
                }else{
                    param.put(conditionWarpper.getConditionField(), conditionWarpper.getConditionValue());
                }
            }else{
                sql.append("pro."+conditionWarpper.getConditionField());
                if(!"like".equals(conditionWarpper.getConditionkey()) && !"not".equals(conditionWarpper.getConditionkey())){
                    param.put(conditionWarpper.getConditionField(), conditionWarpper.getConditionValue());
                }
                if("like".equals(conditionWarpper.getConditionkey()) || "not".equals(conditionWarpper.getConditionkey())){
                    param.put(conditionWarpper.getConditionField(), "%"+conditionWarpper.getConditionValue()+"%");
                }
                if("not".equals(conditionWarpper.getConditionkey())){
                    sql.append(" "+conditionWarpper.getConditionkey()+"like :"+conditionWarpper.getConditionField());
                }else{
                    sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField());
                }
            }
            i++;
        }
        sql.append(" GROUP BY stock.fk_product_id");
        return baseDao.queryForListMapLimit(sql.toString(),param,productSpecialCommond);
    }
}
