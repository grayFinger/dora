package com.dora.common.http;


import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.dora.common.exception.BusinessException;
import com.dora.common.http.decoder.HttpJsonArrayDecoderImpl;
import com.dora.common.http.decoder.HttpJsonDecoderImpl;
import com.dora.common.http.decoder.HttpPageInfoDecoderImpl;
import com.dora.common.utils.StringMap;
import com.dora.common.view.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IHttpClientResolver {
    Logger logger = LoggerFactory.getLogger(IHttpClientResolver.class);

    IHttpClientResolver interceptor(IHttpInterceptor interceptor);

    IHttpClientResolver header(String name, Object value);

    IHttpClientResolver flushCache(boolean flushcache);

    IHttpClientResolver cacheable(boolean cacheable);

    IHttpClientResolver fallbackable(boolean fallbackable);

    boolean cacheable();

    boolean flushCache();

    boolean fallbackable();

    boolean enableLoggerBody();

    IHttpClientResolver enableLoggerBody(boolean enableLoggerBody);

    IHttpClientResolver cache(IHttpCache httpCache);

    IHttpClientResolver cacheKeyGenerator(IHttpCacheKeyGenerator cacheKeyGenerator);

    IHttpCacheKeyGenerator cacheKeyGenerator();

    IHttpCache cache();

    IHttpClientResolver cacheKey(String cacheKey);

    String cacheKey();

    StringMap params();

    Object body();

    IHttpClientResolver expireTime(Long expireTime);

    Long expireTime();

    IHttpClientResolver param(String key, Object value);

    IHttpClientResolver body(Object body);

    IHttpClientResolver contentType(HttpMediaType mediaType);

    IHttpClientResolver charset(String charset);

    IHttpClientResolver baseUrl(String baseUrl);

    <T> IHttpClientResolver fallback(IHttpFallback<T> fallback);

    <T> IHttpFallback<T> fallback();

    String baseUrl();

    IHttpClientResolver url(String url);

    String url();

    <I, T> HttpResponse<T> execute(HttpMethod method, IHttpDecoder<I, T> decoder) throws Exception;

    <T> HttpResponse<String> execute(HttpMethod method) throws Exception;

    void download(OutputStream os) throws Exception;

    default String executeForString(HttpMethod method) throws Exception {
        HttpResponse<String> httpResponse = this.execute(method);
        if (httpResponse.isSuccess()) {
            return (String)httpResponse.getResult();
        } else {
            throw new BusinessException(httpResponse.getCode() + "", httpResponse.getMessage());
        }
    }

    default String executeCacheForString(HttpMethod method) throws Exception {
        String value = null;
        IHttpCache httpCache = null;
        String cacheKey = null;
        String url = null;
        if (method == HttpMethod.GET && this.cacheable() && !this.flushCache()) {
            httpCache = this.cache();
            cacheKey = this.cacheKey();
            url = this.getFullUrl();
            if (httpCache != null) {
                if (cacheKey == null) {
                    IHttpCacheKeyGenerator cacheKeyGenerator = this.cacheKeyGenerator();
                    cacheKey = cacheKeyGenerator.generateCacheKey(url, this.params(), this.body());
                }

                if (cacheKey != null) {
                    this.cacheKey(cacheKey);

                    try {
                        value = (String)httpCache.getCache(cacheKey, String.class);
                        logger.debug("调用接口[{}]，使用key[{}]从缓存中获取接口数据：{}", new Object[]{url, cacheKey, value});
                    } catch (Exception var8) {
                        logger.error("从缓存获取接口[{}]的数据失败，key={}", new Object[]{url, cacheKey, var8});
                    }
                }
            }
        }

        if (value == null) {
            value = this.executeForString(method);
        }

        if (value != null && method == HttpMethod.GET && this.cacheable() && httpCache != null && cacheKey != null) {
            try {
                httpCache.setCache(cacheKey, value, this.expireTime());
            } catch (Exception var7) {
                logger.error("缓存接口[{}]的数据失败，key={}", new Object[]{url, cacheKey, var7});
            }
        }

        return value;
    }

    default <T> T executeForObject(HttpMethod method, IHttpDecoder<String, T> decoder) throws Exception {
        if (decoder == null) {
            throw new BusinessException("8000", "回调接口不能为空");
        } else {
            String result = this.executeCacheForString(method);
            return result != null ? decoder.execute(result) : null;
        }
    }

    default <T> PageInfo<T> executeForPageInfo(HttpMethod method, IHttpDecoder<String, PageInfo<T>> decoder) throws Exception {
        if (decoder == null) {
            throw new BusinessException("8000", "回调接口不能为空");
        } else {
            String result = this.executeCacheForString(method);
            return result != null ? (PageInfo)decoder.execute(result) : null;
        }
    }

    default <T> List<T> executeForList(HttpMethod method, IHttpDecoder<String, List<T>> decoder) throws Exception {
        if (decoder == null) {
            throw new BusinessException("8000", "回调接口不能为空");
        } else {
            String result = this.executeCacheForString(method);
            return result != null ? (List)decoder.execute(result) : null;
        }
    }

    default <T> T executeForObject(HttpMethod method, String path, Class<T> clazz) throws Exception {
        return (T) this.executeForObject(method, (IHttpDecoder)(new HttpJsonDecoderImpl(clazz, path)));
    }

    default <T> T executeForObject(HttpMethod method, Class<T> clazz) throws Exception {
        return (T) this.executeForObject(method, (IHttpDecoder)(new HttpJsonDecoderImpl(clazz)));
    }

    default <T> PageInfo<T> executeForPageInfo(HttpMethod method, Class<T> clazz) throws Exception {
        return (PageInfo)this.executeForObject(method, (IHttpDecoder)(new HttpPageInfoDecoderImpl(clazz)));
    }

    default <T> List<T> executeForList(HttpMethod method, String path, Class<T> clazz) throws Exception {
        return (List)this.executeForObject(method, (IHttpDecoder)(new HttpJsonArrayDecoderImpl(clazz, path)));
    }

    default <T> List<T> executeForList(HttpMethod method, Class<T> clazz) throws Exception {
        return (List)this.executeForObject(method, (IHttpDecoder)(new HttpJsonArrayDecoderImpl(clazz)));
    }

    default <T> T getForObject(IHttpDecoder<String, T> decoder) throws Exception {
        return this.executeForObject(HttpMethod.GET, decoder);
    }

    default <T> T getForObject(String path, Class<T> clazz) throws Exception {
        return this.executeForObject(HttpMethod.GET, path, clazz);
    }

    default <T> T getForObject(Class<T> clazz) throws Exception {
        return this.executeForObject(HttpMethod.GET, clazz);
    }

    default <T> PageInfo<T> getForPageInfo(Class<T> clazz) throws Exception {
        return this.executeForPageInfo(HttpMethod.GET, clazz);
    }

    default <T> List<T> getForList(Class<T> clazz) throws Exception {
        return this.executeForList(HttpMethod.GET, clazz);
    }

    default <T> List<T> getForList(IHttpDecoder<String, List<T>> decoder) throws Exception {
        return this.executeForList(HttpMethod.GET, decoder);
    }

    default <T> List<T> getForList(String path, Class<T> clazz) throws Exception {
        return this.executeForList(HttpMethod.GET, path, clazz);
    }

    default String get() throws Exception {
        return (String)this.getForObject(String.class);
    }

    default <T> T postForObject(IHttpDecoder<String, T> decoder) throws Exception {
        return this.executeForObject(HttpMethod.POST, decoder);
    }

    default <T> T postForObject(Class<T> clazz) throws Exception {
        return this.executeForObject(HttpMethod.POST, clazz);
    }

    default <T> PageInfo<T> postForPageInfo(Class<T> clazz) throws Exception {
        return this.executeForPageInfo(HttpMethod.POST, clazz);
    }

    default <T> T postForObject(String path, Class<T> clazz) throws Exception {
        return this.executeForObject(HttpMethod.POST, path, clazz);
    }

    default <T> List<T> postForList(IHttpDecoder<String, List<T>> decoder) throws Exception {
        return this.executeForList(HttpMethod.POST, decoder);
    }

    default <T> List<T> postForList(Class<T> clazz) throws Exception {
        return this.executeForList(HttpMethod.POST, clazz);
    }

    default <T> List<T> postForList(String path, Class<T> clazz) throws Exception {
        return this.executeForList(HttpMethod.POST, path, clazz);
    }

    default String post() throws Exception {
        return (String)this.postForObject(String.class);
    }

    String getFullUrl() throws IOException;
}
