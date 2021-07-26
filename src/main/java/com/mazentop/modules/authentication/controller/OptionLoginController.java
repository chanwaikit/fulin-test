package com.mazentop.modules.authentication.controller;

import com.mazentop.entity.SysLoginLog;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.data.Result;
import com.mztframework.jwt.component.ILoginOrLogoutHandler;
import com.mztframework.jwt.component.JwtTokenGenerator;
import com.mztframework.jwt.security.AuthenticationInfo;
import com.mztframework.jwt.security.AuthorizationUser;
import com.mztframework.jwt.security.JwtUser;
import com.mztframework.jwt.security.Subject;
import com.mztframework.logging.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/option")
public class OptionLoginController {

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ILoginOrLogoutHandler loginOrLogoutHandler;

    /**
     * 登录授权gu
     * @param authorizationUser
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "/login")
    public Result login(@Validated @RequestBody AuthorizationUser authorizationUser,HttpServletRequest request) {
        SysLoginLog sysLoginLog=new SysLoginLog();
        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        if (Objects.isNull(jwtUser) || !bCryptPasswordEncoder.matches(authorizationUser.getPassword(), jwtUser.getPassword())) {
            sysLoginLog.setIsSuccess(0);
            sysLoginLog.setRemark("用户名密码错误");
            return Result.toast("用户名密码错误");
        }
        if (!jwtUser.isEnabled()) {
            sysLoginLog.setIsSuccess(0);
            sysLoginLog.setRemark("账号已停用");
            return Result.toast("账号已停用，请联系管理员");
        }
        sysLoginLog.setIsSuccess(1);
        sysLoginLog.setLoginName(authorizationUser.getUsername());
        sysLoginLog.setLoginTime(Utils.currentTimeSecond());
        sysLoginLog.setLoginIp(Helper.getIpAddress(request));
        sysLoginLog.insert();
        // 生成令牌
        final String token = jwtTokenGenerator.generateToken(jwtUser);
        // 返回 token
        return Result.build(() -> new AuthenticationInfo(token, jwtUser));
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo() {
        return Result.build(() -> (JwtUser) userDetailsService.loadUserByUsername(Subject.username()));
    }

    /**
     * 获取用户菜单权限
     *
     * @return
     */
    @GetMapping(value = "/getUserAuthority")
    public Result getUserAuthority() {
        return Result.build(() -> {
            JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(Subject.username());
            return jwtUser.getAuthorities();
        });
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        CompletableFuture.runAsync(() -> loginOrLogoutHandler.logoutAfter(request, Subject.getUserDetails()));
        return Result.success();
    }
}
