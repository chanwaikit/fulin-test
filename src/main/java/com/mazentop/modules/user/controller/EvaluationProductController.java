package com.mazentop.modules.user.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.controller.BaseController;
import com.mztframework.data.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: SRC
 * @create: 2021-04-26 17:47
 **/
@Controller
@RequestMapping("/product")
public class EvaluationProductController extends BaseController {

    @GetMapping("/checkProduct")
    @ResponseBody
    public Result checkProduct(String url, String asin) {
       // HttpResponse httpResponse = HttpRequest.get(url).execute();
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        if(url.contains(asin)) {
            return Result.build(()->cliClienteleInfo.getAccountCertification());
        }
        return Result.toast("Please enter the correct Amazon Product link!");
    }
}
