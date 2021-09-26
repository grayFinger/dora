package com.dora.common.http;


import com.dora.common.utils.StringMap;

public class HttpRequest {
    private StringMap attrs = new StringMap();
    private String url;
    private StringMap headers;
    private StringMap params;
    private HttpMethod method;
    private Object body;
    protected HttpMediaType contentType;
    private boolean change;

    public HttpRequest() {
        this.method = HttpMethod.GET;
    }

    public String url() {
        return this.url;
    }

    public HttpRequest url(String url) {
        this.url = url;
        this.change = true;
        return this;
    }

    public HttpRequest contentType(HttpMediaType contentType) {
        if (this.contentType == null && contentType != null) {
            this.contentType = contentType;
        }

        return this;
    }

    public HttpMediaType contentType() {
        return this.contentType;
    }

    public StringMap headers() {
        return this.headers;
    }

    private StringMap setValue(StringMap map, String name, Object value) {
        if (name != null) {
            if (map == null) {
                map = new StringMap();
            }

            if (value == null) {
                map.remove(name);
            } else {
                map.put(name, value);
            }

            this.change = true;
        }

        return map;
    }

    public HttpRequest header(String name, Object value) {
        this.headers = this.setValue(this.headers, name, value);
        return this;
    }

    public StringMap params() {
        return this.params;
    }

    public HttpRequest param(String name, Object value) {
        this.params = this.setValue(this.params, name, value);
        return this;
    }

    public Object body() {
        return this.body;
    }

    public HttpRequest body(Object body) {
        this.body = body;
        this.change = true;
        return this;
    }

    public boolean change() {
        return this.change;
    }

    HttpRequest change(boolean change) {
        this.change = change;
        return this;
    }

    public HttpRequest method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public HttpMethod method() {
        return this.method;
    }

    public StringMap getAttrs() {
        return this.attrs;
    }

    public void setAttrs(StringMap attrs) {
        this.attrs = attrs;
    }
}
