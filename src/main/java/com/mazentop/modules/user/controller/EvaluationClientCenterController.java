package com.mazentop.modules.user.controller;

import com.mazentop.entity.*;
import com.mazentop.model.*;
import com.mazentop.modules.user.commond.*;
import com.mazentop.modules.user.service.EvaluationBalanceService;
import com.mazentop.modules.user.service.EvaluationClientCenterService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.web.controller.BaseController;
import com.mazentop.util.Helper;
import com.mztframework.FileProperties;
import com.mztframework.SimpleFile;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.file.FileBuilder;
import com.mztframework.file.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoumei
 * 用户中心Controller
 */
@Authorize
@Controller
@RequestMapping("/evaClient")
public class EvaluationClientCenterController extends BaseController {

    @Autowired
    private EvaluationClientCenterService clientCenterService;

    @Autowired
    private EvaluationBalanceService balanceService;

    @Autowired
    IUploadService uploadService;

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    BaseDao baseDao;

    /**
     * 我的订单分页查询
     *
     * @param modelMap
     */
    @GetMapping("/order")
    public String orderListIndex(ModelMap modelMap) {
        modelMap.put("active", "order");
        return "/web/user/index";
    }


    /**
     * 查询客户基本信息
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
    @GetMapping("/findSettingPassword")
    public String findSettingPassword(ModelMap modelMap) {
        // 获取用户信息
        User user = User.auth();
        // 查询用户基本信息
        CliClienteleInfo clienteleInfo = clientCenterService.queryByClientId(user.getId());
        modelMap.put("clientInfo", clienteleInfo);
        modelMap.put("active", "settingPassword");

        return "/web/user/index";
    }


    /**
     * 修改个人信息以及图片
     */
    @ResponseBody
    @PostMapping("/editSetting")
    public Result editBasicSettings(CliClienteleInfo clienteleInfo){

        CliClienteleInfo queryUser = CliClienteleInfo.me().setId(User.id()).get();
        queryUser
                .setClientSurname(clienteleInfo.getClientSurname())
                .setClientName(clienteleInfo.getClientName())
                .setAmazonProfileUrl(clienteleInfo.getAmazonProfileUrl())
                .setPhone(clienteleInfo.getPhone())
                .setOtherContacts(clienteleInfo.getOtherContacts())
                .setEmail(clienteleInfo.getEmail())
                .setAccountCertification(ClienteleCertificationEnum.PENDING_UNAUTHORIZED.status())
                .setWhatsApp(clienteleInfo.getWhatsApp())
                .setCountry(clienteleInfo.getCountry())
                .setPaypalAccount(clienteleInfo.getPaypalAccount())
                .setAmazonProfileScreenshot(clienteleInfo.getAmazonProfileScreenshot())
                .setIconImageUrl(clienteleInfo.getIconImageUrl())
                ;

        queryUser.update();

        return Result.success("modifySuccess");
    }


    /**
     * 用户推荐好友
     * @param modelMap
     * @return
     */
    @GetMapping("/share")
    public String share(ModelMap modelMap) {
        //推荐链接
        String optionValue = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get().getOptionValue();
        CliClienteleInfo userInfo = CliClienteleInfo.me().setId(User.id()).get();
        modelMap.put("shareLinks", optionValue + "?cId=" + userInfo.getInvitationCode() );

        modelMap.put("active", "share");

        return "/web/user/index";
    }

    /**
     * 提现分页
     * @param commond
     * @return
     */
    @GetMapping("/queryUserRecommendation")
    @ResponseBody
    public Result<Page<EvaUserRecommendation>> queryUserRecommendation(EvaluationUserRecommendationCommond commond) {
        return Result.build(() -> balanceService.queryUserRecommendation(commond));
    }



    /**
     * 我的余额 (佣金余额、返现余额、下级好友数量)
     * @param modelMap
     * @return
     */
    @GetMapping("/balance")
    public String balance(ModelMap modelMap) {
        EvaUserBill userBill = EvaUserBill.me().setFkClienteleId(User.id()).get();
        // 总提现金额、佣金余额
        if(Objects.nonNull(userBill)){
            modelMap.put("totalCashBack",Helper.transformF2Y(new BigDecimal(userBill.getTotalCashBack())));
            modelMap.put("commissionBalance",Helper.transformF2Y(new BigDecimal(userBill.getCommissionBalance())));
        }else {
            modelMap.put("totalCashBack",0);
            modelMap.put("commissionBalance",0);
        }
        // 待提现金额
        String sql = "SELECT sum(rebate) as rebate  from  eva_ord_order WHERE status in (1,2,3,4) AND fk_clientele_id =:userId AND is_enable = 1";
        Map<String, Object> param = new HashMap<>(1);
        param.put("userId",User.id());
        Long totalRebate = baseDao.queryForLong(sql,param);
        modelMap.put("pendingCashBack", Helper.transformF2Y(totalRebate));

        // 推荐好友数量
        modelMap.put("referrerCount",EvaUserRecommendation.me().setReferrerId(User.id()).findCount());

        modelMap.put("active", "balance");

        return "/web/user/index";
    }

    /**
     * 个人中心页面加载方法
     * @param modelMap
     * @return
     */
    @GetMapping("/home")
    public String queryPersonInformation(ModelMap modelMap) {
        balance(modelMap);
        return "/web/user/index";
    }

    /**
     * 取消订单
     */
    @ResponseBody
    @PostMapping("/cancelOrder/{id}")
    public Result cancelOrder(@PathVariable String id) {
        Db.tx(() -> {
            EvaOrdOrder ordOrder = EvaOrdOrder.me().setId(id).get();
            ordOrder.setStatus(8);
            ordOrder.update();
            return true;
        });
        return Result.success("modifySuccess");
    }
}
