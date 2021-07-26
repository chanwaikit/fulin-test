package com.mazentop.modules.emp.commond;

import com.mztframework.commond.PageCommond;
import lombok.Data;

/**
 * @author dengy
 * @title: ActReduceActivityCommond
 * @description: 满减活动
 * @date 2020/03/18 09:59
 */
@Data
public class ActReduceActivityCommond extends PageCommond {


    private Long startTime;

    private Long endTime;

    private String activityName;

    private String activityStatus;

    private String companyId;
}
