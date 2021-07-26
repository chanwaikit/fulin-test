package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinI18n;
import com.mazentop.entity.SkinI18nMap;
import com.mazentop.entity.SysTemplate;
import com.mazentop.model.Status;
import com.mazentop.modules.skin.commond.SkinI18nCommond;
import com.mazentop.modules.skin.dto.SkinI18nSaveDto;
import com.mazentop.modules.skin.service.SkinI18nService;
import com.mazentop.plugins.i18n.cache.I18nCache;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 语言国际化
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/30 16:42
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/skinI18n")
public class SkinI18nController {

    @Autowired
    SkinI18nService skinI18nService;

    @GetMapping(value = "")
    public Result skinI18n(SkinI18nCommond commond) {
        return Result.build(() ->  {
            List<SkinI18n> skinI18ns = SkinI18n.me().find(commond);
            skinI18ns.forEach(skinI18n -> {
                SysTemplate sysTemplate = SysTemplate.me().setId(skinI18n.getId()).get();
                if(!Objects.isNull(sysTemplate)) {
                    skinI18n.addExten("templateName", sysTemplate.getTemplateName());
                } else {
                    skinI18n.addExten("templateName", "-");
                }
            });
            return new Page<>(skinI18ns, commond);
        });
    }

    @GetMapping(value = "/{id}")
    public Result skinI18n(@PathVariable String id) {
        return Result.build(() -> SkinI18n.me().setId(id).get());
    }

    @PostMapping(value = "")
    public Result skinI18n(@RequestBody SkinI18nSaveDto skinI18n) {
        boolean isUpdateCache = skinI18nService.insertOrUpdate(skinI18n);
        if(isUpdateCache) {
            I18nCache.put(skinI18n);
        }
        return Result.success();
    }

    @PostMapping(value = "/{id}/enable/{status}")
    public Result skinI18nStatus(@PathVariable String id, @PathVariable String status) {
        Db.tx(() -> {
            SkinI18n
                    .me()
                    .setId(id)
                    .setIsEnable("yes".equalsIgnoreCase(status) ? Status.YES : Status.NO)
                    .update();
            return false;
        });
        return Result.success();
    }


    @GetMapping(value = "/language")
    public Result skinI18nLanguage(SkinI18nCommond commond) {
        return Result.build(() ->  {
            List<SkinI18nMap> skinI18nMaps = SkinI18nMap.me().find(commond);
            return new Page<>(skinI18nMaps, commond);
        });
    }

    @PostMapping(value = "/language")
    public Result skinI18nLanguage(@RequestBody SkinI18nMap skinI18n) {
        Db.tx(() -> {
            skinI18n.insertOrUpdate();
            I18nCache.put(skinI18n);
           return true;
        });
        return Result.success();
    }

    @GetMapping(value = "/language/{id}")
    public Result skinI18nLanguage(@PathVariable String id) {
        return Result.build(() -> SkinI18nMap.me().setId(id).get());
    }

    @DeleteMapping(value = "/language/{id}")
    public Result delSkinI18n(@PathVariable String id) {
        Db.tx(() -> {
            SkinI18nMap skinI18nMap = SkinI18nMap.me().setId(id).get();
            if(!Objects.isNull(skinI18nMap)) {
                I18nCache.del(skinI18nMap);
                skinI18nMap.setId(id).delete();
            }
            return true;
        });
        return Result.success();
    }

    @GetMapping("/language/list")
    public Result skinI18nLanguageList() {
        return Result.build(() -> SkinI18n.me().list());
    }



}
