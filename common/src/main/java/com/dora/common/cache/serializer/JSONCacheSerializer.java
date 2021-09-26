package com.dora.common.cache.serializer;

import com.alibaba.fastjson.parser.Feature;

import com.dora.common.cache.ICacheSerializer;
import com.dora.common.utils.JsonUtils;

import java.io.Serializable;

public class JSONCacheSerializer implements ICacheSerializer {
    public JSONCacheSerializer() {
    }

    public <T> T decode(Object value, Class<T> clazz) {
        return JsonUtils.fromJson(value.toString(), clazz, new Feature[0]);
    }

    public Serializable encode(Object value) {
        return JsonUtils.toJson(value);
    }
}
