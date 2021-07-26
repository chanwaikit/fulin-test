package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.SysFriendsLinkType;
import com.mazentop.entity.SysLoginLog;
import com.mazentop.modules.emp.commond.SysFriendsLinkTypeCommond;
import com.mazentop.modules.emp.commond.SysLoginLogCommond;
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
@RequestMapping("/option/{api_version}/sysLoginLog")
@Api(tags = "后台登录日志")
public class SysLoginLogController {

    @Log("后台登录日志列表")
    @ApiOperation("后台登录日志列表")
    @GetMapping("/loginLog/list")
    public Result sysLoginLogList(SysLoginLogCommond commond) {
        return Result.build(() ->{
            List<SysLoginLog> list = SysLoginLog.me().setOrderByFields(Order.desc(SysLoginLog.F_LOGIN_TIME)).find(commond);
            return  new Page(list,commond);
        });

    }
}
