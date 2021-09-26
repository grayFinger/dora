package com.dora.common.http;


import com.dora.common.utils.StringMap;

public class HttpResponse<T> {
    private StringMap headers;
    private Integer code;
    private String message;
    private boolean success;
    private T result;

    public HttpResponse(StringMap headers, Integer code, String message, boolean success, T result) {
        this.headers = headers;
        this.code = code;
        this.message = message;
        this.success = success;
        this.result = result;
    }

    public StringMap getHeaders() {
        return this.headers;
    }

    public Integer getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }
}
