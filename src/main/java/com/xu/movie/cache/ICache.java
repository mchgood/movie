package com.xu.movie.cache;

/**
 * 缓存通用接口
 */
public interface ICache {
    Object get(String key);
    boolean containsKey(String key);
    void put(String key, Object value);
    void put(String key, Object value,Long expireTime);
    void delete(String key);
}
