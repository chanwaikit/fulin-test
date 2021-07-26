package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductType;
import com.mazentop.entity.ProSeo;
import lombok.Data;

@Data
public class ProductTypeDto extends ProProductType {

    private ProSeo seo;
}
