//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache;

import java.util.List;
import java.util.Map;

public interface ICache {
    <T> T get(String key, Class<T> clazz);

    Long expire(String key);

    void expire(String key, Long expires);

    void set(String key, Object value);

    void set(Map<String, ?> values);

    <T> List<T> get(List<String> keys, Class<T> clazz);

    void set(String key, Object value, int expires);

    void remove(String key);

    boolean exists(String key);

    long incr(String key);

    long incrBy(String key, long value);

    long decr(String key);

    long decrBy(String key, long value);

    <T> T getAndSet(String key, T value);

    void setSerializer(ICacheSerializer serializer);
}
