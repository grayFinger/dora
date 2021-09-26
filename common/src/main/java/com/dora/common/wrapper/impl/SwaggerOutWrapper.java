package com.dora.common.wrapper.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwaggerOutWrapper extends CommonOutWrapper {
    public SwaggerOutWrapper() {
    }

    public Object wrapperSuccess(HttpServletRequest request, HttpServletResponse response, Object data) {
        return data == null ? "" : data;
    }
}
