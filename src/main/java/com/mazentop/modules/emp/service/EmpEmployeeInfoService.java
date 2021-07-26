package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.modules.emp.commond.EmpEmployeeInfoCommond;
import com.mazentop.modules.emp.dto.EmployeeDto;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import com.mztframework.snowflake.IDSnowflake;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 员工管理业务类
 *
 * @author dengy
 * @version 1.0
 * @date 2020/3/10 14:57
 */

@Service
public class EmpEmployeeInfoService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Page findEmployeeInfoList(EmpEmployeeInfoCommond empEmployeeInfoCommond) {

        empEmployeeInfoCommond.setOrderBy("add_time desc");
        //查询员工信息
        List<EmpEmployeeInfo> list = EmpEmployeeInfo.me().find(empEmployeeInfoCommond);
        if (!list.isEmpty()) {
            //获取处理员工信息的菜单权限
            list.forEach(empEmployeeInfo -> {
                List<EmpAuthorityInfo> authorityInfoList = new ArrayList<>();
                //获取查询到的员工权限
                List<EmpEmployeeAuthorityInfo> empEmployeeAuthorityInfo = EmpEmployeeAuthorityInfo.me().setFkEmployeeId(empEmployeeInfo.getId()).find();
                if (!empEmployeeAuthorityInfo.isEmpty()) {
                    empEmployeeAuthorityInfo.forEach(info -> {
                        EmpAuthorityInfo empAuthorityInfo = EmpAuthorityInfo.me().setId(info.getFkAuthorityId()).get();
                        authorityInfoList.add(empAuthorityInfo);
                    });
                }
                empEmployeeInfo.addExten("authorityInfoList", authorityInfoList);
            });
        }
        return new Page(list, empEmployeeInfoCommond);
    }


    /**
     *  新增或编辑
     * @param employeeDto
     * @return
     */
    public R doEmployeeAddOrUpdate(EmployeeDto employeeDto) {
        EmpEmployeeInfo empEmployeeInfo = employeeDto.getEmployeeInfo();
        List<String> authorityIds = employeeDto.getAuthorityIds();

        // 操作人信息
        String curUserId = Subject.id();
        if(StringUtils.isBlank(curUserId)){
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)){
            R.error("当前用户失效");
        }

        // 补全部门信息
        if (StringUtils.isBlank(empEmployeeInfo.getDepartmentName())) {
            EmpDepartmentInfo empDepartmentInfo = EmpDepartmentInfo.me().setId(empEmployeeInfo.getFkDepartmentId()).get();
            empEmployeeInfo.setDepartmentName(empDepartmentInfo.getDepartmentName());
        }

        if (StringUtils.isBlank(empEmployeeInfo.getId())) {
            if(verifyCurLoginNameUse(empEmployeeInfo.getLoginName())){
                R.error("当前用户名已存在");
            }
            String userId = IDSnowflake.id();
            empEmployeeInfo.setId(userId).setPassword(passwordEncoder.encode(empEmployeeInfo.getPassword())).setAddTime(Utils.currentTimeSecond()).setAddUserId(curUserId).setUpdatePwdTime(Utils.currentTimeSecond()).insert();

            // 创建用户和其权限
            for (String authorityId : authorityIds) {
                EmpEmployeeAuthorityInfo.me().setFkEmployeeId(userId).setFkAuthorityId(authorityId).insert();
            }
        } else {
            // 判断当前密码是否修改 修改 重新加密
            EmpEmployeeInfo employeeInfo = EmpEmployeeInfo.me().setId(empEmployeeInfo.getId()).get();
            if (!employeeInfo.getPassword().equals(empEmployeeInfo.getPassword())) {
                empEmployeeInfo.setPassword(passwordEncoder.encode(empEmployeeInfo.getPassword()));
                empEmployeeInfo.setUpdatePwdTime(Utils.currentTimeSecond());
            }

            empEmployeeInfo.update();
            // 更新权限
            EmpEmployeeAuthorityInfo.me().setFkEmployeeId(empEmployeeInfo.getId()).delete();
            for (String authorityId : authorityIds) {
                EmpEmployeeAuthorityInfo.me().setFkEmployeeId(empEmployeeInfo.getId()).setFkAuthorityId(authorityId).insert();
            }
        }
        return R.ok();
    }



    public R doUpdate(EmpEmployeeInfo employeeInfo) {
        if(StringUtils.isBlank(employeeInfo.getId())){
            R.error("用户id不能为空");
        }
        employeeInfo.update();
        return R.ok();
    }

    /**
     * 删除当前员工
     * @param ids
     * @return
     */
    public R doDeleteEmployee(List<String> ids) {
        for (String id : ids) {
            EmpEmployeeInfo.me().setId(id).delete();
        }
        return R.ok();
    }

    /**
     * 判断当前员工登录账户是否已存在 true存在
     * @param loginName
     * @return
     */
    public Boolean verifyCurLoginNameUse (String loginName) {

        if(EmpEmployeeInfo.me().setLoginName(loginName).findCount()>0){
            return true;
        }
        return false;
    }
}
