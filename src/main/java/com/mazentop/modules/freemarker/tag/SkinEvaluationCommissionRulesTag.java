package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.entity.SysOptions;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.modules.web.User;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class SkinEvaluationCommissionRulesTag extends Tag{

    @Override
    public void execute() {

        Map<String, Object> map = new HashMap<>(1);
        // 获取分享金
        SysOptions sysOptions = SysOptions.me().setOptionKey("site_commission_rules").get();
        if(Objects.nonNull(sysOptions)){
            map.put("commission",sysOptions.getOptionValue());
        }else{
            map.put("commission",0);
        }
        setVariable("rule", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_evaluation_commission_rules";
    }
}
