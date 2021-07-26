package com.mazentop.modules.freemarker.fun;

import com.mztframework.render.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * logo
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/19 17:03
 */
@Component
public class LogoFunction extends Function{

    @Autowired
    ResourceUrlProvider resourceUrlProvider;

    @Override
    public Object exec() {
        return resourceUrlProvider.getForLookupPath("/static/image/logo.png");
    }

    @Override
    public String name() {
        return "pageLogo";
    }
}
