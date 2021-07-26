package com.mazentop.modules.user.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.model.*;
import com.mazentop.modules.emp.service.EmailService;
import com.mazentop.modules.emp.service.OrderManageService;
import com.mazentop.modules.user.dto.*;
import com.mazentop.modules.web.User;
import com.mazentop.modules.user.commond.OrdBalanceTheBooksCommond;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.service.ReportProductService;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.modules.web.service.TouristService;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import com.mztframework.data.R;
import com.mztframework.email.Email;
import com.mztframework.exception.ToastException;
import com.mztframework.snowflake.IDSnowflake;
import com.paypal.CaptureOrder;
import com.paypal.CrateOrder;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

import com.xkcoding.http.util.StringUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wangzy
 * @date: 2020/3/20
 * @description:
 */
@Service
public class PaypalService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderManageService orderManageService;

    @Autowired
    EmailService service;

    @Autowired
    ReportProductService reportService;

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;

    @Autowired
    TouristService touristService;


    public HttpResponse<Order> doCreatePayments(PaymentDto paymentDto, String cancelUrl, String successUrl) throws Exception {
        //paypal支付sendbox账号
        PayPalEnvironment palEnvironment = checkPayPalEnvironment();
        //测试数据构建
        OrdPaymentRecord ordPaymentRecord = CreatePayData(paymentDto);
        HttpResponse<Order> orderResponse = new CrateOrder().createOrder(true, palEnvironment, cancelUrl, successUrl, ordPaymentRecord, paymentDto.getSource());
        ordPaymentRecord.setPayId(orderResponse.result().id()).update();
        return orderResponse;
    }

    /**
     * 获取购物车信息
     * @return
     */
    public List<OrdShoppingCart> getCartList(String cartStr){
        List<String> ids = new ArrayList<>();
        if (cartStr.contains(",")) {
            String[] id = cartStr.split(",");
            ids = Arrays.asList(id);
        } else {
            ids.add(cartStr);
        }
        List<OrdShoppingCart> cartList = new ArrayList<>();
        //封装构建数据
        if(!ids.isEmpty()) {
            ids.forEach(catId -> {
                OrdShoppingCart cart = OrdShoppingCart.me().setId(catId).get();
                cartList.add(cart);
            });
        }
        return cartList;
    }

    public HttpResponse<Order> executePayments(String paymentId) throws Exception {
        //paypal支付sendbox账号
        PayPalEnvironment palEnvironment = checkPayPalEnvironment();
        SysOptions sysOptions = SysOptions.me().setOptionKey("paypal_BnCode").get();
        String bnCode = sysOptions.getOptionValue();
        HttpResponse<Order> captureOrderResponse = new CaptureOrder().captureOrders(paymentId, false, palEnvironment,bnCode);
        return captureOrderResponse;
    }


    public Payment doCreatePayment(OrdBalanceTheBooksDto ordBalanceTheBooksDto, String cancelUrl, String successUrl) throws Exception {
        //paypal支付sendbox账号
        APIContext payPalContext = checkPayPal();
        //测试数据构建
        OrdPaymentRecord ordPaymentRecord = CreateData(ordBalanceTheBooksDto);

        Amount amount = new Amount();
        amount.setCurrency(ordBalanceTheBooksDto.getCurrency());
        amount.setTotal(String.format("%.2f", Helper.transformF2Y(ordPaymentRecord.getPaymentTotalPrice())));

        Transaction transaction = new Transaction();
        transaction.setDescription("payment description");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(PaypalPaymentMethod.paypal.toString());

        Payment payment = new Payment();
        payment.setIntent(PaypalPaymentIntent.sale.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl + "?payId=" + ordPaymentRecord.getId());
        redirectUrls.setReturnUrl(successUrl + "?payId=" + ordPaymentRecord.getId());
        payment.setRedirectUrls(redirectUrls);

        return payment.create(payPalContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws Exception {
        //paypal支付sendbox账号
        APIContext payPalContext = checkPayPal();
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(payPalContext, paymentExecute);
    }

    private APIContext checkPayPal() throws Exception {
        SysOptions mode = SysOptions.me().setOptionKey("paypal_sendbox_account").get();
        if (Objects.isNull(mode)) {
            throw new Exception("payPal支付账号获取失败!");
        }
        //获取paypal支付client_id
        SysOptions clientId = SysOptions.me().setOptionKey("paypal_client_id").get();
        if (Objects.isNull(clientId)) {
            throw new Exception("paypal支付client_id获取失败!");
        }
        //paypal支付clientSecret
        SysOptions clientSecret = SysOptions.me().setOptionKey("paypal_secret").get();
        if (Objects.isNull(clientSecret)) {
            throw new Exception("paypal支付clientSecret获取失败!");
        }
        APIContext apiContext = apiContext(mode.getOptionValue(), clientId.getOptionValue(), clientSecret.getOptionValue());
        return apiContext;
    }

    public PayPalEnvironment checkPayPalEnvironment() throws Exception {
        SysOptions mode = SysOptions.me().setOptionKey("paypal_sendbox_account").get();
        if (Objects.isNull(mode)) {
            throw new Exception("payPal支付账号获取失败!");
        }
        //获取paypal支付client_id
        SysOptions clientId = SysOptions.me().setOptionKey("paypal_client_id").get();
        if (Objects.isNull(clientId)) {
            throw new Exception("paypal支付client_id获取失败!");
        }
        //paypal支付clientSecret
        SysOptions clientSecret = SysOptions.me().setOptionKey("paypal_secret").get();
        if (Objects.isNull(clientSecret)) {
            throw new Exception("paypal支付clientSecret获取失败!");
        }
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId.getOptionValue(), clientSecret.getOptionValue());
        return environment;
    }

    public OrdPaymentRecord CreateData(OrdBalanceTheBooksDto ordBalanceTheBooksDto) {
        //封装销售单信息
        OrdPaymentRecord ordPaymentRecord = new OrdPaymentRecord();
        OrdBalanceTheBooks ordBalanceTheBook = OrdBalanceTheBooks.me().setId(ordBalanceTheBooksDto.getId()).get();
        ordBalanceTheBook.setRemark(ordBalanceTheBooksDto.getRemark());
        BeanUtils.copyProperties(ordBalanceTheBook, ordBalanceTheBooksDto);
        if (Helper.isNotEmpty(ordBalanceTheBooksDto.getShoppingCartList())) {
            List<String> ids = new ArrayList<>();
            if (ordBalanceTheBooksDto.getShoppingCartList().contains(",")) {
                String[] id = ordBalanceTheBooksDto.getShoppingCartList().split(",");
                ids = Arrays.asList(id);
            } else {
                ids.add(ordBalanceTheBooksDto.getShoppingCartList());
            }
            for (String id : ids) {
                OrdShoppingCart.me().setId(id).setIsGenBalanceAccounts(1).update();
            }
        }

        optionOrdPaymentRecord(ordBalanceTheBooksDto, ordPaymentRecord);
        return ordPaymentRecord;
    }

    public void optionOrdBalanceTheBooks(OrdBalanceTheBooksDto ordBalanceTheBooksDto, HttpServletRequest request, HttpServletResponse response) {
        String exchangeTuc = Helper.getExchangeTuc(request, CacheConstant.RATIO);
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        ordBalanceTheBooksDto.setBalanceTheBooksNo("SM" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        String ip = Helper.getIpAddress(request);
        String idArry = "";
        for (String id : ordBalanceTheBooksDto.getOrdShoppingCartList()) {
            if (StringUtils.isBlank(idArry)) {
                idArry += id;
            } else {
                idArry += "," + id;
            }
        }
        //无登录无召回
        if (User.isAuth()) {
            getCliClienteleInfo(ordBalanceTheBooksDto);
            ordBalanceTheBooksDto.setIsRecall(0);
            ordBalanceTheBooksDto.setIsNeedRecall(1);
        } else {
            getIsAuthCliClienteleInfo(ordBalanceTheBooksDto, ip);
            ordBalanceTheBooksDto.setIsRecall(1);
            ordBalanceTheBooksDto.setIsNeedRecall(0);
        }
        ordBalanceTheBooksDto.setIsDiscard(1);
        ordBalanceTheBooksDto.setShoppingCartList(idArry);
        //判断国家信息是否为空
        if (!StringUtils.isBlank(ordBalanceTheBooksDto.getFkSysCountryId())) {
            SysCountry sysCountry = SysCountry.me().setAlpha3(ordBalanceTheBooksDto.getFkSysCountryId()).get();
            ordBalanceTheBooksDto.setFkSysCountryId(sysCountry.getAlpha3());
            ordBalanceTheBooksDto.setCountryName(sysCountry.getName());
        }
        ordBalanceTheBooksDto.setCurrency("USD");
        if (!StringUtil.isEmpty(exchangeTuc)) {
            ordBalanceTheBooksDto.setCurrency(exchangeTuc);
        }
        //处理结算单费用
        optionOrdBalanceTheBooksPrice(ordBalanceTheBooksDto);
        ordBalanceTheBooksDto.setFkCurrencyId(exchangeId);
        ordBalanceTheBooksDto.insert();
    }

    private void optionOrdPaymentRecord(OrdBalanceTheBooksDto ordBalanceTheBooks, OrdPaymentRecord ordPaymentRecord) {
        //封装交易信息
        ordPaymentRecord.setPaymentTotalPrice(ordBalanceTheBooks.getPaymentTotalPrice());
        ordPaymentRecord.setProductTotalPrice(ordBalanceTheBooks.getProductTotalPrice());
        ordPaymentRecord.setProductDiscountPrice(ordBalanceTheBooks.getProductDiscountPrice());
        ordPaymentRecord.setFreeSchemeTotalPrice(ordBalanceTheBooks.getFreeSchemeTotalPrice());
        ordPaymentRecord.setBalanceTheBooksNo(ordBalanceTheBooks.getBalanceTheBooksNo());
        ordPaymentRecord.setPaymentTime(Utils.currentTimeSecond());
        ordPaymentRecord.setIsPaySuccess(0);
        //获取支付人信息
        if (User.isAuth()) {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(ordBalanceTheBooks.getFkClienteleId()).get();
            ordPaymentRecord.setAddUserId(cliClienteleInfo.getId());
            ordPaymentRecord.setAddUserName(cliClienteleInfo.getLoginName());
            ordPaymentRecord.setAddTime(Utils.currentTimeSecond());
            ordPaymentRecord.setFkClienteleId(cliClienteleInfo.getId());
            ordPaymentRecord.setClientName(cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName());
        } else {
            SysNotLoginPurchaseInfo sysNotLoginPurchaseInfo = SysNotLoginPurchaseInfo.me().setId(ordBalanceTheBooks.getFkClienteleId()).get();
            if(!Objects.isNull(sysNotLoginPurchaseInfo)) {
                ordPaymentRecord.setAddUserName(sysNotLoginPurchaseInfo.getNotLoginName());
                ordPaymentRecord.setAddTime(Utils.currentTimeSecond());
                ordPaymentRecord.setClientName(sysNotLoginPurchaseInfo.getNotLoginName());
                ordPaymentRecord.setFkClienteleId(sysNotLoginPurchaseInfo.getId());
            }
        }
        //获取支付方式
        String typeName = PaymentEnum.getName(ordBalanceTheBooks.getPayType() + "");
        OrdPaymentType ordPaymentType = OrdPaymentType.me().setPaymentTypeName(typeName).get();
        if (!Objects.isNull(ordPaymentType)) {
            ordPaymentRecord.setFkPaymentTypeId(ordPaymentType.getId());
            ordPaymentRecord.setPaymentTypeName(ordPaymentType.getPaymentTypeName());
            ordBalanceTheBooks.setFkPaymentTypeId(ordPaymentType.getId());
            ordBalanceTheBooks.setPaymentTypeName(ordPaymentType.getPaymentTypeName());
        }
        ordBalanceTheBooks.setIsDiscard(0);
        ordPaymentRecord.insert();
        ordBalanceTheBooks.update();
    }

    private void optionOrdBalanceTheBooksPrice(OrdBalanceTheBooksDto ordBalanceTheBooksDto) {
        //判断商品总价格是否为空
        if (ordBalanceTheBooksDto.getProductTotalPriceBig() != null) {
            ordBalanceTheBooksDto.setProductTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getProductTotalPriceBig()));
        }
        //判断支付总价格是否为空
        if (ordBalanceTheBooksDto.getPaymentTotalPriceBig() != null) {
            ordBalanceTheBooksDto.setPaymentTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getPaymentTotalPriceBig()));
        }
        //判断优惠总价格是否为空
        if (ordBalanceTheBooksDto.getProductDiscountPriceBig() != null) {
            ordBalanceTheBooksDto.setProductDiscountPrice(Helper.transformY2F(ordBalanceTheBooksDto.getProductDiscountPriceBig()));
        }
        //运费
        if (ordBalanceTheBooksDto.getFreeSchemeTotalPriceBig() != null) {
            ordBalanceTheBooksDto.setFreeSchemeTotalPrice(Helper.transformY2F(ordBalanceTheBooksDto.getFreeSchemeTotalPriceBig()));
        }
        //税费
        if (ordBalanceTheBooksDto.getTaxRateBig() != null) {
            ordBalanceTheBooksDto.setTaxRate(Helper.transformY2F(ordBalanceTheBooksDto.getTaxRateBig()));
        }
        if (ordBalanceTheBooksDto.getFkFreeSchemeId() != null) {
            ordBalanceTheBooksDto.setFreeSchemeName(OrdFreeSchemeDetails.me().setId(ordBalanceTheBooksDto.getFkFreeSchemeId()).get().getSchemeInsideName());
        }
        if (ordBalanceTheBooksDto.getCouponDiscountValueBig() != null) {
            ordBalanceTheBooksDto.setCouponDiscountValue(Helper.transformY2F(ordBalanceTheBooksDto.getCouponDiscountValueBig()));
        }
        if (ordBalanceTheBooksDto.getReduceDiscountValueBig() != null) {
            ordBalanceTheBooksDto.setReduceDiscountValue(Helper.transformY2F(ordBalanceTheBooksDto.getReduceDiscountValueBig()));
        }
    }

    private void getCliClienteleInfo(OrdBalanceTheBooksDto ordBalanceTheBooksDto) {
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        ordBalanceTheBooksDto.setAddUserId(cliClienteleInfo.getId());
        ordBalanceTheBooksDto.setAddUserName(cliClienteleInfo.getLoginName());
        ordBalanceTheBooksDto.setAddTime(Utils.currentTimeSecond());
        ordBalanceTheBooksDto.setFkClienteleId(cliClienteleInfo.getId());
        ordBalanceTheBooksDto.setClientName(cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName());
        //判断是否为发票收货地址
        if (ordBalanceTheBooksDto.getIsInvoice() == 1) {
            if (!Helper.isEmpty(ordBalanceTheBooksDto.getFkClienteleReceiverAddressId())) {
                CliClienteleReceiverAddress address = CliClienteleReceiverAddress.me().setId(ordBalanceTheBooksDto.getFkClienteleReceiverAddressId()).get();
                if (!Objects.isNull(address)) {
                    OrdInvoiceAddress ordInvoiceAddress = new OrdInvoiceAddress();
                    ordInvoiceAddress.setAddUserId(cliClienteleInfo.getId());
                    ordInvoiceAddress.setAddUserName(cliClienteleInfo.getLoginName());
                    ordInvoiceAddress.setAddTime(Utils.currentTimeSecond());
                    ordInvoiceAddress.setFkClienteleId(address.getFkClienteleId());
                    ordInvoiceAddress.setReceiptName(address.getClientName());
                    ordInvoiceAddress.setReceiptSurname(address.getClientSurname());
                    ordInvoiceAddress.setReceiptCountry(address.getCountry());
                    ordInvoiceAddress.setReceiptCity(address.getCity());
                    ordInvoiceAddress.setReceiptPhone(address.getPhone());
                    ordInvoiceAddress.setReceiptEmail(address.getEmail());
                    ordInvoiceAddress.setReceiptPost(address.getPost());
                    ordInvoiceAddress.setReceiptProvince(address.getProvince());
                    ordInvoiceAddress.setReceiptSheet(address.getSheet());
                    ordInvoiceAddress.setReceiptAddress(address.getAddress());
                    ordInvoiceAddress.setFkBalanceTheBooksId(ordBalanceTheBooksDto.getBalanceTheBooksNo());
                    ordInvoiceAddress.insert();
                    ordBalanceTheBooksDto.setFkOrdInvoiceAddressId(ordInvoiceAddress.getId());
                }
            }
        } else {
            addOrdInvoiceAddress(ordBalanceTheBooksDto, cliClienteleInfo.getId());
        }
    }

    private void getIsAuthCliClienteleInfo(OrdBalanceTheBooksDto ordBalanceTheBooksDto, String ip) {
        if(!Objects.isNull(ordBalanceTheBooksDto.getSysNotLoginPurchaseInfo())) {
            SysNotLoginPurchaseInfo sysNotLoginPurchaseInfo = ordBalanceTheBooksDto.getSysNotLoginPurchaseInfo();
            sysNotLoginPurchaseInfo.setAddTime(Utils.currentTimeSecond());
            sysNotLoginPurchaseInfo.setIsPaySuccess(0);
            sysNotLoginPurchaseInfo.setNotLoginIp(ip);
            sysNotLoginPurchaseInfo.insert();
            if (ordBalanceTheBooksDto.getIsInvoice() == 1) {
                if (!Helper.isEmpty(sysNotLoginPurchaseInfo.getId())) {
                    OrdInvoiceAddress ordInvoiceAddress = new OrdInvoiceAddress();
                    ordInvoiceAddress.setAddUserName(sysNotLoginPurchaseInfo.getNotLoginName());
                    ordInvoiceAddress.setAddTime(Utils.currentTimeSecond());
                    ordInvoiceAddress.setFkClienteleId(sysNotLoginPurchaseInfo.getId());
                    ordInvoiceAddress.setReceiptName(sysNotLoginPurchaseInfo.getName());
                    ordInvoiceAddress.setReceiptSurname(sysNotLoginPurchaseInfo.getSurname());
                    ordInvoiceAddress.setReceiptCountry(sysNotLoginPurchaseInfo.getCountry());
                    ordInvoiceAddress.setReceiptCity(sysNotLoginPurchaseInfo.getCity());
                    ordInvoiceAddress.setReceiptPhone(sysNotLoginPurchaseInfo.getPhone());
                    ordInvoiceAddress.setReceiptEmail(sysNotLoginPurchaseInfo.getEmail());
                    ordInvoiceAddress.setReceiptPost(sysNotLoginPurchaseInfo.getPost());
                    ordInvoiceAddress.setReceiptProvince(sysNotLoginPurchaseInfo.getProvince());
                    ordInvoiceAddress.setReceiptSheet(sysNotLoginPurchaseInfo.getSheet());
                    ordInvoiceAddress.setReceiptAddress(sysNotLoginPurchaseInfo.getAddress());
                    ordInvoiceAddress.setFkBalanceTheBooksId(ordBalanceTheBooksDto.getBalanceTheBooksNo());
                    ordInvoiceAddress.insert();
                    ordBalanceTheBooksDto.setFkOrdInvoiceAddressId(ordInvoiceAddress.getId());
                }
            } else {
                addOrdInvoiceAddress(ordBalanceTheBooksDto, sysNotLoginPurchaseInfo.getId());
            }
            ordBalanceTheBooksDto.setFkSysCountryId(sysNotLoginPurchaseInfo.getCountry());
            ordBalanceTheBooksDto.setAddUserName(sysNotLoginPurchaseInfo.getNotLoginName());
            ordBalanceTheBooksDto.setAddTime(Utils.currentTimeSecond());
            ordBalanceTheBooksDto.setFkClienteleId(sysNotLoginPurchaseInfo.getId());
            ordBalanceTheBooksDto.setClientName(sysNotLoginPurchaseInfo.getNotLoginName());
        }
    }

    private void addOrdInvoiceAddress(OrdBalanceTheBooksDto ordBalanceTheBooksDto, String Id) {
        OrdInvoiceAddress ordInvoiceAddress = ordBalanceTheBooksDto.getOrdInvoiceAddress();
        if (!Objects.isNull(ordInvoiceAddress)) {
            ordInvoiceAddress.setFkBalanceTheBooksId(ordBalanceTheBooksDto.getBalanceTheBooksNo());
            ordInvoiceAddress.setFkClienteleId(Id);
            ordInvoiceAddress.insert();
            ordBalanceTheBooksDto.setFkOrdInvoiceAddressId(ordInvoiceAddress.getId());
        }
    }


    public void doAddPaySalesOrder(String payId, String paymentId, String payerId, ModelMap map)  {
        if (StringUtils.isNotBlank(payId)) {
            OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setId(payId).get();
            Map<String,Object> resMap =new HashMap<>(1);
            String userId;
            if (User.isAuth()) {
                CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
                ordPaymentRecord.setOperationUserId(cliClienteleInfo.getId());
                ordPaymentRecord.setOperationUserName(cliClienteleInfo.getLoginName());
                map.put("loginName", cliClienteleInfo.getLoginName());
                map.put("payTypeName", ordPaymentRecord.getPaymentTypeName());
                userId = cliClienteleInfo.getId();
            }else{
                SysNotLoginPurchaseInfo sysNotLoginPurchaseInfo = SysNotLoginPurchaseInfo.me().setId(ordPaymentRecord.getAddUserId()).get();
                ordPaymentRecord.setOperationUserId(sysNotLoginPurchaseInfo.getId());
                ordPaymentRecord.setOperationUserName(sysNotLoginPurchaseInfo.getNotLoginName());
                map.put("loginName", sysNotLoginPurchaseInfo.getNotLoginName());
                map.put("payTypeName", ordPaymentRecord.getPaymentTypeName());
                userId = sysNotLoginPurchaseInfo.getId();
                touristService.deleteCookie();
            }
            if (!StringUtils.isBlank(payerId)) {
                map.put("payerId", payerId);
                resMap.put("payerId",payerId);
            } else {
                map.put("payerId", userId);
                resMap.put("payerId", userId);
            }
            map.put("paymentId", paymentId);
            resMap.put("paymentId", paymentId);
            map.put("paymentPrice", Helper.transformF2Y(ordPaymentRecord.getPaymentTotalPrice()));
            JSONObject record = new JSONObject(resMap);
            ordPaymentRecord.setIsPaySuccess(1);
            ordPaymentRecord.setOperationTime(Utils.currentTimeSecond());
            ordPaymentRecord.setPaymentReturnRecord(record.toJSONString());
            ordPaymentRecord.update();
            //更改销售单
            OrdSalesOrder order = OrdSalesOrder.me().setSalesOrderNo(ordPaymentRecord.getOrderId()).get();
            map.put("orderNo",order.getSalesOrderNo());
            order.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_ING_COMPLETE.status());
            order.setPaymentName(ordPaymentRecord.getAddUserName());
            order.setPaymentTime(Utils.currentTimeSecond());
            order.setPaymentStreamNo(paymentId);
            order.setIsEnable(1);
            order.update();
            OrdSettlementConfirm.me().setId(ordPaymentRecord.getBalanceTheBooksNo()).delete();
        }
    }

    /**
     * 生成订单
     * @param ordBalanceTheBooks
     * @param id
     * @param paymentId
     * @param cliClienteleInfo
     * @param exchangeId
     */
    public void addSalesOrder(OrdBalanceTheBooks ordBalanceTheBooks, String[] id, String paymentId, CliClienteleInfo cliClienteleInfo, String exchangeId) {
        OrdSalesOrder ordSalesOrder = new OrdSalesOrder();
        OrdInvoiceAddress ordInvoiceAddress = OrdInvoiceAddress.me().setFkBalanceTheBooksId(ordBalanceTheBooks.getBalanceTheBooksNo()).get();
        String salesOrderNo = "PR" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        ordSalesOrder.setSalesOrderNo(salesOrderNo);
        ordSalesOrder.setId(IDSnowflake.id());
        Long totalVol = 0L;
        String activityId = null;
        List<OrdSalesOrderDetail> detailList = new ArrayList<>();

        CliClienteleReceiverAddress cliClienteleReceiverAddress = CliClienteleReceiverAddress.me().setId(ordBalanceTheBooks.getFkClienteleReceiverAddressId()).get();
        if (!Objects.isNull(cliClienteleReceiverAddress)) {
            //收货信息
            ordSalesOrder.setFkClienteleAddressId(cliClienteleReceiverAddress.getId());
            ordSalesOrder.setReceiverId(cliClienteleReceiverAddress.getId());
            ordSalesOrder.setReceiverPhone(cliClienteleReceiverAddress.getPhone());
            ordSalesOrder.setReceiverAddress(cliClienteleReceiverAddress.getAddress());
            ordSalesOrder.setReceiverCity(cliClienteleReceiverAddress.getCity());
            ordSalesOrder.setReceiverEmail(cliClienteleReceiverAddress.getEmail());
            ordSalesOrder.setReceiverSheet(cliClienteleReceiverAddress.getSheet());
            ordSalesOrder.setReceiverName(cliClienteleReceiverAddress.getClientSurname() + cliClienteleReceiverAddress.getClientName());
            ordSalesOrder.setReceiverPost(cliClienteleReceiverAddress.getPost());
            ordSalesOrder.setReceiverCountry(cliClienteleReceiverAddress.getCountry());
            if (Helper.isNotEmpty(cliClienteleReceiverAddress.getProvince())) {
                ordSalesOrder.setReceiverProvince(cliClienteleReceiverAddress.getProvince());
            }
        }else{
            OrdInvoiceAddress invoiceAddress = OrdInvoiceAddress.me().setId(ordBalanceTheBooks.getFkOrdInvoiceAddressId()).get();
            //收货信息
            ordSalesOrder.setReceiverPhone(invoiceAddress.getReceiptPhone());
            ordSalesOrder.setReceiverAddress(invoiceAddress.getReceiptAddress());
            ordSalesOrder.setReceiverCity(invoiceAddress.getReceiptCity());
            ordSalesOrder.setReceiverEmail(invoiceAddress.getReceiptEmail());
            ordSalesOrder.setReceiverSheet(invoiceAddress.getReceiptSheet());
            ordSalesOrder.setReceiverName(invoiceAddress.getReceiptSurname() + invoiceAddress.getReceiptName());
            ordSalesOrder.setReceiverPost(invoiceAddress.getReceiptPost());
            ordSalesOrder.setReceiverCountry(invoiceAddress.getReceiptCountry());
            if (Helper.isNotEmpty(invoiceAddress.getReceiptProvince())) {
                ordSalesOrder.setReceiverProvince(invoiceAddress.getReceiptProvince());
            }
        }
        ordSalesOrder.setAddTime(Utils.currentTimeSecond());
        ordSalesOrder.setAddUserName(cliClienteleInfo.getLoginName());
        ordSalesOrder.setAddUserId(cliClienteleInfo.getId());
        ordSalesOrder.setFkClienteleId(cliClienteleInfo.getId());
        ordSalesOrder.setClientName(cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName());
        ordSalesOrder.setClientEmail(cliClienteleInfo.getEmail());
        ordSalesOrder.setClientPhone(cliClienteleInfo.getPhone());
        ordSalesOrder.setTotalWeight(ordBalanceTheBooks.getTotalWeight());
        ordSalesOrder.setTotalProductNumber(ordBalanceTheBooks.getTotalProductNumber());
        ordSalesOrder.setTotalPaymentFree(ordBalanceTheBooks.getPaymentTotalPrice());
        ordSalesOrder.setTotalTransportsFree(ordBalanceTheBooks.getFreeSchemeTotalPrice());
        ordSalesOrder.setTransportsChannelId(ordBalanceTheBooks.getFkFreeSchemeId());
        ordSalesOrder.setTransportsChannelName(ordBalanceTheBooks.getFreeSchemeName());
        ordSalesOrder.setTotalPrice(ordBalanceTheBooks.getProductTotalPrice());
        ordSalesOrder.setProductDiscountPrice(ordBalanceTheBooks.getProductDiscountPrice());
        ordSalesOrder.setPaymentTime(ordBalanceTheBooks.getAddTime());
        ordSalesOrder.setPaymentName(cliClienteleInfo.getLoginName());
        ordSalesOrder.setPaymentStreamNo(paymentId);
        ordSalesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_ING_COMPLETE.status());
        ordSalesOrder.setReduceActivityId(ordBalanceTheBooks.getFkReduceActivityId());
        ordSalesOrder.setReduceActivityName(ordBalanceTheBooks.getReduceActivityName());
        ordSalesOrder.setReduceActivityResult(ordBalanceTheBooks.getReduceDiscountTypeName());
        ordSalesOrder.setCouponActivityId(ordBalanceTheBooks.getFkCouponActivityId());
        ordSalesOrder.setCouponActivityName(ordBalanceTheBooks.getCouponActivityName());
        ordSalesOrder.setCouponActivityResult(ordBalanceTheBooks.getCouponDiscountTypeName());
        ordSalesOrder.setFkCurrencyId(ordBalanceTheBooks.getFkCurrencyId());
        ordSalesOrder.setTaxAmount(ordBalanceTheBooks.getTaxRate());
        if (!StringUtils.isBlank(ordBalanceTheBooks.getFkSysCountryId())) {
            SysCountry sysCountry = SysCountry.me().setAlpha3(ordBalanceTheBooks.getFkSysCountryId()).get();
            if (!Objects.isNull(sysCountry)) {
                ordSalesOrder.setTaxRate(sysCountry.getTaxRate().toString());
            }
        }
        ordSalesOrder.setPaymentPlatformTypeId(ordBalanceTheBooks.getFkPaymentTypeId());
        ordSalesOrder.setPaymentPlatformName(ordBalanceTheBooks.getPaymentTypeName());
        ordSalesOrder.setCurrency(ordBalanceTheBooks.getCurrency());
        if (Helper.isNotEmpty(ordInvoiceAddress)) {
            ordSalesOrder.setFkInvoiceAddressId(ordInvoiceAddress.getId());
        }
        ordBalanceTheBooks.setIsRecall(0);
        ordBalanceTheBooks.setIsNeedRecall(0);
        ordBalanceTheBooks.update();
        ordSalesOrder.setRemark(ordBalanceTheBooks.getRemark());
        ordSalesOrder.insert();
        ordSalesOrder = OrdSalesOrder.me().setId(ordSalesOrder.getId()).get();
        //封装销售单详情
        for (String booksId : id) {
            OrdShoppingCart ordShoppingCart = OrdShoppingCart.me().setId(booksId).get();
            ProProductMaster proProductMaster = ProProductMaster.me().setId(ordShoppingCart.getFkProductId()).get();
            OrdSalesOrderDetail detail = new OrdSalesOrderDetail();
            OrdBalanceTheBooksCommond ordBalanceTheBooksCommond = new OrdBalanceTheBooksCommond();
            ordBalanceTheBooksCommond.setShoppingCartList(booksId);
            List<OrdBalanceTheBooks> balanceTheBooks = OrdBalanceTheBooks.me().setIsDiscard(0).find(ordBalanceTheBooksCommond);
            if (!balanceTheBooks.isEmpty()) {
                balanceTheBooks.forEach(books -> {
                    books.setIsNeedRecall(0).setIsRecall(1).update();
                });
            }
            detail.setFkSalesOrderId(ordSalesOrder.getId());
            detail.setSalesOrderNo(salesOrderNo);
            detail.setAddTime(Utils.currentTimeSecond());
            detail.setAddUserId(cliClienteleInfo.getId());
            detail.setAddUserName(cliClienteleInfo.getLoginName());
            detail.setFkProductId(proProductMaster.getId());
            detail.setFkProductTypeId(proProductMaster.getFkProductTypeId());
            ProProductType proProductType = ProProductType.me().setId(detail.getFkProductTypeId()).get();
            if(!Objects.isNull(proProductType)){
                detail.setProductTypeName(proProductType.getProductTypeName());
            }
            detail.setProductName(ordShoppingCart.getProductName());
            detail.setProductImageUrl(ordShoppingCart.getPrductPicImageUrl());
            detail.setProductNum(ordShoppingCart.getTotalProductNumber());
            detail.setProductBarcode(proProductMaster.getProductBarcode());
            detail.setProductSku(proProductMaster.getProductSku());
            detail.setProductMallPrice(Helper.transformY2F(sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(ordShoppingCart.getProductMallPrice()))));
            detail.setProductMarketPrice(Helper.transformY2F(sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(ordShoppingCart.getProductMarketPrice()))));
            detail.setProductActivityPrice(Helper.transformY2F(sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(ordShoppingCart.getProductPromotionPrice()))));
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
            detail.setCusPrice(Helper.transformY2F(sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(proProductMaster.getCusPrice()))));
            detail.setCusProductType(proProductMaster.getCusProductType());
            detail.setCusZhDescription(proProductMaster.getCusZhDescription());
            detail.setLength(proProductMaster.getLength());
            detail.setWidth(proProductMaster.getWidth());
            detail.setHeight(proProductMaster.getHeight());
            detail.setFkBrandId(proProductMaster.getFkBrandId());
            ProBrand proBrand = ProBrand.me().setId(detail.getFkBrandId()).get();
            if(!Objects.isNull(proBrand)){
                detail.setBrandName(proBrand.getBrandName());
            }
            detail.setProductSubSku(ordShoppingCart.getProductSubSku());
            detail.setProductSpec(ordShoppingCart.getProductSpec());
            detail.setLocalhostSn(ordSalesOrder.getLocalhostSn());
            totalVol = totalVol + (detail.getUnitVol() * detail.getProductNum());
            if (!StringUtils.isBlank(ordShoppingCart.getFkActivityId())) {
                activityId = ordShoppingCart.getFkActivityId();
            }
            detail.insert();
            detailList.add(detail);
            // 减库存
            ProProductStock proProductStock = ProProductStock.me().setProductSubSku(ordShoppingCart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
            proProductStock.setProductStockNumber(proProductStock.getProductStockNumber() - ordShoppingCart.getTotalProductNumber());
            proProductStock.update();

            // 加商品的销售数量
            if (proProductMaster.getSales() != null) {
                proProductMaster.setSales(proProductMaster.getSales() + ordShoppingCart.getTotalProductNumber());
            } else {
                proProductMaster.setSales(proProductMaster.getSales());
            }
            proProductMaster.update();

        }
        ordSalesOrder.setTotalVol(totalVol);
        ActPromotionActivity actPromotionActivity = ActPromotionActivity.me().setId(activityId).get();
        if (!Objects.isNull(actPromotionActivity)) {
            ordSalesOrder.setPromotionActivityId(actPromotionActivity.getId());
            ordSalesOrder.setPromotionActivityName(actPromotionActivity.getActivityName());
            ordSalesOrder.setCouponActivityResult("抵扣");
            if (actPromotionActivity.getDiscountTypeId().equals("01")) {
                ordSalesOrder.setCouponActivityResult("折扣");
            }
        }
        ordSalesOrder.update();
        //物流状态和email发送通知
        addOrderLogisticsStatus(ordSalesOrder, detailList);
    }

    public void doCreatePaymentAsiabill(PaymentDto paymentDto, Map<String, Object> map) throws Exception {
        //测试数据构建
        OrdPaymentRecord ordPaymentRecord = CreatePayData(paymentDto);
        SysOptions merNo = SysOptions.me().setOptionKey("asiabill_mer_no").get();
        if (Objects.isNull(merNo)) {
            throw new ToastException("商户号为空!");
        }
        //获取商户接入号
        SysOptions gatewayNo = SysOptions.me().setOptionKey("asiabill_gateway_no").get();
        if (Objects.isNull(gatewayNo)) {
            throw new ToastException("网关接入号为空!");
        }
        //获取商户密钥
        SysOptions signKey = SysOptions.me().setOptionKey("asiabill_sign_key").get();
        if (Objects.isNull(signKey)) {
            throw new ToastException("密钥为空!");
        }
        map.put("merNo", merNo.getOptionValue());
        map.put("gatewayNo", gatewayNo.getOptionValue());
        map.put("orderNo", ordPaymentRecord.getOrderId());
        map.put("orderCurrency", "USD");
        OrdSalesOrder ordOrder = OrdSalesOrder.me().setSalesOrderNo(paymentDto.getSource()).findFirst();

        OrdSettlementConfirm ordSettlementConfirm = OrdSettlementConfirm.me().setId(ordPaymentRecord.getBalanceTheBooksNo()).get();
        if(!Objects.isNull(ordSettlementConfirm)){
            SettlementConfirmDto settlementConfirmDto = JSON.parseObject(ordSettlementConfirm.getContent(), SettlementConfirmDto.class);
            if(settlementConfirmDto.getShippingAndInvoice()){
                map.put("firstName", settlementConfirmDto.getShippingAddress().getName());
                map.put("lastName", settlementConfirmDto.getShippingAddress().getSurname());
            }else{
                map.put("firstName", settlementConfirmDto.getInvoiceAddress().getName());
                map.put("lastName", settlementConfirmDto.getInvoiceAddress().getSurname());
            }
        }
        map.put("email", ordOrder.getClientEmail());
        map.put("phone", "123");
        map.put("paymentMethod", "Credit Card");
        map.put("country", "US");
        map.put("state", "Collierville");
        map.put("city", "110 North Tree Drive");
        map.put("address", "110 North Tree Drive");
        map.put("zip", "38017");
        map.put("orderAmount", String.format("%.2f", Helper.transformF2Y(ordPaymentRecord.getPaymentTotalPrice())));
        String signInfo = getSha256(map.get("merNo").toString() +
                map.get("gatewayNo").toString() +
                map.get("orderNo").toString() +
                map.get("orderCurrency").toString() +
                map.get("orderAmount").toString() +
                map.get("returnUrl").toString() +
                signKey.getOptionValue());
        map.put("signInfo", signInfo);
    }

    public static String getSha256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();

    }

    public R doCreatePayReality(OrdBalanceTheBooksDto ordBalanceTheBooksDto, Map<String, Object> map, HttpServletRequest request){
        //测试数据构建
        OrdPaymentRecord ordPaymentRecord = CreateData(ordBalanceTheBooksDto);
        String paymentId = "py" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        if (StringUtils.isNotBlank(ordPaymentRecord.getId())) {
            OrdPaymentRecord paymentRecord = OrdPaymentRecord.me().setId(ordPaymentRecord.getId()).get();
            OrdBalanceTheBooks ordBalanceTheBooks = OrdBalanceTheBooks.me().setBalanceTheBooksNo(ordPaymentRecord.getBalanceTheBooksNo()).get();
            String[] id = ordBalanceTheBooks.getShoppingCartList().split(",");
            if (User.isAuth()) {
                for (String booksId : id) {
                    OrdShoppingCart.me().setId(booksId).setIsGenBalanceAccounts(1).setIsGenOrder(1).update();
                }
                CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
                paymentRecord.setIsPaySuccess(1);
                map.put("paymentId", paymentId);
                map.put("payerId", cliClienteleInfo.getId());
                JSONObject record = new JSONObject(map);
                paymentRecord.setPaymentReturnRecord(record.toJSONString());
                paymentRecord.update();
                map.put("balanceTheBooksNo", ordBalanceTheBooks.getBalanceTheBooksNo());
                map.put("loginName", cliClienteleInfo.getLoginName());
                map.put("paymentPrice", Helper.transformF2Y(ordPaymentRecord.getPaymentTotalPrice()));
                //生成销售单
                addSalesOrder(ordBalanceTheBooks, id, paymentId, cliClienteleInfo, exchangeId);
            }

        }
        return R.ok();
    }

    public void addOrderLogisticsStatus(OrdSalesOrder order, List<OrdSalesOrderDetail> detailList) {

        List<OrdOrderDetailSendEmailDto> ordOrderDetailList = new ArrayList<>();
        OrdOrderSendEmailDto orderDto = new OrdOrderSendEmailDto();
        OrdOrderLogisticsStatus status = new OrdOrderLogisticsStatus();
        status.setIsSign(0);
        status.setAddUserId(order.getAddUserId());
        status.setAddUserName(order.getAddUserName());
        status.setAddTime(Utils.currentTimeSecond());
        status.setFkSalesOrderId(order.getId());
        status.setSalesOrderStatus("待处理");
        status.insert();
        if (order.getPaymentPlatformTypeId().equals(PaymentEnum.PAYMENT_TYPE_OFFLINE.type())) {
            order.setSalesOrderStatus(OrdSalesOrderStatusEnum.CREATE_COMPLETE.status());
            order.update();
            orderDto.setPaymentMethod(order.getPaymentPlatformName());
        } else {
            status.setId(null);
            status.setAddTime(Utils.currentTimeSecond());
            status.setSalesOrderStatus("已付款");
            status.insert();
            orderDto.setPaymentMethod(order.getPaymentPlatformName());
        }
        Long totalSubtractPrice = 0L;
        for (OrdSalesOrderDetail detail : detailList) {
            OrdOrderDetailSendEmailDto dto = addOrderDetailDto(detail,totalSubtractPrice);
            ordOrderDetailList.add(dto);
            setReport(detail,order);
        }
        orderDto.setTotalSubtractPrice(Helper.transformF2Y(totalSubtractPrice).toString());
        orderDto.setReceiverAddress(order.getReceiverAddress());
        if (Helper.isNotEmpty(order.getReceiverCountry())) {
            SysCountry country = SysCountry.me().setAlpha3(order.getReceiverCountry()).get();
            orderDto.setReceiverCountry(country.getName() + " (" + country.getNameCn() + ")");
        }
        if (Helper.isNotEmpty(order.getReceiverCity())) {
            orderDto.setReceiverCity(order.getReceiverCity());
        }
        if (Helper.isNotEmpty(order.getReceiverProvince())) {
            orderDto.setReceiverProvince(order.getReceiverProvince());
        }
        if (Helper.isNotEmpty(order.getReceiverPhone())) {
            orderDto.setReceiverPhone(order.getReceiverPhone());
        }
        orderDto.setOrderId(order.getId());
        orderDto.setProductTotalPrice(Helper.transformF2Y(order.getTotalPrice()).toString());
        orderDto.setList(ordOrderDetailList);
        if (Helper.isNotEmpty(order.getClientEmail())) {
            orderDto.setEmail(order.getClientEmail());
        }
        if (Helper.isNotEmpty(order.getReceiverEmail())) {
            if (!order.getClientEmail().equals(order.getReceiverEmail())) {
                orderDto.setReceiverEmail(order.getReceiverEmail());
            }
        }
        orderDto.setReceiverPost(order.getReceiverPost());
        orderDto.setEmailSendTime(Helper.getDateEngLiSh());
        if (Helper.isNotEmpty(order.getFkInvoiceAddressId())) {
            OrdInvoiceAddress ordInvoiceAddress = OrdInvoiceAddress.me().setId(order.getFkInvoiceAddressId()).get();
            SysCountry sysCountry = SysCountry.me().setAlpha3(ordInvoiceAddress.getReceiptCountry()).get();
            orderDto.setInvoiceAddress(ordInvoiceAddress.getReceiptAddress());
            orderDto.setInvoicePost(ordInvoiceAddress.getReceiptPost());
            orderDto.setInvoiceCountry(sysCountry.getName() + " （" + sysCountry.getNameCn() + "）");
            if (Helper.isNotEmpty(ordInvoiceAddress.getReceiptProvince())) {
                orderDto.setInvoiceProvince(ordInvoiceAddress.getReceiptProvince());
            }
            if (Helper.isNotEmpty(ordInvoiceAddress.getReceiptPhone())) {
                orderDto.setInvoicePhone(ordInvoiceAddress.getReceiptPhone());
            }
            orderDto.setInvoiceCity(ordInvoiceAddress.getReceiptCity());
            orderDto.setInvoiceEmail(ordInvoiceAddress.getReceiptEmail());
        }
        orderDto.setOrderNo(String.valueOf(order.getLocalhostSn()));
        orderDto.setCurrency(order.getCurrency());
        orderDto.setTotalTransportsFree(Helper.transformF2Y(order.getTotalTransportsFree()).toString());
        orderDto.setTotalPaymentFree(Helper.transformF2Y(order.getTotalPaymentFree()).toString());
        orderDto.setTaxAmount(Helper.transformF2Y(order.getTaxAmount()).toString());
        orderDto.setTaxRate(order.getTaxRate());
        orderDto.setProductDiscountPrice(Helper.transformF2Y(order.getProductDiscountPrice()).toString());
        SysOptions sysOptions = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get();
        SysOptions sysOptionsEmail = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_EMAIL.key()).get();
        String orderDetailsUrl = orderManageService.orderDetailsUrl(order.getId(), order.getFkClienteleId(), sysOptions);
        orderDto.setOrderDetailUrl(orderDetailsUrl);
        orderDto.setContactEmail(sysOptionsEmail.getOptionValue());
        orderDto.setShopUrl(sysOptions.getOptionValue());
        doSendOrderPayEmail(orderDto);
        HttpServletRequest request = WebUtil.getHttpServletRequest();
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        ProReportDevice device=new ProReportDevice();
        device.setType(ReportDeviceEnum.POPULAR_TRANSACTIONE.type());
        device.setAddTime(Utils.currentTimeSecond());
        device.setDevice(ua.getPlatform().toString());
        device.setSource(SourceEnum.POPULAR_PLATFORM.tile());
        device.setIp(Helper.getIpAddress(request));
        device.insert();
    }

    public R addOrdOfflinePaymentRecord(OrdPaymentOfflineRecord record) {
        if (!User.isAuth()) {
            return R.error("用户未登录不支持线下支付");
        }
        if (Helper.isNotEmpty(record.getPaymentState()) && record.getPaymentState().equals(PaymentOfflineStateEnum.PAYMENT_STATE_REJECT.state())) {
            if (Helper.isEmpty(record.getPaymentVoucherTag())) {
                return R.error("请补充凭证！");
            }
        }
        if (Helper.isEmpty(record.getPaymentVoucher())) {
            return R.error("请上传凭证！");
        }
        if (Helper.isEmpty(record.getFkSalesOrderId())) {
            return R.error("订单信息不能为空！");
        }
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        record.setPaymentName(cliClienteleInfo.getClientName());
        record.setAddTime(Utils.currentTimeSecond());

        if (Helper.isEmpty(record.getPaymentEmail())) {
            record.setPaymentEmail(cliClienteleInfo.getEmail());
        }
        if (Helper.isNotEmpty(record.getPaymentState())) {
            record.setPaymentState(2);
        } else {
            record.setPaymentState(0);
        }
        if (Helper.isNotEmpty(record.getId())) {
            record.update();
        } else {
            record.insert();
        }
        return R.ok();
    }


    private OrdOrderDetailSendEmailDto addOrderDetailDto(OrdSalesOrderDetail detail,Long totalSubtractPrice) {
        OrdOrderDetailSendEmailDto dto = new OrdOrderDetailSendEmailDto();
        if (Helper.isNotEmpty(detail.getProductActivityPrice()) && detail.getProductActivityPrice() > 0 && detail.getProductActivityPrice() < detail.getProductMallPrice()) {
            Long subtractPrice = detail.getProductMallPrice()-detail.getProductActivityPrice();
            dto.setProductActivityPrice(Helper.transformF2Y(detail.getProductActivityPrice()).toString());
            dto.setSubtractPrice(Helper.transformF2Y(subtractPrice).toString());
            totalSubtractPrice = totalSubtractPrice+detail.getProductMallPrice();
        }
        dto.setProductMallPrice(Helper.transformF2Y(detail.getProductMallPrice()).toString());
        if (detail.getProductImageUrl().contains("http:")||detail.getProductImageUrl().contains("https:")){
            dto.setImageUrl(detail.getProductImageUrl());
        }else {
            dto.setImageUrl("http:"+detail.getProductImageUrl());
        }
        dto.setProductSku(detail.getProductSku());
        dto.setProductName(detail.getProductName());
        dto.setProductNum(detail.getProductNum().toString());
        dto.setSpec(detail.getProductSpec());
//        if (Helper.isNotEmpty(detail.getFkProductColorSizeId())) {
//            dto.setSpec(detail.getProductSpec());
//        }
        return dto;
    }


    private void doSendOrderPayEmail(OrdOrderSendEmailDto orderDto) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(() -> {
            SysEmailSendRecord emailSendRecord = new SysEmailSendRecord();
            List<String> list = new ArrayList<>();
            SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setEmailSendMoment(EmailTemplateTypeEnum.TYPE_CONFIRM_ORDER.type()).get();
            String theme= DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
            if (Helper.isNotEmpty(sysEmailTemplate.getTheme())){
                theme= sysEmailTemplate.getTheme();
            }
            if (sysEmailTemplate.getIsEnable().equals(BooleanEnum.TRUE.getValue())) {
                Email email = null;
                if (Helper.isNotEmpty(orderDto.getEmail()) && Helper.isNotEmpty(orderDto.getReceiverEmail())) {
                    email = Email.create().to(orderDto.getEmail(), orderDto.getReceiverEmail()).subject(theme);
                    list.add(orderDto.getEmail());
                    list.add(orderDto.getReceiverEmail());
                    emailSendRecord.setSendPersonList(Helper.toJson(list));
                } else {
                    email = Email.create().to(orderDto.getEmail()).subject(theme);
                    list.add(orderDto.getEmail());
                    emailSendRecord.setSendPersonList(Helper.toJson(list));
                }
                Map<String, Object> map = new HashMap<>();
                map.put("order", orderDto);
                map.put("theme", theme);
                R send = service.sendMail(map, email, sysEmailTemplate.getTemplateShowContent());
                emailSendRecord.setAddTime(Utils.currentTimeSecond());
                emailSendRecord.setFkEmailTemplateId(sysEmailTemplate.getId());
                emailSendRecord.setEmailTemplateName(sysEmailTemplate.getEmailTemplateName());
                emailSendRecord.setSendTime(Utils.currentTimeSecond());
                emailSendRecord.setSendContent(Helper.toJson(orderDto));
                if (send.getState() == 200) {
                    logger.info("发送email成功");
                    emailSendRecord.setIsSuccess(BooleanEnum.TRUE.getValue());
                } else {
                    logger.info("发送email失败原因：{}", send.getMessage());
                    emailSendRecord.setIsSuccess(BooleanEnum.FALSE.getValue());
                }
                emailSendRecord.insert();
            }
        });
    }

    private Map<String, String> paypalSdkConfig(String mode) {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    private OAuthTokenCredential authTokenCredential(String mode, String clientId, String clientSecret) {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig(mode));
    }

    private APIContext apiContext(String mode, String clientId, String clientSecret) throws PayPalRESTException {
        APIContext apiContext = new APIContext(authTokenCredential(mode, clientId, clientSecret).getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig(mode));
        return apiContext;
    }

    private void setReport(OrdSalesOrderDetail detail, OrdSalesOrder order) {
        ProReportProduct reportProduct = null;
        if (Helper.isNotEmpty(detail.getAddUserId())) {
            Long count = OrdSalesOrderDetail.me().setAddUserId(detail.getAddUserId()).setFkProductId(detail.getFkProductId()).findCount();
            if (count>0){
                reportProduct = new ProReportProduct();
                reportProduct.setFkProductId(detail.getFkProductId());
                reportProduct.setTitle(detail.getProductName());
                reportProduct.setQuantity(detail.getProductNum());
                reportProduct.setType(ReportProductEnum.POPULAR_PRODUCT_REPURCHASE.type());
                reportProduct.setFkClienteleId(detail.getAddUserId());
                reportProduct.setAddress(SysCountry.me().setAlpha3(order.getReceiverCountry()).get().getNameCn());
                reportService.reportRecordingAdd(reportProduct);
            }
        }
        reportProduct = new ProReportProduct();
        reportProduct.setFkProductId(detail.getFkProductId());
        reportProduct.setTitle(detail.getProductName());
        reportProduct.setQuantity(detail.getProductNum());
        reportProduct.setAddress(order.getReceiverCountry());
        reportProduct.setType(ReportProductEnum.POPULAR_PRODUCT.type());
        if (Helper.isNotEmpty(detail.getAddUserId())) {
            reportProduct.setFkClienteleId(detail.getAddUserId());
        }
        reportService.reportRecordingAdd(reportProduct);
    }

    public OrdPaymentRecord CreatePayData(PaymentDto paymentDto) {
        //封装销售单信息
        OrdPaymentRecord ordPaymentRecord = new OrdPaymentRecord();
        Long count = OrdPaymentRecord.me().setOrderId(paymentDto.getSource()).findCount();
        if(count>0){
            ordPaymentRecord = OrdPaymentRecord.me().setOrderId(paymentDto.getSource()).get();
        }
        OrdSalesOrder ordOrder = OrdSalesOrder.me().setSalesOrderNo(paymentDto.getSource()).findFirst();
        ordPaymentRecord.setFkClienteleId(ordOrder.getAddUserId());
        ordPaymentRecord.setPayId(paymentDto.getTransactionId());
        ordPaymentRecord.setOrderId(ordOrder.getSalesOrderNo());
        ordPaymentRecord.setBalanceTheBooksNo(paymentDto.getSettlementId());

        // 用户名称、订单号、支付交易号 查询一次记录如果存在直接返回
        OrdPaymentRecord dbOrdPaymentRecord = ordPaymentRecord.findFirst();
        if(!Objects.isNull(dbOrdPaymentRecord)) {
            return dbOrdPaymentRecord;
        }
        optionOrdPaymentRecordData(ordOrder, ordPaymentRecord);

        return ordPaymentRecord;
    }


    private void optionOrdPaymentRecordData(OrdSalesOrder ordOrder, OrdPaymentRecord ordPaymentRecord) {
        //封装交易信息
        ordPaymentRecord.setPaymentTotalPrice(ordOrder.getTotalPaymentFree());
        ordPaymentRecord.setFreeSchemeTotalPrice(ordOrder.getTotalTransportsFree());
        ordPaymentRecord.setProductTotalPrice(ordOrder.getTotalPrice());
        ordPaymentRecord.setProductDiscountPrice(ordOrder.getProductDiscountPrice());
        ordPaymentRecord.setOrderId(ordOrder.getSalesOrderNo());

        ordPaymentRecord.setPaymentTime(Utils.currentTimeSecond());
        ordPaymentRecord.setAddTime(Utils.currentTimeSecond());
        ordPaymentRecord.setAddUserId(ordOrder.getAddUserId());
        ordPaymentRecord.setIsPaySuccess(0);
        //获取支付方式
        OrdPaymentType ordPaymentType = OrdPaymentType.me().setPaymentTypeName(ordOrder.getPaymentPlatformName()).get();
        if (!Objects.isNull(ordPaymentType)) {
            ordPaymentRecord.setFkPaymentTypeId(ordPaymentType.getId());
            ordPaymentRecord.setPaymentTypeName(ordPaymentType.getPaymentTypeName());
        }
        ordPaymentRecord.insertOrUpdate();
    }

}
