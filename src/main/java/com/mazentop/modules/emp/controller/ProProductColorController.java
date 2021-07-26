package com.mazentop.modules.emp.controller;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.ProProductColor;
import com.mazentop.modules.emp.commond.ProProductColorCommond;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.commons.Utils;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/product/color")
@Api(value = "/option/v1", tags = "商品: 色卡管理",  produces = MediaType.APPLICATION_JSON_VALUE)
public class ProProductColorController {


    @ApiOperation("新增/编辑")
    @ResponseBody
    @PostMapping(value = "/edit")
    public Result edit(@RequestBody ProProductColor proProductColor) {
        if (Helper.isEmpty(proProductColor.getColorName())){
            return Result.toast("颜色名称不能为空");
        }
        if (Helper.isEmpty(proProductColor.getColor())){
            return Result.toast("颜色不能为空");
        }
        String curUserId = Subject.id();
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Helper.isNotEmpty(proProductColor.getId())){
            proProductColor.update();
        }else {
            proProductColor.setAddTime(Utils.currentTimeSecond());
            proProductColor.setAddUserId(curEmployee.getId());
            proProductColor.insert();
        }
        return Result.success();
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "商品颜色列表", notes = "入参:查询条件")
    public Result page(ProProductColorCommond proProductColorCommond) {
        proProductColorCommond.setOrderBy("id desc ");
        List<ProProductColor> list = ProProductColor.me().find(proProductColorCommond);
        if(!list.isEmpty()) {
            list.forEach(color -> {
                EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(color.getAddUserId()).get();
                if(!Objects.isNull(curEmployee)){
                    color.addExten("addUserName",curEmployee.getEmployeeName());
                }
            });
        }
        return Result.build(() -> new Page(list,proProductColorCommond));
    }

    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "商品颜色详情")
    public Result info(@PathVariable("id") String id) {
        return Result.build(() ->  ProProductColor.me().setId(id).get());
    }

    @PostMapping(value = "/batch/delete")
    @ApiOperation(value = "颜色删除")
    public Result batch(@RequestBody List<String> ids) {
        ProProductColor.me().delete(ids);
        return Result.success();
    }

    @GetMapping(value = "/getColorList")
    @ApiOperation(value = "查询颜色集合")
    public Result getColorList() {
        return Result.build(() ->  ProProductColor.me().find());
    }
}
