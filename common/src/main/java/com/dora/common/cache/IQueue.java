//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache;

import java.io.Serializable;
import java.util.List;

public interface IQueue {
    <T extends Serializable> T pop(String key, Class<T> clazz);

    void push(String key, Object value);

    void push2Top(String key, Object value);

    long size(String key);

    <T extends Serializable> List<T> popList(String key, int size, Class<T> clazz);

    void pushList(String key, List<? extends Serializable> values);

    void pushList2Top(String key, List<? extends Serializable> values);

    void remove(String key);
}
