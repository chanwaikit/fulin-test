package com.mazentop.modules.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen quan
 * @date 2020/4/16 16:17
 **/
@Data
public class CountryDTO implements Serializable {

    /**
     * 国家编号
     */
    private String countrySort;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 城市名称
     */
    private String cityName;






}
