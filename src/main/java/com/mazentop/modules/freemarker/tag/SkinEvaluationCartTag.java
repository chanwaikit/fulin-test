package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.modules.web.User;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SkinEvaluationCartTag extends Tag{

    @Override
    public void execute() {

        Map<String, Object> map = new HashMap<>(1);
        if(User.isAuth()){
            map.put("cartNum", EvaOrdOrder.me().setStatus(EvaOrdOrderStatusEnum.PENDING.getStatus()).setFkClienteleId(User.id()).setIsEnable(BooleanEnum.TRUE.getValue()).findCount());
        }else{
            map.put("cartNum", -1);
        }
        setVariable("cart", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_evaluation_cart";
    }
}
