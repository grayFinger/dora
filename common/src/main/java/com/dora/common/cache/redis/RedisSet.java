//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.SetOperations;

public class RedisSet extends AbstractRedis {
    public RedisSet() {
    }

    public void add(String name, Serializable... values) {
        this.getOp().add(this.getKey(name), values);
    }

    public void remove(String name, Serializable... values) {
        this.getOp().remove(this.getKey(name), values);
    }

    public <T extends Serializable> T pop(String name, Class<T> clazz) {
        Serializable value = (Serializable)this.getOp().pop(this.getKey(name));
        return (T) this.decode(value, clazz);
    }

    public boolean move(String from, Serializable value, String to) {
        return this.getOp().move(this.getKey(from), value, this.getKey(to));
    }

    public Long size(String name) {
        return this.getOp().size(this.getKey(name));
    }

    public boolean isMember(String name, Serializable value) {
        return this.getOp().isMember(this.getKey(name), value);
    }

    public <T extends Serializable> Set<T> intersect(String name1, String name2, Class<T> clazz) {
        Set<Serializable> values = this.getOp().intersect(this.getKey(name1), this.getKey(name2));
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> Set<T> intersect(String name, Collection<String> keys, Class<T> clazz) {
        Set<Serializable> values = this.getOp().intersect(this.getKey(name), this.getKeys(keys));
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> Set<T> difference(String name1, String name2, Class<T> clazz) {
        Set<Serializable> values = this.getOp().difference(this.getKey(name1), this.getKey(name2));
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> Set<T> difference(String name, Collection<String> keys, Class<T> clazz) {
        Set<Serializable> values = this.getOp().difference(this.getKey(name), this.getKeys(keys));
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> Set<T> union(String name1, String name2, Class<T> clazz) {
        Set<Serializable> values = this.getOp().union(this.getKey(name1), this.getKey(name2));
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> Set<T> union(String name, Collection<String> keys, Class<T> clazz) {
        Set<Serializable> values = this.getOp().union(this.getKey(name), this.getKeys(keys));
        return this.decodeSet(values, clazz);
    }

    public void intersectAndStore(String from1, String from2, String to) {
        this.getOp().intersectAndStore(this.getKey(from1), this.getKey(from2), this.getKey(to));
    }

    public void intersectAndStore(String from, Collection<String> keys, String to) {
        this.getOp().intersectAndStore(this.getKey(from), this.getKeys(keys), this.getKey(to));
    }

    public void unionAndStore(String from1, String from2, String to) {
        this.getOp().unionAndStore(this.getKey(from1), this.getKey(from2), this.getKey(to));
    }

    public void unionAndStore(String from, Collection<String> keys, String to) {
        this.getOp().unionAndStore(this.getKey(from), this.getKeys(keys), this.getKey(to));
    }

    public void differenceAndStore(String from1, String from2, String to) {
        this.getOp().differenceAndStore(this.getKey(from1), this.getKey(from2), this.getKey(to));
    }

    public void differenceAndStore(String from, Collection<String> keys, String to) {
        this.getOp().differenceAndStore(this.getKey(from), this.getKeys(keys), this.getKey(to));
    }

    public <T extends Serializable> Set<T> members(String name, Class<T> clazz) {
        Set<Serializable> values = this.getOp().members(this.getKey(name));
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> T randomMember(String name, Class<T> clazz) {
        Serializable value = (Serializable)this.getOp().randomMember(this.getKey(name));
        return (T) this.decode(value, clazz);
    }

    public <T extends Serializable> Set<T> distinctRandomMembers(String name, int count, Class<T> clazz) {
        Set<Serializable> values = this.getOp().distinctRandomMembers(this.getKey(name), (long)count);
        return this.decodeSet(values, clazz);
    }

    public <T extends Serializable> List<T> randomMembers(String name, int count, Class<T> clazz) {
        List<Serializable> values = this.getOp().randomMembers(this.getKey(name), (long)count);
        return this.decodeList(values, clazz);
    }

    private List<String> getKeys(Collection<String> keys) {
        List<String> list = new ArrayList();
        Iterator var3 = keys.iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            list.add(this.getKey(key));
        }

        return list;
    }

    private SetOperations<String, Serializable> getOp() {
        return this.redisTemplate.opsForSet();
    }
}
