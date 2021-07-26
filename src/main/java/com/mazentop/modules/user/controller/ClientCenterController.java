package com.mazentop.modules.user.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.model.OrderLogisticsStatusEnum;
import com.mazentop.modules.emp.commond.CliClienteleGoodsTrailCommond;
import com.mazentop.modules.user.commond.ActGetCouponRecordCommond;
import com.mazentop.modules.user.commond.OrdPaymentRecordCommond;
import com.mazentop.modules.user.commond.UserOrderCommond;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.web.annotation.NonAuthorize;
import com.mazentop.modules.web.commond.CliCollectorItemCommond;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.modules.web.dto.CountryDTO;
import com.mazentop.modules.user.service.ClientCenterService;
import com.mazentop.modules.user.service.OrderService;
import com.mazentop.util.Helper;
import com.mztframework.FileProperties;
import com.mztframework.SimpleFile;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.data.Result;
import com.mztframework.file.FileBuilder;
import com.mztframework.file.service.IUploadService;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author cq
 * 用户中心Controller
 */
@Authorize
@Controller
@RequestMapping("/client")
public class ClientCenterController extends BaseController {

    @Autowired
    private ClientCenterService clientCenterService;

    @Autowired
    private OrderService orderService;

    @Autowired
    IUploadService uploadService;

    @Autowired
    private FileProperties fileProperties;

    /**
     * 个人中心页面加载方法
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/home")
    public String queryPersonInformation(ModelMap modelMap) {

        // 获取用户信息
        User user = User.auth();
        // 查询用户基本信息
        CliClienteleInfo clienteleInfo = clientCenterService.queryByClientId(user.getId());
        // 查询订单各类数量统计
        Map<String, Integer> countOrderStatusMap = orderService.queryOrderStatusCount(clienteleInfo.getId());
        // 查询最新的3个订单
        Map<String, Object> orderDetailMap = orderService.queryNewestOrderInformation(clienteleInfo.getId());
        // 查询最近的浏览记录
        List<CliClienteleGoodsTrail> goodsTrails = orderService.queryGoodsTrail(clienteleInfo.getId(), 7);
        modelMap.put("clientInfo", clienteleInfo);
        modelMap.put("countOrderStatusMap", countOrderStatusMap);
        modelMap.put("orderNo", orderDetailMap.get("orderNo"));
        modelMap.put("orderDetail", orderDetailMap.get("orderDetail"));
        modelMap.put("goodsTrails", goodsTrails);
        modelMap.put("active", "account");
        return "/web/user/index";
    }

    /**
     * 分页查询浏览记录
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/trails")
    public String trailsIndex(ModelMap modelMap, CliClienteleGoodsTrailCommond commond) {
        trailsListData(modelMap, commond);
        return "/web/user/index";
    }

    @GetMapping("/trails/trailsListData")
    public String trailsListData(ModelMap modelMap, CliClienteleGoodsTrailCommond commond) {
        commond.setPageSize(6);
        commond.setClientId(User.id());
        modelMap.put("goodsTrailList", clientCenterService.queryGoodsTrailsList(commond));
        modelMap.put("commond", commond);
        modelMap.put("active", "goodsTrails");
        return "/web/user/_data/trailsListData";
    }



    /**
     * 查询订单收货地址
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/address")
    public String addressIndex(ModelMap modelMap) {
        // 获取用户信息
        addressListData(modelMap);
        return "/web/user/index";
    }

    @GetMapping("/address/addressListData")
    public String addressListData(ModelMap modelMap) {

        List<CliClienteleReceiverAddress> addresses =
                CliClienteleReceiverAddress.me()
                        .setFkClienteleId(User.id())
                        .setOrderByFields("-" + CliClienteleReceiverAddress.F_ADD_TIME).find();
        List<SysCountry> sysCountries = SysCountry.me().find();

        for (CliClienteleReceiverAddress address : addresses) {
            SysCountry sysCountry = SysCountry.me().setAlpha3(address.getCountry()).get();
            address.addExten("country", sysCountry);
        }
        modelMap.put("clienteleReceiverAddresses", addresses);
        modelMap.put("sysCountries", sysCountries);
        modelMap.put("active", "address");
        return templatePath("/user/_data/addressListData");
    }

    /**
     * 根据ID查询订单信息
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/address/{id}")
    public Result findByIdQueryAddress(@PathVariable("id") String id) {

        CliClienteleReceiverAddress cliClienteleReceiverAddress =
                CliClienteleReceiverAddress.me().setId(id).get();

        List<SysCountry> sysCountries = SysCountry.me().find();

        SysCountry sysCountry = SysCountry.me().setId(cliClienteleReceiverAddress.getCountry()).get();
        cliClienteleReceiverAddress.addExten("country", sysCountry);

        Map<String, Object> map = Maps.newHashMap();
        map.put("address", cliClienteleReceiverAddress);
        map.put("sysCountries", sysCountries);
        map.put("active", "address");

        return Result.build(() -> map);
    }

    /**
     * 添加收货地址(仅应对于个人中心)
     *
     * @param receiverAddress
     * @return
     */
    @ResponseBody
    @PostMapping("/address/edit")
    public Result editReceiptAddress(@RequestBody CliClienteleReceiverAddress receiverAddress) {
        return  Result.build(() -> {
            if(Objects.isNull(receiverAddress.getIsDefault())) {
                receiverAddress.setIsDefault(BooleanEnum.FALSE.getValue());
            }
            return clientCenterService.editReceiptAddress(receiverAddress);
        });
    }

