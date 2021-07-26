package com.mazentop.modules.emp.service;

import com.mazentop.entity.BloBlogType;
import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.SysCompany;
import com.mazentop.modules.emp.commond.BloBlogTypeCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/11
 * @description:
 */

@Service
public class BloBlogTypeService {

    @Autowired
    BaseDao baseDao;

    public Page bloBlogTypeList(BloBlogTypeCommond bloBlogTypeCommond){
        bloBlogTypeCommond.setCompanyId(Subject.group());
        bloBlogTypeCommond.setOrderBy(" add_time desc");
        List<BloBlogType> list = BloBlogType.me().find(bloBlogTypeCommond);
        return new Page(list,bloBlogTypeCommond);
    }

    public Result doBloBlogTypeAddOrUpdate(BloBlogType bloBlogType){
        String curUserId = Subject.id();
        if(StringUtils.isEmpty(curUserId)){
            return Result.toast("登录信息获取失败!");
        }
        if(Objects.isNull(bloBlogType)){
            return Result.toast("数据获取失败!");
        }
        EmpEmployeeInfo curEmployee =  EmpEmployeeInfo.me().setId(curUserId).get();
        if(StringUtils.isEmpty(bloBlogType.getId())){
            bloBlogType.setAddTime(Utils.currentTimeSecond());
            bloBlogType.setAddUserId(curEmployee.getId());
            bloBlogType.setAddUserName(curEmployee.getEmployeeName());
            SysCompany sysCompany = SysCompany.me().setId(Subject.group()).get();
            bloBlogType.setCompanyId(sysCompany.getId());
            bloBlogType.setCompanyName(sysCompany.getName());
        }else{
            bloBlogType.setOperationTime(Utils.currentTimeSecond());
            bloBlogType.setOperationUserId(curEmployee.getId());
            bloBlogType.setOperationUserName(curEmployee.getEmployeeName());
        }
        bloBlogType.insertOrUpdate();
        return Result.success();
    }

    public Result deleteBloBlogType(List<String> ids){
        if (ids.isEmpty()) {
            return Result.toast("请选择博客类型信息!");
        }
        Db.tx(() -> {
            for (String id :ids) {
                BloBlogType.me().setId(id).delete();
            }
            return true;
        });
        return Result.success();
    }
    public Result delete(String id){
        if (id.isEmpty()) {
            return Result.toast("请选择顾客类型信息!");
        }
        Db.tx(() -> {
            BloBlogType.me().setId(id).delete();
            return true;
        });
        return Result.success();
    }
}
