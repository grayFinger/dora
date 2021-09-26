package com.dora.common.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dora.common.http.decoder.HttpInputStreamDecoderImpl;
import com.dora.common.http.decoder.HttpStringDecoderImpl;
import com.dora.common.http.generator.DefaultHttpCacheKeyGenerator;
import com.dora.common.http.utils.URLUtils;
import com.dora.common.utils.StringMap;
import org.apache.commons.io.IOUtils;

public abstract class AbstractHttpClientResolver implements IHttpClientResolver {
    protected List<IHttpInterceptor> interceptors = null;
    private HttpRequest request;
    protected String charset = "UTF-8";
    protected Long expireTime = 60L;
    protected Boolean flushCache = false;
    protected Boolean cacheable = false;
    protected Boolean fallbackable = true;
    protected IHttpCache httpCache;
    protected IHttpCacheKeyGenerator cacheKeyGenerator = new DefaultHttpCacheKeyGenerator();
    protected String cacheKey;
    protected IHttpFallback fallback;
    protected String baseUrl;
    protected String url;
    protected String fullUrl;
    protected boolean hystrixEnabled = true;
    protected boolean enableLoggerBody = true;

    public AbstractHttpClientResolver() {
        this.header("User-Agent", "common-http/1.0.0");
        this.header("Accept", "*/*");
        this.header("Cache-Control", "no-cache");
        this.header("Connection", "keep-alive");
    }

    public IHttpClientResolver interceptor(IHttpInterceptor interceptor) {
        if (interceptor == null) {
            return this;
        } else {
            if (this.interceptors == null) {
                this.interceptors = new ArrayList();
            }

            this.interceptors.add(interceptor);
            return this;
        }
    }

    public IHttpClientResolver header(String name, Object value) {
        if (name != null && value != null && "content-type".equals(name.toLowerCase())) {
            if (value instanceof String) {
                this.getHttpRequest().contentType(HttpMediaType.parse((String)value));
            } else if (value instanceof HttpMediaType) {
                this.getHttpRequest().contentType((HttpMediaType)value);
            }
        } else {
            this.getHttpRequest().header(name, value);
        }

        return this;
    }

    public <T> IHttpClientResolver fallback(IHttpFallback<T> fallback) {
        this.fallback = fallback;
        return this;
    }

    public <T> IHttpFallback<T> fallback() {
        return this.fallback;
    }

    public StringMap params() {
        return this.getHttpRequest().params();
    }

    public boolean flushCache() {
        return this.flushCache;
    }

    public Long expireTime() {
        return this.expireTime;
    }

    public IHttpCache cache() {
        return this.httpCache;
    }

    public String cacheKey() {
        return this.cacheKey;
    }

    public IHttpClientResolver cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return this;
    }

    public Object body() {
        return this.getHttpRequest().body();
    }

    public IHttpClientResolver flushCache(boolean flushcache) {
        this.flushCache = flushcache;
        return this;
    }

    public boolean cacheable() {
        return this.cacheable;
    }

    public IHttpClientResolver cacheable(boolean cacheable) {
        this.cacheable = cacheable;
        return this;
    }

    public IHttpClientResolver expireTime(Long expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public IHttpClientResolver param(String name, Object value) {
        this.getHttpRequest().param(name, value);
        return this;
    }

    public IHttpClientResolver body(Object body) {
        this.getHttpRequest().body(body);
        return this;
    }

    public IHttpCacheKeyGenerator cacheKeyGenerator() {
        return this.cacheKeyGenerator;
    }

    public IHttpClientResolver cacheKeyGenerator(IHttpCacheKeyGenerator cacheKeyGenerator) {
        this.cacheKeyGenerator = cacheKeyGenerator;
        return this;
    }

    public IHttpClientResolver cache(IHttpCache httpCache) {
        this.httpCache = httpCache;
        return this;
    }

    public IHttpClientResolver contentType(HttpMediaType contentType) {
        this.getHttpRequest().contentType(contentType);
        return this;
    }

    public IHttpClientResolver charset(String charset) {
        this.charset = charset;
        return this;
    }

    public HttpRequest getHttpRequest() {
        if (this.request == null) {
            this.request = new HttpRequest();
        }

        return this.request;
    }

    public IHttpClientResolver baseUrl(String baseUrl) {
        if (baseUrl != null && !baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }

        this.baseUrl = baseUrl;
        return this;
    }

    public String baseUrl() {
        return this.baseUrl;
    }

    public IHttpClientResolver url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return this.url;
    }

    public String getFullUrl() throws IOException {
        if (this.fullUrl == null) {
            HttpMethod method = this.getHttpRequest().method();
            boolean flag = this.getHttpRequest().params() != null && this.getHttpRequest().params().size() > 0 && (method == HttpMethod.GET || method == HttpMethod.DELETE || this.body() != null || this.getHttpRequest().contentType == HttpMediaType.APPLICATION_JSON || this.getHttpRequest().contentType == HttpMediaType.TEXT_PLAIN);
            this.url = URLUtils.getFullUrl(this.baseUrl, this.url);
            if (flag) {
                StringBuilder builder = new StringBuilder(this.url);
                String op = "&";
                if (this.url.indexOf("?") == -1) {
                    op = "?";
                }

                for(Iterator var5 = this.getHttpRequest().params().keySet().iterator(); var5.hasNext(); op = "&") {
                    String key = (String)var5.next();
                    builder.append(op).append(key).append("=").append(URLEncoder.encode(this.getHttpRequest().params().getString(key), this.charset));
                }

                return builder.toString();
            }

            this.fullUrl = this.url;
        }

        return this.fullUrl;
    }

    public boolean isHystrixEnabled() {
        return this.hystrixEnabled;
    }

    public void setHystrixEnabled(boolean hystrixEnabled) {
        this.hystrixEnabled = hystrixEnabled;
    }

    public boolean change() {
        return this.getHttpRequest().change();
    }

    protected IHttpClientResolver change(boolean change) {
        this.getHttpRequest().change(change);
        return this;
    }

    public HttpResponse<String> execute(HttpMethod method) throws Exception {
        return this.execute(method, new HttpStringDecoderImpl(this.charset));
    }

    public void download(OutputStream os) throws Exception {
        HttpResponse<InputStream> response = this.execute(HttpMethod.GET, new HttpInputStreamDecoderImpl());
        IOUtils.copy((InputStream)response.getResult(), os);
    }

    public boolean fallbackable() {
        return this.fallbackable;
    }

    public IHttpClientResolver fallbackable(boolean fallbackable) {
        this.fallbackable = fallbackable;
        return this;
    }

    public boolean enableLoggerBody() {
        return this.enableLoggerBody;
    }

    public IHttpClientResolver enableLoggerBody(boolean enableLoggerBody) {
        this.enableLoggerBody = enableLoggerBody;
        return this;
    }
}
