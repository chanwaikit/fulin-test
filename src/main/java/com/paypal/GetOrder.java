package com.paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetOrder {

	/**
	 * Method to perform sample GET on an order
	 *
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Order> getOrder(String orderId, PayPalEnvironment environment) throws IOException {
		OrdersGetRequest request = new OrdersGetRequest(orderId);
		PayPalHttpClient client = new PayPalHttpClient(environment);
		HttpResponse<Order> response = client.execute(request);
		/*System.out.println("Full response body:");*/
		/*try {
			System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		return response;
	}

	/**
	 * This is the driver method which invokes the getOrder function with Order Id
	 * to retrieve an order details.
	 *
	 * To get the correct Order id, we are using the createOrder to create new order
	 * and then we are using the newly created order id.
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		HttpResponse<Order> response = new GetOrder().getOrder(null,null);
	}
}
