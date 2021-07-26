package com.mazentop.modules.emp.dto;

import com.mazentop.entity.OrdFreeScheme;
import com.mazentop.entity.OrdFreeSchemeCity;
import com.mazentop.entity.OrdFreeSchemeCountry;
import lombok.Data;

import java.util.List;

@Data
public class OrdFreeSchemeDto extends OrdFreeScheme {

    List<OrdFreeSchemeCountryDto> countryList;

    List<OrdFreeSchemeDetailDto> detailList;

}
