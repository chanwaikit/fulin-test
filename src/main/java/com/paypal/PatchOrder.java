package com.paypal;

import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.web.dto.SettlementDto;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatchOrder {
	/**
	 * Method to created body for patch order
	 *
	 * @return List<Patch> list of patches to be made
	 * @throws IOException
	 */
	private List<Patch> buildRequestBody(SettlementDto settlementDto) throws IOException {
		List<Patch> patches = new ArrayList<>();
		patches.add(new Patch().op("replace").path("/intent").value("CAPTURE"));
		patches.add(new Patch().op("replace").path("/purchase_units/@reference_id=='PUHF'/amount")
				.value(new AmountWithBreakdown().currencyCode("USD").value(settlementDto.getTotalPrice())
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(settlementDto.getSubTotalPrice()))
								.shipping(new Money().currencyCode("USD").value(settlementDto.getShippingPrice()))
								.discount(new Money().currencyCode("USD").value("0.0"))
								.taxTotal(new Money().currencyCode("USD").value("0.0")))));
		return patches;
	}

	/**
	 * Method to patch order
	 *
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Order> patchOrder(String orderId, PayPalEnvironment environment, SettlementDto settlementDto ) throws IOException {
		PayPalHttpClient client = new PayPalHttpClient(environment);
		OrdersPatchRequest request = new OrdersPatchRequest(orderId);
		request.requestBody(buildRequestBody(settlementDto));
		client.execute(request);
		OrdersGetRequest getRequest = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = client.execute(getRequest);
		return response;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Before PATCH:");
		HttpResponse<Order> response = new PatchOrder().patchOrder(null,null,null);
	}
}
