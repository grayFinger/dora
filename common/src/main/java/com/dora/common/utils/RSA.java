package com.dora.common.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSA {
    public static final String CHARSET = "UTF-8";

    public static final String RSA_ALGORITHM = "RSA";

    public static StringMap createKeys(int keySize) {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[RSA]");
        }
        kpg.initialize(keySize);
        KeyPair keyPair = kpg.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encode(publicKey.getEncoded());
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encode(privateKey.getEncoded());
        StringMap keyPairMap = new StringMap();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    public static StringMap createKeys() {
        return createKeys(1024);
    }

    public static RSAPublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
            RSAPublicKey key = (RSAPublicKey)keyFactory.generatePublic(x509KeySpec);
            return key;
        } catch (Exception e) {
            throw new RuntimeException("获取公钥失败", e);
        }
    }

    public static RSAPrivateKey getPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            RSAPrivateKey key = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
            return key;
        } catch (Exception e) {
            throw new RuntimeException("获取私钥失败", e);
        }
    }

    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, publicKey);
            return Base64.encode(rsaSplitCodec(cipher, 1, data.getBytes("UTF-8"), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串["+ data + "]时遇到异常", e);
        }
    }

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, privateKey);
            return new String(rsaSplitCodec(cipher, 2, Base64.decode(data), privateKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("解密字符串"+ data + "]时遇到异常", e);
        }
    }

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, privateKey);
            return Base64.encode(rsaSplitCodec(cipher, 1, data.getBytes("UTF-8"), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串["+ data + "]时遇到异常", e);
        }
    }

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, publicKey);
            return new String(rsaSplitCodec(cipher, 2, Base64.decode(data), publicKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("解密字符串"+ data + "]时遇到异常", e);
        }
    }

    public static String publicDecrypt(String data, String publicKey) {
        return publicDecrypt(data, getPublicKey(publicKey));
    }

    public static String publicEncrypt(String data, String publicKey) {
        return publicEncrypt(data, getPublicKey(publicKey));
    }

    public static String privateDecrypt(String data, String privateKey) {
        return privateDecrypt(data, getPrivateKey(privateKey));
    }

    public static String privateEncrypt(String data, String privateKey) {
        return privateEncrypt(data, getPrivateKey(privateKey));
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == 2) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] resultDatas = null;
        try {
            while (datas.length > offSet) {
                byte[] buff;
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            resultDatas = out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为["+ maxBlock + "]的数据时发生异常", e);
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (Exception exception) {}
        }
        return resultDatas;
    }

    public static void main(String[] args) {
        StringMap map = createKeys();
        System.out.println("publicKey:" + map.getString("publicKey"));
        System.out.println("----------------");
        System.out.println("privateKey:" + map.getString("privateKey"));
    }
}