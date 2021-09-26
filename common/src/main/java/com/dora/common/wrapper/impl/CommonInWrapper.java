package com.dora.common.wrapper.impl;

import com.alibaba.fastjson.parser.Feature;

import com.dora.common.exception.BusinessException;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.StringMap;
import com.dora.common.wrapper.IInWrapper;
import org.springframework.http.HttpEntity;

public class CommonInWrapper implements IInWrapper {
    public CommonInWrapper() {
    }

    public void valid(HttpEntity entity, Object obj) {
        if (obj != null && obj instanceof String) {
            String str = (String)obj;
            StringMap map = (StringMap) JsonUtils.fromJson(str, StringMap.class, new Feature[0]);
            String code = map.getString("code");
            if (code != null && !"0".equals(code)) {
                String msg = map.getString("msg");
                if (msg == null) {
                    msg = "系统异常";
                }

                throw new BusinessException(code, msg, map.get("data"));
            }
        }

    }

    public Object unwrap(Object obj) {
        if (obj != null && obj instanceof String) {
            String str = (String)obj;
            StringMap map = (StringMap)JsonUtils.fromJson(str, StringMap.class, new Feature[0]);
            return map.get("data");
        } else {
            return obj;
        }
    }
}
