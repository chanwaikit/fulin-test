package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinPageLayout;
import com.mazentop.modules.skin.commond.SkinPageLayoutCommond;
import com.mazentop.modules.skin.service.SkinPageLayoutService;
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
@RequestMapping("/option/{api_version}/skinPageLayout")
public class SkinPageLayoutController {

    @Autowired
    SkinPageLayoutService skinPageLayoutService;

    @GetMapping(value = "/findSkinPageLayoutList")
    public Result findSkinPageLayoutList(SkinPageLayoutCommond commond) {
        return Result.build(() -> skinPageLayoutService.findSkinPageLayoutList(commond));
    }

    @PostMapping(value = "/addOrUpdateSkinPageLayout")
    public Result addOrUpdateSkinPageLayout(@RequestBody SkinPageLayout skinPageLayout) {
        if (Objects.isNull(skinPageLayout)) {
            return Result.toast("参数不能为空!");
        }
        skinPageLayout.insertOrUpdate();
        return Result.success();
    }

    @GetMapping(value = "/getSkinPageLayoutInfo/{id}")
    public Result getSkinPageLayoutInfo( @PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SkinPageLayout skinPageLayout = SkinPageLayout.me().setId(id).get();
        if (Objects.isNull(skinPageLayout)) {
            return Result.toast("布局信息获取失败!");
        }
        return Result.build(()->skinPageLayout);
    }

    @PostMapping("/deletePageLayoutInfo")
    public Result deletePageLayoutInfo(@RequestBody List<String> ids) {
        return Result.build(() -> skinPageLayoutService.doDeletePageLayoutInfo(ids));
    }

    @GetMapping(value = "/getSkinPageLayoutList/{id}")
    public Result getSkinPageLayoutList(@PathVariable String id) {
        return Result.build(() -> SkinPageLayout.me().setTemplateId(id).find());
    }

}
