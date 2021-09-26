package com.dora.common.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public RequestUtils() {
    }

    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.indexOf(",") != -1) {
            ip = ip.split(",")[0];
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.indexOf(":") != -1) {
            ip = request.getRemoteAddr();
        }

        if (ip.indexOf(":") != -1) {
            ip = "127.0.0.1";
        }

        return ip;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = null;
        if (request != null) {
            userAgent = request.getHeader("User-Agent");
        }

        return userAgent;
    }

    public static String getReferer(HttpServletRequest request) {
        String referer = null;
        if (request != null) {
            referer = request.getHeader("Referer");
        }

        return referer;
    }

    public static boolean isSwagger(HttpServletRequest request) {
        String referer = getReferer(request);
        return referer != null && (referer.endsWith("/swagger-ui.html") || referer.endsWith("/document.html"));
    }

    public static boolean isNoFilterUrl(HttpServletRequest request) {
        return "true".equals(request.getAttribute("_NO_FILTER_URL_"));
    }

    public static boolean isNoFilterReferer(HttpServletRequest request) {
        return "true".equals(request.getAttribute("_NO_FILTER_REFERER_"));
    }

    public static boolean isNoFilter(HttpServletRequest request) {
        return "true".equals(request.getAttribute("_NO_FILTER_"));
    }
}
