package com.mazentop.modules.emp.controller;

import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.SysFriendsLink;
import com.mazentop.entity.SysFriendsLinkType;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.SysFriendsLinkCommond;
import com.mazentop.modules.emp.commond.SysFriendsLinkTypeCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/option/{api_version}/sysFriendsLink")
@Api(tags = "友情链接接口")
public class SysFriendsLinkController {

    @Log("友情链接类型列表")
    @ApiOperation("友情链接类型列表")
    @GetMapping("/type/list")
    public Result sysFriendsLinkTypeList() {
        return Result.build(() -> SysFriendsLinkType.me().find());

    }

    @Log("友情链接类型列表(分页)")
    @ApiOperation("友情链接类型列表(分页)")
    @GetMapping("/type/list/page")
    public Result sysFriendsLinkTypeListPage( SysFriendsLinkTypeCommond commond) {
        return Result.build(() ->{
                List<SysFriendsLinkType> sysFriendsLinkType =SysFriendsLinkType.me().find(commond);
                return  new Page(sysFriendsLinkType,commond);
        });

    }

    @Log("友情链接类型新增")
    @ApiOperation("友情链接类型新增")
    @ResponseBody
    @PostMapping(value = "/addSysFriendsLinkType")
    public Result doSysFriendsLinkTypeAdd(@RequestBody SysFriendsLinkType sysFriendsLinkType) {
        if (Objects.isNull(sysFriendsLinkType)) {
            return Result.toast("友情信息类型获取失败!");
        }
        Long count = SysFriendsLinkType.me().setFriendsLinkTypeName(sysFriendsLinkType.getFriendsLinkTypeName()).findCount();
        if (count > 0) {
            return Result.toast("该友情类型已存在!");
        }
        sysFriendsLinkType.insert();
        return Result.success();
    }


    @Log("友情链接类型编辑")
    @ApiOperation("友情链接类型编辑")
    @ResponseBody
    @PostMapping(value = "/editSysFriendsLinkType")
    public Result editSysFriendsLinkType(@RequestBody SysFriendsLinkType sysFriendsLinkType) {
        if (Objects.isNull(sysFriendsLinkType) || Objects.isNull(sysFriendsLinkType.getId())) {
            return Result.toast("友情信息类型获取失败!");
        }
        SysFriendsLinkType sysFriendsType = SysFriendsLinkType.me().setFriendsLinkTypeName(sysFriendsLinkType.getFriendsLinkTypeName());
        Long count = SysFriendsLinkType.me().setFriendsLinkTypeName(sysFriendsLinkType.getFriendsLinkTypeName()).findCount();
        if (count > 0 && !sysFriendsLinkType.getId().equals(sysFriendsType.getId())) {
            return Result.toast("该友情已存在!");
        }
        sysFriendsLinkType.update();
        return Result.success();
    }

    @Log("获取友情类型详情")
    @ApiOperation("获取友情类型详情")
    @ResponseBody
    @GetMapping(value = "/getSysFriendsLinkType/{id}")
    public Result getSysFriendsLinkType(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysFriendsLinkType sysFriendsLinkType = SysFriendsLinkType.me().setId(id).get();
        if (Objects.isNull(sysFriendsLinkType)) {
            return Result.toast("友情信息类型获取失败!");
        }
        return Result.build(() -> sysFriendsLinkType);
    }

    @Log("删除友情类型")
    @ApiOperation("删除友情类型")
    @ResponseBody
    @GetMapping(value = "/delSysFriendsLinkType/{id}")
    public Result delSysFriendsLinkType(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysFriendsLinkType sysFriendsLinkType = SysFriendsLinkType.me().setId(id).get();
        if (Objects.isNull(sysFriendsLinkType)) {
            return Result.toast("友情信息类型获取失败!");
        }
        sysFriendsLinkType.deleteById();
        return Result.success();
    }

    @Log("友情链接列表")
    @ApiOperation("友情链接列表")
    @ResponseBody
    @GetMapping("/list")
    public Result SysFriendsLinkList( SysFriendsLinkCommond commond) {
        return Result.build(() -> {
            List<SysFriendsLink> sysFriendsLink = SysFriendsLink.me()
                    .setIsEnable(Status.YES)
                    .setOrderByFields(Order.desc(ProProductMaster.F_ADD_TIME))
                    .find(commond);
            return new Page(sysFriendsLink,commond);
        });

    }


