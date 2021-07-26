package com.mazentop.modules.user.controller;

import com.mazentop.entity.OrdBalanceTheBooks;
import com.mazentop.entity.OrdPaymentOfflineRecord;
import com.mazentop.entity.OrdPaymentRecord;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.modules.user.dto.PaymentDto;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.user.service.PaypalService;
import com.mazentop.util.Helper;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/20
 * @description:
 */
@Controller
@RequestMapping(PaymentController.modelPath)
public class PaymentController extends BaseController {

    public static final String PAYPAL_SUCCESS_URL = "/success";
    public static final String PAYPAL_CANCEL_URL = "/cancel";
    public static final String modelPath = "/paymentInfo";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;


    @GetMapping()
    public String index() {
        return "/web/payment/index";
    }

    @PostMapping("/pay")
    @ResponseBody
    public Result pay(HttpServletRequest request,ModelMap modelMap,@RequestBody PaymentDto paymentDto) {
        String cancelUrl = Helper.getBaseURl(request) + modelPath + PAYPAL_CANCEL_URL;
        String successUrl = Helper.getBaseURl(request) + modelPath + PAYPAL_SUCCESS_URL;
        return Result.build(() -> {
            Map<String, Object> map = new HashMap<>(1);
            try {
                    HttpResponse<Order> orderResponse = paypalService.doCreatePayments(paymentDto, cancelUrl, successUrl);
                    for (LinkDescription link : orderResponse.result().links()) {
                        if (link.rel().equals("approve")) {
                            map.put("url", link.href());
                            map.put("orderId",orderResponse.result().id());
                            map.put("code", 200);
                            return map;
                        }
                    }
            } catch (PayPalRESTException e) {
                log.error(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("settlementId",paymentDto.getSettlementId());
            map.put("url", "/web/paymentInfo/failure");
            map.put("code", 500);
            return map;
        });
    }

    @PostMapping("/asiabill")
    @ResponseBody
    public Result payAsiabill(HttpServletRequest request, @RequestBody PaymentDto paymentDto) {
        String cancelUrl = Helper.getBaseURl(request) + modelPath + PAYPAL_CANCEL_URL;
        String successUrl = Helper.getBaseURl(request) + modelPath + "/callbacks";
        Map<String, Object> map = new HashMap<>(1);
        return Result.build(() -> {
            map.put("callbackUrl", cancelUrl);
            map.put("returnUrl", successUrl);
            paypalService.doCreatePaymentAsiabill(paymentDto, map);
            return map;
        });
    }

    /**
     * payPal 取消支付
     */
    @GetMapping(PAYPAL_CANCEL_URL)
    public String cancelPay(@RequestParam("payId") String payId) {
        return "/web/payment/cancel";
    }

    /**
     * payPal 回调
     */
    @GetMapping("/success")
    @ResponseBody
    public Result successPay(@RequestParam("token") String paymentId, @RequestParam("PayerID") String payerId, ModelMap map) {
        return Result.build(() -> {
            Map<String,Object> messageMap = new HashMap<>(1);
            OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setPayId(paymentId).get();
            try {
                HttpResponse<Order> captureOrderResponse = paypalService.executePayments(paymentId);
                if (captureOrderResponse.statusCode() == 201) {
                    if (captureOrderResponse.result().status().equals("COMPLETED")) {
                        paypalService.doAddPaySalesOrder(ordPaymentRecord.getId(), paymentId, payerId, map);
                        messageMap.put("paymentId", paymentId);
                        messageMap.put("payerId", payerId);
                        messageMap.put("loginName", map.get("loginName"));
                        messageMap.put("code",200);
                        return messageMap;
                    }
                }
                messageMap.put("settlementId",ordPaymentRecord.getBalanceTheBooksNo());
            } catch (PayPalRESTException e) {
                log.error(e.getMessage());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageMap.put("code","500");
            messageMap.put("settlementId",ordPaymentRecord.getBalanceTheBooksNo());
            return messageMap;
        });
    }

    @PostMapping("/payReality")
    @ResponseBody
    public Result payReality(HttpServletRequest request, @RequestBody OrdBalanceTheBooksDto ordBalanceTheBooksDto) {
        if (User.isAuth()){
            Map<String, Object> map = new HashMap<>(1);
            return Result.build(() -> {
                paypalService.doCreatePayReality(ordBalanceTheBooksDto, map,request);
                return map;
            });
        }
        return Result.toast("暂不支持无登录支付");

    }

    @GetMapping("/realitySuccess")
    public String realitySuccess(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId, @RequestParam("balanceTheBooksNo") String balanceTheBooksNo, @RequestParam("loginName") String loginName, @RequestParam("paymentPrice") String paymentPrice, ModelMap map) {
        map.put("paymentId", paymentId);
        map.put("payerId", payerId);
        map.put("balanceTheBooksNo", balanceTheBooksNo);
        OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setBalanceTheBooksNo(balanceTheBooksNo).get();
        if (!Objects.isNull(ordPaymentRecord)) {
            map.put("payTypeName", ordPaymentRecord.getPaymentTypeName());
        }
        map.put("loginName", loginName);
        map.put("paymentPrice", paymentPrice);
        return "/web/payment/succeed";
    }

    @PostMapping("/callbacks")
    public String callback(@RequestParam("tradeNo") String tradeNo, @RequestParam("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus, @RequestParam("orderInfo") String orderInfo, ModelMap map, HttpServletRequest request,HttpServletResponse response) {
        OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setOrderId(orderNo).get();
        if (orderStatus.equals("1")) {
            try {
                paypalService.doAddPaySalesOrder(ordPaymentRecord.getId(), tradeNo, null, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "/web/payment/succeed";
        } else {
            map.put("settlementId",ordPaymentRecord.getBalanceTheBooksNo());
            return "/web/payment/failure";
        }
    }

    @GetMapping("/failure")
    public String registered(ModelMap map) {
        return templatePath("payment/failure");
    }

    @GetMapping("/registeredFailure")
    public String registeredFailure(ModelMap map,@RequestParam("settlementId") String settlementId) {
        map.put("settlementId",settlementId);
        return "/web/payment/failure";
    }
    @GetMapping("/registeredSucceed")
    public String registeredSucceed(ModelMap map,@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId,@RequestParam("loginName")String loginName) {
        OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setPayId(paymentId).get();
        map.put("paymentId", paymentId);
        map.put("payerId", payerId);
        map.put("orderNo", ordPaymentRecord.getOrderId());
        map.put("paymentPrice", Helper.transformF2Y(ordPaymentRecord.getPaymentTotalPrice()));
        map.put("payTypeName", ordPaymentRecord.getPaymentTypeName());
        map.put("loginName", loginName);
        return "/web/payment/succeed";
    }

    @GetMapping("/offline/{id}")
    @ResponseBody
    public Result getOfflineDetail(@PathVariable String id){
        OrdPaymentOfflineRecord ordPaymentOfflineRecord = OrdPaymentOfflineRecord.me().setFkSalesOrderId(id).get();
        return Result.build(()->
                ordPaymentOfflineRecord
        );
    }

    @PostMapping("/pay/offline")
    @ResponseBody
    public R editOfflinePayment(@RequestBody OrdPaymentOfflineRecord record){
        return paypalService.addOrdOfflinePaymentRecord(record);
    }



}