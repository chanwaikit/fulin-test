package com.mazentop.plugins.filter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.mazentop.entity.CliClienteleLog;
import com.mazentop.entity.ProReportDevice;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ReportDeviceEnum;
import com.mazentop.model.SourceEnum;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.commons.WebUtil;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogCostFilter implements Filter {
    private static final String SESSION_KET = "visit";
    private static final String SESSION_USER_KET = "current_user";
    private FilterConfig filterConfig;
    private FilterChain chain;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.chain = chain;
        this.request = (HttpServletRequest) request;
        this.response = ((HttpServletResponse) response);
        try {
            UserAgent ua = UserAgentUtil.parse(this.request.getHeader("User-Agent"));
            String uri = this.request.getRequestURI();
            if (Helper.isNotEmpty(uri) && !uri.contains("static") && !uri.contains("jquery") && !uri.contains("/option")&& !uri.contains("/getUserInfo")&& !uri.contains("/getUserInfo")) {
                Long count = ProReportDevice.me().setIp(Helper.getIpAddress(this.request)).setAddTime(Utils.currentTimeSecond()).setType(ReportDeviceEnum.POPULAR_ACCESS.type()).findCount();
                if (count == 0) {
                    ProReportDevice proReportDevice = new ProReportDevice();
                    proReportDevice.setType(ReportDeviceEnum.POPULAR_ACCESS.type());
                    proReportDevice.setDevice(ua.getPlatform().toString());
                    proReportDevice.setIp(Helper.getIpAddress(this.request));
                    proReportDevice.setAddTime(Utils.currentTimeSecond());
                    proReportDevice.setSource(SourceEnum.POPULAR_PLATFORM.tile());
                    proReportDevice.insert();
                }
                if (Helper.isNotEmpty(this.request)&&Helper.isNotEmpty(this.request.getSession())&&Helper.isEmpty(this.request.getSession().getAttribute(SESSION_KET)) && Helper.isEmpty(this.request.getSession().getAttribute(SESSION_USER_KET))) {
                    if (Helper.isEmpty(this.request.getSession().getAttribute(SESSION_USER_KET))) {
                        WebUtils.setSessionAttribute(WebUtil.getHttpServletRequest(), SESSION_KET, "session");
                        CliClienteleLog log = new CliClienteleLog();
                        log.setAddTime(Utils.currentTimeSecond());
                        log.setDevice(ua.getPlatform().toString());
                        log.setSessionId(this.request.getSession().getId());
                        log.setStatus(BooleanEnum.TRUE.getValue());
                        log.setType(BooleanEnum.FALSE.getValue());
                        log.setIp(Helper.getIpAddress(this.request));
                        log.insert();
                    }
                }
            }
        }catch (Exception e){
        }finally {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }


}
