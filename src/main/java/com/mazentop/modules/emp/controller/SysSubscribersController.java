package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.SysSiteSearchRecord;
import com.mazentop.entity.SysSubscriber;
import com.mazentop.modules.emp.commond.SysSiteSearchRecordCommond;
import com.mazentop.modules.emp.commond.SysSubscriberCommond;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/option/{api_version}/sysSubscriber")
@Api(tags = "订阅接口")
public class SysSubscribersController {

    @Log("订阅列表")
    @ApiOperation("订阅列表")
    @ResponseBody
    @GetMapping("/list")
    public Result sysSubscriberList(SysSubscriberCommond commond) {
        return Result.build(() -> {
            List<SysSubscriber> sysSubscriberList = SysSubscriber.me()
                    .setOrderByFields(Order.desc(ProProductMaster.F_ADD_TIME))
                    .find(commond);
            return new Page(sysSubscriberList,commond);
        });

    }
}
