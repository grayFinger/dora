package com.dora.common.http;

public interface IHttpCache {
    void setCache(String cacheKey, Object result, Long expireTime);

    <T> T getCache(String cacheKey, Class<T> clazz);
}
