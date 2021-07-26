package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        17:38 2020/06/06
* Company:     美赞拓
* Version:     1.0
* Description: OrdSalesOrder实体
*/
@SuppressWarnings("all")
public class OrdSalesOrder extends BaseBean<OrdSalesOrder> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_sales_order";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 销售单编号
     */
    public static final String F_SALES_ORDER_NO = "sales_order_no";
    /**
     * 公司编号
     */
    public static final String F_COMPANY_ID = "company_id";
    /**
     * 公司名称
     */
    public static final String F_COMPANY_NAME = "company_name";
    /**
     * 客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 下单客户电子邮件
     */
    public static final String F_CLIENT_EMAIL = "client_email";
    /**
     * 客户联系电话
     */
    public static final String F_CLIENT_PHONE = "client_phone";
    /**
     * 收货人名称
     */
    public static final String F_RECEIVER_NAME = "receiver_name";
    /**
     * 收货人编号
     */
    public static final String F_RECEIVER_ID = "receiver_id";
    /**
     * 收货地址编号
     */
    public static final String F_FK_CLIENTELE_ADDRESS_ID = "fk_clientele_address_id";
    /**
     * 收货人电话
     */
    public static final String F_RECEIVER_PHONE = "receiver_phone";
    /**
     * 收货人电子邮件
     */
    public static final String F_RECEIVER_EMAIL = "receiver_email";
    /**
     * 收货人国家
     */
    public static final String F_RECEIVER_COUNTRY = "receiver_country";
    /**
     * 收货人省
     */
    public static final String F_RECEIVER_PROVINCE = "receiver_province";
    /**
     * 收货人城市
     */
    public static final String F_RECEIVER_CITY = "receiver_city";
    /**
     * 收货人街道
     */
    public static final String F_RECEIVER_SHEET = "receiver_sheet";
    /**
     * 收货人详细地址
     */
    public static final String F_RECEIVER_ADDRESS = "receiver_address";
    /**
     * 收货人邮编
     */
    public static final String F_RECEIVER_POST = "receiver_post";
    /**
     * 发票地址编号
     */
    public static final String F_FK_INVOICE_ADDRESS_ID = "fk_invoice_address_id";
    /**
     * 发货人名称
     */
    public static final String F_SENDER_NAME = "sender_name";
    /**
     * 发货人编号
     */
    public static final String F_SENDER_ID = "sender_id";
    /**
     * 发货地址
     */
    public static final String F_SENDER_ADDRESS = "sender_address";
    /**
     * 发货人电话
     */
    public static final String F_SENDER_PHONE = "sender_phone";
    /**
     * 发货人电子邮件
     */
    public static final String F_SENDER_EMAIL = "sender_email";
    /**
     * 总价格
     */
    public static final String F_TOTAL_PRICE = "total_price";
    /**
     * 总体积
     */
    public static final String F_TOTAL_VOL = "total_vol";
    /**
     * 总重量
     */
    public static final String F_TOTAL_WEIGHT = "total_weight";
    /**
     * 总商品数
     */
    public static final String F_TOTAL_PRODUCT_NUMBER = "total_product_number";
    /**
     * 物流渠道名称
     */
    public static final String F_TRANSPORTS_CHANNEL_NAME = "transports_channel_name";
    /**
     * 物流渠道编号
     */
    public static final String F_TRANSPORTS_CHANNEL_ID = "transports_channel_id";
    /**
     * 运单号
     */
    public static final String F_TRANSPORTS_NO = "transports_no";
    /**
     * 运费方案编号
     */
    public static final String F_TRANSPORTS_FREE_ID = "transports_free_id";
    /**
     * 运费方案名称
     */
    public static final String F_TRANSPORTS_FREE_NAME = "transports_free_name";
    /**
     * 总运费
     */
    public static final String F_TOTAL_TRANSPORTS_FREE = "total_transports_free";
    /**
     * 总支付费用
     */
    public static final String F_TOTAL_PAYMENT_FREE = "total_payment_free";
    /**
     * 支付时间
     */
    public static final String F_PAYMENT_TIME = "payment_time";
    /**
     * 支付人
     */
    public static final String F_PAYMENT_NAME = "payment_name";
    /**
     * 支付流水号
     */
    public static final String F_PAYMENT_STREAM_NO = "payment_stream_no";
    /**
     * 支付平台名称
     */
    public static final String F_PAYMENT_PLATFORM_NAME = "payment_platform_name";
    /**
     * 支付平台类型名称
     */
    public static final String F_PAYMENT_PLATFORM_TYPE_NAME = "payment_platform_type_name";
    /**
     * 支付平台类型编号
     */
    public static final String F_PAYMENT_PLATFORM_TYPE_ID = "payment_platform_type_id";
    /**
     * 支付卡号
     */
    public static final String F_PAYMENT_CARD_NO = "payment_card_no";
    /**
     * 订单状态
     待支付
     支付出错
     待发货 03
     已发货 04
     已完成 05
     已取消
     */
    public static final String F_SALES_ORDER_STATUS = "sales_order_status";
    /**
     * 订单状态 OrdSalesOrderStatusEnum枚举类
     */
    public static final String F_REFUND_REQUEST_NO = "refund_request_no";
    /**
     * 退款单流水号
     */
    public static final String F_REFUND_STREAM_NO = "refund_stream_no";
    /**
     * 退款单截图地址
     */
    public static final String F_REFUND_STREAM_IMAGE_URL = "refund_stream_image_url";
    /**
     * 满减活动编号
     */
    public static final String F_REDUCE_ACTIVITY_ID = "reduce_activity_id";
    /**
     * 满减活动名称
     */
    public static final String F_REDUCE_ACTIVITY_NAME = "reduce_activity_name";
    /**
     * 满减活动结果
     */
    public static final String F_REDUCE_ACTIVITY_RESULT = "reduce_activity_result";
    /**
     * 优惠卷活动编号
     */
    public static final String F_COUPON_ACTIVITY_ID = "coupon_activity_id";
    /**
     * 优惠卷活动名称
     */
    public static final String F_COUPON_ACTIVITY_NAME = "coupon_activity_name";
    /**
     * 优惠卷活动结果
     */
    public static final String F_COUPON_ACTIVITY_RESULT = "coupon_activity_result";
    /**
     * 限时促销活动编号
     */
    public static final String F_PROMOTION_ACTIVITY_ID = "promotion_activity_id";
    /**
     * 限时促销活动名称
     */
    public static final String F_PROMOTION_ACTIVITY_NAME = "promotion_activity_name";
    /**
     * 限时促销活动结果
     */
    public static final String F_PROMOTION_ACTIVITY_RESULT = "promotion_activity_result";
    /**
     * 员工编号
     */
    public static final String F_FK_EMPLOYEE_ID = "fk_employee_id";
    /**
     * 员工名称
     */
    public static final String F_EMPLOYEE_NAME = "employee_name";
    /**
     * 税率
     */
    public static final String F_TAX_RATE = "tax_rate";
    /**
     * 税额
     */
    public static final String F_TAX_AMOUNT = "tax_amount";
    /**
     * 免税全额
     */
    public static final String F_NOT_TAX_AMOUNT = "not_tax_amount";
    /**
     * 同级排序
     */
    public static final String F_SORT = "sort";
    /**
     * SEO-标题
     */
    public static final String F_SEO_TITLE = "seo_title";
    /**
     * SEO-关键词
     */
    public static final String F_SEO_KEYWORDS = "seo_keywords";
    /**
     * SEO-概述
     */
    public static final String F_SEO_DESCRIPTION = "seo_description";
    /**
     * SEO-地址
     */
    public static final String F_SEO_ADDRESS = "seo_address";
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
     * 币种
     */
    public static final String F_CURRENCY = "currency";
    /**
     * 币种ID
     */
    public static final String F_FK_CURRENCY_ID = "fk_currency_id";
    /**
     * 召回邮件状态(01:未发送；02:已发送)
     */
    public static final String F_CALLBACK_EMAIL_STATUS = "callback_email_status";
    /**
     * 订单召回状态(01:未召回；02:已召回；03:召回失败)
     */
    public static final String F_ORDER_CALLBACK_STATUS = "order_callback_status";
    /**
     * 商品扣后总价格
     */
    public static final String F_PRODUCT_DISCOUNT_PRICE = "product_discount_price";
    /**
     *
     */
    public static final String F_LOCALHOST_SN = "localhost_sn";
    /**
     * 标签编号
     */
    public static final String F_FK_TAG_ID = "fk_tag_id";
    /**
     * 是否启用
     */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SALES_ORDER_NO, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_CLIENT_EMAIL, null);
        put(F_CLIENT_PHONE, null);
        put(F_RECEIVER_NAME, null);
        put(F_RECEIVER_ID, null);
        put(F_FK_CLIENTELE_ADDRESS_ID, null);
        put(F_RECEIVER_PHONE, null);
        put(F_RECEIVER_EMAIL, null);
        put(F_RECEIVER_COUNTRY, null);
        put(F_RECEIVER_PROVINCE, null);
        put(F_RECEIVER_CITY, null);
        put(F_RECEIVER_SHEET, null);
        put(F_RECEIVER_ADDRESS, null);
        put(F_RECEIVER_POST, null);
        put(F_FK_INVOICE_ADDRESS_ID, null);
        put(F_SENDER_NAME, null);
        put(F_SENDER_ID, null);
        put(F_SENDER_ADDRESS, null);
        put(F_SENDER_PHONE, null);
        put(F_SENDER_EMAIL, null);
        put(F_TOTAL_PRICE, null);
        put(F_TOTAL_VOL, null);
        put(F_TOTAL_WEIGHT, null);
        put(F_TOTAL_PRODUCT_NUMBER, null);
        put(F_TRANSPORTS_CHANNEL_NAME, null);
        put(F_TRANSPORTS_CHANNEL_ID, null);
        put(F_TRANSPORTS_NO, null);
        put(F_TRANSPORTS_FREE_ID, null);
        put(F_TRANSPORTS_FREE_NAME, null);
        put(F_TOTAL_TRANSPORTS_FREE, null);
        put(F_TOTAL_PAYMENT_FREE, null);
        put(F_PAYMENT_TIME, null);
        put(F_PAYMENT_NAME, null);
        put(F_PAYMENT_STREAM_NO, null);
        put(F_PAYMENT_PLATFORM_NAME, null);
        put(F_PAYMENT_PLATFORM_TYPE_NAME, null);
        put(F_PAYMENT_PLATFORM_TYPE_ID, null);
        put(F_PAYMENT_CARD_NO, null);
        put(F_SALES_ORDER_STATUS, null);
        put(F_REFUND_REQUEST_NO, null);
        put(F_REFUND_STREAM_NO, null);
        put(F_REFUND_STREAM_IMAGE_URL, null);
        put(F_REDUCE_ACTIVITY_ID, null);
        put(F_REDUCE_ACTIVITY_NAME, null);
        put(F_REDUCE_ACTIVITY_RESULT, null);
        put(F_COUPON_ACTIVITY_ID, null);
        put(F_COUPON_ACTIVITY_NAME, null);
        put(F_COUPON_ACTIVITY_RESULT, null);
        put(F_PROMOTION_ACTIVITY_ID, null);
        put(F_PROMOTION_ACTIVITY_NAME, null);
        put(F_PROMOTION_ACTIVITY_RESULT, null);
        put(F_FK_EMPLOYEE_ID, null);
        put(F_EMPLOYEE_NAME, null);
        put(F_TAX_RATE, null);
        put(F_TAX_AMOUNT, null);
        put(F_NOT_TAX_AMOUNT, null);
        put(F_SORT, null);
        put(F_SEO_TITLE, null);
        put(F_SEO_KEYWORDS, null);
        put(F_SEO_DESCRIPTION, null);
        put(F_SEO_ADDRESS, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_CURRENCY, null);
        put(F_FK_CURRENCY_ID, null);
        put(F_CALLBACK_EMAIL_STATUS, null);
        put(F_ORDER_CALLBACK_STATUS, null);
        put(F_PRODUCT_DISCOUNT_PRICE, null);
        put(F_LOCALHOST_SN, null);
        put(F_FK_TAG_ID, null);
        put(F_IS_ENABLE, null);
    }

    public OrdSalesOrder() {
        super();
    }

    public OrdSalesOrder(Map<String, Object> map) {
        super(map);
    }

    public OrdSalesOrder(String id) {
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
    public OrdSalesOrder setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return sales_order_no to salesOrderNo 销售单编号<BR/>
     */
    public String getSalesOrderNo() {
        return getTypedValue(F_SALES_ORDER_NO, String.class);
    }
    /**
     * @param salesOrderNo to sales_order_no 销售单编号 set
     */
    public OrdSalesOrder setSalesOrderNo(String salesOrderNo) {
        set(F_SALES_ORDER_NO, salesOrderNo);
        return this;
    }
    /**
     * @return company_id to companyId 公司编号<BR/>
     */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
     * @param companyId to company_id 公司编号 set
     */
    public OrdSalesOrder setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
     * @return company_name to companyName 公司名称<BR/>
     */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
     * @param companyName to company_name 公司名称 set
     */
    public OrdSalesOrder setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
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
    public OrdSalesOrder setFkClienteleId(String fkClienteleId) {
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
    public OrdSalesOrder setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return client_email to clientEmail 下单客户电子邮件<BR/>
     */
    public String getClientEmail() {
        return getTypedValue(F_CLIENT_EMAIL, String.class);
    }
    /**
     * @param clientEmail to client_email 下单客户电子邮件 set
     */
    public OrdSalesOrder setClientEmail(String clientEmail) {
        set(F_CLIENT_EMAIL, clientEmail);
        return this;
    }
    /**
     * @return client_phone to clientPhone 客户联系电话<BR/>
     */
    public String getClientPhone() {
        return getTypedValue(F_CLIENT_PHONE, String.class);
    }
    /**
     * @param clientPhone to client_phone 客户联系电话 set
     */
    public OrdSalesOrder setClientPhone(String clientPhone) {
        set(F_CLIENT_PHONE, clientPhone);
        return this;
    }
    /**
     * @return receiver_name to receiverName 收货人名称<BR/>
     */
    public String getReceiverName() {
        return getTypedValue(F_RECEIVER_NAME, String.class);
    }
    /**
     * @param receiverName to receiver_name 收货人名称 set
     */
    public OrdSalesOrder setReceiverName(String receiverName) {
        set(F_RECEIVER_NAME, receiverName);
        return this;
    }
    /**
     * @return receiver_id to receiverId 收货人编号<BR/>
     */
    public String getReceiverId() {
        return getTypedValue(F_RECEIVER_ID, String.class);
    }
    /**
     * @param receiverId to receiver_id 收货人编号 set
     */
    public OrdSalesOrder setReceiverId(String receiverId) {
        set(F_RECEIVER_ID, receiverId);
        return this;
    }
    /**
     * @return fk_clientele_address_id to fkClienteleAddressId 收货地址编号<BR/>
     */
    public String getFkClienteleAddressId() {
        return getTypedValue(F_FK_CLIENTELE_ADDRESS_ID, String.class);
    }
    /**
     * @param fkClienteleAddressId to fk_clientele_address_id 收货地址编号 set
     */
    public OrdSalesOrder setFkClienteleAddressId(String fkClienteleAddressId) {
        set(F_FK_CLIENTELE_ADDRESS_ID, fkClienteleAddressId);
        return this;
    }
    /**
     * @return receiver_phone to receiverPhone 收货人电话<BR/>
     */
    public String getReceiverPhone() {
        return getTypedValue(F_RECEIVER_PHONE, String.class);
    }
    /**
     * @param receiverPhone to receiver_phone 收货人电话 set
     */
    public OrdSalesOrder setReceiverPhone(String receiverPhone) {
        set(F_RECEIVER_PHONE, receiverPhone);
        return this;
    }
    /**
     * @return receiver_email to receiverEmail 收货人电子邮件<BR/>
     */
    public String getReceiverEmail() {
        return getTypedValue(F_RECEIVER_EMAIL, String.class);
    }
    /**
     * @param receiverEmail to receiver_email 收货人电子邮件 set
     */
    public OrdSalesOrder setReceiverEmail(String receiverEmail) {
        set(F_RECEIVER_EMAIL, receiverEmail);
        return this;
    }
    /**
     * @return receiver_country to receiverCountry 收货人国家<BR/>
     */
    public String getReceiverCountry() {
        return getTypedValue(F_RECEIVER_COUNTRY, String.class);
    }
    /**
     * @param receiverCountry to receiver_country 收货人国家 set
     */
    public OrdSalesOrder setReceiverCountry(String receiverCountry) {
        set(F_RECEIVER_COUNTRY, receiverCountry);
        return this;
    }
    /**
     * @return receiver_province to receiverProvince 收货人省<BR/>
     */
    public String getReceiverProvince() {
        return getTypedValue(F_RECEIVER_PROVINCE, String.class);
    }
    /**
     * @param receiverProvince to receiver_province 收货人省 set
     */
    public OrdSalesOrder setReceiverProvince(String receiverProvince) {
        set(F_RECEIVER_PROVINCE, receiverProvince);
        return this;
    }
    /**
     * @return receiver_city to receiverCity 收货人城市<BR/>
     */
    public String getReceiverCity() {
        return getTypedValue(F_RECEIVER_CITY, String.class);
    }
    /**
     * @param receiverCity to receiver_city 收货人城市 set
     */
    public OrdSalesOrder setReceiverCity(String receiverCity) {
        set(F_RECEIVER_CITY, receiverCity);
        return this;
    }
    /**
     * @return receiver_sheet to receiverSheet 收货人街道<BR/>
     */
    public String getReceiverSheet() {
        return getTypedValue(F_RECEIVER_SHEET, String.class);
    }
    /**
     * @param receiverSheet to receiver_sheet 收货人街道 set
     */
    public OrdSalesOrder setReceiverSheet(String receiverSheet) {
        set(F_RECEIVER_SHEET, receiverSheet);
        return this;
    }
    /**
     * @return receiver_address to receiverAddress 收货人详细地址<BR/>
     */
    public String getReceiverAddress() {
        return getTypedValue(F_RECEIVER_ADDRESS, String.class);
    }
    /**
     * @param receiverAddress to receiver_address 收货人详细地址 set
     */
    public OrdSalesOrder setReceiverAddress(String receiverAddress) {
        set(F_RECEIVER_ADDRESS, receiverAddress);
        return this;
    }
    /**
     * @return receiver_post to receiverPost 收货人邮编<BR/>
     */
    public String getReceiverPost() {
        return getTypedValue(F_RECEIVER_POST, String.class);
    }
    /**
     * @param receiverPost to receiver_post 收货人邮编 set
     */
    public OrdSalesOrder setReceiverPost(String receiverPost) {
        set(F_RECEIVER_POST, receiverPost);
        return this;
    }
    /**
     * @return fk_invoice_address_id to fkInvoiceAddressId 发票地址编号<BR/>
     */
    public String getFkInvoiceAddressId() {
        return getTypedValue(F_FK_INVOICE_ADDRESS_ID, String.class);
    }
    /**
     * @param fkInvoiceAddressId to fk_invoice_address_id 发票地址编号 set
     */
    public OrdSalesOrder setFkInvoiceAddressId(String fkInvoiceAddressId) {
        set(F_FK_INVOICE_ADDRESS_ID, fkInvoiceAddressId);
        return this;
    }
    /**
     * @return sender_name to senderName 发货人名称<BR/>
     */
    public String getSenderName() {
        return getTypedValue(F_SENDER_NAME, String.class);
    }
    /**
     * @param senderName to sender_name 发货人名称 set
     */
    public OrdSalesOrder setSenderName(String senderName) {
        set(F_SENDER_NAME, senderName);
        return this;
    }
    /**
     * @return sender_id to senderId 发货人编号<BR/>
     */
    public String getSenderId() {
        return getTypedValue(F_SENDER_ID, String.class);
    }
    /**
     * @param senderId to sender_id 发货人编号 set
     */
    public OrdSalesOrder setSenderId(String senderId) {
        set(F_SENDER_ID, senderId);
        return this;
    }
    /**
     * @return sender_address to senderAddress 发货地址<BR/>
     */
    public String getSenderAddress() {
        return getTypedValue(F_SENDER_ADDRESS, String.class);
    }
    /**
     * @param senderAddress to sender_address 发货地址 set
     */
    public OrdSalesOrder setSenderAddress(String senderAddress) {
        set(F_SENDER_ADDRESS, senderAddress);
        return this;
    }
    /**
     * @return sender_phone to senderPhone 发货人电话<BR/>
     */
    public String getSenderPhone() {
        return getTypedValue(F_SENDER_PHONE, String.class);
    }
    /**
     * @param senderPhone to sender_phone 发货人电话 set
     */
    public OrdSalesOrder setSenderPhone(String senderPhone) {
        set(F_SENDER_PHONE, senderPhone);
        return this;
    }
    /**
     * @return sender_email to senderEmail 发货人电子邮件<BR/>
     */
    public String getSenderEmail() {
        return getTypedValue(F_SENDER_EMAIL, String.class);
    }
    /**
     * @param senderEmail to sender_email 发货人电子邮件 set
     */
    public OrdSalesOrder setSenderEmail(String senderEmail) {
        set(F_SENDER_EMAIL, senderEmail);
        return this;
    }
    /**
     * @return total_price to totalPrice 总价格<BR/>
     */
    public Long getTotalPrice() {
        return getTypedValue(F_TOTAL_PRICE, Long.class);
    }
    /**
     * @param totalPrice to total_price 总价格 set
     */
    public OrdSalesOrder setTotalPrice(Long totalPrice) {
        set(F_TOTAL_PRICE, totalPrice);
        return this;
    }
    /**
     * @return total_vol to totalVol 总体积<BR/>
     */
    public Long getTotalVol() {
        return getTypedValue(F_TOTAL_VOL, Long.class);
    }
    /**
     * @param totalVol to total_vol 总体积 set
     */
    public OrdSalesOrder setTotalVol(Long totalVol) {
        set(F_TOTAL_VOL, totalVol);
        return this;
    }
    /**
     * @return total_weight to totalWeight 总重量<BR/>
     */
    public Long getTotalWeight() {
        return getTypedValue(F_TOTAL_WEIGHT, Long.class);
    }
    /**
     * @param totalWeight to total_weight 总重量 set
     */
    public OrdSalesOrder setTotalWeight(Long totalWeight) {
        set(F_TOTAL_WEIGHT, totalWeight);
        return this;
    }
    /**
     * @return total_product_number to totalProductNumber 总商品数<BR/>
     */
    public Integer getTotalProductNumber() {
        return getTypedValue(F_TOTAL_PRODUCT_NUMBER, Integer.class);
    }
    /**
     * @param totalProductNumber to total_product_number 总商品数 set
     */
    public OrdSalesOrder setTotalProductNumber(Integer totalProductNumber) {
        set(F_TOTAL_PRODUCT_NUMBER, totalProductNumber);
        return this;
    }
    /**
     * @return transports_channel_name to transportsChannelName 物流渠道名称<BR/>
     */
    public String getTransportsChannelName() {
        return getTypedValue(F_TRANSPORTS_CHANNEL_NAME, String.class);
    }
    /**
     * @param transportsChannelName to transports_channel_name 物流渠道名称 set
     */
    public OrdSalesOrder setTransportsChannelName(String transportsChannelName) {
        set(F_TRANSPORTS_CHANNEL_NAME, transportsChannelName);
        return this;
    }
    /**
     * @return transports_channel_id to transportsChannelId 物流渠道编号<BR/>
     */
    public String getTransportsChannelId() {
        return getTypedValue(F_TRANSPORTS_CHANNEL_ID, String.class);
    }
    /**
     * @param transportsChannelId to transports_channel_id 物流渠道编号 set
     */
    public OrdSalesOrder setTransportsChannelId(String transportsChannelId) {
        set(F_TRANSPORTS_CHANNEL_ID, transportsChannelId);
        return this;
    }
    /**
     * @return transports_no to transportsNo 运单号<BR/>
     */
    public String getTransportsNo() {
        return getTypedValue(F_TRANSPORTS_NO, String.class);
    }
    /**
     * @param transportsNo to transports_no 运单号 set
     */
    public OrdSalesOrder setTransportsNo(String transportsNo) {
        set(F_TRANSPORTS_NO, transportsNo);
        return this;
    }
    /**
     * @return transports_free_id to transportsFreeId 运费方案编号<BR/>
     */
    public String getTransportsFreeId() {
        return getTypedValue(F_TRANSPORTS_FREE_ID, String.class);
    }
    /**
     * @param transportsFreeId to transports_free_id 运费方案编号 set
     */
    public OrdSalesOrder setTransportsFreeId(String transportsFreeId) {
        set(F_TRANSPORTS_FREE_ID, transportsFreeId);
        return this;
    }
    /**
     * @return transports_free_name to transportsFreeName 运费方案名称<BR/>
     */
    public String getTransportsFreeName() {
        return getTypedValue(F_TRANSPORTS_FREE_NAME, String.class);
    }
    /**
     * @param transportsFreeName to transports_free_name 运费方案名称 set
     */
    public OrdSalesOrder setTransportsFreeName(String transportsFreeName) {
        set(F_TRANSPORTS_FREE_NAME, transportsFreeName);
        return this;
    }
    /**
     * @return total_transports_free to totalTransportsFree 总运费<BR/>
     */
    public Long getTotalTransportsFree() {
        return getTypedValue(F_TOTAL_TRANSPORTS_FREE, Long.class);
    }
    /**
     * @param totalTransportsFree to total_transports_free 总运费 set
     */
    public OrdSalesOrder setTotalTransportsFree(Long totalTransportsFree) {
        set(F_TOTAL_TRANSPORTS_FREE, totalTransportsFree);
        return this;
    }
    /**
     * @return total_payment_free to totalPaymentFree 总支付费用<BR/>
     */
    public Long getTotalPaymentFree() {
        return getTypedValue(F_TOTAL_PAYMENT_FREE, Long.class);
    }
    /**
     * @param totalPaymentFree to total_payment_free 总支付费用 set
     */
    public OrdSalesOrder setTotalPaymentFree(Long totalPaymentFree) {
        set(F_TOTAL_PAYMENT_FREE, totalPaymentFree);
        return this;
    }
    /**
     * @return payment_time to paymentTime 支付时间<BR/>
     */
    public Long getPaymentTime() {
        return getTypedValue(F_PAYMENT_TIME, Long.class);
    }
    /**
     * @param paymentTime to payment_time 支付时间 set
     */
    public OrdSalesOrder setPaymentTime(Long paymentTime) {
        set(F_PAYMENT_TIME, paymentTime);
        return this;
    }
    /**
     * @return payment_name to paymentName 支付人<BR/>
     */
    public String getPaymentName() {
        return getTypedValue(F_PAYMENT_NAME, String.class);
    }
    /**
     * @param paymentName to payment_name 支付人 set
     */
    public OrdSalesOrder setPaymentName(String paymentName) {
        set(F_PAYMENT_NAME, paymentName);
        return this;
    }
    /**
     * @return payment_stream_no to paymentStreamNo 支付流水号<BR/>
     */
    public String getPaymentStreamNo() {
        return getTypedValue(F_PAYMENT_STREAM_NO, String.class);
    }
    /**
     * @param paymentStreamNo to payment_stream_no 支付流水号 set
     */
    public OrdSalesOrder setPaymentStreamNo(String paymentStreamNo) {
        set(F_PAYMENT_STREAM_NO, paymentStreamNo);
        return this;
    }
    /**
     * @return payment_platform_name to paymentPlatformName 支付平台名称<BR/>
     */
    public String getPaymentPlatformName() {
        return getTypedValue(F_PAYMENT_PLATFORM_NAME, String.class);
    }
    /**
     * @param paymentPlatformName to payment_platform_name 支付平台名称 set
     */
    public OrdSalesOrder setPaymentPlatformName(String paymentPlatformName) {
        set(F_PAYMENT_PLATFORM_NAME, paymentPlatformName);
        return this;
    }
    /**
     * @return payment_platform_type_name to paymentPlatformTypeName 支付平台类型名称<BR/>
     */
    public String getPaymentPlatformTypeName() {
        return getTypedValue(F_PAYMENT_PLATFORM_TYPE_NAME, String.class);
    }
    /**
     * @param paymentPlatformTypeName to payment_platform_type_name 支付平台类型名称 set
     */
    public OrdSalesOrder setPaymentPlatformTypeName(String paymentPlatformTypeName) {
        set(F_PAYMENT_PLATFORM_TYPE_NAME, paymentPlatformTypeName);
        return this;
    }
    /**
     * @return payment_platform_type_id to paymentPlatformTypeId 支付平台类型编号<BR/>
     */
    public String getPaymentPlatformTypeId() {
        return getTypedValue(F_PAYMENT_PLATFORM_TYPE_ID, String.class);
    }
    /**
     * @param paymentPlatformTypeId to payment_platform_type_id 支付平台类型编号 set
     */
    public OrdSalesOrder setPaymentPlatformTypeId(String paymentPlatformTypeId) {
        set(F_PAYMENT_PLATFORM_TYPE_ID, paymentPlatformTypeId);
        return this;
    }
    /**
     * @return payment_card_no to paymentCardNo 支付卡号<BR/>
     */
    public String getPaymentCardNo() {
        return getTypedValue(F_PAYMENT_CARD_NO, String.class);
    }
    /**
     * @param paymentCardNo to payment_card_no 支付卡号 set
     */
    public OrdSalesOrder setPaymentCardNo(String paymentCardNo) {
        set(F_PAYMENT_CARD_NO, paymentCardNo);
        return this;
    }
    /**
     * @return sales_order_status to salesOrderStatus 订单状态
    待支付
    支付出错
    待发货 03
    已发货 04
    已完成 05
    已取消<BR/>
     */
    public String getSalesOrderStatus() {
        return getTypedValue(F_SALES_ORDER_STATUS, String.class);
    }
    /**
     * @param salesOrderStatus to sales_order_status 订单状态
    待支付
    支付出错
    待发货 03
    已发货 04
    已完成 05
    已取消 set
     */
    public OrdSalesOrder setSalesOrderStatus(String salesOrderStatus) {
        set(F_SALES_ORDER_STATUS, salesOrderStatus);
        return this;
    }
    /**
     * @return refund_request_no to refundRequestNo 订单状态 OrdSalesOrderStatusEnum枚举类<BR/>
     */
    public String getRefundRequestNo() {
        return getTypedValue(F_REFUND_REQUEST_NO, String.class);
    }
    /**
     * @param refundRequestNo to refund_request_no 订单状态 OrdSalesOrderStatusEnum枚举类 set
     */
    public OrdSalesOrder setRefundRequestNo(String refundRequestNo) {
        set(F_REFUND_REQUEST_NO, refundRequestNo);
        return this;
    }
    /**
     * @return refund_stream_no to refundStreamNo 退款单流水号<BR/>
     */
    public String getRefundStreamNo() {
        return getTypedValue(F_REFUND_STREAM_NO, String.class);
    }
    /**
     * @param refundStreamNo to refund_stream_no 退款单流水号 set
     */
    public OrdSalesOrder setRefundStreamNo(String refundStreamNo) {
        set(F_REFUND_STREAM_NO, refundStreamNo);
        return this;
    }
    /**
     * @return refund_stream_image_url to refundStreamImageUrl 退款单截图地址<BR/>
     */
    public String getRefundStreamImageUrl() {
        return getTypedValue(F_REFUND_STREAM_IMAGE_URL, String.class);
    }
    /**
     * @param refundStreamImageUrl to refund_stream_image_url 退款单截图地址 set
     */
    public OrdSalesOrder setRefundStreamImageUrl(String refundStreamImageUrl) {
        set(F_REFUND_STREAM_IMAGE_URL, refundStreamImageUrl);
        return this;
    }
    /**
     * @return reduce_activity_id to reduceActivityId 满减活动编号<BR/>
     */
    public String getReduceActivityId() {
        return getTypedValue(F_REDUCE_ACTIVITY_ID, String.class);
    }
    /**
     * @param reduceActivityId to reduce_activity_id 满减活动编号 set
     */
    public OrdSalesOrder setReduceActivityId(String reduceActivityId) {
        set(F_REDUCE_ACTIVITY_ID, reduceActivityId);
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
    public OrdSalesOrder setReduceActivityName(String reduceActivityName) {
        set(F_REDUCE_ACTIVITY_NAME, reduceActivityName);
        return this;
    }
    /**
     * @return reduce_activity_result to reduceActivityResult 满减活动结果<BR/>
     */
    public String getReduceActivityResult() {
        return getTypedValue(F_REDUCE_ACTIVITY_RESULT, String.class);
    }
    /**
     * @param reduceActivityResult to reduce_activity_result 满减活动结果 set
     */
    public OrdSalesOrder setReduceActivityResult(String reduceActivityResult) {
        set(F_REDUCE_ACTIVITY_RESULT, reduceActivityResult);
        return this;
    }
    /**
     * @return coupon_activity_id to couponActivityId 优惠卷活动编号<BR/>
     */
    public String getCouponActivityId() {
        return getTypedValue(F_COUPON_ACTIVITY_ID, String.class);
    }
    /**
     * @param couponActivityId to coupon_activity_id 优惠卷活动编号 set
     */
    public OrdSalesOrder setCouponActivityId(String couponActivityId) {
        set(F_COUPON_ACTIVITY_ID, couponActivityId);
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
    public OrdSalesOrder setCouponActivityName(String couponActivityName) {
        set(F_COUPON_ACTIVITY_NAME, couponActivityName);
        return this;
    }
    /**
     * @return coupon_activity_result to couponActivityResult 优惠卷活动结果<BR/>
     */
    public String getCouponActivityResult() {
        return getTypedValue(F_COUPON_ACTIVITY_RESULT, String.class);
    }
    /**
     * @param couponActivityResult to coupon_activity_result 优惠卷活动结果 set
     */
    public OrdSalesOrder setCouponActivityResult(String couponActivityResult) {
        set(F_COUPON_ACTIVITY_RESULT, couponActivityResult);
        return this;
    }
    /**
     * @return promotion_activity_id to promotionActivityId 限时促销活动编号<BR/>
     */
    public String getPromotionActivityId() {
        return getTypedValue(F_PROMOTION_ACTIVITY_ID, String.class);
    }
    /**
     * @param promotionActivityId to promotion_activity_id 限时促销活动编号 set
     */
    public OrdSalesOrder setPromotionActivityId(String promotionActivityId) {
        set(F_PROMOTION_ACTIVITY_ID, promotionActivityId);
        return this;
    }
    /**
     * @return promotion_activity_name to promotionActivityName 限时促销活动名称<BR/>
     */
    public String getPromotionActivityName() {
        return getTypedValue(F_PROMOTION_ACTIVITY_NAME, String.class);
    }
    /**
     * @param promotionActivityName to promotion_activity_name 限时促销活动名称 set
     */
    public OrdSalesOrder setPromotionActivityName(String promotionActivityName) {
        set(F_PROMOTION_ACTIVITY_NAME, promotionActivityName);
        return this;
    }
    /**
     * @return promotion_activity_result to promotionActivityResult 限时促销活动结果<BR/>
     */
    public String getPromotionActivityResult() {
        return getTypedValue(F_PROMOTION_ACTIVITY_RESULT, String.class);
    }
    /**
     * @param promotionActivityResult to promotion_activity_result 限时促销活动结果 set
     */
    public OrdSalesOrder setPromotionActivityResult(String promotionActivityResult) {
        set(F_PROMOTION_ACTIVITY_RESULT, promotionActivityResult);
        return this;
    }
    /**
     * @return fk_employee_id to fkEmployeeId 员工编号<BR/>
     */
    public String getFkEmployeeId() {
        return getTypedValue(F_FK_EMPLOYEE_ID, String.class);
    }
    /**
     * @param fkEmployeeId to fk_employee_id 员工编号 set
     */
    public OrdSalesOrder setFkEmployeeId(String fkEmployeeId) {
        set(F_FK_EMPLOYEE_ID, fkEmployeeId);
        return this;
    }
    /**
     * @return employee_name to employeeName 员工名称<BR/>
     */
    public String getEmployeeName() {
        return getTypedValue(F_EMPLOYEE_NAME, String.class);
    }
    /**
     * @param employeeName to employee_name 员工名称 set
     */
    public OrdSalesOrder setEmployeeName(String employeeName) {
        set(F_EMPLOYEE_NAME, employeeName);
        return this;
    }
    /**
     * @return tax_rate to taxRate 税率<BR/>
     */
    public String getTaxRate() {
        return getTypedValue(F_TAX_RATE, String.class);
    }
    /**
     * @param taxRate to tax_rate 税率 set
     */
    public OrdSalesOrder setTaxRate(String taxRate) {
        set(F_TAX_RATE, taxRate);
        return this;
    }
    /**
     * @return tax_amount to taxAmount 税额<BR/>
     */
    public Long getTaxAmount() {
        return getTypedValue(F_TAX_AMOUNT, Long.class);
    }
    /**
     * @param taxAmount to tax_amount 税额 set
     */
    public OrdSalesOrder setTaxAmount(Long taxAmount) {
        set(F_TAX_AMOUNT, taxAmount);
        return this;
    }
    /**
     * @return not_tax_amount to notTaxAmount 免税全额<BR/>
     */
    public Long getNotTaxAmount() {
        return getTypedValue(F_NOT_TAX_AMOUNT, Long.class);
    }
    /**
     * @param notTaxAmount to not_tax_amount 免税全额 set
     */
    public OrdSalesOrder setNotTaxAmount(Long notTaxAmount) {
        set(F_NOT_TAX_AMOUNT, notTaxAmount);
        return this;
    }
    /**
     * @return sort to sort 同级排序<BR/>
     */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
     * @param sort to sort 同级排序 set
     */
    public OrdSalesOrder setSort(Integer sort) {
        set(F_SORT, sort);
        return this;
    }
    /**
     * @return seo_title to seoTitle SEO-标题<BR/>
     */
    public String getSeoTitle() {
        return getTypedValue(F_SEO_TITLE, String.class);
    }
    /**
     * @param seoTitle to seo_title SEO-标题 set
     */
    public OrdSalesOrder setSeoTitle(String seoTitle) {
        set(F_SEO_TITLE, seoTitle);
        return this;
    }
    /**
     * @return seo_keywords to seoKeywords SEO-关键词<BR/>
     */
    public String getSeoKeywords() {
        return getTypedValue(F_SEO_KEYWORDS, String.class);
    }
    /**
     * @param seoKeywords to seo_keywords SEO-关键词 set
     */
    public OrdSalesOrder setSeoKeywords(String seoKeywords) {
        set(F_SEO_KEYWORDS, seoKeywords);
        return this;
    }
    /**
     * @return seo_description to seoDescription SEO-概述<BR/>
     */
    public String getSeoDescription() {
        return getTypedValue(F_SEO_DESCRIPTION, String.class);
    }
    /**
     * @param seoDescription to seo_description SEO-概述 set
     */
    public OrdSalesOrder setSeoDescription(String seoDescription) {
        set(F_SEO_DESCRIPTION, seoDescription);
        return this;
    }
    /**
     * @return seo_address to seoAddress SEO-地址<BR/>
     */
    public String getSeoAddress() {
        return getTypedValue(F_SEO_ADDRESS, String.class);
    }
    /**
     * @param seoAddress to seo_address SEO-地址 set
     */
    public OrdSalesOrder setSeoAddress(String seoAddress) {
        set(F_SEO_ADDRESS, seoAddress);
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
    public OrdSalesOrder setRemark(String remark) {
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
    public OrdSalesOrder setAddTime(Long addTime) {
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
    public OrdSalesOrder setAddUserId(String addUserId) {
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
    public OrdSalesOrder setAddUserName(String addUserName) {
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
    public OrdSalesOrder setOperationTime(Long operationTime) {
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
    public OrdSalesOrder setOperationUserId(String operationUserId) {
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
    public OrdSalesOrder setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
     * @return currency to currency 币种<BR/>
     */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
     * @param currency to currency 币种 set
     */
    public OrdSalesOrder setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
     * @return fk_currency_id to fkCurrencyId 币种ID<BR/>
     */
    public String getFkCurrencyId() {
        return getTypedValue(F_FK_CURRENCY_ID, String.class);
    }
    /**
     * @param fkCurrencyId to fk_currency_id 币种ID set
     */
    public OrdSalesOrder setFkCurrencyId(String fkCurrencyId) {
        set(F_FK_CURRENCY_ID, fkCurrencyId);
        return this;
    }
    /**
     * @return callback_email_status to callbackEmailStatus 召回邮件状态(01:未发送；02:已发送)<BR/>
     */
    public String getCallbackEmailStatus() {
        return getTypedValue(F_CALLBACK_EMAIL_STATUS, String.class);
    }
    /**
     * @param callbackEmailStatus to callback_email_status 召回邮件状态(01:未发送；02:已发送) set
     */
    public OrdSalesOrder setCallbackEmailStatus(String callbackEmailStatus) {
        set(F_CALLBACK_EMAIL_STATUS, callbackEmailStatus);
        return this;
    }
    /**
     * @return order_callback_status to orderCallbackStatus 订单召回状态(01:未召回；02:已召回；03:召回失败)<BR/>
     */
    public String getOrderCallbackStatus() {
        return getTypedValue(F_ORDER_CALLBACK_STATUS, String.class);
    }
    /**
     * @param orderCallbackStatus to order_callback_status 订单召回状态(01:未召回；02:已召回；03:召回失败) set
     */
    public OrdSalesOrder setOrderCallbackStatus(String orderCallbackStatus) {
        set(F_ORDER_CALLBACK_STATUS, orderCallbackStatus);
        return this;
    }
    /**
     * @return product_discount_price to productDiscountPrice 商品扣后总价格<BR/>
     */
    public Long getProductDiscountPrice() {
        return getTypedValue(F_PRODUCT_DISCOUNT_PRICE, Long.class);
    }
    /**
     * @param productDiscountPrice to product_discount_price 商品扣后总价格 set
     */
    public OrdSalesOrder setProductDiscountPrice(Long productDiscountPrice) {
        set(F_PRODUCT_DISCOUNT_PRICE, productDiscountPrice);
        return this;
    }
    /**
     * @return localhost_sn to localhostSn <BR/>
     */
    public Long getLocalhostSn() {
        return getTypedValue(F_LOCALHOST_SN, Long.class);
    }
    /**
     * @param localhostSn to localhost_sn  set
     */
    public OrdSalesOrder setLocalhostSn(Long localhostSn) {
        set(F_LOCALHOST_SN, localhostSn);
        return this;
    }
    /**
     * @return fk_tag_id to fkTagId 标签编号<BR/>
     */
    public String getFkTagId() {
        return getTypedValue(F_FK_TAG_ID, String.class);
    }
    /**
     * @param fkTagId to fk_tag_id 标签编号 set
     */
    public OrdSalesOrder setFkTagId(String fkTagId) {
        set(F_FK_TAG_ID, fkTagId);
        return this;
    }
    /**
     * @return is_enable to isEnable 是否启用<BR/>
     */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
     * @param isEnable to is_enable 是否启用 set
     */
    public OrdSalesOrder setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdSalesOrder setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdSalesOrder me(){
        return new OrdSalesOrder();
    }

    private static class Mapper implements RowMapper<OrdSalesOrder> {

        private Supplier<OrdSalesOrder> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdSalesOrder mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            OrdSalesOrder bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setSalesOrderNo(Utils.toCast(columnsName.contains(F_SALES_ORDER_NO) ? rs.getObject(F_SALES_ORDER_NO) : null, String.class));
            bean.setCompanyId(Utils.toCast(columnsName.contains(F_COMPANY_ID) ? rs.getObject(F_COMPANY_ID) : null, String.class));
            bean.setCompanyName(Utils.toCast(columnsName.contains(F_COMPANY_NAME) ? rs.getObject(F_COMPANY_NAME) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setClientEmail(Utils.toCast(columnsName.contains(F_CLIENT_EMAIL) ? rs.getObject(F_CLIENT_EMAIL) : null, String.class));
            bean.setClientPhone(Utils.toCast(columnsName.contains(F_CLIENT_PHONE) ? rs.getObject(F_CLIENT_PHONE) : null, String.class));
            bean.setReceiverName(Utils.toCast(columnsName.contains(F_RECEIVER_NAME) ? rs.getObject(F_RECEIVER_NAME) : null, String.class));
            bean.setReceiverId(Utils.toCast(columnsName.contains(F_RECEIVER_ID) ? rs.getObject(F_RECEIVER_ID) : null, String.class));
            bean.setFkClienteleAddressId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ADDRESS_ID) ? rs.getObject(F_FK_CLIENTELE_ADDRESS_ID) : null, String.class));
            bean.setReceiverPhone(Utils.toCast(columnsName.contains(F_RECEIVER_PHONE) ? rs.getObject(F_RECEIVER_PHONE) : null, String.class));
            bean.setReceiverEmail(Utils.toCast(columnsName.contains(F_RECEIVER_EMAIL) ? rs.getObject(F_RECEIVER_EMAIL) : null, String.class));
            bean.setReceiverCountry(Utils.toCast(columnsName.contains(F_RECEIVER_COUNTRY) ? rs.getObject(F_RECEIVER_COUNTRY) : null, String.class));
            bean.setReceiverProvince(Utils.toCast(columnsName.contains(F_RECEIVER_PROVINCE) ? rs.getObject(F_RECEIVER_PROVINCE) : null, String.class));
            bean.setReceiverCity(Utils.toCast(columnsName.contains(F_RECEIVER_CITY) ? rs.getObject(F_RECEIVER_CITY) : null, String.class));
            bean.setReceiverSheet(Utils.toCast(columnsName.contains(F_RECEIVER_SHEET) ? rs.getObject(F_RECEIVER_SHEET) : null, String.class));
            bean.setReceiverAddress(Utils.toCast(columnsName.contains(F_RECEIVER_ADDRESS) ? rs.getObject(F_RECEIVER_ADDRESS) : null, String.class));
            bean.setReceiverPost(Utils.toCast(columnsName.contains(F_RECEIVER_POST) ? rs.getObject(F_RECEIVER_POST) : null, String.class));
            bean.setFkInvoiceAddressId(Utils.toCast(columnsName.contains(F_FK_INVOICE_ADDRESS_ID) ? rs.getObject(F_FK_INVOICE_ADDRESS_ID) : null, String.class));
            bean.setSenderName(Utils.toCast(columnsName.contains(F_SENDER_NAME) ? rs.getObject(F_SENDER_NAME) : null, String.class));
            bean.setSenderId(Utils.toCast(columnsName.contains(F_SENDER_ID) ? rs.getObject(F_SENDER_ID) : null, String.class));
            bean.setSenderAddress(Utils.toCast(columnsName.contains(F_SENDER_ADDRESS) ? rs.getObject(F_SENDER_ADDRESS) : null, String.class));
            bean.setSenderPhone(Utils.toCast(columnsName.contains(F_SENDER_PHONE) ? rs.getObject(F_SENDER_PHONE) : null, String.class));
            bean.setSenderEmail(Utils.toCast(columnsName.contains(F_SENDER_EMAIL) ? rs.getObject(F_SENDER_EMAIL) : null, String.class));
            bean.setTotalPrice(Utils.toCast(columnsName.contains(F_TOTAL_PRICE) ? rs.getObject(F_TOTAL_PRICE) : null, Long.class));
            bean.setTotalVol(Utils.toCast(columnsName.contains(F_TOTAL_VOL) ? rs.getObject(F_TOTAL_VOL) : null, Long.class));
            bean.setTotalWeight(Utils.toCast(columnsName.contains(F_TOTAL_WEIGHT) ? rs.getObject(F_TOTAL_WEIGHT) : null, Long.class));
            bean.setTotalProductNumber(Utils.toCast(columnsName.contains(F_TOTAL_PRODUCT_NUMBER) ? rs.getObject(F_TOTAL_PRODUCT_NUMBER) : null, Integer.class));
            bean.setTransportsChannelName(Utils.toCast(columnsName.contains(F_TRANSPORTS_CHANNEL_NAME) ? rs.getObject(F_TRANSPORTS_CHANNEL_NAME) : null, String.class));
            bean.setTransportsChannelId(Utils.toCast(columnsName.contains(F_TRANSPORTS_CHANNEL_ID) ? rs.getObject(F_TRANSPORTS_CHANNEL_ID) : null, String.class));
            bean.setTransportsNo(Utils.toCast(columnsName.contains(F_TRANSPORTS_NO) ? rs.getObject(F_TRANSPORTS_NO) : null, String.class));
            bean.setTransportsFreeId(Utils.toCast(columnsName.contains(F_TRANSPORTS_FREE_ID) ? rs.getObject(F_TRANSPORTS_FREE_ID) : null, String.class));
            bean.setTransportsFreeName(Utils.toCast(columnsName.contains(F_TRANSPORTS_FREE_NAME) ? rs.getObject(F_TRANSPORTS_FREE_NAME) : null, String.class));
            bean.setTotalTransportsFree(Utils.toCast(columnsName.contains(F_TOTAL_TRANSPORTS_FREE) ? rs.getObject(F_TOTAL_TRANSPORTS_FREE) : null, Long.class));
            bean.setTotalPaymentFree(Utils.toCast(columnsName.contains(F_TOTAL_PAYMENT_FREE) ? rs.getObject(F_TOTAL_PAYMENT_FREE) : null, Long.class));
            bean.setPaymentTime(Utils.toCast(columnsName.contains(F_PAYMENT_TIME) ? rs.getObject(F_PAYMENT_TIME) : null, Long.class));
            bean.setPaymentName(Utils.toCast(columnsName.contains(F_PAYMENT_NAME) ? rs.getObject(F_PAYMENT_NAME) : null, String.class));
            bean.setPaymentStreamNo(Utils.toCast(columnsName.contains(F_PAYMENT_STREAM_NO) ? rs.getObject(F_PAYMENT_STREAM_NO) : null, String.class));
            bean.setPaymentPlatformName(Utils.toCast(columnsName.contains(F_PAYMENT_PLATFORM_NAME) ? rs.getObject(F_PAYMENT_PLATFORM_NAME) : null, String.class));
            bean.setPaymentPlatformTypeName(Utils.toCast(columnsName.contains(F_PAYMENT_PLATFORM_TYPE_NAME) ? rs.getObject(F_PAYMENT_PLATFORM_TYPE_NAME) : null, String.class));
            bean.setPaymentPlatformTypeId(Utils.toCast(columnsName.contains(F_PAYMENT_PLATFORM_TYPE_ID) ? rs.getObject(F_PAYMENT_PLATFORM_TYPE_ID) : null, String.class));
            bean.setPaymentCardNo(Utils.toCast(columnsName.contains(F_PAYMENT_CARD_NO) ? rs.getObject(F_PAYMENT_CARD_NO) : null, String.class));
            bean.setSalesOrderStatus(Utils.toCast(columnsName.contains(F_SALES_ORDER_STATUS) ? rs.getObject(F_SALES_ORDER_STATUS) : null, String.class));
            bean.setRefundRequestNo(Utils.toCast(columnsName.contains(F_REFUND_REQUEST_NO) ? rs.getObject(F_REFUND_REQUEST_NO) : null, String.class));
            bean.setRefundStreamNo(Utils.toCast(columnsName.contains(F_REFUND_STREAM_NO) ? rs.getObject(F_REFUND_STREAM_NO) : null, String.class));
            bean.setRefundStreamImageUrl(Utils.toCast(columnsName.contains(F_REFUND_STREAM_IMAGE_URL) ? rs.getObject(F_REFUND_STREAM_IMAGE_URL) : null, String.class));
            bean.setReduceActivityId(Utils.toCast(columnsName.contains(F_REDUCE_ACTIVITY_ID) ? rs.getObject(F_REDUCE_ACTIVITY_ID) : null, String.class));
            bean.setReduceActivityName(Utils.toCast(columnsName.contains(F_REDUCE_ACTIVITY_NAME) ? rs.getObject(F_REDUCE_ACTIVITY_NAME) : null, String.class));
            bean.setReduceActivityResult(Utils.toCast(columnsName.contains(F_REDUCE_ACTIVITY_RESULT) ? rs.getObject(F_REDUCE_ACTIVITY_RESULT) : null, String.class));
            bean.setCouponActivityId(Utils.toCast(columnsName.contains(F_COUPON_ACTIVITY_ID) ? rs.getObject(F_COUPON_ACTIVITY_ID) : null, String.class));
            bean.setCouponActivityName(Utils.toCast(columnsName.contains(F_COUPON_ACTIVITY_NAME) ? rs.getObject(F_COUPON_ACTIVITY_NAME) : null, String.class));
            bean.setCouponActivityResult(Utils.toCast(columnsName.contains(F_COUPON_ACTIVITY_RESULT) ? rs.getObject(F_COUPON_ACTIVITY_RESULT) : null, String.class));
            bean.setPromotionActivityId(Utils.toCast(columnsName.contains(F_PROMOTION_ACTIVITY_ID) ? rs.getObject(F_PROMOTION_ACTIVITY_ID) : null, String.class));
            bean.setPromotionActivityName(Utils.toCast(columnsName.contains(F_PROMOTION_ACTIVITY_NAME) ? rs.getObject(F_PROMOTION_ACTIVITY_NAME) : null, String.class));
            bean.setPromotionActivityResult(Utils.toCast(columnsName.contains(F_PROMOTION_ACTIVITY_RESULT) ? rs.getObject(F_PROMOTION_ACTIVITY_RESULT) : null, String.class));
            bean.setFkEmployeeId(Utils.toCast(columnsName.contains(F_FK_EMPLOYEE_ID) ? rs.getObject(F_FK_EMPLOYEE_ID) : null, String.class));
            bean.setEmployeeName(Utils.toCast(columnsName.contains(F_EMPLOYEE_NAME) ? rs.getObject(F_EMPLOYEE_NAME) : null, String.class));
            bean.setTaxRate(Utils.toCast(columnsName.contains(F_TAX_RATE) ? rs.getObject(F_TAX_RATE) : null, String.class));
            bean.setTaxAmount(Utils.toCast(columnsName.contains(F_TAX_AMOUNT) ? rs.getObject(F_TAX_AMOUNT) : null, Long.class));
            bean.setNotTaxAmount(Utils.toCast(columnsName.contains(F_NOT_TAX_AMOUNT) ? rs.getObject(F_NOT_TAX_AMOUNT) : null, Long.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setSeoTitle(Utils.toCast(columnsName.contains(F_SEO_TITLE) ? rs.getObject(F_SEO_TITLE) : null, String.class));
            bean.setSeoKeywords(Utils.toCast(columnsName.contains(F_SEO_KEYWORDS) ? rs.getObject(F_SEO_KEYWORDS) : null, String.class));
            bean.setSeoDescription(Utils.toCast(columnsName.contains(F_SEO_DESCRIPTION) ? rs.getObject(F_SEO_DESCRIPTION) : null, String.class));
            bean.setSeoAddress(Utils.toCast(columnsName.contains(F_SEO_ADDRESS) ? rs.getObject(F_SEO_ADDRESS) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddUserName(Utils.toCast(columnsName.contains(F_ADD_USER_NAME) ? rs.getObject(F_ADD_USER_NAME) : null, String.class));
            bean.setOperationTime(Utils.toCast(columnsName.contains(F_OPERATION_TIME) ? rs.getObject(F_OPERATION_TIME) : null, Long.class));
            bean.setOperationUserId(Utils.toCast(columnsName.contains(F_OPERATION_USER_ID) ? rs.getObject(F_OPERATION_USER_ID) : null, String.class));
            bean.setOperationUserName(Utils.toCast(columnsName.contains(F_OPERATION_USER_NAME) ? rs.getObject(F_OPERATION_USER_NAME) : null, String.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setFkCurrencyId(Utils.toCast(columnsName.contains(F_FK_CURRENCY_ID) ? rs.getObject(F_FK_CURRENCY_ID) : null, String.class));
            bean.setCallbackEmailStatus(Utils.toCast(columnsName.contains(F_CALLBACK_EMAIL_STATUS) ? rs.getObject(F_CALLBACK_EMAIL_STATUS) : null, String.class));
            bean.setOrderCallbackStatus(Utils.toCast(columnsName.contains(F_ORDER_CALLBACK_STATUS) ? rs.getObject(F_ORDER_CALLBACK_STATUS) : null, String.class));
            bean.setProductDiscountPrice(Utils.toCast(columnsName.contains(F_PRODUCT_DISCOUNT_PRICE) ? rs.getObject(F_PRODUCT_DISCOUNT_PRICE) : null, Long.class));
            bean.setLocalhostSn(Utils.toCast(columnsName.contains(F_LOCALHOST_SN) ? rs.getObject(F_LOCALHOST_SN) : null, Long.class));
            bean.setFkTagId(Utils.toCast(columnsName.contains(F_FK_TAG_ID) ? rs.getObject(F_FK_TAG_ID) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdSalesOrder> newMapper(){
        return newMapper(OrdSalesOrder::new);
    }

    public RowMapper<OrdSalesOrder> newMapper(Supplier<OrdSalesOrder> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdSalesOrder {
        @Override
        public abstract RowMapper<OrdSalesOrder> newMapper();
    }
}
