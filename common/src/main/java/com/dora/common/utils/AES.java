package com.dora.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static final String KEY_ALGORITHM = "AES";
    private static String algorithm = "AES/CBC/PKCS5Padding";
    private static String charset = "UTF-8";
    private static byte[] defaultIV = "0000000000000000".getBytes();

    public AES() {
    }

    protected static SecretKeySpec generateKey(byte[] encryptKey) throws Exception {
        int len = encryptKey.length % 8;
        int needLen = 0;
        if (encryptKey.length < 16) {
            needLen = 16;
        } else if (len > 0) {
            needLen = encryptKey.length + 8 - len;
        }

        if (needLen > 0) {
            byte[] newKeys = new byte[needLen];
            System.arraycopy(encryptKey, 0, newKeys, 0, encryptKey.length);
            encryptKey = newKeys;
        }

        return new SecretKeySpec(encryptKey, "AES");
    }

    public static byte[] crypt(int mode, byte[] content, byte[] encryptKey, byte[] ivByte) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKeySpec keySpec = generateKey(encryptKey);
        if (algorithm.toUpperCase().indexOf("ECB") == -1) {
            if (ivByte == null || ivByte.length == 0) {
                ivByte = defaultIV;
            }

            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);
            cipher.init(mode, keySpec, ivParameterSpec);
        } else {
            cipher.init(mode, keySpec);
        }

        return cipher.doFinal(content);
    }

    public static byte[] encrypt(byte[] content, byte[] encryptKey, byte[] ivByte) throws Exception {
        return crypt(1, content, encryptKey, ivByte);
    }

    public static byte[] decrypt(byte[] content, byte[] encryptKey, byte[] ivByte) throws Exception {
        return crypt(2, content, encryptKey, ivByte);
    }

    public static String encrypt(String content, String password, byte[] iv) throws Exception {
        byte[] result = encrypt(content.getBytes(charset), password.getBytes(charset), iv);
        return Base64.encode(result);
    }

    public static String decrypt(String content, String password, byte[] iv) throws Exception {
        byte[] contentBytes = Base64.decode(content);
        return new String(decrypt(contentBytes, password.getBytes(charset), iv), charset);
    }

    public static String encrypt(String content, String password) throws Exception {
        return encrypt((String)content, (String)password, (byte[])null);
    }

    public static String decrypt(String content, String password) throws Exception {
        return decrypt((String)content, (String)password, (byte[])null);
    }

    public static void setAlgorithm(String algorithm) {
        AES.algorithm = algorithm;
    }

    public static void setCharset(String charset) {
        AES.charset = charset;
    }

    public static void setDefaultIV(byte[] defaultIV) {
        AES.defaultIV = defaultIV;
    }

    public static void main(String[] args) throws Exception {
        String content = "MC4kxxrV4hcv72YrieoQ4xloZaKO8TnuodYjaS2pDl++ZABkTi4qIPeqKbUT4qX73/wIOxKFVaDTBB/gix8fcA==";
        String key = "e67af1641fe0b81e7a4131568602bbde";
        String value = "hello";
        String result = decrypt(content, key);
        System.out.println(result);
        byte[] b1 = new byte[1];
        byte[] b2 = new byte[]{0};
        System.out.println(b1[0] + "," + b2[0]);
        System.out.println();
    }
}
