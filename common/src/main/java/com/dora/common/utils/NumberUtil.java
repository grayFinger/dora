package com.dora.common.utils;

import org.apache.commons.lang.math.NumberUtils;

public class NumberUtil {
    public NumberUtil() {}

    public static Long[] toLongs(String ids) {
        Long[] idArr = null;
        if (ids != null) {
            if (ids.indexOf(",") == -1) {
                idArr = new Long[]{NumberUtils.toLong(ids)};
            } else {
                String[] _idArr = ids.split(",", -1);
                if (_idArr.length > 0) {
                    idArr = new Long[_idArr.length];
                    int index = 0;
                    String[] var4 = _idArr;
                    int var5 = _idArr.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        String id = var4[var6];
                        idArr[index++] = NumberUtils.toLong(id);
                    }
                }
            }
        }
        return idArr;
    }
}
