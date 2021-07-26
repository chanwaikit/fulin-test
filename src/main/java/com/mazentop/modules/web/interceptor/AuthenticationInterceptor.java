package com.mazentop.modules.web.interceptor;


import com.mazentop.modules.web.User;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.web.annotation.NonAuthorize;
import com.mazentop.plugins.exception.NoLoginException;
import com.mazentop.plugins.interceptor.AbstractRedirectInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 基础权限拦截器
 *
 * @author zhaoqt
 */
public class AuthenticationInterceptor extends AbstractRedirectInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();
        // 若扫描到 方法存在 NonAuthorize 直接放过 不进行校验
        if (!Objects.isNull(method.getAnnotation(NonAuthorize.class))) {
            return true;
        }

        // 优先扫描类层注解是否 包含 Authorize
        Authorize authorize = handlerMethod.getBeanType().getAnnotation(Authorize.class);
        if (null == authorize) {
            // 从方法处理器中获取出要调用的方法
            // 获取出方法上的Authorize注解
            authorize = method.getAnnotation(Authorize.class);

        }
        if (null == authorize) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }

        if (!isLoginHandle()) {
            return onLoginHandle(request, response);
        } else {
            return true;
        }
    }

    /**
     * 验证用户是否登录
     *
     * @return
     */
    private boolean isLoginHandle() {
        return User.isAuth();
    }

    /**
     * 当前用户为登录访问授权保护页面 跳转登录
     *
     * @param response
     */
    private boolean onLoginHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAjaxRequest(request)) {
            // 异步请求访问josn格式 403状态
            throw new NoLoginException();
        }

        String servletPath = request.getServletPath();
        if(Objects.isNull(servletPath)){
            servletPath = "";
        }
        // 如果连接携带分享码，追加删分享码
        String loginUrl = "/login";
        String cId = request.getParameter("cId");
        if(Objects.nonNull(cId)){
            loginUrl += "?cId="+cId;
        }
        sendRedirect(response, loginUrl, true);
        return false;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return (null != request.getHeader("accept")
                && request.getHeader("accept").contains("application/json"))
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }

}
