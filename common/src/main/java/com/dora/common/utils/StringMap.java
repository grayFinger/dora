package com.dora.common.utils;


import com.dora.common.exception.BusinessException;
import com.dora.common.utils.transfer.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringMap extends LinkedHashMap<String, Object> {
    private static Map<Class<?>, IValueTransfer<?>> transferMap = new HashMap();

    public StringMap() {
    }

    public StringMap(String key, Object value) {
        this.put(key, value);
    }

    public StringMap addValue(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public StringMap addIfAbsent(String key, Object value) {
        this.putIfAbsent(key, value);
        return this;
    }

    public StringMap removeValue(String key) {
        this.remove(key);
        return this;
    }

    public StringMap clearAll() {
        this.clear();
        return this;
    }

    public <T> T getValue(String key, Class<T> clazz, T defaultValue) {
        Object value = this.get(key);
        if (value == null) {
            return defaultValue;
        } else if (clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        } else {
            IValueTransfer<T> transfer = (IValueTransfer)transferMap.get(clazz);
            return transfer != null ? transfer.getValue(value) : defaultValue;
        }
    }

    public Long getLong(String key, Long defaultValue) {
        return (Long)this.getValue(key, Long.class, defaultValue);
    }

    public Long getLong(String key) {
        return (Long)this.getValue(key, Long.class, 0L);
    }

    public Integer getInt(String key, Integer defaultValue) {
        return (Integer)this.getValue(key, Integer.class, defaultValue);
    }

    public Integer getInt(String key) {
        return this.getInt(key, 0);
    }

    public String getString(String key, String defaultValue) {
        return (String)this.getValue(key, String.class, defaultValue);
    }

    public String getString(String key) {
        return this.getString(key, (String)null);
    }

    public <T> T getValue(String key, T defaultValue) {
        Object value = this.get(key);
        return value == null ? defaultValue : (T) value;
    }

    public Double getDouble(String key, Double defaultValue) {
        return (Double)this.getValue(key, Double.class, defaultValue);
    }

    public Double getDouble(String key) {
        return (Double)this.getValue(key, 0.0D);
    }

    public <T> T getValue(String key) {
        return (T) this.getValue(key, (Object)null);
    }

    public boolean checkNull(String key, String msg) throws BusinessException {
        Object value = this.get(key);
        if (value == null) {
            throw new BusinessException("7001", msg);
        } else {
            return true;
        }
    }

    public String toURLQuery() {
        return this.toURLQuery(false);
    }

    public String toURLQuery(boolean encode) {
        StringBuilder builder = new StringBuilder();
        if (this.size() > 0) {
            try {
                Iterator it = this.keySet().iterator();

                while(it.hasNext()) {
                    String name = (String)it.next();
                    String value = this.getString(name);
                    if (value == null) {
                        builder.append(name);
                    } else if (encode) {
                        builder.append(name + "=" + URLEncoder.encode(value, "UTF-8"));
                    } else {
                        builder.append(name + "=" + value);
                    }

                    if (it.hasNext()) {
                        builder.append("&");
                    }
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        return builder.toString();
    }

    static {
        transferMap.put(Long.class, new LongTransfer());
        transferMap.put(Integer.class, new IntegerTransfer());
        transferMap.put(String.class, new StringTransfer());
        transferMap.put(Double.class, new DoubleTransfer());
    }
}
