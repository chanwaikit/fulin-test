package com.mazentop.modules.skin.service;

import com.mazentop.entity.SkinI18n;
import com.mazentop.entity.SkinI18nMap;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.skin.dto.SkinI18nSaveDto;
import com.mazentop.plugins.i18n.cache.I18nCache;
import com.mazentop.plugins.translation.ITranslation;
import com.mztframework.commons.Utils;
import com.xkcoding.http.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 语言包
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/6 01:04
 */
@Service
public class SkinI18nService {

    @Autowired
    ITranslation translation;

    ThreadPoolExecutor pool;


    public boolean insertOrUpdate(SkinI18nSaveDto skinI18n) {
        boolean isUpdateCache = false;
        // 主键非空 认为更新操作，
        if(!Utils.isBlank(skinI18n.getId())) {
            // 判断是否修改语言编码，如果语言编码发生变更，需要同步缓存，
            SkinI18n skinI18nDb = SkinI18n.me().setId(skinI18n.getId()).get();
            if(!Objects.isNull(skinI18nDb) && !skinI18nDb.getLangCode().equals(skinI18n.getLangCode())) {
                // 删除旧的
                I18nCache.del(skinI18nDb);
                // 更新语言内编码
                SkinI18nMap.me()
                        .setLangCode(skinI18n.getLangCode())
                        .batchUpdate(SkinI18nMap.me().setI18nId(skinI18n.getId()));
                isUpdateCache = true;
            }
        }
        skinI18n.insertOrUpdate();

        if (!"en".equals(skinI18n.getLangCode())) {
            I18nCache.del(skinI18n);
        }
        if (BooleanEnum.TRUE.getValue().equals(skinI18n.getIsTranslate())) {
            // 转换语言
            convertAndSaveLanguage(skinI18n.getId(),skinI18n.getSourceLanguage(),skinI18n.getLangCode());
        }
        return isUpdateCache;
    }


    /**
     * 转换并保存 国际化映射
     * @param skinI18nId
     * @param sourceLanguage
     * @param targetLanguage
     */
    private void convertAndSaveLanguage (String skinI18nId,String sourceLanguage,String targetLanguage) {
        List<SkinI18nMap> list = Optional.ofNullable(SkinI18nMap.me().setI18nId(skinI18nId).list()).orElse(Collections.emptyList());
        list.forEach(item -> item.deleteById());

        List<SkinI18nMap> skinI18nMapList = Optional.ofNullable(SkinI18nMap.me().setLangCode(sourceLanguage).list()).orElse(Collections.emptyList());

        pool = new ThreadPoolExecutor(10, 10, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(skinI18nMapList.size()), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy()
        );

        skinI18nMapList.forEach(item -> {
            // 转换语言
            pool.execute(() -> {
                String i18nValue = translation.translate(item.getI18nValue(),"en",targetLanguage,"general");
                SkinI18nMap skinI18nMap = SkinI18nMap.me().setI18nId(skinI18nId)
                        .setPage(item.getPage())
                        .setLangCode(targetLanguage)
                        .setI18nKey(item.getI18nKey())
                        .setI18nValue(i18nValue);
                skinI18nMap.insert();
                I18nCache.put(skinI18nMap);
            });
        });
        //执行shutdown
        if (!pool.isShutdown()) {
            pool.shutdown();
            //等待执行结束
            try {
                pool.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
