package com.mazentop.modules.user.controller;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.user.dto.PaymentDto;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.user.service.OrderBooksService;
import com.mazentop.modules.user.service.PaypalService;
import com.mazentop.modules.web.service.SettlementService;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author: dengy
 * @date: 2020/5/11
 * @description:
 */
@Controller
@RequestMapping("/orderBooks")
public class OrderBooksController extends BaseController {

    @Autowired
    OrderBooksService orderBooksService;

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    PaypalService paypalService;

    @Autowired
    SettlementService settlementService;


    @PostMapping("/createOrder")
    @ResponseBody
    public Result createOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam("productId") String productId, @RequestParam("num") Integer num,@RequestParam("productSubSku") String productSubSku) {
        if(Helper.isEmpty(productId)){
            return Result.toast("商品信息获取失败!");
        }
        if(num == null || num<=0){
            return Result.toast("数量不能小于0!");
        }
        ProProductStock proProductStock = ProProductStock.me().setProductSubSku(productSubSku).setIsEnable(BooleanEnum.TRUE.getValue()).get();
        if (proProductStock.getProductStockNumber() < num) {
            return Result.toast("商品库存不足！");
        }
        return Result.build(() -> {
            Map<String, Object> map = new HashMap<>(1);
            String cancelUrl = Helper.getBaseURl(request) + PaymentController.modelPath + PaymentController.PAYPAL_CANCEL_URL;
            String successUrl = Helper.getBaseURl(request) + PaymentController.modelPath + PaymentController.PAYPAL_SUCCESS_URL;
            List<String> idList = orderBooksService.getCartList(productId,productSubSku,num);
            List<OrdShoppingCart> notLoginCartList = orderBooksService.getNotLoginCartList(productId,productSubSku,num,request,response);

            List<Map<String,Object>>cartList = orderBooksService.getShoppingCartList(idList,notLoginCartList);

            OrdBalanceTheBooksDto ordBalanceTheBooksDto = orderBooksService.getBooksDto(cartList,request,response);

            /*HttpResponse<Order> orderResponse = orderBooksService.doCreatePayments(ordBalanceTheBooksDto, cancelUrl, successUrl, request, response);
            for (LinkDescription link : orderResponse.result().links()) {
                if (link.rel().equals("approve")) {
                    map.put("url", link.href());
                    map.put("orderId",orderResponse.result().id());
                    map.put("code", 200);
                    return map;
                }
            }*/
            return map;
         });
    }


    @GetMapping("/getOrderData")
    @ResponseBody
    public Result getOrderData(@RequestParam("token") String token,@RequestParam("payerID") String payerID) {
        return Result.build(() -> {
            Map<String, Object> map = new HashMap<>(1);
            String settlementId = orderBooksService.getOrder(token);
            if(Helper.isEmpty(settlementId)){
                return Result.toast("系统异常!");
            }
            map.put("settlementId",settlementId);
            return map;
        });
    }

    /**
     * 跳转结算
     */
    @GetMapping("/jumpSettlement/{booksId}")
    public String jumpSettlement(ModelMap modelMap,@PathVariable("booksId") String booksId) {
        modelMap.put("booksId",booksId);
        return "/web/user/settlement_buyPay";
    }


    @PostMapping("/completeOrder")
    @ResponseBody
    public Result completeOrder(@RequestBody PaymentDto paymentDto,ModelMap map,  HttpServletRequest request) {
        return Result.build(() -> {
            Map<String,Object> messageMap = new HashMap<>(1);
            OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setBalanceTheBooksNo(paymentDto.getSettlementId()).get();
            try {
                //修改结算单信息
                HttpResponse<Order> captureOrderResponse = orderBooksService.doUpdateOrdBooksData(paymentDto.getSource(),ordPaymentRecord,request);
                if(captureOrderResponse != null) {
                    if (captureOrderResponse.statusCode() == 201) {
                        String paymentId = captureOrderResponse.result().id();
                        String payerId = captureOrderResponse.result().payer().payerId();
                        if (captureOrderResponse.result().status().equals("COMPLETED")) {
                            paypalService.doAddPaySalesOrder(ordPaymentRecord.getId(), paymentId, payerId, map);
                            messageMap.put("paymentId", paymentId);
                            messageMap.put("payerId", payerId);
                            messageMap.put("loginName", map.get("loginName"));
                            messageMap.put("code",200);
                            return messageMap;
                        }
                    }
                }
                messageMap.put("settlementId",ordPaymentRecord.getBalanceTheBooksNo());
            }  catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageMap.put("code","500");
            messageMap.put("settlementId",ordPaymentRecord.getBalanceTheBooksNo());
            return messageMap;
        });
    }
    @GetMapping("/registeredFailure")
    public String registeredFailure(ModelMap map,@RequestParam("booksId") String booksId) {
        map.put("booksId",booksId);
        return "/web/payment/failures";
    }

    @PostMapping("/createCartOrder")
    @ResponseBody
    public Result createCartOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrdBalanceTheBooksDto booksDto) {
        if(booksDto.getOrdShoppingCartList().isEmpty()){
            return Result.toast("商品信息获取失败!");
        }
        return Result.build(() -> {
            Map<String, Object> map = new HashMap<>(1);
            String cancelUrl = Helper.getBaseURl(request) + PaymentController.modelPath + PaymentController.PAYPAL_CANCEL_URL;
            String successUrl = Helper.getBaseURl(request) + PaymentController.modelPath + PaymentController.PAYPAL_SUCCESS_URL;

            List<String> cartList = ordShoppingCartService.getCartIds(request);
            String settlementId = settlementService.doSaveSettlement(cartList,true);
            HttpResponse<Order> orderResponse = orderBooksService.doCreatePayments(settlementId, cancelUrl, successUrl, request);
            for (LinkDescription link : orderResponse.result().links()) {
                if (link.rel().equals("approve")) {
                    map.put("url", link.href());
                    map.put("orderId",orderResponse.result().id());
                    map.put("code", 200);
                    return map;
                }
            }
            return map;
        });
    }
}
