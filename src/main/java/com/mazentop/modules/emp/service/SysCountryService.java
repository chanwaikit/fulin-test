package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.OrdFreeSchemeCity;
import com.mazentop.entity.OrdFreeSchemeCountry;
import com.mazentop.entity.SysCountry;
import com.mazentop.model.CountryEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.SysCountryCommond;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author: wangzy
 * @date: 2020/3/19
 * @description:
 */
@Service
public class SysCountryService {

    @Autowired
    BaseDao baseDao;

    public List<SysCountry> findCountryList(SysCountryCommond commond) {
        commond.setPage(Status.NO);
        return SysCountry.me().find(commond);
    }


    public Page findCountryListPage(SysCountryCommond commond) {
        return new Page(SysCountry.me().find(commond),commond);
    }


    public R doCountryAddOrUpdate(SysCountry country) {

        String curUserId = Subject.id();
        if(StringUtils.isBlank(curUserId)){
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(empEmployeeInfo)){
            R.error("当前用户失效");
        }

        if(StringUtils.isNotBlank(country.getId())){

            country.update();

        }

        return R.ok();
    }


    public List<Map<String,Object>> allCountryData(String schemeId){
        List<Map<String,Object>> data = new ArrayList<>();
        String[] name = {"亚洲","北美洲","南美洲","南极洲","大洋洲","欧洲","非洲"};
        String[] nameCn = {"AS","NA","SA","AN","OA","EU","AF"};
        for (int i = 0; i < 7; i++) {
            Map<String, Object> country = new HashMap<>();
            country.put("id", IDSnowflake.id());
            country.put("name",name[i]);
            country.put("nameCn",nameCn[i]);
            List<Map<String,Object>>children = optionChildrenData(name[i],schemeId);
            country.put("children",children);
            country.put("isChildren",true);
            data.add(country);
        }
        return data;
    }

    private List<Map<String,Object>> optionChildrenData(String id,String schemeId){
        List<SysCountry> sysCountryList = SysCountry.me().setContinentId(CountryEnum.getId(id)).find();
        List<Map<String,Object>> mapList = new ArrayList<>();
        if(!sysCountryList.isEmpty()) {
            sysCountryList.forEach(country -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", country.getId());
                map.put("alpha3", country.getAlpha3());
                map.put("name", country.getName());
                map.put("nameCn", country.getNameCn());
                map.put("flag", country.getFlag());
                map.put("disabled", false);
                map.put("isCheck", 0);
                if (StringUtils.isBlank(schemeId)) {
                    Long count = OrdFreeSchemeCountry.me().setCountryId(country.getId()).findCount();
                    if (count > 0) {
                        optionProhibit(country, map);
                    }
                } else {
                    Long count = OrdFreeSchemeCountry.me().setCountryId(country.getId()).setFkSchemeId(schemeId).findCount();
                    if (count > 0) {
                        map.put("isCheck", 1);
                    } else {
                        //获取省/洲列表
                        optionProhibit(country, map);
                    }
                }
                mapList.add(map);
            });
        }
        return mapList;
    }

    private void optionProhibit(SysCountry country, Map<String, Object> map) {
        //获取省/洲列表
        String sql = "select count(*) from sys_country_province_city where country_sort = :country and (province !='' and city = '' or  province ='' and city !='')";
        Map<String, Object> param = new HashMap<>();
        param.put("country", country.getAlpha3());
        Long cityList = baseDao.queryForLong(sql, param);
        Long cityCount = OrdFreeSchemeCity.me().setCountryId(country.getId()).findCount();
        //若列表的值大于存在方案的值则不禁用国家
        if (cityList <= cityCount) {
            map.put("disabled", true);
        }
    }
}