    /**
     * 修改收货地址信息
     *
     * @param receiverAddress
     * @return
     */
    @ResponseBody
    @PostMapping("/address/update")
    public Result editClientOrderReceiveAddress(@RequestBody CliClienteleReceiverAddress receiverAddress) {
        return Result.build(() -> clientCenterService.editReceiptAddress(receiverAddress));
    }


    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete/address/{id}")
    public Result deleteClientOrderReceiveAddress(@PathVariable("id") String id) {
        return Result.build(() -> CliClienteleReceiverAddress.me().setId(id).delete());
    }



    /**
     * 查询地址是否均为非默认地址
     */
    @ResponseBody
    @GetMapping("/address/isDefault")
    public Result addressIsDefault() {
        Long count = CliClienteleReceiverAddress.me().setFkClienteleId(User.id()).setIsDefault(1).findCount();
        if (count > 0) {
            return Result.build(() -> true);
        }
        return Result.build(() -> false);
    }


    /**
     * 查询客户基本信息
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/findSetting")
    public String basicSettings(ModelMap modelMap) {
        // 获取用户信息
        User user = User.auth();
        // 查询用户基本信息
        CliClienteleInfo clienteleInfo = clientCenterService.queryByClientId(user.getId());
        modelMap.put("clientInfo", clienteleInfo);
        modelMap.put("active", "setting");

        return "/web/user/index";
    }


    /**
     * 修改个人信息以及图片
     */
    @ResponseBody
    @PostMapping("/editSetting")
    public Result editBasicSettings(CliClienteleInfo clienteleInfo, HttpServletRequest request) throws IOException {

        CliClienteleInfo queryUser = CliClienteleInfo.me().setId(User.id()).get();
        queryUser.setClientSurname(clienteleInfo.getClientSurname()).setClientName(clienteleInfo.getClientName());

        SimpleFile simpleFile = uploadService.up(request, FileBuilder
                .builder()
                .folder(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .middle(fileProperties.getLocalPath())
                .owner(User.name())
                .build());

        if(!Objects.isNull(simpleFile)) {
            queryUser.setIconImageUrl(simpleFile.getUrl());
        }
        queryUser.update();

        return Result.success("modifySuccess");
    }

    @GetMapping("/system")
    public String querySystemMessage(ModelMap modelMap) {
        User user = User.auth();
        List<SysMessage> sysMessageList = clientCenterService.querySystemMessage(user.getId());

        modelMap.put("sysMessageList", sysMessageList);
        modelMap.put("active", "system");
        return "/web/user/index";

    }

    @GetMapping("/remove/system/{messageId}")
    public void removeSystemMessage(HttpServletResponse response, @PathVariable("messageId") String messageId)
            throws IOException {
        User user = User.auth();
        Assert.isTrue(clientCenterService.systemMessageStatusEdit(messageId, user.getId(), 1, 0)
                , "删除系统信息失败");
        response.sendRedirect("/client/system");
    }

    @ResponseBody
    @GetMapping("/view/system/{messageId}")
    public Result viewSystemMessage(HttpServletResponse response, @PathVariable("messageId") String messageId)
            throws IOException {
        User user = User.auth();
        Assert.isTrue(clientCenterService.systemMessageStatusEdit(messageId, user.getId(), 0, 1)
                , "修改系统信息失败");
        SysMessage sysMessage = SysMessage.me().setId(messageId).get();
        String dataFormat = DateUtil.formatDateTime(DateUtil.date(sysMessage.getAddTime() * 1000));
        sysMessage.addExten("addTime", dataFormat);
        return Result.build(() -> sysMessage);

    }


    @GetMapping("/cart")
    public String queryShopping(ModelMap modelMap) {

        User user = User.auth();
        List<OrdShoppingCart> ordShoppingCarts = clientCenterService.queryShopping(user.getId());
        ordShoppingCarts.forEach(c -> {
            c.addExten("totalAmount", new BigDecimal(c.getTotalProductNumber())
                    .multiply(new BigDecimal(c.getProductPromotionPrice())));
        });
        Long ordShoppingCartCount = OrdShoppingCart.me().setFkClienteleId(user.getId()).findCount();


        modelMap.put("ordShoppingCarts", ordShoppingCarts);
        modelMap.put("ordShoppingCartCount", ordShoppingCartCount);

        return "/web/user/cart";

    }


    /**
     * 对购物车物品进行添加减少
     *
     * @param cartId   物品ID
     * @param type     增加或者减少 1加 0减
     * @param response
     */
    @ResponseBody
    @GetMapping("/calculation/{cartId}/{type}")
    public Result calculationArticleAmount(HttpServletResponse response,
                                           @PathVariable("cartId") String cartId, @PathVariable("type") Integer type)
            throws IOException {
        User user = User.auth();
        OrdShoppingCart ordShoppingCart = OrdShoppingCart.me().setId(cartId).setFkClienteleId(user.getId()).get();
        Assert.notNull(ordShoppingCart, "找不到物品记录");

        Assert.isTrue(type == 1 || type == 0, "操作错误，请检查");
        if (1 == type) {
            ordShoppingCart.setTotalProductNumber(ordShoppingCart.getTotalProductNumber() + 1);
            ordShoppingCart.update();
            return Result.build(() -> ordShoppingCart);
        } else {
            if (ordShoppingCart.getTotalProductNumber() < 2) {
                OrdShoppingCart.me().setId(ordShoppingCart.getId()).delete();
                return Result.build(() -> null);
            } else {
                ordShoppingCart.setTotalProductNumber(ordShoppingCart.getTotalProductNumber() - 1);
                ordShoppingCart.update();
                return Result.build(() -> ordShoppingCart);
            }
        }

    }

    /**
     * 帮助中心-
     *
     * @param modelMap
     * @return
     */
    @NonAuthorize
    @GetMapping("/help")
    public String helpContent(ModelMap modelMap) {
        Map<String, Object> map = clientCenterService.helpContent();
        modelMap.put("sysHelpMap", map);
        return "/web/user/helpcenter";
    }

    /**
     * 帮助中心
     *
     * @param helpId 帮助中心内容ID
     * @return
     */
    @NonAuthorize
    @ResponseBody
    @GetMapping("/help/{helpId}")
    public Result findByIdQueryHelpContentDetail(@PathVariable("helpId") String helpId) {
        return Result.build((() -> SysHelpCenterContent.me().setId(helpId).get()));
    }

    /**
     * 我的订单分页查询
     *
     * @param modelMap
     * @param page
     * @return
     */
    @GetMapping("/order/{page}")
    public String orderListIndex(ModelMap modelMap, @PathVariable("page") Integer page,
                                 UserOrderCommond userOrderCommond) {
        userOrderCommond.setP(page);
        orderListData(modelMap, userOrderCommond);
        return "/web/user/index";
    }

    @GetMapping("/order/orderListData")
    public String orderListData(ModelMap modelMap, UserOrderCommond userOrderCommond) {
        userOrderCommond.setUserId(User.id());
        userOrderCommond.setPageSize(6);
        Map<String, Object> orderMap = clientCenterService.queryOrderPageList(userOrderCommond);
        if (StringUtils.isBlank(userOrderCommond.getOrderStatus())) {
            modelMap.put("orderActive", "all");
        } else if ("01".equals(userOrderCommond.getOrderStatus())) {
            modelMap.put("orderActive", "obligation");
        } else if ("04".equals(userOrderCommond.getOrderStatus())) {
            modelMap.put("orderActive", "waitForReceiving");
        } else if ("06".equals(userOrderCommond.getOrderStatus())) {
            modelMap.put("orderActive", "returnAfter");
        }
        modelMap.put("active", "order");
        modelMap.put("order", orderMap);
        return templatePath("/user/_data/orderListData");
    }


    /**
     * 查询订单详情
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/order/detail/{orderId}")
    public String orderDetail(ModelMap modelMap, @PathVariable("orderId") String orderId) {

        Map<String, Object> orderMap = clientCenterService.queryOrderDetail(orderId);

        modelMap.put("order", orderMap);
        return "/web/user/orderdetail";
    }

    /**
     * 退款申请
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/order/refund")
    public Result addRefundOrder(OrdRefundRequest ordRefundRequest) {

        return clientCenterService.refundOrder(ordRefundRequest, User.id());
    }


    /**
     * 收藏分页查询
     *
     * @param modelMap
     * @param page
     * @return`
     */
    @GetMapping("/favorite/{page}")
    public String favoriteIndex(ModelMap modelMap, @PathVariable("page") Integer page, CliCollectorItemCommond commond) {
        commond.setP(page);
        favoriteListData(modelMap, commond);
        return "/web/user/index";
    }

    /**
     * 收藏列表数据
     * @param modelMap
     * @param commond
     * @return
     */
    @GetMapping("/favorite/favoriteListData")
    public String favoriteListData(ModelMap modelMap, CliCollectorItemCommond commond) {
        commond.setClientId(User.id());
        modelMap.put("favoritePage", clientCenterService.queryMeFavoriteList(commond));
        modelMap.put("commond", commond);
        modelMap.put("active", "favorite");
        return templatePath("/user/_data/favoriteListData");
    }

    /**
     * 删除收藏
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/favorite/del/{id}")
    public Result delFavoriteById(@PathVariable("id") String id) {
        return Result.build(() -> CliCollectorItem.me().setId(id).setFkClienteleId(User.id()).delete());
    }

    /**
     * 删除商品收藏
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/delProClientItem/del/{id}")
    public Result delProClientItem(@PathVariable("id") String id) {
        return Result.build(() -> CliCollectorItem.me().setFkProductId(id).setFkClienteleId(User.id()).delete());
    }

    /**
     * 新增商品收藏
     *
     * @param commodityId
     * @return
     */
    @ResponseBody
    @GetMapping("/favorite/add/{id}")
    public Result addFavoriteById(@PathVariable("id") String commodityId) {
        return Result.build(() -> clientCenterService.addFavorite(User.id(), commodityId));
    }


    /**
     * 优惠卷分页查询
     *
     * @param modelMap
     * @param page
     * @return
     */
    @GetMapping("/coupon/{page}")
    public String couponIndex(ModelMap modelMap, @PathVariable("page") Integer page, ActGetCouponRecordCommond commond) {
        commond.setP(page);
        couponListData(modelMap, commond);
        return "/web/user/index";
    }

    @GetMapping("/coupon/couponListData")
    public String couponListData(ModelMap modelMap, ActGetCouponRecordCommond commond) {
        commond.setPageSize(6);
        commond.setClientId(User.id());
        modelMap.put("couponList", clientCenterService.queryCouponList(commond));
        modelMap.put("commond", commond);
        modelMap.put("active", "coupon");
        return "/web/user/_data/couponListData";
    }


    /**
     * 流水分页查询
     *
     * @param modelMap
     * @param page
     * @return
     */
    @GetMapping("/payment/{page}")
    public String paymentIndex(ModelMap modelMap, @PathVariable("page") Integer page, OrdPaymentRecordCommond commond) {
        commond.setP(page);
        paymentListData(modelMap, commond);
        return "/web/user/index";
    }

    @GetMapping("/payment/paymentListData")
    public String paymentListData(ModelMap modelMap, OrdPaymentRecordCommond commond) {
        commond.setClientId(User.id());
        commond.setPageSize(6);
        commond.setIsPaySuccess(1);
        modelMap.put("paymentRecordList", clientCenterService.queryPaymentRecord(commond));
        modelMap.put("commond", commond);
        modelMap.put("active", "paymentRecord");
        return "/web/user/_data/paymentListData";
    }

    /**
     * 流水详情
     *
     * @param paymentId 流水ID
     * @return
     */
    @ResponseBody
    @GetMapping("/payment/detail/{paymentId}")
    public Result queryPaymentRecordDetail(@PathVariable("paymentId") String paymentId) {
        return clientCenterService.queryPaymentRecordDetail(paymentId);
    }


    /**
     * 查询当前用户信息
     *
     * @return
     */
    @NonAuthorize
    @ResponseBody
    @GetMapping(value = "queryClient")
    public Result queryCurrentClientInfo() {
        if (User.isAuth()) {
            return Result.build(() -> CliClienteleInfo.me().ignoreSelectFields(CliClienteleInfo.F_PASSWORD).setId(User.id()).get());
        }
        return Result.build(() -> null);
    }

    /**
     * 签收物流
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/order/sign/{orderId}")
    public Result orderSign(@PathVariable String orderId) {
        if (User.isAuth()) {
            OrdOrderLogisticsStatus ordOrderLogisticsStatus = new OrdOrderLogisticsStatus();
            OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setId(orderId).get();
            if (ordSalesOrder.getSalesOrderStatus().equals(OrdSalesOrderStatusEnum.DELIVER_COMPLETE.status())) {
                ordOrderLogisticsStatus.setIsSign(1);
                ordOrderLogisticsStatus.setAddUserId(ordSalesOrder.getAddUserId());
                ordOrderLogisticsStatus.setAddUserName(ordSalesOrder.getAddUserName());
                ordOrderLogisticsStatus.setAddTime(Utils.currentTimeSecond());
                ordOrderLogisticsStatus.setFkSalesOrderId(ordSalesOrder.getId());
                ordOrderLogisticsStatus.setSalesOrderStatus("已签收");
                ordOrderLogisticsStatus.setTransportsChannelId(ordSalesOrder.getTransportsChannelId());
                ordOrderLogisticsStatus.setTransportsChannelName(ordSalesOrder.getTransportsChannelName());
                ordOrderLogisticsStatus.setTransportsNo(ordSalesOrder.getTransportsNo());
                ordOrderLogisticsStatus.insert();
                ordSalesOrder.setSalesOrderStatus("05");
                ordSalesOrder.setOperationUserName(User.name());
                ordSalesOrder.setOperationTime(Utils.currentTimeSecond());
                ordSalesOrder.setOperationUserId(User.id());
                ordSalesOrder.update();
                return Result.success();
            }
            return Result.toast("订单状态异常！");

        }
        return Result.toast("未登录");
    }

    @Log("物流列表")
    @ApiOperation("物流列表")
    @ResponseBody
    @GetMapping("/logistics/list/{orderId}")
    public Result orderLogistics(@PathVariable() String orderId) {
        List<OrdOrderLogisticsStatus> list = OrdOrderLogisticsStatus.me().setFkSalesOrderId(orderId).setOrderByFields(Order.asc(ProProductMaster.F_ADD_TIME)).find();
        for (OrdOrderLogisticsStatus o : list) {
            o.addExten("addTime", Helper.timestampToLocalDateTime(o.getAddTime()));
            o.addExten("status", OrderLogisticsStatusEnum.getDesc(o.getSalesOrderStatus()));
        }
        return Result.build(() -> list);
    }


    /**
     * 得到国家地址列表
     *
     * @return
     */
    @ResponseBody
    @NonAuthorize
    @GetMapping("/countryList")
    public Result findByCountryQuery(@RequestParam(value = "name", required = false) String name) {
        return Result.build(() -> clientCenterService.findByCountryQuery(name));
    }

    @ResponseBody
    @NonAuthorize
    @GetMapping("/countryList/{alpha3}")
    public Result findByCountryDTOQueryCountryList(@PathVariable String alpha3) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountrySort(alpha3);
        return clientCenterService.findByCountryDTOQueryCountryList(countryDTO);
    }


