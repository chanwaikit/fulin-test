package com.mazentop.modules.user.controller;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.model.ClienteleCertificationEnum;
import com.mazentop.modules.emp.dto.CliClientUserDto;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.user.service.UserCenterService;
import com.mztframework.commons.WebUtil;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author: dengy
 * @date: 2020/3/20
 * @description:
 */
@Controller
@RequestMapping("/user")
public class UserCenterController {

    @Autowired
    UserCenterService userService;

    /**用户注册
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/doUserRegistration")
    public R doUserRegistration(@RequestBody CliClientUserDto user) {
        if(StringUtils.isBlank(user.getCode())){
            return R.toast("Email Acquisition Failed!");
        }
        String code = user.getCode();
        Long count = CliClienteleInfo.me().setEmail(user.getEmail()).findCount();
        if(count>0){
            return R.toast("Email Registered!");
        }

        Map<String,Object> map = (Map<String, Object>) WebUtils.getSessionAttribute(WebUtil.getHttpServletRequest(), user.getEmail());
        if(Objects.isNull(map)){
            return R.toast("Email Acquisition Failed!");
        }
        Date date = new Date();
        Long addtime = (Long) map.get("addTime");
        if(date.getTime()>addtime){
            return R.toast("Verification Code Expired!");
        }
        if(!code.equals(map.get("code").toString())){
            return R.toast("Verification Code Error!");
        }
        return userService.doUserRegistration(user);
    }

    /**获取验证码
     * @param email
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/getSysHelpCenterType/{email}")
    public Result getSysHelpCenterType(@PathVariable String email) {
        return Result.build(() -> userService.getEmailVerificationCode(email,""));
    }

    /**用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody CliClientUserDto user,HttpServletRequest request) {
        return userService.login(user,request);
    }

    /**
     * 获取验证码 (校验当前账户是否已注册)
     *
     * @param loginName 要修改密码的账户
     * @return
     */
    @GetMapping(value = "/findCodeReg/{loginName}")
    @ResponseBody
    public R findCodeReg(@PathVariable String loginName) {
        Long count = CliClienteleInfo.me().setLoginName(loginName).findCount();
        if(count<=0){
            return R.toast("请输入正确账户信息");
        }
        return  userService.getEmailVerificationCode(loginName,"rest");
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Authorize
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws IOException {
        User.logout();
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    /**
     * 登录成功跳转个人中心
     *
     * @return
     */
    @Authorize
    @GetMapping("/JumpHome")
    public String JumpHome(HttpServletRequest request, ModelMap modelMap) throws IOException {
        if(!Objects.isNull(WebUtils.getSessionAttribute(WebUtil.getHttpServletRequest(), User.SESSION_KET))){
            return User.redirect();
        }
        return "redirect:/login";
    }


    /**重置密码校验
     * @param email
     * @param code
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/checkCode")
    public R checkCode(String email,String code) {
        if(Objects.isNull(email)){
            return R.toast("邮箱信息获取失败!");
        }
        if(StringUtils.isBlank(code)){
            return R.toast("验证码信息获取失败!");
        }
        Long count = CliClienteleInfo.me().setEmail(email).findCount();
        if(count<0){
            return R.toast("用户信息不存在!");
        }
        Map<String,Object> map = (Map<String, Object>) WebUtils.getSessionAttribute(WebUtil.getHttpServletRequest(), "rest"+email);
        if(Objects.isNull(map)){
            return R.toast("验证码信息获取失败!");
        }
        Date date = new Date();
        Long addtime = (Long) map.get("addTime");
        if(date.getTime()>addtime){
            return R.toast("验证码已过期!");
        }
        if(!code.equals(map.get("code").toString())){
            return R.toast("验证码不一致!");
        }
        return R.ok();
    }

    /**密码重置
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/restPassword")
    public R restPassword(@RequestBody CliClientUserDto user) {
        if(Objects.isNull(user)){
            return R.toast("parameter is empty");
        }
        if(StringUtils.isBlank(user.getCode())){
            return R.toast("The verification code is empty");
        }
        Map<String,Object> map = (Map<String, Object>) WebUtils.getSessionAttribute(WebUtil.getHttpServletRequest(), "rest"+user.getLoginName());
        if(Objects.isNull(map)){
            return R.toast("parameter is empty");
        }
        Date date = new Date();
        Long addtime = (Long) map.get("addTime");
        if(date.getTime()>addtime){
            return R.toast("The verification code has expired");
        }
        if(!user.getCode().equals(map.get("code").toString())){
            return R.toast("The verification code is inconsistent");
        }

        return userService.restPassword(user);
    }

    @ResponseBody
    @PostMapping(value = "/updatePassword")
    public R updatePassword(@RequestBody CliClientUserDto user){
        if(Objects.isNull(user)){
            return R.toast("FailedToObtainUserInformation");
        }
        return userService.updatePassword(user);
    }



    @ResponseBody
    @PostMapping(value = "/updateCliClientUser")
    public R updateCliClientUser(@RequestBody CliClienteleInfo user){
        if(StrUtil.isBlank(user.getLoginName())){
            return R.toast("Failed To Obtain User Information");
        }
        return userService.updateCliClientUser(user);
    }

    @ResponseBody
    @PostMapping(value = "/updateCertification")
    public Result updateCertification(CliClienteleInfo user){
        Db.tx(() -> {
            CliClienteleInfo.me()
                    .setId(User.id())
                    .setAmazonProfileUrl(user.getAmazonProfileUrl())
                    .setAmazonProfileScreenshot(user.getAmazonProfileScreenshot())
                    .setAccountCertification(ClienteleCertificationEnum.PENDING_UNAUTHORIZED.status())
                    .update();
            return true;
        });
        return Result.success();
    }

    @ResponseBody
    @PostMapping(value = "/settingPassword")
    public R settingPassword(CliClientUserDto user){
        if(Objects.isNull(user)){
            return R.toast("Failed to get user information");
        }
        return userService.settingPassword(user);
    }

//    /**
//     * 滑块验证码获取
//     * @return 返回参数：tari(目标图)，orii(原图)，slii(滑块)，coox(滑块x坐标)，cooy(滑块y坐标)，chet(滑块校验token)
//     */
//    @GetMapping("/sliding/verification")
//    @ResponseBody
//    public Result slidingVerificationImage(){
//        return Result.build(() -> userService.slidingVerificationImage());
//    }

//    /**
//     * 验证码校验
//     * @param chet 滑块校验令牌token
//     * @param coox 滑块滑动后的x坐标
//     * @param cooy 滑块滑动后的y坐标
//     * @return
//     */
//    @PostMapping("/sliding/check")
//    @ResponseBody
//    public Result slidingCheck(@RequestParam("chet") String chet, @RequestParam("coox") int coox, @RequestParam("cooy") int cooy){
////        if(Helper.isEmpty(chet) || Helper.isEmpty(secretKey)){
////            return Result.toast("请求错误，请检查请求参数！");
////        }
////        return userService.slidingCheck(chet, secretKey, coox, cooy);
//    }


}
