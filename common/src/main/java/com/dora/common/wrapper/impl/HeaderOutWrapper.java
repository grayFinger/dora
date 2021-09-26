package com.dora.common.wrapper.impl;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dora.common.wrapper.IOutWrapper;
import org.apache.commons.lang.StringUtils;

public class HeaderOutWrapper implements IOutWrapper {
    public HeaderOutWrapper() {
    }

    public Object wrapper(HttpServletRequest request, HttpServletResponse response, String code, String msg, Object data) throws IOException {
        if (response != null) {
            response.setHeader("code", code);
            if (msg == null) {
                response.setHeader("msg", URLEncoder.encode("系统异常", "UTF-8"));
            } else {
                response.setHeader("msg", URLEncoder.encode(msg, "UTF-8"));
            }
        }

        if (!StringUtils.isEmpty(code) && "0".equalsIgnoreCase(code) && data == null) {
            data = "";
        }

        return data;
    }
}
