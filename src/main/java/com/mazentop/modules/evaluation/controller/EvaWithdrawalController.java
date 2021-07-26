package com.mazentop.modules.evaluation.controller;

import com.mazentop.entity.EvaWithdrawal;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.modules.evaluation.commond.EvaWithdrawalCommond;
import com.mazentop.modules.evaluation.dto.CredentialsDto;
import com.mazentop.modules.evaluation.service.EvaWithdrawalService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author zhoumei
 * @title: EvaWithdrawalController
 * @description: 提现
 * @date 2021/1/8 10:22
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/evaWithdrawal")
@Api(value = "/option/v1", tags = "返现申请", description = "EvaWithdrawalController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaWithdrawalController {

    @Autowired
    EvaWithdrawalService withdrawalService;


    @GetMapping(value = "/getPage")
    public Result getPage(EvaWithdrawalCommond commond) {
        return Result.build(() -> withdrawalService.getPage(commond));
    }


    @GetMapping(value = "/getWithdrawalStatus")
    public Result getWithdrawalStatus() {
        return Result.build(() -> withdrawalService.getWithdrawalStatus());
    }

    @GetMapping(value = "/getDetail/{applyId}")
    public Result getDetail(@PathVariable String applyId) {
        return Result.build(() -> withdrawalService.getDetail(applyId));
    }


    @PostMapping(value = "/examineWithdrawal")
    public Result examineWithdrawal(@RequestBody EvaWithdrawal withdrawal) {
        return Result.build(() -> withdrawalService.doExamineWithdrawal(withdrawal));
    }

    @PostMapping(value = "/withdrawal/credentials")
    public Result withdrawalCredentials(@RequestBody CredentialsDto dto) {
        EvaWithdrawal withdrawal = EvaWithdrawal.me().setId(dto.getId()).get();
        if (Objects.nonNull(withdrawal)){
            withdrawal.setTransferVoucher(dto.getCredentials());
            withdrawal.setTransactionNo(dto.getTransactionNo());
            withdrawal.setStatus(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus());
            withdrawal.update();
            return Result.success();
        }
        return Result.toast("参数异常");
    }

}