    /**
     * 根据国家DTO 查询国家下的省份以及城市
     *
     * @param countryDTO 国家信息DTO
     * @return Result
     */
    @ResponseBody
    @NonAuthorize
    @PostMapping("/country/dto")
    public Result findByCountryDTOQueryCountryList(@RequestBody CountryDTO countryDTO) {
        return clientCenterService.findByCountryDTOQueryCountryList(countryDTO);
    }

    @ResponseBody
    @NonAuthorize
    @GetMapping("/coupon/{id}")
    public Result findByIdQueryCouponDetails(@PathVariable(value = "id") String id) {
        return clientCenterService.findByIdQueryCouponDetails(id);
    }

    @NonAuthorize
    @ResponseBody
    @GetMapping("/dynamicLoadingCode")
    public Result dynamicLoadingThirdPartyCode() {
        return clientCenterService.dynamicLoadingThirdPartyCode();
    }

    /**
     * 查询订单详情
     *
     * @param modelMap
     * @return
     */
    @NonAuthorize
    @GetMapping("/redirect/order/detail/{key}")
    public String redirectOrderDetail(ModelMap modelMap, @PathVariable("key") String key, HttpServletRequest request) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        String json = null;
        try {
            json = new String(base64Decoder.decodeBuffer(key), "UTF-8");
            Map<String, String> map = Helper.json2SimpleMap(json);
            String userId = map.get("userId");
            String orderId = map.get("orderId");
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
            Map<String, Object> orderMap = clientCenterService.queryOrderDetail(orderId);
            modelMap.put("order", orderMap);
            return "/web/user/orderdetail";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转到评价页面
     * @param modelMap
     * @param itemId - 訂單項id
     * @return
     */
    @Authorize
    @GetMapping("/getComment/{itemId}/{orderTime}")
    public String getSettlementOrder(ModelMap modelMap, @PathVariable("itemId") String itemId,@PathVariable("orderTime") String orderTime) {
        OrdSalesOrderDetail ordOrderItem = OrdSalesOrderDetail.me().setId(itemId).get();
        ordOrderItem.addExten("orderTime",orderTime);
        ordOrderItem.addExten("price", Helper.transformF2Y(ordOrderItem.getProductMallPrice()));
        modelMap.put("orderItem", ordOrderItem);
        return "/web/user/afterComments";
    }

}
