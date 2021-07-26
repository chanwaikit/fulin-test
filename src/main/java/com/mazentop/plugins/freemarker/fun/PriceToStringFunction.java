package com.mazentop.plugins.freemarker.fun;

import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

@Component
public class PriceToStringFunction extends Function {
    @Override
    public Object exec() {
        Long price = getToLong(0, 10000000000L);

        Double curPrice = (double) (price / 100);

        return curPrice;
    }

    @Override
    public String name() {
        return "priceToStr";
    }
}
