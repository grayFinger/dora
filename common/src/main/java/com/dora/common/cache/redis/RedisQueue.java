package com.dora.common.cache.redis;

import com.alibaba.fastjson.parser.Feature;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dora.common.cache.IQueue;
import com.dora.common.utils.JsonUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisQueue extends AbstractRedis implements IQueue {
    public RedisQueue() {
    }

    public <T extends Serializable> T pop(String key, Class<T> clazz) {
        Object value = this.redisTemplate.opsForList().leftPop(this.getKey(key));
        return value != null ? (T) this.decode((Serializable)value, clazz) : null;
    }

    public void push(String key, Object value) {
        this.redisTemplate.opsForList().rightPush(this.getKey(key), JsonUtils.toJson(value));
    }

    public void push2Top(String key, Object value) {
        this.redisTemplate.opsForList().leftPush(this.getKey(key), JsonUtils.toJson(value));
    }

    public long size(String key) {
        return this.redisTemplate.opsForList().size(this.getKey(key));
    }

    public <T extends Serializable> List<T> popList(final String key, final int size, final Class<T> clazz) {
        byte[] keyBytes = this.redisTemplate.getKeySerializer().serialize(this.getKey(key));
        return (List)this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            List<T> list = new ArrayList();

            for(int i = 0; i < size; ++i) {
                String value = (String)this.redisTemplate.getValueSerializer().deserialize(redisConnection.lPop(keyBytes));
                if (value == null) {
                    break;
                }

                list.add(JsonUtils.fromJson(value, clazz, new Feature[0]));
            }

            return list;
        });
    }

    public void pushList(String key, List<? extends Serializable> values) {
        if (values != null && values.size() > 0) {
            String[] strs = new String[values.size()];
            int index = 0;

            String value;
            for(Iterator var5 = values.iterator(); var5.hasNext(); strs[index++] = value) {
                Object obj = var5.next();
                value = (String)this.encode(obj);
            }

            this.redisTemplate.opsForList().rightPushAll(this.getKey(key), strs);
        }

    }

    public void pushList2Top(String key, List<? extends Serializable> values) {
        if (values != null && values.size() > 0) {
            String[] strs = new String[values.size()];
            int index = 0;

            String value;
            for(Iterator var5 = values.iterator(); var5.hasNext(); strs[index++] = value) {
                Object obj = var5.next();
                value = (String)this.encode(obj);
            }

            this.redisTemplate.opsForList().leftPushAll(this.getKey(key), strs);
        }

    }

    public void remove(final String key) {
        this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            return redisConnection.del(new byte[][]{this.redisTemplate.getKeySerializer().serialize(this.getKey(key))});
        });
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setConfig(RedisConfig config) {
        this.config = config;
    }
}
