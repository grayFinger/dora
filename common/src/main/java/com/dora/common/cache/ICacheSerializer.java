//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache;

import java.io.Serializable;

public interface ICacheSerializer {
    <T> T decode(Object value, Class<T> clazz);

    Serializable encode(Object value);
}
