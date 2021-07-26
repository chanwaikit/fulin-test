package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.*;
import com.mazentop.modules.emp.dto.OrdFreeSchemeCountryDto;
import com.mazentop.modules.emp.dto.OrdFreeSchemeDetailDto;
import com.mazentop.modules.emp.dto.OrdFreeSchemeDto;
import com.mazentop.util.Helper;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrdFreeSchemeService {

    @Autowired
    BaseDao baseDao;


    public List<Map<String,Object>> findOrdFreeScheme(){
        StringBuilder sql = new StringBuilder(" select * from ord_free_scheme where 1=1 ");
        List<Map<String,Object>>list = baseDao.queryForList(sql.toString(),new HashMap<>());
        if(!list.isEmpty()) {
            list.forEach(map->{
                List<OrdFreeSchemeDetails> detailsList = OrdFreeSchemeDetails.me().setFkOrdFreeSchemeId(map.get("id").toString()).find();
                if(!detailsList.isEmpty()) {
                    detailsList.forEach(details -> {
                        optionDetails(details);

                    });
                }
                map.put("detailsList",detailsList);
                List<OrdFreeSchemeCountry> countryList =  OrdFreeSchemeCountry.me().setFkSchemeId(map.get("id").toString()).find();
                map.put("countryList",countryList);
            });
        }
        return list;
    }
    public void optionDetails(OrdFreeSchemeDetails details) {
        details.addExten("ykgFree",0);
        details.addExten("fixedFreeValue", 0);
        details.addExten("kgFree", 0);
        if (details.getYkgFree() != null) {
            details.addExten("ykgFree", Helper.transformF2Y(details.getYkgFree()));
        }
        if(details.getKgFree() != null){
            details.addExten("kgFree", Helper.transformF2Y(details.getKgFree()));
        }
        if(details.getFixedFreeValue() != null){
            details.addExten("fixedFreeValue", Helper.transformF2Y(details.getFixedFreeValue()));
        }
    }
    public R doOrdFreeSchemeAddOrUpdate(OrdFreeSchemeDto ordFreeSchemeDto){
        if(Objects.isNull(ordFreeSchemeDto)){
           return R.error("数据异常!");
        }
        String curUserId = Subject.id();
        if(StringUtils.isEmpty(curUserId)){
            return R.error("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee =  EmpEmployeeInfo.me().setId(curUserId).get();
        //封装物流方案
        if(StringUtils.isBlank(ordFreeSchemeDto.getId())){
            ordFreeSchemeDto.setAddUserId(curEmployee.getId());
            ordFreeSchemeDto.setAddUserName(curEmployee.getEmployeeName());
            ordFreeSchemeDto.setAddTime(Utils.currentTimeSecond());
        }else{
            ordFreeSchemeDto.setOperationUserId(curEmployee.getId());
            ordFreeSchemeDto.setOperationUserName(curEmployee.getEmployeeName());
            ordFreeSchemeDto.setOperationTime(Utils.currentTimeSecond());
        }
        ordFreeSchemeDto.insertOrUpdate();
        addFreeSchemeDetailInfo(ordFreeSchemeDto,curEmployee);
        addCountry(ordFreeSchemeDto);
        return R.ok();
    }
    public void addFreeSchemeDetailInfo(OrdFreeSchemeDto ordFreeSchemeDto,EmpEmployeeInfo curEmployee){
        List<OrdFreeSchemeDetails> details = OrdFreeSchemeDetails.me().setFkOrdFreeSchemeId(ordFreeSchemeDto.getId()).find();
        if(!ordFreeSchemeDto.getDetailList().isEmpty()){
            if(details.isEmpty()) {
                ordFreeSchemeDto.getDetailList().forEach(detailDto -> {
                    optionFreeschemDetail(detailDto,true,curEmployee);
                    detailDto.setFkOrdFreeSchemeId(ordFreeSchemeDto.getId());
                    detailDto.insert();
                });
            }else {
                List<String>ids = new ArrayList<>();
                details.forEach(id->{
                        ids.add(id.getId());
                });
                ordFreeSchemeDto.getDetailList().forEach(detailDto -> {
                    if(StringUtils.isBlank(detailDto.getId())) {
                        optionFreeschemDetail(detailDto, true, curEmployee);
                        detailDto.setFkOrdFreeSchemeId(ordFreeSchemeDto.getId());
                    }else{
                        optionFreeschemDetail(detailDto, false, curEmployee);
                        if(ids.contains(detailDto.getId())){
                            ids.remove(ids.indexOf(detailDto.getId()));
                        }
                    }
                    detailDto.insertOrUpdate();
                });
                //id集合若不为空则删除
                if(!ids.isEmpty()){
                    ids.forEach(id->{
                        OrdFreeSchemeDetails.me().setId(id).delete();
                    });
                }
            }
        }
    }
    //处理新增修改详情信息
    public void optionFreeschemDetail(OrdFreeSchemeDetailDto detailDto,Boolean flag,EmpEmployeeInfo curEmployee){
        if(!StringUtils.isBlank(detailDto.getYkgFreeStr())){
            detailDto.setYkgFree(Helper.transformY2F(new BigDecimal(detailDto.getYkgFreeStr())));
        }else{
            detailDto.setYkgFree(new Long(0));
        }
        if(!StringUtils.isBlank(detailDto.getKgFreeStr())){
            detailDto.setKgFree(Helper.transformY2F(new BigDecimal(detailDto.getKgFreeStr())));
        }else{
            detailDto.setKgFree(new Long(0));
        }
        if(!StringUtils.isBlank(detailDto.getFixedFreeValueStr())){
            detailDto.setFixedFreeValue(Helper.transformY2F(new BigDecimal(detailDto.getFixedFreeValueStr())));
        }else{
            detailDto.setFixedFreeValue(new Long(0));
        }
        if(false){
            detailDto.setAddUserId(curEmployee.getId());
            detailDto.setAddUserName(curEmployee.getEmployeeName());
            detailDto.setAddTime(Utils.currentTimeSecond());
        }else{
            detailDto.setOperationUserId(curEmployee.getId());
            detailDto.setOperationUserName(curEmployee.getEmployeeName());
            detailDto.setOperationTime(Utils.currentTimeSecond());
        }
    }
    public void addCountry(OrdFreeSchemeDto ordFreeSchemeDto){
        List<OrdFreeSchemeCountryDto> list = ordFreeSchemeDto.getCountryList();
        List<OrdFreeSchemeCountry> countryList = OrdFreeSchemeCountry.me().setFkSchemeId(ordFreeSchemeDto.getId()).find();
        if(!countryList.isEmpty()){
            countryList.forEach(count->{
                OrdFreeSchemeCity.me().setFkSchemeCountId(count.getId()).delete();
            });
            OrdFreeSchemeCountry.me().setFkSchemeId(ordFreeSchemeDto.getId()).delete();
        }
        if (!list.isEmpty()) {
            for (OrdFreeSchemeCountryDto ordFreeSchemeCountry : list) {
               addCountryInfo(ordFreeSchemeDto,ordFreeSchemeCountry);
            }
       }
    }

    private void getAddCity(OrdFreeSchemeDto ordFreeSchemeDto, OrdFreeSchemeCountryDto ordFreeSchemeCountry, List<OrdFreeSchemeCity> ordFreeSchemeCityList) {
        for (OrdFreeSchemeCity city : ordFreeSchemeCityList) {
            city.setFkSchemeCountId(ordFreeSchemeDto.getId());
            city.setFkSchemeCountId(ordFreeSchemeCountry.getId());
            city.insert();
        }
    }

    private void addCountryInfo(OrdFreeSchemeDto ordFreeSchemeDto, OrdFreeSchemeCountryDto ordFreeSchemeCountry) {
        ordFreeSchemeCountry.setFkSchemeId(ordFreeSchemeDto.getId());
        ordFreeSchemeCountry.insert();
        //获取城市集合
        List<OrdFreeSchemeCity> ordFreeSchemeCityList = ordFreeSchemeCountry.getCityList();
        if (!ordFreeSchemeCityList.isEmpty()) {
            getAddCity(ordFreeSchemeDto, ordFreeSchemeCountry, ordFreeSchemeCityList);
        }
    }

    public R deleteFreeScheme(List<String> ids){
        if(ids.isEmpty()){
           return R.error("物流方案信息获取失败！");
        }
        ids.forEach(id->{
          OrdFreeScheme.me().setId(id).delete();
          OrdFreeSchemeDetails.me().setFkOrdFreeSchemeId(id);
          List<OrdFreeSchemeCountry> countryList = OrdFreeSchemeCountry.me().setFkSchemeId(id).find();
          if(!countryList.isEmpty()){
              countryList.forEach(country->{
                      OrdFreeSchemeCity.me().setFkSchemeCountId(country.getId()).delete();
              });
          }
          OrdFreeSchemeCountry.me().setFkSchemeId(id).delete();
        });
        return R.ok();
    }

    public List<Map<String,Object>>findOrdFreeSCity(OrdFreeSchemeCity ordFreeSchemeCity){
        List<Map<String,Object>> mapList = new ArrayList<>();
        SysCountry country = SysCountry.me().setId(ordFreeSchemeCity.getCountryId()).get();
        String sql = "select * from sys_country_province_city where country_sort = :country and (province !='' and city = '' or  province ='' and city !='')";
        Map<String,Object> param = new HashMap<>();
        param.put("country",country.getAlpha3());
        List<Map<String,Object>> cityList = baseDao.queryForList(sql,param);

        if(StringUtils.isBlank(ordFreeSchemeCity.getFkSchemeCountId())){
            if(!cityList.isEmpty()) {
                cityList.forEach(city -> {
                    Map<String,Object> map = new HashMap<>();
                    map.put("isCheck",0);
                    map.put("isProhibit",0);
                    map.put("id","");
                    getCityName(city, map);
                    map.put("fkSchemeCountId","");
                    map.put("countryId",country.getId());
                    mapList.add(map);
                });
            }
        }else{
            if(!cityList.isEmpty()) {
                cityList.forEach(city -> {
                    Map<String,Object> map = new HashMap<>();
                    Long count = OrdFreeSchemeCity.me().setFkSchemeCountId(ordFreeSchemeCity.getFkSchemeCountId()).setFkProvinceCityId(city.get("id").toString()).findCount();
                    map.put("id","");
                    map.put("isCheck",0);
                    map.put("isProhibit",0);
                    if(count > 0){
                        OrdFreeSchemeCity freeSchemeCity = OrdFreeSchemeCity.me().setFkSchemeCountId(ordFreeSchemeCity.getFkSchemeCountId()).setFkProvinceCityId(city.get("id").toString()).get();
                        map.put("id",freeSchemeCity.getId());
                        map.put("isCheck",1);
                    }
                    getCityName(city, map);
                    map.put("countryId",country.getId());
                    mapList.add(map);
                });
            }
        }
        return mapList;
    }

    /**
     * 处理省洲
     * @param city
     * @param map
     */
    private void getCityName(Map<String, Object> city, Map<String, Object> map) {
        SysCountryProvinceCity sysCountryProvinceCity= JSON.parseObject(JSON.toJSONString(city),SysCountryProvinceCity.class);
        if (Helper.isNotEmpty(sysCountryProvinceCity.getProvince())) {
            map.put("cityName", sysCountryProvinceCity.getProvince());
        } else {
            map.put("cityName", "");
            if (Helper.isNotEmpty(sysCountryProvinceCity.getCity())) {
                map.put("cityName", sysCountryProvinceCity.getCity());
            }
        }
        map.put("fkProvinceCityId", sysCountryProvinceCity.getId());
    }
}
