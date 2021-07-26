package com.mazentop.modules.freemarker.fun;

import com.mazentop.util.JsoupUtils;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

/**
 * 截取html 文本
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 16:26
 */
@Component
public class SkinSummaryWithFun extends Function {
    @Override
    public Object exec() {
        String html = getToString(0);
        Integer len = getToInteger(1, 100);
        if (html == null) {
            return null;
        }
        String text = JsoupUtils.getText(html);
        if (text != null && text.length() > len) {
            return text.substring(0, len);
        }
        return html;
    }

    @Override
    public String name() {
        return "skinSummaryWith";
    }
}
