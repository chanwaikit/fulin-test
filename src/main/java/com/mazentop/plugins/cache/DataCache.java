package com.mazentop.plugins.cache;

import com.mztframework.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Objects;


/**
 * @author zhaoqt
 * @title: DataCache
 * @description: 数据缓存
 * @date 2019/5/818:32
 */
@Slf4j
public class DataCache {

    /**
     * 缓存名称 分类
     */
    public static final String NAME_CATEGORY = "cache-product-types";


    private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

    private String cacheName;

    public DataCache() {}

    public DataCache(String cacheName) {
        this.cacheName = cacheName;
    }

    public DataCache setCacheName(String cacheName) {
        this.cacheName = cacheName;
        return this;
    }

    public boolean existKey(String key) {
        return geEhcache().isKeyInCache(key);
    }

    public <T> boolean put(String key, T value) {
        getCache().put(key, value);
        return true;
    }

    public <T> T get(String key, Class<T> clazz) {
        return  getCache().get(key, clazz);
    }

    public boolean del(String key) {
        getCache().evict(key);
        return true;
    }

    public boolean clear() {
        Objects.requireNonNull(cacheName, "缓存名称必须给定");
        getCache().clear();
        return true;
    }


    public Ehcache geEhcache() {
        return (Ehcache)getCache().getNativeCache();
    }
    public Cache getCache() {
        return cacheManager.getCache(cacheName);
    }

    public static DataCache me(String cacheName) {
        return new DataCache(cacheName);
    }
}
