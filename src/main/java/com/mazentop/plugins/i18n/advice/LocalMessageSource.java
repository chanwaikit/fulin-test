package com.mazentop.plugins.i18n.advice;

import com.mazentop.plugins.i18n.cache.I18nCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * 重写国家化数据来源实现方案
 *
 * @author zhaoqt
 */
@Slf4j
public class LocalMessageSource extends AbstractMessageSource {

    /**
     * @param code ：对应messages配置的key.
     * @return
     */
    public String getMessage(String code){
        return getMessage(code, code);
    }

    /**
     *
     * @param code ：对应messages配置的key.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public String getMessage(String code, String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(code, new Object[0], defaultMessage, locale);
    }

    /**
     * 从缓存中取出国际化配置对应的数据 或者从父级获取
     *
     * @param code
     * @param locale
     * @return
     */
    private String getSourceFromCache(String code, Locale locale) {
        String language = locale.getLanguage();
        Map<String, String> props = I18nCache.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        } else {
            try {
                return this.getParentMessageSource().getMessage(code, new Object[0], locale);
            } catch (Exception ex) {
                log.warn("未找到语言：{}-{}", language, code);
            }
            return null;
        }
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getSourceFromCache(code, locale);
        if(Objects.isNull(msg)) {
            msg = "Undefined";
        }
        return new MessageFormat(msg, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getSourceFromCache(code, locale);
    }

}
