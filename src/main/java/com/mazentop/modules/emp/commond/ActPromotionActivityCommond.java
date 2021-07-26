package com.mazentop.modules.emp.commond;

import com.mztframework.commond.PageCommond;
import lombok.Data;

/**
 * @author: dengy
 * @date: 2020/3/19
 * @description:
 */
@Data
public class ActPromotionActivityCommond extends PageCommond {

    private Long startTime;

    private Long endTime;

    private String companyId;

    private String activityName;

    private String activityStatus;

}
