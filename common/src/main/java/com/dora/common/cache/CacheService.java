//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

public class CacheService {
    private static Logger logger = LoggerFactory.getLogger(CacheService.class);
    private ICache cache;
    private ICacheSerializer cacheSerializer;

    public CacheService() {
    }

    public <T> T load(String key, ICacheDataCallback<T> callback, boolean forceRefresh, int expires, Class<T> clazz) throws Throwable {
        T value = null;
        if (!forceRefresh) {
            try {
                value = this.cache.get(key, clazz);
            } catch (Throwable var9) {
                logger.error("加载key={}的缓存数据失败", key, var9);
            }
        }

        if (value == null) {
            value = callback.load();
            if (value != null) {
                try {
                    this.cache.set(key, value, expires);
                } catch (Throwable var8) {
                    logger.error("缓存key={}的数据失败", key, var8);
                }
            }
        }

        return value;
    }

    public <T> T get(String key, Class<T> clazz) {
        return this.cache.get(key, clazz);
    }

    public void set(String key, Object value) {
        this.cache.set(key, value);
    }

    @Async
    public void setWithAsync(String key, Object value) {
        this.set(key, value);
    }

    public void set(String key, Object value, int expire) {
        this.cache.set(key, value, expire);
    }

    @Async
    public void setWithAsync(String key, Object value, int expire) {
        this.set(key, value, expire);
    }

    public Long expire(String key) {
        return this.cache.expire(key);
    }

    public void expire(String key, long expires) {
        if (expires > 0L) {
            this.cache.expire(key, expires);
        }

    }

    @Async
    public void expireWithAsync(String key, long expires) {
        this.expire(key, expires);
    }

    public void remove(String key) {
        this.cache.remove(key);
    }

    @Async
    public void removeWithAsync(String key) {
        this.remove(key);
    }

    public boolean exists(String key) {
        return this.cache.exists(key);
    }

    public <T> T load(String key, ICacheDataCallback<T> callback, int expires, Class<T> clazz) throws Throwable {
        return this.load(key, callback, false, expires, clazz);
    }

    public <T> T load(String key, ICacheDataCallback<T> callback, Class<T> clazz) throws Throwable {
        return this.load(key, callback, false, -1, clazz);
    }

    public <T> T load(String key, ICacheDataCallback<T> callback, boolean forceRefresh, Class<T> clazz) throws Throwable {
        return this.load(key, callback, forceRefresh, -1, clazz);
    }

    public void setCache(ICache cache) {
        this.cache = cache;
        if (this.cacheSerializer != null) {
            this.cache.setSerializer(this.cacheSerializer);
        }

    }

    public void setCacheSerializer(ICacheSerializer cacheSerializer) {
        this.cacheSerializer = cacheSerializer;
        if (this.cache != null) {
            this.cache.setSerializer(cacheSerializer);
        }

    }
}
