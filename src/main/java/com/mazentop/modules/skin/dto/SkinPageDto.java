package com.mazentop.modules.skin.dto;

import com.mazentop.entity.ProSeo;
import com.mazentop.entity.SkinPage;
import com.mazentop.modules.skin.warpper.ConditionWarpper;
import lombok.Data;

import java.util.List;

@Data
public class SkinPageDto extends SkinPage {

   List<ConditionWarpper> conditionData;

   List<String> proId;

   Integer conditionFlag;

   String dataSource;

   String sortingRules;

   Integer maxLimit;

   private ProSeo seo;
}
