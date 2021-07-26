package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinBlockMenuDto;
import com.mazentop.modules.skin.service.SkinPageBlockMenuService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面导航
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/21 07:09
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/SkinPageMenu")
public class SkinPageMenuController {

    @Autowired
    SkinPageBlockMenuService skinPageBlockMenuService;

    @GetMapping(value = "")
    public Result findSkinPageMenuList(SkinPageBlockCommond commond) {
        commond.setView("menu");
        return Result.build(() -> skinPageBlockMenuService.findSkinPageMenuList(commond));
    }

    @GetMapping(value = "/{blockId}")
    public Result getSkinPageBlockMenu(@PathVariable  String blockId) {
        return Result.build(() -> skinPageBlockMenuService.getSkinPageBlockMenu(blockId));
    }

    @DeleteMapping(value = "/{blockId}")
    public Result delSkinPageBlockMenu(@PathVariable  String blockId) {
        return Result.build(() -> skinPageBlockMenuService.delSkinPageBlockMenu(blockId));
    }

    @PostMapping(value = "")
    public Result saveSkinPageMenu(@RequestBody SkinBlockMenuDto skinBlockMenuDto) {
        return Result.build(() -> {
            Map<String, String> map = new HashMap<>(1);
            skinPageBlockMenuService.saveSkinPageMenu(skinBlockMenuDto);
            map.put("blockId", skinBlockMenuDto.getId());
            return map;
        });
    }

    @GetMapping(value = "/getSkinPageMenuList")
    public Result getSkinPageMenuList() {
        SkinPageBlockCommond commond = new SkinPageBlockCommond();
        commond.setView("menu");
        commond.setOrderBy(SkinPageBlock.F_SORT);
        return Result.build(() -> SkinPageBlock.me().find(commond));
    }
}
