package com.dora.common.lock.impl;


import com.dora.common.lock.ILockExecute;
import com.dora.common.lock.ILockHandler;
import com.dora.common.utils.StringMap;

public class SynchronizedLockHandler implements ILockHandler {
    private static StringMap caches = new StringMap();

    public SynchronizedLockHandler() {
    }

    public static synchronized Object get(String key) {
        if (!caches.containsKey(key)) {
            caches.put(key, key);
        }

        return caches.get(key);
    }

    public static synchronized void remove(String key) {
        caches.remove(key);
    }

    public void lock(String key, Integer timeout, ILockExecute lockExecute) {
        this.lock(key, lockExecute);
    }

    public void lock(String key, ILockExecute lockExecute) {
        Object lock = get(key);
        synchronized(lock) {
            lockExecute.lock();
            remove(key);
        }
    }
}
