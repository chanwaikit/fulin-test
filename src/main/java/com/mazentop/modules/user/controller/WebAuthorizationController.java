package com.mazentop.modules.user.controller;


import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.modules.user.service.WebAuthorizationService;
import com.mztframework.cache.Options;
import com.mztframework.data.Result;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthFacebookScope;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthFacebookRequest;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import me.zhyd.oauth.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.Objects;


@Controller
@RequestMapping("/webAuthorization")
public class WebAuthorizationController {

    @Autowired
    WebAuthorizationService authorizationService;

    @PostMapping("/render/{source}")
    @ResponseBody
    public Result renderAuth(@PathVariable String source) {
        String url = "/webAuthorization/message";
        if(!Boolean.parseBoolean(Options.get("site_is_senbox"))){
            AuthRequest authRequest = getAuthRequest(source);
            if (Objects.isNull(authRequest)) {
                return Result.toast(" Configuration error ");
            }
            url = authRequest.authorize(AuthStateUtils.createState());
        }
        String finalUrl = url;
        return Result.build(() -> finalUrl);
    }

    @RequestMapping("/callback/{source}")
    public String login(AuthCallback callback, @PathVariable String source,  ModelMap map) {
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        AuthUser user = (AuthUser) response.getData();
        if(!Objects.isNull(user)){
            if(StringUtils.isNotEmpty(user.getEmail())) {
                CliClienteleInfo userInfo = authorizationService.doAddAuthorize(user, source);
                map.put("redirect", "/evaClient/findSettingPassword");
                if (StringUtils.isNotEmpty(userInfo.getPassword())) {
                    map.put("redirect", "/evaClient/order");
                }
            }else{
                map.put("error", "Authorization fails Email is null found");
            }
        } else {
            map.put("error", "Authorization fails");
        }
        return "/web/authorization/callback";
    }

    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "Facebook":
                if (StringUtils.isNotEmpty(Options.get("facebook_auth_client_id")) && StringUtils.isNotEmpty(Options.get("facebook_auth_client_secret"))) {
                    authRequest = new AuthFacebookRequest(AuthConfig.builder()
                            .clientId(Options.get("facebook_auth_client_id"))
                            .clientSecret(Options.get("facebook_auth_client_secret"))
                            .redirectUri(Options.get("site_auth_domain_name") + "/webAuthorization/callback/Facebook")
                            .scopes(AuthScopeUtils.getScopes(AuthFacebookScope.EMAIL))
                            // 针对国外平台配置代理
                            .httpConfig(facebookGoogleProxy())
                            .build());
                }
                break;
            case "Google":
                if (StringUtils.isNotEmpty(Options.get("google_auth_client_id")) && StringUtils.isNotEmpty(Options.get("google_auth_client_secret"))) {
                    authRequest = new AuthGoogleRequest(AuthConfig.builder()
                            .clientId(Options.get("google_auth_client_id"))
                            .clientSecret(Options.get("google_auth_client_secret"))
                            .redirectUri(Options.get("site_auth_domain_name") + "/webAuthorization/callback/Google")
                            // 针对国外平台配置代理
                            .httpConfig(facebookGoogleProxy())
                            .build());
                }
                break;

        }
        return authRequest;
    }

    public HttpConfig facebookGoogleProxy() {
        if(Boolean.parseBoolean(Options.get("site_auth_sendbox"))) {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("kHttpUkZmxrJcYWf", "2glihupiDpJNbDZl".toCharArray());
                }
            });
            return HttpConfig.builder()
                    // Http 请求超时时间
                    .timeout(15000)
                    // host 和 port 请修改为开发环境的参数
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", Integer.parseInt("4781"))))
                    .build();
        }
        return HttpConfig.builder()
                .timeout(15000)
                .build();
    }

    @RequestMapping("/message")
    public String message() {
        return "/web/authorization/message";
    }
}
