package com.mazentop.modules.web.controller;

import com.mazentop.entity.SysAdvertisementPop;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
@Controller
@RequestMapping("/advertisementPop")
@Api(tags = "弹浮窗广告获取(web)")
public class SysAdvertisementPopWebController {


    @Log("弹浮窗广告获取")
    @ApiOperation("弹浮窗广告获取")
    @ResponseBody
    @GetMapping(value = "/getAdvertisementPop/position/{position}")
    public Result getAdvertisementPopByPosition(@PathVariable String position) {
        if (Objects.isNull(position)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(() -> SysAdvertisementPop.me().setPosition(position).get());
    }
}
