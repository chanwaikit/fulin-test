package com.mazentop.modules.user.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.mazentop.entity.*;
import com.mazentop.model.*;
import com.mazentop.modules.emp.dto.CliClientUserDto;
import com.mazentop.modules.emp.service.EmailService;
import com.mazentop.modules.web.User;
import com.mazentop.util.Helper;
import com.mztframework.FileProperties;
import com.mztframework.cache.Options;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import com.mztframework.data.R;
import com.mztframework.email.Email;
import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author: dengy
 * @date: 2020/3/20
 * @description:
 */

@Service
public class UserCenterService {

    @Autowired
    EmailService service;

    @Autowired
    FileProperties fileProperties;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 用户注册
    public R doUserRegistration(CliClientUserDto user) {
        String certification=ClienteleCertificationEnum.AUTHENTICATION.status();
        if (Convert.toBool(Options.get("site_setting_user_oauth"))){
            certification =  ClienteleCertificationEnum.UNAUTHORIZED.status();
        }
        String userId = IDSnowflake.id();
        user.setId(userId);
        user.setAddTime(Utils.currentTimeSecond());
        user.setAddUserId(user.getId());
        user.setLevel(0);
        user.setBuySum(new Long(0));
        user.setBuyTime(0);
        user.setIsEnable(1);
        user.setIsBuyer(0);
        user.setAccountCertification(certification);
        user.setIsMoreBuyer(0);
        user.setIsSubscriber(0);
        user.setBuyGoodsNumber(0);
        user.setLoginName(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsFlag(1);
        // 给用户一个默认地址
        user.setIconImageUrl(fileProperties.getBaseUrl()+"/2021-05-17/5414129601746280475.png");
        // 用户邀请码


        user.setInvitationCode(getCode());
        user.insert();
        // 生成推荐信息
        if(!StringUtils.isBlank(User.pcid())){
            CliClienteleInfo userInfo = CliClienteleInfo.me().setInvitationCode(User.pcid()).get();
            if(Objects.nonNull(userInfo)){
                EvaUserRecommendation recommendation = new EvaUserRecommendation();
                //推荐人id
                recommendation.setReferrerId(userInfo.getId());
                recommendation.setAddUserId(userId);
                //被推荐人id
                recommendation.setUserId(userId);
                // 默认为没有发放佣金
                recommendation.setIsPaid(BooleanEnum.FALSE.getValue());
                recommendation.insert();
            }
        }

        User.auth(user);
/*        CompletableFuture.runAsync(()-> {
            SysOptions options = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get();
            SysOptions sysOptionsEmail = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_EMAIL.key()).get();
            SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setEmailSendMoment(EmailTemplateTypeEnum.TYPE_WELCOME_USER.type()).get();
            String theme = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
            if (Helper.isNotEmpty(sysEmailTemplate.getTheme())){
                theme = sysEmailTemplate.getTheme();
            }
            Email email = Email.create().to(user.getEmail()).subject(theme);
            Map<String,Object> map = new HashMap<>(2);
            map.put("domain",options);
            map.put("contactEmail",sysOptionsEmail);
            R send = service.sendMail(map, email, sysEmailTemplate.getTemplateShowContent());
            SysEmailSendRecord emailSendRecord = new SysEmailSendRecord();
            emailSendRecord.setAddTime(Utils.currentTimeSecond());
            emailSendRecord.setFkEmailTemplateId(sysEmailTemplate.getId());
            emailSendRecord.setEmailTemplateName(sysEmailTemplate.getEmailTemplateName());
            List<String> list = new ArrayList<>();
            list.add(User.id());
            emailSendRecord.setSendPersonList(Helper.toJson((list)));
            emailSendRecord.setSendTime(Utils.currentTimeSecond());
            if (send.getState() == 200) {
                emailSendRecord.setIsSuccess(BooleanEnum.TRUE.getValue());
            }else {
                emailSendRecord.setIsSuccess(BooleanEnum.FALSE.getValue());
            }
            emailSendRecord.insert();
        });*/
        return R.ok();
    }

    public String getCode(){
        String code = RandomUtil.randomString(6);
        Long count = CliClienteleInfo.me().setInvitationCode(code).findCount();
        if (count>0){
            getCode();
        }
        return code.toUpperCase();
    }

    //生成用户验证码信息并发送邮件
    public R getEmailVerificationCode(String emails, String flag) {

        if (!Objects.isNull(WebUtils.getSessionAttribute(WebUtil.getHttpServletRequest(), flag + emails))) {
            Map<String, Object> map = (Map<String, Object>) WebUtils.getSessionAttribute(WebUtil.getHttpServletRequest(), flag + emails);
            Date date = new Date();
            Long resetTime = (Long) map.get("resetTime");
            if (date.getTime() < resetTime) {
                return R.error("验证码已发送，请勿频繁重复获取！");
            }
        }

        Map<String, Object> map = new HashMap<>(3);
        String code = RandomUtil.randomString(4);
        SysOptions options=SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get();
        SysOptions sysOptionsEmail = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_EMAIL.key()).get();
        map.put("code", code);
        map.put("domain",options);
        map.put("contactEmail",sysOptionsEmail);
        SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setEmailSendMoment(EmailTemplateTypeEnum.TYPE_RESET_PWD.type()).get();
        String theme= DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        if (Helper.isNotEmpty(sysEmailTemplate.getTheme())){
            theme= sysEmailTemplate.getTheme();
        }
        Email email = Email.create().to(emails).subject(theme);
        R send = service.sendMail(map, email, sysEmailTemplate.getTemplateShowContent());
        SysEmailSendRecord emailSendRecord = new SysEmailSendRecord();
        emailSendRecord.setAddTime(Utils.currentTimeSecond());
        emailSendRecord.setFkEmailTemplateId(sysEmailTemplate.getId());
        emailSendRecord.setEmailTemplateName(sysEmailTemplate.getEmailTemplateName());
        emailSendRecord.setSendTime(Utils.currentTimeSecond());
        if (send.getState() == 200) {
            Date now = new Date();
            Date afterDate = new Date(now.getTime() + 300000);
            map.put("addTime", afterDate.getTime());
            map.put("resetTime", now.getTime() + 60000);
            WebUtils.setSessionAttribute(WebUtil.getHttpServletRequest(), flag + emails, map);
            emailSendRecord.setIsSuccess(BooleanEnum.TRUE.getValue());
            return R.ok();
        }else {
            emailSendRecord.setIsSuccess(BooleanEnum.FALSE.getValue());
        }
        emailSendRecord.insert();
        return R.error("邮箱发送失败!");
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public R login(CliClientUserDto user, HttpServletRequest request) {
        if (StringUtils.isBlank(user.getLoginName())) {
            return R.error("Login Name Empty!");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return R.error("Please Enter User Password !");
        }
        CliClienteleInfo cliClienteleInfo  = CliClienteleInfo.me().setLoginName(user.getLoginName()).get();
        if (Objects.isNull(cliClienteleInfo)) {
            return R.error("User not registered!");
        }
        if (!verlifyPassword(user.getPassword(), cliClienteleInfo.getPassword())) {
            return R.error("Incorrect username or password!");
        }
        if (cliClienteleInfo.getIsEnable().equals(Status.NO)){
            return R.error("The user has been disabled!");
        }
        // 用户登录信息存缓存
        User.auth(cliClienteleInfo);
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        CliClienteleLog log=new CliClienteleLog();
        log.setAddTime(Utils.currentTimeSecond());
        log.setCliClienteleId(cliClienteleInfo.getId());
        log.setDevice(ua.getPlatform().toString());
        log.setSessionId(request.getSession().getId());
        log.setStatus(BooleanEnum.TRUE.getValue());
        log.setType(BooleanEnum.TRUE.getValue());
        log.setIp(Helper.getIpAddress(request));
        log.insert();
        return R.ok();
    }

    /**
     * 验证密码是否正确
     *
     * @param inputPassword,password
     * @return
     */
    public Boolean verlifyPassword(String inputPassword, String password) {

        boolean matches = passwordEncoder.matches(inputPassword, password);

        return matches;
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    public R restPassword(CliClientUserDto user) {

        CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setEmail(user.getLoginName()).get();

        if (passwordEncoder.matches(user.getPassword(), clienteleInfo.getPassword())) {
            return R.toast("Cannot Use Recently Used Password");
        }
        clienteleInfo.setPassword(passwordEncoder.encode(user.getPassword()));
        clienteleInfo.update();
        return R.ok();
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    public R updatePassword(CliClientUserDto user) {

        CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setLoginName(user.getLoginName()).get();
        if (!passwordEncoder.matches(user.getOldPassword(), clienteleInfo.getPassword())) {
            return R.toast("Old password error");
        }

        if (passwordEncoder.matches(user.getPassword(), clienteleInfo.getPassword())) {
            return R.toast("Cannot Use Recently Used Password");
        }

        clienteleInfo.setPassword(passwordEncoder.encode(user.getPassword()));
        clienteleInfo.update();
        return R.ok();
    }


    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public R updateCliClientUser(CliClienteleInfo user) {
        CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        clienteleInfo.setPaypalAccount(user.getPaypalAccount());
        clienteleInfo.update();
        clienteleInfo.update();
        return R.ok();
    }

    /**
     @param user
      * @return
     */
    public R settingPassword(CliClientUserDto user) {
        CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            return R.toast("The two passwords are inconsistent");
        }
        clienteleInfo.setPassword(passwordEncoder.encode(user.getPassword()));
        clienteleInfo.update();
        return R.ok();
    }

    private void setLog(HttpServletRequest request) {
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        CliClienteleLog log = new CliClienteleLog();
        log.setAddTime(Utils.currentTimeSecond());
        log.setDevice(ua.getPlatform().toString());
        log.setSessionId(request.getSession().getId());
        log.setStatus(BooleanEnum.TRUE.getValue());
        log.setIp(Helper.getIpAddress(request));
        log.insert();

    }
}
