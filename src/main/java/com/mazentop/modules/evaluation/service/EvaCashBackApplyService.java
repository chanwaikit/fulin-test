package com.mazentop.modules.evaluation.service;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.modules.evaluation.commond.EvaCashBackApplyCommond;
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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author: zhoumei
 * @date: 2021/01/9
 * @description: 返现申请
 */

@Service
public class EvaCashBackApplyService {

    @Autowired
    BaseDao baseDao;

    public Page getPage(EvaCashBackApplyCommond commond){
        commond.setOrderBy(" add_time desc");
        SearchTime searchTime = new SearchTime();
        if(commond.getStartTime() != null){
            searchTime.setStart(commond.getStartTime());
        }
        if(commond.getEndTime() != null){
            searchTime.setEnd(commond.getEndTime());
        }
        if (StrUtil.isNotBlank(commond.getAsin())){
            commond.setAsin("%"+commond.getAsin()+"%");
        }
        commond.setAddTime(searchTime);
        List<EvaCashBackApply> list = EvaCashBackApply.me().find(commond);
        for (EvaCashBackApply cashBackApply : list) {
            // 申请人
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(cashBackApply.getFkClienteleId()).get();
            cashBackApply.addExten("addUserName",cliClienteleInfo.getLoginName());
            //订单编号
            EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(cashBackApply.getEvaOrderId()).get();
            EvaProProduct proProduct = EvaProProduct.me().setId(evaOrdOrder.getFkProductId()).get();
            if (Objects.nonNull(proProduct)) {
                SysCountry country = SysCountry.me().setId(proProduct.getCountryId()).get();
                cashBackApply.addExten("country", country.getNameCn());
            }
            cashBackApply.addExten("orderNo",evaOrdOrder.getOrderNo());
            cashBackApply.addExten("amazonStoreUrl",evaOrdOrder.getAmazonStoreUrl());
            cashBackApply.addExten("amazonOrderNo",evaOrdOrder.getAmazonOrderNo());
        }
        return new Page(list,commond);
    }


    /**
     * 获取申请状态数量
     * @return
     */
    public HashMap<String, Object> getApplyStatus() {
        HashMap<String, Object> map = new HashMap<String, Object>(4);

        map.put("allCount", EvaCashBackApply.me().findCount());
        map.put("reviewedCount", EvaCashBackApply.me().setStatus(EvaCashBackApplyStatusEnum.REVIEVED.getStatus()).findCount());
        map.put("adoptCount", EvaCashBackApply.me().setStatus(EvaCashBackApplyStatusEnum.ADOPT.getStatus()).findCount());
        map.put("rejectCount", EvaCashBackApply.me().setStatus(EvaCashBackApplyStatusEnum.REJECT.getStatus()).findCount());
        map.put("transferCount", EvaCashBackApply.me().setStatus(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus()).findCount());

        return map;
    }


    /**
     * 获取返现申请详情
     * @param applyId
     * @return
     */
    public EvaCashBackApply getDetail(String applyId) {
        EvaCashBackApply cashBackApply = EvaCashBackApply.me().setId(applyId).get();
        EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(cashBackApply.getEvaOrderId()).get();
        evaOrdOrder.addExten("price", Helper.transformF2Y(new BigDecimal(evaOrdOrder.getPrice())));
        evaOrdOrder.addExten("originalPrice",Helper.transformF2Y(new BigDecimal(evaOrdOrder.getOriginalPrice())));
        evaOrdOrder.addExten("rebate",Helper.transformF2Y(new BigDecimal(evaOrdOrder.getRebate())));
        CliClienteleInfo applyUser = CliClienteleInfo.me().setId(evaOrdOrder.getApplyUserId()).get();
        if(!Objects.isNull(applyUser)) {
            evaOrdOrder.addExten("applyUserName", applyUser.getLoginName());
        }
        cashBackApply.addExten("evaOrdOrder",evaOrdOrder);
        // 申请人
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(cashBackApply.getFkClienteleId()).get();
        cashBackApply.addExten("addUserName",cliClienteleInfo.getLoginName());

        // 审核人
        if(!cashBackApply.getStatus().equals(EvaCashBackApplyStatusEnum.REVIEVED.getStatus())){
            EmpEmployeeInfo employee = EmpEmployeeInfo.me().setId(cashBackApply.getReviewerId()).get();
            cashBackApply.addExten("reviewer",employee.getEmployeeName());
        }
        return cashBackApply;
    }

