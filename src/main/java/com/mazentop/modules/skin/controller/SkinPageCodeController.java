package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinPageCode;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.skin.commond.SkinPageCodeCommond;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author zhaoqt
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/skinPageCode")
public class SkinPageCodeController {

    @GetMapping(value = "")
    public Result<Page<SkinPageCode>> skinPageCode(SkinPageCodeCommond commond) {
        return Result.build(() -> new Page<>(SkinPageCode.me().find(commond), commond));
    }

    @GetMapping(value = "/{id}")
    public Result<SkinPageCode> skinPageCode(@PathVariable String id) {
        return Result.build(() -> SkinPageCode.me().setId(id).get());
    }


    @PostMapping(value = "/enable/{id}")
    public Result<R> enableSkinPageCode(@PathVariable String id) {
        return Result.build(() -> {
            Db.tx(() -> {
                SkinPageCode.me().setId(id).setIsEnable(Status.YES).update();
                return true;
            });
            return R.ok();
        });
    }


    @PostMapping(value = "/disabled/{id}")
    public Result<R> disabledSkinPageCode(@PathVariable String id) {
        return Result.build(() -> {
            Db.tx(() -> {
                SkinPageCode.me().setId(id).setIsEnable(Status.NO).update();
                return true;
            });
            return R.ok();
        });
    }

    @PostMapping(value = "")
    public Result<R> skinPageCode(@RequestBody SkinPageCode skinPageCode) {
        return Result.build(() -> {
            Db.tx(() -> {
                if(Objects.isNull(skinPageCode.getIsEnable())) {
                    skinPageCode.setIsEnable(BooleanEnum.TRUE.getValue());
                }
                skinPageCode.insertOrUpdate();
                return true;
            });
            return R.ok();
        });
    }

    @DeleteMapping(value = "/{id}")
    public Result<R> deleteSkinPageCode(@PathVariable String id) {
        return Result.build(() -> {
            Db.tx(() -> {
                SkinPageCode.me().setId(id).delete();
                return true;
            });
            return R.ok();
        });
    }
}
