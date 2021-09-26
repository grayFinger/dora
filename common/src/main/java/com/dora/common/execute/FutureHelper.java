package com.dora.common.execute;


import com.dora.common.utils.SpringContextUtils;

import java.util.concurrent.Executor;

public class FutureHelper {
    private Executor executor;

    public FutureHelper(Executor executor) {
        this.executor = executor;
    }

    public FutureHelper() {
        this.executor = (Executor) SpringContextUtils.getBean("taskExecutor", Executor.class);
    }

    public FutureHelperResolver create() {
        return new FutureHelperResolver(this.executor);
    }
}
