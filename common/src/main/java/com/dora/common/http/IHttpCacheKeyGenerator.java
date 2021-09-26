package com.dora.common.http;


import com.dora.common.utils.StringMap;

public interface IHttpCacheKeyGenerator {
    String generateCacheKey(String url, StringMap params, Object body);
}