    @Log("友情链接新增")
    @ApiOperation("友情链接新增")
    @ResponseBody
    @PostMapping(value = "/addSysFriendsLink")
    public Result addSysFriendsLink(@RequestBody SysFriendsLink sysFriendsLink) {
        if (Objects.isNull(sysFriendsLink)) {
            return Result.toast("友情信息获取失败!");
        }
        if (Objects.isNull(sysFriendsLink.getFkFriendsLinkTypeId())) {
            return Result.toast("友情信息获取失败!");
        }
        SysFriendsLinkType sysFriendsLinkType = SysFriendsLinkType.me().setId(sysFriendsLink.getFkFriendsLinkTypeId()).get();
        if (Objects.isNull(sysFriendsLinkType)) {
            return Result.toast("友情信息获取失败!");
        }
        Long count = SysFriendsLink.me().setFriendsLinkTitle(sysFriendsLink.getFriendsLinkTitle()).findCount();
        if (count > 0) {
            return Result.toast("该友情已存在!");
        }
        sysFriendsLink.setAddTime(Utils.currentTimeSecond());
        sysFriendsLink.setFriendsLinkTypeName(sysFriendsLinkType.getFriendsLinkTypeName());
        sysFriendsLink.insert();
        return Result.success();
    }

    @Log("友情链接编辑")
    @ApiOperation("友情链接编辑")
    @ResponseBody
    @PostMapping(value = "/editSysFriendsLink")
    public Result editSysFriendsLink(@RequestBody SysFriendsLink sysFriendsLink) {
        if (Objects.isNull(sysFriendsLink) || Objects.isNull(sysFriendsLink.getId())) {
            return Result.toast("友情信息获取失败!");
        }
        if (Objects.isNull(sysFriendsLink.getFkFriendsLinkTypeId())) {
            return Result.toast("友情信息类型获取失败!");
        }
        SysFriendsLinkType sysFriendsLinkType = SysFriendsLinkType.me().setId(sysFriendsLink.getFkFriendsLinkTypeId()).get();
        if (Objects.isNull(sysFriendsLinkType)) {
            return Result.toast("友情信息类型获取失败!");
        }
        SysFriendsLink sysFriends = SysFriendsLink.me().setFriendsLinkTitle(sysFriendsLink.getFriendsLinkTitle()).get();

        Long count = SysFriendsLink.me().setFriendsLinkTitle(sysFriendsLink.getFriendsLinkTitle()).findCount();
        if (count > 0 && !sysFriends.getId().equals(sysFriendsLink.getId())) {
            return Result.toast("该友情已存在!");
        }
        sysFriendsLink.setFriendsLinkTypeName(sysFriendsLinkType.getFriendsLinkTypeName());
        sysFriendsLink.update();
        return Result.success();
    }

    @Log("编辑友情链接状态")
    @ApiOperation("编辑友情链接状态")
    @ResponseBody
    @GetMapping(value = "/editSysFriendsLinkEnable/{id}")
    public Result editSysFriendsLinkEnable(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysFriendsLink sysFriendsLink = SysFriendsLink.me().setId(id).get();
        if (Objects.isNull(sysFriendsLink)) {
            return Result.toast("友情信息获取失败!");
        }
        if (sysFriendsLink.getIsEnable() == 1) {
            sysFriendsLink.setIsEnable(0);
        } else {
            sysFriendsLink.setIsEnable(1);
        }
        sysFriendsLink.update();
        return Result.success();
    }

    @Log("获取友情链接详情")
    @ApiOperation("获取友情链接详情")
    @ResponseBody
    @GetMapping(value = "/getSysFriendsLink/{id}")
    public Result getSysFriendsLink(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysFriendsLink sysFriendsLink = SysFriendsLink.me().setId(id).get();
        if (Objects.isNull(sysFriendsLink)) {
            return Result.toast("友情信息获取失败!");
        }
        return Result.build(() -> sysFriendsLink);
    }

    @Log("删除友情链接")
    @ApiOperation("删除友情链接")
    @ResponseBody
    @GetMapping(value = "/delSysFriendsLink/{id}")
    public Result delSysFriendsLink(@PathVariable String id) {
        if (Objects.isNull(id)) {
            return Result.toast("参数不能为空!");
        }
        SysFriendsLink sysFriendsLink = SysFriendsLink.me().setId(id).get();
        if (Objects.isNull(sysFriendsLink)) {
            return Result.toast("友情信息获取失败!");
        }
        sysFriendsLink.deleteById();
        return Result.success();
    }


}
