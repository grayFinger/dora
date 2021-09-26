package com.dora.common.http.okhttp.interceptor;

import com.dora.common.http.HttpRequest;
import com.dora.common.http.HttpResponse;
import com.dora.common.http.IHttpClientResolver;
import com.dora.common.http.IHttpInterceptor;

import java.util.Iterator;
import java.util.List;

public class OKHttp3HttpInterceptor implements IHttpInterceptor {
    private List<IHttpInterceptor> interceptors;

    public OKHttp3HttpInterceptor(List<IHttpInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public boolean preHandler(IHttpClientResolver resolver, HttpRequest request) {
        if (this.interceptors != null && this.interceptors.size() > 0) {
            Iterator var3 = this.interceptors.iterator();

            while(var3.hasNext()) {
                IHttpInterceptor interceptor = (IHttpInterceptor)var3.next();
                if (!interceptor.preHandler(resolver, request)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void postHandler(IHttpClientResolver resolver, HttpRequest request, HttpResponse response, Throwable throwable) {
        if (this.interceptors != null && this.interceptors.size() > 0) {
            Iterator var5 = this.interceptors.iterator();

            while(var5.hasNext()) {
                IHttpInterceptor interceptor = (IHttpInterceptor)var5.next();
                interceptor.postHandler(resolver, request, response, throwable);
            }
        }

    }
}
