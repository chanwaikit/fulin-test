package com.mazentop.modules.web.controller;

import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/exchange/rate")
public class ExchangeRateWebControoler {

    @Autowired
    SysExchangeRateWebService sysExchangeRate;

    @Log("外汇比率列表")
    @ResponseBody
    @GetMapping("/list")
    public Result sysFriendsLinkList() {
       return  Result.build(() ->sysExchangeRate.sysExchangeRateList());
    }

    @Log("币种切换")
    @ResponseBody
    @GetMapping("/switch/{id}")
    public Result switchCurrency(@PathVariable String id, HttpServletResponse response) {
       return  Result.build(() ->sysExchangeRate.addSysExchangeRateCookie(response,id));
    }


}
