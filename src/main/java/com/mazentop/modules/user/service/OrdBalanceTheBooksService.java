package com.mazentop.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.model.OrderAddressTypeEnum;
import com.mazentop.modules.user.commond.OrdShoppingCartsCommond;
import com.mazentop.modules.user.dto.CreateOrder;
import com.mazentop.modules.user.dto.SettlementConfirmDto;
import com.mazentop.modules.web.User;
import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.dto.SettlementDto;
import com.mazentop.modules.web.service.SettlementService;
import com.mazentop.modules.web.service.TouristService;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.R;
import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单结算表
 *
 * @author dengy
 */
@Service
public class OrdBalanceTheBooksService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    OrdShoppingCartService optOrdShopping;

    @Autowired
    SettlementService settlementService;

    @Autowired
    TouristService touristService;

    /**
     * 创建订单
     */
    public R doGenerateOrder(CreateOrder createOrder, HttpServletRequest request) throws UnsupportedEncodingException {
        OrdSettlementConfirm settlementConfirm = OrdSettlementConfirm.me().setId(createOrder.getSettlement()).get();
        if (Objects.isNull(settlementConfirm)) {
            return R.error("Failed to get order information");
        }
        SettlementConfirmDto settlementConfirmDto = JSON.parseObject(settlementConfirm.getContent(), SettlementConfirmDto.class);
        // 判断是不是换支付方式
        Long count = OrdPaymentRecord.me().setBalanceTheBooksNo(createOrder.getSettlement()).findCount();
        if(count>0){
            OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setBalanceTheBooksNo(createOrder.getSettlement()).get();
            OrdSalesOrder order = OrdSalesOrder.me().setSalesOrderNo(ordPaymentRecord.getOrderId()).setSalesOrderStatus(OrdSalesOrderStatusEnum.CREATE_COMPLETE.status()).get();
            if(Objects.nonNull(order)){
                order.setPaymentPlatformTypeId(createOrder.getPayType());
                OrdPaymentType ordPaymentType = OrdPaymentType.me().setId(createOrder.getPayType()).get();
                if (Objects.nonNull(ordPaymentType)) {
                    order.setPaymentPlatformName(ordPaymentType.getPaymentTypeName());
                }
                optionUpdateOrder(order,settlementConfirm.getId(),settlementConfirmDto,request);
                order.update();
                return R.ok(mapOrderInitResult(order));
            }
        }

        //收货地址
        OrdOrderAddress shippingAddress = new OrdOrderAddress();
        OrdOrderAddress invoiceAddress = new OrdOrderAddress();
        builderAddress(settlementConfirmDto, shippingAddress, invoiceAddress);

        // 组中购物车运费方案查询条件
        OrdBalanceTheBooksDto ordBalanceTheBooksDto = new OrdBalanceTheBooksDto();
        ordBalanceTheBooksDto.setFkFreeSchemeId(settlementConfirmDto.getFreeSchemes());

        createOrder.setItems(settlementConfirmDto.getCarts());

        SettlementDto settlementDto = settlementService.getSettlementDto(createOrder.getSettlement(),request);
        //校验库存
        List<OrdShoppingCart> carts;
        if(User.isAuth()) {
            OrdShoppingCartsCommond commond = new OrdShoppingCartsCommond();
            commond.setCarts(createOrder.getItems());
            carts = OrdShoppingCart.me().find(commond);
        }else{
            carts = touristService.getCartInCookie(request);
        }
        if (checkStack(carts)) {
            OrdSalesOrder order = saveOrder(settlementDto, createOrder, shippingAddress, invoiceAddress);
            saveOrderDetail(order, carts);
            return R.ok(mapOrderInitResult(order));
        }
        return R.error("Insufficient stock");

    }

    public void builderAddress(SettlementConfirmDto settlementConfirmDto, OrdOrderAddress shippingAddress, OrdOrderAddress invoiceAddress) {
        // 收货地址
        BeanUtils.copyProperties(settlementConfirmDto.getShippingAddress(), shippingAddress);
        shippingAddress.setType(OrderAddressTypeEnum.SHIPPING.type());
        shippingAddress.insert();

        // 发票地址
        BeanUtils.copyProperties(settlementConfirmDto.getInvoiceAddress(), invoiceAddress);
        invoiceAddress.setType(OrderAddressTypeEnum.INVOICE.type());
        invoiceAddress.insert();
    }

    public OrdSalesOrder saveOrder(SettlementDto settlementDto, CreateOrder createOrder, OrdOrderAddress shippingAddress, OrdOrderAddress invoiceAddress) {
        OrdSalesOrder ordSalesOrder = new OrdSalesOrder();
        ordSalesOrder.setSalesOrderNo(IDSnowflake.id());
        optionOrder(settlementDto, shippingAddress, ordSalesOrder);
        //获取优惠
        /*getDiscountPrice(ordSalesOrder, map);*/
        ordSalesOrder.setPaymentPlatformTypeId(createOrder.getPayType());
        OrdPaymentType ordPaymentType = OrdPaymentType.me().setId(createOrder.getPayType()).get();
        if (Objects.nonNull(ordPaymentType)) {
            ordSalesOrder.setPaymentPlatformName(ordPaymentType.getPaymentTypeName());
        }
        ordSalesOrder.setCurrency("USD");
        if (Helper.isNotEmpty(invoiceAddress)) {
            ordSalesOrder.setFkInvoiceAddressId(invoiceAddress.getId());
        }
        ordSalesOrder.setRemark(createOrder.getOrdRemark());
        Long sn = baseDao.queryForLong("select max(localhost_sn) from ord_sales_order",new HashMap(1));
        ordSalesOrder.setLocalhostSn(sn+1);
        if(settlementDto.getCartFlag()){
            ordSalesOrder.setIsEnable(0);
        }
        ordSalesOrder.insert();
        return ordSalesOrder;
    }

    private void optionOrder(SettlementDto settlementDto, OrdOrderAddress shippingAddress, OrdSalesOrder ordSalesOrder) {
        CliClienteleInfo cliClienteleInfo = new CliClienteleInfo();
        if(User.isAuth()){
            cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        }else{
            SysNotLoginPurchaseInfo info = addNotLongUser(ordSalesOrder.getSalesOrderNo(),shippingAddress);
            BeanUtils.copyProperties(info,cliClienteleInfo);
            cliClienteleInfo.setClientName(info.getName());
            cliClienteleInfo.setClientSurname(info.getSurname());
            cliClienteleInfo.setLoginName(info.getNotLoginName());
            info.setId(IDSnowflake.id());
            info.insert();
        }
        if (!Objects.isNull(shippingAddress)) {
            //收货信息
            ordSalesOrder.setFkClienteleAddressId(shippingAddress.getId());
            ordSalesOrder.setReceiverId(cliClienteleInfo.getId());
            ordSalesOrder.setReceiverPhone(shippingAddress.getPhone());
            ordSalesOrder.setReceiverAddress(shippingAddress.getAddress());
            ordSalesOrder.setReceiverCity(shippingAddress.getCity());
            ordSalesOrder.setReceiverEmail(shippingAddress.getEmail());
            ordSalesOrder.setReceiverSheet(shippingAddress.getSheet());
            ordSalesOrder.setReceiverName(shippingAddress.getName());
            ordSalesOrder.setReceiverPost(shippingAddress.getPost());
            ordSalesOrder.setReceiverCountry(shippingAddress.getCountry());
            ordSalesOrder.setReceiverProvince(shippingAddress.getProvince());
        }
        ordSalesOrder.setAddTime(Utils.currentTimeSecond());
        ordSalesOrder.setAddUserName(cliClienteleInfo.getLoginName());
        ordSalesOrder.setAddUserId(cliClienteleInfo.getId());
        ordSalesOrder.setFkClienteleId(cliClienteleInfo.getId());
        ordSalesOrder.setClientName(cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName());
        ordSalesOrder.setClientEmail(cliClienteleInfo.getEmail());
        ordSalesOrder.setClientPhone(cliClienteleInfo.getPhone());
        ordSalesOrder.setTotalProductNumber(settlementDto.getCartNum());
        ordSalesOrder.setProductDiscountPrice(Helper.transformY2F(new BigDecimal(settlementDto.getSubTotalPrice())));
        ordSalesOrder.setTotalPaymentFree(Helper.transformY2F(new BigDecimal(settlementDto.getTotalPrice())));
        ordSalesOrder.setTotalTransportsFree(Helper.transformY2F(new BigDecimal(settlementDto.getShippingPrice())));
        ordSalesOrder.setTransportsChannelId(settlementDto.getFreeSchemes());
        ordSalesOrder.setTransportsChannelName(settlementDto.getFreeSchemesName());
        ordSalesOrder.setTotalPrice(Helper.transformY2F(new BigDecimal(settlementDto.getSubTotalPrice())));
        ordSalesOrder.setPaymentName(cliClienteleInfo.getLoginName());
        ordSalesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.CREATE_COMPLETE.status());
        ordSalesOrder.setIsEnable(1);
    }

    /**
     * 添加无登录购买记录
     * @param ordNo
     * @param shippingAddress
     * @return
     */
    public SysNotLoginPurchaseInfo addNotLongUser(String ordNo, OrdOrderAddress shippingAddress){
        SysNotLoginPurchaseInfo sysNotLoginPurchaseInfo = new SysNotLoginPurchaseInfo();
        if(!Objects.isNull(shippingAddress)){
            BeanUtils.copyProperties(shippingAddress,sysNotLoginPurchaseInfo);
        }
        sysNotLoginPurchaseInfo.setNotLoginOrderNo(ordNo);
        sysNotLoginPurchaseInfo.setIsPaySuccess(0);
        sysNotLoginPurchaseInfo.setNotLoginIp(Helper.getIpAddress(WebUtil.getHttpServletRequest()));
        sysNotLoginPurchaseInfo.setNotLoginName(shippingAddress.getSurname()+shippingAddress.getName());
        sysNotLoginPurchaseInfo.insert();
        return sysNotLoginPurchaseInfo;
    }

    public void getDiscountPrice(OrdSalesOrder ordSalesOrder, Map<String, Object> map) {
        Long productDiscountPrice = Helper.transformY2F(new BigDecimal(map.get("productDiscountPrice").toString()));
        //优惠卷
        if (Objects.nonNull(map.get("fkCouponActivityId"))) {
            if(StringUtils.isNotBlank(map.get("fkCouponActivityId").toString())) {
                ordSalesOrder.setCouponActivityId(map.get("fkCouponActivityId").toString());
                ordSalesOrder.setCouponActivityName(map.get("couponActivityName").toString());
                ordSalesOrder.setCouponActivityResult(map.get("couponDiscountTypeName").toString());
            }
        }
        if(Objects.nonNull(map.get("fkReduceActivityId"))){
            //满减活动
            if(StringUtils.isNotBlank(map.get("fkReduceActivityId").toString())) {
                ordSalesOrder.setReduceActivityId(map.get("fkReduceActivityId").toString());
                ordSalesOrder.setReduceActivityName(map.get("reduceActivityName").toString());
                ordSalesOrder.setReduceActivityResult(map.get("reduceDiscountTypeName").toString());
            }
        }
        ordSalesOrder.setProductDiscountPrice(productDiscountPrice);
    }

    public Map<String, Object> mapOrderInitResult(OrdSalesOrder order) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("source", order.getSalesOrderNo());
        resultMap.put("payType", order.getPaymentPlatformName());
        return resultMap;
    }


    public void saveOrderDetail(OrdSalesOrder ordSalesOrder, List<OrdShoppingCart> carts) {
        carts.forEach(ordShoppingCart -> {
            OrdSalesOrderDetail detail = new OrdSalesOrderDetail();
            detail.setFkSalesOrderId(ordSalesOrder.getId());
            detail.setSalesOrderNo(ordSalesOrder.getSalesOrderNo());
            detail.setAddTime(Utils.currentTimeSecond());
            detail.setAddUserId(ordSalesOrder.getAddUserId());
            detail.setAddUserName(ordSalesOrder.getAddUserName());
            ProProductMaster proProductMaster = ProProductMaster.me().setId(ordShoppingCart.getFkProductId()).get();
            detail.setFkProductId(proProductMaster.getId());
            detail.setFkProductTypeId(proProductMaster.getFkProductTypeId());
            ProProductType proProductType = ProProductType.me().setId(detail.getFkProductTypeId()).get();
            if (!Objects.isNull(proProductType)) {
                detail.setProductTypeName(proProductType.getProductTypeName());
            }
            detail.setProductName(ordShoppingCart.getProductName());
            detail.setProductImageUrl(ordShoppingCart.getPrductPicImageUrl());
            detail.setProductNum(ordShoppingCart.getTotalProductNumber());
            detail.setProductBarcode(proProductMaster.getProductBarcode());
            detail.setProductSku(proProductMaster.getProductSku());
            detail.setProductMallPrice(Helper.transformY2F(Helper.transformF2Y(ordShoppingCart.getProductMallPrice())));
            detail.setProductMarketPrice(Helper.transformY2F(Helper.transformF2Y(ordShoppingCart.getProductMarketPrice())));
            detail.setProductActivityPrice(Helper.transformY2F(Helper.transformF2Y(ordShoppingCart.getProductPromotionPrice())));
            detail.setProductArea(proProductMaster.getProductArea());
            detail.setPromotionActivityId(ordShoppingCart.getFkActivityId());
            detail.setPromotionActivityName(ordShoppingCart.getActivityName());
            detail.setPromotionActivityResult(ordShoppingCart.getDiscountTypeName());
            detail.setUnitVol(proProductMaster.getTotalVol());
            detail.setUnitNetWeight(proProductMaster.getNetWeight() * ordShoppingCart.getTotalProductNumber());
            detail.setUnitWeight(ordShoppingCart.getTotalVol());
            detail.setCurrency(ordShoppingCart.getCurrency());
            detail.setCusEnDescription(proProductMaster.getCusEnDescription());
            detail.setCusHsCode(proProductMaster.getCusHsCode());
            detail.setCusPrice(Helper.transformY2F(Helper.transformF2Y(proProductMaster.getCusPrice())));
            detail.setCusProductType(proProductMaster.getCusProductType());
            detail.setCusZhDescription(proProductMaster.getCusZhDescription());
            detail.setLength(proProductMaster.getLength());
            detail.setWidth(proProductMaster.getWidth());
            detail.setHeight(proProductMaster.getHeight());
            detail.setFkBrandId(proProductMaster.getFkBrandId());
            ProBrand proBrand = ProBrand.me().setId(detail.getFkBrandId()).get();
            if (!Objects.isNull(proBrand)) {
                detail.setBrandName(proBrand.getBrandName());
            }

            ProProductStock productSku = ProProductStock.me().setFkProductId(ordShoppingCart.getFkProductId()).setProductSubSku(ordShoppingCart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
            //减少库存
            productSku.setProductStockNumber(productSku.getProductStockNumber() - ordShoppingCart.getTotalProductNumber()).update();

            detail.setProductSubSku(ordShoppingCart.getProductSubSku());
            detail.setProductSpec(ordShoppingCart.getProductSpec());
            detail.setLocalhostSn(ordSalesOrder.getLocalhostSn());
            ordShoppingCart.setIsGenOrder(1).setIsDel(1).setIsGenBalanceAccounts(1).update();
            detail.insert();
        });
    }

    public boolean checkStack(List<OrdShoppingCart> carts) {
        boolean flag = true;
        for (OrdShoppingCart ordShoppingCart : carts) {
            //判断库存
            ProProductStock productSku = new ProProductStock().setFkProductId(ordShoppingCart.getFkProductId()).setProductSubSku(ordShoppingCart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
            if (Objects.nonNull(productSku)) {
                Integer productStockNumber = productSku.getProductStockNumber();
                if (ordShoppingCart.getTotalProductNumber() > productStockNumber) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
    public void optionUpdateOrder(OrdSalesOrder order,String id,SettlementConfirmDto settlementConfirmDto,HttpServletRequest request){
        SettlementDto settlementDto = settlementService.getSettlementDto(id,request);
        //收货地址
        OrdOrderAddress shippingAddress = new OrdOrderAddress();
        OrdOrderAddress invoiceAddress = new OrdOrderAddress();
        builderAddress(settlementConfirmDto, shippingAddress, invoiceAddress);
        optionOrder(settlementDto, shippingAddress, order);
    }
}
