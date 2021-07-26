package com.mazentop.modules.freemarker.fun;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SkinBlock;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class SkinBlockFun extends Function {
    @Override
    public Object exec() {
        Map<String, Object> map = new HashMap<>(3);
        String quote = getToString(0, "");
        if(!StrUtil.isBlank(quote)) {
            SkinBlock skinBlock = SkinBlock.me().setQuote(quote).get();
            if (!Objects.isNull(skinBlock)){
                map.put("title", skinBlock.getTitle());
                map.put("subTitle", skinBlock.getSubTitle());
                map.put("content", JSON.parse(skinBlock.getContent()));
            }
        }
        return map;
    }

    @Override
    public String name() {
        return "skinBlockFun";
    }
}
