package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.SysCompany;
import com.mazentop.entity.SysHelpCenterContent;
import com.mazentop.entity.SysHelpCenterType;
import com.mazentop.modules.emp.commond.SysHelpCenterContentCommond;
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
public class SysHelpCenterContentService {

    @Autowired
    BaseDao baseDao;

    public Page  sysHelpCenterList(SysHelpCenterContentCommond sysHelpCenterContentCommond){
        sysHelpCenterContentCommond.setOrderBy("sort");
        List<SysHelpCenterContent> list = SysHelpCenterContent.me().find(sysHelpCenterContentCommond);
        return new Page(list,sysHelpCenterContentCommond);
    }

    public R doDeleteSysHelpCenterInfo(List<String> ids){
        if(ids.isEmpty()){
            return R.error("id获取失败!");
        }
        ids.forEach(id->{
            SysHelpCenterContent.me().setId(id).delete();
        });
        return R.ok();
    }


    public R doSysHelpCenterInfoAddOrUpdate(SysHelpCenterContent sysHelpCenterContent){
        if(Objects.isNull(sysHelpCenterContent)){
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
        if(StringUtils.isBlank(sysHelpCenterContent.getId())){
            sysHelpCenterContent.setAddTime(Utils.currentTimeSecond());
            sysHelpCenterContent.setAddUserId(empEmployeeInfo.getId());
        }
        SysHelpCenterType sysHelpCenterType = SysHelpCenterType.me().setId(sysHelpCenterContent.getFkHelpCenterTypeId());
        sysHelpCenterContent.setHelpCenterTypeName(sysHelpCenterType.getHelpCenterTypeName());
        sysHelpCenterContent.insertOrUpdate();
        return R.ok();
    }

    public R doSysHelpTypeAddOrUpdate(SysHelpCenterType sysHelpCenterType){
        if(Objects.isNull(sysHelpCenterType)){
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
        if(StringUtils.isBlank(sysHelpCenterType.getId())){
            SysCompany sysCompany = SysCompany.me().setId(Subject.group()).get();
            sysHelpCenterType.setCompanyId(sysCompany.getId());
            sysHelpCenterType.setCompanyName(sysCompany.getName());
        }

        sysHelpCenterType.insertOrUpdate();
        return R.ok();
    }
}
