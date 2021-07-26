package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:05 2020/04/23
* Company:     美赞拓
* Version:     1.0
* Description: OrdBalanceTheBooks实体
*/
@SuppressWarnings("all")
public class OrdBalanceTheBooks extends BaseBean<OrdBalanceTheBooks> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_balance_the_books";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 购物车编号集合
     */
    public static final String F_SHOPPING_CART_LIST = "shopping_cart_list";
    /**
     * 结算单编号
     */
    public static final String F_BALANCE_THE_BOOKS_NO = "balance_the_books_no";
    /**
     * 客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 收货地址编号
     */
    public static final String F_FK_CLIENTELE_RECEIVER_ADDRESS_ID = "fk_clientele_receiver_address_id";
    /**
     * 发票地址
     */
    public static final String F_FK_ORD_INVOICE_ADDRESS_ID = "fk_ord_invoice_address_id";
    /**
     * 收货国家编号
     */
    public static final String F_FK_SYS_COUNTRY_ID = "fk_sys_country_id";
    /**
     * 收货国家名称
     */
    public static final String F_COUNTRY_NAME = "country_name";
    /**
     * 国家税费
     */
    public static final String F_TAX_RATE = "tax_rate";
    /**
     * 商品数量
     */
    public static final String F_TOTAL_PRODUCT_NUMBER = "total_product_number";
    /**
     * 商品总重量
     */
    public static final String F_TOTAL_WEIGHT = "total_weight";
    /**
     * 币制
     */
    public static final String F_CURRENCY = "currency";
    /**
     * 币种id
     */
    public static final String F_FK_CURRENCY_ID = "fk_currency_id";
    /**
     * 支付方式编号
     */
    public static final String F_FK_PAYMENT_TYPE_ID = "fk_payment_type_id";
    /**
     * 支付方式名称
     */
    public static final String F_PAYMENT_TYPE_NAME = "payment_type_name";
    /**
     * 物流费用方案编号
     */
    public static final String F_FK_FREE_SCHEME_ID = "fk_free_scheme_id";
    /**
     * 物流费用方案名称
     */
    public static final String F_FREE_SCHEME_NAME = "free_scheme_name";
    /**
     * 商品总价格
     */
    public static final String F_PRODUCT_TOTAL_PRICE = "product_total_price";
    /**
     * 商品优惠后总价格
     */
    public static final String F_PRODUCT_DISCOUNT_PRICE = "product_discount_price";
    /**
     * 物流费用费用价格
     */
    public static final String F_FREE_SCHEME_TOTAL_PRICE = "free_scheme_total_price";
    /**
     * 待支付总价格
     */
    public static final String F_PAYMENT_TOTAL_PRICE = "payment_total_price";
    /**
     * 优惠卷活动编号
     */
    public static final String F_FK_COUPON_ACTIVITY_ID = "fk_coupon_activity_id";
    /**
     * 优惠卷活动名称
     */
    public static final String F_COUPON_ACTIVITY_NAME = "coupon_activity_name";
    /**
     * 优惠卷码
     */
    public static final String F_COUPON_CODE = "coupon_code";
    /**
     * 优惠卷优惠类型名称(折扣、抵扣)
     */
    public static final String F_COUPON_DISCOUNT_TYPE_NAME = "coupon_discount_type_name";
    /**
     * 优惠卷优惠数据
     */
    public static final String F_COUPON_DISCOUNT_VALUE = "coupon_discount_value";
    /**
     * 满减活动编号
     */
    public static final String F_FK_REDUCE_ACTIVITY_ID = "fk_reduce_activity_id";
    /**
     * 满减活动名称
     */
    public static final String F_REDUCE_ACTIVITY_NAME = "reduce_activity_name";
    /**
     * 满减优惠类型名称(折扣、抵扣)
     */
    public static final String F_REDUCE_DISCOUNT_TYPE_NAME = "reduce_discount_type_name";
    /**
     * 满减优惠数据
     */
    public static final String F_REDUCE_DISCOUNT_VALUE = "reduce_discount_value";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
     * 添加人名称
     */
    public static final String F_ADD_USER_NAME = "add_user_name";
    /**
     * 操作时间
     */
    public static final String F_OPERATION_TIME = "operation_time";
    /**
     * 操作人编号
     */
    public static final String F_OPERATION_USER_ID = "operation_user_id";
    /**
     * 操作人名称
     */
    public static final String F_OPERATION_USER_NAME = "operation_user_name";
    /**
     * 0.否 1.是 是否召回成功
     */
    public static final String F_IS_RECALL = "is_recall";
    /**
     * 是否需要召回
     */
    public static final String F_IS_NEED_RECALL = "is_need_recall";
    /**
     * 是否废弃
     */
    public static final String F_IS_DISCARD = "is_discard";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SHOPPING_CART_LIST, null);
        put(F_BALANCE_THE_BOOKS_NO, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_FK_CLIENTELE_RECEIVER_ADDRESS_ID, null);
        put(F_FK_ORD_INVOICE_ADDRESS_ID, null);
        put(F_FK_SYS_COUNTRY_ID, null);
        put(F_COUNTRY_NAME, null);
        put(F_TAX_RATE, null);
        put(F_TOTAL_PRODUCT_NUMBER, null);
        put(F_TOTAL_WEIGHT, null);
        put(F_CURRENCY, null);
        put(F_FK_CURRENCY_ID, null);
        put(F_FK_PAYMENT_TYPE_ID, null);
        put(F_PAYMENT_TYPE_NAME, null);
        put(F_FK_FREE_SCHEME_ID, null);
        put(F_FREE_SCHEME_NAME, null);
        put(F_PRODUCT_TOTAL_PRICE, null);
        put(F_PRODUCT_DISCOUNT_PRICE, null);
        put(F_FREE_SCHEME_TOTAL_PRICE, null);
        put(F_PAYMENT_TOTAL_PRICE, null);
        put(F_FK_COUPON_ACTIVITY_ID, null);
        put(F_COUPON_ACTIVITY_NAME, null);
        put(F_COUPON_CODE, null);
        put(F_COUPON_DISCOUNT_TYPE_NAME, null);
        put(F_COUPON_DISCOUNT_VALUE, null);
        put(F_FK_REDUCE_ACTIVITY_ID, null);
        put(F_REDUCE_ACTIVITY_NAME, null);
        put(F_REDUCE_DISCOUNT_TYPE_NAME, null);
        put(F_REDUCE_DISCOUNT_VALUE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_IS_RECALL, null);
        put(F_IS_NEED_RECALL, null);
        put(F_IS_DISCARD, null);
    }

    public OrdBalanceTheBooks() {
        super();
    }

    public OrdBalanceTheBooks(Map<String, Object> map) {
        super(map);
    }

    public OrdBalanceTheBooks(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 编号<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 编号 set
     */
    public OrdBalanceTheBooks setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return shopping_cart_list to shoppingCartList 购物车编号集合<BR/>
     */
    public String getShoppingCartList() {
        return getTypedValue(F_SHOPPING_CART_LIST, String.class);
    }
    /**
     * @param shoppingCartList to shopping_cart_list 购物车编号集合 set
     */
    public OrdBalanceTheBooks setShoppingCartList(String shoppingCartList) {
        set(F_SHOPPING_CART_LIST, shoppingCartList);
        return this;
    }
    /**
     * @return balance_the_books_no to balanceTheBooksNo 结算单编号<BR/>
     */
    public String getBalanceTheBooksNo() {
        return getTypedValue(F_BALANCE_THE_BOOKS_NO, String.class);
    }
    /**
     * @param balanceTheBooksNo to balance_the_books_no 结算单编号 set
     */
    public OrdBalanceTheBooks setBalanceTheBooksNo(String balanceTheBooksNo) {
        set(F_BALANCE_THE_BOOKS_NO, balanceTheBooksNo);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 客户编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 客户编号 set
     */
    public OrdBalanceTheBooks setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_name to clientName 客户名称<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 客户名称 set
     */
    public OrdBalanceTheBooks setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return fk_clientele_receiver_address_id to fkClienteleReceiverAddressId 收货地址编号<BR/>
     */
    public String getFkClienteleReceiverAddressId() {
        return getTypedValue(F_FK_CLIENTELE_RECEIVER_ADDRESS_ID, String.class);
    }
    /**
     * @param fkClienteleReceiverAddressId to fk_clientele_receiver_address_id 收货地址编号 set
     */
    public OrdBalanceTheBooks setFkClienteleReceiverAddressId(String fkClienteleReceiverAddressId) {
        set(F_FK_CLIENTELE_RECEIVER_ADDRESS_ID, fkClienteleReceiverAddressId);
        return this;
    }
    /**
     * @return fk_ord_invoice_address_id to fkOrdInvoiceAddressId 发票地址<BR/>
     */
    public String getFkOrdInvoiceAddressId() {
        return getTypedValue(F_FK_ORD_INVOICE_ADDRESS_ID, String.class);
    }
    /**
     * @param fkOrdInvoiceAddressId to fk_ord_invoice_address_id 发票地址 set
     */
    public OrdBalanceTheBooks setFkOrdInvoiceAddressId(String fkOrdInvoiceAddressId) {
        set(F_FK_ORD_INVOICE_ADDRESS_ID, fkOrdInvoiceAddressId);
        return this;
    }
    /**
     * @return fk_sys_country_id to fkSysCountryId 收货国家编号<BR/>
     */
    public String getFkSysCountryId() {
        return getTypedValue(F_FK_SYS_COUNTRY_ID, String.class);
    }
    /**
     * @param fkSysCountryId to fk_sys_country_id 收货国家编号 set
     */
    public OrdBalanceTheBooks setFkSysCountryId(String fkSysCountryId) {
        set(F_FK_SYS_COUNTRY_ID, fkSysCountryId);
        return this;
    }
    /**
     * @return country_name to countryName 收货国家名称<BR/>
     */
    public String getCountryName() {
        return getTypedValue(F_COUNTRY_NAME, String.class);
    }
    /**
     * @param countryName to country_name 收货国家名称 set
     */
    public OrdBalanceTheBooks setCountryName(String countryName) {
        set(F_COUNTRY_NAME, countryName);
        return this;
    }
    /**
     * @return tax_rate to taxRate 国家税费<BR/>
     */
    public Long getTaxRate() {
        return getTypedValue(F_TAX_RATE, Long.class);
    }
    /**
     * @param taxRate to tax_rate 国家税费 set
     */
    public OrdBalanceTheBooks setTaxRate(Long taxRate) {
        set(F_TAX_RATE, taxRate);
        return this;
    }
    /**
     * @return total_product_number to totalProductNumber 商品数量<BR/>
     */
    public Integer getTotalProductNumber() {
        return getTypedValue(F_TOTAL_PRODUCT_NUMBER, Integer.class);
    }
    /**
     * @param totalProductNumber to total_product_number 商品数量 set
     */
    public OrdBalanceTheBooks setTotalProductNumber(Integer totalProductNumber) {
        set(F_TOTAL_PRODUCT_NUMBER, totalProductNumber);
        return this;
    }
    /**
     * @return total_weight to totalWeight 商品总重量<BR/>
     */
    public Long getTotalWeight() {
        return getTypedValue(F_TOTAL_WEIGHT, Long.class);
    }
    /**
     * @param totalWeight to total_weight 商品总重量 set
     */
    public OrdBalanceTheBooks setTotalWeight(Long totalWeight) {
        set(F_TOTAL_WEIGHT, totalWeight);
        return this;
    }
    /**
     * @return currency to currency 币制<BR/>
     */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
     * @param currency to currency 币制 set
     */
    public OrdBalanceTheBooks setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
     * @return fk_currency_id to fkCurrencyId 币种id<BR/>
     */
    public String getFkCurrencyId() {
        return getTypedValue(F_FK_CURRENCY_ID, String.class);
    }
    /**
     * @param fkCurrencyId to fk_currency_id 币种id set
     */
    public OrdBalanceTheBooks setFkCurrencyId(String fkCurrencyId) {
        set(F_FK_CURRENCY_ID, fkCurrencyId);
        return this;
    }
    /**
     * @return fk_payment_type_id to fkPaymentTypeId 支付方式编号<BR/>
     */
    public String getFkPaymentTypeId() {
        return getTypedValue(F_FK_PAYMENT_TYPE_ID, String.class);
    }
    /**
     * @param fkPaymentTypeId to fk_payment_type_id 支付方式编号 set
     */
    public OrdBalanceTheBooks setFkPaymentTypeId(String fkPaymentTypeId) {
        set(F_FK_PAYMENT_TYPE_ID, fkPaymentTypeId);
        return this;
    }
    /**
     * @return payment_type_name to paymentTypeName 支付方式名称<BR/>
     */
    public String getPaymentTypeName() {
        return getTypedValue(F_PAYMENT_TYPE_NAME, String.class);
    }
    /**
     * @param paymentTypeName to payment_type_name 支付方式名称 set
     */
    public OrdBalanceTheBooks setPaymentTypeName(String paymentTypeName) {
        set(F_PAYMENT_TYPE_NAME, paymentTypeName);
        return this;
    }
    /**
     * @return fk_free_scheme_id to fkFreeSchemeId 物流费用方案编号<BR/>
     */
    public String getFkFreeSchemeId() {
        return getTypedValue(F_FK_FREE_SCHEME_ID, String.class);
    }
    /**
     * @param fkFreeSchemeId to fk_free_scheme_id 物流费用方案编号 set
     */
    public OrdBalanceTheBooks setFkFreeSchemeId(String fkFreeSchemeId) {
        set(F_FK_FREE_SCHEME_ID, fkFreeSchemeId);
        return this;
    }
    /**
     * @return free_scheme_name to freeSchemeName 物流费用方案名称<BR/>
     */
    public String getFreeSchemeName() {
        return getTypedValue(F_FREE_SCHEME_NAME, String.class);
    }
    /**
     * @param freeSchemeName to free_scheme_name 物流费用方案名称 set
     */
    public OrdBalanceTheBooks setFreeSchemeName(String freeSchemeName) {
        set(F_FREE_SCHEME_NAME, freeSchemeName);
        return this;
    }
    /**
     * @return product_total_price to productTotalPrice 商品总价格<BR/>
     */
    public Long getProductTotalPrice() {
        return getTypedValue(F_PRODUCT_TOTAL_PRICE, Long.class);
    }
    /**
     * @param productTotalPrice to product_total_price 商品总价格 set
     */
    public OrdBalanceTheBooks setProductTotalPrice(Long productTotalPrice) {
        set(F_PRODUCT_TOTAL_PRICE, productTotalPrice);
        return this;
    }
    /**
     * @return product_discount_price to productDiscountPrice 商品优惠后总价格<BR/>
     */
    public Long getProductDiscountPrice() {
        return getTypedValue(F_PRODUCT_DISCOUNT_PRICE, Long.class);
    }
    /**
     * @param productDiscountPrice to product_discount_price 商品优惠后总价格 set
     */
    public OrdBalanceTheBooks setProductDiscountPrice(Long productDiscountPrice) {
        set(F_PRODUCT_DISCOUNT_PRICE, productDiscountPrice);
        return this;
    }
    /**
     * @return free_scheme_total_price to freeSchemeTotalPrice 物流费用费用价格<BR/>
     */
    public Long getFreeSchemeTotalPrice() {
        return getTypedValue(F_FREE_SCHEME_TOTAL_PRICE, Long.class);
    }
    /**
     * @param freeSchemeTotalPrice to free_scheme_total_price 物流费用费用价格 set
     */
    public OrdBalanceTheBooks setFreeSchemeTotalPrice(Long freeSchemeTotalPrice) {
        set(F_FREE_SCHEME_TOTAL_PRICE, freeSchemeTotalPrice);
        return this;
    }
    /**
     * @return payment_total_price to paymentTotalPrice 待支付总价格<BR/>
     */
    public Long getPaymentTotalPrice() {
        return getTypedValue(F_PAYMENT_TOTAL_PRICE, Long.class);
    }
    /**
     * @param paymentTotalPrice to payment_total_price 待支付总价格 set
     */
    public OrdBalanceTheBooks setPaymentTotalPrice(Long paymentTotalPrice) {
        set(F_PAYMENT_TOTAL_PRICE, paymentTotalPrice);
        return this;
    }
    /**
     * @return fk_coupon_activity_id to fkCouponActivityId 优惠卷活动编号<BR/>
     */
    public String getFkCouponActivityId() {
        return getTypedValue(F_FK_COUPON_ACTIVITY_ID, String.class);
    }
    /**
     * @param fkCouponActivityId to fk_coupon_activity_id 优惠卷活动编号 set
     */
    public OrdBalanceTheBooks setFkCouponActivityId(String fkCouponActivityId) {
        set(F_FK_COUPON_ACTIVITY_ID, fkCouponActivityId);
        return this;
    }
    /**
     * @return coupon_activity_name to couponActivityName 优惠卷活动名称<BR/>
     */
    public String getCouponActivityName() {
        return getTypedValue(F_COUPON_ACTIVITY_NAME, String.class);
    }
    /**
     * @param couponActivityName to coupon_activity_name 优惠卷活动名称 set
     */
    public OrdBalanceTheBooks setCouponActivityName(String couponActivityName) {
        set(F_COUPON_ACTIVITY_NAME, couponActivityName);
        return this;
    }
    /**
     * @return coupon_code to couponCode 优惠卷码<BR/>
     */
    public String getCouponCode() {
        return getTypedValue(F_COUPON_CODE, String.class);
    }
    /**
     * @param couponCode to coupon_code 优惠卷码 set
     */
    public OrdBalanceTheBooks setCouponCode(String couponCode) {
        set(F_COUPON_CODE, couponCode);
        return this;
    }
    /**
     * @return coupon_discount_type_name to couponDiscountTypeName 优惠卷优惠类型名称(折扣、抵扣)<BR/>
     */
    public String getCouponDiscountTypeName() {
        return getTypedValue(F_COUPON_DISCOUNT_TYPE_NAME, String.class);
    }
    /**
     * @param couponDiscountTypeName to coupon_discount_type_name 优惠卷优惠类型名称(折扣、抵扣) set
     */
    public OrdBalanceTheBooks setCouponDiscountTypeName(String couponDiscountTypeName) {
        set(F_COUPON_DISCOUNT_TYPE_NAME, couponDiscountTypeName);
        return this;
    }
    /**
     * @return coupon_discount_value to couponDiscountValue 优惠卷优惠数据<BR/>
     */
    public Long getCouponDiscountValue() {
        return getTypedValue(F_COUPON_DISCOUNT_VALUE, Long.class);
    }
    /**
     * @param couponDiscountValue to coupon_discount_value 优惠卷优惠数据 set
     */
    public OrdBalanceTheBooks setCouponDiscountValue(Long couponDiscountValue) {
        set(F_COUPON_DISCOUNT_VALUE, couponDiscountValue);
        return this;
    }
    /**
     * @return fk_reduce_activity_id to fkReduceActivityId 满减活动编号<BR/>
     */
    public String getFkReduceActivityId() {
        return getTypedValue(F_FK_REDUCE_ACTIVITY_ID, String.class);
    }
    /**
     * @param fkReduceActivityId to fk_reduce_activity_id 满减活动编号 set
     */
    public OrdBalanceTheBooks setFkReduceActivityId(String fkReduceActivityId) {
        set(F_FK_REDUCE_ACTIVITY_ID, fkReduceActivityId);
        return this;
    }
    /**
     * @return reduce_activity_name to reduceActivityName 满减活动名称<BR/>
     */
    public String getReduceActivityName() {
        return getTypedValue(F_REDUCE_ACTIVITY_NAME, String.class);
    }
    /**
     * @param reduceActivityName to reduce_activity_name 满减活动名称 set
     */
    public OrdBalanceTheBooks setReduceActivityName(String reduceActivityName) {
        set(F_REDUCE_ACTIVITY_NAME, reduceActivityName);
        return this;
    }
    /**
     * @return reduce_discount_type_name to reduceDiscountTypeName 满减优惠类型名称(折扣、抵扣)<BR/>
     */
    public String getReduceDiscountTypeName() {
        return getTypedValue(F_REDUCE_DISCOUNT_TYPE_NAME, String.class);
    }
    /**
     * @param reduceDiscountTypeName to reduce_discount_type_name 满减优惠类型名称(折扣、抵扣) set
     */
    public OrdBalanceTheBooks setReduceDiscountTypeName(String reduceDiscountTypeName) {
        set(F_REDUCE_DISCOUNT_TYPE_NAME, reduceDiscountTypeName);
        return this;
    }
    /**
     * @return reduce_discount_value to reduceDiscountValue 满减优惠数据<BR/>
     */
    public Long getReduceDiscountValue() {
        return getTypedValue(F_REDUCE_DISCOUNT_VALUE, Long.class);
    }
    /**
     * @param reduceDiscountValue to reduce_discount_value 满减优惠数据 set
     */
    public OrdBalanceTheBooks setReduceDiscountValue(Long reduceDiscountValue) {
        set(F_REDUCE_DISCOUNT_VALUE, reduceDiscountValue);
        return this;
    }
    /**
     * @return remark to remark 备注<BR/>
     */
    public String getRemark() {
        return getTypedValue(F_REMARK, String.class);
    }
    /**
     * @param remark to remark 备注 set
     */
    public OrdBalanceTheBooks setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }
    /**
     * @return add_time to addTime 添加时间<BR/>
     */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
     * @param addTime to add_time 添加时间 set
     */
    public OrdBalanceTheBooks setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return add_user_id to addUserId 添加人编号<BR/>
     */
    public String getAddUserId() {
        return getTypedValue(F_ADD_USER_ID, String.class);
    }
    /**
     * @param addUserId to add_user_id 添加人编号 set
     */
    public OrdBalanceTheBooks setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
     * @return add_user_name to addUserName 添加人名称<BR/>
     */
    public String getAddUserName() {
        return getTypedValue(F_ADD_USER_NAME, String.class);
    }
    /**
     * @param addUserName to add_user_name 添加人名称 set
     */
    public OrdBalanceTheBooks setAddUserName(String addUserName) {
        set(F_ADD_USER_NAME, addUserName);
        return this;
    }
    /**
     * @return operation_time to operationTime 操作时间<BR/>
     */
    public Long getOperationTime() {
        return getTypedValue(F_OPERATION_TIME, Long.class);
    }
    /**
     * @param operationTime to operation_time 操作时间 set
     */
    public OrdBalanceTheBooks setOperationTime(Long operationTime) {
        set(F_OPERATION_TIME, operationTime);
        return this;
    }
    /**
     * @return operation_user_id to operationUserId 操作人编号<BR/>
     */
    public String getOperationUserId() {
        return getTypedValue(F_OPERATION_USER_ID, String.class);
    }
    /**
     * @param operationUserId to operation_user_id 操作人编号 set
     */
    public OrdBalanceTheBooks setOperationUserId(String operationUserId) {
        set(F_OPERATION_USER_ID, operationUserId);
        return this;
    }
    /**
     * @return operation_user_name to operationUserName 操作人名称<BR/>
     */
    public String getOperationUserName() {
        return getTypedValue(F_OPERATION_USER_NAME, String.class);
    }
    /**
     * @param operationUserName to operation_user_name 操作人名称 set
     */
    public OrdBalanceTheBooks setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
     * @return is_recall to isRecall 0.否 1.是 是否召回成功<BR/>
     */
    public Integer getIsRecall() {
        return getTypedValue(F_IS_RECALL, Integer.class);
    }
    /**
     * @param isRecall to is_recall 0.否 1.是 是否召回成功 set
     */
    public OrdBalanceTheBooks setIsRecall(Integer isRecall) {
        set(F_IS_RECALL, isRecall);
        return this;
    }
    /**
     * @return is_need_recall to isNeedRecall 是否需要召回<BR/>
     */
    public Integer getIsNeedRecall() {
        return getTypedValue(F_IS_NEED_RECALL, Integer.class);
    }
    /**
     * @param isNeedRecall to is_need_recall 是否需要召回 set
     */
    public OrdBalanceTheBooks setIsNeedRecall(Integer isNeedRecall) {
        set(F_IS_NEED_RECALL, isNeedRecall);
        return this;
    }
    /**
     * @return is_discard to isDiscard 是否废弃<BR/>
     */
    public Integer getIsDiscard() {
        return getTypedValue(F_IS_DISCARD, Integer.class);
    }
    /**
     * @param isDiscard to is_discard 是否废弃 set
     */
    public OrdBalanceTheBooks setIsDiscard(Integer isDiscard) {
        set(F_IS_DISCARD, isDiscard);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdBalanceTheBooks setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdBalanceTheBooks me(){
        return new OrdBalanceTheBooks();
    }

    private static class Mapper implements RowMapper<OrdBalanceTheBooks> {

        private Supplier<OrdBalanceTheBooks> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdBalanceTheBooks mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdBalanceTheBooks bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setShoppingCartList(Utils.toCast(rs.getObject(F_SHOPPING_CART_LIST), String.class));
            bean.setBalanceTheBooksNo(Utils.toCast(rs.getObject(F_BALANCE_THE_BOOKS_NO), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setClientName(Utils.toCast(rs.getObject(F_CLIENT_NAME), String.class));
            bean.setFkClienteleReceiverAddressId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_RECEIVER_ADDRESS_ID), String.class));
            bean.setFkOrdInvoiceAddressId(Utils.toCast(rs.getObject(F_FK_ORD_INVOICE_ADDRESS_ID), String.class));
            bean.setFkSysCountryId(Utils.toCast(rs.getObject(F_FK_SYS_COUNTRY_ID), String.class));
            bean.setCountryName(Utils.toCast(rs.getObject(F_COUNTRY_NAME), String.class));
            bean.setTaxRate(Utils.toCast(rs.getObject(F_TAX_RATE), Long.class));
            bean.setTotalProductNumber(Utils.toCast(rs.getObject(F_TOTAL_PRODUCT_NUMBER), Integer.class));
            bean.setTotalWeight(Utils.toCast(rs.getObject(F_TOTAL_WEIGHT), Long.class));
            bean.setCurrency(Utils.toCast(rs.getObject(F_CURRENCY), String.class));
            bean.setFkCurrencyId(Utils.toCast(rs.getObject(F_FK_CURRENCY_ID), String.class));
            bean.setFkPaymentTypeId(Utils.toCast(rs.getObject(F_FK_PAYMENT_TYPE_ID), String.class));
            bean.setPaymentTypeName(Utils.toCast(rs.getObject(F_PAYMENT_TYPE_NAME), String.class));
            bean.setFkFreeSchemeId(Utils.toCast(rs.getObject(F_FK_FREE_SCHEME_ID), String.class));
            bean.setFreeSchemeName(Utils.toCast(rs.getObject(F_FREE_SCHEME_NAME), String.class));
            bean.setProductTotalPrice(Utils.toCast(rs.getObject(F_PRODUCT_TOTAL_PRICE), Long.class));
            bean.setProductDiscountPrice(Utils.toCast(rs.getObject(F_PRODUCT_DISCOUNT_PRICE), Long.class));
            bean.setFreeSchemeTotalPrice(Utils.toCast(rs.getObject(F_FREE_SCHEME_TOTAL_PRICE), Long.class));
            bean.setPaymentTotalPrice(Utils.toCast(rs.getObject(F_PAYMENT_TOTAL_PRICE), Long.class));
            bean.setFkCouponActivityId(Utils.toCast(rs.getObject(F_FK_COUPON_ACTIVITY_ID), String.class));
            bean.setCouponActivityName(Utils.toCast(rs.getObject(F_COUPON_ACTIVITY_NAME), String.class));
            bean.setCouponCode(Utils.toCast(rs.getObject(F_COUPON_CODE), String.class));
            bean.setCouponDiscountTypeName(Utils.toCast(rs.getObject(F_COUPON_DISCOUNT_TYPE_NAME), String.class));
            bean.setCouponDiscountValue(Utils.toCast(rs.getObject(F_COUPON_DISCOUNT_VALUE), Long.class));
            bean.setFkReduceActivityId(Utils.toCast(rs.getObject(F_FK_REDUCE_ACTIVITY_ID), String.class));
            bean.setReduceActivityName(Utils.toCast(rs.getObject(F_REDUCE_ACTIVITY_NAME), String.class));
            bean.setReduceDiscountTypeName(Utils.toCast(rs.getObject(F_REDUCE_DISCOUNT_TYPE_NAME), String.class));
            bean.setReduceDiscountValue(Utils.toCast(rs.getObject(F_REDUCE_DISCOUNT_VALUE), Long.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setIsRecall(Utils.toCast(rs.getObject(F_IS_RECALL), Integer.class));
            bean.setIsNeedRecall(Utils.toCast(rs.getObject(F_IS_NEED_RECALL), Integer.class));
            bean.setIsDiscard(Utils.toCast(rs.getObject(F_IS_DISCARD), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdBalanceTheBooks> newMapper(){
        return newMapper(OrdBalanceTheBooks::new);
    }

    public RowMapper<OrdBalanceTheBooks> newMapper(Supplier<OrdBalanceTheBooks> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdBalanceTheBooks {
        @Override
        public abstract RowMapper<OrdBalanceTheBooks> newMapper();
    }
}
