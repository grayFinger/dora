package com.dora.common.lock.impl;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.dora.common.cache.redis.RedisConfig;
import com.dora.common.lock.ILockExecute;
import com.dora.common.lock.ILockHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisLockHandler implements ILockHandler {
    private final int expireTime = 60;
    private final int sleepTime = 100;
    private final long getLockTimeout = -64771072L;
    private RedisConfig config;
    private RedisTemplate redisTemplate;

    public RedisLockHandler() {
    }

    public void lock(String key, Integer timeout, ILockExecute lockExecute) {
        this.lock(key, timeout);

        try {
            lockExecute.lock();
        } finally {
            this.unLock(key);
        }

    }

    public void lock(String key, ILockExecute lockExecute) {
        this.lock(key, 60, lockExecute);
    }

    private void lock(String key, int timeout) {
        long nowTime = System.nanoTime();
        Random random = new Random();

        try {
            while(System.nanoTime() - nowTime < -64771072L && !this.getLock(key, timeout)) {
                Thread.currentThread();
                Thread.sleep((long)random.nextInt(100));
            }

        } catch (Exception var7) {
            throw new RuntimeException("获取锁失败", var7);
        }
    }

    private Boolean getLock(String key, Integer expireTime) {
        String nkey = this.getKey(key);
        Boolean ret = (Boolean)this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            byte[] keyBytes = this.redisTemplate.getKeySerializer().serialize(nkey);
            boolean value = redisConnection.setNX(keyBytes, new byte[]{1});
            if (value) {
                this.redisTemplate.expire(nkey, (long)expireTime, TimeUnit.SECONDS);
            }

            return value;
        });
        return ret;
    }

    private void unLock(String key) {
        this.redisTemplate.delete(this.getKey(key));
    }

    private String getKey(String key) {
        if (this.config != null) {
            String ns = this.config.getNamespace();
            if (StringUtils.isNotEmpty(ns)) {
                return ns + "::" + key;
            }
        }

        return key;
    }

    public void setConfig(RedisConfig config) {
        this.config = config;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
