package com.mazentop.modules.user.service;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.modules.emp.commond.DictionaryCommond;
import com.mazentop.modules.user.commond.EvaluationUserOrderCommond;
import com.mazentop.modules.user.commond.EvaluationUserRecommendationCommond;
import com.mazentop.modules.user.commond.EvaluationWithdrawalCommond;
import com.mazentop.modules.user.dto.EvawithdrawalDto;
import com.mazentop.modules.web.User;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author cq
 * 订单service
 */
@Service
public class EvaluationBalanceService {

    @Autowired
    private BaseDao baseDao;


    /**
     *  返现提现
     * @param evaWithdrawal
     * @return
     */
    public R doApplyWithdrawal(EvawithdrawalDto evaWithdrawal) {

        // 判断佣金余额够不够提现
        EvaUserBill userBill =  EvaUserBill.me().setFkClienteleId(User.id()).get();
        if(userBill.getCommissionBalance().intValue()<=0){
            return R.error(997,"Insufficient commission balance");
        }
        evaWithdrawal.setStatus(EvaCashBackApplyStatusEnum.REVIEVED.getStatus());
        evaWithdrawal.setAddUserId(User.id());
        evaWithdrawal.setFkClienteleId(User.id());
        evaWithdrawal.setWithdrawalAmount(userBill.getCommissionBalance());
        evaWithdrawal.setSn(IDSnowflake.id());
        evaWithdrawal.insert();
        // 处理账单佣金数据
        userBill.setCommissionToBeWithdrawn(userBill.getCommissionToBeWithdrawn() + userBill.getCommissionBalance());
        userBill.setCommissionBalance(userBill.getCommissionBalance() - userBill.getCommissionBalance());
        userBill.update();

        return R.ok();
    }


    /**
     * 佣金提现列表
     * @param commond
     * @return
     */
    public Page<EvaWithdrawal> queryUserCommissionWithdrawal(EvaluationWithdrawalCommond commond) {
        commond.setType(2);
        commond.setFkClienteleId(User.id());
        List<EvaWithdrawal> list = EvaWithdrawal.me().find(commond);
        for (EvaWithdrawal withdrawal: list) {
            withdrawal.addExten("withdrawalAmount",Helper.transformF2Y(new BigDecimal(withdrawal.getWithdrawalAmount())));
            withdrawal.addExten("status",EvaCashBackApplyStatusEnum.getDescEn(withdrawal.getStatus()));
        }
        return new Page<>(list, commond);
    }



    /**
     * 获取推荐用户列表
     * @param commond
     * @return
     */
    public Page<EvaUserRecommendation> queryUserRecommendation(EvaluationUserRecommendationCommond commond) {
        commond.setReferrerId(User.id());
        List<EvaUserRecommendation> list = EvaUserRecommendation.me().find(commond);
        for (EvaUserRecommendation recommendation: list) {
            CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(recommendation.getUserId()).get();
            recommendation.addExten("addTime",clienteleInfo.getAddTime());
            recommendation.addExten("loginName",clienteleInfo.getLoginName());
        }
        return new Page<>(list, commond);
    }
}
