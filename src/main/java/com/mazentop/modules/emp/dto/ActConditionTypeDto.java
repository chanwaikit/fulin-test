package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ActConditionType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class ActConditionTypeDto extends ActConditionType {

    private String typeConditionStr;

    private String discountValueStr;

}
