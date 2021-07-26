package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.SysCompany;
import com.mazentop.entity.SysMessage;
import com.mazentop.modules.emp.commond.SysMessageCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class SysMessageService {

    @Autowired
    BaseDao babseDao;


    public Page sysMessageList(SysMessageCommond sysMessageCommond){
        sysMessageCommond.setCompanyId(Subject.group());
        List<SysMessage> sysMessageList = SysMessage.me().find(sysMessageCommond);
        return new Page(sysMessageList,sysMessageCommond);
    }
    public R doSysMessageAddOrUpdate(SysMessage sysMessage){
        if(Objects.isNull(sysMessage)){
            return R.error("数据获取失败!");
        }
        String curUserId = Subject.id();
        if(StringUtils.isBlank(curUserId)){
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(empEmployeeInfo)){
            R.error("当前用户失效");
        }
        if(StringUtils.isBlank(sysMessage.getId())){
            sysMessage.setAddTime(Utils.currentTimeSecond());
            sysMessage.setAddUserId(empEmployeeInfo.getId());
            sysMessage.setAddUserName(empEmployeeInfo.getEmployeeName());
            SysCompany sysCompany = SysCompany.me().setId(Subject.group()).get();
            sysMessage.setCompanyId(sysCompany.getId());
            sysMessage.setCompanyName(sysCompany.getName());
        }else{
            sysMessage.setOperationTime(Utils.currentTimeSecond());
            sysMessage.setOperationUserId(empEmployeeInfo.getId());
            sysMessage.setOperationUserName(empEmployeeInfo.getEmployeeName());
        }
        sysMessage.insertOrUpdate();
        return R.ok();
    }

    public R deleteSysMessageInfo(List<String> ids){
        if(ids.isEmpty()){
            return R.error("id获取失败!");
        }
        ids.forEach(id->{
            SysMessage.me().setId(id).delete();
        });
        return R.ok();
    }
}
