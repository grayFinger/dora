package com.dora.common.http.utils;

import org.apache.commons.lang.StringUtils;

public class URLUtils {
    public URLUtils() {
    }

    public static String getFullUrl(String baseUrl, String url) {
        if (baseUrl == null) {
            return url;
        } else if (url == null) {
            return baseUrl;
        } else {
            return !url.startsWith("http://") && !url.startsWith("https://") ? StringUtils.strip(baseUrl, "/") + "/" + StringUtils.strip(url, "/") : url;
        }
    }
}
