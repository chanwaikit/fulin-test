package com.mazentop;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.mazentop.modules.authentication.service.UserDetailsServiceImpl;
import com.mazentop.modules.web.interceptor.AuthenticationInterceptor;
import com.mazentop.modules.web.interceptor.ThemeInterceptor;
import com.mazentop.plugins.cache.EhCacheFactoryBean;
import com.mazentop.plugins.filter.ThreadContent;
import com.mazentop.plugins.i18n.advice.LocalMessageSource;
import com.mazentop.plugins.resservlet.ResourcesServlet;
import com.mazentop.plugins.translation.AliyunTranslation;
import com.mazentop.plugins.translation.ITranslation;
import com.mztframework.FileProperties;
import com.mztframework.file.service.IUploadService;
import com.mztframework.file.service.RemoteUploadService;
import com.mztframework.file.service.SimpleUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Locale;
import java.util.Properties;


/**
 * 自动装配入口
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/6/20 15:13
 */
@Configuration
@ComponentScan("com.mazentop")
@EnableAsync
@EnableSwagger2
@EnableConfigurationProperties({ FileProperties.class })
public class CmsAutoConfiguration  implements WebMvcConfigurer {

    @Autowired
    FileProperties fileProperties;

    @Autowired
    CmsConfig cmsConfig;

    @Bean
    @ConditionalOnProperty(prefix = "mzt.file", name = "remote", havingValue = "false")
    public IUploadService uploadService() {
        return new SimpleUploadService();
    }

    @Bean
    @ConditionalOnProperty(prefix = "mzt.file", name = "remote", havingValue = "true")
    public IUploadService remoteUploadService() {
        return new RemoteUploadService();
    }

    @Bean
    @ConditionalOnProperty(prefix = "mzt.file", name = "remote", havingValue = "false")
    public ServletRegistrationBean<ResourcesServlet> staticResourcesServletRegistration() {
        ServletRegistrationBean<ResourcesServlet> registration = new ServletRegistrationBean<>();
        registration.setServlet(new ResourcesServlet(fileProperties));
        registration.addUrlMappings(String.format("%s%s", ResourcesServlet.URL_MAPPING, "/*"));
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        return methodInvokingFactoryBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册权限认证拦截器
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/attachments/**");
        registry.addInterceptor(new ThemeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/attachments/**");
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }

    @Bean
    public EhCacheFactoryBean ehCacheManagerFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean(cmsConfig);
        ehCacheFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheFactoryBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.US);
        return slr;
    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public MessageSource messageSource() {
        return new LocalMessageSource();
    }

    /**
     * 验证码生成相关
     */
    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "red");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "60");
        properties.put("kaptcha.textproducer.font.size", "45");
        properties.put("kaptcha.session.key", Constants.KAPTCHA_SESSION_KEY);
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.background.clear.from", "white");
        properties.put("kaptcha.background.clear.to", "white");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    @Bean
    public FilterRegistrationBean<ThreadContent> threadContent() {
        FilterRegistrationBean<ThreadContent>filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ThreadContent());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public ITranslation translation() {
        return new AliyunTranslation();
    }

}
