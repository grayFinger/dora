package com.dora.common.cache.redis;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.dora.common.cache.ICache;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCache extends AbstractRedis implements ICache {
    public RedisCache() {
    }

    public <T> T get(String key, Class<T> clazz) {
        Serializable ret = (Serializable)this.getOp().get(this.getKey(key));
        return this.decode(ret, clazz);
    }

    public Long expire(String key) {
        return this.redisTemplate.getExpire(this.getKey(key));
    }

    public void expire(String key, Long expires) {
        if (expires != null && expires > 0L) {
            this.redisTemplate.expire(this.getKey(key), expires, TimeUnit.SECONDS);
        }

    }

    public void set(String key, Object value) {
        this.set(key, this.encode(value), this.config.getExpires());
    }

    public void set(Map<String, ?> values) {
        if (values != null && values.size() > 0) {
            Map<String, Serializable> _values = new HashMap();
            Iterator var3 = values.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                _values.put(this.getKey(key), this.encode(values.get(key)));
            }

            this.getOp().multiSet(_values);
        }

    }

    public <T> List<T> get(List<String> keys, Class<T> clazz) {
        if (keys != null && keys.size() > 0) {
            List<String> _keys = new ArrayList();
            Iterator var4 = keys.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                _keys.add(this.getKey(key));
            }

            List<Serializable> list = this.getOp().multiGet(keys);
            return this.decodeList(list, clazz);
        } else {
            return new ArrayList();
        }
    }

    public void set(String key, Object value, int expires) {
        this.getOp().set(this.getKey(key), this.encode(value));
        if (expires > 0) {
            this.redisTemplate.expire(this.getKey(key), (long)expires, TimeUnit.SECONDS);
        }

    }

    public void remove(String key) {
        this.getOp().getOperations().delete(this.getKey(key));
    }

    public boolean exists(String key) {
        return this.getOp().getOperations().hasKey(this.getKey(key));
    }

    public long incr(final String key) {
        return (Long)this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            return redisConnection.incr(this.redisTemplate.getKeySerializer().serialize(this.getKey(key)));
        });
    }

    public long incrBy(final String key, final long value) {
        return this.getOp().increment(key, value);
    }

    public long decr(final String key) {
        return (Long)this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            return redisConnection.decr(this.redisTemplate.getKeySerializer().serialize(this.getKey(key)));
        });
    }

    public long decrBy(final String key, final long value) {
        return (Long)this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            return redisConnection.decrBy(this.redisTemplate.getKeySerializer().serialize(this.getKey(key)), value);
        });
    }

    public <T> T getAndSet(String key, T value) {
        Serializable ret = (Serializable)this.getOp().getAndSet(this.getKey(key), this.encode(value));
        return (T) this.decode(ret, value.getClass());
    }

    private ValueOperations<String, Serializable> getOp() {
        return this.redisTemplate.opsForValue();
    }
}
