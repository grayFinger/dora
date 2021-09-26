package com.dora.common.utils.transfer;

import org.apache.commons.lang.math.NumberUtils;

public class LongTransfer implements IValueTransfer<Long> {
    public LongTransfer() {
    }

    public Long getValue(Object value) {
        return NumberUtils.toLong(value + "");
    }
}
