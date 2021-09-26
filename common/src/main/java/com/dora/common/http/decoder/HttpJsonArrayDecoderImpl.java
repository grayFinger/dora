package com.dora.common.http.decoder;

import com.dora.common.http.IHttpDecoder;
import com.dora.common.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

public class HttpJsonArrayDecoderImpl<T> implements IHttpDecoder<String, List<T>> {
    private Class<T> clazz;
    private String path;

    public HttpJsonArrayDecoderImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public HttpJsonArrayDecoderImpl(Class<T> clazz, String path) {
        this.clazz = clazz;
        this.path = path;
    }

    public List<T> execute(String body) throws IOException {
        return this.path == null ? JsonUtils.fromJsonList(body, this.clazz) : JsonUtils.fromJsonList(body, this.path, this.clazz);
    }
}
