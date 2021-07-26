package com.mazentop.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.PaymentEnum;
import com.mazentop.modules.user.dto.PaymentDto;
import com.mazentop.modules.user.dto.SettlementConfirmDto;
import com.mazentop.modules.web.User;
import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.web.dto.SettlementDto;
import com.mazentop.modules.web.service.SettlementService;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.modules.web.service.TouristService;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.snowflake.IDSnowflake;
import com.paypal.*;
import com.paypal.core.PayPalEnvironment;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.Order;
import com.paypal.orders.Payer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: dengy
 * @date: 2020/5/11
 * @description:
 */
@Service
public class OrderBooksService {


    @Autowired
    BaseDao baseDao;

    @Autowired
    PaypalService paypalService;

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;

    @Autowired
    TouristService touristService;

    @Autowired
    SettlementService settlementService;

    public HttpResponse<Order> doCreatePayments(String settlementId, String cancelUrl, String successUrl, HttpServletRequest request) throws Exception {
        SettlementDto settlementDto = settlementService.getSettlementDto(settlementId,request);

        //paypal支付sendbox账号
        PayPalEnvironment palEnvironment = paypalService.checkPayPalEnvironment();
        //测试数据构建
        OrdPaymentRecord ordPaymentRecord = CreatePayData(settlementDto);
        //封装构建数据
        HttpResponse<Order> orderResponse = new PayCrateOrder().createOrder(false,palEnvironment,cancelUrl,successUrl,ordPaymentRecord,settlementDto);
        ordPaymentRecord.setPayId(orderResponse.result().id()).update();
        return orderResponse;
    }


    public List<String>getCartList(String productId,String productSubSku,Integer num){
        List<String> list = new ArrayList<>();
        ProProductMaster productMaster = ProProductMaster.me().setId(productId).get();
        //构建购物车信息
        if(User.isAuth()){
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
            OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
            ordShoppingCart.setAddTime(Utils.currentTimeSecond());
            ordShoppingCart.setAddUserName(cliClienteleInfo.getLoginName());
            ordShoppingCart.setAddUserId(cliClienteleInfo.getId());
            ordShoppingCart.setFkClienteleId(cliClienteleInfo.getId());
            ordShoppingCart.setClientName(cliClienteleInfo.getClientName());
            ordShoppingCart.setFkProductId(productMaster.getId());
            ordShoppingCart.setProductName(productMaster.getProductName());
            ordShoppingCart.setPrductPicImageUrl(productMaster.getPrductPicImageUrl());

            ProProductStock productStock = ProProductStock.me().setId(productSubSku).setIsEnable(BooleanEnum.TRUE.getValue()).get();
            ordShoppingCart.setProductMallPrice(productStock.getProductMallPrice());
            ordShoppingCart.setProductMarketPrice(productStock.getProductMarketPrice());
            ordShoppingCart.setProductSubSku(productStock.getProductSubSku());

            if (productMaster.getIsSingleProduct() == 0) {
                ordShoppingCartService.optionSecJson(ordShoppingCart);
            }

            ordShoppingCart.setTotalProductNumber(num);
            ordShoppingCart.setTotalVol(productMaster.getTotalWeight());
            ordShoppingCart.setCurrency(productMaster.getCurrency());
            ordShoppingCart.setIsGenOrder(0);
            ordShoppingCart.setIsGenBalanceAccounts(1);
            ordShoppingCart.setIsGenReturnOrder(0);
            ordShoppingCart.setIsDel(0);
            //获取商品促销信息
            ordShoppingCartService.finActPromotion(productSubSku, productMaster, ordShoppingCart);
            ordShoppingCart.insert();
            list.add(ordShoppingCart.getId());
        }
        return list;
    }


