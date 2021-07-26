package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.SysAdvertisement;
import com.mazentop.entity.SysCompany;
import com.mazentop.modules.emp.commond.SysAdvertisementCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: wangzy
 * @date: 2020/3/19
 * @description:
 */
@Service
public class SysAdvertisementService {

    public Page<SysAdvertisement> findSysAdvertisementList(SysAdvertisementCommond advertisementCommond) {

        List<SysAdvertisement> sysAdvertisements = SysAdvertisement.me().find(advertisementCommond);

        return new Page<>(sysAdvertisements,advertisementCommond);
    }

    public R doSysAdvertisementAddOrUpdate(SysAdvertisement sysAdvertisement) {

        String curUserId = Subject.id();
        if(StringUtils.isBlank(curUserId)){
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)){
            R.error("当前用户失效");
        }

        if(StringUtils.isBlank(sysAdvertisement.getId())){

            sysAdvertisement
                    .setAddTime(Utils.currentTimeSecond())
                    .setAddUserId(curUserId);

        }
        sysAdvertisement
                    .insertOrUpdate();

        return R.ok();
    }
}
