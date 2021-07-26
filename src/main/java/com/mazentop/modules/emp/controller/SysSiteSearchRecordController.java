package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.SysHelpCenterContent;
import com.mazentop.entity.SysPartner;
import com.mazentop.entity.SysSiteSearchRecord;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.SysPartnerCommond;
import com.mazentop.modules.emp.commond.SysSiteSearchRecordCommond;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/option/{api_version}/siteSearchRecord")
@Api(tags = "站内搜索接口")
public class SysSiteSearchRecordController {


    @Log("站内搜索列表")
    @ApiOperation("站内搜索列表")
    @ResponseBody
    @GetMapping("/list")
    public Result siteSearchRecord(SysSiteSearchRecordCommond commond) {
        return Result.build(() -> {
            List<SysSiteSearchRecord> sysSiteSearchRecord = SysSiteSearchRecord.me()
                    .setOrderByFields(Order.desc(ProProductMaster.F_ADD_TIME))
                    .find(commond);
            return new Page(sysSiteSearchRecord,commond);
        });

    }

    @Log("查询站内搜索详情")
    @GetMapping(value = "/getSiteSearchRecordInfo/{id}")
    @ApiOperation(value = "查询站内搜索详情", notes = "入参:查询条件")
    public Result getSiteSearchRecordInfo(@PathVariable String id) {
        return Result.build(() ->  {
            SysSiteSearchRecord sysSiteSearchRecord = SysSiteSearchRecord.me().setId(id).get();
            return sysSiteSearchRecord;
        });

    }
}
