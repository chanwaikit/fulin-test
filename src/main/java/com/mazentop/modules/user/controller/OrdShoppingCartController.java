package com.mazentop.modules.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.user.commond.ProProductSecInfoCommond;
import com.mazentop.modules.user.dto.SettlementConfirmDto;
import com.mazentop.modules.web.service.TouristService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.user.commond.OrdShoppingCartCommond;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.web.constants.*;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.modules.user.dto.OrdBalanceTheBooksDto;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.service.SettlementService;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.util.Helper;
import com.mztframework.commons.Lists;
import com.mztframework.commons.Maps;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.data.R;
import com.mztframework.data.Result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * 购物车
 * @author dengy
 */
@Controller
@RequestMapping("/shoppingCart")
public class OrdShoppingCartController extends BaseController {

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;

    @Autowired
    SettlementService settlementService;

    @Autowired
    BaseDao baseDao;

    @Autowired
    TouristService touristService;
    /**
     * 查询购物车信息
     */
    @GetMapping("")
    public String shoppingCart(ModelMap map, HttpServletRequest request) {
        ordShoppingCartData(map, request);
        return "/web/user/cart";
    }

    @GetMapping("/shoppingCartData")
    public String ordShoppingCartData(ModelMap map, HttpServletRequest request) {
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        //获取购物车商品信息
        List<OrdShoppingCart> cartList;
        if(User.isAuth()){
            cartList = getOrdShoppingCarts();
        }else{
            try {
                cartList = touristService.getCartInCookie(request);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        ordShoppingCartService.addExchangeId(cartList, exchangeId);
        ordShoppingCartService.optProductPrice(cartList);
        optionColorOrSize(cartList);
        map.put("cartList", cartList);
        map.put("cartCount", cartList.size());
        return "/web/user/_cart/shoppingCartData";
    }

    private List<OrdShoppingCart> getOrdShoppingCarts() {
        return OrdShoppingCart.me().setFkClienteleId(User.id()).setIsGenOrder(0).setIsGenBalanceAccounts(0).setIsDel(0).find();
    }

    /**
     * 处理购物车参数信息
     */
    private void optionColorOrSize(List<OrdShoppingCart> cartList) {
        if (!cartList.isEmpty()) {
            cartList.forEach(cart -> {
                cart.addExten("colorOrSize", 0);
                if (!Helper.isEmpty(cart.getProductSubSku())) {
                    Long count = ProProductStock.me().setProductSubSku(cart.getProductSubSku()).setIsEnable(BooleanEnum.TRUE.getValue()).findCount();
                    if (count > 0) {
                        cart.addExten("colorOrSize", 1);
                        cart.addExten("sec", cart.getProductSpec());
                    }
                }
            });
        }
    }

    /**
     * 查询购物车信息
     */
    @GetMapping("shoppingCartDataList")
    @ResponseBody
    public Result ordShoppingCartDataList(HttpServletRequest request) {
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        Map<String, Object> map = new HashMap<>(1);
        return Result.build(() -> {
            //获取购物车商品信息
            List<OrdShoppingCart> cartList = getOrdShoppingCarts();
            ordShoppingCartService.addExchangeId(cartList, exchangeId);
            ordShoppingCartService.optProductPrice(cartList);
            optionColorOrSize(cartList);
            map.put("cartList", cartList);
            map.put("cartCount", cartList.size());
            return map;
        });

    }

    /**
     * 添加商品至购物车
     */
    @GetMapping("/addOrdShopping")
    @ResponseBody
    public R addOrdShopping(OrdShoppingCartCommond commond,HttpServletRequest request,HttpServletResponse response) {
        if (StringUtils.isBlank(commond.getId())) {
            return R.error("商品信息id为空!");
        }
        if (commond.getProNum() == null) {
            return R.error("加入数量不能为空!");
        }
        if (commond.getProNum() < 0) {
            return R.error("加入数量不能小于0!");
        }
        if(User.isAuth()) {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
            if (Objects.isNull(cliClienteleInfo)) {
                return R.error("用户信息获取失败!");
            }
            return ordShoppingCartService.doAddOrdShopping(commond.getId(), commond.getProNum(), cliClienteleInfo, commond.getProductSubSku());
        }
        try {
            return touristService.doNotIsAuthAddOrdShopping(commond.getId(),commond.getProNum(), commond.getProductSubSku(),request,response);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("系统异常!");
        }
    }

    /**
     * 修改购物车商品数量
     */
    @ResponseBody
    @GetMapping("/updateOrdShopping/{id}/{flag}")
    public Result updateOrdShopping(@PathVariable("id") String id, @PathVariable("flag") int flag,HttpServletRequest request,HttpServletResponse response) {
        if (StringUtils.isBlank(id)) {
            R.error("商品信息id为空!");
        }
        Long count = OrdShoppingCart.me().setId(id).findCount();
        if (count <= 0) {
            R.error("商品信息获取失败!");
        }
        if(User.isAuth()) {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
            if (Objects.isNull(cliClienteleInfo)) {
                R.error("用户信息获取失败!");
            }
            return ordShoppingCartService.doUpdateOrdShopping(id, flag, cliClienteleInfo, request);
        }else {
            try {
                return touristService.isAuthUpdateOrdShopping(id, flag, request, response);
            }catch (Exception e){
                return Result.toast("System abnormality!");
            }
        }
    }


    /**
     * 删除商品
     *
     */
    @GetMapping("/delOrdShopping")
    @ResponseBody
    public Result delOrdShopping(@RequestParam("goods") String[] goods,HttpServletRequest request,HttpServletResponse response) {

        if (goods.length < 1) {
            return Result.toast("商品信息id为空!");
        }
        if(User.isAuth()) {
            //判断是否登录
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
            if (Objects.isNull(cliClienteleInfo)) {
                R.toast("用户信息获取失败!");
            }
            return ordShoppingCartService.doDelOrdShopping(Lists.newArrayList(goods));
        }
        try {
            return ordShoppingCartService.isAuthDelOrdShopping(Lists.newArrayList(goods),request,response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Result.toast("System abnormality!");
    }

    @PostMapping("/updateShoppingCartSelected/{id}/{selected}")
    @ResponseBody
    public Result updateShoppingCartSelected(@PathVariable("id") String id, @PathVariable("selected") Integer selected) {
        Db.tx(() -> {
            if(User.isAuth()) {
                OrdShoppingCart shoppingCart = new OrdShoppingCart();
                shoppingCart.setSelected(selected);
                if (!"all".equals(id)) {
                    shoppingCart.setFkClienteleId(User.id());
                    shoppingCart.setId(id);
                    shoppingCart.update();
                } else {
                    shoppingCart.batchUpdate(OrdShoppingCart.me().setFkClienteleId(User.id()));
                }
            }
            return true;
        });
        return Result.success();
    }

    /**
     * 单商品跳结算
     */
    @GetMapping("/jumpSettlementData")
    @ResponseBody
    public Result<Map<String, Object>> jumpSettlementData(OrdShoppingCartCommond commond, HttpServletResponse response,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(1);
        ProProductMaster productMaster = ProProductMaster.me().setId(commond.getId()).get();
        if (Objects.isNull(productMaster)) {
            return Result.toast("商品数据获取失败！");
        }
        // 2020-10-30 去掉，商品主表库存字段去掉
        ProProductStock stock = ProProductStock.me().setProductSubSku(commond.getProductSubSku()).setFkProductId(commond.getId()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
        if (stock.getProductStockNumber() < commond.getProNum()) {
            return Result.toast("库存数量不足！");
        }
        return Result.build(() -> {
            OrdShoppingCart ordShoppingCart;
            if(User.isAuth()) {
                CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
                ordShoppingCart = ordShoppingCartService.doAddOrdShoppingCart(productMaster, commond.getProNum(), cliClienteleInfo, commond.getProductSubSku());
            }else{
                ordShoppingCart = touristService.doAddIsNotAuthOrdShoppingCart(productMaster,commond.getProNum(),commond.getProductSubSku(),response,request);
            }
            List<String> ids = new ArrayList<>(1);
            ids.add(ordShoppingCart.getId());
            String settlementId =  settlementService.doSaveSettlement(ids,false);
            map.put("settlementId", settlementId);
            return map;
        });
    }

    /**
     * 购物车跳结算
     */
    @GetMapping("/jumpSettlement")
    public String jumpSettlement() {
        return "/web/user/settlement_preview";
    }



    @GetMapping("/recallJumpSettlement/{key}")
    public String recallJumpSettlement(@PathVariable("key") String param, HttpServletRequest request) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        String url = "";
        try {
            String json = new String(base64Decoder.decodeBuffer(param), StandardCharsets.UTF_8);
            JSONObject jsonObject = JSON.parseObject(json);
            Map<String, Object> params = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, Object>>() {
            });
            String userId = params.get("userId").toString();
            String booksId = params.get("booksId").toString();
            if (User.isAuth()) {
                if (!User.id().equals(userId)) {
                    User.logout();
                    HttpSession session = request.getSession();
                    session.invalidate();
                }
            }
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(userId).get();
            if (Objects.isNull(cliClienteleInfo)) {
                return "redirect:/login";
            }
            User.auth(cliClienteleInfo);
            OrdBalanceTheBooks ordBalanceTheBooks = OrdBalanceTheBooks.me().setId(booksId).get();
            if (Objects.isNull(ordBalanceTheBooks)) {
                return "/web/payment/complete";
            }
            if (ordBalanceTheBooks.getIsRecall() == 1) {
                return "/web/payment/complete";
            }
            return "redirect:/shoppingCart/jumpSettlement?ordShoppingCartList=" + ordBalanceTheBooks.getShoppingCartList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 收货人信息
     */
    @GetMapping("/getAddressData")
    @ResponseBody
    public Result getAddressData() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("addressList", null);
        //判断用户是否登录状态
        if (User.isAuth()) {
            //获取最新添加非默认地址记录
            List<CliClienteleReceiverAddress> addresses = CliClienteleReceiverAddress.me().setFkClienteleId(User.id())
                    .setOrderByFields(" is_default desc,add_time desc").setLimit(0).setLimitEnd(3).find();
            if (!addresses.isEmpty()) {
                addresses.forEach(address -> {
                    SysCountry sysCountry = SysCountry.me().setAlpha3(address.getCountry()).get();
                    address.addExten("country", sysCountry);
                });
            }
            map.put("addressList", addresses);
        }
        return Result.build(() -> map);
    }

    /**
     * 生成结算单
     */
    @PostMapping("/saveOrdBalanceTheBooks")
    @ResponseBody
    public Result saveOrdBalanceTheBooks(HttpServletRequest request, @RequestBody OrdBalanceTheBooksDto ordBalanceTheBooksDto, HttpServletResponse response) {
        return Result.build(() -> ordShoppingCartService.doSaveOrdBalanceTheBooks(ordBalanceTheBooksDto, request, response));
    }

    /**
     * 跳转到结算页面
     */
    @GetMapping("/getOrdBalanceTheBooks/{id}")
    public String orderList(ModelMap modelMap, @PathVariable("id") String id) {
        try {
            ordShoppingCartService.getOrdBalanceTheBooks(modelMap, id);
            return "/web/user/settlement_pay";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户优惠卷信息
     */
    @GetMapping("/getCouponRecord")
    @ResponseBody
    public Result getCouponRecord(HttpServletRequest request) {
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        Map<String, Object> map = new HashMap<>(1);
        return Result.build(() -> {
            map.put("couponRecordList", new ArrayList<>());
            //判断用户是否登录状态
            if (User.isAuth()) {
                CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
                List<Map<String, Object>> list = new ArrayList<>();
                if (!Objects.isNull(cliClienteleInfo)) {
                    list = ordShoppingCartService.getCouponRecord(cliClienteleInfo, exchangeId);
                }
                map.put("couponRecordList", list);
            }
            return map;
        });
    }

    /**
     * 添加商品至购物车
     */
    @PostMapping("/addToOrdShopping")
    @ResponseBody
    public R addToOrdShopping(@RequestBody OrdShoppingCartCommond commond) {
        if (StringUtils.isBlank(commond.getId())) {
            return R.error("商品信息id为空!");
        }
        if (commond.getProNum() == null || commond.getProNum() < 0) {
            return R.error("加入数量不能为空并且不能小于0!");
        }
        //多变体校验
        if (commond.getIsSingle() == 0) {
            List<ProProductStock> stockList = ProProductStock.me().setFkProductId(commond.getId()).setIsEnable(BooleanEnum.TRUE.getValue()).find();
            if (!stockList.isEmpty()) {
                ProProductSecInfoCommond proProductSecInfoCommond = new ProProductSecInfoCommond();
                proProductSecInfoCommond.setIds(commond.getSpecList());
                stockList.forEach(stock -> {
                    proProductSecInfoCommond.setStockId(stock.getId());
                    Long count = ProProductSecInfo.me().findCount(proProductSecInfoCommond);
                    if (count == commond.getSpecList().size()) {
                        commond.setProductSubSku(stock.getProductSubSku());
                    }
                });
                if (StringUtils.isBlank(commond.getProductSubSku())) {
                    return R.error("规格匹配失败!");
                }
            }
        }
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        if (Objects.isNull(cliClienteleInfo)) {
            return R.error("用户信息获取失败!");
        }
        return ordShoppingCartService.doAddOrdShopping(commond.getId(), commond.getProNum(), cliClienteleInfo, commond.getProductSubSku());
    }

    /**生成结算单
     */
    @PostMapping("/saveSettlement")
    @ResponseBody
    public Result saveOrdBalanceTheBooks(@RequestBody SettlementConfirmDto settlementConfirmDto) {
        OrdSettlementConfirm settlementConfirm = new OrdSettlementConfirm();
        Db.tx(() -> {
            settlementConfirm.setContent(JSON.toJSONString(settlementConfirmDto));
            settlementConfirm.insert();

            return true;
        });
        return Result.build(settlementConfirm::getId);
    }

    /**
     * 跳转到结算页面
     * @param modelMap
     * @param id
     * @return
     */
    @Authorize
    @GetMapping("/getSettlement/{id}")
    public String getSettlement(ModelMap modelMap, @PathVariable("id") String id,HttpServletRequest request) {
        ordShoppingCartService.getSettlement(modelMap,id,request);
        return "/web/user/settlement_pay";
    }


    @Authorize
    @GetMapping("/getCouponPrice/{code}")
    @ResponseBody
    public Result getCouponPrice(@PathVariable("code") String code){
        if(StringUtils.isBlank(code)){
            return Result.toast("Discount volume code cannot be empty！");
        }
        Long count = ActGetCouponRecord.me().setFkClienteleId(User.id()).setCouponCode(code).findCount();
        if(count < 1){
            return Result.toast("The coupon does not exist !");
        }
        String sql = "select count(*) from act_coupon_activity where activity_status ='02' and end_time > :time";
        Map<String,Object> param = new HashMap<>(1);
        param.put("time", Utils.currentTimeSecond());
        Long actCount = baseDao.queryForLong(sql,param);
        if(actCount < 1){
            return Result.toast(" Coupon expired ! ");
        }
        if(!ordShoppingCartService.checkCoupon(code)){
            return Result.toast(" This coupon is not applicable to the current product ! ");
        }
        return Result.build(() -> ordShoppingCartService.getCouponPrices(code));
    }
}
