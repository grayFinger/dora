package com.dora.common.db.datasource;

import cn.hutool.core.util.HexUtil;
import com.dora.common.utils.AES;

public class DataSourceEncrypt {
    private static final String PKEY = "1234565437892132";

    public DataSourceEncrypt() {
    }

    public static String encrypt(String value) throws Exception {
        String key = HexUtil.encodeHexStr("1234565437892132");
        return AES.encrypt(value, key);
    }

    public static String decrypt(String value) throws Exception {
        String key = HexUtil.encodeHexStr("1234565437892132");
        return AES.decrypt(value, key);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(decrypt("mt+qOHf70vJUv6rOzPApZQ=="));
    }
}
