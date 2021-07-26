package com.mazentop.modules.emp.commond;

import com.mztframework.commond.PageCommond;
import lombok.Data;

/**
 * @author dengy
 * @title: OrdRefundRequestCommond
 * @description: 退款表
 * @date 2020/03/16 10:22
 */
@Data
public class OrdRefundRequestCommond extends PageCommond {

    private Long startTime;

    private Long endTime;

    private String query;

    private String refundRequestStatus;
}
