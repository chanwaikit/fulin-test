package com.mazentop.modules.skin.controller.advice;

import com.mazentop.plugins.theme.ThemeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@ControllerAdvice
public class ResControllerAdvice {

    @Autowired
    ResourceUrlProvider resourceUrlProvider;

    @ModelAttribute("_res")
    public ResControllerAdvice resControllerAdvice() {
        return this;
    }

    public String img(String fileName) {
        return resourceUrlProvider.getForLookupPath(String.format("/static/%s/skin/img/%s", ThemeUtil.getTemplatName(), fileName));
    }

    public String css(String fileName) {
        return resourceUrlProvider.getForLookupPath(String.format("/static/%s/skin/css/%s", ThemeUtil.getTemplatName(), fileName));
    }

    public String js(String fileName) {
        return resourceUrlProvider.getForLookupPath(String.format("/static/%s/skin/js/%s", ThemeUtil.getTemplatName(), fileName));
    }

    public String plu(String fileName) {
        return resourceUrlProvider.getForLookupPath(String.format("/static/%s/skin/plugins/%s", ThemeUtil.getTemplatName(), fileName));
    }
}
