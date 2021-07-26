package com.mazentop.modules.evaluation.controller;

import com.mazentop.modules.evaluation.commond.EvaCommissionCommond;
import com.mazentop.modules.evaluation.commond.InvitedCommond;
import com.mazentop.modules.evaluation.service.EvaCommissionDetailsService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoumei
 * @title: EvaCommissionDetailsController
 * @description: 佣金明细
 * @date 2021/1/8 10:22
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/evaCommissionDetails")
@Api(value = "/option/v1", tags = "佣金明细", description = "EvaCommissionDetailsController", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaCommissionDetailsController {

    @Autowired
    EvaCommissionDetailsService commissionService;


    @GetMapping(value = "/getPage")
    public Result getPage(EvaCommissionCommond commond) {
        return Result.build(() -> commissionService.getPage(commond));
    }

    @GetMapping(value = "/beInvited")
    public Result getBeInvitedByUser(InvitedCommond commond) {
        return Result.build(() -> commissionService.getBeInvitedByUser(commond));
    }

}
