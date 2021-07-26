package com.paypal;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.dto.SettlementDto;
import com.mazentop.util.Helper;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PayCrateOrder {

    @Autowired
    OrdShoppingCartService ordShoppingCartService;
    /**
     * Method to create complete order body with <b>AUTHORIZE</b> intent
     *
     * @return OrderRequest with created order request
     */
    private OrderRequest buildCompleteRequestBody(String cancelUrl, String successUrl, OrdPaymentRecord ordPaymentRecord, SettlementDto settlementDto) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext().brandName("EXAMPLE INC").landingPage("BILLING")
                .cancelUrl(cancelUrl + "?payId=" + ordPaymentRecord.getId()).returnUrl(successUrl + "?payId=" + ordPaymentRecord.getId()).userAction("CONTINUE")
                .shippingPreference("GET_FROM_FILE");
        orderRequest.applicationContext(applicationContext);
        //获取结算单商品数据
        List<Item> itemList = new ArrayList<>();
        if(!settlementDto.getCarts().isEmpty()){
            settlementDto.getCarts().forEach(cart->{
                BigDecimal price;
                String sku = cart.getProductSubSku();
                //判断是否优惠价格
                price =  Helper.transformF2Y(cart.getProductMallPrice());
                if(cart.getProductPromotionPrice() != null && cart.getProductPromotionPrice() !=0){
                    price = Helper.transformF2Y(cart.getProductPromotionPrice());
                }
                //封装商品详情数据
                itemList.add(new Item().name(cart.getProductName()).description(cart.getProductSpec()).sku(sku)
                        .unitAmount(new Money().currencyCode("USD").value(price.toString()))
                        .tax(new Money().currencyCode("USD").value("0.0")).quantity(cart.getTotalProductNumber().toString())
                        .category("PHYSICAL_GOODS"));
            });
        }
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
                .description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(settlementDto.getTotalPrice())
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(settlementDto.getSubTotalPrice()))
                                .shipping(new Money().currencyCode("USD").value(settlementDto.getShippingPrice()))
                                .handling(new Money().currencyCode("USD").value("0.0"))
                                .taxTotal(new Money().currencyCode("USD").value("0.0"))
                                .discount(new Money().currencyCode("USD").value("0.0"))))
                .items(itemList);
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    /**
     * Method to create order with complete payload
     *
     * @param debug true = print response data
     * @return HttpResponse<Order> response received from API
     * @throws IOException Exceptions from API if any
     */
    public HttpResponse<Order> createOrder(boolean debug, PayPalEnvironment environment, String cancelUrl, String successUrl, OrdPaymentRecord ordPaymentRecord,SettlementDto settlementDto) throws IOException, JSONException {
        OrdersCreateRequest ordRequest = new OrdersCreateRequest();
        ordRequest.header("prefer","return=representation");
        ordRequest.requestBody(buildCompleteRequestBody(cancelUrl, successUrl,ordPaymentRecord,settlementDto));
        PayPalHttpClient client = new PayPalHttpClient(environment);
        HttpResponse<Order> ordResponse = client.execute(ordRequest);
        if (debug) {
            if (ordResponse.statusCode() == 201) {
                System.out.println("Order with Complete Payload: ");
                System.out.println("Status Code: " + ordResponse.statusCode());
                System.out.println("Status: " + ordResponse.result().status());
                System.out.println("Order ID: " + ordResponse.result().id());
                System.out.println("Intent: " + ordResponse.result().checkoutPaymentIntent());
                System.out.println("Links: ");
                for (LinkDescription link : ordResponse.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                }
                System.out.println("Total Amount: " + ordResponse.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode() + " " + ordResponse.result().purchaseUnits().get(0).amountWithBreakdown().value());
                System.out.println("Full response body:");
                System.out.println(new JSONObject(new Json().serialize(ordResponse.result())).toString(4));
            }
        }
        return ordResponse;
    }
}
