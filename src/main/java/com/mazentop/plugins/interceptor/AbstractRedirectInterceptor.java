package com.mazentop.plugins.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoqt
 * @title: AbstractRedirectInterceptor
 * @description: 代码重构重定向代码提升到父类
 * @date 2019/4/1016:33
 */
public abstract  class AbstractRedirectInterceptor extends HandlerInterceptorAdapter {

    protected void sendRedirect(HttpServletResponse response, String targetUrl, boolean http10Compatible) throws IOException {
        if (http10Compatible) {
            response.sendRedirect(response.encodeRedirectURL(targetUrl));
        } else {
            response.setStatus(303);
            response.setHeader("Location", response.encodeRedirectURL(targetUrl));
        }
    }
}
