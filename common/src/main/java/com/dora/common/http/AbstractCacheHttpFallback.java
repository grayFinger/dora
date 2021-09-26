package com.dora.common.http;

import com.dora.common.utils.ClassUtils;

import java.io.IOException;

public abstract class AbstractCacheHttpFallback<T> extends AbstractHttpFallback<T> {
    public AbstractCacheHttpFallback(String groupKey, String commandKey) {
        super(groupKey, commandKey);
    }

    protected T getData(IHttpClientResolver resolver) throws Exception {
        if (resolver.cache() != null) {
            String cacheKey = this.getCacheKey(resolver);
            if (cacheKey != null) {
                Class clazz = ClassUtils.getGenericClass(this.getClass(), 1);
                return (T) resolver.cache().getCache(cacheKey, clazz);
            }
        }

        return null;
    }

    public void onSuccess(IHttpClientResolver resolver, T result) throws Exception {
        if (resolver.cache() != null) {
            String cacheKey = this.getCacheKey(resolver);
            if (cacheKey != null) {
                resolver.cache().setCache(cacheKey, result, 3600L);
            }
        }

    }

    private String getCacheKey(IHttpClientResolver resolver) throws IOException {
        String cacheKey = resolver.cacheKey();
        if (cacheKey == null && resolver.cacheKeyGenerator() != null) {
            cacheKey = resolver.cacheKeyGenerator().generateCacheKey(resolver.getFullUrl(), resolver.params(), resolver.body());
            resolver.cacheKey(cacheKey);
        }

        return cacheKey;
    }
}
