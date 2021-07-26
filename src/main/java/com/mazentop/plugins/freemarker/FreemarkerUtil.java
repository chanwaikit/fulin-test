package com.mazentop.plugins.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * @Title: FreemarkerUtil.java
 * @Description: freemarker 模板生成
 *
 * @author zhaoqt
 * @date Jun 24, 2016 11:34:11 PM
 */
@Slf4j
public class FreemarkerUtil {

	public static Configuration getConfiguration(String templateContent){
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		try {
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("templateStr", templateContent);
			cfg.setTemplateLoader(stringLoader);
			cfg.setLocale(Locale.ENGLISH);
			cfg.setDefaultEncoding("utf-8");
			cfg.setClassicCompatible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cfg;
	}

	/**
	 * 生成  Freemarker Template 文件  非静态 文件生成 
	 * 
	 * @param rootMap 参数  map 
	 */
	public static String make(Map<String, Object> rootMap, String templateContent) {
		// 生成 Freemarker 配置文件
		Configuration configuration = getConfiguration(templateContent);
		// <#> 转换[#]
		configuration.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
		//  模板 文件生成
		Template template;
		Writer writer = null;
		try {
			template = configuration.getTemplate("templateStr");
			writer = new StringWriter();
			// 文件输出
			template.process(rootMap, writer);
			return writer.toString();
		} catch (IOException e) {
			log.error("[Freemarker Template Make] io 异常", e);
		} catch (TemplateException e) {
            log.error("[Freemarker Template Make] 未找到指定模板 or 模板错误", e);
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
                log.error("[Freemarker Template Make] 生成 io关闭 异常", e);
			}
		}
		return "";
	}


	/*public static void main(String[] args) {
		Map<String, Object> rootMap = new HashMap<>();
		rootMap.put("name", "扎根没事");
		System.out.println(FreemarkerUtil.make(rootMap, "[#if name== '扎根没事']hello ${name}![/#if]"));
	}
*/
}
