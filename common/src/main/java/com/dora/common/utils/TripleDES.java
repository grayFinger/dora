package com.dora.common.utils;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TripleDES {
    private static final String Algorithm = "DESede";
    private static final String DES_PADDING = "DESede/ECB/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    public TripleDES() {
    }

    private static SecretKey getKey(String key) throws Exception {
        byte[] bytes = key.getBytes("UTF-8");
        if (bytes.length != 24) {
            byte[] bs = new byte[24];
            System.arraycopy(bytes, 0, bs, 0, Math.min(24, bytes.length));
            bytes = bs;
        }

        return new SecretKeySpec(bytes, "DESede");
    }

    public static byte[] encrypt(byte[] data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(1, getKey(key));
            return cipher.doFinal(data);
        } catch (Exception var3) {
            throw new RuntimeException("DESede加密失败", var3);
        }
    }

    public static byte[] decrypt(byte[] data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(2, getKey(key));
            return cipher.doFinal(data);
        } catch (Exception var3) {
            throw new RuntimeException("DESede解密失败", var3);
        }
    }

    public static String decrypt(String data, String key) {
        try {
            byte[] bytes = Base64.decode(data);
            byte[] retBytes = decrypt(bytes, key);
            return new String(retBytes, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String data, String key) {
        try {
            byte[] bytes = data.getBytes("UTF-8");
            byte[] retBytes = encrypt(bytes, key);
            return Base64.encode(retBytes);
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
