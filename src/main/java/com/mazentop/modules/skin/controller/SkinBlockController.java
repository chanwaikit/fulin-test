package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinBlock;
import com.mazentop.modules.skin.commond.SkinBlockCommond;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaoqt
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/skinBlock")
public class SkinBlockController {

    @GetMapping(value = "")
    public Result skinBlock(SkinBlockCommond commond) {
        return Result.build(() ->  {
            List<SkinBlock> skinBlocks = SkinBlock.me().ignoreSelectFields(SkinBlock.F_CONTENT).find(commond);
            return new Page<>(skinBlocks, commond);
        });
    }

    @GetMapping(value = "/{id}")
    public Result<SkinBlock> skinBlock(@PathVariable String id) {
        return Result.build(() -> SkinBlock.me().setId(id).ignoreSelectFields(SkinBlock.F_CONTENT).get());
    }

    @GetMapping(value = "/{id}/data")
    public Result<SkinBlock> getSkinBlockData(@PathVariable String id) {
        return Result.build(() -> SkinBlock.me().setId(id).get());
    }

    @PostMapping(value = "")
    public Result<R> skinBlock(@RequestBody SkinBlock skinBlock) {
        return Result.build(() -> {
            Db.tx(() -> {
                skinBlock.insertOrUpdate();
                return true;
            });
            return R.ok();
        });
    }

    @DeleteMapping(value = "/{id}")
    public Result<R> deleteSkinBlock(@PathVariable String id) {
        return Result.build(() -> {
            Db.tx(() -> {
                SkinBlock.me().setId(id).delete();
                return true;
            });
            return R.ok();
        });
    }

}
