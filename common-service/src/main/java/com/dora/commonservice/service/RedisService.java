package com.dora.commonservice.service;

import org.redisson.api.RKeys;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RedisService
 * @Author: zhaolianqi
 * @Date: 2021/8/23 14:26
 * @Version: v1.0
 */
@Service
public class RedisService {

    @Autowired
    private RedissonClient redissonClient;

    public Object get(String key) {
        return redissonClient.getBucket(key).get();
    }

    public void put(String key, Object object, int seconds) {
        redissonClient.getBucket(key).set(object, seconds, TimeUnit.SECONDS);
    }

    public Object getFromMapCache(String map, String key) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(map);
        if (mapCache == null)
            return null;
        return mapCache.get(key);
    }

    public void removeMapCache(String map) {
        redissonClient.getMapCache(map).delete();
    }

    public void removeMapCache(String map, String key) {
        redissonClient.getMapCache(map).remove(key);
    }

    public void putMapCache(String map, String key, Object object, int seconds) {
        redissonClient.getMapCache(map).put(key, object, seconds, TimeUnit.SECONDS);
    }

    public int incrementNumber(String map, String key) {
        return (int) redissonClient.getMapCache(map).addAndGet(key, 1);
    }

    public List<String> getKeysByPattern(String pattern) {
        RKeys rKeys = redissonClient.getKeys();
        Iterable<String> keys = rKeys.getKeysByPattern(pattern);
        Iterator<String> keyIt = keys.iterator();
        List<String> res = new ArrayList<>(10);
        while (keyIt.hasNext()){
            res.add(keyIt.next());
        }
        return res;
    }

    public RedissonClient getRedissonClient(){
        return redissonClient;
    }

}
