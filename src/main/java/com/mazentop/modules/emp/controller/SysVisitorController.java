package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysLoginLog;
import com.mazentop.entity.SysVisitorComeFrom;
import com.mazentop.entity.SysVisitorRecord;
import com.mazentop.modules.emp.commond.SysLoginLogCommond;
import com.mazentop.modules.emp.commond.SysVisitorComeFromCommond;
import com.mazentop.modules.emp.commond.SysVisitorRecordCommond;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/option/{api_version}/sysVisitor")
@Api(tags = "访客日志")
public class SysVisitorController {

    @Log("访客来源列表")
    @ApiOperation("访客来源列表")
    @GetMapping("/comeFrom/list")
    public Result SysVisitorComeFromList(SysVisitorComeFromCommond commond) {
        return Result.build(() ->{
            List<SysVisitorComeFrom> list = SysVisitorComeFrom.me().setOrderByFields(Order.desc(SysVisitorComeFrom.F_VISIT_TIME)).find(commond);
            return  new Page(list,commond);
        });

    }

    @Log("访客记录列表")
    @ApiOperation("访客记录列表")
    @GetMapping("/visitor/record/list")
    public Result SysVisitorRecordList(SysVisitorRecordCommond commond) {
        return Result.build(() ->{
            List<SysVisitorRecord> list = SysVisitorRecord.me().setOrderByFields(Order.desc(SysVisitorRecord.F_VISIT_TIME)).find(commond);
            return  new Page(list,commond);
        });

    }
}
