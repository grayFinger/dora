package com.dora.common.utils;

public class Base64 {
    public Base64() {
    }

    public static byte[] decode(String data) {
        return java.util.Base64.getDecoder().decode(data);
    }

    public static String encode(byte[] data) {
        return java.util.Base64.getEncoder().encodeToString(data);
    }
}
