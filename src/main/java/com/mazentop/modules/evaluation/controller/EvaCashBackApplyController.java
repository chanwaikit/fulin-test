package com.mazentop.modules.evaluation.controller;


import com.mazentop.entity.EvaCashBackApply;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.modules.evaluation.commond.EvaCashBackApplyCommond;
import com.mazentop.modules.evaluation.dto.CredentialsDto;
import com.mazentop.modules.evaluation.service.EvaCashBackApplyService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author zhoumei
 * @title: EvaCashBackApplyController
 * @description: 返现申请表
 * @date 2021/1/6 10:22
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/evaCashBackApply")
@Api(value = "/option/v1", tags = "返现申请", description = "EvaCashBackApplyController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaCashBackApplyController {

    @Autowired
    EvaCashBackApplyService applyService;


    @GetMapping(value = "/getPage")
    @ApiOperation(value = "返现列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commond", value = "查询条件", required = true, dataType = "EvaCashBackApplyCommond")
    })
    public Result getPage(EvaCashBackApplyCommond commond) {
        return Result.build(() -> applyService.getPage(commond));
    }


    @GetMapping(value = "/getApplyStatus")
    @ApiOperation(value = "获取订单状态数量", notes = "入参:查询条件")
    public Result getApplyStatus() {
        return Result.build(() -> applyService.getApplyStatus());
    }


    @GetMapping(value = "/getDetail/{applyId}")
    @ApiOperation(value = "获取返现申请详情", notes = "入参:查询条件")
    public Result getDetail(@PathVariable String applyId) {
        return Result.build(() -> applyService.getDetail(applyId));
    }


    @PostMapping(value = "/examineApply")
    @ApiOperation(value = "审核返现审核", notes = "入参:查询条件")
    public Result examineApply(@RequestBody EvaCashBackApply cashBackApply) {
        return Result.build(() -> applyService.doExamineApply(cashBackApply));
    }

    @PostMapping(value = "/cashBackApply/credentials")
    public Result cashBackApplyCredentials(@RequestBody CredentialsDto dto) {
        EvaCashBackApply cashBackApply = EvaCashBackApply.me().setId(dto.getId()).get();
        if (Objects.nonNull(cashBackApply)){
            cashBackApply.setStatus(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus());
            cashBackApply.setPaypalSerialNo(dto.getTransactionNo());
            cashBackApply.setTransferVoucher(dto.getCredentials());
            cashBackApply.update();
            return Result.success();
        }
        return  Result.toast("参数异常");
    }


}
