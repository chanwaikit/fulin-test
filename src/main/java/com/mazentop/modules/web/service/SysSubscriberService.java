package com.mazentop.modules.web.service;

import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.SysSubscriber;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SysSubscriberService {

    @Autowired
    BaseDao baseDao;


    public R addSysSubscriber(CliClienteleInfo cliClienteleInfo, HttpServletRequest request){
        //获取@Excel信息
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        SysSubscriber sysSubscriber = new SysSubscriber();
        sysSubscriber.setEmail(cliClienteleInfo.getEmail());
        sysSubscriber.setIp(ip);
        sysSubscriber.setAddTime(Utils.currentTimeSecond());
        sysSubscriber.setAddUserId(cliClienteleInfo.getId());
        sysSubscriber.insert();
        return R.ok();
    }

    public R cancelSysSubscriber(String id){
        SysSubscriber.me().setId(id).delete();
        return R.ok();
    }

}
