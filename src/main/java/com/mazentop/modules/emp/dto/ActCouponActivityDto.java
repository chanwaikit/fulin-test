package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ActCouponActivity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ActCouponActivityDto extends ActCouponActivity {

    private List<Map<String,Object>> actPromotionProductList;
}
