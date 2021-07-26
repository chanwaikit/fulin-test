package com.paypal.payments.test;

import com.paypal.core.PayPalEnvironment;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Capture;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.PurchaseUnit;


public class RunAll {
    static String clientId = "AYZi9tGZscGMzxlsHEWdXLsM3WY_-rvIWvc12j8c5udLWz87AQbRs1Ha16p5ulNDVDczNJSxYdjrPg_I";
    static String secret = "EDQXp7mk80GRcN_CigmOJh0EgYq6Ts3SK2ti_UjauKsf0UjP6ZFY5hUeFLUFGPFlwchozWkkNGOTXuPx";

    public static void main(String[] args) {
        try {
            PayPalEnvironment environment =new PayPalEnvironment.Sandbox(clientId,secret);
            // Creating Order
            HttpResponse<Order> orderResponse = new CreateOrder().createOrder(false,environment);
            String orderId = "";
            System.out.println("Creating Order...");
            if (orderResponse.statusCode() == 201){
                orderId = orderResponse.result().id();
                System.out.println("Links:");
                for (LinkDescription link : orderResponse.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href());
                }
            }
            System.out.println("Created Successfully\n");
            System.out.println("Copy approve link and paste it in browser. Login with buyer account and follow the instructions.\nOnce approved hit enter...");
            System.in.read();

            // Capturing Order
            System.out.println("Capturing Order...");
            orderResponse = new CaptureOrder().captureOrder(orderId, false,environment);
            String captureId = "";
            if (orderResponse.statusCode() == 201){
                System.out.println("Captured Successfully");
                System.out.println("Status Code: " + orderResponse.statusCode());
                System.out.println("Status: " + orderResponse.result().status());
                System.out.println("Order ID: " + orderResponse.result().id());
                System.out.println("Links:");
                for (LinkDescription link : orderResponse.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href());
                }
                System.out.println("Capture ids:");
    			for (PurchaseUnit purchaseUnit : orderResponse.result().purchaseUnits()) {
    				for (Capture capture : purchaseUnit.payments().captures()) {
    					System.out.println("\t" + capture.id());
    					captureId = capture.id();
    				}
    			}
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
