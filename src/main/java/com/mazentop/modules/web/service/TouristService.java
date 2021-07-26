package com.mazentop.modules.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.util.Helper;
import com.mztframework.commons.Lists;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.snowflake.IDSnowflake;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Service
public class TouristService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    /**
     * 获取购物车商品Cookie
     * @param request
     * @return
     */
    public Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cartCookie = null;
        //获取购物车cookie
        if (!Objects.isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    cartCookie = cookie;
                }
            }
        }
        return cartCookie;
    }

    /**
     * 制作cookie所需value
     *
     * @param cartVos 购物车列表
     * @return 解析json的购物车列表
     */
    public String makeCookieValue(List<OrdShoppingCart> cartVos) {
        List<Map<String, Object>> mapList = new ArrayList<>(1);
        //处理存储至cookie的
        cartVos.forEach(cart -> {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("id", cart.getId());
            objectMap.put("fkProductId", cart.getFkProductId());
            objectMap.put("productSubSku", cart.getProductSubSku());
            objectMap.put("fkActivityId", cart.getFkActivityId());
            objectMap.put("totalProductNumber", cart.getTotalProductNumber());
            mapList.add(objectMap);
        });
        return JSON.toJSONString(mapList);
    }

    private void updateCookie(HttpServletRequest request, HttpServletResponse response, List<OrdShoppingCart> cartVos) throws UnsupportedEncodingException {
        // 获取名为"cart"的cookie
        Cookie cookie = getCookie(request);
        // 为cookie设置value
        cookie.setValue(URLEncoder.encode(makeCookieValue(cartVos), "utf-8"));
        // 设置寿命
        cookie.setMaxAge(60 * 30);
        // 设置路径
        cookie.setPath("/");
        // 更新cookie
        response.addCookie(cookie);
    }

    public Long getCartNum(HttpServletRequest request)  {
        try {
            return Long.valueOf(getCartInCookie(request).size());
        }catch (Exception e){

        }
        return 0L;
    }

    /**
     * 获取cookie中的购物车列表
     *
     * @param request
     * @return 购物车列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    public List<OrdShoppingCart> getCartInCookie(HttpServletRequest request) throws UnsupportedEncodingException {
        // 定义空的购物车列表
        List<OrdShoppingCart> items = new ArrayList<>(1);
        String ordShoppingCartValue;
        // 购物cookie
        Cookie cookie;
        // 判断cookie是否为空
        if (!Objects.isNull(getCookie(request))) {
            cookie = getCookie(request);
            //从cookie获取购物车
            ordShoppingCartValue = URLDecoder.decode(cookie.getValue(), "utf-8");
            // 判断value是否为空或者""字符串
            if (ordShoppingCartValue != null && !"".equals(ordShoppingCartValue)) {
                List<OrdShoppingCart> mapList = JSONArray.parseArray(ordShoppingCartValue, OrdShoppingCart.class);
                mapList.forEach(map -> {
                    OrdShoppingCart ordShoppingCart = getOrdShoppingCart(map);
                    if(!Objects.isNull(ordShoppingCart)) {
                        ordShoppingCart.setTotalProductNumber(map.getTotalProductNumber());
                        // 将当前传来的商品添加到购物车列表
                        items.add(ordShoppingCart);
                    }
                });

            }
        }
        return items;
    }


    /**
     *  根据商品id、sku从Cookie获取购物车信息
     * @param productId
     * @param productSubSku
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public OrdShoppingCart getOrdShoppingCartBySku(String productId, String productSubSku,HttpServletRequest request)throws UnsupportedEncodingException {
        Cookie cookie;
        String ordShoppingCartValue;
        // 判断cookie是否为空
        if (!Objects.isNull(getCookie(request))) {
            cookie = getCookie(request);
            //从cookie获取购物车
            ordShoppingCartValue = URLDecoder.decode(cookie.getValue(), "utf-8");
            // 判断value是否为空或者""字符串
            if (ordShoppingCartValue != null && !"".equals(ordShoppingCartValue)) {
                List<OrdShoppingCart> mapList = JSONArray.parseArray(ordShoppingCartValue, OrdShoppingCart.class);
                for (OrdShoppingCart shoppingCart : mapList) {
                    if(shoppingCart.getFkProductId().equals(productId) && shoppingCart.getProductSubSku().equals(productSubSku)){
                        OrdShoppingCart ordShoppingCart = getOrdShoppingCart(shoppingCart);
                        ordShoppingCart.setTotalProductNumber(shoppingCart.getTotalProductNumber());
                        return ordShoppingCart;
                    }
                }
            }
        }
        return null;
    }

    public OrdShoppingCart getOrdShoppingCart(OrdShoppingCart shoppingCart) {
        OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
        ordShoppingCart.setId(shoppingCart.getId());

        ProProductMaster productMaster = ProProductMaster.me().setId(shoppingCart.getFkProductId()).get();
        if(!Objects.isNull(productMaster)) {
            ordShoppingCart.addExten("isSingleProduct", productMaster.getIsSingleProduct());
            ordShoppingCart.setFkProductId(productMaster.getId());
            ordShoppingCart.setProductName(productMaster.getProductName());
            ordShoppingCart.setPrductPicImageUrl(productMaster.getPrductPicImageUrl());

            ProProductStock productStock = ProProductStock.me().setProductSubSku(shoppingCart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
            ordShoppingCart.setProductMallPrice(productStock.getProductMallPrice());
            ordShoppingCart.setProductMarketPrice(productStock.getProductMarketPrice());
            ordShoppingCart.setProductSubSku(productStock.getProductSubSku());

            if (productMaster.getIsSingleProduct() == 0) {
                ordShoppingCartService.optionSecJson(ordShoppingCart);
            }
            ordShoppingCart.setTotalVol(productMaster.getTotalWeight() * shoppingCart.getTotalProductNumber());
            ordShoppingCart.setCurrency(productMaster.getCurrency());
            ordShoppingCart.setIsGenOrder(0);
            ordShoppingCart.setIsGenBalanceAccounts(0);
            ordShoppingCart.setIsGenReturnOrder(0);
            ordShoppingCart.setIsDel(0);
            //获取商品促销信息
            ordShoppingCartService.finActPromotion(ordShoppingCart.getProductSubSku(), productMaster, ordShoppingCart);
        }
        return ordShoppingCart;
    }

    public R doNotIsAuthAddOrdShopping(String id, Integer proNum, String productSubSku, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
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
        // 从cookie中获取购物车列表
        List<OrdShoppingCart> cartVos = getCartInCookie(request);
        // 如果购物车列表为空
        if (cartVos.isEmpty()) {
            OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
            ordShoppingCart.setId(IDSnowflake.id());
            ordShoppingCart.setAddTime(Utils.currentTimeSecond());
            ordShoppingCartService.addShopCartData(proNum, productSubSku, productMaster, cartVos, ordShoppingCart);
            // 当获取的购物车列表不为空时
        } else {
            boolean flag = false;
            for (OrdShoppingCart cart : cartVos) {
                if (cart.getFkProductId().equals(id)) {
                    if (Helper.isEmpty(productSubSku)) {
                        cart.setTotalProductNumber(cart.getTotalProductNumber() + proNum);
                        cart.setTotalVol(productMaster.getTotalWeight() * cart.getTotalProductNumber());
                        flag = true;
                    } else {
                        if (cart.getProductSubSku().equals(productSubSku)) {
                            cart.setTotalProductNumber(cart.getTotalProductNumber() + proNum);
                            cart.setTotalVol(productMaster.getTotalWeight() * cart.getTotalProductNumber());
                            flag = true;
                        }
                    }
                }
            }
            if (!flag) {
                OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
                ordShoppingCart.setId(IDSnowflake.id());
                ordShoppingCart.setAddTime(Utils.currentTimeSecond());
                ordShoppingCartService.addShopCartData(proNum, productSubSku, productMaster, cartVos, ordShoppingCart);
            }
        }
        if (cartVos.size() > 10) {
            return R.error("未登录状态只能添加10件商品");
        }
        checkCookies(response, request, cartVos);
        return R.ok();
    }

    public void checkCookies(HttpServletResponse response, HttpServletRequest request, List<OrdShoppingCart> cartVos) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie("cart", URLEncoder.encode(makeCookieValue(cartVos), "utf-8"));
        if( Objects.isNull(getCookie(request))) {
            // 制作购物车cookie数据
            //设置在该项目下都可以访问该cookie
            cookie.setPath("/");
            cookie.setMaxAge(60 * 30);
            //添加cookie
        } else {
            cookie = getCookie(request);
            //设置在该项目下都可以访问该cookie
            cookie.setPath("/");
            cookie.setMaxAge(60 * 30);
            cookie.setValue(URLEncoder.encode(makeCookieValue(cartVos)));
            //添加cookie
        }
        response.addCookie(cookie);
    }

    public Result isAuthUpdateOrdShopping(String id, int flag, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
        List<OrdShoppingCart> cartVos = getCartInCookie(request);
        for (OrdShoppingCart cart : cartVos) {
            if (cart.getId().equals(id)) {
                if (flag == 1) {
                    cart.setTotalProductNumber(cart.getTotalProductNumber() + 1);
                    ProProductMaster proProductMaster = ProProductMaster.me().setId(cart.getFkProductId()).get();
                    if (ordShoppingCartService.checkStock(cart, proProductMaster)){
                        return Result.toast("商品库存不足！");
                    }
                    cart.setTotalVol(proProductMaster.getTotalVol() * cart.getTotalProductNumber());
                    cart.setOperationTime(Utils.currentTimeSecond());
                } else {
                    if (cart.getTotalProductNumber() - 1 > 0) {
                        cart.setTotalProductNumber(cart.getTotalProductNumber() - 1);
                        ProProductMaster proProductMaster = ProProductMaster.me().setId(cart.getFkProductId()).get();
                        cart.setTotalVol(proProductMaster.getTotalVol() * cart.getTotalProductNumber());
                        cart.setOperationTime(Utils.currentTimeSecond());
                    }

                }
                ordShoppingCart = cart;
            }
        }
        updateCookie(request, response, cartVos);
        Lists.newArrayList(ordShoppingCart);
        ordShoppingCartService.optProductPrice(Lists.newArrayList(ordShoppingCart));
        OrdShoppingCart finalOrdShoppingCart = ordShoppingCart;
        return Result.build(() -> finalOrdShoppingCart);
    }

    public void delCartCookies(List<String> ids, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        List<OrdShoppingCart> cartVos = getCartInCookie(request);
        List<OrdShoppingCart> cartList = new ArrayList<>(1);
        if (!cartVos.isEmpty()) {
            cartVos.forEach(cart -> {
                if (!ids.contains(cart.getId())) {
                    cartList.add(cart);
                }
            });
        }
        // 获取名为"cart"的cookie
        updateCookie(request, response, cartList);
    }

    public void deleteCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        // 获取名为"cart"的cookie
        Cookie cookie = getCookie(request);
        if(!Objects.isNull(cookie)) {
            // 设置寿命为0秒
            cookie.setMaxAge(0);
            // 设置路径
            cookie.setPath("/");
            // 设置cookie的value为null
            cookie.setValue(null);
            // 更新cookie
            response.addCookie(cookie);
        }
    }

    /**
     * 获取cookie中的购物车列表
     *
     * @param request
     * @return 购物车
     */
    public OrdShoppingCart getCartCookieData(HttpServletRequest request, String id) throws UnsupportedEncodingException {
        // 定义空的购物车列表
        OrdShoppingCart items = new OrdShoppingCart();
        String ordShoppingCartValue;
        // 购物cookie
        Cookie cart_cookie = getCookie(request);
        // 判断cookie是否为空
        if (cart_cookie != null) {
            // 获取cookie中String类型的value
            //从cookie获取购物车
            ordShoppingCartValue = URLDecoder.decode(cart_cookie.getValue(), "utf-8");
            // 判断value是否为空或者""字符串
            if (ordShoppingCartValue != null && !"".equals(ordShoppingCartValue)) {
                List<OrdShoppingCart> mapList = JSONArray.parseArray(ordShoppingCartValue, OrdShoppingCart.class);
                for (OrdShoppingCart cart : mapList) {
                    if (cart.getId().equals(id)) {
                        OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
                        ordShoppingCart.setId(cart.getId());

                        ProProductMaster productMaster = ProProductMaster.me().setId(cart.getFkProductId()).get();
                        ordShoppingCart.setFkProductId(productMaster.getId());
                        ordShoppingCart.setProductName(productMaster.getProductName());
                        ordShoppingCart.setPrductPicImageUrl(productMaster.getPrductPicImageUrl());

                        ProProductStock productStock = ProProductStock.me().setProductSubSku(cart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
                        // 2020-10-30 去掉，商品主表库存字段去掉
                        ordShoppingCart.setProductMallPrice(productStock.getProductMallPrice());
                        ordShoppingCart.setProductMarketPrice(productStock.getProductMarketPrice());
                        ordShoppingCart.setProductSubSku(productStock.getProductSubSku());

                        if (productMaster.getIsSingleProduct() == 0) {
                            ordShoppingCartService.optionSecJson(ordShoppingCart);
                        }

                        ordShoppingCart.setTotalProductNumber(cart.getTotalProductNumber());
                        ordShoppingCart.setTotalVol(productMaster.getTotalWeight() * cart.getTotalProductNumber());
                        ordShoppingCart.setCurrency(productMaster.getCurrency());
                        ordShoppingCart.setIsGenOrder(0);
                        ordShoppingCart.setIsGenBalanceAccounts(0);
                        ordShoppingCart.setIsGenReturnOrder(0);
                        ordShoppingCart.setIsDel(0);
                        //获取商品促销信息
                        ordShoppingCartService.finActPromotion(ordShoppingCart.getProductSubSku(), productMaster, ordShoppingCart);
                        // 将当前传来的商品添加到购物车列表
                        return ordShoppingCart;
                    }
                }

            }
        }
        return items;
    }

    public OrdShoppingCart doAddIsNotAuthOrdShoppingCart(ProProductMaster productMaster, Integer proNum, String productSubSku, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        List<OrdShoppingCart> cartVos = new ArrayList<>();
        //获取商品信息
        //获取用户购物车信息
        OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
        ordShoppingCart.setId(IDSnowflake.id());
        ordShoppingCart.setAddTime(Utils.currentTimeSecond());
        ordShoppingCartService.addShopCartData(proNum, productSubSku, productMaster, cartVos, ordShoppingCart);
        checkCookies(response, request, cartVos);
        return ordShoppingCart;
    }

}
