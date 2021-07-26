package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinPage;
import com.mazentop.modules.skin.commond.SkinPageCommond;
import com.mazentop.modules.skin.dto.SkinPageDto;
import com.mazentop.modules.skin.service.SkinPageService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author: dengy
 * @date: 2020/6/8
 * @description:
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/skinPage")
public class SkinPageController {

    @Autowired
    SkinPageService skinPageService;

    @GetMapping(value = "/findSkinPageList")
    public Result findSkinPageList(SkinPageCommond commond) {
        return Result.build(() -> skinPageService.findSkinPageList(commond));
    }

    @PostMapping(value = "/addOrUpdateSkinPage")
    public Result addOrUpdateSkinPage(@RequestBody SkinPage skinPage) {
        if (Objects.isNull(skinPage)) {
            return Result.toast("参数不能为空!");
        }
        skinPage.insertOrUpdate();
        return Result.success();
    }

    @GetMapping(value = "/getSkinPageInfo/{id}")
    public Result getSkinPageInfo( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SkinPage skinPage = SkinPage.me().setId(id).get();
        if (Objects.isNull(skinPage)) {
            return Result.toast("模板信息获取失败!");
        }
        return Result.build(()->skinPage);
    }



    @PostMapping("/deleteSkinPageInfo")
    public Result deleteSkinPageInfo(@RequestBody List<String> ids) {
        return Result.build(() -> skinPageService.deleteSkinPageInfo(ids));
    }



    @GetMapping(value = "/findGoodSpecialList")
    public Result findGoodSpecialList(SkinPageCommond commond) {
        return Result.build(() -> skinPageService.findGoodSpecialList(commond));
    }


    @PostMapping(value = "/getConditionGoodsData")
    public Result getConditionGoodsData(@RequestBody SkinPageDto skinPageDto){
        return Result.build(() -> skinPageService.getConditionGoodsData(skinPageDto));
    }


    @PostMapping(value = "/addOrUpdateSpecial")
    public Result addOrUpdateSpecial(@RequestBody SkinPageDto skinPage) {
        if(Objects.isNull(skinPage)){
            return Result.toast("数据获取失败!");
        }
        return Result.build(() -> skinPageService.doAddOrUpdateSpecial(skinPage));
    }


    @PostMapping("/deleteSpecialPageInfo")
    public Result deleteSpecialPageInfo(@RequestBody List<String> ids) {
        return Result.build(() -> skinPageService.deleteSpecialPageInfo(ids));
    }



    @GetMapping(value = "/getGoodSpecialList/{type}")
    public Result getGoodSpecialList(@PathVariable String type) {
        return Result.build(() -> skinPageService.getGoodSpecialList(type));
    }



    @GetMapping(value = "/getSpecialPageInfo/{id}")
    public Result getSpecialPageInfo( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(()->skinPageService.getSpecialPageInfo(id));
    }


    @GetMapping(value = "/getEvaluationSpecialPageInfo/{id}")
    public Result getEvaluationSpecialPageInfo( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        return Result.build(()->skinPageService.getEvaluationSpecialPageInfo(id));
    }



    @GetMapping(value = "/getPageCustomCount")
    public Result getSpecialPageInfo() {
        return Result.build(()-> skinPageService.getPageCustomCount());
    }


    @PostMapping(value = "/savePages")
    public Result addOrUpdateCustomPage(@RequestBody SkinPageDto skinPageDto) {
        skinPageService.savePages(skinPageDto);
        return Result.success();
    }


    @GetMapping(value = "/getPages/{id}")
    public Result getCustomPages(@PathVariable String id) {
        return Result.build(() -> skinPageService.getPages(id));
    }

    @GetMapping(value = "/getCustomPages")
    public Result getCustomPages(SkinPageCommond commond) {
        commond.setType("custom");
        return Result.build(() -> skinPageService.getPagesList(commond));
    }


    @PostMapping("/deletePages")
    public Result deletePages(@RequestBody List<String> ids) {
        return Result.build(() -> skinPageService.deletePages(ids));
    }

    @GetMapping(value = "/findSpecialPageToSelect")
    public Result findSpecialPageToSelect() {
        return Result.build(() -> skinPageService.findSpecialPageToSelect());
    }


    @GetMapping(value = "/findCustomPageToSelect")
    public Result findCustomPageToSelect() {
        return Result.build(() -> skinPageService.findCustomPageToSelect());
    }


}
