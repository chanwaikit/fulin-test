package com.mazentop.modules.emp.controller;

import com.mazentop.modules.emp.commond.CountCommond;
import com.mazentop.modules.emp.service.CountSiteService;
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
@RequestMapping("/option/{api_version}/countSite")
@Api(value = "/option/v1", tags = "数据统计：独立站统计管理", description = "CountSiteCountroller", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountSiteCountroller {


    @Autowired
    CountSiteService countSiteService;

    @GetMapping(value = "/order/getPreviewHotGoodsCount")
    @ApiOperation("获取预览商品热门列表")
    public Result getPreviewHotGoodsCount(CountCommond countCommond) {
        return Result.build(() -> countSiteService.getPreviewHotGoodsCount(countCommond));
    }

    @GetMapping(value = "/get/count/overview")
    @ApiOperation("概览数据统计")
    public Result countOverviewData(CountCommond commond) {
        return Result.build(() -> countSiteService.countOverview(commond));
    }

    @GetMapping(value = "/get/data/analysis")
    @ApiOperation("核心数据分析")
    public Result dataAnalysis(CountCommond commond) {
        return Result.build(() -> countSiteService.dataAnalysis(commond));
    }

    @GetMapping(value = "/get/trends")
    @ApiOperation("交易核心趋势")
    public Result countOrderTrends(CountCommond commond) {
        Map<String,Object> map=new HashMap<>();
        map.put("order",countSiteService.countOrderTrends(commond));
        return Result.build(() -> map);
    }


    @GetMapping(value = "/get/clientele/trends")
    @ApiOperation("访问核心趋势")
    public Result countClienteleTrends(CountCommond commond) {
        Map<String,Object> map=new HashMap<>();
        map.put("clientele",countSiteService.countClienteleTrends(commond));
        return Result.build(() -> map);
    }

    @GetMapping(value = "/get/conversion")
    @ApiOperation("转化过程")
    public Result countConversion(CountCommond commond) {
        return Result.build(() -> countSiteService.countConversion(commond));
    }

//    @GetMapping(value = "/get/order/sales/trend")
//    @ApiOperation("销售走势")
//    public Result countSalesTrend(CountCommond commond) {
//        return Result.build(() -> countSiteService.countSalesTrend(commond));
//    }


    @GetMapping(value = "/get/countSocialSales")
    @ApiOperation("社交来源销售")
    public Result countSocialSales(CountCommond commond) {
        return Result.build(() -> countSiteService.countSocialSales(commond));
    }

    @GetMapping(value = "/get/order/trend")
    @ApiOperation("订单走势")
    public Result countOrderTrend(CountCommond commond) {
        return Result.build(() -> countSiteService.countOrderTrend(commond));
    }

    @GetMapping(value = "/get/access/equipment")
    @ApiOperation("获取访问设备占比")
    public Result countAccessEquipment(CountCommond commond) {
        return Result.build(() -> countSiteService.countAccessEquipment(commond));
    }

    @GetMapping(value = "/get/trading/device")
    @ApiOperation("交易设备占比")
    public Result countTradingDevice(CountCommond commond) {
        return Result.build(() -> countSiteService.countTradingDevice(commond));
    }

    @GetMapping(value = "/get/hot/paymen")
    @ApiOperation("交易支付占比")
    public Result countPayment(CountCommond commond) {
        return Result.build(() -> countSiteService.countPayment(commond));
    }

    @GetMapping(value = "/get/online/user")
    @ApiOperation("统计在线用户")
    public Result countOnlineUser(CountCommond commond) {
        return Result.build(() -> countSiteService.countOnlineUser(commond));
    }

    @GetMapping(value = "/get/order/price")
    @ApiOperation("统计客单价")
    public Result countOrderPrice(CountCommond commond) {
        return Result.build(() -> countSiteService.countOrderPrice(commond));
    }

    @GetMapping(value = "/get/hot")
    @ApiOperation("获取热门数据")
    public Result countHotKeyword(CountCommond commond) {
        Map<String,Object> map=new HashMap<>();
        map.put("keyword",countSiteService.countHotKeyword(commond));
        map.put("goods",countSiteService.countHotGoods(commond));
        map.put("repurchaseGoods",countSiteService.countHotRepurchaseGoods(commond));
        map.put("country",countSiteService.countHotCountry(commond));
        map.put("goodsTrail",countSiteService.countHotGoodsTrail(commond));
        return Result.build(() ->map );
    }


    @GetMapping(value = "/getNearestOrder")
    @ApiOperation("获取最近订单")
    public Result getNearestOrder(CountCommond commond) {
        return Result.build(() -> countSiteService.getNearestOrder(commond));
    }

}
