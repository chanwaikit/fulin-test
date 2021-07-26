package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpDepartmentInfo;
import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.modules.emp.commond.EmpDepartmentInfoCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门管理业务类
 * com.mztframework.StarterApplication
 *
 * @author dengy
 * @version 1.0
 * @date 2020/3/10 14:57
 */

@Service
public class EmpDepartmentInfoService {

    @Autowired
    BaseDao baseDao;


    public Page findDepartmentList(EmpDepartmentInfoCommond empDepartmentInfoCommond) {
        empDepartmentInfoCommond.setOrderBy(" add_time desc");
        //查询部门列表
        List<EmpDepartmentInfo> list = EmpDepartmentInfo.me().find(empDepartmentInfoCommond);
        list.forEach(info -> {
            info.addExten("count", EmpEmployeeInfo.me().setFkDepartmentId(info.getId()).findCount());
        });
        return new Page(list, empDepartmentInfoCommond);
    }

    public Result doDepartmentAddOrUpdate(EmpDepartmentInfo empDepartmentInfo) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return Result.toast("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (StringUtils.isEmpty(empDepartmentInfo.getId())) {
            empDepartmentInfo.setAddTime(Utils.currentTimeSecond());
            empDepartmentInfo.setAddUserId(curEmployee.getId());
        }
        empDepartmentInfo.insertOrUpdate();
        return Result.success();
    }

    public Result doDeleteDepartment(String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.toast("编号获取失败!");
        }
        Long count = EmpEmployeeInfo.me().setFkDepartmentId(id).findCount();
        if (count > 0) {
            return Result.toast("该部门下存在员工!");
        }
        EmpDepartmentInfo.me().setId(id).delete();
        return Result.success();
    }

    public Result doDeleteDepartments(List<String> ids) {
        if (ids.isEmpty()) {
            return Result.toast("请选择部门");
        }
        for (String id : ids) {
            Long count = EmpEmployeeInfo.me().setFkDepartmentId(id).findCount();
            if (count > 0) {
                continue;
            }
            EmpDepartmentInfo.me().setId(id).delete();
        }
        return Result.success();
    }

}
