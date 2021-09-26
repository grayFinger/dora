package com.dora.common.wrapper.impl;


import com.dora.common.utils.StringMap;
import com.dora.common.wrapper.IOutWrapper;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonOutWrapper implements IOutWrapper {
    public CommonOutWrapper() {
    }

    public Object wrapper(HttpServletRequest request, HttpServletResponse response, String code, String msg, Object data) throws IOException {
        StringMap map = new StringMap();
        map.put("code", code);
        map.put("msg", msg);
        if (data != null) {
            map.put("data", data);
        }

        return map;
    }
}
