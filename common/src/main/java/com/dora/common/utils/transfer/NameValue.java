//
package com.dora.common.utils.transfer;

import java.math.BigDecimal;
import java.util.HashMap;
import org.apache.commons.lang.math.NumberUtils;

public class NameValue extends HashMap<String, Object> {
    public NameValue() {
        this((String)null, (Object)null);
    }

    public NameValue(String name, Object value) {
        this.setName(name);
        this.setValue(value);
    }

    public Object getValue() {
        return this.get("value");
    }

    public String getName() {
        return (String)this.get("name");
    }

    public void setName(String name) {
        this.put("name", name);
    }

    public void setValue(Object value) {
        this.put("value", value);
    }

    public String getStringValue() {
        Object value = this.getValue();
        if (value == null) {
            return null;
        } else {
            return value instanceof String ? (String)value : value.toString();
        }
    }

    public long getLongValue() {
        Object value = this.getValue();
        if (value == null) {
            return 0L;
        } else if (value instanceof Long) {
            return (Long)value;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal)value).longValue();
        } else {
            return value instanceof String ? Long.valueOf((String)value) : 0L;
        }
    }

    public double getDoubleValue() {
        Object value = this.getValue();
        if (value == null) {
            return 0.0D;
        } else if (value instanceof Double) {
            return (Double)value;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal)value).doubleValue();
        } else {
            return value instanceof Long ? ((Long)value).doubleValue() : NumberUtils.toDouble(value.toString(), 0.0D);
        }
    }

    public double getIntValue() {
        Object value = this.getValue();
        if (value == null) {
            return 0.0D;
        } else if (value instanceof Integer) {
            return (double)(Integer)value;
        } else if (value instanceof BigDecimal) {
            return (double)((BigDecimal)value).intValue();
        } else {
            return value instanceof Long ? (double)((Long)value).intValue() : (double)NumberUtils.toInt(value.toString(), 0);
        }
    }
}
