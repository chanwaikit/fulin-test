package com.mazentop.modules.evaluation.controller;

import com.mazentop.modules.emp.commond.CountCommond;
import com.mazentop.modules.emp.service.CountSiteService;
import com.mazentop.modules.evaluation.service.EvaCountSiteService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/evaCountSite")
@Api(value = "/option/v1", tags = "数据统计：独立站统计管理", description = "CountSiteCountroller", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaCountSiteCountroller {


    @Autowired
    EvaCountSiteService countSiteService;

    @GetMapping(value = "/get/count/overview")
    @ApiOperation("概览数据统计")
    public Result countOverviewData(CountCommond commond) {
        return Result.build(() -> countSiteService.countOverview(commond));
    }


    /**
     * 总返现额
     * @param commond
     * @return
     */
    @GetMapping(value = "/getCashback/trend")
    @ApiOperation("总返现额走势")
    public Result countSalesTrend(CountCommond commond) {
        return Result.build(() -> countSiteService.countCashbackTrend(commond));
    }


    /**
     * 最近订单
     * @param commond
     * @return
     */
    @GetMapping(value = "/getNearestOrder")
    @ApiOperation("获取最近订单")
    public Result getNearestOrder(CountCommond commond) {
        return Result.build(() -> countSiteService.getNearestOrder(commond));
    }


}
