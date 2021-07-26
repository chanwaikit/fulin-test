package com.mazentop.modules.user.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.model.*;
import com.mazentop.modules.emp.commond.DictionaryCommond;
import com.mazentop.modules.evaluation.service.EvaProProductService;
import com.mazentop.modules.user.commond.EvaluationUserOrderCommond;
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
 * @author zhoumei
 * 测评-订单service
 */
@Service
public class EvaluationOrderService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private EvaProProductService proProductService;

    /**
     *获取当前用户的订单
     * @param commond
     * @return
     */
    public Page<EvaOrdOrder> queryOrderPageList(EvaluationUserOrderCommond commond) {
        commond.setIsEnable(BooleanEnum.TRUE.getValue());
        commond.setO("-" + EvaOrdOrder.F_ADD_TIME);

        List<EvaOrdOrder> orderList = EvaOrdOrder.me().find(commond);

        for (EvaOrdOrder evaOrdOrder : orderList) {
            evaOrdOrder.addExten("statusDesc",EvaOrdOrderStatusEnum.getDescEn(evaOrdOrder.getStatus()));
            evaOrdOrder.addExten("rebate", Helper.transformF2Y(new BigDecimal(evaOrdOrder.getRebate())));
            // 如果过期，是负数
            long time = evaOrdOrder.getExpirationTime() - Utils.currentTimeSecond();
            if(time <= 0 && (EvaOrdOrderStatusEnum.PENDING.getStatus().equals(evaOrdOrder.getStatus()))){
                    evaOrdOrder.setStatus(EvaOrdOrderStatusEnum.CLOSED_ORDERS.getStatus());
                }
            evaOrdOrder.addExten("time", time);
            // 检验商品是否下架或被删除
            EvaProProduct evaProProduct = EvaProProduct.me().setId(evaOrdOrder.getFkProductId()).get();
            evaOrdOrder.addExten("orderTiming",evaProProduct.getOrderTiming());
            evaOrdOrder.addExten("amazonProductUrl",evaProProduct.getAmazonUrl());
            evaOrdOrder.addExten("model",evaProProduct.getBuyingPatterns());
            if (evaOrdOrder.getStatus().equals(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus())){
                EvaCashBackApply apply = EvaCashBackApply.me().setEvaOrderId(evaOrdOrder.getId()).get();
                if (Objects.nonNull(apply)&&apply.getStatus().equals(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus())){
                    evaOrdOrder.addExten("transferVoucher",apply.getTransferVoucher());
                    evaOrdOrder.addExten("transactionNo",apply.getPaypalSerialNo());
                }
            }


        }
        return new Page<>(orderList, commond);
    }


    /**
     * 获取商品状态数量
     * @return
     */
    public HashMap<String, Object> getOrderStatus() {
        HashMap<String, Object> map = new HashMap<>(3);

        String sql = "SELECT sum(rebate) as rebate  from  eva_ord_order WHERE status =:status AND fk_clientele_id =:userId AND is_enable = 1 ";
        Map<String, Object> param = new HashMap<>(2);
        param.put("userId",User.id());
        param.put("status",6);
        Long payable = baseDao.queryForLong(sql,param);
        map.put("payable", Helper.transformF2Y(payable));

        param.put("status",1);
        Long pending = baseDao.queryForLong(sql,param);
        map.put("pending", Helper.transformF2Y(pending));

        param.put("status",8);
        Long closed = baseDao.queryForLong(sql,param);
        map.put("closed", Helper.transformF2Y(closed));

        return map;
    }


    /**
     * 严重是否已添加该商品订单
     * @param productId
     * @return
     */
    public R checkOrder(String productId) {
        if (!User.isAuth()){
           return R.error("Please log in again!");
        }
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        if (!cliClienteleInfo.getIsEnable().equals(Status.YES)){
            return R.error("The account is disabled and cannot be purchased!");
        }
        Long count = EvaOrdOrder.me().setFkClienteleId(User.id()).setFkProductId(productId).setIsEnable(Status.YES).setStatus(EvaOrdOrderStatusEnum.PENDING.getStatus()).findCount();
        if(count > 0){
            // 已经加入订单
            return R.error(999,"There are still unprocessed orders for the current product! Please process the order before adding it");
        }

        Long pendingCount = EvaOrdOrder.me().setFkClienteleId(User.id()).setStatus(EvaOrdOrderStatusEnum.PENDING.getStatus()).setIsEnable(Status.YES).findCount();
        if (pendingCount>3){
            //待处理订单不能超过三单
            return R.error(999,"Too many unprocessed orders! Please add after processing these orders");
        }
        Long countCompleted = EvaOrdOrder.me().setFkClienteleId(User.id()).setFkProductId(productId).setStatus(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus()).setIsEnable(Status.YES).findCount();
        if (countCompleted>0){
            //已经购买过的产品
            return R.error(999,"Each user is limited to one purchase!");
        }
        String whereSql=" fk_clientele_id =:clienteleId and fk_product_id=:productId and is_enable=1  and status in (:status) ";
        Map<String,Object> param=new HashMap<>();
        param.put("clienteleId",User.id());
        param.put("productId",productId);
        param.put("status",Arrays.asList(EvaOrdOrderStatusEnum.AMAZON_WAIT.getStatus(),EvaOrdOrderStatusEnum.AMAZON_ADOPT.getStatus(),EvaOrdOrderStatusEnum.CASHBACK_WAIT.getStatus()));
        Long countReview = EvaOrdOrder.me().findCount(whereSql,param);
        if (countReview>0){
            //待审核或者已审核通过
            return R.error(999,"This product is under review, please do not repeat the purchase!");
        }
        // 判断活动次数
        EvaProProduct proProduct = EvaProProduct.me().setId(productId).ignoreSelectFields(EvaProProduct.F_PRODUCT_DESCRIPTION).get();
        if(proProduct.getTrialsTotalTimes().equals(proProduct.getTrialsTimes())){
            return R.error(998,"The product activity is over");
        }
        return R.ok();
    }


    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    public EvaOrdOrder getOrderDetails(String orderId) {
        EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(orderId).get();
        if(Objects.nonNull(evaOrdOrder)){
            evaOrdOrder.addExten("rebates",Helper.transformF2Y(new BigDecimal(evaOrdOrder.getRebate())));
            // 返回订单返现申请信息
            EvaCashBackApply apply = EvaCashBackApply.me().setEvaOrderId(orderId).get();
            if(Objects.nonNull(apply)){
                apply.addExten("auditStatus",EvaCashBackApplyStatusEnum.getDescEn(apply.getStatus()));
                evaOrdOrder.addExten("cashBackApply",apply);
            }
            evaOrdOrder.addExten("auditStatus",EvaOrdOrderStatusEnum.getDescEn(evaOrdOrder.getStatus()));
            evaOrdOrder.addExten("time",evaOrdOrder.getExpirationTime() - Utils.currentTimeSecond());
        }
        return evaOrdOrder;
    }


    /**
     * 获取产品详情
     * @param productId
     * @return
     */
    public EvaProProduct getProductDetails(String productId) {
        EvaProProduct proProduct =  EvaProProduct.me().setId(productId).setIsShelve(BooleanEnum.TRUE.getValue()).setIsEnable(BooleanEnum.TRUE.getValue()).ignoreSelectFields(EvaProProduct.F_PRODUCT_DESCRIPTION).get();
        if(Objects.nonNull(proProduct)){
            proProduct.addExten("price",Helper.transformF2Y(new BigDecimal(proProduct.getPrice())));
            proProduct.addExten("originalPrice",Helper.transformF2Y(new BigDecimal(proProduct.getOriginalPrice())));
            proProduct.addExten("rebates",Helper.transformF2Y(new BigDecimal(proProduct.getRebates())));
            //计算折扣
            Double oldPrice_d = proProduct.getOriginalPrice().doubleValue();
            Double viewPrice_d = proProduct.getPrice().doubleValue();
            Double discount = (((oldPrice_d - viewPrice_d)/oldPrice_d))*100;
            proProduct.addExten("discount",discount);
            // 购买提示
            if(StringUtils.isNoneBlank(proProduct.getTips())){
                List<SysDictionary> dictionaryList = new ArrayList<SysDictionary>();
                List<String> ids =  JSONObject.parseObject(proProduct.getTips(), List.class);
                if(!ids.isEmpty()) {
                    DictionaryCommond dictionaryCommond = new DictionaryCommond();
                    dictionaryCommond.setIds(ids);
                    dictionaryList = SysDictionary.me().find(dictionaryCommond);
                }
                proProduct.addExten("tips",dictionaryList);
            }
        }
        return proProduct;
    }



    /**
     * 添加订单
     * @param evaOrdOrder
     * @return
     */
    public R addOrder(EvaOrdOrder evaOrdOrder) {


        EvaProProduct proProduct = EvaProProduct.me().setId(evaOrdOrder.getFkProductId()).get();
        String orderId = IDSnowflake.id();

        evaOrdOrder.setId(orderId);
        evaOrdOrder.setProductName(proProduct.getProductName());
        evaOrdOrder.setProductSku(proProduct.getProductSku());
        evaOrdOrder.setOriginalPrice(proProduct.getOriginalPrice());
        evaOrdOrder.setPrice(proProduct.getPrice());
        evaOrdOrder.setProductImageUrl(proProduct.getProductPicImageUrl());
        evaOrdOrder.setAmazonUrl(proProduct.getAmazonUrl());
        evaOrdOrder.setIsEnable(BooleanEnum.TRUE.getValue());
        evaOrdOrder.setStatus(EvaOrdOrderStatusEnum.PENDING.getStatus());
        evaOrdOrder.setRebate(proProduct.getRebates());
        evaOrdOrder.setProductNum(1);

        Map<String,Object> map = new HashMap<>(2);

        // 订单定时过期时间
        if(Objects.nonNull(proProduct.getOrderTiming())){
            long time = Utils.currentTimeSecond() + (proProduct.getOrderTiming() * 60 * 60);
            evaOrdOrder.setExpirationTime(time);
        }
        evaOrdOrder.setOrderNo(IDSnowflake.id());
        Long sn = baseDao.queryForLong("select max(localhost_sn) from eva_ord_order",new HashMap(1));
        evaOrdOrder.setLocalhostSn(sn+1);
        evaOrdOrder.setAddUserId(User.id());
        evaOrdOrder.setFkClienteleId(User.id());
        CliClienteleInfo clientele = CliClienteleInfo.me().setId(User.id()).get();
        map.put("isvAmazonProfileUrl",true);
        map.put("amazonUrl",proProduct.getAmazonUrl());
        if (StrUtil.isNotBlank(clientele.getAmazonProfileUrl())){
            evaOrdOrder.setAmazonStoreUrl(clientele.getAmazonProfileUrl());
            map.put("isvAmazonProfileUrl",false);
        }
        evaOrdOrder.insert();

        // 减商品返现活动次数
        proProduct.setTrialsTimes(proProduct.getTrialsTimes()+1).update();
        map.put("orderId",orderId);
        map.put("time", evaOrdOrder.getExpirationTime() - Utils.currentTimeSecond());
        return R.ok(map);
    }

    /**
     * 申请亚马逊订单信息
     * @param evaOrdOrder
     * @return
     */
    public R doApplyAmazonOrder(EvaOrdOrder evaOrdOrder) {
        // 判断该订单是否超时
        EvaOrdOrder order = EvaOrdOrder.me().setId(evaOrdOrder.getId()).get();
        if(Objects.nonNull(order.getExpirationTime())){
            if(Utils.currentTimeSecond()  >= order.getExpirationTime()){
                order.setStatus(EvaOrdOrderStatusEnum.CLOSED_ORDERS.getStatus()).update();
                return R.error(996,"Order has timed out");
            }
        }
        evaOrdOrder.setStatus(EvaOrdOrderStatusEnum.AMAZON_WAIT.getStatus());
        evaOrdOrder.setApplyUserId(User.id());
        evaOrdOrder.setApplyTime(Utils.currentTimeSecond());
        evaOrdOrder.update();

        CliClienteleInfo cliClienteleInfo  = CliClienteleInfo.me().setId(User.id()).get();
        if (StrUtil.isBlank(cliClienteleInfo.getAmazonProfileUrl())){
            cliClienteleInfo.setAmazonProfileUrl(evaOrdOrder.getAmazonStoreUrl());
            cliClienteleInfo.update();
        }
        return R.ok();
    }

    /**
     *  返现申请
     * @param cashBackApply
     * @return
     */
    public R doApplyCashBack(EvaCashBackApply cashBackApply) {
        EvaCashBackApply evaCashBackApply = EvaCashBackApply.me().setEvaOrderId(cashBackApply.getEvaOrderId()).get();
        if (Objects.isNull(evaCashBackApply)){
            cashBackApply.setStatus(EvaCashBackApplyStatusEnum.REVIEVED.getStatus());
            cashBackApply.setAddUserId(User.id());
            cashBackApply.setFkClienteleId(User.id());
            cashBackApply.insert();


            CliClienteleInfo cliClienteleInfo  = CliClienteleInfo.me().setId(User.id()).get();
            cliClienteleInfo.setPaypalAccount(cashBackApply.getPaypalAccount());
            cliClienteleInfo.update();
        }else {
            evaCashBackApply.setStatus(EvaCashBackApplyStatusEnum.REVIEVED.getStatus());
            evaCashBackApply.update();
        }

        // 更新订单状态
        EvaOrdOrder
                .me().setId(cashBackApply.getEvaOrderId())
                .setStatus(EvaOrdOrderStatusEnum.CASHBACK_WAIT.getStatus())
                .update();


        return R.ok();
    }


    /**
     * 删除订单
     * @param orderId
     * @return
     */
    public R doDeleteOrder(String orderId) {
        EvaOrdOrder ordOrder = EvaOrdOrder.me().setId(orderId).get();
        if (ordOrder.getStatus().equals(EvaOrdOrderStatusEnum.CLOSED_ORDERS.getStatus())
                ||ordOrder.getStatus().equals(EvaOrdOrderStatusEnum.PENDING.getStatus())){
            ordOrder.setIsEnable(Status.NO).update();
            //减商品返现次数
            return R.ok();
        }
        return R.error(996,"Only pending orders before and after closure can be deleted!");
    }

    /**
     * 关闭订单
     * @param orderId
     * @return
     */
    public R doCloseOrder(String orderId) {
        EvaOrdOrder ordOrder = EvaOrdOrder.me().setId(orderId).get();
        if (ordOrder.getStatus().equals(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus())){
            return R.error(996,"Withdrawal passed Unable to close order!");
        }
        ordOrder.setStatus(EvaOrdOrderStatusEnum.CLOSED_ORDERS.getStatus());
        ordOrder.update();
        proProductService.doTrialsTimes(ordOrder.getFkProductId());
        return R.ok();
    }
}
