package com.mazentop.plugins.freemarker.fun;

import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class DateToStringFunction extends Function {
    @Override
    public Object exec() {
        Long timestamp = getToLong(0, 0L);
        // String format = getToString(1, "");
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(new Date(timestamp * 1000));
    }

    @Override
    public String name() {
        return "dateToStr";
    }
}
