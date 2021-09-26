package com.dora.common.json;

import com.alibaba.fastjson.parser.Feature;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.dora.common.annotation.JSON;
import com.dora.common.annotation.JSONS;
import com.dora.common.config.JsonConfig;
import com.dora.common.utils.JsonUtils;
import com.dora.common.utils.StringMap;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {
    private final HandlerMethodReturnValueHandler delegate;
    private JsonConfig jsonConfig;

    public ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate, JsonConfig jsonConfig) {
        this.delegate = delegate;
        this.jsonConfig = jsonConfig;
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(JSON.class) != null || this.delegate.supportsReturnType(returnType);
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        Annotation[] annos = returnType.getMethodAnnotations();
        if (annos != null && annos.length != 0 && returnValue != null) {
            CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer(this.jsonConfig);
            boolean hasJSONAnnotation = false;
            Iterator var8 = Arrays.asList(annos).iterator();

            while(var8.hasNext()) {
                Annotation annotation = (Annotation)var8.next();
                if (annotation instanceof JSON) {
                    hasJSONAnnotation = true;
                    jsonSerializer.filter((JSON)annotation);
                } else if (annotation instanceof JSONS) {
                    hasJSONAnnotation = true;
                    jsonSerializer.filter((JSONS)annotation);
                }
            }

            if (returnValue != null && hasJSONAnnotation) {
                String json = jsonSerializer.toJson(returnValue);
                Object value = json;
                if (json != null && json.length() > 0) {
                    if (!returnValue.getClass().isArray() && !Collection.class.isAssignableFrom(returnValue.getClass())) {
                        value = JsonUtils.fromJson(json, StringMap.class, new Feature[0]);
                    } else {
                        value = JsonUtils.fromJsonList(json, StringMap.class);
                    }
                }

                this.delegate.handleReturnValue(value, returnType, mavContainer, webRequest);
            } else {
                this.delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            }
        } else {
            this.delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }

    }
}
