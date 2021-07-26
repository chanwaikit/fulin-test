package com.mazentop.modules.authentication.service;

import com.mazentop.entity.EmpAuthorityInfo;
import com.mazentop.entity.EmpEmployeeAuthorityInfo;
import com.mazentop.entity.EmpEmployeeInfo;
import com.mztframework.jwt.security.JwtUser;
import com.mztframework.model.Status;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wangzy
 * @date: 2019/8/30
 * @description:
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 实现UserDetailsService 接口，主要是在loadUserByUsername方法中验证一个用户
     * 这里需要从数据库中读取验证表单提交过来的用户
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public JwtUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        boolean enabled = false;

        EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setLoginName(userName).get();
        if(Objects.isNull(empEmployeeInfo)) {
            return null;
        }

        if (Status.YES.equals(empEmployeeInfo.getIsEnable())){
            enabled = true;
        }

        ArrayList<String> permissions = new ArrayList<>();
        List<EmpEmployeeAuthorityInfo> empEmployeeAuthorityInfos = EmpEmployeeAuthorityInfo.me().setFkEmployeeId(empEmployeeInfo.getId()).find();
        for (EmpEmployeeAuthorityInfo empEmployeeAuthorityInfo : empEmployeeAuthorityInfos) {
            EmpAuthorityInfo empAuthorityInfo = EmpAuthorityInfo.me().setId(empEmployeeAuthorityInfo.getFkAuthorityId()).get();
            permissions.add(empAuthorityInfo.getCode());
        }

        Timestamp scurrtest = new Timestamp(empEmployeeInfo.getAddTime()*1000);
        Date date = new Date(empEmployeeInfo.getUpdatePwdTime()*1000);
        return  new JwtUser(
                empEmployeeInfo.getId(),
                empEmployeeInfo.getLoginName(),
                empEmployeeInfo.getPassword(),
                "",
                empEmployeeInfo.getEmployeeName(),
                null,
                null,
                null,
                "zh",
                "",
                permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                null,
                enabled,
                scurrtest,
                date
        );
    }
}
