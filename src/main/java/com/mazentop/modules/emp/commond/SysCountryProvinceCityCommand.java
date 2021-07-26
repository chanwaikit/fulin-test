package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysCountryProvinceCity;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

/**
 * @author chen quan
 * @date 2020/4/16 16:24
 **/
@Data
public class SysCountryProvinceCityCommand extends BaseCommond {

    @Criteria(expression = Expression.EQ, property = SysCountryProvinceCity.F_COUNTRY_SORT, alias = SysCountryProvinceCity.TABLE_NAME)
    private String countrySort;

    @Criteria(expression = Expression.EQ, property = SysCountryProvinceCity.F_PROVINCE, alias = SysCountryProvinceCity.TABLE_NAME)
    private String province;

    @Criteria(expression = Expression.EQ, property = SysCountryProvinceCity.F_CITY, alias = SysCountryProvinceCity.TABLE_NAME)
    private String city;


    @Criteria(expression = Expression.EQ, property = SysCountryProvinceCity.F_PROVINCE_EN, alias = SysCountryProvinceCity.TABLE_NAME)
    private String provinceEn;

}
