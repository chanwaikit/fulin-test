package com.mazentop.modules.user.controller;

import com.mazentop.entity.EvaCashBackApply;
import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.entity.EvaWithdrawal;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.user.commond.EvaluationUserOrderCommond;
import com.mazentop.modules.user.commond.EvaluationWithdrawalCommond;
import com.mazentop.modules.user.dto.EvawithdrawalDto;
import com.mazentop.modules.user.service.EvaluationBalanceService;
import com.mazentop.modules.user.service.EvaluationOrderService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.controller.BaseController;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhoumei
 * @date: 2020/5/11
 * @description: 测评-我的账单
 */
@Controller
@RequestMapping("/evaBalance")
public class EvaluationBalanceController extends BaseController {

    @Autowired
    EvaluationBalanceService balanceService;


    /**
     *  佣金提现
     * @param evaWithdrawal
     * @return
     */
    @PostMapping("/applyWithdrawal")
    @ResponseBody
    public Result applyWithdrawal(EvawithdrawalDto evaWithdrawal) {
        return Result.build(() -> balanceService.doApplyWithdrawal(evaWithdrawal));
    }

    /**
     * 提现分页
     * @param commond
     * @return
     */
    @GetMapping("/queryUserCommissionWithdrawal")
    @ResponseBody
    public Result<Page<EvaWithdrawal>> queryUserCommissionWithdrawal(EvaluationWithdrawalCommond commond) {
        return Result.build(() -> balanceService.queryUserCommissionWithdrawal(commond));
    }


}
