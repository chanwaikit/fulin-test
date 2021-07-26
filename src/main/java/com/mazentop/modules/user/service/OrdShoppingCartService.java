package com.mazentop.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.listener.MessageSource;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.emp.commond.ProCommentCommond;
import com.mazentop.modules.emp.commond.ProProductQaCommond;
import com.mazentop.modules.user.commond.ActConditionTypeCommond;
import com.mazentop.modules.user.dto.CreateOrder;
import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.user.dto.SettlementConfirmDto;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.dto.ProCommentDto;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.modules.web.service.TouristService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.plugins.event.EventHolder;
import com.mazentop.plugins.event.Message;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.cache.Options;
import com.mztframework.commons.Lists;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.file.service.IUploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 购物车业务类
 *
 * @author dengy
 */
@Service
public class OrdShoppingCartService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    IUploadService uploadService;

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;

    @Autowired
    PaypalService paypalService;

    @Autowired
    TouristService touristService;

    /**
     * 添加购物车
     *
     * @param id
     * @param cliClienteleInfo
     * @return
     */
    public R doAddOrdShopping(String id, Integer proNum, CliClienteleInfo cliClienteleInfo, String productSubSku) {
        //获取商品信息
        ProProductMaster productMaster = ProProductMaster.me().setId(id).get();
        if (Helper.isNotEmpty(productSubSku)) {
            ProProductStock proProductStock = ProProductStock.me().setProductSubSku(productSubSku).setIsEnable(BooleanEnum.TRUE.getValue()).get();
            if (Objects.isNull(proProductStock)) {
                return R.error("该商品属性不存在！");
            }
            if (proProductStock.getProductStockNumber() < proNum) {
                return R.error("商品库存不足！");
            }
        }
        OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
        Long count;
        count = OrdShoppingCart.me().setFkClienteleId(cliClienteleInfo.getId()).setFkProductId(productMaster.getId()).setProductSubSku(productSubSku).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).findCount();
        //封装购物车信息
        if (count > 0) {
            ordShoppingCart = OrdShoppingCart.me().setFkClienteleId(cliClienteleInfo.getId()).setFkProductId(productMaster.getId()).setProductSubSku(productSubSku).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).get();
            ordShoppingCart.setTotalProductNumber(ordShoppingCart.getTotalProductNumber() + proNum);
            ordShoppingCart.setTotalVol(productMaster.getTotalWeight() * ordShoppingCart.getTotalProductNumber());
            ordShoppingCart.setOperationTime(Utils.currentTimeSecond());
            ordShoppingCart.setOperationUserName(cliClienteleInfo.getLoginName());
            ordShoppingCart.setOperationUserId(cliClienteleInfo.getId());
        } else {
            ordShoppingCart.setTotalProductNumber(proNum);
            ordShoppingCart.setTotalVol(productMaster.getTotalWeight());
            getCartData(cliClienteleInfo, productSubSku, productMaster, ordShoppingCart);
        }
        ordShoppingCart.insertOrUpdate();
        return R.ok();
    }


    public void addShopCartData(Integer proNum, String productSubSku, ProProductMaster productMaster, List<OrdShoppingCart> cartVos, OrdShoppingCart ordShoppingCart) {
        ordShoppingCart.setFkProductId(productMaster.getId());
        ordShoppingCart.setProductName(productMaster.getProductName());
        ordShoppingCart.setPrductPicImageUrl(productMaster.getPrductPicImageUrl());

        ProProductStock productStock = ProProductStock.me().setProductSubSku(productSubSku).get();
        ordShoppingCart.setProductMallPrice(productStock.getProductMallPrice());
        ordShoppingCart.setProductMarketPrice(productStock.getProductMarketPrice());
        ordShoppingCart.setProductSubSku(productStock.getProductSubSku());

        if (productMaster.getIsSingleProduct() == 0) {
            optionSecJson(ordShoppingCart);
        }
        ordShoppingCart.setTotalProductNumber(proNum);
        ordShoppingCart.setTotalVol(productMaster.getTotalWeight());
        ordShoppingCart.setCurrency(productMaster.getCurrency());
        ordShoppingCart.setIsGenOrder(0);
        ordShoppingCart.setIsGenBalanceAccounts(0);
        ordShoppingCart.setIsGenReturnOrder(0);
        ordShoppingCart.setIsDel(0);
        //获取商品促销信息
        finActPromotion(productSubSku, productMaster, ordShoppingCart);
        // 将当前传来的商品添加到购物车列表
        cartVos.add(ordShoppingCart);
    }



    public void finActPromotion(String productSubSku, ProProductMaster productMaster, OrdShoppingCart ordShoppingCart) {
        Map<String, Object> param = new HashMap<>();
        String sql = " select pro.* from act_promotion_activity act,act_promotion_product pro where act.id = pro.fk_activity_id and act.activity_status=:status and fk_product_id=:productId ";
        param.put("status", 2);
        param.put("productId", productMaster.getId());
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        //获取到则插入购物车信息
        optionShoppingCartList(productSubSku, ordShoppingCart, list);
    }

    private void optionShoppingCartList(String productSubSku, OrdShoppingCart ordShoppingCart, List<Map<String, Object>> list) {
        if (!list.isEmpty()) {
            Map<String, Object> promotionMap = list.get(0);
            ActPromotionProduct actPromotionProduct = ActPromotionProduct.me().setId(promotionMap.get("id").toString()).get();
            ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setId(actPromotionProduct.getDiscountTypeId()).get();
            BigDecimal productPromotionPrice = new BigDecimal(0);
            if (!Helper.isEmpty(productSubSku)) {
                BigDecimal promotionAfterPrice = Helper.transformF2Y(ordShoppingCart.getProductMallPrice());
                BigDecimal discontValue = Helper.transformF2Y(actPromotionProduct.getDiscountValue());
                // 2020-10-30 去掉，商品主表库存字段去掉
                if ("02".equals(actDiscountUseType.getDiscountUseTypeCode())) {
                    productPromotionPrice = promotionAfterPrice.subtract(discontValue);
                } else {
                    discontValue = discontValue.multiply(new BigDecimal(10));
                    productPromotionPrice = promotionAfterPrice.multiply(discontValue);
                }
            }
            ordShoppingCart.setProductPromotionPrice(Helper.transformY2F(productPromotionPrice));
            ordShoppingCart.setFkActivityId(actPromotionProduct.getFkActivityId());
            ordShoppingCart.setActivityName(actPromotionProduct.getActivityName());
            ordShoppingCart.setDiscountTypeName(actPromotionProduct.getDiscountTypeName());
            ordShoppingCart.setDiscountValue(actPromotionProduct.getDiscountValue());
        }
    }

    public Result doUpdateOrdShopping(String id, int flag, CliClienteleInfo cliClienteleInfo, HttpServletRequest request) {
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        OrdShoppingCart ordShoppingCart = OrdShoppingCart.me().setId(id).get();
        if (flag == 1) {
            ordShoppingCart.setTotalProductNumber(ordShoppingCart.getTotalProductNumber() + 1);
            ProProductMaster proProductMaster = ProProductMaster.me().setId(ordShoppingCart.getFkProductId()).get();
            if (checkStock(ordShoppingCart, proProductMaster)){
                return Result.toast("商品库存不足！");
            }
            ordShoppingCart.setTotalVol(proProductMaster.getTotalVol() * ordShoppingCart.getTotalProductNumber());
            ordShoppingCart.setOperationTime(Utils.currentTimeSecond());
            ordShoppingCart.setOperationUserName(cliClienteleInfo.getLoginName());
            ordShoppingCart.setOperationUserId(cliClienteleInfo.getId());
            ordShoppingCart.update();
        } else {
            if (ordShoppingCart.getTotalProductNumber() - 1 > 0) {
                ordShoppingCart.setTotalProductNumber(ordShoppingCart.getTotalProductNumber() - 1);
                ProProductMaster proProductMaster = ProProductMaster.me().setId(ordShoppingCart.getFkProductId()).get();
                ordShoppingCart.setTotalVol(proProductMaster.getTotalVol() * ordShoppingCart.getTotalProductNumber());
                ordShoppingCart.setOperationTime(Utils.currentTimeSecond());
                ordShoppingCart.setOperationUserName(cliClienteleInfo.getLoginName());
                ordShoppingCart.setOperationUserId(cliClienteleInfo.getId());
                ordShoppingCart.update();
            }

        }
        addExchangeId(Lists.newArrayList(ordShoppingCart), exchangeId);
        optProductPrice(Lists.newArrayList(ordShoppingCart));
        return Result.build(() -> ordShoppingCart);
    }

    public Result doDelOrdShopping(List<String> ids) {
        ids.forEach(id -> OrdShoppingCart.me().setId(id).setIsDel(1).update());
        return Result.success();
    }


    /**
     * 根据sku获取购物车商品
     * @param productId
     * @param productSubSku
     * @param request
     * @return
     */
    public OrdShoppingCart getShoppingCartBySku(String productId, String productSubSku,HttpServletRequest request) {
        OrdShoppingCart ordShoppingCart;
        if(User.isAuth()){
            ordShoppingCart = OrdShoppingCart.me().setFkClienteleId(User.id()).setFkProductId(productId).setProductSubSku(productSubSku).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).get();
        }else{
            try {
                ordShoppingCart = touristService.getOrdShoppingCartBySku(productId,productSubSku,request);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        String currency = LFUCache.get(CacheConstant.SITE_SETTING_CURRENCY).toString();
        if (Helper.isNotEmpty(exchangeId)) {
            SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(exchangeId).get();
            currency = sysExchangeRate.getTcur();
        }
        this.handleCartPrice(ordShoppingCart,currency,exchangeId);
        return ordShoppingCart;
    }


    /**
     *金额处理
     */
    public void optProductPrice(List<OrdShoppingCart> ordShoppingCartsList) {
        String exchangeId = null;
        if (null != ordShoppingCartsList && ordShoppingCartsList.size() > 0) {
            Map<String, Object> map = ordShoppingCartsList.get(0).getExten();
            if (Objects.nonNull(map.get("exchangeId"))) {
                exchangeId = map.get("exchangeId").toString();
            }
        }
        String currency = LFUCache.get(CacheConstant.SITE_SETTING_CURRENCY).toString();
        if (Helper.isNotEmpty(exchangeId)) {
            SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(exchangeId).get();
            currency = sysExchangeRate.getTcur();
        }
        for (OrdShoppingCart ordShoppingCart : ordShoppingCartsList) {
           this.handleCartPrice(ordShoppingCart,currency,exchangeId);
        }
    }

    /**
     *  处理购物车价格
     * @param ordShoppingCart
     * @param currency
     * @param exchangeId
     */
    public void handleCartPrice(OrdShoppingCart ordShoppingCart,String currency,String exchangeId) {
        ordShoppingCart.addExten("seoUrl", Seo.getSeoUrlForPorduct(ordShoppingCart.getFkProductId()));
        // 判断商品是否下架或删除，则标志
        ProProductMaster productMaster = ProProductMaster.me().setId(ordShoppingCart.getFkProductId()).get();
        if(Objects.nonNull(productMaster)){
            if (productMaster.getIsShelve() == 0){
                ordShoppingCart.addExten("isEnable",BooleanEnum.FALSE.getValue());
            }
            ordShoppingCart.addExten("isSingleProduct",productMaster.getIsSingleProduct());
        }
        ordShoppingCart.addExten("currency", currency);
        if (ordShoppingCart.getProductMallPrice() != null) {
            if (Helper.isNotEmpty(exchangeId)) {
                BigDecimal productMallPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(ordShoppingCart.getProductMallPrice()));
                ordShoppingCart.addExten("productMallPrice", productMallPrice);
            } else {
                ordShoppingCart.addExten("productMallPrice", Helper.transformF2Y(ordShoppingCart.getProductMallPrice()));
            }
        } else {
            ordShoppingCart.addExten("productMallPrice", 0);
        }
        if (ordShoppingCart.getProductMarketPrice() != null) {
            if (Helper.isNotEmpty(exchangeId)) {
                BigDecimal productMarketPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(ordShoppingCart.getProductMarketPrice()));
                ordShoppingCart.addExten("productMarketPrice", productMarketPrice);
            } else {
                ordShoppingCart.addExten("productMarketPrice", Helper.transformF2Y(ordShoppingCart.getProductMarketPrice()));
            }
        } else {
            ordShoppingCart.addExten("productMarketPrice", 0);
        }
        if (ordShoppingCart.getProductPromotionPrice() != null) {
            if (Helper.isNotEmpty(exchangeId)) {
                BigDecimal productPromotionPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(ordShoppingCart.getProductPromotionPrice()));
                ordShoppingCart.addExten("productPromotionPrice", productPromotionPrice);
            } else {
                ordShoppingCart.addExten("productPromotionPrice", Helper.transformF2Y(ordShoppingCart.getProductPromotionPrice()));
            }
        } else {
            ordShoppingCart.addExten("productPromotionPrice", 0);
        }
        if (ordShoppingCart.getDiscountValue() != null) {
            ordShoppingCart.addExten("discountValue", Helper.transformF2Y(ordShoppingCart.getDiscountValue()));
        } else {
            ordShoppingCart.addExten("discountValue", 0);
        }
        if (StringUtils.isNotBlank(ordShoppingCart.getFkActivityId())) {
            BigDecimal totalAmount = new BigDecimal(ordShoppingCart.getExten().get("productMallPrice").toString());
            ordShoppingCart.addExten("totalAmount", totalAmount.multiply(new BigDecimal(ordShoppingCart.getTotalProductNumber().toString())));
            BigDecimal discountAmount = new BigDecimal(ordShoppingCart.getExten().get("productPromotionPrice").toString()).subtract(totalAmount);
            BigDecimal discountCount = new BigDecimal(ordShoppingCart.getExten().get("productPromotionPrice").toString()).multiply(new BigDecimal(ordShoppingCart.getTotalProductNumber()));
            ordShoppingCart.addExten("discountCount", discountCount);
            ordShoppingCart.addExten("discountAmount", discountAmount.multiply(new BigDecimal(ordShoppingCart.getTotalProductNumber().toString())));
        } else {
            BigDecimal totalAmount = new BigDecimal(ordShoppingCart.getExten().get("productMallPrice").toString());
            ordShoppingCart.addExten("totalAmount", totalAmount.multiply(new BigDecimal(ordShoppingCart.getTotalProductNumber().toString())));
            ordShoppingCart.addExten("discountAmount", 0);
        }
    }

    /**
     *根据选中的商品生成临时订单
     */
    public List<Map<String, Object>> productList() {
        List<Map<String, Object>> list = new ArrayList<>();
        if (User.isAuth()) {
            String sql = "select * from ord_shopping_cart where 1=1 and selected = 1 and fk_clientele_id = :userId and is_del = 0 and is_gen_order = 0 order by add_time desc";
            Map<String, Object> param = new HashMap<>(1);
            param.put("userId", User.id());
            list = baseDao.queryForList(sql, param);
        }
        return list;
    }

    public List<String> getCartIds(HttpServletRequest request) throws UnsupportedEncodingException {
        List<OrdShoppingCart> cartList;
        if (User.isAuth()) {
            cartList = OrdShoppingCart.me().setFkClienteleId(User.id()).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).find();
        } else {
            cartList = touristService.getCartInCookie(request);
        }
        List<String> carts  = cartList.stream().map(OrdShoppingCart::getId).collect(Collectors.toList());
        return carts;
    }

    /**
     * 购物车信息处理
     *
     * @param list
     * @param map
     * @param ordBalanceTheBooksDto
     */
    public void optOrdShopping(List<Map<String, Object>> list, Map<String, Object> map, OrdBalanceTheBooksDto ordBalanceTheBooksDto, String exchangeId,OrdOrderAddress shippingAddress) {
        if (!list.isEmpty()) {
            //计算购物车结算价格、优惠价格
            BigDecimal discountValue = new BigDecimal(0);
            BigDecimal totalCountValue = new BigDecimal(0);
            BigDecimal totalCountVol = new BigDecimal(0);
            BigDecimal totalWeight = new BigDecimal(0);

            map.put("fkReduceActivityId", "");
            map.put("fkCouponActivityId", "");
            for (Map<String, Object> maps : list) {
                BigDecimal mallPrice;
                BigDecimal marketPrice;
                BigDecimal promotionPrice;
                int number = Integer.parseInt(maps.get("total_product_number").toString());
                //金额处理
                if (!Objects.isNull(maps.get("product_mall_price"))) {
                    mallPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(maps.get("product_mall_price")));
                    maps.put("product_mall_price", mallPrice);
                } else {
                    mallPrice = Helper.transformF2Y(0);
                    maps.put("product_mall_price", mallPrice);
                }
                if (!Objects.isNull(maps.get("product_market_price"))) {
                    marketPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(maps.get("product_market_price")));
                    maps.put("product_market_price", marketPrice);
                } else {
                    marketPrice = Helper.transformF2Y(0);
                    maps.put("product_market_price", marketPrice);
                }
                if (!Objects.isNull(maps.get("product_promotion_price"))) {
                    promotionPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(maps.get("product_promotion_price")));
                    maps.put("product_promotion_price", promotionPrice);
                } else {
                    promotionPrice = Helper.transformF2Y(0);
                    maps.put("product_promotion_price", promotionPrice);
                }
                if (!Objects.isNull(maps.get("discount_value"))) {
                    maps.put("discount_value", sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(maps.get("discount_value"))));
                }
                totalCountValue = totalCountValue.add(mallPrice.multiply(new BigDecimal(number)));
                if (!Objects.isNull(maps.get("fk_activity_id"))) {
                    BigDecimal dis = mallPrice.subtract(promotionPrice).multiply(new BigDecimal(number));
                    discountValue = discountValue.add(dis);
                }
                ProProductMaster proProductMaster = ProProductMaster.me().setId(maps.get("fk_product_id").toString()).get();
                if (Objects.nonNull(proProductMaster)) {
                    if (proProductMaster.getIsTransFree() == 0) {
                        totalCountVol = totalCountVol.add(Helper.transformF2Y(maps.get("net_weight")));
                    }
                }
                totalWeight = totalWeight.add(Helper.transformF2Y(maps.get("net_weight")));
                //商品集合
                map.put("productList", list);
                //商品总价格
                map.put("productTotalPrice", totalCountValue);
                //优惠总价
                map.put("discountValue", discountValue);
                totalCountValue = totalCountValue.subtract(discountValue);
                map.put("productDiscountPrice", totalCountValue);
                map.put("paymentTotalPrice", totalCountValue);
                //商品总重量
                map.put("totalWeight", totalWeight);
            }
            map.put("productNum",list.size());
            ordBalanceTheBooksDto.setTotalCountValue(totalCountValue);

            //如果优惠编号为空查询是否有满减活动
            if (StringUtils.isEmpty(ordBalanceTheBooksDto.getCouponCode())) {
               // optReduceActivity(map, ordBalanceTheBooksDto, exchangeId);
            } else {
                boolean flag = optActivity(ordBalanceTheBooksDto.getCouponCode(), map, ordBalanceTheBooksDto, exchangeId,list);
                if(!flag){
                    optReduceActivity(map, ordBalanceTheBooksDto, exchangeId);
                }
            }
            if (User.isAuth()) {
                //查询封装当前用户收货信息
                CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
                //获取优惠卷数量
                List<Map<String, Object>> mapList = getCouponRecord(cliClienteleInfo, exchangeId);
                map.put("counponRecordSize", mapList.size());
            }
            //根据收货地址计算商品重量计算物流运费
            if (!Objects.isNull(shippingAddress) && !Helper.isEmpty(shippingAddress.getId())) {
                map.put("cliClienteleReceiverAddress", shippingAddress);
                getAddressOrCountry(map, ordBalanceTheBooksDto.getTotalCountValue(), totalCountVol, shippingAddress, ordBalanceTheBooksDto, exchangeId);
            }
            if(Objects.nonNull(map.get("paymentTotalPrice"))){
                BigDecimal paymentTotalPrice = new BigDecimal(map.get("paymentTotalPrice").toString());
                if(paymentTotalPrice.intValue()<0){
                    map.put("paymentTotalPrice",0);
                }
            }
        }

    }

    public void getAddressOrCountry(Map<String, Object> map, BigDecimal totalCountValue, BigDecimal totalCountVol,
                                    OrdOrderAddress shippingAddress, OrdBalanceTheBooksDto
                                             ordBalanceTheBooksDto, String exchangeId) {
        SysCountry sysCountry = SysCountry.me().setAlpha3(shippingAddress.getCountry()).get();
        map.put("sysCountry", sysCountry);
        map.put("freeSchemeTotalPrice", 0);
        List<OrdFreeSchemeCountry> countryList;
        String provinceCityId;
        //获取省/洲编号
        provinceCityId = getProvinceId(shippingAddress);
        //为空直接根据国家获取方案
        if (!Helper.isEmpty(provinceCityId)) {
            List<OrdFreeSchemeCity> ordFreeSchemeCityList = OrdFreeSchemeCity.me().setFkProvinceCityId(provinceCityId).find();
            if (!ordFreeSchemeCityList.isEmpty()) {
                countryList = getOrdFreeSchemeCountryList(ordFreeSchemeCityList);
            } else {
                countryList = OrdFreeSchemeCountry.me().setCountryId(sysCountry.getId()).find();
            }
        } else {
            countryList = OrdFreeSchemeCountry.me().setCountryId(sysCountry.getId()).find();
        }
        //若根据国家获取方案为空则跳过
        if (!countryList.isEmpty()) {
            List<OrdFreeScheme> ordFreeSchemeList = getOrdFreeSchemeList(countryList);
            if (!ordFreeSchemeList.isEmpty()) {
                  freeScheme(map, ordFreeSchemeList, totalCountVol, totalCountValue, ordBalanceTheBooksDto, exchangeId);
            }
        }
    }

    /**
     * 运费计算
     *
     * @param map
     * @param ordFreeSchemeList
     * @param totalCountVol
     * @param totalCountValue
     * @param ordBalanceTheBooksDto
     */
    private void freeScheme(Map<String, Object> map,  List<OrdFreeScheme> ordFreeSchemeList, BigDecimal totalCountVol, BigDecimal totalCountValue, OrdBalanceTheBooksDto ordBalanceTheBooksDto, String exchangeId) {
        map.put("fkFreeSchemeId", ordFreeSchemeList.get(0).getId());
        map.put("freeSchemeName", ordFreeSchemeList.get(0).getSchemeOutsideName());
        //方案集合
        List<Map<String, Object>> freeList = new ArrayList<>();
        for (OrdFreeScheme ordFreeScheme : ordFreeSchemeList) {
            List<OrdFreeSchemeDetails> details = OrdFreeSchemeDetails.me().setFkOrdFreeSchemeId(ordFreeScheme.getId()).find();
            //判断该方案是否是固定运费方案
            if (!details.isEmpty()) {
                if (Helper.isEmpty(ordBalanceTheBooksDto.getFkFreeSchemeId())) {
                    ordBalanceTheBooksDto.setFkFreeSchemeId(details.get(0).getId());
                }
                for (OrdFreeSchemeDetails freeSchemeDetails : details) {
                    Map<String, Object> maps = new HashMap<>();
                    if (freeSchemeDetails.getIsFixedFree() == 1) {
                        BigDecimal fixedFreeValue = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(freeSchemeDetails.getFixedFreeValue()));
                        if (freeSchemeDetails.getId().equals(ordBalanceTheBooksDto.getFkFreeSchemeId())) {
                            map.put("freeSchemeTotalPrice", fixedFreeValue);
                            totalCountValue = totalCountValue.add(fixedFreeValue);
                            map.put("paymentTotalPrice", format(totalCountValue));
                            map.put("fkFreeSchemeId", ordFreeScheme.getId());
                            map.put("freeSchemeName", ordFreeScheme.getSchemeOutsideName());
                            optionFreeMaps(freeSchemeDetails.getId(), fixedFreeValue, freeSchemeDetails.getSchemeInsideName(), maps, 1);
                        } else {
                            optionFreeMaps(freeSchemeDetails.getId(), fixedFreeValue, freeSchemeDetails.getSchemeInsideName(), maps, 0);
                        }
                    } else {
                        //获取首重、续重运费
                        BigDecimal ykgFree = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(freeSchemeDetails.getYkgFree()));
                        BigDecimal kgFree = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(freeSchemeDetails.getKgFree()));
                        //获取首重值、续重值
                        BigDecimal ykgValue = new BigDecimal(freeSchemeDetails.getYkgValue());
                        BigDecimal kgValue = new BigDecimal(freeSchemeDetails.getKgValue());
                        if ("kg".equals(freeSchemeDetails.getYkgUnit())) {
                            //换算kg
                            totalCountVol = totalCountVol.divide(new BigDecimal(1000));
                        } else {
                            totalCountVol = totalCountVol.divide(new BigDecimal(1000));
                            ykgValue = ykgValue.divide(new BigDecimal(1000));
                            kgValue = kgValue.divide(new BigDecimal(1000));
                        }
                        //如果首重值小于商品总重量则取首重运费
                        if (totalCountVol.compareTo(ykgValue) < 1) {
                            freeCalculation(map, ordFreeScheme, totalCountValue, ordBalanceTheBooksDto, freeSchemeDetails, maps, ykgFree);
                        } else {
                            ykgValue = totalCountVol.subtract(ykgValue);
                            kgValue = ykgValue.divide(kgValue);
                            //向下取整加一
                            Long kgValues = kgValue.setScale(0, BigDecimal.ROUND_DOWN).longValue() + 1;
                            //首重费用+取整后的续重数
                            ykgFree = ykgFree.add(new BigDecimal(kgValues));
                            //续重费用*换算后的续重数 = 总价格
                            kgFree = kgFree.multiply(ykgFree);
                            freeCalculation(map, ordFreeScheme, totalCountValue, ordBalanceTheBooksDto, freeSchemeDetails, maps, kgFree);
                        }
                    }
                    freeList.add(maps);
                }
            }
        }
        map.put("freeList", freeList);
    }

    private void freeCalculation(Map<String, Object> map, OrdFreeScheme ordFreeScheme, BigDecimal totalCountValue, OrdBalanceTheBooksDto ordBalanceTheBooksDto, OrdFreeSchemeDetails freeSchemeDetails, Map<String, Object> maps, BigDecimal kgFree) {
        if (freeSchemeDetails.getId().equals(ordBalanceTheBooksDto.getFkFreeSchemeId())) {
            map.put("freeSchemeTotalPrice", format(kgFree));
            map.put("paymentTotalPrice", format(totalCountValue.add(kgFree)));
            map.put("fkFreeSchemeId", ordFreeScheme.getId());
            map.put("freeSchemeName", ordFreeScheme.getSchemeOutsideName());
            optionFreeMaps(freeSchemeDetails.getId(), kgFree, freeSchemeDetails.getSchemeInsideName(), maps, 1);
        } else {
            optionFreeMaps(freeSchemeDetails.getId(), kgFree, freeSchemeDetails.getSchemeInsideName(), maps, 0);
        }
    }

    private void optionFreeMaps(String id, BigDecimal freeValue, String schemeOutsideName, Map<String, Object> maps, int flag) {
        maps.put("id", id);
        maps.put("schemeOutsideName", schemeOutsideName);
        maps.put("freeValue", format(freeValue));
        maps.put("isCheck", flag);
    }

    //计算优惠卷
    public boolean optActivity(String code, Map<String, Object> map, OrdBalanceTheBooksDto ordBalanceTheBooksDto, String exchangeId,List<Map<String,Object>>cartList) {
        if(User.isAuth()) {
            //根据code查询优惠卷信息
            ActCouponActivity actCouponActivity = ActCouponActivity.me().setCouponCode(code).get();

            map.put("productDiscountPrice", ordBalanceTheBooksDto.getTotalCountValue());
            map.put("paymentTotalPrice", ordBalanceTheBooksDto.getTotalCountValue());

            //优惠卷不为空处理优惠价格
            if (!Objects.isNull(actCouponActivity)) {
                if (actCouponActivity.getActivityStatus().equals("02")) {
                    String activityTypeId = checkActivity(cartList,map,actCouponActivity,ordBalanceTheBooksDto.getTotalCountValue());
                    if(Helper.isNotEmpty(activityTypeId)) {
                        ActConditionType actConditionTypes = ActConditionType.me().setId(activityTypeId).get();
                        if (!Objects.isNull(actConditionTypes)) {
                            BigDecimal discountValue = Helper.transformF2Y(actConditionTypes.getDiscountValue());
                            ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setId(actConditionTypes.getDiscountTypeId()).get();
                            BigDecimal couponDiscountValue = Helper.transformF2Y(actConditionTypes.getDiscountValue());
                            if (actDiscountUseType.getDiscountUseTypeCode().equals("01")) {
                                discountValue = discountValue.divide(new BigDecimal(10));
                                discountValue = ordBalanceTheBooksDto.getTotalCountValue().subtract(discountValue.multiply(ordBalanceTheBooksDto.getTotalCountValue()));
                                ordBalanceTheBooksDto.setTotalCountValue(ordBalanceTheBooksDto.getTotalCountValue().subtract(discountValue));
                            } else {
                                couponDiscountValue = sysExchangeRateWebService.payCalculation(exchangeId, couponDiscountValue);
                                ordBalanceTheBooksDto.setTotalCountValue(ordBalanceTheBooksDto.getTotalCountValue().subtract(couponDiscountValue));
                            }
                            map.put("fkCouponActivityId", actConditionTypes.getFkActivityId());
                            map.put("couponActivityName", actConditionTypes.getActivityName());
                            map.put("couponCode", actCouponActivity.getCouponCode());
                            map.put("couponDiscountTypeName", actDiscountUseType.getDiscountUseTypeName());
                            map.put("couponDiscountValue", couponDiscountValue);
                            map.put("productDiscountPrice", format(ordBalanceTheBooksDto.getTotalCountValue()));
                            map.put("paymentTotalPrice", format(ordBalanceTheBooksDto.getTotalCountValue()));
                            return true;
                        }
                    }
                    map.put("errMsgCode","3");
                    return false;
                }
            }
            map.put("errMsgCode","4");
            return false;
        }else{
            map.put("errMsgCode","5");
            return false;
        }
    }
    public String checkActivity(List<Map<String,Object>>cartList,Map<String, Object> map,ActCouponActivity actCouponActivity,BigDecimal totalCount){
        Long count = ActPromotionProduct.me().setFkActivityId(actCouponActivity.getId()).findCount();
        ActConditionTypeCommond actConditionTypeCommond = new ActConditionTypeCommond();
        actConditionTypeCommond.setActivityId(actCouponActivity.getId());
        actConditionTypeCommond.setOrderBy(" discount_value desc ");
        if(count > 0){
            //如果是指定商品优惠卷
            if(!cartList.isEmpty()) {
                List<String> productIds = new ArrayList<>();
                BigDecimal totalCountValue = new BigDecimal(0);
                for (Map<String, Object> maps : cartList) {
                    Long productCount =  ActPromotionProduct.me().setFkProductId(maps.get("fk_product_id").toString()).findCount();
                    if(productCount>0){
                        productIds.add(maps.get("fk_product_id").toString());
                        BigDecimal mallPrice = new BigDecimal(0);
                        BigDecimal promotionPrice = new BigDecimal(0);
                        int number = Integer.parseInt(maps.get("total_product_number").toString());
                        //金额处理
                        if (!Objects.isNull(maps.get("product_mall_price"))) {
                            mallPrice =  Helper.transformF2Y(maps.get("product_mall_price"));
                            maps.put("product_mall_price", mallPrice);
                        }
                        if (!Objects.isNull(maps.get("product_promotion_price"))) {
                            promotionPrice =Helper.transformF2Y(maps.get("product_promotion_price"));
                            maps.put("product_promotion_price", promotionPrice);
                        }
                        if (!Objects.isNull(maps.get("fk_activity_id"))) {
                            totalCountValue = totalCountValue.add(promotionPrice.multiply(new BigDecimal(number)));
                        }else{
                            totalCountValue = totalCountValue.add(mallPrice.multiply(new BigDecimal(number)));
                        }
                    }
                }
                if(productIds.isEmpty()){
                    //商品不在适用该优惠卷
                    map.put("errMsgCode","1");
                    return null;
                }
                return gettypeId(map, actConditionTypeCommond, totalCountValue);
            }
            //商品信息获取失败!
            map.put("errMsgCode","2");
            return null;
        }else{
            return gettypeId(map, actConditionTypeCommond, totalCount);
        }
    }

    private String gettypeId(Map<String, Object> map, ActConditionTypeCommond actConditionTypeCommond, BigDecimal totalCountValue) {
        actConditionTypeCommond.setTypeCondition(Helper.transformY2F(totalCountValue));
        List<ActConditionType> actConditionTypeList = ActConditionType.me().find(actConditionTypeCommond);
        if (actConditionTypeList.isEmpty()) {
            //金额不达标
            map.put("errMsgCode", "3");
            return null;
        }
        return actConditionTypeList.get(0).getId();
    }

    // 计算满减活动
    public void optReduceActivity(Map<String, Object> map, OrdBalanceTheBooksDto ordBalanceTheBooksDto, String exchangeId) {
        //查询进行的满减活动
        ActReduceActivity actReduceActivity = ActReduceActivity.me().setActivityStatus("2").get();
        BigDecimal count = ordBalanceTheBooksDto.getTotalCountValue();
        sysExchangeRateWebService.CurrencyToDollar(exchangeId, count);
        if (!Objects.isNull(actReduceActivity)) {
            ActConditionTypeCommond actConditionTypeCommond = new ActConditionTypeCommond();
            actConditionTypeCommond.setActivityId(actReduceActivity.getId());
            actConditionTypeCommond.setTypeCondition(Helper.transformY2F(count));
            actConditionTypeCommond.setOrderBy(" type_condition desc ");
            List<ActConditionType> actConditionType = ActConditionType.me().find(actConditionTypeCommond);
            if (!actConditionType.isEmpty()) {
                //获取满减最大值
                ActConditionType type = actConditionType.get(0);
                //获取满减条件
                BigDecimal discountValue = Helper.transformF2Y(type.getDiscountValue());
                BigDecimal reduceDiscountValue = Helper.transformF2Y(type.getDiscountValue());
                //查询折扣还是抵扣
                ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setId(type.getDiscountTypeId()).get();
                if (actDiscountUseType.getDiscountUseTypeCode().equals("01")) {
                    discountValue = discountValue.divide(new BigDecimal(10));
                    ordBalanceTheBooksDto.setTotalCountValue(discountValue.multiply(ordBalanceTheBooksDto.getTotalCountValue()));
                } else {
                    reduceDiscountValue = sysExchangeRateWebService.payCalculation(exchangeId, reduceDiscountValue);
                    ordBalanceTheBooksDto.setTotalCountValue(ordBalanceTheBooksDto.getTotalCountValue().subtract(sysExchangeRateWebService.payCalculation(exchangeId, discountValue)));
                }
                map.put("fkReduceActivityId", type.getFkActivityId());
                map.put("reduceActivityName", type.getActivityName());
                map.put("reduceDiscountTypeName", actDiscountUseType.getDiscountUseTypeName());
                map.put("reduceDiscountValue", reduceDiscountValue);
                map.put("typeCondition", sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(type.getTypeCondition())));
            }
        }
        map.put("productDiscountPrice", format(ordBalanceTheBooksDto.getTotalCountValue()));
        map.put("paymentTotalPrice", format(ordBalanceTheBooksDto.getTotalCountValue()));
    }

    public OrdShoppingCart doAddOrdShoppingCart(ProProductMaster productMaster, Integer proNum, CliClienteleInfo cliClienteleInfo,String productSubSku) {
        // 取消购物车其他选择
        OrdShoppingCart.me().setSelected(BooleanEnum.FALSE.getValue()).batchUpdate(OrdShoppingCart.me().setFkClienteleId(User.id()));
        //获取用户购物车信息
        OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
        ordShoppingCart.setTotalProductNumber(proNum);
        ordShoppingCart.setSelected(BooleanEnum.TRUE.getValue());
        ordShoppingCart.setTotalVol(productMaster.getTotalWeight());
        getCartData(cliClienteleInfo, productSubSku, productMaster, ordShoppingCart);
        ordShoppingCart.insert();
        return ordShoppingCart;
    }


    private void getCartData(CliClienteleInfo cliClienteleInfo, String productSubSku, ProProductMaster productMaster, OrdShoppingCart ordShoppingCart) {
        ordShoppingCart.setAddUserId(cliClienteleInfo.getId());
        ordShoppingCart.setFkClienteleId(cliClienteleInfo.getId());
        ordShoppingCart.setClientName(cliClienteleInfo.getClientName());
        ordShoppingCart.setFkProductId(productMaster.getId());
        ordShoppingCart.setProductName(productMaster.getProductName());
        ordShoppingCart.setPrductPicImageUrl(productMaster.getPrductPicImageUrl());

        ProProductStock productStock = ProProductStock.me().setProductSubSku(productSubSku).setIsEnable(BooleanEnum.TRUE.getValue()).get();
        ordShoppingCart.setProductMallPrice(productStock.getProductMallPrice());
        ordShoppingCart.setProductMarketPrice(productStock.getProductMarketPrice());
        ordShoppingCart.setProductSubSku(productStock.getProductSubSku());

        // 如果是多属性，添加商品属性
        if (productMaster.getIsSingleProduct() == 0) {
            optionSecJson(ordShoppingCart);
        }

        ordShoppingCart.setCurrency(productMaster.getCurrency());
        ordShoppingCart.setIsGenOrder(0);
        ordShoppingCart.setIsGenBalanceAccounts(0);
        ordShoppingCart.setIsGenReturnOrder(0);
        ordShoppingCart.setIsDel(0);
        //获取商品促销信息
        finActPromotion(productSubSku, productMaster, ordShoppingCart);

    }
    public void optionSecJson(OrdShoppingCart ordShoppingCart) {
        ProProductStock productStock = ProProductStock.me().setProductSubSku(ordShoppingCart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
        List<ProProductSecInfo> secInfoList = ProProductSecInfo.me().setFkStockId(productStock.getId()).find();
        StringBuilder json = new StringBuilder();
        secInfoList.forEach(sec -> {
            ProProductSpec partenSpec = ProProductSpec.me().setId(sec.getFkParentSpecId()).get();
            ProProductSpec spec = ProProductSpec.me().setId(sec.getFkSpecId()).get();
            if(json.length() > 0){
                json.append("，");
            }
            json.append(partenSpec.getSpecName() + "：" + spec.getSpecName() + " ");
        });
        ordShoppingCart.setProductSpec(json.toString());
    }

    /**
     * 新增商品评论
     *
     * @param proComment 评论对象
     * @return
     */
    public Result productComment(ProCommentDto proComment) {
        if(!Objects.isNull(proComment.getSlideShows()) && !proComment.getSlideShows().isEmpty()) {
            proComment.setProductProImage(String.join(",", proComment.getSlideShows()));
        }
        ProProductMaster master = ProProductMaster.me().setId(proComment.getFkProductId()).get();

        proComment.setFkClienteleId(User.id());
        proComment.setProductName(master.getProductName());
        proComment.setOperationTime(Utils.currentTimeSecond());

        String fractionSwitch = Options.get("comment_fraction_switch");
        if ("1".equals(fractionSwitch)) {
            String fractionFilter = Options.get("comment_fraction_filter");
            if (proComment.getRangeNum() != null && Integer.parseInt(fractionFilter) <= proComment.getRangeNum()) {
                proComment.setIsAuditPass(1);
                proComment.setIsDisplay(1);
                // 如果商品是上架状态，需要发布一下，首页要看评论数量
                ProProductMaster product = ProProductMaster.me().setId(proComment.getFkProductId()).get();
                if (product.getIsShelve() == 1) {
                    EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, proComment.getFkProductId()));
                }
            } else {
                proComment.setIsAuditPass(0);
                proComment.setIsDisplay(0);

            }
        } else {
            proComment.setIsAuditPass(0);
            proComment.setIsDisplay(0);
        }
        proComment.insert();

        return Result.build(() -> true);
    }


    /**
     * 新增商品提问
     * @param dto 提问对象
     * @return
     */
    public Result saveQuestion(ProProductQa dto) {
        dto.setFkClienteleId(User.id());
        // 默认不显示出来
        dto.setIsDisplay(0);
        dto.setProblemTime(Utils.currentTimeSecond());
        dto.insert();
        return Result.build(() -> true);
    }

    /**
     * 问答分页查询
     *
     * @return
     */
    public Page<ProProductQa> getProductQuestion(ProProductQaCommond commond) {
        commond.setOrderBy("-" + ProProductQa.F_PROBLEM_TIME);
        commond.setIsDisplay(1);
        List<ProProductQa> list = ProProductQa.me().find(commond);
        list.forEach(p -> {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(p.getFkClienteleId()).get();
            p.addExten("userName", cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName());
        });

        return new Page<>(list, commond);
    }



    public List<Map<String, Object>> getCouponRecord(CliClienteleInfo cliClienteleInfo, String exchangeId) {
        String sql = "select * from act_get_coupon_record where fk_clientele_id =:clienteleId and is_use = 0 and start_time < :startTime and end_time >= :endTime";
        Map<String, Object> param = new HashMap<>();
        param.put("clienteleId", cliClienteleInfo.getId());
        param.put("startTime", Utils.currentTimeSecond());
        param.put("endTime", Utils.currentTimeSecond());
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        if (!list.isEmpty()) {
            list.forEach(map -> {
                if (!Objects.isNull(map.get("coupon_discount_value"))) {
                    map.put("coupon_discount_value", sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(map.get("coupon_discount_value"))));
                } else {
                    map.put("coupon_discount_value", 0);
                }
                if (!Objects.isNull(map.get("fk_condition_type_id"))) {
                    map.put("type_condition", 0);
                    ActConditionType actConditionType = ActConditionType.me().setId(map.get("fk_condition_type_id").toString()).get();
                    if (!Objects.isNull(actConditionType)) {
                        if (actConditionType.getTypeCondition() != null) {
                            map.put("type_condition", sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(actConditionType.getTypeCondition())));
                        } else {
                            map.put("type_condition", 0);
                        }
                    }
                }
            });
        }
        return list;
    }

    public Page<ProComment> findCommentRecording(ProCommentCommond commond) {
        commond.setOrderBy("-" + ProComment.F_ADD_TIME);
        commond.setIsAuditPass(1);
        List<ProComment> comments = ProComment.me().find(commond);

        comments.forEach(p -> {
            String[] split = p.getProductProImage().split(",");
            p.addExten("images", split);
            CliClienteleInfo client = CliClienteleInfo.me().setId(p.getFkClienteleId()).get();
            if (Objects.nonNull(client)) {
                p.addExten("clientImg", client.getIconImageUrl());
            }

        });

        return new Page<>(comments, commond);
    }


    /**
     * 保存用户操作记录和关键词搜索记录
     *
     * @param id    商品ID
     * @param keyId 关键词ID
     */
    public void addProductOperatingRecord(String id, String keyId) {
        String ip = Helper.getIpAddress(WebUtil.getHttpServletRequest());

        if (User.isAuth()) {
            String currentUserId = User.id();
            ProProductMaster product = ProProductMaster.me().setId(id).get();
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(currentUserId).get();
            String previousGoodsTrailSql = String.format("SELECT * FROM cli_clientele_goods_trail where fk_goods_id = '%s' ORDER BY add_time DESC LIMIT 1", product.getId());
            CliClienteleGoodsTrail previousGoodsTrail = baseDao.queryForBean(previousGoodsTrailSql, CliClienteleGoodsTrail.me());

            if (keyId != null) {
                SysSiteSearchRecord searchRecord = SysSiteSearchRecord.me().setId(keyId).get();
                SysSiteSearchRecordDetail.me()
                        .setFkSiteSearchRecordId(searchRecord.getId())
                        .setKeywords(searchRecord.getKeywords())
                        .setSearchTime(searchRecord.getSearchTime())
                        .setFkClienteleId(cliClienteleInfo.getId())
                        .setClientName(cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName())
                        .setProductName(product.getProductName())
                        .setIp(ip)
                        .setAddUserId(cliClienteleInfo.getId())
                        .insert();
            }


            //得到当前时间戳
            Long currentTime = Utils.currentTimeSecond();

            // 5分钟的时间戳
            Long fiveTime = 60L * 5;

            // 如果目前是访问的爱后一条记录则不新增只是修改时间 1364725
            if (Objects.nonNull(previousGoodsTrail)) {
                if (currentTime - previousGoodsTrail.getAddTime() < fiveTime) {
                    previousGoodsTrail.setAddTime(Utils.currentTimeSecond()).update();
                    return;
                }
            }
            CliClienteleGoodsTrail.me()
                    .setFkClienteleId(currentUserId)
                    .setClientName(cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName())
                    .setFkGoodsId(id)
                    .setGoodsName(product.getProductName())
                    .setDefaultImageUrl(product.getPrductPicImageUrl())
                    .setIp(ip)
                    .insert();

        }
    }

    public Double format(BigDecimal pic) {
        Double df1 = pic.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df1;
    }

    /**
     * 获取购物车商品Cookie
     *
     * @param request
     * @return
     */
    public Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cart_cookie = null;
        //获取购物车cookie
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    cart_cookie = cookie;
                }
            }
        }
        return cart_cookie;
    }

    /**
     * 无登录移除购物车商品
     *
     * @param ids
     * @return
     */
    public Result isAuthDelOrdShopping(List<String> ids, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        touristService.delCartCookies(ids, request, response);
        return Result.success();
    }

    public boolean checkStock(OrdShoppingCart ordShoppingCart, ProProductMaster proProductMaster) {
        ProProductStock proProductStock = ProProductStock.me().setProductSubSku(ordShoppingCart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
        if (proProductStock.getProductStockNumber() < ordShoppingCart.getTotalProductNumber()) {
            return true;
        }
        return false;
    }
    /**
     * 获取省编号
     */
    private String getProvinceId(OrdOrderAddress shippingAddress) {
        String proinveId = "";
        if (StringUtils.isNotBlank(shippingAddress.getProvince()) && StringUtils.isNotBlank(shippingAddress.getCity())) {
            String sql = "select * from sys_country_province_city where province = :province and (city = '' or city is null)";
            Map<String, Object> param = new HashMap<>();
            param.put("province", shippingAddress.getProvince());
            List<Map<String, Object>> cityList = baseDao.queryForList(sql, param);
            if (!cityList.isEmpty()) {
                proinveId = cityList.get(0).get("id").toString();
            }
        } else {
            SysCountryProvinceCity city;
            if (StringUtils.isNotBlank(shippingAddress.getProvince())) {
                city = SysCountryProvinceCity.me().setProvince(shippingAddress.getProvince()).get();
                if (!Objects.isNull(city)) {
                    proinveId = city.getId();
                }

            }
            if (StringUtils.isNotBlank(shippingAddress.getCity())) {
                city = SysCountryProvinceCity.me().setCity(shippingAddress.getCity()).get();
                if (!Objects.isNull(city)) {
                    proinveId = city.getId();
                }
            }
        }
        return proinveId;
    }

    public void addExchangeId(List<OrdShoppingCart> cartList, String exchangeId) {
        if (Helper.isNotEmpty(exchangeId)) {
            for (OrdShoppingCart ordShoppingCart : cartList) {
                ordShoppingCart.addExten("exchangeId", exchangeId);
            }
        }
    }

    public String doSaveOrdBalanceTheBooks(OrdBalanceTheBooksDto ordBalanceTheBooksDto, HttpServletRequest request, HttpServletResponse response) {
        paypalService.optionOrdBalanceTheBooks(ordBalanceTheBooksDto, request, response);
        return ordBalanceTheBooksDto.getId();
    }

    public void getOrdBalanceTheBooks(ModelMap map, String id){
        //获取结算单数据
        OrdBalanceTheBooks ordBalanceTheBooks = OrdBalanceTheBooks.me().setId(id).get();
        map.put("ordBalanceTheData", null);
        map.put("productList", null);
        if (!Objects.isNull(ordBalanceTheBooks)) {
            String catId = ordBalanceTheBooks.getShoppingCartList();
            List<String> catList = new ArrayList<>();
            if (catId.contains(",")) {
                String[] ids = catId.split(",");
                catList = Arrays.asList(ids);
            } else {
                catList.add(catId);
            }
            List<Map<String, Object>> productList = productList();
            //金额处理
            optionCatPrice(productList);
            optionOrdBalanceTheBooks(ordBalanceTheBooks);
            CliClienteleReceiverAddress cliClienteleReceiverAddress = new CliClienteleReceiverAddress();
            if (User.isAuth()) {
                cliClienteleReceiverAddress = CliClienteleReceiverAddress.me().setId(ordBalanceTheBooks.getFkClienteleReceiverAddressId()).get();
            } else {
                SysNotLoginPurchaseInfo sysNotLoginPurchaseInfo = SysNotLoginPurchaseInfo.me().setId(ordBalanceTheBooks.getFkClienteleId()).get();
                BeanUtils.copyProperties(sysNotLoginPurchaseInfo,cliClienteleReceiverAddress);
                cliClienteleReceiverAddress.setClientName(sysNotLoginPurchaseInfo.getNotLoginName());

            }
            OrdInvoiceAddress ordInvoiceAddress = OrdInvoiceAddress.me().setId(ordBalanceTheBooks.getFkOrdInvoiceAddressId()).get();
            map.put("ordInvoiceAddress", ordInvoiceAddress);
            map.put("ordBalanceTheData", ordBalanceTheBooks);
            map.put("productList", productList);
            map.put("productNum", productList.size());
            map.put("cliClienteleReceiverAddress", cliClienteleReceiverAddress);
        }
    }

    private void optionCatPrice(List<Map<String, Object>> list) {
        if (!list.isEmpty()) {
            for (Map<String, Object> maps : list) {
                if (!Objects.isNull(maps.get("product_mall_price"))) {
                    maps.put("product_mall_price", Helper.transformF2Y(maps.get("product_mall_price")));
                } else {
                    maps.put("product_mall_price", 0);
                }
                if (!Objects.isNull(maps.get("product_market_price"))) {
                    maps.put("product_market_price", Helper.transformF2Y(maps.get("product_market_price")));
                } else {
                    maps.put("product_market_price", 0);
                }
                if (!Objects.isNull(maps.get("product_promotion_price"))) {
                    maps.put("product_promotion_price", Helper.transformF2Y(maps.get("product_promotion_price")));
                } else {
                    maps.put("product_promotion_price", 0);
                }
                if (!Objects.isNull(maps.get("discount_value"))) {
                    maps.put("discount_value", Helper.transformF2Y(maps.get("discount_value")));
                }
            }
        }
    }

    private void optionOrdBalanceTheBooks(OrdBalanceTheBooks ordBalanceTheBooks) {
        //处理结算单金额数据
        if (ordBalanceTheBooks.getTaxRate() != null) {
            ordBalanceTheBooks.addExten("taxRate", Helper.transformF2Y(ordBalanceTheBooks.getTaxRate()));
        } else {
            ordBalanceTheBooks.addExten("taxRate", Helper.transformF2Y(0));
        }
        if (ordBalanceTheBooks.getProductDiscountPrice() != null) {
            ordBalanceTheBooks.addExten("productDiscountPrice", Helper.transformF2Y(ordBalanceTheBooks.getProductDiscountPrice()));
        } else {
            ordBalanceTheBooks.addExten("productDiscountPrice", Helper.transformF2Y(0));
        }
        if (ordBalanceTheBooks.getPaymentTotalPrice() != null) {
            ordBalanceTheBooks.addExten("paymentTotalPrice", Helper.transformF2Y(ordBalanceTheBooks.getPaymentTotalPrice()));
        } else {
            ordBalanceTheBooks.addExten("paymentTotalPrice", Helper.transformF2Y(0));
        }
        if (ordBalanceTheBooks.getFreeSchemeTotalPrice() != null) {
            ordBalanceTheBooks.addExten("freeSchemeTotalPrice", Helper.transformF2Y(ordBalanceTheBooks.getFreeSchemeTotalPrice()));
        } else {
            ordBalanceTheBooks.addExten("freeSchemeTotalPrice", Helper.transformF2Y(0));
        }
        if (ordBalanceTheBooks.getProductTotalPrice() != null) {
            ordBalanceTheBooks.addExten("productTotalPrice", Helper.transformF2Y(ordBalanceTheBooks.getProductTotalPrice()));
        } else {
            ordBalanceTheBooks.addExten("productTotalPrice", Helper.transformF2Y(0));
        }
        if (ordBalanceTheBooks.getCouponDiscountValue() != null) {
            ordBalanceTheBooks.addExten("couponDiscountValue", Helper.transformF2Y(ordBalanceTheBooks.getCouponDiscountValue()));
        } else {
            ordBalanceTheBooks.addExten("couponDiscountValue", 0);
        }
        if (ordBalanceTheBooks.getReduceDiscountValue() != null) {
            ordBalanceTheBooks.addExten("reduceDiscountValue", Helper.transformF2Y(ordBalanceTheBooks.getReduceDiscountValue()));
        } else {
            ordBalanceTheBooks.addExten("reduceDiscountValue", 0);
        }
    }

    public List<OrdFreeSchemeCountry> getOrdFreeSchemeCountryList(List<OrdFreeSchemeCity> ordFreeSchemeCityList){
        List<OrdFreeSchemeCountry> countryList = new ArrayList<>();
        ordFreeSchemeCityList.forEach(city->{
            OrdFreeSchemeCountry country = OrdFreeSchemeCountry.me().setId(city.getFkSchemeCountId()).get();
            countryList.add(country);
        });
        return countryList;
    }

    public List<OrdFreeScheme> getOrdFreeSchemeList(List<OrdFreeSchemeCountry> countryList){
        List<OrdFreeScheme> schemeList = new ArrayList<>();
        countryList.forEach(country->{
            OrdFreeScheme ordFreeScheme = OrdFreeScheme.me().setId(country.getFkSchemeId()).get();
            schemeList.add(ordFreeScheme);
        });
        return schemeList;
    }

    public void getSettlement(ModelMap map, String id,HttpServletRequest request) {
        //获取结算单数据
        OrdSettlementConfirm settlementConfirm = OrdSettlementConfirm.me().setId(id).get();

        if (!Objects.isNull(settlementConfirm)) {
            SettlementConfirmDto settlementConfirmDto = JSON.parseObject(settlementConfirm.getContent(), SettlementConfirmDto.class);

            CreateOrder createOrder = new CreateOrder();
            createOrder.setItems(new ArrayList<>());
            OrdBalanceTheBooksDto ordBalanceTheBooksDto = new OrdBalanceTheBooksDto();
            if(Objects.nonNull(settlementConfirmDto.getCarts())){
                createOrder.setItems(settlementConfirmDto.getCarts());
            }
            ordBalanceTheBooksDto.setFkFreeSchemeId(settlementConfirmDto.getFreeSchemes());
            List<Map<String,Object>> shoppingCart = geUserCartIds(createOrder);
            //金额处理
            OrdOrderAddress shippingAddress = new OrdOrderAddress();
            BeanUtils.copyProperties(settlementConfirmDto.getShippingAddress(),shippingAddress);
            optOrdShopping(shoppingCart, map, ordBalanceTheBooksDto, Helper.getExchangeId(request, CacheConstant.RATIO),shippingAddress);
            map.put("settlementConfirmData", settlementConfirm);
        }
    }
    /**
     * 查询购物车列表根据商户进行分组
     */
    public List<Map<String,Object>> geUserCartIds(CreateOrder createOrder) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("ids", createOrder.getItems());
        List<Map<String,Object>> userCartList = new ArrayList<>();
        if(User.isAuth()) {
            baseDao.queryForList("select * from ord_shopping_cart where id in (:ids)", paramMap);
        }
        return userCartList;

    }

    public BigDecimal getCouponPrices(String code){
        // TODO: 优惠卷
        BigDecimal couponPrice = new BigDecimal(0);
        List<OrdShoppingCart> carts =  OrdShoppingCart.me().setFkClienteleId(User.id()).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).find();
        if(!carts.isEmpty()) {
            optProductPrice(carts);
            //获取优惠卷信息
            ActCouponActivity activity = ActCouponActivity.me().setCouponCode(code).get();
            Long productCount = ActPromotionProduct.me().setFkActivityId(activity.getId()).findCount();
            for (OrdShoppingCart cart : carts) {
                if (productCount > 0) {
                    Long existence =  ActPromotionProduct.me().setFkActivityId(activity.getId()).setFkProductId(cart.getFkProductId()).findCount();
                    if(existence > 0){

                    }
                }
            }
        }
        return couponPrice;
    }

    public boolean checkCoupon(String code){
        //获取优惠卷信息
        ActCouponActivity activity = ActCouponActivity.me().setCouponCode(code).get();
        if(!Objects.isNull(activity)){
            //获取优惠商品,判断该优惠卷是否优惠指定商品
            Long productCount = ActPromotionProduct.me().setFkActivityId(activity.getId()).findCount();
            if(productCount>0){
               List<OrdShoppingCart> carts =  OrdShoppingCart.me().setFkClienteleId(User.id()).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).find();
               Long count = 0L;
               for(OrdShoppingCart cart : carts){
                   Long existence =  ActPromotionProduct.me().setFkActivityId(activity.getId()).setFkProductId(cart.getFkProductId()).findCount();
                   if(existence > 0){
                       count++;
                   }
               }
               if(count < 1){
                   return false;
               }
            }
            return true;
        }
        return false;
    }
}
