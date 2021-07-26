package com.mazentop.plugins.cache;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具类
 */
public   class LFUCache {



    private final static long expire=43200;
    private final static int size=100;
    private static ConcurrentHashMap<Object, Cache> concurrentHashMap= new ConcurrentHashMap<>(size);


    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        checkNotNull(key);
        if (concurrentHashMap.isEmpty()) return null;
        if (!concurrentHashMap.containsKey(key)) return null;
        Cache cache = concurrentHashMap.get(key);
        if (cache == null) return null;
        cache.setHitCount(cache.getHitCount()+1);
        cache.setAccessTime(System.currentTimeMillis());
        return cache.getValue();
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        checkNotNull(key);
        checkNotNull(value);
        // 当缓存存在时，更新缓存
        if (concurrentHashMap.containsKey(key)){
            Cache cache = concurrentHashMap.get(key);
            cache.setHitCount(cache.getHitCount()+1);
            cache.setWriteTime(System.currentTimeMillis());
            cache.setAccessTime(System.currentTimeMillis());
            cache.setExpireTime(expire);
            cache.setValue(value);
            return;
        }
        // 已经达到最大缓存
        if (isFull()) {
            Object kickedKey = getKickedKey();
            if (kickedKey !=null){
                // 移除最少使用的缓存
                concurrentHashMap.remove(kickedKey);
            }else {
                return;
            }
        }
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setValue(value);
        cache.setWriteTime(System.currentTimeMillis());
        cache.setAccessTime(System.currentTimeMillis());
        cache.setHitCount(1);
        cache.setExpireTime(expire);
        concurrentHashMap.put(key, cache);
    }

    /**
     * 检测字段是否合法
     *
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * 判断是否达到最大缓存
     *
     * @return
     */
    private static boolean isFull() {
        return concurrentHashMap.size() == size;
    }

    /**
     * 获取最少使用的缓存
     * @return
     */
    private static Object getKickedKey() {
        Cache min = Collections.min(concurrentHashMap.values());
        return min.getKey();
    }

    public static void remove(Object key){
        concurrentHashMap.remove(key);
    }

}


