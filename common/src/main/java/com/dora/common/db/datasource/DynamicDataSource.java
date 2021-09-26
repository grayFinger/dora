package com.dora.common.db.datasource;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dora.common.db.tx.TransactionContext;
import com.dora.common.db.tx.TxConnectionFactory;
import com.dora.common.db.tx.TxConnectionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

public class DynamicDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
    private static DynamicDataSource instance;
    private static byte[] lock = new byte[0];
    private static Map<Object, Object> dataSourceMap = new HashMap();

    public DynamicDataSource() {
    }

    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        dataSourceMap.putAll(targetDataSources);
        super.afterPropertiesSet();
    }

    public static Map<Object, Object> getDataSourceMap() {
        return dataSourceMap;
    }

    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDatasourceType();
    }

    public Connection getConnection() throws SQLException {
        String xid = TransactionContext.getXID();
        if (StringUtils.isEmpty(xid)) {
            return super.getConnection();
        } else {
            String ds = DataSourceContextHolder.getDatasourceType();
            TxConnectionProxy connection = TxConnectionFactory.getConnection(ds);
            if (connection == null) {
                connection = new TxConnectionProxy(ds, super.getConnection());
                TxConnectionFactory.putConnection(ds, connection);
            }

            return connection;
        }
    }

    public Connection getConnection(String username, String password) throws SQLException {
        String xid = TransactionContext.getXID();
        if (StringUtils.isEmpty(xid)) {
            return super.getConnection(username, password);
        } else {
            String ds = DataSourceContextHolder.getDatasourceType();
            TxConnectionProxy connection = TxConnectionFactory.getConnection(ds);
            if (connection == null) {
                connection = new TxConnectionProxy(ds, super.getConnection(username, password));
                TxConnectionFactory.putConnection(ds, connection);
            }

            return connection;
        }
    }

    public static synchronized DynamicDataSource getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new DynamicDataSource();
                }
            }
        }

        return instance;
    }

    public void destroy() throws Exception {
        DataSourceContextHolder.clearDatasourceType();
    }
}
