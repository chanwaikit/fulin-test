package com.mazentop.plugins.freemarker.fun;

import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 为null 显示默认值
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/20 16:12
 */
@Component
public class NullDefaultFunction extends Function {

    private List args;

    @Override
    public Object exec(List args)  {
        this.args = args;
        return super.exec(args);
    }
    @Override
    public Object exec() {
        if(Objects.isNull(args) || args.isEmpty()) {
            return  "";
        }
        return getToString(0, getToString(1));
    }

    @Override
    public String name() {
        return "nullDefault";
    }
}
