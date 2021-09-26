package com.dora.common.http.okhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import com.dora.common.exception.BusinessException;
import com.dora.common.http.*;
import com.dora.common.http.okhttp.interceptor.OKHttp3HttpInterceptor;
import com.dora.common.utils.ClassUtils;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.StringMap;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

public class OkHttp3ClientResolver extends AbstractHttpClientResolver {
    private static Logger logger = LoggerFactory.getLogger(OkHttp3ClientResolver.class);

    private OkHttpClient httpClient;

    private OKHttp3HttpInterceptor httpInterceptor;

    public OkHttp3ClientResolver(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected RequestBody getRequestBody() {
        HttpRequest request = getHttpRequest();
        HttpMethod method = getHttpRequest().method();
        if (method == HttpMethod.GET || method == HttpMethod.DELETE)
            return null;
        if (request.body() == null) {
            if (request.contentType() == HttpMediaType.APPLICATION_FORM_URLENCODED_VALUE) {
                FormBody.Builder builder = new FormBody.Builder();
                if (request.params() != null && request.params().size() > 0)
                    for (String key : request.params().keySet())
                        builder.add(key, request.params().getString(key));
                return (RequestBody)builder.build();
            }
            return FormBody.create(MediaType.parse(request.contentType().getType()), "");
        }
        return FormBody.create(MediaType.parse(request.contentType().getType()), JsonUtils.toJson(request.body()));
    }

    public Request getRequest() throws IOException {
        Request.Builder builder = new Request.Builder();
        HttpRequest request = getHttpRequest();
        if (request.method() == HttpMethod.POST || request.method() == HttpMethod.PUT) {
            if (request.contentType() == null)
                request.contentType(HttpMediaType.APPLICATION_FORM_URLENCODED_VALUE);
            builder.header("Content-Type", request.contentType().toString());
        }
        if (request.headers() != null && request.headers().size() > 0)
            for (String key : request.headers().keySet()) {
                if (!"accept-encoding".equals(key.toLowerCase()))
                    builder.addHeader(key, request.headers().getString(key));
            }
        HttpMethod method = getHttpRequest().method();
        String fullUrl = getFullUrl();
        request.url(fullUrl);
        change(false);
        RequestBody requestBody = getRequestBody();
        builder.url(fullUrl);
        builder.method(method.getName(), requestBody);
        return builder.build();
    }

    public String getLoggerBody(ResponseBody responseBody) throws IOException {
        if (responseBody == null)
            return null;
        if (!this.enableLoggerBody)
            return null;
        InputStream in = responseBody.byteStream();
        if (in == null)
            return null;
        try {
            return StreamUtils.copyToString(responseBody.byteStream(), Charset.forName(this.charset));
        } finally {
            responseBody.close();
        }
    }

    public <I, T> HttpResponse<T> execute(HttpMethod method, final IHttpDecoder<I, T> decoder) throws Exception {
        getHttpRequest().method(method);
        final Request request = getRequest();
        final Response[] responses = new Response[1];
        T body = null;
        if (this.fallback == null || !isHystrixEnabled()) {
            responses[0] = execute(request);
            body = parseResponseBody(responses[0], decoder);
        } else {
            HttpHystrixCommand<T> command = new HttpHystrixCommand<T>(this.fallback, (IHttpClientResolver)this, decoder) {
                protected T run() throws Exception {
                    responses[0] = OkHttp3ClientResolver.this.execute(request);
                    T body = OkHttp3ClientResolver.this.parseResponseBody(responses[0], decoder);
                    try {
                        OkHttp3ClientResolver.this.fallback.onSuccess((IHttpClientResolver)OkHttp3ClientResolver.this, body);
                    } catch (Exception e) {
                        OkHttp3ClientResolver.logger.error("缓存接口[{}]的数据失败", request.url(), e);
                    }
                    return body;
                }
            };
            body = (T)command.execute();
        }
        HttpResponse<T> httpResponse = null;
        if (responses.length > 0)
            try (Response response = responses[0]) {
                httpResponse = getHttpResponse(response, body);
            } catch (Throwable t) {
                if (this.httpInterceptor != null)
                    this.httpInterceptor.postHandler((IHttpClientResolver)this, getHttpRequest(), null, t);
                throw new BusinessException(t);
            }
        if (httpResponse == null)
            httpResponse = getHttpResponse((Response)null, body);
        if (this.httpInterceptor != null)
            this.httpInterceptor.postHandler((IHttpClientResolver)this, getHttpRequest(), httpResponse, null);
        return httpResponse;
    }

    private <I, T> T parseResponseBody(Response response, IHttpDecoder<I, T> decoder) throws Exception {
        T body = null;
        ResponseBody responseBody = null;
        if (response != null && (responseBody = response.body()) != null) {
            Class<?> genericClass = ClassUtils.getGenericClass(decoder.getClass());
            if (InputStream.class.isAssignableFrom(genericClass)) {
                try (InputStream is = responseBody.byteStream()) {
                    body = (T)decoder.execute((I) is);
                }
            } else if (String.class.isAssignableFrom(genericClass)) {
                body = (T)decoder.execute((I) responseBody.string());
            } else if (Byte[].class.isAssignableFrom(genericClass)) {
                body = (T)decoder.execute((I) responseBody.bytes());
            } else if (Reader.class.isAssignableFrom(genericClass)) {
                body = (T)decoder.execute((I) responseBody.charStream());
            } else if (ResponseBody.class.isAssignableFrom(genericClass)) {
                body = (T)decoder.execute((I) responseBody);
            } else {
                throw new BusinessException("8000", "不支持回调函数指定的类型"+ genericClass);
            }
        }
        return body;
    }

    private Response execute(Request request) throws Exception {
        this.httpInterceptor = new OKHttp3HttpInterceptor(this.interceptors);
        if (!this.httpInterceptor.preHandler((IHttpClientResolver)this, getHttpRequest()))
            return null;
        Call call = this.httpClient.newCall(request);
        try {
            return call.execute();
        } catch (Exception e) {
            if (call != null)
                try {
                    call.cancel();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            throw e;
        } finally {
            logger.debug("connectionCount==============>{}", Integer.valueOf(this.httpClient.connectionPool().connectionCount()));
            logger.debug("idleConnectionCount==============>{}", Integer.valueOf(this.httpClient.connectionPool().idleConnectionCount()));
        }
    }

    public <T> HttpResponse getHttpResponse(Response response, T body) throws IOException {
        StringMap httpHeaders = new StringMap();
        if (response == null) {
            if (body == null)
                return new HttpResponse(httpHeaders, Integer.valueOf(200), "", true, null);
            return new HttpResponse(httpHeaders, Integer.valueOf(200), "", true, body);
        }
        Headers headers = response.headers();
        if (headers != null && headers.size() > 0)
            for (String name : headers.names()) {
                List<String> values = headers.values(name);
                if (values != null && values.size() > 0)
                    httpHeaders.put(name, values.get(0));
            }
        return new HttpResponse(httpHeaders, Integer.valueOf(response.code()), response.message(), response.isSuccessful(), body);
    }
}