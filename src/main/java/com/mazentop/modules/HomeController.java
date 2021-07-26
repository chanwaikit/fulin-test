package com.mazentop.modules;

import com.mazentop.modules.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaoqt
 * @version 1.0
 * @date 2020/1/16 22:57
 */
@Controller
public class HomeController extends BaseController {

    @RequestMapping("/csrf")
    @ResponseBody
    public String csrf() {
        return "ok";
    }

}