    /**
     * 审核返现申请
     * @param cashBackApply
     * @return
     */
    public R doExamineApply(EvaCashBackApply cashBackApply){
        cashBackApply.setReviewerId(Subject.id());
        cashBackApply.setReviewerTime(Utils.currentTimeSecond());
        cashBackApply.update();

        EvaCashBackApply backApply = EvaCashBackApply.me().setId(cashBackApply.getId()).get();
        EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(backApply.getEvaOrderId()).get();
        // 同意返现，增加用户返现金额
        if(cashBackApply.getStatus().equals(EvaCashBackApplyStatusEnum.ADOPT.getStatus())){

            EvaUserBill userBill =  EvaUserBill.me().setFkClienteleId(evaOrdOrder.getFkClienteleId()).get();
            if(Objects.isNull(userBill)){
                userBill = new EvaUserBill();
                userBill.setFkClienteleId(evaOrdOrder.getFkClienteleId());
                userBill.setTotalCashBack(evaOrdOrder.getRebate());
                userBill.setTotalCashWithdrawal(evaOrdOrder.getRebate());
                userBill.insert();
            }else{
                userBill.setTotalCashBack(userBill.getTotalCashBack() + evaOrdOrder.getRebate());
                userBill.setTotalCashWithdrawal(userBill.getTotalCashWithdrawal() + evaOrdOrder.getRebate());
                userBill.update();
            }
            // 更新商品销售数量
            EvaProProduct proProduct = EvaProProduct.me().setId(evaOrdOrder.getFkProductId()).get();
            if(Objects.nonNull(proProduct)){
                proProduct.setSales(proProduct.getSales()+1).update();
            }
            // 改变订单状态
            evaOrdOrder.setStatus(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus()).update();

            // 更改用户购买次数以及返现金额
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(evaOrdOrder.getFkClienteleId()).get();
            cliClienteleInfo.setBuyTime(cliClienteleInfo.getBuyTime()+1);
            cliClienteleInfo.setBuySum(cliClienteleInfo.getBuySum()+evaOrdOrder.getRebate());
            cliClienteleInfo.update();

            // 分享人添加分享金
            EvaUserRecommendation userRecommendation = EvaUserRecommendation.me().setUserId(evaOrdOrder.getFkClienteleId()).get();
            if(Objects.nonNull(userRecommendation) && userRecommendation.getIsPaid().equals(BooleanEnum.FALSE.getValue())){
                //获取分享佣金
                SysOptions commissionRules = SysOptions.me().setOptionKey("site_commission_rules").get();
                EvaUserBill userBill2 =  EvaUserBill.me().setFkClienteleId(userRecommendation.getReferrerId()).get();
                if(Objects.isNull(userBill2)){
                    userBill2 = new EvaUserBill();
                    userBill2.setFkClienteleId(userRecommendation.getReferrerId());
                }
                userBill2.setCommissionBalance(Helper.transformY2F(new BigDecimal(commissionRules.getOptionValue())).longValue());
                userBill2.insertOrUpdate();

                // 更新发放状态
                userRecommendation.setIsPaid(BooleanEnum.TRUE.getValue()).update();
            }
        }else{
            // 改变订单状态
            evaOrdOrder.setStatus(EvaOrdOrderStatusEnum.CASHBACK_REJECT.getStatus()).update();
        }
        return R.ok();
    }




}
