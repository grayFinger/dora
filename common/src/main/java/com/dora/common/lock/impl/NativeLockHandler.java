//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.lock.impl;

import com.dora.common.lock.ILockExecute;
import com.dora.common.lock.ILockHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class NativeLockHandler implements ILockHandler {
    private ReentrantLock globalLock = new ReentrantLock();
    private static Map<String, ReentrantLock> lockMap = new HashMap();

    public NativeLockHandler() {
    }

    private ReentrantLock getLock(String key) {
        ReentrantLock lock = (ReentrantLock)lockMap.get(key);
        if (lock == null) {
            try {
                this.globalLock.tryLock(10L, TimeUnit.SECONDS);
                lock = (ReentrantLock)lockMap.get(key);
                if (lock == null) {
                    lock = new ReentrantLock();
                    lockMap.put(key, lock);
                }
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            } finally {
                this.globalLock.unlock();
            }
        }

        return lock;
    }

    public ReentrantLock removeLock(String key) {
        return (ReentrantLock)lockMap.remove(key);
    }

    public void lock(String key, Integer timeout, ILockExecute lockExecute) {
        ReentrantLock lock = this.getLock(key);

        try {
            lock.tryLock((long)timeout, TimeUnit.SECONDS);
            lockExecute.lock();
        } catch (InterruptedException var9) {
            var9.printStackTrace();
        } finally {
            lock.unlock();
            this.removeLock(key);
        }

    }

    public void lock(String key, ILockExecute lockExecute) {
        ReentrantLock lock = this.getLock(key);
        lock.lock();

        try {
            lockExecute.lock();
        } finally {
            lock.unlock();
            this.removeLock(key);
        }

    }

    public static void main(String[] args) {
        ILockHandler lockHandler = new NativeLockHandler();

        for(int i = 0; i < 100; ++i) {
            if (i % 2 == 0) {
                (new TestThread("A", lockHandler)).start();
            } else {
                (new TestThread("B", lockHandler)).start();
            }
        }

    }

    private static class TestThread extends Thread {
        private String key;
        private ILockHandler lockHandler;

        public TestThread(String key, ILockHandler lockHandler) {
            this.key = key;
            this.lockHandler = lockHandler;
        }

        public void run() {
            this.lockHandler.lock(this.key, new ILockExecute() {
                public void lock() {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }

                    System.out.println("当前线程：key==>" + TestThread.this.key + ", name===>" + TestThread.this.getName() + ":" + TestThread.this.toString());
                }
            });
        }
    }
}
