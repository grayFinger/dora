package com.dora.common.view;

import java.util.List;

public interface ITree<T> {
    Long getId();

    Long getParentId();

    String getTitle();

    void setLevel(Integer level);

    default Long getValue() {
        return this.getId();
    }

    default String getLabel() {
        return this.getTitle();
    }

    List<T> getChildren();

    void setChildren(List<T> children);

    default void addChild(T bean) {
        List<T> list = this.getChildren();
        if (list != null) {
            list.add(bean);
        }

    }
}
