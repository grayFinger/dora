package com.dora.common.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesUtil {
    private static final String SECRET_KEY_TYPE = "DES";
    private static final String ECB_MOB = "DES/ECB/PKCS5Padding";
    private static final String CHAESET_NAME = "UTF-8";

    public DesUtil() {
    }

    private static Key getKey(String password) throws Exception {
        byte[] DESkey = password.getBytes("UTF-8");
        DESKeySpec keySpec = new DESKeySpec(DESkey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(keySpec);
    }

    public static String encode(String data, String password, String ecbMode) throws Exception {
        Cipher enCipher = Cipher.getInstance(ecbMode);
        Key key = getKey(password);
        enCipher.init(1, key);
        byte[] pasByte = enCipher.doFinal(data.getBytes("UTF-8"));
        return encodeByte2HexStr(pasByte);
    }

    public static String encode(String data, String password) throws Exception {
        return encode(data, password, "DES/ECB/PKCS5Padding");
    }

    public static String decode(String data, String password, String ecbMode) throws Exception {
        Cipher deCipher = Cipher.getInstance(ecbMode);
        Key key = getKey(password);
        deCipher.init(2, key);
        byte[] pasByte = deCipher.doFinal(parseHexStr2Byte(data));
        return new String(pasByte, "UTF-8");
    }

    public static String decode(String data, String password) throws Exception {
        return decode(data, password, "DES/ECB/PKCS5Padding");
    }

    public static String encodeByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }
}
