package com.mazentop.plugins.i18n.cache;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.SkinI18n;
import com.mazentop.entity.SkinI18nMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * i18n缓存
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/30 01:23
 */
public class I18nCache {

    private static final Map<String, Map<String, String>> LOCAL_CACHE;

    static {
        LOCAL_CACHE = new ConcurrentHashMap<>(20);
    }

    public static void loadAll() {
        LOCAL_CACHE.clear();
        List<SkinI18nMap> skinI18nMaps = SkinI18nMap.me().find();
        Map<String, List<SkinI18nMap>> skinI18nMapGroup =
                skinI18nMaps.stream().collect(Collectors.groupingBy(SkinI18nMap::getLangCode));

        skinI18nMapGroup.forEach((langCode, skinI18ns)
                        -> {
                    Map<String, String> cacheI18nMap  = new HashMap<>(100);
                    LOCAL_CACHE.put(StrUtil.trim(langCode), cacheI18nMap);
                    for(SkinI18nMap skinI18nMap: skinI18ns) {
                        cacheI18nMap.put(StrUtil.trim(skinI18nMap.getI18nKey()), skinI18nMap.getI18nValue());
                    }
                }
        );
    }

    public static void put(SkinI18n skinI18n) {
        if(Objects.isNull(skinI18n)) {
            return;
        }
        if(!LOCAL_CACHE.containsKey(skinI18n.getLangCode())) {
            LOCAL_CACHE.put(skinI18n.getLangCode(), new HashMap<>(1));
        }
        List<SkinI18nMap> skinI18nMaps = SkinI18nMap.me().setI18nId(skinI18n.getId()).find();
        skinI18nMaps.forEach(skinI18nMap ->
                LOCAL_CACHE.get(skinI18nMap.getLangCode())
                        .put(skinI18nMap.getI18nKey(), skinI18nMap.getI18nValue()));
    }
    public static void del(SkinI18n skinI18n) {
        if(Objects.isNull(skinI18n)) {
            return;
        }
        LOCAL_CACHE.remove(skinI18n.getLangCode());
    }

    public static Map<String, String> get(String language) {
        return LOCAL_CACHE.get(language);
    }

    public static void put(SkinI18nMap skinI18nMap) {
        if(Objects.isNull(skinI18nMap)) {
            return;
        }
        if(!LOCAL_CACHE.containsKey(skinI18nMap.getLangCode())) {
            LOCAL_CACHE.put(skinI18nMap.getLangCode(), new HashMap<>(1));
        }
        LOCAL_CACHE.get(skinI18nMap.getLangCode()).put(skinI18nMap.getI18nKey(), skinI18nMap.getI18nValue());
    }

    public static void del(SkinI18nMap skinI18nMap) {
        if(Objects.isNull(skinI18nMap)) {
            return;
        }
        if(LOCAL_CACHE.containsKey(skinI18nMap.getLangCode())) {
            LOCAL_CACHE.get(skinI18nMap.getLangCode()).remove(skinI18nMap.getI18nKey());
        }
    }
}
