package com.mazentop.modules.evaluation.service;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.*;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.modules.evaluation.commond.EvaWithdrawalCommond;
import com.mazentop.modules.evaluation.dto.CredentialsDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: zhoumei
 * @date: 2021/1/8
 * @description:提现
 */

@Service
public class EvaWithdrawalService {

    @Autowired
    BaseDao baseDao;

    public Page getPage(EvaWithdrawalCommond commond){
        commond.setOrderBy(" add_time desc");
        SearchTime searchTime = new SearchTime();
        if(commond.getStartTime() != null){
            searchTime.setStart(commond.getStartTime());
        }
        if(commond.getEndTime() != null){
            searchTime.setEnd(commond.getEndTime());
        }
        if (StrUtil.isNotBlank(commond.getEmail())){
            commond.setEmail("%"+commond.getEmail()+"%");
        }
        commond.setAddTime(searchTime);
        List<EvaWithdrawal> list = EvaWithdrawal.me().find(commond);
        for (EvaWithdrawal withdrawal : list) {
            withdrawal.addExten("withdrawalAmount",Helper.transformF2Y(new BigDecimal(withdrawal.getWithdrawalAmount())));
            // 申请人
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(withdrawal.getFkClienteleId()).get();
            withdrawal.addExten("addUserName",cliClienteleInfo.getLoginName());
        }
        return new Page(list,commond);
    }


    /**
     * 获取申请状态数量
     * @return
     */
    public HashMap<String, Object> getWithdrawalStatus() {
        HashMap<String, Object> map = new HashMap<String, Object>(4);

        map.put("allCount", EvaWithdrawal.me().findCount());
        map.put("reviewedCount", EvaWithdrawal.me().setStatus(EvaCashBackApplyStatusEnum.REVIEVED.getStatus()).findCount());
        map.put("adoptCount", EvaWithdrawal.me().setStatus(EvaCashBackApplyStatusEnum.ADOPT.getStatus()).findCount());
        map.put("rejectCount", EvaWithdrawal.me().setStatus(EvaCashBackApplyStatusEnum.REJECT.getStatus()).findCount());
        map.put("transferCount", EvaWithdrawal.me().setStatus(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus()).findCount());

        return map;
    }


    /**
     * 获取返现申请详情
     * @param id
     * @return
     */
    public EvaWithdrawal getDetail(String id) {
        EvaWithdrawal withdrawal = EvaWithdrawal.me().setId(id).get();
        // 申请人
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(withdrawal.getFkClienteleId()).get();
        withdrawal.addExten("addUserName",cliClienteleInfo.getLoginName());
        withdrawal.addExten("paypalAccount",cliClienteleInfo.getPaypalAccount());

        // 审核人
        if(!withdrawal.getStatus().equals(EvaCashBackApplyStatusEnum.REVIEVED.getStatus())){
            EmpEmployeeInfo employee = EmpEmployeeInfo.me().setId(withdrawal.getReviewerId()).get();
            withdrawal.addExten("reviewer",employee.getEmployeeName());
        }
        withdrawal.addExten("withdrawalAmount",Helper.transformF2Y(new BigDecimal(withdrawal.getWithdrawalAmount())));

        return withdrawal;

    }

    /**
     * 审核提现申请
     * @param withdrawal
     * @return
     */
    public R doExamineWithdrawal(EvaWithdrawal withdrawal){
        withdrawal.setReviewerId(Subject.id());
        withdrawal.setReviewerTime(Utils.currentTimeSecond());
        withdrawal.update();

        EvaWithdrawal evaWithdrawal = EvaWithdrawal.me().setId(withdrawal.getId()).get();
        // 同意提现，处理佣金余额
        if(withdrawal.getStatus().equals(EvaCashBackApplyStatusEnum.ADOPT.getStatus())){
            EvaUserBill userBill =  EvaUserBill.me().setFkClienteleId(evaWithdrawal.getFkClienteleId()).get();
            if(Objects.isNull(userBill)){
                userBill = new EvaUserBill();
                userBill.setFkClienteleId(evaWithdrawal.getFkClienteleId());
                userBill.setTotalCommission(evaWithdrawal.getWithdrawalAmount());
                userBill.setCommissionWithdrawal(evaWithdrawal.getWithdrawalAmount());
                userBill.insert();
            }else{
                userBill.setTotalCommission(userBill.getTotalCommission() + evaWithdrawal.getWithdrawalAmount());
                userBill.setCommissionWithdrawal(userBill.getTotalCashWithdrawal() + evaWithdrawal.getWithdrawalAmount());
                userBill.setCommissionToBeWithdrawn(userBill.getCommissionToBeWithdrawn() - evaWithdrawal.getWithdrawalAmount());
                userBill.update();
            }
        // 不同意
        }else{
            EvaUserBill userBill =  EvaUserBill.me().setFkClienteleId(evaWithdrawal.getFkClienteleId()).get();
            userBill.setCommissionToBeWithdrawn(userBill.getCommissionToBeWithdrawn() - evaWithdrawal.getWithdrawalAmount());
            userBill.setCommissionBalance(userBill.getCommissionBalance() + evaWithdrawal.getWithdrawalAmount());
            userBill.update();

        }
        return R.ok();
    }
}
