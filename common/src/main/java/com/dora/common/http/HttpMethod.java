package com.dora.common.http;

public enum HttpMethod {
    GET {
        public String getName() {
            return "GET";
        }
    },
    POST {
        public String getName() {
            return "POST";
        }
    },
    PUT {
        public String getName() {
            return "PUT";
        }
    },
    DELETE {
        public String getName() {
            return "DELETE";
        }
    };

    private HttpMethod() {
    }

    public abstract String getName();
}
