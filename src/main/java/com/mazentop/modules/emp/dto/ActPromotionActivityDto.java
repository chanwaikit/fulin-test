package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ActPromotionActivity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ActPromotionActivityDto extends ActPromotionActivity {

    private List<Map<String,Object>> actPromotionProductList;
}
