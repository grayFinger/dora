package com.dora.common.db.tx;

import org.springframework.util.StringUtils;

public class TransactionContext {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal();

    public TransactionContext() {
    }

    public static String getXID() {
        String xid = (String)CONTEXT_HOLDER.get();
        return !StringUtils.isEmpty(xid) ? xid : null;
    }

    public static String unbind(String xid) {
        CONTEXT_HOLDER.remove();
        return xid;
    }

    public static String bind(String xid) {
        CONTEXT_HOLDER.set(xid);
        return xid;
    }

    public static void remove() {
        CONTEXT_HOLDER.remove();
    }
}
