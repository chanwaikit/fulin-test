package com.mazentop.plugins.resservlet;

import com.mazentop.plugins.resservlet.rule.Rule;
import com.mztframework.FileProperties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoqt
 */
public class ResourcesServlet extends HttpServlet {

    FileProperties fileProperties;

    public static final String URL_MAPPING = "/attachments";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Rule.write(req, resp, fileProperties);
    }

    public ResourcesServlet(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }
}
