package com.mazentop.modules.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrdOrderSendEmailDto {

    private String orderId;

    private String orderNo;

    private String productTotalPrice;

    private String receiverCountry;

    private String receiverCity;

    private String receiverEmail;

    private String receiverName;

    private String receiverPost;

    private String receiverPhone;

    private String receiverSheet;

    private String receiverProvince;

    private String receiverAddress;

    private String invoiceName;

    private String invoiceId;

    private String invoiceAddress;

    private String invoicePhone;

    private String invoiceEmail;

    private String invoicePost;

    private String invoiceCountry;

    private String invoiceCity;

    private String invoiceProvince;

    private String paymentMethod;

    private String totalTransportsFree;

    private String totalPaymentFree;

    private String shippingMethod;

    private String companyId;

    private String companyName;

    private String email;

    private String emailSendTime;

    private String currency;

    private String productDiscountPrice;

    private String taxAmount;

    private String taxRate;

    private String orderDetailUrl;

    private String shopUrl;

    private String contactEmail;

    private String totalSubtractPrice;


    private List<OrdOrderDetailSendEmailDto> list;

}
