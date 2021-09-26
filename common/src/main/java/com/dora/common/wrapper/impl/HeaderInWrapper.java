package com.dora.common.wrapper.impl;

import com.alibaba.fastjson.parser.Feature;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.dora.common.exception.BusinessException;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.StringMap;
import com.dora.common.wrapper.IInWrapper;
import org.springframework.http.HttpEntity;

public class HeaderInWrapper implements IInWrapper {
    public HeaderInWrapper() {
    }

    public Object unwrap(Object obj) {
        return obj;
    }

    public void valid(HttpEntity entity, Object obj) {
        String code = entity.getHeaders().getFirst("code");
        if (code != null && !"0".equals(code)) {
            String msg = entity.getHeaders().getFirst("msg");
            if (msg != null) {
                try {
                    msg = URLDecoder.decode(msg, "UTF-8");
                } catch (UnsupportedEncodingException var6) {
                    var6.printStackTrace();
                }
            }

            if (msg == null) {
                msg = "系统异常";
            }

            throw new BusinessException(code, msg, this.parseObject(obj));
        }
    }

    protected Object parseObject(Object obj) {
        if (obj != null && obj instanceof String) {
            String str = (String)obj;
            return str.startsWith("[") ? JsonUtils.fromJsonList(str, StringMap.class) : JsonUtils.fromJson(str, StringMap.class, new Feature[0]);
        } else {
            return null;
        }
    }
}
