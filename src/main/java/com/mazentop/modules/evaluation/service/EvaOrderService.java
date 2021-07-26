package com.mazentop.modules.evaluation.service;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ClienteleCertificationEnum;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.modules.emp.dto.EvaOrderDto;
import com.mazentop.modules.evaluation.commond.EvaOrderCommond;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: dengy
 * @date: 2020/3/13
 * @description:
 */

@Service
public class EvaOrderService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    private EvaProProductService proProductService;


    public Page getOrderList(EvaOrderCommond commond) {
        commond.setIsEnable(BooleanEnum.TRUE.getValue());
        commond.setOrderBy(" add_time desc");
        SearchTime searchTime = new SearchTime();
        if (commond.getStartTime() != null) {
            searchTime.setStart(commond.getStartTime());
        }
        if (commond.getEndTime() != null) {
            searchTime.setEnd(commond.getEndTime());
        }
        if (StrUtil.isNotBlank(commond.getEmail())) {
            commond.setEmail("%" + commond.getEmail() + "%");
        }
        commond.setAddTime(searchTime);
        List<EvaOrdOrder> list = EvaOrdOrder.me().find(commond);
        for (EvaOrdOrder ordOrder : list) {
            ordOrder.addExten("price", Helper.transformF2Y(new BigDecimal(ordOrder.getPrice())));
            ordOrder.addExten("originalPrice", Helper.transformF2Y(new BigDecimal(ordOrder.getOriginalPrice())));
            ordOrder.addExten("rebate", Helper.transformF2Y(new BigDecimal(ordOrder.getRebate())));
            ordOrder.addExten("status", EvaOrdOrderStatusEnum.getDescStr(ordOrder.getStatus()));
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(ordOrder.getAddUserId()).get();
            if (Objects.nonNull(cliClienteleInfo)) {
                ordOrder.addExten("email", cliClienteleInfo.getEmail());
                EvaProProduct proProduct = EvaProProduct.me().setProductSku(ordOrder.getProductSku()).get();
                if (Objects.nonNull(proProduct)) {
                    SkinCountry country = SkinCountry.me().setCountryCode(proProduct.getCountryId()).get();
                    ordOrder.addExten("country", country.getCountryCode());
                    ordOrder.addExten("currency", country.getCurrency());
                }
            }
            ordOrder.addExten("name", "");
            ordOrder.addExten("value", "");
            if (StringUtils.isNotBlank(ordOrder.getTag())) {
                SysDictionary sysDictionary = SysDictionary.me().setId(ordOrder.getTag()).get();
                if (!Objects.isNull(sysDictionary)) {
                    ordOrder.addExten("name", sysDictionary.getName());
                    ordOrder.addExten("value", sysDictionary.getValue());
                }
            }
            if (ordOrder.getStatus().equals(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus())){
                EvaCashBackApply backApply = EvaCashBackApply.me().setEvaOrderId(ordOrder.getId()).get();
                ordOrder.addExten("backApplyStatusDesc", EvaCashBackApplyStatusEnum.getDesc(backApply.getStatus()));
                ordOrder.addExten("backApplyStatus", backApply.getStatus());
            }

        }
        return new Page(list, commond);
    }


    public HashMap<String, Object> getOrderStatus() {
        HashMap<String, Object> map = new HashMap<String, Object>(3);

        // 全部
        map.put("allCount", EvaOrdOrder.me().setIsEnable(BooleanEnum.TRUE.getValue()).findCount());
        EvaOrderCommond commond = new EvaOrderCommond();
        commond.setIsEnable(BooleanEnum.TRUE.getValue());
        // 待处理
        commond.setStatus("pending");
        map.put("pendingCount", EvaOrdOrder.me().findCount(commond));
        // 待审核
        commond.setStatus("wait");
        map.put("waitCount", EvaOrdOrder.me().findCount(commond));
        // 审核中
        commond.setStatus("review");
        map.put("reviewCount", EvaOrdOrder.me().findCount(commond));
        // 已完成
        commond.setStatus("completed");
        map.put("completedCount", EvaOrdOrder.me().findCount(commond));
        // 已关闭
        commond.setStatus("closed");
        map.put("closedCount", EvaOrdOrder.me().findCount(commond));
        return map;
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    public EvaOrdOrder getOrderDetail(String orderId) {
        EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(orderId).get();
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(evaOrdOrder.getFkClienteleId()).get();
        evaOrdOrder.addExten("userName", cliClienteleInfo.getLoginName());
        evaOrdOrder.addExten("price", Helper.transformF2Y(new BigDecimal(evaOrdOrder.getPrice())));
        evaOrdOrder.addExten("originalPrice", Helper.transformF2Y(new BigDecimal(evaOrdOrder.getOriginalPrice())));
        evaOrdOrder.addExten("amazonProfileUrl",cliClienteleInfo.getAmazonProfileUrl());
        evaOrdOrder.addExten("amazonProfileScreenshot",cliClienteleInfo.getAmazonProfileScreenshot());
        evaOrdOrder.addExten("certification",cliClienteleInfo.getAccountCertification());
        // 获取商品币种
        EvaProProduct evaProProduct = EvaProProduct.me().setId(evaOrdOrder.getFkProductId()).get();
        if (Objects.nonNull(evaProProduct)) {
            SkinCountry skinCountry = SkinCountry.me().setCountryCode(evaProProduct.getCountryId()).get();
            evaOrdOrder.addExten("currency", skinCountry.getCurrency());
        }
        evaOrdOrder.addExten("rebate", Helper.transformF2Y(new BigDecimal(evaOrdOrder.getRebate())));
        // 审核人
        if (evaOrdOrder.getStatus() > 1) {
            EmpEmployeeInfo employee = EmpEmployeeInfo.me().setId(evaOrdOrder.getReviewerId()).get();
            if (Objects.nonNull(employee)) {
                evaOrdOrder.addExten("reviewer", employee.getEmployeeName());
            }
        }

        //评论审核人
        if (evaOrdOrder.getStatus().equals(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus())
                ||evaOrdOrder.getStatus().equals(EvaOrdOrderStatusEnum.CASHBACK_REJECT.getStatus())){
            EmpEmployeeInfo employee = EmpEmployeeInfo.me().setId(evaOrdOrder.getCommentsId()).get();
            if (Objects.nonNull(employee)) {
                evaOrdOrder.addExten("comments", employee.getEmployeeName());
            }
        }
        // 返回订单返现申请信息
        EvaCashBackApply apply = EvaCashBackApply.me().setEvaOrderId(orderId).get();
        if (Objects.nonNull(apply)) {
            //审核人
            if (apply.getStatus() > 1) {
                EmpEmployeeInfo employee = EmpEmployeeInfo.me().setId(evaOrdOrder.getReviewerId()).get();
                if (Objects.nonNull(employee)) {
                    apply.addExten("reviewer", employee.getEmployeeName());
                }
            }
            evaOrdOrder.addExten("cashBackApply", apply);
        }
        return evaOrdOrder;
    }


    /**
     * 亚马逊信息审核
     *
     * @param dto
     * @return
     */
    public R doExamineApply(EvaOrderDto dto) {
        EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(dto.getId()).get();
        if (dto.getType().equals("order")){
            evaOrdOrder.setReviewerId(Subject.id());
            evaOrdOrder.setReviewerTime(Utils.currentTimeSecond());
            evaOrdOrder.setReviewerRemarks(dto.getReviewerRemarks());
        }else if (dto.getType().equals("comments")){
            evaOrdOrder.setCommentsId(Subject.id());
            evaOrdOrder.setCommentsTime(Utils.currentTimeSecond());
            evaOrdOrder.setCommentsRemarks(dto.getReviewerRemarks());
        }
        if (dto.getStatus().equals(EvaOrdOrderStatusEnum.AMAZON_ADOPT.getStatus())){
            CliClienteleInfo info = CliClienteleInfo.me().setId(evaOrdOrder.getFkClienteleId()).get();
            if (!info.getAccountCertification().equals(ClienteleCertificationEnum.AUTHENTICATION.status())){
                info.setAccountCertification(ClienteleCertificationEnum.AUTHENTICATION.status());
                info.update();
            }
        }

        evaOrdOrder.setStatus(dto.getStatus());
        evaOrdOrder.update();

        return R.ok();
    }
    /**
     * 关闭订单
     * @param orderId
     * @return
     */
    public R doCloseOrder(String orderId) {
        EvaOrdOrder ordOrder = EvaOrdOrder.me().setId(orderId).get();
        if (ordOrder.getStatus().equals(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus())){
            EvaCashBackApply evaCashBackApply = EvaCashBackApply.me().setEvaOrderId(ordOrder.getId()).get();
            if (evaCashBackApply.getStatus().equals(EvaCashBackApplyStatusEnum.ADOPT.getStatus())
                    ||evaCashBackApply.getStatus().equals(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus())){
                return R.error("订单已通过返现申请 无法关闭！");
            }
        }
        ordOrder.setStatus(EvaOrdOrderStatusEnum.CLOSED_ORDERS.getStatus());
        ordOrder.update();
        proProductService.doTrialsTimes(ordOrder.getFkProductId());
        return R.ok();
    }

}
