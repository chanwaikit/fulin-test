package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProSeo;
import com.mazentop.modules.emp.service.ProSeoService;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/seo")
@Api(value = "/option/v1", tags = "SEO：SEO管理", description = "ProSeoController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProSeoController {

    @Autowired
    ProSeoService proSeoService;

    @GetMapping(value = "/{source}")
    @ApiOperation(value = "seo详情")
    public Result getProSeo(@PathVariable String source) {
        if (Helper.isEmpty(source)){
            return Result.toast("数据源为空！");
        }
        return Result.build(() -> proSeoService.getProSeo(source)
        );
    }



    @PostMapping(value = "/edit")
    @ApiOperation(value = "seo新增/编辑")
    public Result editProProductSeo(@RequestBody ProSeo seo) {

        if (Helper.isEmpty(seo.getSource())){
            return Result.toast("数据源为空！");
        }
        if (Helper.isEmpty(seo.getType())){
            return Result.toast("数据源类型为空！");
        }
        Long count = ProSeo.me().setSource(seo.getSource()).findCount();
        if (count>0&& Objects.isNull(seo.getId())){
            return Result.toast("数据源已存在！");
        }
        return Result.build(proSeoService.editProProductSeo(seo));
    }



    @GetMapping(value = "/url/{source}/{type}")
    @ApiOperation(value = "seo全地址")
    public Result getSeoUrlDetail( @PathVariable String source,@PathVariable String type) {
        return Result.build(() -> Seo.getSeoUrlDetail(source, type)
        );
    }


    @GetMapping(value = "/url/{type}")
    @ApiOperation(value = "seo地址")
    public Result getSeoByType( @PathVariable String type) {
        return Result.build(() -> Seo.getSeoUrl(type)
        );
    }
}
