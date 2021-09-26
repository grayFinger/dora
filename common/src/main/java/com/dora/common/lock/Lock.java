package com.dora.common.lock;

public class Lock {
    public ILockHandler lockHandler;

    public Lock() {
    }

    public void lock(String key, int timeout, ILockExecute lockExecute) {
        if (this.lockHandler != null) {
            this.lockHandler.lock(key, lockExecute);
        }

    }

    public void lock(String key, ILockExecute lockExecute) {
        if (this.lockHandler != null) {
            this.lockHandler.lock(key, lockExecute);
        }

    }

    public void setLockHandler(ILockHandler lockHandler) {
        this.lockHandler = lockHandler;
    }
}
