package com.mazentop.plugins.translation;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/6/29 17:11
 */
public interface ITranslation {

    /**
     * 翻译
     * @param sourceText 源文本
     * @param sourceLanguage 源语言种类
     * @param targetLanguage 翻译目标种类
     * @return
     */
    String translate(String sourceText, String sourceLanguage, String targetLanguage, String scene);
}
