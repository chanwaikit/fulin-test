package com.mazentop.plugins.cache;

import lombok.Data;

/**
 * 缓存类
 *
 */
@Data
public class Cache implements Comparable<Cache>{

    // 键
    private Object key;
    // 缓存值
    private Object value;
    // 最后一次访问时间
    private long accessTime;
    // 创建时间
    private long writeTime;
    // 存活时间
    private long expireTime;
    // 命中次数
    private Integer hitCount;

    @Override
    public int compareTo(Cache o) {
        return hitCount.compareTo(o.hitCount);
    }
}
