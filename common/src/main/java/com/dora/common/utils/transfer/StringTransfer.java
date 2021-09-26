package com.dora.common.utils.transfer;

public class StringTransfer implements IValueTransfer<String> {
    public StringTransfer() {
    }

    public String getValue(Object value) {
        return value + "";
    }
}
