package com.mazentop.plugins.resservlet.rule.impl;


import cn.hutool.core.util.URLUtil;
import com.mazentop.plugins.resservlet.ResourcesServlet;
import com.mazentop.plugins.resservlet.rule.Rule;
import com.mztframework.FileProperties;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoqt
 */
public class VideoRule extends Rule {

    public VideoRule(HttpServletRequest req, HttpServletResponse resp, FileProperties fileProperties) {
        this.req = req;
        this.resp = resp;
        this.fileProperties = fileProperties;
    }
    @Override
    public void write() throws IOException {
        String uri = this.req.getRequestURI();
        String filePath = URLUtil.decode(StringUtils.removeStart(uri, ResourcesServlet.URL_MAPPING));
        this.processRequest(req, resp, file(filePath));
    }
}
