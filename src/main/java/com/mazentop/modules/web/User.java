package com.mazentop.modules.web;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.SysTemplate;
import com.mazentop.plugins.session.SessionUtil;
import com.mztframework.commons.Maps;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;

/**
 * 用户信息
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/7/30 15:55
 */
@Data
public class User implements Serializable {

    public static final String SESSION_KET = "current_user";
    public static final String SESSION_CUR_THEME = "current_theme";

    private String id = "";

    private String account = "";

    private String nickname = "";

    private String email = "";

    private String phone = "";

    /**
     * 用户邀请码
     */
    private String sn = "";

    private String avatar;

    private Long integral;

    private String role = "common";

    private Map<String, Object> extra;

    public User() {
        extra = Maps.newHashMap();
    }

    public static String id() {
        return auth().getId();
    }

    public static String name() {
        return CliClienteleInfo.me().setId(auth().getId()).get().getLoginName();
    }

    @SuppressWarnings("unchecked")
    public <T> T extra(String key) {
        return (T) extra.get(key);
    }

    /**
     * 配合 #isAuth 使用登录成功 跳转个人中心
     *
     * @return
     */
    public static String redirect() {
        User user = auth();
        if(RoleType.COMMON.isMe(user.getRole())) {
            return "redirect:/evaClient/home";
        }
        return "";
    }
    public static String isLogin(){
        if(!isAuth()){
            return "redirect:/login";
        }
        return "";
    }

    public User extra(String key, Object value) {
        extra.put(key, value);
        return this;
    }

    public User extra(Map<String, Object> extra) {
        this.extra.putAll(extra);
        return this;
    }

    public static User auth(CliClienteleInfo customer) {
        User user = new User();
        user.setId(customer.getId());
        user.setPhone(customer.getPhone());
        if(StrUtil.isBlank(customer.getClientName())) {
            customer.setClientName("");
        }
        if(StrUtil.isBlank(customer.getClientSurname())) {
            customer.setClientSurname("");
        }
        user.setNickname(customer.getClientSurname() + "" + customer.getClientName());
        if(StrUtil.isBlank(user.getNickname())) {
            user.setNickname(customer.getEmail());
        }
        user.setEmail(customer.getEmail());
        user.setAccount(customer.getEmail());
        user.setAvatar(customer.getIconImageUrl());
        user.setSn(customer.getInvitationCode());
        if(Utils.isBlank(customer.getIconImageUrl())) {
            user.setAvatar(WebUtil.getHttpServletRequest().getContextPath() + "/static/user/images/avatar.png");
        }
        // 存储用户登录信息到当前session
        SessionUtil.setSessionAttribute(SESSION_KET, user);
        //删除临时访问session
        SessionUtil.setSessionAttribute("visit", null);
        return user;
    }

    public static SysTemplate theme() {
        return SessionUtil.getSessionAttribute(User.SESSION_CUR_THEME);
    }

    public static User auth() {
        return SessionUtil.getSessionAttribute(User.SESSION_KET);
    }

    public static boolean isAuth() {
        return !Objects.isNull(auth());
    }

    public static void logout() {
        SessionUtil.setSessionAttribute(SESSION_KET, null);
    }

    public static String cid() {
        return User.auth().getSn();
    }

    public static String pcid() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String pcId = "";
        Cookie cookie = WebUtils.getCookie(request,"U_PID");
        if(!Objects.isNull(cookie)) {
            try {
                pcId = URLDecoder.decode(cookie.getValue(), "utf-8");
            }catch (Exception e){
                return "";
            }
        }
        return pcId;
    }
}
