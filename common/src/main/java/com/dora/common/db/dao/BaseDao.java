package com.dora.common.db.dao;


import java.util.List;

import com.dora.common.db.bean.IDEntity;
import org.apache.ibatis.annotations.Param;

public interface BaseDao<T extends IDEntity> {
    void insert(T bean);

    void insertList(List<T> beans);

    void replaceList(List<T> beans);

    void update(T bean);

    void removeById(@Param("id") Long id);

    void removeByIds(@Param("ids") Long... ids);

    T findById(@Param("id") long id);

    List<T> findByExample(@Param("o") T bean);

    long getCount(@Param("o") T bean);

    List<T> findAll();
}
