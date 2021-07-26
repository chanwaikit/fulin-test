package com.mazentop.modules.user.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private String source;

    private Long totalPrice;

    private String transactionId;

    private String settlementId;
}
