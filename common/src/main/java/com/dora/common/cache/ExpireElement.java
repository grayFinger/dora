//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.cache;

import java.io.Serializable;

public class ExpireElement implements Serializable {
    private Object object;
    private Long createTime;
    private Long expire;

    public ExpireElement(Object object, Long expire) {
        this.expire = -1L;
        this.object = object;
        this.expire = expire;
        this.createTime = System.currentTimeMillis();
    }

    public ExpireElement(Object object) {
        this(object, -1L);
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public Long getExpire() {
        return this.expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public boolean isExpired() {
        return this.expire != -1L && (System.currentTimeMillis() - this.createTime) / 1000L > this.expire;
    }

    public Long getRemainTime() {
        if (this.expire == -1L) {
            return -1L;
        } else {
            Long time = (System.currentTimeMillis() - this.createTime) / 1000L;
            return time > this.expire ? -2L : this.expire - time;
        }
    }
}
