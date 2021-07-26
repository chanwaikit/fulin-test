package com.mazentop.modules.freemarker.fun;

import com.mazentop.entity.SkinPageCode;
import com.mazentop.model.BooleanEnum;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkinPageCodeFun extends Function {

    @Override
    public Object exec() {
        String condIn = getToString(0, "head");
        List<SkinPageCode> skinPageCodes = SkinPageCode.me().setCodeIn(condIn).setIsEnable(BooleanEnum.TRUE.getValue()).find();
        StringBuilder stringBuilder = new StringBuilder();
        skinPageCodes.forEach(item -> stringBuilder.append(item.getContent()));
        return stringBuilder.toString();
    }

    @Override
    public String name() {
        return "skinPageCode";
    }
}
