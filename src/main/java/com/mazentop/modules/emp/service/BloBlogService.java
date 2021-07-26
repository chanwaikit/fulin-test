package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.emp.commond.BloBlogCommond;
import com.mazentop.modules.emp.dto.BloBlogDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
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
public class BloBlogService {
    @Autowired
    ProSeoService proSeoService;

    public Page findBloBlogList(BloBlogCommond bloBlogCommond) {

        bloBlogCommond.setCompanyId(Subject.group());
        bloBlogCommond.setOrderBy(" add_time desc");
        List<BloBlog> bloBlogs = BloBlog.me().find(bloBlogCommond);
        for (BloBlog bloBlog : bloBlogs) {
            BloBlogType bloBlogType = BloBlogType.me().setId(bloBlog.getId()).get();
            bloBlog.addExten("blogType", bloBlogType);
        }
        return new Page<>(bloBlogs, bloBlogCommond);
    }


    public R doBloBlogAddOrUpdate(BloBlogDto bloBlog) {

        String curUserId = Subject.id();
        if (StringUtils.isBlank(curUserId)) {
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(empEmployeeInfo)) {
            R.error("当前用户失效");
        }

        if (StringUtils.isBlank(bloBlog.getId())) {
            BloBlogType bloBlogType = BloBlogType.me().setId(bloBlog.getFkTypeId()).get();

            Db.tx(() -> {
                // 新增博客数据
                bloBlog.setTypeName(bloBlogType.getBlogTypeName())
                        .setAddTime(Utils.currentTimeSecond())
                        .setAddUserId(curUserId)
                        .setAddUserName(empEmployeeInfo.getEmployeeName())
                        .setOperationTime(Utils.currentTimeSecond())
                        .setOperationUserId(curUserId)
                        .setOperationUserName(empEmployeeInfo.getEmployeeName())
                        .setCompanyId(Subject.group())
                        .setCompanyName(SysCompany.me().setId(Subject.group()).get().getName())
                        .insert();


                // 更新博客分类记录
                bloBlogType.setContentNumber(Math.toIntExact(BloBlog.me().setFkTypeId(bloBlogType.getId()).findCount()))
                        .update();

                return true;
            });

        } else {

            BloBlogType bloBlogType = BloBlogType.me().setId(bloBlog.getFkTypeId()).get();

            // 修改博客数据
            bloBlog.setTypeName(bloBlogType.getBlogTypeName())
                    .setOperationTime(Utils.currentTimeSecond())
                    .setOperationUserId(curUserId)
                    .setOperationUserName(empEmployeeInfo.getEmployeeName())
                    .update();

        }
        return R.ok();
    }

    public R doDeleteBloBlog(List<String> ids) {
        Db.tx(() -> {
            for (String id : ids) {
                BloBlog.me().setId(id).delete();
            }
            return true;
        });
        return R.ok();
    }
}
