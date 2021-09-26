package com.dora.common.db.aop;

import java.util.UUID;

import com.dora.common.db.annotation.TX;
import com.dora.common.db.tx.TransactionContext;
import com.dora.common.db.tx.TxConnectionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@Aspect
public class DynamicTransactionAspect {
    private static final Logger log = LoggerFactory.getLogger(DynamicTransactionAspect.class);

    public DynamicTransactionAspect() {
    }

    @Around("@annotation(TX)")
    public Object invoke(ProceedingJoinPoint point, TX TX) throws Throwable {
        if (!StringUtils.isEmpty(TransactionContext.getXID())) {
            return point.proceed();
        } else {
            boolean state = true;
            String xid = UUID.randomUUID().toString();
            TransactionContext.bind(xid);

            Object o;
            try {
                o = point.proceed();
            } catch (Throwable var10) {
                if (this.needRollback(var10, TX)) {
                    state = false;
                }

                throw var10;
            } finally {
                TxConnectionFactory.notify(state);
                TransactionContext.remove();
            }

            return o;
        }
    }

    private boolean needRollback(Throwable e, TX tx) {
        Class[] var3;
        int var4;
        int var5;
        Class tclass;
        if (tx.noRollbackFor().length > 0) {
            var3 = tx.noRollbackFor();
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                tclass = var3[var5];
                if (tclass.isAssignableFrom(e.getClass())) {
                    return false;
                }
            }
        }

        if (tx.rollbackFor().length > 0) {
            var3 = tx.rollbackFor();
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                tclass = var3[var5];
                if (tclass.isAssignableFrom(e.getClass())) {
                    return true;
                }
            }
        }

        return RuntimeException.class.isAssignableFrom(e.getClass());
    }
}
