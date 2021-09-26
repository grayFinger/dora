package com.dora.common.lock;

public interface ILockHandler {
    void lock(String key, Integer timeout, ILockExecute lockExecute);

    void lock(String key, ILockExecute lockExecute);
}
