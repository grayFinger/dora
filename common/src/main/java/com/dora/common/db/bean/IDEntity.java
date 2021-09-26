//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.db.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtilsBean;

public class IDEntity implements Serializable {
    private Long id;

    public IDEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JSONField(
        serialize = false,
        deserialize = false
    )
    public boolean isNew() {
        return this.id == null || this.id == 0L;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            IDEntity entity = (IDEntity)o;
            return this.getId() != null ? this.getId().equals(entity.getId()) : entity.getId() == null;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }

    public static <T extends IDEntity> Map<Long, T> toMap(List<T> list) {
        Map<Long, T> map = new LinkedHashMap();
        if (list != null && list.size() > 0) {
            Iterator<T> var2 = list.iterator();

            while(var2.hasNext()) {
                T bean = var2.next();
                map.put(bean.getId(), bean);
            }
        }

        return map;
    }

    public static <K, T extends IDEntity> Map<K, T> toMap(List<T> list, String key) {
        Map<K, T> map = new LinkedHashMap();
        if (list != null && list.size() > 0) {
            try {
                Iterator<T> var3 = list.iterator();

                while(var3.hasNext()) {
                    T bean = var3.next();
                    K value = (K) BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(bean, key);
                    if (value != null) {
                        map.put(value, bean);
                    }
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        return map;
    }
}
