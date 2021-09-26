package com.dora.common.db.aop;

import com.dora.common.db.annotation.DS;
import com.dora.common.db.datasource.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DynamicDataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    public DynamicDataSourceAspect() {
    }

    @Before("@annotation(DS)")
    public void changeDataSource(JoinPoint point, DS DS) throws Throwable {
        DataSourceContextHolder.setDatasourceType(DS.value());
    }

    @After("@annotation(DS)")
    public void restoreDataSource(JoinPoint point, DS DS) {
        DataSourceContextHolder.clearDatasourceType();
    }
}
