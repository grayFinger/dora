package com.dora.common.http.generator;


import com.dora.common.http.IHttpCacheKeyGenerator;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.MD5;
import com.dora.common.utils.StringMap;

public class DefaultHttpCacheKeyGenerator implements IHttpCacheKeyGenerator {
    public DefaultHttpCacheKeyGenerator() {
    }

    public String generateCacheKey(String url, StringMap params, Object body) {
        return this.getMD5(url, params, body);
    }

    private String getMD5(String url, StringMap params, Object body) {
        return MD5.MD5Encode(url + "@" + JsonUtils.toJson(params) + "@" + JsonUtils.toJson(body));
    }
}
