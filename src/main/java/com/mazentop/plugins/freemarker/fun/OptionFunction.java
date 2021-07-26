package com.mazentop.plugins.freemarker.fun;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.SysOptions;
import com.mztframework.cache.Options;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangzy
 * @title: OptionFunction
 * @description: 參數配置
 * @date 2019/5/917:45
 */
@Component
public class OptionFunction extends Function {

    @Override
    public Object exec() {
        String key = getToString(0, "key");
        String defaultStr = getToString(1, "");
        return StrUtil.nullToDefault(Options.get(key), defaultStr);
    }

    @Override
    public String name() {
        return "options";
    }
}
