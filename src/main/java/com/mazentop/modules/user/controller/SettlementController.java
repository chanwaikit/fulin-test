package com.mazentop.modules.user.controller;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.OrdShoppingCart;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.modules.web.dto.SettlementDto;
import com.mazentop.modules.web.service.SettlementService;
import com.mazentop.modules.web.service.TouristService;
import com.mztframework.data.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 结算
 * @author dengy
 */
@Controller
@RequestMapping("/jumpSettlement")
public class SettlementController extends BaseController {

    @Autowired
    SettlementService settlementService;

    @Autowired
    TouristService touristService;

    /**
     * 跳转结算页面
     * @return
     */
    @GetMapping("/{id}")
    public String jumpSettlement(ModelMap modelMap, @PathVariable("id") String id,@RequestParam("step") String step,HttpServletRequest request) {
        SettlementDto dto = settlementService.getSettlementDto(id,request);
        modelMap.put("settlementData", dto);
        modelMap.put("settlementStr",JSON.toJSONString(dto));
        String url = "";
        switch (step){
            case "information":
                url = "/web/user/settlement_preview";
                break;
            case "shipping":
                url = "/web/user/settlement_shipping";
                break;
            case "payment":
                url = "/web/user/settlement_pay";
                break;
            default:
        }
        return url;
    }

    /**修改结算单
     */
    @PostMapping("/updateSettlement")
    @ResponseBody
    public Result updateSettlement(@RequestBody SettlementDto dto,HttpServletRequest request) {
        return Result.build(()->settlementService.doUpdateSettlement(dto,request));
    }

    /**生成结算单
     */
    @PostMapping("/saveSettlement")
    @ResponseBody
    public Result saveSettlement(String cartId, HttpServletRequest request) {
        List<String> carts = new ArrayList<>(1);
        if(StringUtils.isNotBlank(cartId)){
            carts.add(cartId);
        }else{
            List<OrdShoppingCart> cartList;
            if(User.isAuth()) {
                cartList = OrdShoppingCart.me().setFkClienteleId(User.id()).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).find();
            }else{
                try {
                    cartList = touristService.getCartInCookie(request);
                }catch (Exception e){
                    e.printStackTrace();
                    return Result.toast("System abnormality!");
                }
            }
            carts = cartList.stream().map(OrdShoppingCart::getId).collect(Collectors.toList());
        }
        List<String> finalCarts = carts;
        return Result.build(()->settlementService.doSaveSettlement(finalCarts,false));
    }
}
