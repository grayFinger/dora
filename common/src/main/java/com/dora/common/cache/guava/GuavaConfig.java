package com.dora.common.cache.guava;

public class GuavaConfig {
    private int expires = -1;

    public int getExpires() {
        return this.expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }
}
