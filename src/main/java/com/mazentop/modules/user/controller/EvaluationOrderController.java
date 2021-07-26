package com.mazentop.modules.user.controller;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.user.commond.EvaluationUserOrderCommond;
import com.mazentop.modules.user.service.EvaluationOrderService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.controller.BaseController;
import com.mztframework.commons.Maps;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: zhoumei
 * @date: 2020/5/11
 * @description:
 */
@Controller
@RequestMapping("/evaOrder")
public class EvaluationOrderController extends BaseController {

    @Autowired
    EvaluationOrderService orderService;


    /**
     * 订单分页
     * @param commond
     * @return
     */
    @GetMapping("/orderListData")
    @ResponseBody
    public Result<Page<EvaOrdOrder>> orderListData(EvaluationUserOrderCommond commond) {
        commond.setFkClienteleId(User.id());
        return Result.build(() -> orderService.queryOrderPageList(commond));
    }


    /**
     * 获取分享佣金
     * @return
     */
    @GetMapping("/commissionRulesInfo")
    @ResponseBody
    public Result commissionRulesInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("site_commission_rules", SysOptions.me().setOptionKey("site_commission_rules").get());
        return Result.build(() -> data);
    }


    /**
     * 获取订单状态金额
     * @return
     */
    @GetMapping("/getOrderStatus")
    @ResponseBody
    public Result getOrderStatus() {
        return Result.build(() -> orderService.getOrderStatus());
    }


    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @ResponseBody
    @GetMapping("/deleteOrder/{orderId}")
    public Result deleteOrder(@PathVariable("orderId") String orderId) {
        return Result.build(() -> orderService.doDeleteOrder(orderId));
    }


    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    @ResponseBody
    @GetMapping("/getOrderDetails/{orderId}")
    public Result getOrderDetails(@PathVariable("orderId") String orderId) {
        return Result.build(() -> orderService.getOrderDetails(orderId));
    }

    /**
     * 获取产品详情
     * @param productId
     * @return
     */
    @ResponseBody
    @GetMapping("/getProductDetails/{productId}")
    public Result getProductDetails(@PathVariable("productId") String productId) {
        return Result.build(() -> orderService.getProductDetails(productId));
    }


    /**
     * 验证是否已添加该商品订单
     * @param productId
     * @return
     */
    @ResponseBody
    @GetMapping("/checkOrder/{productId}")
    public Result checkOrder(@PathVariable("productId") String productId) {
        return Result.build(() -> orderService.checkOrder(productId));
    }


    /**
     * 添加订单
     * @param evaOrdOrder
     * @return
     */
    @PostMapping("/addOrder")
    @ResponseBody
    public Result addOrder(EvaOrdOrder evaOrdOrder) {
        R r = orderService.checkOrder(evaOrdOrder.getFkProductId());
        if (r.getState()== HttpStatus.OK.value()){
            return Result.build(()->orderService.addOrder(evaOrdOrder));
        }
        return Result.toast(r.getMessage());
    }


    /**
     *  返现申请
     * @param cashBackApply
     * @return
     */
    @PostMapping("/applyCashBack")
    @ResponseBody
    public Result applyCashBack(EvaCashBackApply cashBackApply) {
        return Result.build(() -> orderService.doApplyCashBack(cashBackApply));
    }


    /**
     *  订单-亚马逊信息提交审核
     * @param evaOrdOrder
     * @return
     */
    @PostMapping("/applyAmazonOrder")
    @ResponseBody
    public Result applyAmazonOrder(EvaOrdOrder evaOrdOrder) {
        return Result.build(() -> orderService.doApplyAmazonOrder(evaOrdOrder));
    }

    /**
     * 关闭订单
     * @param id
     * @return
     */
    @GetMapping("/close/{id}")
    @ResponseBody
    public Result closeOrder(@PathVariable String id) {
        return Result.build(() -> orderService.doCloseOrder(id));
    }




}
