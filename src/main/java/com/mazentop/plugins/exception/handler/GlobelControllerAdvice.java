package com.mazentop.plugins.exception.handler;

import com.mazentop.CmsConfig;
import com.mazentop.plugins.theme.ThemeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/8/17 14:39
 */
@Slf4j
@ControllerAdvice
public class GlobelControllerAdvice {

    @Autowired
    CmsConfig cmsConfig;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        if(StringUtils.isBlank(cmsConfig.getCtxPath())) {
            model.addAttribute("ctxPath", request.getContextPath());
        } else {
            model.addAttribute("ctxPath", cmsConfig.getCtxPath());
        }
        model.addAttribute("templatePath", ThemeUtil.getTemplatPath());
        model.addAttribute("templateName", ThemeUtil.getTemplatName());
    }

}
