package com.mazentop.modules.freemarker.fun;

import com.mazentop.plugins.session.RequestCount;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

/**
 * 限流
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 21:23
 */
@Component
public class CurrentLimitingFun extends Function {
    @Override
    public Object exec() {
        RequestCount.CurrentLimiting currentLimiting
                = RequestCount.CurrentLimiting.getCurrentLimiting(getToString(0, "subscription"));
        return RequestCount.isCurrentLimiting(currentLimiting) || RequestCount.isViewCode(currentLimiting);
    }

    @Override
    public String name() {
        return "currentLimiting";
    }
}
