package com.mazentop.plugins.i18n.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class I18nControllerAdvice {

    @Autowired
    private LocalMessageSource messageSource;

    @ModelAttribute("i18n")
    public I18nControllerAdvice i18nAdvice() {
        return this;
    }

    public String get(String code) {
        return get(code, null);
    }

    public String get(String code, String defaultMessage) {
        return messageSource.getMessage(code, defaultMessage);
    }
}
