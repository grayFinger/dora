package com.dora.common.db.service;

import com.dora.common.db.bean.IDEntity;
import com.dora.common.db.dao.BaseDao;
import com.dora.common.db.sequence.SequenceUtils;
import com.dora.common.utils.ClassUtils;
import com.dora.common.utils.NumberUtil;
import com.dora.common.view.Page;
import com.dora.common.view.PageInfo;
import com.github.pagehelper.PageHelper;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class BaseService<T extends IDEntity> {
    @Autowired
    private BaseDao<T> baseDao;

    public BaseService() {
    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void save(T bean) {
        if (bean.isNew()) {
            this.insert(bean);
        } else {
            this.update(bean);
        }

    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void saveOrUpdate(T bean) {
        this.save(bean);
    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void insert(T bean) {
        if (bean != null) {
            this.setBeanId(bean);
            this.baseDao.insert(bean);
        }

    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void replace(T bean) {
        if (bean != null) {
            List<T> beans = (List)Stream.of(bean).collect(Collectors.toList());
            this.baseDao.replaceList(beans);
        }

    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void replaceList(List<T> beans) {
        if (beans != null && beans.size() > 0) {
            this.baseDao.replaceList(beans);
        }

    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void insert(List<T> beans) {
        if (beans != null && beans.size() > 0) {
            Iterator<T> var2 = beans.iterator();

            while(var2.hasNext()) {
                T bean = var2.next();
                this.setBeanId(bean);
            }

            this.baseDao.insertList(beans);
        }

    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void update(T bean) {
        this.baseDao.update(bean);
    }

    public T findById(Long id) {
        return this.baseDao.findById(id);
    }

    public PageInfo<T> findByPage(T bean, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        return new PageInfo(this.baseDao.findByExample(bean));
    }

    public PageInfo<T> findByPage(T bean, int start, int pagesize) {
        PageHelper.offsetPage(start, pagesize);
        return new PageInfo(this.baseDao.findByExample(bean));
    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void removeById(Long id) {
        this.baseDao.removeById(id);
    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void removeByIds(Long... ids) {
        this.baseDao.removeByIds(ids);
    }

    @Transactional(
        rollbackFor = {Throwable.class}
    )
    public void removeByIds(String ids) {
        Long[] idArr = NumberUtil.toLongs(ids);
        if (idArr != null && idArr.length > 0) {
            this.baseDao.removeByIds(idArr);
        }

    }

    public List<T> findAll() {
        return this.baseDao.findAll();
    }

    public List<T> findAll(T bean) {
        PageHelper.offsetPage(0, 0, false);
        return this.baseDao.findByExample(bean);
    }

    public T get(T bean) {
        return this.selectOne(bean);
    }

    public long getCount(T bean) {
        return this.baseDao.getCount(bean);
    }

    public T selectOne(T bean) {
        PageHelper.offsetPage(0, 1, false);
        List<T> list = this.baseDao.findByExample(bean);
        return list != null && list.size() > 0 ? (T) list.get(0) : null;
    }

    public boolean exists(T bean) {
        return this.getCount(bean) > 0L;
    }

    protected void setBeanId(T bean) {
        if (bean.isNew()) {
            bean.setId(SequenceUtils.nextID());
        }

    }

    public Class<T> getEntityClass() {
        return (Class<T>) ClassUtils.getGenericClass(this.getClass());
    }
}
