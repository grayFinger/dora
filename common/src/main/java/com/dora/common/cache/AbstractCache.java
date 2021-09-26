//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AbstractCache {
    private ICacheSerializer serializer;

    public AbstractCache() {
    }

    public <T> T decode(Object value, Class<T> clazz) {
        if (value == null) {
            return null;
        } else if (this.serializer != null) {
            return this.serializer.decode(value, clazz);
        } else {
            try {
                return (T) value;
            } catch (Throwable var4) {
                return null;
            }
        }
    }

    public Serializable encode(Object value) {
        if (value == null) {
            return null;
        } else if (this.serializer != null) {
            return this.serializer.encode(value);
        } else {
            return value instanceof Serializable ? (Serializable)value : null;
        }
    }

    public <T> Set<T> decodeSet(Set values, Class<T> clazz) {
        if (values != null && values.size() > 0) {
            Set<T> sets = new LinkedHashSet();
            Iterator var4 = values.iterator();

            while(var4.hasNext()) {
                Object value = var4.next();
                sets.add(this.decode(value, clazz));
            }

            return sets;
        } else {
            return null;
        }
    }

    public <T> List<T> decodeList(List values, Class<T> clazz) {
        if (values != null && values.size() > 0) {
            List<T> list = new ArrayList();
            Iterator var4 = values.iterator();

            while(var4.hasNext()) {
                Object value = var4.next();
                list.add(this.decode(value, clazz));
            }

            return list;
        } else {
            return null;
        }
    }

    public void setSerializer(ICacheSerializer serializer) {
        this.serializer = serializer;
    }
}
