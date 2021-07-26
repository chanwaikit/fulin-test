package com.mazentop.modules.emp.dto;

import lombok.Data;

@Data
public class OrdOfflinePaymentRecordDto {
    private String orderId;
    private String remark;
    private Integer state;
}
