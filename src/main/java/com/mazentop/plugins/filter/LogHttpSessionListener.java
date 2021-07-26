package com.mazentop.plugins.filter;

import com.mazentop.entity.CliClienteleLog;
import com.mazentop.model.BooleanEnum;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class LogHttpSessionListener implements HttpSessionListener {
    private static final String SESSION_KET = "visit";
    private static final String SESSION_USER_KET = "current_user";
    public static int online = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(30*60);
        online++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        updateLog(se);
    }

    private void updateLog(HttpSessionEvent se) {
        CliClienteleLog log = CliClienteleLog.me().setSessionId(se.getSession().getId()).get();
        if (Helper.isNotEmpty(log)){
            log.setStatus(BooleanEnum.FALSE.getValue());
            log.setLeaveTime(Utils.currentTimeSecond());
            log.update();
        }
        if (online>0){
            online--;
        }
    }

}
