package com.mazentop.plugins.i18n;

import com.mazentop.plugins.i18n.advice.LocalMessageSource;
import com.mztframework.spring.SpringContextHolder;

/**
 * i18n
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/30 01:29
 */
public class I18n {

    public static String get(String code, String defaultMessage){
        return messageSource().getMessage(code, defaultMessage);
    }

    private static LocalMessageSource messageSource() {
        return SpringContextHolder.getBean(LocalMessageSource.class);
    }
}
