package com.mazentop.modules.web.interceptor;

import com.mazentop.entity.SysTemplate;
import com.mazentop.modules.web.User;
import com.mazentop.plugins.filter.ThreadContent;
import com.mazentop.plugins.interceptor.AbstractRedirectInterceptor;
import com.mztframework.commons.Utils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * 主题拦截器
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 19:26
 */
public class ThemeInterceptor extends AbstractRedirectInterceptor {

    public static final String DEFAULT_PARAM_NAME = "t";

    public static final String THEME_CUR = "cur_theme";

    private static final String THEME_PC = "/evaluation";

    private static final String THEME_MOBILE = "/evaluation-mobile";

    private static final String THEM_CID ="cId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String template = request.getParameter(DEFAULT_PARAM_NAME);
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        if(DeviceType.COMPUTER.equals(userAgent.getOperatingSystem().getDeviceType())) {
            ThreadContent.addData(THEME_CUR, THEME_PC);
        } else {
            ThreadContent.addData(THEME_CUR, THEME_MOBILE);
        }

        if(!Utils.isBlank(template)) {
            SysTemplate sysTemplate = SysTemplate.me().setTemplateName(template).findFirst();
            if(!Objects.isNull(sysTemplate)) {
                sysTemplate.setTemplatePath(ThreadContent.getData(THEME_CUR));
                WebUtils.setSessionAttribute(request, User.SESSION_CUR_THEME, sysTemplate);
            }
        }

       String cid = request.getParameter(THEM_CID);
       if(!StringUtils.isBlank(cid)) {
           Cookie cookie = new Cookie("U_PID", cid);
           response.addCookie(cookie);
       }
       return true;
    }


}
