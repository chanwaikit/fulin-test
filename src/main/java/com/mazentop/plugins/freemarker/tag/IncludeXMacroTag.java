package com.mazentop.plugins.freemarker.tag;

import com.mztframework.render.Tag;
import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.core._MiscTemplateException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Include 增强  支持 默认模板
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/19 22:04
 */
@Slf4j
@Component
public class IncludeXMacroTag extends Tag  {

    private static final String PATH_PARAM = "template";

    private static final String DEFALUT_PATH_PARAM = "defaultTemplate";


    @Override
    public void execute(Environment environment, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)  {
        TemplateLoader templateLoader = environment.getConfiguration().getTemplateLoader();

        try {
            String fullTemplatePath = getFullTemplatePath(environment, params, PATH_PARAM);
            if (templateLoader.findTemplateSource(fullTemplatePath) != null) {
                environment.include(environment.getTemplateForInclusion(fullTemplatePath, null, true));
            } else {
                String defaultFullTemplatePath = getFullTemplatePath(environment, params, DEFALUT_PATH_PARAM);
                if (templateLoader.findTemplateSource(defaultFullTemplatePath) == null) {
                    throw new _MiscTemplateException(environment, "Missing template file path:" + defaultFullTemplatePath);
                }
                environment.include(environment.getTemplateForInclusion(defaultFullTemplatePath, null, true));
            }
        } catch (TemplateException | IOException e) {
            log.error("Missing template file path: ", e);
        }

    }
    private String getFullTemplatePath(Environment environment, @SuppressWarnings("rawtypes") Map params, String templatePath)
            throws MalformedTemplateNameException {
        if (!params.containsKey(templatePath)) {
            throw new MalformedTemplateNameException("missing required parameter '" + templatePath, "'");
        }

        String currentTemplateName = environment.getMainTemplate().getName();
        final String baseName = FilenameUtils.getPath(currentTemplateName);

        final String targetName = params.get(templatePath).toString();

        return environment.toFullTemplateName(baseName, targetName);
    }

    @Override
    public void execute() {}

    @Override
    public String name() {
        return "includeX";
    }
}
