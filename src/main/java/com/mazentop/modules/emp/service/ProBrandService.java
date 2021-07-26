package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.ProBrand;
import com.mazentop.entity.SysCompany;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.ProBrandCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/12
 * @description:
 */

@Service
public class ProBrandService {

    public List<ProBrand> findProBrands() {
        return ProBrand.me().setIsEnable(Status.YES).setOrderByFields(Order.desc(ProBrand.F_ADD_TIME)).find();
    }

    public Page findProBrandList(ProBrandCommond commond) {
        commond.setOrderBy(" add_time desc");
        List<ProBrand> proBrands = ProBrand.me().find(commond);
        if(!proBrands.isEmpty()){
            proBrands.forEach(brand->{
                EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(brand.getAddUserId()).get();
                if(!Objects.isNull(curEmployee)){
                    brand.addExten("addUserName",curEmployee.getEmployeeName());
                }
            });
        }
        return new Page<>(proBrands,commond);
    }


    /**
     * 新增/修改品牌
     * @param proBrand
     * @return
     */
    public R doProBrandAddOrUpdate(ProBrand proBrand) {

        // 获取当前用户信息
        String curUserId = Subject.id();

        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)){
            R.error("当前用户失效");
        }

        if(StringUtils.isBlank(proBrand.getId())){
            proBrand.setAddTime(Utils.currentTimeSecond()).setAddUserId(curUserId).insert();
        }else {
            proBrand.update();
        }

        return R.ok();
    }



    public R doDeleteProBrand(List<String> ids) {
        Db.tx(() -> {
            for (String id : ids) {
                ProBrand.me().setId(id).delete();
            }
            return true;
        });
        return R.ok();
    }
}
