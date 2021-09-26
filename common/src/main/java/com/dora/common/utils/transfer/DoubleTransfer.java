package com.dora.common.utils.transfer;

import org.apache.commons.lang.math.NumberUtils;

public class DoubleTransfer implements IValueTransfer<Double> {
    public DoubleTransfer() {
    }

    public Double getValue(Object value) {
        return NumberUtils.toDouble(value + "");
    }
}
