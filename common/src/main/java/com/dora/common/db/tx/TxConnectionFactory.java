package com.dora.common.db.tx;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TxConnectionFactory {
    private static final ThreadLocal<Map<String, TxConnectionProxy>> CONNECTION_HOLDER = ThreadLocal.withInitial(() -> {
        return new ConcurrentHashMap();
    });

    public TxConnectionFactory() {
    }

    public static void putConnection(String ds, TxConnectionProxy connection) {
        Map<String, TxConnectionProxy> concurrentHashMap = (Map)CONNECTION_HOLDER.get();
        if (!concurrentHashMap.containsKey(ds)) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException var4) {
                var4.printStackTrace();
            }

            concurrentHashMap.put(ds, connection);
        }

    }

    public static TxConnectionProxy getConnection(String ds) {
        return (TxConnectionProxy)((Map)CONNECTION_HOLDER.get()).get(ds);
    }

    public static void notify(Boolean state) {
        try {
            Map<String, TxConnectionProxy> concurrentHashMap = (Map)CONNECTION_HOLDER.get();
            Iterator var2 = concurrentHashMap.values().iterator();

            while(var2.hasNext()) {
                TxConnectionProxy connectionProxy = (TxConnectionProxy)var2.next();
                connectionProxy.notify(state);
            }
        } finally {
            CONNECTION_HOLDER.remove();
        }

    }
}
