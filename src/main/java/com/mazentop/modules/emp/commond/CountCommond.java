package com.mazentop.modules.emp.commond;

import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

@Data
public class CountCommond extends BaseCommond {
    /**
     * 1.今天
     * 2.昨天
     * 3.过去7天
     * 4.过去30天
     * 5.上周
     * 6.上个月
     */
    private Integer countType;

    private Long startTime;

    private Long endTime;
}
