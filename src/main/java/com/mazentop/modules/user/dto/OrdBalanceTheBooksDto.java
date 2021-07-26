package com.mazentop.modules.user.dto;

import com.mazentop.entity.OrdBalanceTheBooks;
import com.mazentop.entity.OrdInvoiceAddress;
import com.mazentop.entity.SysNotLoginPurchaseInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrdBalanceTheBooksDto extends OrdBalanceTheBooks {
    /**
     * 购物车商品信息
     */
     List<String> ordShoppingCartList;
    /**
     * 收货地址
     */
     String addressId;

    /**
     * 是否支付成功!
     */
    boolean PayFlag;

    BigDecimal productTotalPriceBig;

    BigDecimal productDiscountPriceBig;

    BigDecimal freeSchemeTotalPriceBig;

    BigDecimal paymentTotalPriceBig;

    BigDecimal taxRateBig;

    BigDecimal couponDiscountValueBig;

    BigDecimal reduceDiscountValueBig;

    /**
     * 支付类型 0.Paypal 1.Asiabill 2.线下支付
     */
    int payType;

    SysNotLoginPurchaseInfo sysNotLoginPurchaseInfo;

    String sysCountryId;

    /**
     * 是否发票收货地址
     */
    int isInvoice;

    /**
     * 发票地址信息
     */
    OrdInvoiceAddress ordInvoiceAddress;

    BigDecimal totalCountValue;

    String province;
}
