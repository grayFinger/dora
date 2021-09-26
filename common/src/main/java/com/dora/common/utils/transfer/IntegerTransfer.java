package com.dora.common.utils.transfer;

import org.apache.commons.lang.math.NumberUtils;

public class IntegerTransfer implements IValueTransfer<Integer> {
    public IntegerTransfer() {
    }

    public Integer getValue(Object value) {
        return NumberUtils.toInt(value + "");
    }
}
