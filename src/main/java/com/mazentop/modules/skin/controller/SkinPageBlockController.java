package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinPageBlock;
import com.mazentop.model.Status;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mazentop.modules.skin.service.SkinPageBlockService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
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
@RequestMapping("/option/{api_version}/skinPageBlock")
public class SkinPageBlockController {


    @Autowired
    SkinPageBlockService skinPageBlockService;

    @GetMapping(value = "/findSkinPageBlockByPage")
    public Result findSkinPageBlockByPage(String pageId) {
        return Result.build(() -> skinPageBlockService.findSkinPageBlockByPage(pageId));
    }

    @PostMapping(value = "/enableSkinPageBlock")
    public Result enableSkinPageBlock(String blockId) {
        Db.tx(() -> {
            SkinPageBlock.me().setId(blockId).setIsEnable(Status.YES).update();
            return true;
        });
        return Result.success();
    }

    @PostMapping(value = "/disabledSkinPageBlock")
    public Result disabledSkinPageBlock(String blockId) {
        Db.tx(() -> {
            SkinPageBlock.me().setId(blockId).setIsEnable(Status.NO).update();
            return true;
        });
        return Result.success();
    }

    @PostMapping(value = "/deleteSkinPageBlock")
    public Result deleteSkinPageBlock(String blockId) {
        Db.tx(() -> {
            SkinPageBlock.me().setId(blockId).delete();
            return true;
        });
        return Result.success();
    }
    @GetMapping(value = "/getSkinPageBlockInfo")
    public Result getSkinPageBlockInfo(String blockId) {
        return Result.build(() -> skinPageBlockService.getSkinPageBlockInfo(blockId));
    }

    @PostMapping("/updateSortable")
    public Result updateSortable(@RequestBody List<String> ids) {
        Db.tx(() -> {
            int sort = 2;
            for (int i = 0; i < ids.size(); i++) {
                SkinPageBlock.me().setId(ids.get(i)).setSort(sort).update();
                sort++;
            }
            return true;
        });
        return Result.success();
    }

    @GetMapping(value = "/getParentSkinPageBlockInfo")
    public Result getParentSkinPageBlockInfo(SkinPageBlockCommond commond) {
        return Result.build(() -> skinPageBlockService.getParentSkinPageBlockInfo(commond));
    }


    @PostMapping("/addOrUpdateBlockInfo")
    public Result addOrUpdateBlockInfo(@RequestBody SkinPageBlockDto skinPageBlock) {
        if(Objects.isNull(skinPageBlock)){
            return Result.toast("参数为空");
        }
        return Result.build(() -> skinPageBlockService.doAddOrUpdateBlockInfo(skinPageBlock));
    }

    @PostMapping("/updateBlockHandle")
    public Result updateBlockHandle(@RequestBody SkinPageBlockDto skinPageBlock) {
        if(Objects.isNull(skinPageBlock)){
            return Result.toast("参数为空");
        }
        return Result.build(() -> skinPageBlockService.updateBlockHandle(skinPageBlock));
    }


    @PostMapping("/addOrUpdateBlockData")
    public Result addOrUpdateBlockData(@RequestBody SkinPageBlockDto skinPageBlock) {
        return Result.build(() -> skinPageBlockService.addOrUpdateBlockData(skinPageBlock));
    }

    @GetMapping("/getBlockData/{viewType}/{blockId}")
    public Result getBlockData(@PathVariable String viewType, @PathVariable String blockId) {
        SkinPageBlockCommond skinPageBlockCommond = new SkinPageBlockCommond();
        skinPageBlockCommond.setId(blockId);
        return Result.build(() -> skinPageBlockService.getBlockData(viewType, skinPageBlockCommond));
    }

    @GetMapping(value = "/findSkinPageBlockOrder")
    public Result findSkinPageBlockOrder(String pageId) {
        return Result.build(() -> skinPageBlockService.findSkinPageBlockByOrder(pageId));
    }

}
