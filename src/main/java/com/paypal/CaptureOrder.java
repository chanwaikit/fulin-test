package com.paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.paypal.payments.Capture;
import com.paypal.payments.LinkDescription;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CaptureOrder {

    /**
     * Creating empty body for capture request
     *
     * @return OrderRequest request with empty body
     */
    public OrderRequest buildRequestBody() {
        return new OrderRequest();
    }

    /**
     * Method to capture order after authorization
     *
     * @param authId Authorization ID from authorizeOrder response
     * @param debug  true = print response data
     * @return HttpResponse<Capture> response received from API
     * @throws IOException Exceptions from API if any
     */
    public HttpResponse<Capture> captureOrder(String authId, boolean debug, PayPalEnvironment environment) throws IOException, JSONException {
        AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId);
        request.requestBody(buildRequestBody());
        PayPalHttpClient client = new PayPalHttpClient(environment);
        HttpResponse<Capture> response = client.execute(request);
        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Capture ID: " + response.result().id());
            System.out.println("Links: ");
            for (LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }
    public HttpResponse<Order> captureOrders(String orderId, boolean debug , PayPalEnvironment environment,String bnCode) throws IOException, JSONException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId).bnCode(bnCode);
        request.requestBody(buildRequestBody());
        PayPalHttpClient client = new PayPalHttpClient(environment);
        HttpResponse<Order> response = client.execute(request);
        if (debug) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Order ID: " + response.result().id());
            System.out.println("Links: ");
            for (com.paypal.orders.LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href());
            }
            System.out.println("Capture ids:");
            for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
                for (com.paypal.orders.Capture capture : purchaseUnit.payments().captures()) {
                    System.out.println("\t" + capture.id());
                }
            }
            System.out.println("Buyer: ");
            Payer buyer = response.result().payer();
            System.out.println("\tEmail Address: " + buyer.email());
            System.out.println("\tName: " + buyer.name().givenName() + " " + buyer.name().surname());
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }
        return response;
    }
}
