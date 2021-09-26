package com.dora.common.cache.guava;

import com.dora.common.cache.AbstractCache;
import com.dora.common.cache.ExpireElement;
import com.dora.common.cache.ICache;
import com.dora.common.lock.impl.SynchronizedLockHandler;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GuavaCache extends AbstractCache implements ICache {
    private Cache<String, ExpireElement> cache;

    private Object lock = new Object();

    private com.dora.common.cache.guava.GuavaConfig guavaConfig;

    private SynchronizedLockHandler lockHandler = new SynchronizedLockHandler();

    private Cache<String, ExpireElement> getCache() {
        if (this.cache == null)
            synchronized (this.lock) {
                if (this.cache == null)
                    if (this.guavaConfig.getExpires() > 0) {
                        this.cache = CacheBuilder.newBuilder().recordStats().expireAfterWrite(this.guavaConfig.getExpires(), TimeUnit.SECONDS).build();
                    } else {
                        this.cache = CacheBuilder.newBuilder().recordStats().build();
                    }
            }
        return this.cache;
    }

    private ExpireElement getElement(String key) {
        ExpireElement expireElement = (ExpireElement)getCache().getIfPresent(key);
        if (expireElement == null)
            return null;
        if (expireElement.isExpired()) {
            getCache().invalidate(key);
            return null;
        }
        return expireElement;
    }

    public <T> T get(String key, Class<T> clazz) {
        ExpireElement expireElement = getElement(key);
        if (expireElement == null)
            return null;
        return (T)decode(expireElement.getObject(), clazz);
    }

    public Long expire(String key) {
        ExpireElement expireElement = getElement(key);
        if (expireElement == null)
            return Long.valueOf(-2L);
        Long time = expireElement.getRemainTime();
        if (time.longValue() == -2L)
            getCache().invalidate(key);
        return time;
    }

    public void expire(String key, Long expires) {
        ExpireElement expireElement = getElement(key);
        if (expireElement != null)
            expireElement.setExpire(expires);
    }

    public void set(String key, Object value) {
        getCache().put(key, new ExpireElement(encode(value)));
    }

    public void set(Map<String, ?> values) {
        for (String key : values.keySet())
            getCache().put(key, new ExpireElement(encode(values.get(key))));
    }

    public <T> List<T> get(List<String> keys, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (String key : keys)
            list.add(get(key, clazz));
        return list;
    }

    public void set(String key, Object value, int expires) {
        getCache().put(key, new ExpireElement(encode(value), Long.valueOf(expires)));
    }

    public void remove(String key) {
        getCache().invalidate(key);
    }

    public boolean exists(String key) {
        Long time = expire(key);
        return (time.longValue() == -1L || time.longValue() > 0L);
    }

    public long incr(String key) {
        ExpireElement element = getElement(key);
        Long value = Long.valueOf(0L);
        Object lock = SynchronizedLockHandler.get("guava::cache::incr::" + key);
        synchronized (lock) {
            if (element == null) {
                value = Long.valueOf(1L);
                element = new ExpireElement(value);
                getCache().put(key, element);
            } else {
                Long lngValue = (Long)element.getObject();
                value = Long.valueOf(lngValue.longValue() + 1L);
                element.setObject(value);
            }
            SynchronizedLockHandler.remove("guava::cache::incr::" + key);
        }
        return value.longValue();
    }

    public long incrBy(String key, long value) {
        ExpireElement element = getElement(key);
        Long val = Long.valueOf(0L);
        Object lock = SynchronizedLockHandler.get("guava::cache::incrBy::" + key);
        synchronized (lock) {
            if (element == null) {
                val = Long.valueOf(val.longValue() + value);
                element = new ExpireElement(val);
                getCache().put(key, element);
            } else {
                Long lngValue = (Long)element.getObject();
                val = Long.valueOf(lngValue.longValue() + value);
                element.setObject(val);
            }
            SynchronizedLockHandler.remove("guava::cache::incrBy::" + key);
        }
        return val.longValue();
    }

    public long decr(String key) {
        ExpireElement element = getElement(key);
        Long value = Long.valueOf(0L);
        Object lock = SynchronizedLockHandler.get("guava::cache::decr::" + key);
        synchronized (lock) {
            if (element == null) {
                value = Long.valueOf(-1L);
                element = new ExpireElement(value);
                getCache().put(key, element);
            } else {
                Long lngValue = (Long)element.getObject();
                value = Long.valueOf(lngValue.longValue() - 1L);
                element.setObject(value);
            }
            SynchronizedLockHandler.remove("guava::cache::decr::" + key);
        }
        return value.longValue();
    }

    public long decrBy(String key, long value) {
        ExpireElement element = getElement(key);
        Long val = Long.valueOf(0L);
        Object lock = SynchronizedLockHandler.get("guava::cache::decrBy::" + key);
        synchronized (lock) {
            if (element == null) {
                val = Long.valueOf(0L - value);
                element = new ExpireElement(val);
                getCache().put(key, element);
            } else {
                Long lngValue = (Long)element.getObject();
                val = Long.valueOf(lngValue.longValue() - value);
                element.setObject(val);
            }
            SynchronizedLockHandler.remove("guava::cache::decrBy::" + key);
        }
        return val.longValue();
    }

    public Object getAndSet(String key, Object value) {
        set(key, value);
        return value;
    }

    public void setGuavaConfig(GuavaConfig guavaConfig) {
        this.guavaConfig = guavaConfig;
    }
}
