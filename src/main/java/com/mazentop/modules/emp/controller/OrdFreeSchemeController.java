package com.mazentop.modules.emp.controller;

import com.mazentop.entity.*;
import com.mazentop.modules.emp.dto.OrdFreeSchemeCountryDto;
import com.mazentop.modules.emp.dto.OrdFreeSchemeDto;
import com.mazentop.modules.emp.service.OrdFreeSchemeService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dengy
 * @title: OrdFreeSchemeController
 * @description: 物流方案
 * @date 2020/3/23 14:32
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/ordFreeScheme")
@Api(value = "/option/v1", tags = "网站设置:物流方案", description = "OrdFreeSchemeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdFreeSchemeController {

    @Autowired
    OrdFreeSchemeService ordFreeSchemeService;



    @Log("查询物流方案")
    @GetMapping(value = "/findOrdFreeScheme")
    @ApiOperation(value = "查询物流方案", notes = "入参:查询条件")
    public Result findOrdFreeScheme() {
        return Result.build(() ->  ordFreeSchemeService.findOrdFreeScheme());
    }


    @Log("查询单个物流方案")
    @GetMapping(value = "/findOrdFreeSchemeInfo/{id}")
    @ApiOperation(value = "查询单个物流方案", notes = "入参:查询条件")
    public Result findOrdFreeSchemeInfo(@PathVariable String id){
        return Result.build(() ->  {
           OrdFreeScheme ordFreeScheme = OrdFreeScheme.me().setId(id).get();
           List<OrdFreeSchemeDetails> detailList =  OrdFreeSchemeDetails.me().setFkOrdFreeSchemeId(id).find();
           if(!detailList.isEmpty()){
               detailList.forEach(details->{
                   ordFreeSchemeService.optionDetails(details);
               });
           }
            ordFreeScheme.addExten("detailList",detailList);
            List<OrdFreeSchemeCountry> countryList =  OrdFreeSchemeCountry.me().setFkSchemeId(id).find();
            List<OrdFreeSchemeCountryDto> countryDtoList = new ArrayList<>();
            countryList.forEach(country->{
                OrdFreeSchemeCountryDto dto = new OrdFreeSchemeCountryDto();
                BeanUtils.copyProperties(country,dto);
                List<OrdFreeSchemeCity> cityList = OrdFreeSchemeCity.me().setFkSchemeCountId(country.getId()).find();
                dto.setCityList(cityList);
                countryDtoList.add(dto);
            });
            ordFreeScheme.addExten("countryList",countryDtoList);
            return ordFreeScheme;
        });
    }



    @Log("新增/修改物流方案")
    @PostMapping(value = "/doOrdFreeSchemeAddOrUpdate")
    @ApiOperation(value = "新增/修改物流方案", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proBrand", value = "保存参数", required = true, dataType = "ProBrand")
    })
    public Result doOrdFreeSchemeAddOrUpdate(@RequestBody OrdFreeSchemeDto ordFreeSchemeDto) {
        if(Objects.isNull(ordFreeSchemeDto)){
            return Result.toast("参数为空");
        }
        return Result.build(() -> ordFreeSchemeService.doOrdFreeSchemeAddOrUpdate(ordFreeSchemeDto));
    }


    @Log("删除物流方案")
    @PostMapping(value = "/deleteFreeScheme")
    @ApiOperation(value="删除物流方案", notes="入参:主键 方案ID 数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "主键 方案ID 数组", required = true,allowMultiple=true, dataType = "String")
    })
    public Result deleteFreeScheme(@RequestBody List<String> ids){
        return Result.build(() -> ordFreeSchemeService.deleteFreeScheme(ids));
    }

    @Log("查询物流方案省/市")
    @PostMapping(value = "/findOrdFreeSCity")
    @ApiOperation(value = "查询物流方案省/市", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "OrdFreeSchemeCity", value = "查询条件", required = true, dataType = "ordFreeSchemeCity")
    })
    public Result findOrdFreeSCity(@RequestBody OrdFreeSchemeCity ordFreeSchemeCity) {
        return Result.build(() ->  ordFreeSchemeService.findOrdFreeSCity(ordFreeSchemeCity));
    }
}
