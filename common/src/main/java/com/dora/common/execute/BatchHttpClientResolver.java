package com.dora.common.execute;

import com.dora.common.http.*;
import com.dora.common.view.PageInfo;

import java.io.OutputStream;
import java.util.List;

public class BatchHttpClientResolver {
    private IHttpClientResolver httpClientResolver;
    private FutureHelperResolver futureHelperResolver;
    private String name;

    public BatchHttpClientResolver(String name, IHttpClientResolver httpClientResolver, FutureHelperResolver futureHelperResolver) {
        this.httpClientResolver = httpClientResolver;
        this.futureHelperResolver = futureHelperResolver;
        this.name = name;
    }

    public IHttpClientResolver httpClientResolver() {
        return this.httpClientResolver;
    }

    public BatchHttpClientResolver httpClientResolver(IHttpClientResolver httpClientResolver) {
        this.httpClientResolver = httpClientResolver;
        return this;
    }

    public BatchHttpClientResolver interceptor(IHttpInterceptor interceptor) {
        this.httpClientResolver.interceptor(interceptor);
        return this;
    }

    public BatchHttpClientResolver header(String name, Object value) {
        this.httpClientResolver.header(name, value);
        return this;
    }

    public BatchHttpClientResolver flushCache(boolean flushcache) {
        this.httpClientResolver.flushCache(flushcache);
        return this;
    }

    public BatchHttpClientResolver cacheable(boolean cacheable) {
        this.httpClientResolver.cacheable(cacheable);
        return this;
    }

    public BatchHttpClientResolver cache(IHttpCache httpCache) {
        this.httpClientResolver.cache(httpCache);
        return this;
    }

    public BatchHttpClientResolver cacheKeyGenerator(IHttpCacheKeyGenerator cacheKeyGenerator) {
        this.httpClientResolver.cacheKeyGenerator(cacheKeyGenerator);
        return this;
    }

    public BatchHttpClientResolver cacheKey(String cacheKey) {
        this.httpClientResolver.cacheKey(cacheKey);
        return this;
    }

    public BatchHttpClientResolver expireTime(long expireTime) {
        this.httpClientResolver.expireTime(expireTime);
        return this;
    }

    public BatchHttpClientResolver param(String key, Object value) {
        this.httpClientResolver.param(key, value);
        return this;
    }

    public BatchHttpClientResolver body(Object body) {
        this.httpClientResolver.body(body);
        return this;
    }

    public BatchHttpClientResolver contentType(HttpMediaType mediaType) {
        this.httpClientResolver.contentType(mediaType);
        return this;
    }

    public BatchHttpClientResolver charset(String charset) {
        this.httpClientResolver.charset(charset);
        return this;
    }

    public BatchHttpClientResolver baseUrl(String baseUrl) {
        this.httpClientResolver.baseUrl(baseUrl);
        return this;
    }

    public <T> BatchHttpClientResolver fallback(IHttpFallback<T> fallback) {
        this.httpClientResolver.fallback(fallback);
        return this;
    }

    public <T> IHttpFallback<T> fallback() {
        return this.httpClientResolver.fallback();
    }

    public BatchHttpClientResolver url(String url) {
        this.httpClientResolver.url(url);
        return this;
    }

    public <I, T> void execute(HttpMethod method, IHttpDecoder<I, T> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.execute(method, decoder);
        });
    }

    public void execute(HttpMethod method) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.execute(method);
        });
    }

    public void download(OutputStream os) throws Exception {
        this.futureHelperResolver.supplyAsync(() -> {
            this.httpClientResolver.download(os);
            return null;
        });
    }

    public void download(String name, OutputStream os) throws Exception {
        this.futureHelperResolver.supplyAsync(name, () -> {
            this.httpClientResolver.download(os);
            return null;
        });
    }

    public void executeForString(HttpMethod method) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForString(method);
        });
    }

    public <T> void executeForObject(HttpMethod method, IHttpDecoder<String, T> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForObject(method, decoder);
        });
    }

    public <T> void executeForPageInfo(HttpMethod method, IHttpDecoder<String, PageInfo<T>> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForPageInfo(method, decoder);
        });
    }

    public <T> void executeForList(HttpMethod method, IHttpDecoder<String, List<T>> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForList(method, decoder);
        });
    }

    public <T> void executeForObject(HttpMethod method, String path, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForObject(method, path, clazz);
        });
    }

    public <T> void executeForObject(HttpMethod method, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForObject(method, clazz);
        });
    }

    public <T> void executeForPageInfo(HttpMethod method, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForPageInfo(method, clazz);
        });
    }

    public <T> void executeForList(HttpMethod method, String path, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForList(method, path, clazz);
        });
    }

    public <T> void executeForList(HttpMethod method, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.executeForList(method, clazz);
        });
    }

    public <T> void getForObject(IHttpDecoder<String, T> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForObject(decoder);
        });
    }

    public <T> void getForObject(String path, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForObject(path, clazz);
        });
    }

    public <T> void getForObject(Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForObject(clazz);
        });
    }

    public <T> void getForPageInfo(Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForPageInfo(clazz);
        });
    }

    public <T> void getForList(Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForList(clazz);
        });
    }

    public <T> void getForList(IHttpDecoder<String, List<T>> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForList(decoder);
        });
    }

    public <T> void getForList(String path, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.getForList(path, clazz);
        });
    }

    public void get() throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.get();
        });
    }

    public <T> void postForObject(IHttpDecoder<String, T> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForObject(decoder);
        });
    }

    public <T> void postForObject(Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForObject(clazz);
        });
    }

    public <T> void postForPageInfo(Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForPageInfo(clazz);
        });
    }

    public <T> void postForObject(String path, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForObject(path, clazz);
        });
    }

    public <T> void postForList(IHttpDecoder<String, List<T>> decoder) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForList(decoder);
        });
    }

    public <T> void postForList(Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForList(clazz);
        });
    }

    public <T> void postForList(String path, Class<T> clazz) throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.postForList(path, clazz);
        });
    }

    public void post() throws Exception {
        this.futureHelperResolver.supplyAsync(this.name, () -> {
            return this.httpClientResolver.post();
        });
    }
}
