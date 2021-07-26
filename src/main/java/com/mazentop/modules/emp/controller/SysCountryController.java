package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysCountry;
import com.mazentop.model.CountryEnum;
import com.mazentop.modules.emp.commond.SysCountryCommond;
import com.mazentop.modules.emp.service.SysCountryService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import com.mztframework.snowflake.IDSnowflake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: wangzy
 * @date: 2020/3/19
 * @description:
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/country")
@Api(value = "/option/v1", tags = "系统：国家管理", description = "SysCountryController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysCountryController {

    @Autowired
    SysCountryService countryService;

    @Log("查询国家列表")
    @GetMapping(value = "/findCountryList")
    @ApiOperation(value = "查询国家列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryCommond", value = "查询条件", required = true, dataType = "SysCountryCommond")
    })
    public Result findCountryList(SysCountryCommond countryCommond) {
        return Result.build(() -> countryService.findCountryList(countryCommond));
    }

    @Log("查询国家列表(分页)")
    @GetMapping(value = "/findCountryListPage")
    @ApiOperation(value = "查询国家列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryCommond", value = "查询条件", required = true, dataType = "SysCountryCommond")
    })
    public Result findCountryListPage(SysCountryCommond countryCommond) {
        return Result.build(() -> countryService.findCountryListPage(countryCommond));
    }

    @Log("查询国家")
    @GetMapping(value = "/getCountry/{id}")
    @ApiOperation(value = "查询国家", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件", required = true, dataType = "String")
    })
    public Result getCountry(@PathVariable String id) {
        return Result.build(() -> SysCountry.me().setId(id).get());
    }

    @Log("查询洲的信息")
    @GetMapping(value = "/continentInformation")
    @ApiOperation(value = "查询洲的信息", notes = "入参:查询条件")
    public Result continentInformation() {
        return Result.build(() -> {

            HashMap<String, Object> data = new HashMap<>();
            data.put("ALL", SysCountry.me().findCount());
            data.put("AS", SysCountry.me().setContinentId(CountryEnum.AS.id()).findCount());
            data.put("NA", SysCountry.me().setContinentId(CountryEnum.NA.id()).findCount());
            data.put("SA", SysCountry.me().setContinentId(CountryEnum.SA.id()).findCount());
            data.put("AN", SysCountry.me().setContinentId(CountryEnum.AN.id()).findCount());
            data.put("OA", SysCountry.me().setContinentId(CountryEnum.OA.id()).findCount());
            data.put("EU", SysCountry.me().setContinentId(CountryEnum.EU.id()).findCount());
            data.put("AF", SysCountry.me().setContinentId(CountryEnum.AF.id()).findCount());

            return data;
        });
    }

    @Log("新增/修改国家信息")
    @PostMapping(value = "/doCountryAddOrUpdate")
    @ApiOperation(value = "新增/修改国家信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bloBlog", value = "保存参数", required = true, dataType = "BloBlog")
    })
    public Result doCountryAddOrUpdate(@RequestBody SysCountry country) {

        if (Objects.isNull(country)) {
            return Result.toast("传参为空");
        }

        return Result.build(() -> countryService.doCountryAddOrUpdate(country));
    }

    @Log("查询全部国家信息(已周区分)")
    @GetMapping(value = "/allCountry")
    @ApiOperation(value = "查询全部国家信息", notes = "入参:查询条件")
    public Result allCountry() {
        return Result.build(() -> {

            String[] name = {"亚洲","北美洲","南美洲","南极洲","大洋洲","欧洲","非洲"};
            String[] nameCn = {"AS","NA","SA","AN","OA","EU","AF"};

            List<Map<String,Object>> data = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                Map<String, Object> country = new HashMap<>();
                country.put("id",IDSnowflake.id());
                country.put("name",name[i]);
                country.put("nameCn",nameCn[i]);
                country.put("children",SysCountry.me().setContinentId(CountryEnum.getId(name[i])).find());
                country.put("isChildren",true);
                data.add(country);
            }

            return data;
        });
    }

    @Log("查询全部国家信息(已周区分)")
    @GetMapping(value = "/allCountryData/{schemeId}")
    @ApiOperation(value = "查询全部国家信息", notes = "入参:查询条件")
    public Result allCountryData(@PathVariable String schemeId) {
        return Result.build(() -> {
            List<Map<String,Object>> data = countryService.allCountryData(schemeId);
            return data;
        });
    }
}
