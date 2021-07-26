package com.mazentop.modules.user.controller;

import com.mazentop.modules.user.dto.CreateOrder;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.user.service.OrdBalanceTheBooksService;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.mztframework.data.Result.build;

/**
 * 结算
 *
 * @author dengy
 */
@Controller
@RequestMapping("/ordBalanceTheBooksInfo")
public class OrdBalanceTheBooksController {

    @Autowired
    OrdBalanceTheBooksService ordBalanceTheBooksService;

    @PostMapping("/createOrder")
    @ResponseBody
    public Result createOrder(@RequestBody CreateOrder createOrder, HttpServletRequest request) {
        return Result.build(() ->{
            try {
               return ordBalanceTheBooksService.doGenerateOrder(createOrder,request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Result.toast("System abnormal!");
         });
    }
}
