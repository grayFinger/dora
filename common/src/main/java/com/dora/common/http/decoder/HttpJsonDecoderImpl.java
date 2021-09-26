package com.dora.common.http.decoder;

import com.alibaba.fastjson.parser.Feature;
import com.dora.common.http.IHttpDecoder;
import com.dora.common.utils.JsonUtils;

import java.io.IOException;

public class HttpJsonDecoderImpl<T> implements IHttpDecoder<String, T> {
    private Class<T> clazz;
    private String path;

    public HttpJsonDecoderImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public HttpJsonDecoderImpl(Class<T> clazz, String path) {
        this.clazz = clazz;
        this.path = path;
    }

    public T execute(String body) throws IOException {
        return this.path == null ? JsonUtils.fromJson(body, this.clazz, new Feature[0]) : JsonUtils.fromJson(body, this.path, this.clazz, new Feature[0]);
    }
}
