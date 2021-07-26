package com.mazentop.excel.controller;

import com.mazentop.excel.service.ExcelService;
import com.mazentop.model.ExcelEnum;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/excel")
@Api(value = "/option/v1", tags = "Excel：报表管理",  produces = MediaType.APPLICATION_JSON_VALUE)
public class ExcelController {
    @Autowired
    Map<String, ExcelService> map;

    @PostMapping("/import/{slug}")
    @ResponseBody
    public Result importExcel(@PathVariable String slug,@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        if (check(slug))return Result.toast("404");
        return map.get(ThemeUtil.excelRegistrationName(slug)).importExcel(request,file);
    }

    @PostMapping("/export/{slug}")
    @ResponseBody
    public Result exportExcel(@PathVariable String slug, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (check(slug))return Result.toast("404");
        map.get(ThemeUtil.excelRegistrationName(slug)).exportExcel(request,response);
        return null;
    }

    private boolean check(String slug){
        String key = ExcelEnum.getKye(slug);
        if (Helper.isEmpty(key)){
           return true;
        }
        return false;
    }

}
