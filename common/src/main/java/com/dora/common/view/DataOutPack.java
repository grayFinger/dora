package com.dora.common.view;

import java.util.HashMap;
import java.util.Map;

import com.dora.common.exception.ICodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataOutPack extends HashMap<String, Object> {
    private static Logger logger = LoggerFactory.getLogger(DataOutPack.class);

    public DataOutPack() {
        this("0", "");
    }

    public DataOutPack(Map<String, Object> map) {
        this.setData(map);
    }

    public DataOutPack(String code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

    public DataOutPack(Throwable t) {
        this();
        this.setException(t);
    }

    public DataOutPack(Object data) {
        this();
        this.setData(data);
    }

    public DataOutPack(String code, String msg, Object data) {
        this(code, msg);
        this.setData(data);
    }

    public DataOutPack setCode(String code) {
        this.put("code", code);
        return this;
    }

    public DataOutPack setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public DataOutPack setException(Throwable e) {
        Throwable t;
        for(t = e; t.getCause() != null && !t.getCause().equals(t); t = t.getCause()) {
        }

        if (t instanceof ICodeException) {
            this.setCode(((ICodeException)t).getBusiCode());
            if ("8000".equals(this.getCode())) {
                this.setMsg("系统忙，请稍后再试");
            } else {
                this.setMsg(t.getMessage());
            }

            logger.warn(e.getMessage());
        } else {
            logger.error(e.getMessage(), e);
            this.setCode("8000");
            this.setMsg("系统忙，请稍后再试");
        }

        return this;
    }

    public DataOutPack setData(Map<String, Object> data) {
        if (data.containsKey("code")) {
            this.setCode((String)data.remove("code"));
        }

        if (data.containsKey("msg")) {
            this.setMsg((String)data.remove("msg"));
        }

        this.putAll(data);
        return this;
    }

    public DataOutPack addData(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public DataOutPack setData(Object data) {
        this.put("data", data);
        return this;
    }

    public String getCode() {
        String code = (String)this.get("code");
        if (code == null) {
            code = "0";
        }

        return code;
    }

    public String getMessage() {
        String message = (String)this.get("msg");
        if (message == null) {
            message = "";
        }

        return message;
    }
}
