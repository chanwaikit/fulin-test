package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductMaster;
import lombok.Data;

import java.util.List;

@Data
public class ProductMasterIsShelveDto extends ProProductMaster {

    List<String> ids;

}