    public List<OrdShoppingCart> getNotLoginCartList (String productId,String productSubSku,Integer num,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        if(!User.isAuth()) {
            ProProductMaster productMaster = ProProductMaster.me().setId(productId).get();
            List<OrdShoppingCart> cartVos = touristService.getCartInCookie(request);
            OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
            ordShoppingCart.setId(IDSnowflake.id());
            ordShoppingCart.setAddTime(Utils.currentTimeSecond());
            ordShoppingCartService.addShopCartData(num, productSubSku, productMaster, cartVos, ordShoppingCart);
            ordShoppingCart.setIsGenBalanceAccounts(1);
            touristService.checkCookies(response, request, cartVos);
            return cartVos;
        }
        return null;
    }

    public OrdBalanceTheBooksDto getBooksDto(List<Map<String,Object>> cartList,HttpServletRequest request,HttpServletResponse response) {
        OrdBalanceTheBooksDto ordBalanceTheBooksDto = new OrdBalanceTheBooksDto();
        if(!cartList.isEmpty()) {
            //计算购物车结算价格、优惠价格
            BigDecimal discountValue = new BigDecimal(0);
            BigDecimal totalCountValue = new BigDecimal(0);
            BigDecimal totalCountVol = new BigDecimal(0);
            BigDecimal totalWeight = new BigDecimal(0);
            List<String> ids = new ArrayList<>();
            for (Map<String, Object> maps : cartList) {
                BigDecimal mallPrice;
                BigDecimal marketPrice;
                BigDecimal promotionPrice;
                int number = Integer.parseInt(maps.get("total_product_number").toString());
                ids.add(maps.get("id").toString());
                //金额处理
                if (!Objects.isNull(maps.get("product_mall_price"))) {
                    mallPrice = Helper.transformF2Y(maps.get("product_mall_price"));
                    maps.put("product_mall_price", mallPrice);
                } else {
                    mallPrice = Helper.transformF2Y(0);
                    maps.put("product_mall_price", mallPrice);
                }
                if (!Objects.isNull(maps.get("product_market_price"))) {
                    marketPrice = Helper.transformF2Y(maps.get("product_market_price"));
                    maps.put("product_market_price", marketPrice);
                } else {
                    marketPrice = Helper.transformF2Y(0);
                    maps.put("product_market_price", marketPrice);
                }
                if (!Objects.isNull(maps.get("product_promotion_price"))) {
                    promotionPrice = Helper.transformF2Y(maps.get("product_promotion_price"));
                    maps.put("product_promotion_price", promotionPrice);
                } else {
                    promotionPrice = Helper.transformF2Y(0);
                    maps.put("product_promotion_price", promotionPrice);
                }
                if (!Objects.isNull(maps.get("discount_value"))) {
                    maps.put("discount_value", Helper.transformF2Y(maps.get("discount_value")));
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
             }
                ordBalanceTheBooksDto.setProductTotalPriceBig(totalCountValue);
                totalCountValue = totalCountValue.subtract(discountValue);
                ordBalanceTheBooksDto.setProductDiscountPriceBig(totalCountValue);
                ordBalanceTheBooksDto.setPaymentTotalPriceBig(totalCountValue);
                ordBalanceTheBooksDto.setTotalCountValue(totalWeight);
                ordBalanceTheBooksDto.setOrdShoppingCartList(ids);
                ordBalanceTheBooksDto.setIsRecall(0);
                ordBalanceTheBooksDto.setIsNeedRecall(0);
                paypalService.optionOrdBalanceTheBooks(ordBalanceTheBooksDto, request, response);
        }
        return ordBalanceTheBooksDto;
    }



    public String getOrder(String token) throws Exception {
        PayPalEnvironment palEnvironment = paypalService.checkPayPalEnvironment();
        HttpResponse<Order> response = new GetOrder().getOrder(token,palEnvironment);
        if(response.statusCode() == 200){
            //获取购买收货地址
            AddressPortable addressPortable = response.result().purchaseUnits().get(0).shippingDetail().addressPortable();
            //获取购买人信息
            Payer payer = response.result().payer();
            //获取支付订单信息
            OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setPayId(token).get();
            //获取结算单信息
            OrdSettlementConfirm ordSettlementConfirm = OrdSettlementConfirm.me().setId(ordPaymentRecord.getBalanceTheBooksNo()).get();

            SettlementConfirmDto settlementConfirmDto = JSON.parseObject(ordSettlementConfirm.getContent(), SettlementConfirmDto.class);
            //获取国家三代码
            SysCountry sysCountry = SysCountry.me().setId(addressPortable.countryCode()).get();

            //构建订单地址
            SettlementConfirmDto.Address address = new SettlementConfirmDto.Address();
            address.setName(payer.name().givenName());
            address.setSurname(payer.name().surname());
            address.setCountry(sysCountry.getAlpha3());
            address.setProvince(addressPortable.adminArea1());
            address.setCity(addressPortable.adminArea2());
            address.setEmail(payer.email());
            address.setPost(addressPortable.postalCode());
            address.setAddress(addressPortable.addressLine1());

            settlementConfirmDto.setShippingAddress(address);
            ordSettlementConfirm.setContent(JSON.toJSONString(settlementConfirmDto));
            ordSettlementConfirm.update();
            return ordSettlementConfirm.getId();
        }
        return null;
    }

    public CliClienteleReceiverAddress optionAddress(String id, Map<String,Object>map){
        OrdInvoiceAddress ordInvoiceAddress = OrdInvoiceAddress.me().setId(id).get();
        map.put("ordInvoiceAddress",ordInvoiceAddress);
        CliClienteleReceiverAddress cliClienteleReceiverAddress = new CliClienteleReceiverAddress();
        cliClienteleReceiverAddress.setId(ordInvoiceAddress.getId());
        cliClienteleReceiverAddress.setIsDefault(0);
        cliClienteleReceiverAddress.setEmail(ordInvoiceAddress.getReceiptEmail());
        cliClienteleReceiverAddress.setClientName(ordInvoiceAddress.getReceiptName());
        cliClienteleReceiverAddress.setClientSurname(ordInvoiceAddress.getReceiptSurname());
        cliClienteleReceiverAddress.setCountry(ordInvoiceAddress.getReceiptCountry());
        cliClienteleReceiverAddress.setCity(ordInvoiceAddress.getReceiptCity());
        cliClienteleReceiverAddress.setProvince(ordInvoiceAddress.getReceiptProvince());
        cliClienteleReceiverAddress.setAddress(ordInvoiceAddress.getReceiptAddress());
        cliClienteleReceiverAddress.setPost(ordInvoiceAddress.getReceiptPost());
        return cliClienteleReceiverAddress;
    }


    public HttpResponse<Order>  doUpdateOrdBooksData(String orderId,OrdPaymentRecord ordPaymentRecord,HttpServletRequest request) throws Exception {
        PayPalEnvironment palEnvironment = paypalService.checkPayPalEnvironment();
        OrdSalesOrder order = OrdSalesOrder.me().setSalesOrderNo(orderId).get();
        SettlementDto settlementDto = settlementService.getSettlementDto(ordPaymentRecord.getBalanceTheBooksNo(),request);
        optionPaymentRecord(order,ordPaymentRecord);
        HttpResponse<Order> response = new PatchOrder().patchOrder(ordPaymentRecord.getPayId(),palEnvironment,settlementDto);
        if(response.statusCode() == 200) {
            ordPaymentRecord.update();
            SysOptions sysOptions = SysOptions.me().setOptionKey("paypal_BnCode").get();
            String bnCode = sysOptions.getOptionValue();
            HttpResponse<Order> result = new CaptureOrder().captureOrders(ordPaymentRecord.getPayId(), false, palEnvironment,bnCode);
            return result;
        }
        return null;
    }
    public void optionPaymentRecord(OrdSalesOrder order,OrdPaymentRecord ordPaymentRecord){
        ordPaymentRecord.setFkClienteleId(order.getAddUserId());
        ordPaymentRecord.setPaymentTotalPrice(order.getTotalPaymentFree());
        ordPaymentRecord.setFreeSchemeTotalPrice(order.getTotalTransportsFree());
        ordPaymentRecord.setProductTotalPrice(order.getTotalPrice());
        ordPaymentRecord.setProductDiscountPrice(order.getProductDiscountPrice());
        ordPaymentRecord.setOrderId(order.getSalesOrderNo());
        ordPaymentRecord.setFkPaymentTypeId(order.getPaymentPlatformTypeId());
        ordPaymentRecord.setPaymentTypeName(order.getPaymentPlatformName());
        ordPaymentRecord.setPaymentTime(Utils.currentTimeSecond());
        ordPaymentRecord.setAddTime(Utils.currentTimeSecond());
        ordPaymentRecord.setAddUserId(order.getAddUserId());
    }

    public void optionPrice(OrdBalanceTheBooksDto ordBalanceTheBooksDto,OrdBalanceTheBooks ordBalanceTheBooks,OrdPaymentRecord ordPaymentRecord){
        if (ordBalanceTheBooksDto.getProductTotalPriceBig() != null) {
            ordBalanceTheBooksDto.setProductTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getProductTotalPriceBig()));
            ordPaymentRecord.setProductTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getProductTotalPriceBig()));
        }
        //判断支付总价格是否为空
        if (ordBalanceTheBooksDto.getPaymentTotalPriceBig() != null) {
            ordBalanceTheBooksDto.setPaymentTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getPaymentTotalPriceBig()));
            ordPaymentRecord.setPaymentTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getPaymentTotalPriceBig()));
        }
        //判断优惠总价格是否为空
        if (ordBalanceTheBooksDto.getProductDiscountPriceBig() != null) {
            ordBalanceTheBooksDto.setProductDiscountPrice(Helper.transformY2F(ordBalanceTheBooksDto.getProductDiscountPriceBig()));
            ordPaymentRecord.setProductDiscountPrice(Helper.transformY2F(ordBalanceTheBooksDto.getProductDiscountPriceBig()));
        }
        //运费
        if (ordBalanceTheBooksDto.getFreeSchemeTotalPriceBig() != null) {
            ordBalanceTheBooksDto.setFreeSchemeTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getFreeSchemeTotalPriceBig()));
            ordPaymentRecord.setFreeSchemeTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getFreeSchemeTotalPriceBig()));
        }
        //税费
        if (ordBalanceTheBooksDto.getTaxRateBig() != null) {
            ordBalanceTheBooksDto.setTaxRate(Helper.transformY2F(ordBalanceTheBooksDto.getTaxRateBig()));
        }
        if (Helper.isNotEmpty(ordBalanceTheBooksDto.getFkFreeSchemeId())) {
            ordBalanceTheBooksDto.setFreeSchemeName(OrdFreeSchemeDetails.me().setId(ordBalanceTheBooksDto.getFkFreeSchemeId()).get().getSchemeInsideName());
        }
        if(ordBalanceTheBooksDto.getCouponDiscountValueBig() !=null){
            ordBalanceTheBooksDto.setCouponDiscountValue(Helper.transformY2F(ordBalanceTheBooksDto.getCouponDiscountValueBig()));
        }
        if(ordBalanceTheBooksDto.getReduceDiscountValueBig() !=null){
            ordBalanceTheBooksDto.setReduceDiscountValue(Helper.transformY2F(ordBalanceTheBooksDto.getReduceDiscountValueBig()));
        }
        ordBalanceTheBooks.setRemark(ordBalanceTheBooksDto.getRemark());
        BeanUtils.copyProperties(ordBalanceTheBooksDto,ordBalanceTheBooks);
    }

    public List<Map<String, Object>> getShoppingCartList(List<String> ids,List<OrdShoppingCart> catList) throws UnsupportedEncodingException {
        List<Map<String, Object>> list = new ArrayList<>();
        if (User.isAuth()) {
            String sql = "select * from ord_shopping_cart where 1=1 and id in (:ids)  ";
            Map<String, Object> param = new HashMap<>();
            param.put("ids", ids);
            list = baseDao.queryForList(sql, param);
        }else{
            List<Map<String, Object>> mapList = new ArrayList<>();
            catList.forEach(cartData->{
               OrdShoppingCart cart = cartData;
               if(!Objects.isNull(cart)) {
                   Map<String, Object> map = new HashMap<>();
                   map.put("id", cart.getId());
//                   map.put("fk_product_color_size_id", cart.getFkProductColorSizeId());
                   map.put("product_sub_sku", cart.getProductSubSku());
                   map.put("fk_product_id", cart.getFkProductId());
                   map.put("product_name", cart.getProductName());
                   map.put("prduct_pic_image_url", cart.getPrductPicImageUrl());
                   map.put("product_mall_price", cart.getProductMallPrice());
                   map.put("product_market_price", cart.getProductMarketPrice());
                   map.put("product_promotion_price", cart.getProductPromotionPrice());
                   map.put("fk_activity_id", cart.getFkActivityId());
                   map.put("activity_name", cart.getActivityName());
                   map.put("discount_type_name", cart.getDiscountTypeName());
                   map.put("discount_value", cart.getDiscountValue());
                   map.put("total_product_number", cart.getTotalProductNumber());
                   map.put("total_vol", cart.getTotalVol());
                   map.put("currency", cart.getCurrency());
                   map.put("product_spec", cart.getProductSpec());
                   mapList.add(map);
                }

            });
            list = mapList;
        }
        return list;
    }


    public OrdPaymentRecord CreatePayData(SettlementDto settlementDto) {
        //封装销售单信息
        OrdPaymentRecord ordPaymentRecord = new OrdPaymentRecord();
        if(User.isAuth()) {
            ordPaymentRecord.setFkClienteleId(User.id());
        }
        ordPaymentRecord.setBalanceTheBooksNo(settlementDto.getSettlementId());

        // 用户名称、订单号、支付交易号 查询一次记录如果存在直接返回
        OrdPaymentRecord dbOrdPaymentRecord = ordPaymentRecord.findFirst();
        if(!Objects.isNull(dbOrdPaymentRecord)) {
            return dbOrdPaymentRecord;
        }
        optionOrdPaymentRecordData(settlementDto, ordPaymentRecord);

        return ordPaymentRecord;
    }
    private void optionOrdPaymentRecordData(SettlementDto settlementDto, OrdPaymentRecord ordPaymentRecord) {
        //封装交易信息
        ordPaymentRecord.setPaymentTotalPrice(Helper.transformY2F(new BigDecimal(settlementDto.getTotalPrice())));
        ordPaymentRecord.setFreeSchemeTotalPrice(Helper.transformY2F(new BigDecimal(settlementDto.getShippingPrice())));
        ordPaymentRecord.setProductTotalPrice(Helper.transformY2F(new BigDecimal(settlementDto.getSubTotalPrice())));
        ordPaymentRecord.setProductDiscountPrice(Helper.transformY2F(new BigDecimal(settlementDto.getTotalPrice())));

        ordPaymentRecord.setPaymentTime(Utils.currentTimeSecond());
        ordPaymentRecord.setAddTime(Utils.currentTimeSecond());
        if(User.isAuth()) {
            ordPaymentRecord.setAddUserId(User.id());
        }
        ordPaymentRecord.setIsPaySuccess(0);
        //获取支付方式
        OrdPaymentType ordPaymentType = OrdPaymentType.me().setPaymentTypeName(PaymentEnum.getName("0")).get();
        if (!Objects.isNull(ordPaymentType)) {
            ordPaymentRecord.setFkPaymentTypeId(ordPaymentType.getId());
            ordPaymentRecord.setPaymentTypeName(ordPaymentType.getPaymentTypeName());
        }
        ordPaymentRecord.insertOrUpdate();
    }
}
