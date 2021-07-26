package com.mazentop.modules.emp.dto;

import com.mazentop.entity.OrdFreeSchemeDetails;
import lombok.Data;

@Data
public class OrdFreeSchemeDetailDto extends OrdFreeSchemeDetails {

    String ykgFreeStr;

    String kgFreeStr;

    String fixedFreeValueStr;
}
