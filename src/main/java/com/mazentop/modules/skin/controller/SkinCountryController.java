package com.mazentop.modules.skin.controller;

import com.mazentop.entity.SkinCountry;
import com.mazentop.entity.SysCountry;
import com.mazentop.modules.skin.commond.SkinCountryCommond;
import com.mazentop.modules.skin.service.SkinCountryService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.model.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: xhl
 * @Date: 2020/8/24 10:24
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/skin/country")
public class SkinCountryController {

    @Autowired
    private SkinCountryService skinCountryService;



    @GetMapping(value = "/page")
    public Result findSkinCountryPage(SkinCountryCommond commond) {
        commond.setIsEnable(Status.YES);
        return Result.build(() -> new Page<>(SkinCountry.me().find(commond),commond));
    }


    @GetMapping(value = "/list")
    public Result findSkinCountryList(SkinCountryCommond commond) {
        commond.setIsEnable(Status.YES);
        List<SkinCountry> skinCountries = Optional.ofNullable(SkinCountry.me().find(commond)).orElse(Collections.emptyList());
        skinCountries.forEach(item -> {
            SysCountry sysCountry = SysCountry.me().setId(item.getCountryCode()).findFirst();
            item.addExten("flag",sysCountry.getFlag());
        });
        return Result.build(() -> skinCountries);
    }

    @GetMapping(value = "/{id}")
    public Result skinCountry(@PathVariable String id) {
        return Result.build(() -> SkinCountry.me().setId(id).get());
    }


    @PostMapping("/addOrUpdate")
    public Result add(@RequestBody SkinCountry skinCountry) {
        if (StringUtils.isBlank(Optional.ofNullable(skinCountry).orElse(SkinCountry.me()).getId())) {
            skinCountry.setIsEnable(Status.YES);
            skinCountryService.save(skinCountry);
        } else {
            skinCountryService.update(skinCountry);
        }
        return Result.success();
    }



    @PostMapping(value = "/delete")
    public Result delete(String id) {
        return Result.build(() ->SkinCountry.me().setId(id).setIsEnable(Status.NO).update());
    }


    @PostMapping(value = "/batchDelete")
    public Result delete(@RequestBody List<String> ids) {
        Db.tx(() -> {
            for (String id : ids) {
                SkinCountry.me().setId(id).setIsEnable(Status.NO).update();
            }
            return true;
        });
        return Result.success();
    }

}
