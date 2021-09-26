package com.dora.common.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TreeResolver<T extends ITree> {
    private Collection<T> data;

    public TreeResolver(Collection<T> data) {
        this.data = data;
    }

    public List<T> findByParent(long parentId) {
        List<T> tmps = new ArrayList();
        if (this.data != null && this.data.size() > 0) {
            if (parentId == 0L) {
                parentId = this.getMinParentId();
            }

            Iterator var4 = this.data.iterator();

            while(var4.hasNext()) {
                T t = (T) var4.next();
                if (t.getParentId() == parentId) {
                    tmps.add(t);
                }
            }
        }

        return tmps;
    }

    private Long getMinParentId() {
        long min = 9223372036854775807L;
        Iterator var3 = this.data.iterator();

        while(var3.hasNext()) {
            T t = (T) var3.next();
            if (min > t.getParentId()) {
                min = t.getParentId();
            }
        }

        return min;
    }

    public List<T> parseTree(long parentId, boolean emptyChilds) {
        List<T> tmps = this.findByParent(parentId);
        if (tmps.size() > 0) {
            Iterator var5 = tmps.iterator();

            while(true) {
                while(var5.hasNext()) {
                    T t = (T) var5.next();
                    List<T> childs = this.parseTree(t.getId(), emptyChilds);
                    if (childs != null && childs.size() > 0) {
                        t.setChildren(childs);
                    } else if (emptyChilds) {
                        t.setChildren(new ArrayList());
                    } else {
                        t.setChildren((List)null);
                    }
                }

                return tmps;
            }
        } else {
            return tmps;
        }
    }

    public List<T> parseTree(long parentId) {
        return this.parseTree(parentId, false);
    }
}
