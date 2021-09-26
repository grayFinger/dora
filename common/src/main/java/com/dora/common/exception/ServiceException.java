package com.dora.common.exception;

import java.io.Serializable;

public class ServiceException extends RuntimeException implements ICodeException {
    private String busiCode;
    private Object data;

    public ServiceException(String busiCode, String message) {
        super(message);
        this.busiCode = busiCode;
    }

    public ServiceException(String busiCode, String message, Object data) {
        super(message);
        this.busiCode = busiCode;
        this.data = data;
    }

    public ServiceException(Throwable t) {
        super(t.getMessage(), t);
        this.processException(t);
    }

    public ServiceException(String busiCode, Throwable t) {
        super(t.getMessage(), t);
        this.processException(t);
        this.busiCode = busiCode;
    }

    public ServiceException(String busiCode, Throwable t, Object data) {
        super(t.getMessage(), t);
        this.busiCode = busiCode;
        this.data = data;
    }

    public ServiceException(String busiCode, String message, Throwable t) {
        super(message, t);
        this.processException(t);
        this.busiCode = busiCode;
    }

    public ServiceException(String busiCode, String message, Throwable t, Object data) {
        super(message, t);
        this.busiCode = busiCode;
        this.data = data;
    }

    private void processException(Throwable t) {
        if (t instanceof ICodeException) {
            this.busiCode = ((ICodeException)t).getBusiCode();
            this.data = ((ICodeException)t).getData();
        } else {
            this.busiCode = "8000";
        }

    }

    public String getBusiCode() {
        return this.busiCode;
    }

    public Object getData() {
        return this.data;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public void setData(Serializable data) {
        this.data = data;
    }
}
