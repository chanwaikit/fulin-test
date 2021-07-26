package com.mazentop.modules.freemarker.fun;

import com.mazentop.plugins.seo.Seo;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

/**
 * seo 地址
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 13:47
 */
@Component
public class SkinSeoUrlFun extends Function {


    @Override
    public Object exec() {
        String source = getToString(0);
        String type = getToString(1, "collections");
        return Seo.getSeoUrlDetail(source, type);
    }

    @Override
    public String name() {
        return "skinSeoUrl";
    }
}
