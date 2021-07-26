package com.mazentop.modules.emp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.mazentop.entity.*;
import com.mazentop.excel.entity.ProCommentEntity;
import com.mazentop.modules.emp.commond.ProCommentCommond;
import com.mazentop.modules.emp.dto.query.ProCommentQueryDto;
import com.mazentop.modules.emp.service.ProCommentService;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author dengy
 * @title: ProCommentController
 * @description: 商品评论管理
 * @date 2020/3/12 18:32
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/proComment")
@Api(value = "/option/v1", tags = "商品评论管理", description = "ProCommentController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProCommentController {

    @Autowired
    ProCommentService proCommentService;


    @Log("商品评论管理列表")
    @GetMapping(value = "/proCommentList")
    @ApiOperation(value = "商品评论管理列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commond", value = "查询条件", required = true, dataType = "ProCommentCommond")
    })
    public Result proCommentList(ProCommentCommond commond) {
        return Result.build(() -> proCommentService.proCommentList(commond));
    }


    @Log("获取商品评论数量")
    @GetMapping(value = "/getCommentStatus")
    @ApiOperation(value = "获取商品评论数量", notes = "入参:查询条件")
    public Result getCommentStatus() {
        return Result.build(() -> proCommentService.getCommentStatus());
    }



    @Log("修改商品评论")
    @PostMapping(value = "/doProCommentUpdate")
    @ApiOperation(value = "修改商品评论", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proComment", value = "保存参数", required = true, dataType = "ProComment")
    })
    public Result doProCommentUpdate(@RequestBody ProComment proComment) {
        return Result.build(() -> proCommentService.doProCommentUpdate(proComment));
    }

    @Log("新增商品评论")
    @PostMapping(value = "/doProCommentSave/{productId}")
    @ApiOperation(value = "新增商品评论", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proComment", value = "保存参数", required = true, dataType = "ProComment")
    })
    public Result doProCommentSave(@PathVariable String productId,@RequestBody List<ProComment> comments) {
        return Result.build(() -> proCommentService.doProCommentSave(productId,comments));
    }


    @Log("复制商品评论")
    @PostMapping(value = "/doCopy/{commentId}")
    @ApiOperation(value = "复制商品评论", notes = "入参:评论id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "String")
    })
    public Result doCopy(@PathVariable String commentId) {
        return Result.build(() -> proCommentService.doCopy(commentId));
    }


    @Log("删除商品评论")
    @PostMapping("/deleteProComment")
    @ApiOperation(value = "删除商品评论", notes = "入参:品牌主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result deleteProComment(@RequestBody List<String> ids) {
        return Result.build(() -> proCommentService.doDeleteProComment(ids));
    }



    @Log("查询单个商品评论")
    @GetMapping(value = "/getProComment/{id}")
    @ApiOperation(value = "查询单个商品信息", notes = "入参:查询条件")
    public Result getProComment(@PathVariable String id) {
        return Result.build(() -> {
            ProComment proComment = ProComment.me().setId(id).get();
            return proComment;
        });
    }


    @Log("查商品评论情况")
    @GetMapping(value = "/getProductCommentDetails/{id}")
    @ApiOperation(value = "查商品评论情况", notes = "入参:商品id")
    public Result getProductMasterInfo(@PathVariable String id) {
        return Result.build(() -> proCommentService.getProductCommentDetails(id));
    }


}
