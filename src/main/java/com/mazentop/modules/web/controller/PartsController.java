package com.mazentop.modules.web.controller;

import com.mazentop.plugins.theme.ThemeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 部件
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 02:12
 */
@Controller
@RequestMapping("/parts")
public class PartsController extends BaseController {

    @GetMapping("/{parts}")
    public String parts(@PathVariable String parts, HttpServletRequest request, ModelMap map) {
        map.put("parts", parts);
        map.put("request", request);
        map.put("templatePath", ThemeUtil.getTemplatPath());
        map.put("templateName", ThemeUtil.getTemplatName());
        return templatePath(String.format("_parts/%s", parts));
    }
}
