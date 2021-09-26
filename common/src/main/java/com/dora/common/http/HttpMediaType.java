package com.dora.common.http;

import java.util.Objects;
import okhttp3.MediaType;

public class HttpMediaType {
    public static final HttpMediaType APPLICATION_FORM_URLENCODED_VALUE = parse("application/x-www-form-urlencoded");
    public static final HttpMediaType APPLICATION_JSON = parse("application/json");
    public static final HttpMediaType TEXT_PLAIN = parse("text/plain");
    private String type;

    public static HttpMediaType parse(String value) {
        if (value != null && value.length() != 0) {
            MediaType media = MediaType.parse(value);
            return new HttpMediaType(media.toString());
        } else {
            return null;
        }
    }

    private HttpMediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            HttpMediaType that = (HttpMediaType)o;
            return Objects.equals(this.type, that.type);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.type});
    }
}
