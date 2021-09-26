package com.dora.common.http.okhttp.interceptor;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Iterator;
import java.util.List;

import com.dora.common.exception.BusinessException;
import com.dora.common.http.HttpRequest;
import com.dora.common.http.HttpResponse;
import com.dora.common.http.IHttpClientResolver;
import com.dora.common.http.IHttpInterceptor;
import com.dora.common.http.okhttp.OkHttp3ClientResolver;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class OKHttpInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private List<IHttpInterceptor> interceptors;

    private OkHttp3ClientResolver resolver;

    public OKHttpInterceptor(List<IHttpInterceptor> interceptors, OkHttp3ClientResolver resolver) {
        this.interceptors = interceptors;
        this.resolver = resolver;
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = null;
        if (this.interceptors != null && this.interceptors.size() > 0) {
            Throwable throwable = null;
            try {
                for (IHttpInterceptor interceptor : this.interceptors) {
                    if (!interceptor.preHandler((IHttpClientResolver)this.resolver, this.resolver.getHttpRequest()))
                        throw new BusinessException("8000", "系统内部错误");
                }
                Request request = chain.request();
                if (this.resolver.change())
                    request = this.resolver.getRequest();
                response = chain.proceed(request);
            } catch (Throwable t) {
                throwable = t;
                throw new BusinessException(t);
            } finally {
                if (response != null && !bodyEncoded(response.headers())) {
                    String body = null;
                    ResponseBody responseBody = null;
                    if (response != null && (responseBody = response.body()) != null &&
                            this.resolver.enableLoggerBody()) {
                        BufferedSource source = responseBody.source();
                        source.request(Long.MAX_VALUE);
                        Buffer buffer = source.getBuffer();
                        Charset charset = UTF8;
                        MediaType contentType = responseBody.contentType();
                        if (contentType != null)
                            try {
                                charset = contentType.charset(UTF8);
                            } catch (UnsupportedCharsetException e) {
                                return response;
                            }
                        body = buffer.clone().readString(charset);
                    }
                    HttpRequest httpRequest = this.resolver.getHttpRequest();
                    HttpResponse httpResponse = this.resolver.getHttpResponse(response, body);
                    for (IHttpInterceptor interceptor : this.interceptors)
                        interceptor.postHandler((IHttpClientResolver)this.resolver, httpRequest, httpResponse, throwable);
                }
            }
        } else {
            response = chain.proceed(chain.request());
        }
        return response;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return (contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity"));
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = (buffer.size() < 64L) ? buffer.size() : 64L;
            buffer.copyTo(prefix, 0L, byteCount);
            for (int i = 0; i < 16 &&
                    !prefix.exhausted(); i++) {
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint))
                    return false;
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }
}
