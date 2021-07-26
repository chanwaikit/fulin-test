package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ActConditionType;
import com.mazentop.entity.ActReduceActivity;
import lombok.Data;

import java.util.List;

@Data
public class ActReducectivityDto extends ActReduceActivity {

    private List<ActConditionTypeDto>typeList;
}
